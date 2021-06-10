package com.statistics.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.statistics.controller.TransactionController;
import com.statistics.domain.Transaction;
import com.statistics.service.TransactionService;

@SpringBootTest
public class TransactionControllerTest {

	@InjectMocks
	TransactionController transactionController;
	
	@Mock
	TransactionService transactionService;
	
	String currentTimestamp;
	
	Transaction t0;
	Transaction t1;
	Transaction t2;
	Transaction t3;
	Transaction t4;
	
	@BeforeEach
	public void init() {
		
		MockitoAnnotations.initMocks(this);
		
		currentTimestamp = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now());

		t0 = new Transaction(null, currentTimestamp);
		t1 = new Transaction("9000.00", DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().plusSeconds(120)));
		t2 = new Transaction("9000.00", DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().minusSeconds(120)));
		t3 = new Transaction("9000.00", currentTimestamp);
	}

	@Test
	public void testSaveTransaction() {
		
		when(transactionService.saveTransaction(ArgumentMatchers.any(Transaction.class))).thenReturn(HttpStatus.BAD_REQUEST, HttpStatus.UNPROCESSABLE_ENTITY, 
			HttpStatus.NO_CONTENT, HttpStatus.CREATED);
		
		ResponseEntity<HttpStatus> res0 = transactionController.saveTransaction(t0);
		ResponseEntity<HttpStatus> res1 = transactionController.saveTransaction(t1);
		ResponseEntity<HttpStatus> res2 = transactionController.saveTransaction(t2);
		ResponseEntity<HttpStatus> res3 = transactionController.saveTransaction(t3);
		
		assertEquals(HttpStatus.BAD_REQUEST, res0.getStatusCode());
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, res1.getStatusCode());
		assertEquals(HttpStatus.NO_CONTENT, res2.getStatusCode());
		assertEquals(HttpStatus.CREATED, res3.getStatusCode());
	}

	@Test
	public void testDeleteTransaction() {
		
		doNothing().when(transactionService).deleteTransactions();
		
		ResponseEntity<HttpStatus> res0 = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		assertThat(transactionController.deleteTransactions().equals(res0));
	}

	@Test
	public void testGetAll() {
				
		when(transactionService.getAll()).thenReturn(Arrays.asList(t3));

		ResponseEntity<List<Transaction>> res0 = new ResponseEntity<>(Arrays.asList(t3), HttpStatus.OK);
		assertThat(transactionController.getAll().equals(res0));
	}

}
