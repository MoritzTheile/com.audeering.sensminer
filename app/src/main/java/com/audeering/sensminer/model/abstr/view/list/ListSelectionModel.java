package com.audeering.sensminer.model.abstr.view.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class ListSelectionModel<T> {

	private Collection<T> selection = new HashSet<T>();

	private List<SelectionListener<T>> listeners = new ArrayList<SelectionListener<T>>();

	public void addListener(SelectionListener<T> listener) {
		listeners.add(listener);
	}

	public void select(T element) {
		selection.add(element);
		callListeners();
	}

	public void selectSilently(T element) {
		selection.add(element);
	}

	public void selectOnly(T element) {
		selection.clear();
		selection.add(element);
		callListeners();
	}

	public void selectAll(Collection<T> elements) {
		selection.addAll(elements);
		callListeners();
	}

	public void clearSelection() {
		selection.clear();
		callListeners();
	}

	public Collection<T> getSelection() {
		return selection;
	}

	public void unselect(T element) {
		selection.remove(element);
		callListeners();
	}

	public boolean isSelected(T element) {
		return selection.contains(element);
	}

	private void callListeners() {
		for (SelectionListener<T> listener : listeners) {
			listener.onHasChanged(new ArrayList<T>(selection));
		}
	};

	public void selectRange(List<T> allData) {

		T first = null;
		T last = null;
		for (T element : allData) {
			if (first == null && isSelected(element)) {
				first = element;
			}
			if (isSelected(element)) {
				last = element;
			}
		}

		List<T> elementsToSelect = new ArrayList<T>();
		boolean withinAreaToSelect = false;
		for (T element : allData) {
			if (element == first) {
				withinAreaToSelect = true;
			}
			if (withinAreaToSelect) {
				elementsToSelect.add(element);
			}
			if (element == last) {
				break;
			}
		}
		selectAll(elementsToSelect);

	}

	public interface SelectionListener<T> {
		void onHasChanged(List<T> selection);
	}
}
