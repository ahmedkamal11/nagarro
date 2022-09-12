package com.nagarro.statement.security.controller;


import com.nagarro.statement.exception.AuthenticationException;
import com.nagarro.statement.security.dto.JwtRequestModel;
import com.nagarro.statement.security.dto.JwtResponseModel;
import com.nagarro.statement.security.service.JwtUserDetailsService;
import com.nagarro.statement.security.config.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
public class JwtController {
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseModel> createToken(@RequestBody JwtRequestModel request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword())
            );
        } catch (DisabledException e) {
            log.error("user is disabled !!",e);
            throw new AuthenticationException("user is disabled !!");
        } catch (BadCredentialsException e) {
            log.error("invalid credentials!!",e);
            throw new AuthenticationException("invalid credentials!! ");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String jwtToken = tokenService.generateJwtToken(userDetails);
        return ResponseEntity.ok(new JwtResponseModel(jwtToken));
    }

}
