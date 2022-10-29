package com.getir.readingisgood.order;


import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@PostMapping("/")
	public Order addOrder(@RequestBody @Valid @NotNull OrderRequest orderRequest) {
		return orderService.order(orderRequest);
	}
	
	@GetMapping("/{orderId}")
	public Order findOrderById(@PathVariable Long orderId) {
		return orderService.findOrderById(orderId);
	}
	
	@GetMapping("/")
	public List<Order> findOrdersByInterval(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
		return orderService.findOrdersByInterval(startDate, endDate);
	}
}
