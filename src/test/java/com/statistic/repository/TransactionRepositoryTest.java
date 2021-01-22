package com.statistic.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.statistics.domain.Transaction;
import com.statistics.repository.TransactionRepository;

@SpringBootTest(classes = TransactionRepository.class)
public class TransactionRepositoryTest {

	@Autowired
	TransactionRepository transactionRepository;

	String currentTimestamp;

	@BeforeEach
	public void variables() {
		currentTimestamp = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now());
	}

	@Test
	public void testSave() {
		Transaction t = new Transaction("-5000.00", currentTimestamp);
		transactionRepository.save(t);
		assertThat(transactionRepository.getAll()).contains(t);
	}

	@Test
	public void testGetAll() {
		Transaction t = new Transaction("-5000.00", currentTimestamp);
		Transaction t1 = new Transaction("400.00", currentTimestamp);
		transactionRepository.save(t);
		transactionRepository.save(t1);

		assertEquals(Arrays.asList(t, t1), transactionRepository.getAll());
	}

	@Test
	public void testDelete() {
		transactionRepository.deleteAll();
		assertThat(transactionRepository.getAll()).isEmpty();
	}

}
