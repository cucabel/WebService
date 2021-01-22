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
		Transaction t = new Transaction("9000", currentTimestamp);
		assertEquals(false, t.parse());

		// if timeStamp is not parsed
		t.setAmount("9000.00");
		t.setTimestamp("2020-10-12T");
		assertEquals(false, t.parse());
		
		// if both are parsed
		t.setAmount("9000.00");
		t.setTimestamp(currentTimestamp);
		assertEquals(true, t.parse());
	}

	@Test
	public void testFuture() {
		Transaction t = new Transaction("9000.00", DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().plusSeconds(120)));
		assertEquals(true, t.future());
		
		t.setTimestamp(currentTimestamp);
		assertEquals(false, t.future());
	}

	@Test
	public void testOlder() {
		Transaction t = new Transaction("9000.00", DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().minusSeconds(120)));
		assertEquals(true, t.older());
		
		t.setTimestamp(currentTimestamp);
		assertEquals(false, t.older());
	}

}
