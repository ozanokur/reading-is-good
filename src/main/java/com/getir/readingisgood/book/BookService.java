package com.getir.readingisgood.book;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
	@Autowired
	BookRepository bookRepository;
    
    public Book save(Book book) {
    	return bookRepository.save(book);
    }
    
    @Transactional
    public Book updateStock(Long id, Long count) {
    	Book bookToBeUpdated = findById(id);
    	
    	validateUpdateRequest(bookToBeUpdated, count);
    	
    	return persistUpdateRequest(bookToBeUpdated, count);
    }

	private void validateUpdateRequest(Book bookToBeUpdated, Long count) {
		if (bookToBeUpdated.getCount() + count < 0) {
			throw new RuntimeException("Not enough books in stock.");
		}
	}

    private Book persistUpdateRequest(Book bookToBeUpdated, Long count) {
    	bookToBeUpdated.setCount(bookToBeUpdated.getCount() + count);
    	return bookRepository.save(bookToBeUpdated);
	}

	public Book findById(Long id) {
    	return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("No book with id:" + id + " found."));
    }
    
}
