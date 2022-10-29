package com.getir.readingisgood.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2247009649660330713L;

	@Id
    @SequenceGenerator(name="SEQUENCE", sequenceName="SEQUENCE", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE")
    private Long id;
	
	private Long count;
    
    private Long customerId;

    private Long bookId;

	@JsonFormat(pattern="dd/MM/yyyy ")
    private Date date;
    
    @PrePersist
    public void prePersist() {
    	date = new Date();
    }
    
}

