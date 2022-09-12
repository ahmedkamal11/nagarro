package com.nagarro.statement.service;

import com.nagarro.statement.dao.StatementDao;
import com.nagarro.statement.dto.AccountStatementDto;
import com.nagarro.statement.dto.StatementParametersDto;
import com.nagarro.statement.exception.InvalidDataFormat;
import com.nagarro.statement.exception.MissingMandatoryDate;
import com.nagarro.statement.service.impl.StatementServiceImpl;
import com.nagarro.statement.until.DateTimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("java:S5778")
@ExtendWith(MockitoExtension.class)
class StatementServiceTests {

	@InjectMocks
	private StatementServiceImpl statementService;

	@Mock
	StatementDao statementDao;

	List<AccountStatementDto> statementDtoList = new ArrayList<>();

	@BeforeEach
	void initTestDate (){
		statementDtoList.add(new AccountStatementDto(1l,"01234",LocalDate.now(),BigDecimal.valueOf(10)));
		statementDtoList.add(new AccountStatementDto(1l,"04567",LocalDate.now().minusMonths(1),BigDecimal.valueOf(20)));
		statementDtoList.add(new AccountStatementDto(1l,"089456",LocalDate.now().minusMonths(4),BigDecimal.valueOf(30)));
	}
	@Test
	void test_GetStatementsByAccountId_LastThreeMonths() {

		Mockito.when(statementDao.findAllByAccountId(1l)).thenReturn(statementDtoList);
		List<AccountStatementDto> result =statementService.getStatementsByAccountId(1L , new StatementParametersDto());
		Assertions.assertEquals(2,result.size());
	}


	@Test
	void test_GetStatementsByAccountId_FilterByAmount() {

		Mockito.when(statementDao.findAllByAccountId(1l)).thenReturn(statementDtoList);
		List<AccountStatementDto> result =statementService.getStatementsByAccountId(1L ,
				new StatementParametersDto(BigDecimal.ONE,BigDecimal.TEN,null,DateTimeUtil.localDateToString(null)));
		Assertions.assertEquals(1,result.size());
	}


	@Test
	void test_GetStatementsByAccountId_FilterByAmountAndDate() {

		Mockito.when(statementDao.findAllByAccountId(1l)).thenReturn(statementDtoList);
		List<AccountStatementDto> result =statementService.getStatementsByAccountId(1L ,
				new StatementParametersDto(BigDecimal.valueOf(1),
						BigDecimal.valueOf(25),
						DateTimeUtil.localDateToString(LocalDate.now().minusMonths(3)),
						DateTimeUtil.localDateToString(LocalDate.now())));
		Assertions.assertEquals(2,result.size());
	}

	@Test
	void test_GetStatementsByAccountId_FilterByDate() {

		Mockito.when(statementDao.findAllByAccountId(1l)).thenReturn(statementDtoList);
		List<AccountStatementDto> result =statementService.getStatementsByAccountId(1L ,
				new StatementParametersDto(null,
						null,
						DateTimeUtil.localDateToString(LocalDate.now().minusMonths(3)),
						DateTimeUtil.localDateToString(LocalDate.now())));
		Assertions.assertEquals(2,result.size());
	}

	@Test
	void test_GetStatementsByAccountId_DateToIsNull() {
		Mockito.when(statementDao.findAllByAccountId(1l)).thenReturn(statementDtoList);

		Assertions.assertThrows(MissingMandatoryDate.class, () -> {
			statementService.getStatementsByAccountId(1L ,
					new StatementParametersDto(BigDecimal.valueOf(1),
							BigDecimal.valueOf(25),
							DateTimeUtil.localDateToString(LocalDate.now().minusMonths(3)),
							null));
		});
	}

	@Test
	void test_GetStatementsByAccountId_AmountToIsNull() {
		Mockito.when(statementDao.findAllByAccountId(1l)).thenReturn(statementDtoList);

		Assertions.assertThrows(MissingMandatoryDate.class, () -> {
			statementService.getStatementsByAccountId(1L ,
					new StatementParametersDto(BigDecimal.valueOf(1),
							null,
							DateTimeUtil.localDateToString(LocalDate.now().minusMonths(3)),
							DateTimeUtil.localDateToString(LocalDate.now())));
		});
	}


	@Test
	void test_GetStatementsByAccountId_DateToIsInvalidFormat() {
		Mockito.when(statementDao.findAllByAccountId(1l)).thenReturn(statementDtoList);

		Assertions.assertThrows(InvalidDataFormat.class, () -> {
			statementService.getStatementsByAccountId(1L ,
					new StatementParametersDto(BigDecimal.valueOf(1),
							BigDecimal.valueOf(25),
							DateTimeUtil.localDateToString(LocalDate.now().minusMonths(3)),
							"05.30.2020"));
		});
	}


}
