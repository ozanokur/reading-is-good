package com.getir.readingisgood.common.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging {
	boolean paged = true;
	int pageNumber = 0;
	int pageSize = 20;
}
