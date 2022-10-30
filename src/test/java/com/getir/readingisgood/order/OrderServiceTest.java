package com.getir.readingisgood.order;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.getir.readingisgood.book.Book;
import com.getir.readingisgood.book.BookService;
import com.getir.readingisgood.customer.Customer;
import com.getir.readingisgood.customer.CustomerService;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
	
	@InjectMocks
	OrderService orderService;

	@Mock
	OrderRepository orderRepository;
	@Mock
	BookService bookService;
	@Mock
	CustomerService customerService;
	
	@Test
	void givenValidConditions_order_persist() {
		doReturn(new Book())
			.when(bookService).updateStock(any(), any());
		doReturn(new Customer())
			.when(customerService).findByCustomerId(any());
		orderService.order(new OrderRequest(1L, 2L, 5L));
		
		verify(orderRepository, times(1)).save(any());
	}
	
	@Test
	void givenValidId_findOrderById_noException() {
		doReturn(Optional.ofNullable(new Order()))
			.when(orderRepository).findById(anyLong());
		
		assertDoesNotThrow(() -> {
			orderService.findOrderById(1L);
		});
	}
	
	@Test
	void givenInValidId_findOrderById_throwException() {
		doReturn(Optional.ofNullable(null))
			.when(orderRepository).findById(anyLong());
		
		assertThrows(RuntimeException.class, () -> {
			orderService.findOrderById(1L);
		});
	}
	
	@Test
	void givenRequest_findOrdersByInterval_callRepo() {
		
		orderService.findOrdersByInterval(new Date(), new Date());
		
		verify(orderRepository, times(1)).findByDateGreaterThanEqualAndDateLessThanEqual(any(), any());
	}

}
