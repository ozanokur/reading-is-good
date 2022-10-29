package com.getir.readingisgood.order;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.getir.readingisgood.book.Book;
import com.getir.readingisgood.book.BookService;
import com.getir.readingisgood.customer.Customer;
import com.getir.readingisgood.customer.CustomerService;

@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	BookService bookService;
	@Autowired
	CustomerService customerService;
    
    public Order save(@RequestBody Order customer) {
    	return orderRepository.save(customer);
    }

	public Page<Order> findAllOrdersOfCustomer(Long customerId, Pageable pageable) {
		return orderRepository.findByCustomerId(customerId, pageable);
	}

	@Transactional
	public Order order(OrderRequest orderRequest) {
		Customer customer = customerService.findByCustomerId(orderRequest.getCustomerId());
		Book book = bookService.updateStock(orderRequest.getBookId(), -orderRequest.getCount());
		
		return persistOrder(orderRequest, customer, book);
	}

	private Order persistOrder(OrderRequest orderRequest, Customer customer, Book book) {
		Order order = new Order();
		order.setBookId(book.getId());
		order.setCustomerId(customer.getId());
		order.setCount(orderRequest.getCount());
		
		return orderRepository.save(order);
	}

	public Order findOrderById(Long orderId) {
		return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("No order with id:" + orderId + " found."));
	}

	public List<Order> findOrdersByInterval(Date startDate, Date endDate) {
		return orderRepository.findByDateGreaterThanEqualAndDateLessThanEqual(startDate, endDate);
	}
    
}
