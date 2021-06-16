package com.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.statistics.domain.Transaction;
import com.statistics.repository.TransactionRepository;
import com.statistics.service.TransactionService;

@Service(value = "transactionServer")
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public HttpStatus save(Transaction transaction) {

		// if the JSON is invalid
		if (transaction.getAmount() == null || transaction.getTimestamp() == null)
			return HttpStatus.BAD_REQUEST;
		
		// if any of the fields are not parsed
		if (transaction.validateIsParsed() == false)
			return HttpStatus.UNPROCESSABLE_ENTITY;
		
		// if the transaction date is in the future
		if (transaction.validateIsFuture() == true)
			return HttpStatus.UNPROCESSABLE_ENTITY;
		
		// if the transaction is older than 60 seconds
		if (transaction.validateIsOlder() == true)
			return HttpStatus.NO_CONTENT;

		// in case of success
		transactionRepository.save(transaction);
		return HttpStatus.CREATED;
	}

	@Override
	public void deleteAll() {
		transactionRepository.deleteAll();
	}

	@Override
	public List<Transaction> getAll() {
		return transactionRepository.getAll();
	}

}
