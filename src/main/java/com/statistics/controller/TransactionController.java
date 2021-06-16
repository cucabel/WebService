package com.statistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.statistics.domain.Transaction;
import com.statistics.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@PostMapping
	public ResponseEntity<HttpStatus> save(@RequestBody Transaction transaction) {
		return new ResponseEntity<>(transactionService.save(transaction));
	}

	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAll() {
		transactionService.deleteAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<List<Transaction>> getAll() {
		return new ResponseEntity<>(transactionService.getAll(), HttpStatus.OK);
	}

}
