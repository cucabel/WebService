package com.statistics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.statistics.domain.Statistic;
import com.statistics.domain.Transaction;
import com.statistics.repository.TransactionRepository;
import com.statistics.service.impl.StatisticServiceImpl;

@SpringBootTest
public class StatisticsServiceTest {

	@InjectMocks
	StatisticServiceImpl statisticServiceImpl;
	@Mock
	TransactionRepository transactionRepository;
	
	String currentTimestamp;
	List<Transaction> transactions = new ArrayList<>();
	Statistic statistic;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);

		currentTimestamp = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now());
		
		transactions.add(new Transaction("-5000.00", currentTimestamp));
		transactions.add(new Transaction("400.00", currentTimestamp));
		transactions.add(new Transaction("6000.00", currentTimestamp));	
		
		statistic = new Statistic();
		statistic.setSum(new BigDecimal("1400.00"));
		statistic.setAvg(new BigDecimal("466.67"));
		statistic.setMax(new BigDecimal("6000.00"));
		statistic.setMin(new BigDecimal("-5000.00"));
		statistic.setCount((long) 3);
	}

	@Test
	public void testGetStatistic() {
		when(transactionRepository.getAll()).thenReturn(transactions);

		assertEquals(statistic, statisticServiceImpl.getStatistic());
	}

}
