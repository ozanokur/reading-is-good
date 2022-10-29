package com.getir.readingisgood.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

	@NotNull(message = "Book id can not be null.")
	private Long bookId;
	@NotNull(message = "Customer id can not be null.")
	private Long customerId;
	@NotNull(message = "Order count can not be null.")
	@Min(value = 1, message = "Order count must be at least 1.")
	private Long count;
}
