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

		Statistic s = new Statistic();
		s.statistics(Arrays.asList("-5000.00", "400.00", "6000.00"));

		assertThat(s.getSum()).isEqualByComparingTo("1400.00");
		assertThat(s.getAvg()).isEqualByComparingTo("466.67");
		assertThat(s.getMax()).isEqualByComparingTo("6000.00");
		assertThat(s.getMin()).isEqualByComparingTo("-5000.00");
		assertEquals(3, s.getCount());

		// if there weren't transactions on the last 60 seconds
		s.statistics(new ArrayList<String>());

		assertThat(s.getSum()).isEqualByComparingTo("0.00");
		assertThat(s.getAvg()).isEqualByComparingTo("0.00");
		assertThat(s.getMax()).isEqualByComparingTo("0.00");
		assertThat(s.getMin()).isEqualByComparingTo("0.00");
		assertEquals(0, s.getCount());

	}

}
