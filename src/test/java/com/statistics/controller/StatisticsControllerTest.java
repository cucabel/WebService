package com.statistics.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.statistics.domain.Statistic;
import com.statistics.repository.TransactionRepository;

@SpringBootTest
public class StatisticsControllerTest {

	@Autowired
	StatisticController statisticController;

	@Autowired
	TransactionRepository transactionRepository;

	@Test
	public void testLast60secTransactionsStatistic() {
		assertThat(statisticController.getStatistic().getBody() instanceof Statistic);
	}

}
