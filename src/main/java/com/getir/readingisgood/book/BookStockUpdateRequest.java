package com.getir.readingisgood.book;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookStockUpdateRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5589037548558975442L;

	@NotNull(message = "Book update count can not be null.")
	private Long count;
	
}

