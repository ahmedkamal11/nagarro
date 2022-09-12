package com.nagarro.statement.model;


import com.nagarro.statement.dto.AccountStatementDto;
import com.nagarro.statement.until.DateTimeUtil;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountStatementMapper implements RowMapper<AccountStatementDto> {


    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ACCOUNT_NUMBER = "account_number";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DATE = "datefield";

    @Override
    public AccountStatementDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        AccountStatementDto dto = new AccountStatementDto();
        dto.setId(rs.getLong(COLUMN_ID));
        dto.setAccountNumber(rs.getString(COLUMN_ACCOUNT_NUMBER));
        dto.setAmount(new BigDecimal(rs.getString(COLUMN_AMOUNT)));
        dto.setDate(DateTimeUtil.convertStringToLocalDate(rs.getString(COLUMN_DATE)));
        return dto;
    }

}
