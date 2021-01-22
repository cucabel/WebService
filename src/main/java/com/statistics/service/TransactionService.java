package com.statistics.service;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.statistics.domain.Transaction;

public interface TransactionService {

	public HttpStatus saveTransaction(Transaction transaction);

	public void deleteTransactions();

	public List<Transaction> getAll();

}
