package com.nagarro.statement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatementDto {
    private Long id;
    private String accountNumber;
    private LocalDate date;
    private BigDecimal amount;

}
