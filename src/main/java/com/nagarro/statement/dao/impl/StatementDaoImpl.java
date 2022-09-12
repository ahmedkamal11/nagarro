package com.nagarro.statement.dao.impl;


import com.nagarro.statement.dao.StatementDao;
import com.nagarro.statement.dto.AccountStatementDto;
import com.nagarro.statement.model.AccountStatementMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StatementDaoImpl implements StatementDao {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public static final String GET_STATEMENTS_FOR_ACCOUNT_QUERY = "select acc.account_number," +
            " sts.id," +
            " sts.amount," +
            " sts.datefield from account acc inner join statement sts on sts.account_id = acc.id where acc.id = :id";



    @Override
    public List<AccountStatementDto> findAllByAccountId(Long accountId) {
        log.info("fetch statements for account {}",accountId);
        return jdbcTemplate.query(
                GET_STATEMENTS_FOR_ACCOUNT_QUERY,
                new MapSqlParameterSource("id",accountId.toString()),
                new AccountStatementMapper());
    }
}
