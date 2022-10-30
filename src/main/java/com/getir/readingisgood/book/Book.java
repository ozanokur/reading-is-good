package com.getir.readingisgood.book;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2247009649660330713L;


	@Id
	@SequenceGenerator(name="SEQUENCE", sequenceName="SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE")
	private Long id;
	
	@NotNull(message = "Book name can not be null.")
	private String name;

	@NotNull(message = "Book count can not be null.")
	@Min(value = 0, message = "Book count must be at least 0.")
	private Long count;

	@NotNull(message = "Book price can not be null.")
	@Min(value = 0, message = "Book price must be at least 0.")
	private BigDecimal price;
	
}

