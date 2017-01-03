package com.audeering.sensminer.model.abstr.view.list;

import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.audeering.sensminer.model.abstr.AbstrDTO;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.view.AbstrListDataProvider;
import com.audeering.sensminer.model.abstr.view.AbstrObservable;

public abstract class AbstrListViewList<T extends AbstrDTO, F extends FetchQuery> {

	private final AbstrListViewList<?, F> thisView;

	private AbstrListDataProvider<T, F> dataProvider;

	private final ListSelectionModel<T> selectionModel;

	private final Map<T, View> data2view = new HashMap<T, View>();

	public enum SELECTIONMODE {
		MULTISELECT, RANGESELECT, SINGLESELECT, DISABLED
	}

	/*
	 * The SELECTIONMODE is saved to give Observers the possibility to decide weather to take action on selection.
	 */
	private SELECTIONMODE selectionmode = SELECTIONMODE.SINGLESELECT;

	public AbstrListViewList(AbstrListDataProvider<T, F> dataProvider) {

		thisView = this;

		this.dataProvider = dataProvider;

		selectionModel = new ListSelectionModel<T>();

		selectionModel.addListener(new ListSelectionModel.SelectionListener<T>() {

			@Override
			public void onHasChanged(List<T> selection) {

				render();

			}

		});

	}

	private void render() {

		String selectedStyle = "selected";

		for (final T data : data2view.keySet()) {

			View view = data2view.get(data);

			if (selectionModel.isSelected(data)) {
				//view.addStyleName(selectedStyle);
			} else {
				//view.removeStyleName(selectedStyle);
			}
		}

	}

	public ListSelectionModel<T> getSelectionModel() {

		return selectionModel;

	}

	public abstract View getView(T data);

	private AbstrObservable.Observer<Void> observer = new AbstrObservable.Observer<Void>() {

		@Override
		public void modelHasChanged(Void noll) {

			data2view.clear();


			for (final T data : dataProvider.getData()) {

				View view = getView(data);

				// without this style, text gets selected on holding Shift while clicking mouse
				//view.addStyleName("unselectable");
				view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						if (SELECTIONMODE.DISABLED.equals(getSelectionmode())) {
							return;
						}

						if (SELECTIONMODE.MULTISELECT.equals(getSelectionmode())) {

							if (selectionModel.isSelected(data)) {
								selectionModel.unselect(data);
							} else {
								selectionModel.select(data);
							}

						} else if (SELECTIONMODE.RANGESELECT.equals(getSelectionmode())) {

							selectionModel.selectSilently(data);
							selectionModel.selectRange(dataProvider.getData());

						} else {

							selectionModel.selectOnly(data);

						}

					}
				});

				data2view.put(data, view);

			}

			render();

		}

	};


	public SELECTIONMODE getSelectionmode() {
		return selectionmode;
	}

	public void setSelectionmode(SELECTIONMODE selectionmode) {
		this.selectionmode = selectionmode;
	}
}
