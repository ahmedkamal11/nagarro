package com.nagarro.statement.exception;

public class MissingMandatoryDate extends RuntimeException{

    public MissingMandatoryDate(String msg){
        super(msg);
    }
}
