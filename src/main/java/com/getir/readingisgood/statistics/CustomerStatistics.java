package com.getir.readingisgood.statistics;

import java.math.BigDecimal;


public interface CustomerStatistics {
	String getMonth();
	Integer getOrderCount();
	Integer getBookCount();
	BigDecimal getPurchasedAmount();
}
