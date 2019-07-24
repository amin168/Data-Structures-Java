package com.amin;

import com.amin.list.LinkedList;
import com.amin.list.List;

/**
 * 双端队列
 */
public class Deque<E> {
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

	// 从队尾入队
	public void enQueueRear(E element) {
		list.add(element);
	}

	// 从队头出队
	public E deQueueFront() {
		return list.remove(0);
	}

	// 从队头入队
	public void enQueueFront(E element) {
		list.add(0, element);
	}

	// 从队尾出队
	public E deQueueRear() {
		return list.remove(list.size() - 1);
	}

	// 获取队列的头元素
	public E front() {
		return list.get(0);
	}

	// 获取队列的尾元素
	public E rear() {
		return list.get(list.size() - 1);
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("Queue: ");
		res.append("front [");

		for(int i = 0 ; i < list.size() ; i ++){
			E item = list.get(i);
            res.append(item);
            if(i != list.size() - 1)
                res.append(", ");
        }
		
		res.append("] tail");
		return res.toString();
	}
}
