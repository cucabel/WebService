package com.statistics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.statistics.domain.Statistic;
import com.statistics.domain.Transaction;
import com.statistics.repository.TransactionRepository;

@SpringBootTest
public class StatisticsServiceTest {

	@Autowired
	StatisticService statisticService;
	@Autowired
	TransactionRepository transactionRepository;

	@Test
	public void testGetStatistic() {

		String currentTimestamp = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now());
		transactionRepository.save(new Transaction("-5000.00", currentTimestamp));
		transactionRepository.save(new Transaction("400.00", currentTimestamp));
		transactionRepository.save(new Transaction("6000.00", currentTimestamp));

		Statistic statistic = new Statistic();
		statistic.setSum(new BigDecimal("1400.00"));
		statistic.setAvg(new BigDecimal("466.67"));
		statistic.setMax(new BigDecimal("6000.00"));
		statistic.setMin(new BigDecimal("-5000.00"));
		statistic.setCount((long) 3);

		assertEquals(statistic, statisticService.getStatistic());

	}

}
