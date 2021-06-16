package com.statistics.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.statistics.domain.Statistic;
import com.statistics.service.StatisticService;

@SpringBootTest
public class StatisticsControllerTest {

	@InjectMocks
	StatisticController statisticController;
	@Mock
	StatisticService statisticService;
		
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testLast60secTransactionsStatistic() {
		Statistic statistic = new Statistic(Arrays.asList("9000.00"));
		ResponseEntity<Statistic> response = new ResponseEntity<>(statistic, HttpStatus.OK);

		when(statisticService.getStatistic()).thenReturn(statistic);
		assertThat(statisticController.getStatistic().equals(response));
	}

}
