package com.statistics.service.impl;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.statistics.domain.Statistic;
import com.statistics.repository.TransactionRepository;
import com.statistics.service.StatisticService;

@Service(value = "statisticService")
public class StatisticServiceImpl implements StatisticService {

	@Autowired
	TransactionRepository transactionsRepository;

	@Override
	public Statistic calcStatLast60SecTrans() {

		// transactions made in the last 60 seconds
		List<String> amounts = transactionsRepository.getAll()
				.stream()
				.filter(t -> Instant.from(DateTimeFormatter.ISO_INSTANT.parse(t.getTimestamp()))
						.isAfter(Instant.from(DateTimeFormatter.ISO_INSTANT.parse(DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now()
						.minusSeconds(60))))))
				.map(t -> t.getAmount())
				.collect(Collectors.toList());

		return new Statistic(amounts);
	}

}
