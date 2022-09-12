package com.nagarro.statement.controller;

import com.nagarro.statement.dto.AccountStatementDto;
import com.nagarro.statement.dto.StatementParametersDto;
import com.nagarro.statement.entrypoint.rest.StatementController;
import com.nagarro.statement.service.StatementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@SuppressWarnings("java:S5778")
@ExtendWith(MockitoExtension.class)
class StatementControllerTests {


    @InjectMocks
    StatementController statementController;

    @Mock
    StatementService statementService;

    @Bean
    public Authentication authenticationUser() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("user", "password", authorities); return authentication; }


    @Bean
    public Authentication authenticationAdmin() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("admin", "password", authorities); return authentication; }

    @Test
    void test_GetStatements_ForUser(){

        Assertions.assertThrows(AuthorizationServiceException.class, () -> {
         statementController.getStatements(1l, BigDecimal.ONE,BigDecimal.TEN,null,null, authenticationUser());
        });
    }

    @Test
    void test_GetStatements_ForUserWithNoParams(){

        Mockito.when(statementService.getStatementsByAccountId(1l,new StatementParametersDto(null,null,null,null)))
                .thenReturn(Arrays.asList(new AccountStatementDto(1l,"01234", LocalDate.now(),BigDecimal.valueOf(10))));

        List<AccountStatementDto> response = statementController.getStatements(1l, null,null,null,null, authenticationUser());
        Assertions.assertEquals(1,response.size());
    }


    @Test
    void test_GetStatements_ForAdmin(){

        Mockito.when(statementService.getStatementsByAccountId(1l,new StatementParametersDto(BigDecimal.ONE,BigDecimal.TEN,null,null)))
                .thenReturn(Arrays.asList(new AccountStatementDto(1l,"01234", LocalDate.now(),BigDecimal.valueOf(10))));

        List<AccountStatementDto> response = statementController.getStatements(1l, BigDecimal.ONE,BigDecimal.TEN,null,null, authenticationAdmin());
        Assertions.assertEquals(1,response.size());
    }

}
