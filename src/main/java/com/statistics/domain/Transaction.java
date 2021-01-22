package com.statistics.domain;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Transaction {

	private String amount;
	private String timestamp;

	public Transaction() {
	}

	public Transaction(String amount, String timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	// validations
	// if any of the fields, timeStamp and amount, are not parsed
	public boolean parse() {

		try {
			DateTimeFormatter.ISO_INSTANT.parse(this.getTimestamp());
		} catch (Exception e1) {
			return false;
		}

		if (!Pattern.compile("(\\+|-)?([0-9]+(\\.[0-9]+))").matcher(this.getAmount()).find())
			return false;
		else
			return true;
	}

	// if the transaction date is in the future
	public boolean future() {

		if (Instant.from(DateTimeFormatter.ISO_INSTANT.parse(this.getTimestamp()))
				.isAfter(Instant.from(DateTimeFormatter.ISO_INSTANT.parse(DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now())))))
			return true;
		else
			return false;
	}

	// if the transaction is older than 60 seconds
	public boolean older() {

		if (Instant.from(DateTimeFormatter.ISO_INSTANT.parse(this.getTimestamp()))
				.isBefore(Instant.from(DateTimeFormatter.ISO_INSTANT.parse(DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now())))
				.minusSeconds(60)))
			return true;
		else
			return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [amount=" + amount + ", timestamp=" + timestamp + "]";
	}

}
