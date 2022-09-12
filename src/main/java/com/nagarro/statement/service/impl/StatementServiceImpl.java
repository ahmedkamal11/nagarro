package com.nagarro.statement.service.impl;


import com.nagarro.statement.dao.StatementDao;
import com.nagarro.statement.dto.AccountStatementDto;
import com.nagarro.statement.dto.StatementParametersDto;
import com.nagarro.statement.exception.MissingMandatoryDate;
import com.nagarro.statement.service.StatementService;
import com.nagarro.statement.until.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatementServiceImpl implements StatementService {

    private static final LocalDate DEFAULT_DATE = LocalDate.now().minusMonths(3);

    @Autowired
    StatementDao statementDao;

    public List<AccountStatementDto> getStatementsByAccountId(Long accountId, StatementParametersDto params){
        List<AccountStatementDto> statements = statementDao.findAllByAccountId(accountId);
        List<AccountStatementDto> result = statements;

        // filter by amount
        if(params.getAmountFrom()!=null || params.getAmountTo()!=null){
           result= filterByAmount(statements,params.getAmountFrom(),params.getAmountTo());
        }

        // filter by date
        if(params.getDateTo()!=null || params.getDateFrom()!=null){
            result= filterByDate(result,
                    DateTimeUtil.convertStringToLocalDate(params.getDateFrom()),
                    DateTimeUtil.convertStringToLocalDate(params.getDateTo()));
        }

        // filter by last 3 months
        if(params.getDateFrom()==null && params.getDateTo()==null
                && params.getAmountFrom()==null && params.getAmountTo()==null){

            result= filterByDate(statements,DEFAULT_DATE,LocalDate.now());
        }

        // hash accountNumber
        for (AccountStatementDto statement : result) {
            statement.setAccountNumber(hashString(statement.getAccountNumber()));
        }

        return result;
    }


    private List<AccountStatementDto> filterByAmount(List<AccountStatementDto> statementDtoList,
                                BigDecimal amountFrom,
                                BigDecimal amountTo){

        if(amountFrom==null || amountTo==null){
            log.error("amount to and from : both should be filled or both should be null");
            throw new MissingMandatoryDate("amount to and from : both should be filled or both should be null");
        }

         return statementDtoList.stream().filter(
                statement -> statement.getAmount().compareTo(amountFrom)>=0 &&
                        statement.getAmount().compareTo(amountTo)<=0).
                collect(Collectors.toList());

    }

    private List<AccountStatementDto> filterByDate(List<AccountStatementDto> statementDtoList,
                              LocalDate dateFrom,
                              LocalDate dateTo){

        return statementDtoList.stream().filter(
                        statement -> (statement.getDate().isBefore(dateTo) || statement.getDate().isEqual(dateTo))&&
                                (statement.getDate().isAfter(dateFrom) || statement.getDate().isEqual(dateFrom))).
                collect(Collectors.toList());
    }


    private String hashString(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("error while hashing accountNumber {}",str);
            return str;
        }
    }

}
