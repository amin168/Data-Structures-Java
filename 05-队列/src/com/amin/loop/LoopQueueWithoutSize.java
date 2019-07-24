package com.amin.loop;

import com.amin.Queue;

/*
 * 循环队列，数组实现，有头索引和尾索引，这个版本中，不使用size，只使用front跟tail来完成所有的逻辑
*/
@SuppressWarnings("unchecked")
public class LoopQueueWithoutSize<E> implements Queue<E> {

	private E[] elements;
	private int front, tail;

	private static final int DEFAULT_CAPACITY = 10;

	public LoopQueueWithoutSize() {
		elements = (E[]) new Object[DEFAULT_CAPACITY + 1];
		front = 0;
		tail = 0;
	}

	@Override
	public int size() {
		if (isEmpty()) {
			return 0;
		}
		// 注意此时getSize的逻辑:
		// 如果tail >= front，非常简单，队列中的元素个数就是tail - front
		// 如果tail < front，说明我们的循环队列"循环"起来了，此时，队列中的元素个数为：
		// tail - front + data.length
		// 画画图，看能不能理解为什么？
		//
		// 也可以理解成，此时，data中没有元素的数目为front - tail,
		// 整体元素个数就是 data.length - (front - tail) = data.length + tail - front
		return tail > front ? tail - front : tail - front + elements.length;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size(); i++) {
			elements[index(i)] = null;
		}
		front = 0;
		tail = 0;
	}

	@Override
	public boolean isEmpty() {
		return front == tail;
	}

	@Override
	public void enQueue(E e) {
		ensureCapacity(size() + 1);

		elements[tail] = e;
		tail = (tail + 1) % elements.length;
	}

	@Override
	public E deQueue() {

		E ret = elements[front];
		elements[front] = null;

		// 出队后
		// front = (front + 1) % elements.length;
		front = index(1);

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
		sb.append(String.format("Queue: size = %d , capacity = %d\n", size(), elements.length));
		sb.append("front [");

		// 注意，由于不浪费空间，循环遍历打印队列的逻辑也有相应的更改

		for (int i = front; i != tail; i = (i + 1) % elements.length) {
			sb.append(elements[i]);
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

		int size = size();

		int newCapacity = capacity * 2; // oldCapacity + (oldCapacity >> 1);
		E[] newElements = (E[]) new Object[newCapacity + 1];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[index(i)];
		}
		elements = newElements;

		// 重置front
		front = 0;
		tail = size;
	}

	private void trim() {
		int size = size();

		int oldCapacity = elements.length;
		int newCapacity4 = oldCapacity / 4;
		int newCapacity2 = oldCapacity / 2;
		if (size == newCapacity4 && newCapacity2 != 0) {
			E[] newElements = (E[]) new Object[newCapacity2 + 1];
			for (int i = 0; i < size; i++) {
				newElements[i] = elements[index(i)];
			}
			elements = newElements;
			front = 0;
			tail = size;
		}
	}

}
