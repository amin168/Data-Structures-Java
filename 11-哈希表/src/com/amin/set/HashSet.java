package com.amin.set;

import com.amin.map.HashMap;
import com.amin.map.Map;
import com.amin.map.TreeMap;
import com.amin.set.Set.Visitor;

public class HashSet<E> implements Set<E> {
	private HashMap<E, Object> map = new HashMap<>();
	
	
	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean contains(E element) {
		return map.containsKey(element);
	}

	@Override
	public void add(E element) {
		map.put(element, null); // 因为内部已经实现了新的会覆盖旧的
	}

	@Override
	public void remove(E element) {
		map.remove(element);
	}

	@Override
	public void traversal(Visitor<E> visitor) {
		map.traversal(new Map.Visitor<E, Object>() {
			@Override
			public boolean visit(E key, Object value) {
				return visitor.visit(key);
			}
		});
	}

}
