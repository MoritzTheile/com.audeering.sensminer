package com.audeering.sensminer.model.abstr.view;


import com.audeering.sensminer.model.abstr.AbstrDTO;
import com.audeering.sensminer.model.abstr.CRUDService;
import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstrListDataProvider<T extends AbstrDTO, F extends FetchQuery>  extends AbstrObservable {

	private F fetchQuery;
	private String fetchQueryFingerprint = "";
	private int startRow = 0;
	private int pageSize = Integer.MAX_VALUE;
	private int totalSize = -1;
	private List<T> data = new ArrayList<T>();

	public AbstrListDataProvider(F fetchQuery) {
		this.fetchQuery = fetchQuery;
	}

	public void fetchData() {

		if (!fetchQueryFingerprint.equals(fetchQuery.getFingerprint())) {
			fetchQueryFingerprint = fetchQuery.getFingerprint();
			startRow = 0;
		}

		DTOFetchList<T> result = getAbstractCRUDService().fetchList(new Page(startRow, pageSize), fetchQuery);

		totalSize = result.getTotalSize();

		hasChanged();

	}

	public F getFetchQuery() {
		return fetchQuery;
	}

	public void setFetchQuery(F query) {
		this.fetchQuery = query;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public List<T> getData() {
		return data;
	}

	public abstract CRUDService<T, F> getAbstractCRUDService();

}
