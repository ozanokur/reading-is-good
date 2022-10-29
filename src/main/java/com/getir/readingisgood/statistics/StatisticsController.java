package com.getir.readingisgood.statistics;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistics")
public class StatisticsController {
	@Autowired
	StatisticsService statisticsService;
    
    @GetMapping("/{customerId}")
    public List<CustomerStatistics> findOrderStatistics(Long customerId) {
    	return statisticsService.findOrderStatistics(customerId);
    }
}
