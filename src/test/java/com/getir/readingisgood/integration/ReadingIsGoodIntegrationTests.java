package com.getir.readingisgood.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.book.Book;
import com.getir.readingisgood.common.security.SecurityConstants;
import com.getir.readingisgood.customer.Customer;
import com.getir.readingisgood.customer.CustomerRepository;
import com.getir.readingisgood.user.AppUser;
import com.getir.readingisgood.user.UserResponse;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReadingIsGoodIntegrationTests {


	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CustomerRepository customerRepository;

	private String getAuthorizationHeader() throws Exception {
		AppUser user = new AppUser(null, "admin", "pass", null);
		
		MvcResult result = mockMvc.perform(post("/users/login")
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(user)))
		        .andExpect(status().isOk()).andReturn();
	
		UserResponse userResponse = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);

		return userResponse.getAuthorizationToken();
	}

	@Test
	void givenValidConditions_addCustomer_persistToDatabase() throws Exception {
		String authorizationHeader = getAuthorizationHeader();
		
		Customer customer = new Customer(null, "test", "test1@gmail.com");
	
		mockMvc.perform(post("/customers/")
				.header(SecurityConstants.HEADER_AUTHORIZATION, authorizationHeader)
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(customer)))
		        .andExpect(status().isOk()).andReturn();
	
		assertTrue(customerRepository.existsByEmail("test1@gmail.com"));
	}

	@Test
	void givenExistingEmail_addCustomer_throwException() throws Exception {
		String authorizationHeader = getAuthorizationHeader();
		
		customerRepository.save(new Customer(null, "test2", "test2@gmail.com"));
		
		Customer customer = new Customer(null, "test2", "test2@gmail.com");
	
		mockMvc.perform(post("/customers/")
				.header(SecurityConstants.HEADER_AUTHORIZATION, authorizationHeader)
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(customer)))
		        .andExpect(status().isInternalServerError()).andReturn();
	}

	@Test
	void givenNonExistingCustomer_getOrdersOfCustomer_throwException() throws Exception {
		String authorizationHeader = getAuthorizationHeader();
	
		mockMvc.perform(get("/customers/{customerId}/orders", 1)
				.header(SecurityConstants.HEADER_AUTHORIZATION, authorizationHeader)
		        .contentType("application/json"))
		        .andExpect(status().isInternalServerError()).andReturn();
	}

	@Test
	void givenExistingCustomer_getOrdersOfCustomer_noException() throws Exception {
		String authorizationHeader = getAuthorizationHeader();

		Customer customer = customerRepository.save(new Customer(null, "test2", "test3@gmail.com"));
		
		mockMvc.perform(get("/customers/{customerId}/orders", customer.getId())
				.header(SecurityConstants.HEADER_AUTHORIZATION, authorizationHeader)
		        .contentType("application/json"))
		        .andExpect(status().isOk()).andReturn();
	}

	@Test
	void givenValidBook_addBook_noException() throws Exception {
		String authorizationHeader = getAuthorizationHeader();

		Book book = new Book(0L, "hayri", 3L, BigDecimal.valueOf(5));
		
		mockMvc.perform(post("/books/")
				.header(SecurityConstants.HEADER_AUTHORIZATION, authorizationHeader)
		        .content(objectMapper.writeValueAsString(book))
		        .contentType("application/json"))
		        .andExpect(status().isOk()).andReturn();
	}

	@Test
	void givenInValidBook_addBook_throwException() throws Exception {
		String authorizationHeader = getAuthorizationHeader();

		Book book = new Book(0L, null, 3L, BigDecimal.valueOf(5));
		
		mockMvc.perform(post("/books/")
				.header(SecurityConstants.HEADER_AUTHORIZATION, authorizationHeader)
		        .content(objectMapper.writeValueAsString(book))
		        .contentType("application/json"))
		        .andExpect(status().isBadRequest()).andReturn();
	}

}
