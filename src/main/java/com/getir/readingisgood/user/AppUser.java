package com.getir.readingisgood.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2247009649660330713L;

	@Id
	@SequenceGenerator(name="SEQUENCE", sequenceName="SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE")
	private Long id;

	private String username;

	private String password;

	// Separate table
	private String roles;
}

