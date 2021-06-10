package com.statistics.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
		
	@BeforeEach
	public void init() {
		
		MockitoAnnotations.initMocks(this);
		
		currentTimestamp = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now());	
	}


	@Test
	public void testLast60secTransactionsStatistic() {
		
		Statistic s = new Statistic();
		s.setSum(new BigDecimal("1400.00"));
		s.setAvg(new BigDecimal("466.67"));
		s.setMax(new BigDecimal("6000.00"));
		s.setMin(new BigDecimal("-5000.00"));
		s.setCount((long) 3);
		
		when(statisticService.getStatistic()).thenReturn(s);
		
		ResponseEntity<Statistic> res0 = new ResponseEntity<>(s, HttpStatus.OK);
		assertThat(statisticController.getStatistic().equals(res0));
	}

}
