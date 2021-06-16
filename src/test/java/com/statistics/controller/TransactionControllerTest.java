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
	
	Transaction trans0, trans1, trans2, trans3, trans4;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		String currentTimestamp = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now());
		
		trans0 = new Transaction(null, currentTimestamp);
		trans1 = new Transaction("9000.00", DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().plusSeconds(120)));
		trans2 = new Transaction("9000.00", DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().minusSeconds(120)));
		trans3 = new Transaction("9000.00", currentTimestamp);
	}

	@Test
	public void testSaveTransaction() {
		
		when(transactionService.save(ArgumentMatchers.any(Transaction.class))).thenReturn(HttpStatus.BAD_REQUEST, HttpStatus.UNPROCESSABLE_ENTITY, 
				HttpStatus.NO_CONTENT, HttpStatus.CREATED);
		
		assertEquals(HttpStatus.BAD_REQUEST, transactionController.save(trans0).getStatusCode());
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, transactionController.save(trans1).getStatusCode());
		assertEquals(HttpStatus.NO_CONTENT, transactionController.save(trans2).getStatusCode());
		assertEquals(HttpStatus.CREATED, transactionController.save(trans3).getStatusCode());
	}

	@Test
	public void testDeleteTransaction() {
		ResponseEntity<HttpStatus> response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		doNothing().when(transactionService).deleteAll();
		assertThat(transactionController.deleteAll().equals(response));
	}

	@Test
	public void testGetAll() {
		ResponseEntity<List<Transaction>> response = new ResponseEntity<>(Arrays.asList(trans3), HttpStatus.OK);
				
		when(transactionService.getAll()).thenReturn(Arrays.asList(trans3));
		assertThat(transactionController.getAll().equals(response));
	}

}
