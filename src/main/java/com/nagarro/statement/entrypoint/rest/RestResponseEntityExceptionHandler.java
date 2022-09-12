package com.nagarro.statement.entrypoint.rest;


import com.nagarro.statement.dto.ErrorMessage;
import com.nagarro.statement.exception.AuthenticationException;
import com.nagarro.statement.exception.InvalidDataFormat;
import com.nagarro.statement.exception.MissingMandatoryDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ InvalidDataFormat.class, MissingMandatoryDate.class})
    public ResponseEntity<ErrorMessage> handleBadRequests(Exception e){
        return new ResponseEntity<>(
                new ErrorMessage(HttpStatus.BAD_REQUEST,
                        LocalDateTime.now(),
                        e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ AuthorizationServiceException.class , AuthenticationException.class})
    public ResponseEntity<ErrorMessage> handleAuthorizationServiceException(Exception e){
        return new ResponseEntity<>(
                new ErrorMessage(HttpStatus.UNAUTHORIZED,
                        LocalDateTime.now(),
                        e.getMessage()), HttpStatus.UNAUTHORIZED);
    }


}
