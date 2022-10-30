package com.getir.readingisgood.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getir.readingisgood.order.OrderRepository;


@Service
public class StatisticsService {
	@Autowired
	OrderRepository orderRepository;

	public List<CustomerStatistics> findOrderStatistics(Long customerId) {
		return orderRepository.getCustomerStatistics(customerId);
	}
	
	
	
	
}
