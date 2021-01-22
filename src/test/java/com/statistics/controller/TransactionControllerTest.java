package com.statistics.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.statistics.controller.TransactionController;
import com.statistics.domain.Transaction;

@SpringBootTest
public class TransactionControllerTest {

	@Autowired
	TransactionController transactionController;

	@Test
	public void testSaveTransaction() {
		assertThat(transactionController.saveTransaction(new Transaction()).getStatusCode() instanceof HttpStatus);
	}

	@Test
	public void testDeleteTransaction() {
		assertThat(transactionController.deleteTransactions().getStatusCode() instanceof HttpStatus);
	}

	@Test
	public void testGetAll() {
		assertThat(transactionController.getAll().getBody().stream().filter(t -> t instanceof Transaction));
	}

}
