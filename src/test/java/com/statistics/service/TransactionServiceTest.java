package com.statistics.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.statistics.domain.Transaction;
import com.statistics.service.TransactionService;

@SpringBootTest
public class TransactionServiceTest {

	@Autowired
	TransactionService transactionService;

	String currentTimestamp;

	@BeforeEach
	public void variables() {
		currentTimestamp = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now());
	}

	@Test
	public void testSaveTransaction() {
		// if the JSON is invalid
		Transaction t = new Transaction(null, currentTimestamp);
		assertEquals(HttpStatus.BAD_REQUEST, transactionService.saveTransaction(t));

		t.setAmount("9000.00");
		t.setTimestamp(null);
		assertEquals(HttpStatus.BAD_REQUEST, transactionService.saveTransaction(t));

		// if amount is not parsed
		t.setAmount("9000");
		t.setTimestamp(currentTimestamp);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, transactionService.saveTransaction(t));

		// if timeStamp is not parsed
		t.setAmount("9000.00");
		t.setTimestamp("2020-10-12T");
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, transactionService.saveTransaction(t));

		// if the transaction date is in the future
		t.setTimestamp(DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().plusSeconds(120)));
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, transactionService.saveTransaction(t));

		// if the transaction is older than 60 seconds
		t.setTimestamp(DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().minusSeconds(120)));
		assertEquals(HttpStatus.NO_CONTENT, transactionService.saveTransaction(t));

		// in case of success
		t.setTimestamp(currentTimestamp);
		assertEquals(HttpStatus.CREATED, transactionService.saveTransaction(t));
		assertThat(transactionService.getAll().contains(t));
	}

	@Test
	public void testGetAll() {
		Transaction t = new Transaction("1000.00", currentTimestamp);
		Transaction t1 = new Transaction("-50.00", currentTimestamp);
		transactionService.saveTransaction(t);
		transactionService.saveTransaction(t1);
		assertEquals(Arrays.asList(t, t1), transactionService.getAll());
	}

	@Test
	public void testDeleteTransaction() {
		transactionService.deleteTransactions();
		assertThat(transactionService.getAll()).isEmpty();
	}

}
