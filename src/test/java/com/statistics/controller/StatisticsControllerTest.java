package com.statistics.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
	
	String currentTimestamp;
	Statistic statistic;
		
	@BeforeEach
	public void init() {
		
		MockitoAnnotations.initMocks(this);
		
		currentTimestamp = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now());
		
		List<String> amounts = new ArrayList<>();
		amounts.add("9000.00");
		statistic = new Statistic(amounts);
	}


	@Test
	public void testLast60secTransactionsStatistic() {
				
		when(statisticService.getStatistic()).thenReturn(statistic);
		
		ResponseEntity<Statistic> response = new ResponseEntity<>(statistic, HttpStatus.OK);
		assertThat(statisticController.getStatistic().equals(response));
	}

}
