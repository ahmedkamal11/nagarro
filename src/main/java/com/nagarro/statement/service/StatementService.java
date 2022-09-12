package com.nagarro.statement.service;


import com.nagarro.statement.dto.AccountStatementDto;
import com.nagarro.statement.dto.StatementParametersDto;

import java.util.List;

public interface StatementService {

    List<AccountStatementDto> getStatementsByAccountId(Long accountId, StatementParametersDto params);
}
