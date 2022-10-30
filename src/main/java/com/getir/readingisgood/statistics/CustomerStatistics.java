package com.getir.readingisgood.statistics;

import java.math.BigDecimal;


public interface CustomerStatistics {
	String getIntervalMonth();
	Integer getOrderCount();
	Integer getBookCount();
	BigDecimal getPurchasedAmount();
}
