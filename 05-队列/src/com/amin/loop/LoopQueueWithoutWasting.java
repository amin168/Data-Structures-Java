package com.amin.loop;

import com.amin.Queue;

/*
 * 循环队列，数组实现，有头索引和尾索引，这个版本中，不浪费空间
*/
@SuppressWarnings("unchecked")
public class LoopQueueWithoutWasting<E> implements Queue<E> {

	private E[] elements;
	private int front, tail;
	private int size;

	private static final int DEFAULT_CAPACITY = 10;

	public LoopQueueWithoutWasting() {
		elements = (E[]) new Object[DEFAULT_CAPACITY];
		front = 0;
		tail = 0;
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[index(i)] = null;
		}
		size = 0;
		front = 0;
		tail = 0;
	}

	@Override
	public boolean isEmpty() {
		// 注意，我们不再使用front和tail之间的关系来判断队列是否为空，而直接使用size
		return size == 0;
	}

	@Override
	public void enQueue(E e) {
		ensureCapacity(size + 1);

		elements[tail] = e;
		tail = (tail + 1) % elements.length;
		size++;
	}

	@Override
	public E deQueue() {

		E ret = elements[front];
		elements[front] = null;

		// 出队后
		// front = (front + 1) % elements.length;
		front = index(1);
		size--;

		trim();

		return ret;
	}

	@Override
	public E front() {
		return elements[front];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Queue: size = %d , capacity = %d\n", size, elements.length));
		sb.append("front [");

		// 注意，由于不浪费空间，循环遍历打印队列的逻辑也有相应的更改

		for (int i = 0; i < size; i++) {
			sb.append(elements[index(i)]);
			if (index(i + 1) != tail)
				sb.append(", ");
		}

		sb.append("] tail");

		return sb.toString();
	}

	/*
	 * 返回索引位置所在的真实索引值
	 */
	private int index(int index) {
		// 尽量避免使用乘*、 除/、 模%、 浮点数运算，效率低下
		/*
		 * 假如元素有7个 假如front在0的位置，在0的位置入队，则表示要在-1的位置插入元素，所以front + index = -1，但是这样是错误的
		 * 所以我们要加上7，则得出7-1=6，刚好就是在索引6的位置插入元素
		 */
		index += front;
		if (index < 0) {
			return index + elements.length;
		}

		return index % elements.length;
		// return index - (element.length > index ? 0 : element.length);

		// 已知n>=0， m>0
		// n%m 等价于 n – (m > n ? 0 : m) 的前提条件： n < 2m
		// return index - (element.length > index ? 0 : element.length);
	}

	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity)
			return;

		int newCapacity = capacity * 2; // oldCapacity + (oldCapacity >> 1);
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[index(i)];
		}
		elements = newElements;

		// 重置front
		front = 0;
		tail = size;
	}

	private void trim() {
		int oldCapacity = elements.length;
		int newCapacity4 = oldCapacity / 4;
		int newCapacity2 = oldCapacity / 2;
		if (size == newCapacity4 && newCapacity2 != 0) {
			E[] newElements = (E[]) new Object[newCapacity2];
			for (int i = 0; i < size; i++) {
				newElements[i] = elements[index(i)];
			}
			elements = newElements;
			front = 0;
			tail = size;
		}
	}

}
