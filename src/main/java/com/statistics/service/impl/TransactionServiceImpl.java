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
	public HttpStatus saveTransaction(Transaction t) {

		// if the JSON is invalid
		if (t.getAmount() == null || t.getTimestamp() == null)
			return HttpStatus.BAD_REQUEST;
		
		// if any of the fields are not parsed
		if (t.parse() == false)
			return HttpStatus.UNPROCESSABLE_ENTITY;
		
		// if the transaction date is in the future
		if (t.future() == true)
			return HttpStatus.UNPROCESSABLE_ENTITY;
		
		// if the transaction is older than 60 seconds
		if (t.older() == true)
			return HttpStatus.NO_CONTENT;

		// in case of success
		transactionRepository.save(t);
		return HttpStatus.CREATED;
	}

	@Override
	public void deleteTransactions() {
		transactionRepository.deleteAll();
	}

	@Override
	public List<Transaction> getAll() {
		return transactionRepository.getAll();
	}

}
