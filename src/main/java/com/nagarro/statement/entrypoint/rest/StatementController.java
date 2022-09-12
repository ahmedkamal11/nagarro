package com.nagarro.statement.entrypoint.rest;


import com.nagarro.statement.dto.AccountStatementDto;
import com.nagarro.statement.dto.StatementParametersDto;
import com.nagarro.statement.service.StatementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
public class StatementController {


    @Autowired
    StatementService statementService;


    @GetMapping("/account/{accountId}/statement")
    public List<AccountStatementDto> getStatements(@PathVariable Long accountId,
                                                   @RequestParam(required = false) BigDecimal amountFrom,
                                                   @RequestParam(required = false) BigDecimal amountTo,
                                                   @RequestParam(required = false) String dateFrom,
                                                   @RequestParam(required = false) String dateTo,
                                                   Authentication authentication){


        log.info("get statements for account {} with params amountFrom {} amountTo {} dateFrom {} dateTo {}",
                accountId,amountFrom,amountTo,dateFrom,dateTo);

        // check authority
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        if(authorities.contains(new SimpleGrantedAuthority("USER")) &&
                (amountFrom !=null || amountTo !=null || dateFrom!=null || dateTo!=null)){
                throw new AuthorizationServiceException("you are not allowed to call this API with any parameters");
        }

        return statementService.getStatementsByAccountId(accountId,new StatementParametersDto(amountFrom,amountTo,
                dateFrom, dateTo));
    }

}
