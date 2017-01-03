package com.audeering.sensminer.model.abstr.view;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstrObservable<T> {

	private final Collection<Observer<T>> observers = new ArrayList<Observer<T>>();

	public void addObserver(Observer<T> observer) {

		observers.add(observer);

	}

	public void removeObserver(Observer<T> observer) {

		observers.remove(observer);

	}

	public void hasChanged() {

		for (Observer<T> listener : new ArrayList<Observer<T>>(observers)) {

			listener.modelHasChanged(getModel());

		}

	}

	public T getModel() {
		// can be overwritten
		return null;
	};

	public interface Observer<T> {

		void modelHasChanged(T changes);

	}
}
