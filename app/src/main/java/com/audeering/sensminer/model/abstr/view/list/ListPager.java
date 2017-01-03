package com.audeering.sensminer.model.abstr.view.list;

import com.audeering.sensminer.model.abstr.view.AbstrListDataProvider;

public class ListPager {

	public final AbstrListDataProvider<?, ?> dataProvider;

	public ListPager(AbstrListDataProvider<?, ?> cwxDataProvider) {

		this.dataProvider = cwxDataProvider;

	}

	public void goToStart() {
		dataProvider.setStartRow(0);
		dataProvider.fetchData();
	}

	public void goToEnd() {
		dataProvider.setStartRow(dataProvider.getTotalSize() - dataProvider.getPageSize());
		dataProvider.fetchData();
	}

	public void goToNextPage() {

		int newStartRow = dataProvider.getStartRow() + dataProvider.getPageSize();

		if (newStartRow >= dataProvider.getTotalSize()) {
			return;

		}
		dataProvider.setStartRow(newStartRow);

		dataProvider.fetchData();
	}

	public void goToPreviousPage() {

		int newStartRow = dataProvider.getStartRow() - dataProvider.getPageSize();

		if (newStartRow < 0) {
			newStartRow = 0;
		}

		dataProvider.setStartRow(newStartRow);

		dataProvider.fetchData();
	}

}
