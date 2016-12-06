package com.audeering.sensminer.model.abstr;

import java.io.Serializable;

public class Page implements Serializable {

	private static final long serialVersionUID = -3401348366239519203L;

	private int startIndex = 0;

	private int pageSize = Integer.MAX_VALUE;

	public Page() {
	}

	public Page(int startIndex, int pageSize) {
		setStartIndex(startIndex);
		setPageSize(pageSize);
	}

	public int getStartIndex() {
		return startIndex;
	}

	private void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	private void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
