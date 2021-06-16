package com.statistics.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

import com.statistics.domain.Transaction;
import com.statistics.repository.TransactionRepository;
import com.statistics.service.impl.TransactionServiceImpl;

@SpringBootTest
public class TransactionServiceTest {

	@InjectMocks
	TransactionServiceImpl transactionServiceImpl;
	@Mock
	TransactionRepository transactionRepository;

	String currentTimestamp;

	@BeforeEach
	public void variables() {
		MockitoAnnotations.initMocks(this);

		currentTimestamp = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now());
	}

	@Test
	public void testSaveTransaction() {				
		// if the JSON is invalid
		Transaction trans0 = new Transaction(null, currentTimestamp);
		Transaction trans1 = new Transaction("9000.00", null);
		assertEquals(HttpStatus.BAD_REQUEST, transactionServiceImpl.save(trans0));
		assertEquals(HttpStatus.BAD_REQUEST, transactionServiceImpl.save(trans1));

		// if amount is not parsed
		Transaction trans2 = new Transaction("9000", currentTimestamp);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, transactionServiceImpl.save(trans2));

		// if timeStamp is not parsed
		Transaction trans3 = new Transaction("9000.00", "2020-10-12T");
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, transactionServiceImpl.save(trans3));

		// if the transaction date is in the future
		String timeStamp0 = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().plusSeconds(120));
		Transaction trans4 = new Transaction("9000.00", timeStamp0);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, transactionServiceImpl.save(trans4));

		// if the transaction is older than 60 seconds
		String timeStamp1 = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().minusSeconds(120));
		Transaction trans5 = new Transaction("9000.00", timeStamp1);
		assertEquals(HttpStatus.NO_CONTENT, transactionServiceImpl.save(trans5));

		// in case of success
		Transaction trans6 = new Transaction("9000.00", currentTimestamp);
		doNothing().when(transactionRepository).save(ArgumentMatchers.any(Transaction.class));
		assertEquals(HttpStatus.CREATED, transactionServiceImpl.save(trans6));
	}

	@Test
	public void testGetAll() {		
		Transaction trans0 = new Transaction("1000.00", currentTimestamp);
		Transaction trans1 = new Transaction("-50.00", currentTimestamp);
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(trans0);
		transactions.add(trans1);
		
		when(transactionRepository.getAll()).thenReturn(transactions);	
		assertEquals(Arrays.asList(trans0, trans1), transactionServiceImpl.getAll());
	}

}
