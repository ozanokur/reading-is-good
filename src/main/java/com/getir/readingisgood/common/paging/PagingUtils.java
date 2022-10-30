package com.getir.readingisgood.common.paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PagingUtils {

	public static Pageable getPageable(Paging paging) {
		if (paging.isPaged()) {
			return PageRequest.of(paging.getPageNumber(), paging.getPageSize());
		}
		else {
			return Pageable.unpaged();
		}
	}

}
