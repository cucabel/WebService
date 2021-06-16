package com.statistics.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionTest {
	
	String currentTimestamp;

	@BeforeEach
	public void variables() {
		currentTimestamp = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now());
	}

	@Test
	public void testParse() {
		// if amount is not parsed
		Transaction trans = new Transaction("9000", currentTimestamp);
		assertEquals(false, trans.validateIsParsed());

		// if timeStamp is not parsed
		trans.setAmount("9000.00");
		trans.setTimestamp("2020-10-12T");
		assertEquals(false, trans.validateIsParsed());
		
		// if both are parsed
		trans.setAmount("9000.00");
		trans.setTimestamp(currentTimestamp);
		assertEquals(true, trans.validateIsParsed());
	}

	@Test
	public void testFuture() {
		Transaction trans = new Transaction("9000.00", DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().plusSeconds(120)));
		assertEquals(true, trans.validateIsFuture());
		
		trans.setTimestamp(currentTimestamp);
		assertEquals(false, trans.validateIsFuture());
	}

	@Test
	public void testOlder() {
		Transaction trans = new Transaction("9000.00", DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().minusSeconds(120)));
		assertEquals(true, trans.validateIsOlder());
		
		trans.setTimestamp(currentTimestamp);
		assertEquals(false, trans.validateIsOlder());
	}

}
