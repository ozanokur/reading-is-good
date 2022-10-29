package com.getir.readingisgood.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.getir.readingisgood.statistics.CustomerStatistics;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

	Page<Order> findByCustomerId(Long customerId, Pageable pageable);

	List<Order> findByDateGreaterThanEqualAndDateLessThanEqual(Date startDate, Date endDate);
	
	@Query(value = "select "
			+ "to_char(DATE_TRUNC('month',date), 'Mon YYYY') as month, count(*) as orderCount, sum(book_order.count) as bookCount, sum(book.price) as purchasedAmount "
			+ "from book_order "
			+ "join book "
			+ "on book_order.book_id = book.id "
			+ "where customer_id = :customerId "
			+ "group by DATE_TRUNC('month',date)", nativeQuery = true)
	List<CustomerStatistics> getCustomerStatistics(Long customerId);
}
