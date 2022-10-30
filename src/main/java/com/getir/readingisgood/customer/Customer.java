package com.getir.readingisgood.customer;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
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
public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2247009649660330713L;

	@Id
	@SequenceGenerator(name="SEQUENCE", sequenceName="SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE")
	private Long id;

	@NotNull(message = "Customer name can not be null.")
	private String name;

	@NotNull(message = "Customer email can not be null.")
	@Email(message = "Customer email is not valid.")
	private String email;
	
}

