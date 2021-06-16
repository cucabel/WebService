package com.statistics.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatisticsTest {

	@Test
	public void testStatistics() {

		Statistic stat = new Statistic();
		stat.calculateStatistics(Arrays.asList("-5000.00", "400.00", "6000.00"));

		assertThat(stat.getSum()).isEqualByComparingTo("1400.00");
		assertThat(stat.getAvg()).isEqualByComparingTo("466.67");
		assertThat(stat.getMax()).isEqualByComparingTo("6000.00");
		assertThat(stat.getMin()).isEqualByComparingTo("-5000.00");
		assertEquals(3, stat.getCount());

		// if there weren't transactions on the last 60 seconds
		stat.calculateStatistics(new ArrayList<String>());

		assertThat(stat.getSum()).isEqualByComparingTo("0.00");
		assertThat(stat.getAvg()).isEqualByComparingTo("0.00");
		assertThat(stat.getMax()).isEqualByComparingTo("0.00");
		assertThat(stat.getMin()).isEqualByComparingTo("0.00");
		assertEquals(0, stat.getCount());

	}

}
