package com.amin.loop;

/*
 * 循环双端队列，数组实现
*/
@SuppressWarnings("unchecked")
public class LoopDeque<E> {

	/*
	 * 真实头结点的index
	 */
	private int front;
	private int size;

	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;

	public LoopDeque() {
		elements = (E[]) new Object[DEFAULT_CAPACITY];
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[index(i)] = null;
		}
		size = 0;
		front = 0;
	}

	/**
	 * 从尾部入队
	 * 
	 * @param element
	 */
	public void enQueueRear(E element) {
		ensureCapacity(size + 1);
		elements[index(size)] = element;
		size++;
	}

	/**
	 * 从头部出队
	 * 
	 * @param element
	 */
	public E deQueueFront() {
		int realFront = index(front);
		E frontElement = elements[realFront];
		elements[realFront] = null;
		// 出队后
		// front = (front + 1) % elements.length;
		front = index(1);
		size--;
		return frontElement;
	}

	/**
	 * 从头部入队
	 * 
	 * @param element
	 */
	public void enQueueFront(E element) {
		ensureCapacity(size + 1);
		front = index(-1);
		elements[front] = element;
		size++;
	}

	/**
	 * 从尾部出队
	 * 
	 * @param element
	 */
	public E deQueueRear() {
		int rearIndex = rearIndex();
		E realElement = elements[rearIndex];
		elements[rearIndex] = null;
		size--;
		return realElement;
	}

	public E front() {
		return elements[front];
	}

	public E rear() {
		return elements[rearIndex()];
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
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("capcacity=").append(elements.length).append(" size=").append(size).append(" front=")
				.append(front).append(", [");
		for (int i = 0; i < elements.length; i++) {
			if (i != 0) {
				string.append(", ");
			}

			string.append(elements[i]);
		}
		string.append("]");
		return string.toString();
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

	private int rearIndex() {
		return index(size - 1);
	}

}
