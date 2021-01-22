package com.statistics.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.statistics.domain.Transaction;

@Repository
public class TransactionRepository {

	private static List<Transaction> transactions = new ArrayList<>();

	public TransactionRepository() {
	}

	public void save(Transaction transaction) {
		transactions.add(transaction);
	}

	public List<Transaction> getAll() {
		return transactions;
	}

	public void deleteAll() {
		transactions.clear();
	}

}
