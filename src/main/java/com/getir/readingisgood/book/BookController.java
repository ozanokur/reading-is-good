package com.getir.readingisgood.book;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@PostMapping("/")
	public Book addBook(@RequestBody @Valid Book book) {
		return bookService.save(book);
	}
	
	@PatchMapping("/{bookId}")
	public Book updateStock(@PathVariable Long bookId, @Valid @RequestBody BookStockUpdateRequest request) {
		return bookService.updateStock(bookId, request.getCount());
	}
}
