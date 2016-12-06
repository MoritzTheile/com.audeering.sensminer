package com.audeering.sensminer.model.abstr;

import java.io.Serializable;

public class FetchQuery implements Serializable {

	private static final long serialVersionUID = 3786879634538137946L;

	public static enum SORTDIRECTION {
		ASC, DESC, NONE
	}

	private SORTDIRECTION sortDirection = SORTDIRECTION.NONE;

	private String searchString;

	private String orderByColumn;

	public String getOrderByColumn() {
		return orderByColumn;
	}

	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public SORTDIRECTION getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(SORTDIRECTION sortDirection) {
		this.sortDirection = sortDirection;
	}

}
