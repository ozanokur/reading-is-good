package com.getir.readingisgood.book;

import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
	public Optional<Book> findById(Long id);
}
