package com.getir.readingisgood.customer;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getir.readingisgood.common.paging.Paging;
import com.getir.readingisgood.common.paging.PagingUtils;
import com.getir.readingisgood.order.Order;
import com.getir.readingisgood.order.OrderService;

@RestController
@RequestMapping("customers")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	@Autowired
	OrderService orderService;
    
    @GetMapping("/{customerId}/orders")
    public Page<Order> findAllOrders(@PathVariable Long customerId, Paging paging) {
    	return orderService.findAllOrdersOfCustomer(customerId, PagingUtils.getPageable(paging));
    }
    
    @PostMapping("/")
    public Customer addCustomer(@RequestBody @Valid Customer customer) {
    	return customerService.save(customer);
    }
}
