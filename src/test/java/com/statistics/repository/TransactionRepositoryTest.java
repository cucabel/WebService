package com.statistics.repository;

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
		Transaction trans = new Transaction("-5000.00", currentTimestamp);
		transactionRepository.save(trans);
		assertThat(transactionRepository.getAll()).contains(trans);
	}

	@Test
	public void testGetAll() {
		Transaction trans = new Transaction("-5000.00", currentTimestamp);
		Transaction trans1 = new Transaction("400.00", currentTimestamp);
		transactionRepository.save(trans);
		transactionRepository.save(trans1);

		assertEquals(Arrays.asList(trans, trans1), transactionRepository.getAll());
	}

	@Test
	public void testDelete() {
		transactionRepository.deleteAll();
		assertThat(transactionRepository.getAll()).isEmpty();
	}

}
