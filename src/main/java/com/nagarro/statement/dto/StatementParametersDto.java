package com.nagarro.statement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementParametersDto {

    private BigDecimal amountFrom;
    private BigDecimal amountTo;

    private String dateFrom;
    private String dateTo;
}
