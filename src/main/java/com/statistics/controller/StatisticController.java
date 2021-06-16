package com.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.statistics.domain.Statistic;
import com.statistics.service.StatisticService;

@RestController
@RequestMapping("/statistics")
public class StatisticController {

	@Autowired
	StatisticService statisticService;

	// returns the statistics computed on the transactions within the last 60 seconds
	@GetMapping
	public ResponseEntity<Statistic> calcStatLast60SecTrans() {
		return new ResponseEntity<>(statisticService.calcStatLast60SecTrans(), HttpStatus.OK);
	}

}
