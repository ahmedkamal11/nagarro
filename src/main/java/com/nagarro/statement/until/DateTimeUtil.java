package com.nagarro.statement.until;

import com.nagarro.statement.exception.InvalidDataFormat;
import com.nagarro.statement.exception.MissingMandatoryDate;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class DateTimeUtil {

    private DateTimeUtil(){

    }

    private static final String DATE_FORMAT = "dd.MM.yyyy";

    public static LocalDate convertStringToLocalDate(String str){

        if(str==null){
            log.error("provided date is null");
            throw new MissingMandatoryDate("provided date is null");
        }

        try{
            return LocalDate.parse(str, DateTimeFormatter.ofPattern(DATE_FORMAT));
        }catch (DateTimeParseException e){
            log.error("provided date is not in a valid format",e);
            throw new InvalidDataFormat("provided date is not in a valid format");
        }
    }


    public static String localDateToString(LocalDate localDate){
        if(localDate ==null)
            return null;

        return localDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
