package com.statistics.service;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.statistics.domain.Transaction;

public interface TransactionService {

	public HttpStatus save(Transaction transaction);

	public void deleteAll();

	public List<Transaction> getAll();

}
