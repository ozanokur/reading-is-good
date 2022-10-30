package com.getir.readingisgood.book;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
	
	@InjectMocks
	BookService bookService;

	@Mock
	BookRepository bookRepository;
	
	@Test
	void givenInsufficientStock_updateStock_throwException() {
		doReturn(Optional.ofNullable(getBookWithStockAndPrice(5L, BigDecimal.ONE)))
			.when(bookRepository).findById(anyLong());
		
		assertThrows(RuntimeException.class, () -> {
			bookService.updateStock(1L, -100L);
		});
	}
	
	@Test
	void givenValidConditions_updateStock_noException() {
		doReturn(Optional.ofNullable(getBookWithStockAndPrice(500L, BigDecimal.ONE)))
			.when(bookRepository).findById(anyLong());
		
		assertDoesNotThrow(() -> {
			bookService.updateStock(1L, -100L);
		});
	}

	private Book getBookWithStockAndPrice(Long count, BigDecimal price) {
		return new Book(1L, "book", count, price);
	}

}
