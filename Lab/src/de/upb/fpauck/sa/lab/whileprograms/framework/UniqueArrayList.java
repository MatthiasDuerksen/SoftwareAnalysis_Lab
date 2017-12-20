package de.upb.fpauck.sa.lab.whileprograms.framework;

import java.util.ArrayList;
import java.util.Collection;

public class UniqueArrayList<T> extends ArrayList<T> {
	private static final long serialVersionUID = 7253021935261115758L;

	public UniqueArrayList() {
		super();
	}

	public UniqueArrayList(Collection<? extends T> c) {
		super(c);
	}

	public UniqueArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public boolean add(T e) {
		if (!this.contains(e)) {
			return super.add(e);
		} else {
			return false;
		}
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean changed = false;
		for (T element : c) {
			if (this.add(element)) {
				changed = true;
			}
		}
		return changed;
	}
}
