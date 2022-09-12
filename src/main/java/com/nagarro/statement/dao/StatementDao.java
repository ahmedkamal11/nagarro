package com.nagarro.statement.dao;

import com.nagarro.statement.dto.AccountStatementDto;

import java.util.List;

public interface StatementDao {

    List<AccountStatementDto> findAllByAccountId(Long accountId);
}
