package com.amin;

import com.amin.list.LinkedList;
import com.amin.list.List;

/**
 * 链表实现的队列
 */
public class LinkedQueue<E> implements Queue<E> {
	private List<E> list = new LinkedList<E>();

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public void clear() {
		list.clear();
	}

	public void enQueue(E element) {
		list.add(element);
	}

	// 先进先出
	public E deQueue() {
		return list.remove(0);
	}

	public E front() {
		return list.get(0);
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("Queue: ");
		res.append("front [");
		for (int i = 0; i < list.size(); i++) {
			res.append(list.get(i));
			if (i != list.size() - 1)
				res.append(", ");
		}
		res.append("] tail");
		return res.toString();
	}

}
