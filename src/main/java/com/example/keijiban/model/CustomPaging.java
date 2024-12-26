package com.example.keijiban.model;

import lombok.Data;

@Data
public class CustomPaging {
	private Integer page = 1;
	private Integer pageSize;
	private Integer totalPages;
	private Integer dataCount;

	public CustomPaging(Integer pageSize, Integer page) {
		setPage(page);
		setPageSize(pageSize);
		setTotalPages(1);
	}

	public Integer getLimit() {
		return pageSize;
	}

	public Integer getOffset() {
		return pageSize * (page - 1);
	}

	public Integer getPreviousPage() {
		if (page - 1 <= 0) {
			return null;
		}
		return page - 1;
	}

	public Integer getNextPage() {
		if (page + 1 > totalPages) {
			return null;
		}
		return page + 1;
	}

	public void setPage(Integer page) {
		this.page = Math.max(page, 1);
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = Math.max(pageSize, 1);
		if (dataCount != null) {
			setTotalPages((int) Math.ceil(1.0 * dataCount / pageSize));
		}
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = Math.max(totalPages, 1);
	}

	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
		setTotalPages((int) Math.ceil(1.0 * dataCount / pageSize));
	}

	public void nextPage() {
		++page;
	}

	public void previousPage() {
		--page;
	}
}
