package com.amin.heap;

import java.util.Comparator;

import com.amin.printer.BinaryTreeInfo;

/**
 * 二叉堆（最大堆）
 * 
 * @author Administrator
 *
 * @param <E>
 */
@SuppressWarnings("unchecked")
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {

	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;

	public BinaryHeap(E[] elements, Comparator<E> comparator) {
		super(comparator);

		if (elements == null || elements.length == 0) {
			this.elements = (E[]) new Object[DEFAULT_CAPACITY];
		} else {
			// 批量建堆
			size = elements.length;
			int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
			this.elements = (E[]) new Object[capacity];
			for (int i = 0; i < elements.length; i++) {
				this.elements[i] = elements[i];
			}
			heapify();
		}
	}

	public BinaryHeap(E[] elements) {
		this(elements, null);
	}

	public BinaryHeap(Comparator<E> comparator) {
		this(null, comparator);
	}

	public BinaryHeap() {
		this(null, null);
	}

	@Override
	public void clear() {
		for (int i = 0; i < elements.length; i++) {
			elements[i] = null;
		}

		size = 0;
	}

	@Override
	public void add(E element) {
		elementNotNullCheck(element);
		ensureCapacity(size + 1);
		elements[size++] = element;
		siftUp(size - 1);
	}

	@Override
	public E get() {
		emptyCheck();
		return elements[0];
	}

	@Override
	public E remove() {

		// 1.用最后一个节点覆盖根节点
		// 2.删除最后一个节点
		// 3.循环执行以下操作
		// 如果 node < 最大的子节点
		// 与最大的子节点交换位置
		// 如果 node ≥ 最大的子节点， 或者 node 没有子节点
		// 退出循环

		emptyCheck();
		int lastIndex = --size;
		E root = elements[0];
		elements[0] = elements[lastIndex];
		elements[lastIndex] = null;

		siftDown(0);
		return root;
	}

	@Override
	public E replace(E element) {

		elementNotNullCheck(element);
		E root = null;
		if (size == 0) {
			elements[0] = element;
			size++;
		} else {
			root = elements[0];
			elements[0] = element;
			siftDown(0);
		}

		return root;
	}

	/**
	 * 让index位置的元素上滤
	 * 
	 * @param index
	 */
	private void siftUp(int index) {
//		E element = elements[index];
//		while (index > 0) {
//			// i > 0 ，它的父节点的索引为 floor((i – 1) / 2)
//			int parentIndex = (index - 1) >> 1;
//			E parent = elements[parentIndex];
//			// 如果少于或等于父节点
//			if (compare(element, parent) <= 0)
//				return;
//
//			// 交换index、跟parentIndex位置的内容
//			E temp = elements[index];
//			elements[index] = parent;
//			elements[parentIndex] = temp;
//
//			// 重新赋值index
//			index = parentIndex;
//		}

		E element = elements[index];
		while (index > 0) {
			// i > 0 ，它的父节点的索引为 floor((i – 1) / 2)
			int parentIndex = (index - 1) >> 1;
			E parent = elements[parentIndex];
			// 如果少于或等于父节点
			if (compare(element, parent) <= 0)
				break;

			// 将父元素存储在index的位置
			elements[index] = parent;

			// 重新赋值index
			index = parentIndex;
		}

		elements[index] = element;
	}

	/**
	 * 让index位置的元素下滤
	 * 
	 * @param index
	 */
	private void siftDown(int index) {
		E element = elements[index];
		// 非叶子节点个数floor( n / 2 )
		int half = size >> 1;
		// 第一个叶子节点的索引 == 非叶子节点的数量
		// index < 第一个叶子节点的索引
		// 必须保证index位置是非叶子节点
		while (index < half) {
			// index节点的情况有两种
			// 1.只有左节点
			// 2.同时有左右子节点

			// 默认为左子节点跟它比较，因为右子节点可能不存在
			// 左子节点的索引为 2i + 1
			int childIndex = (index << 1) + 1;
			E child = elements[childIndex];

			// 右子节点的索引为 2i + 2
			int rightIndex = (index << 1) + 2;

			// 选出左右最大的子节点
			if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
				// 右子节点比较大
				child = elements[childIndex = rightIndex];
			}

			if (compare(element, child) >= 0)
				break;

			// 将子节点存放在index位置
			elements[index] = child;

			// 重新设置index
			index = childIndex;
		}
		elements[index] = element;
	}

	/**
	 * 批量建堆
	 */
	private void heapify() {
		// 自上而下的上滤
		for (int i = 0; i < size; i++) {
			siftUp(i);
		}

		// 自下而上的下滤
		for (int i = (size >> 1) - 1; i > 0; i--) {
			siftDown(i);
		}
	}

	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity)
			return;

		// 新容量为旧容量的1.5倍
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;
	}

	private void emptyCheck() {
		if (size == 0) {
			throw new IndexOutOfBoundsException("Heap is empty");
		}
	}

	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}

	@Override
	public Object root() {
		return 0;
	}

	@Override
	public Object left(Object node) {
		// 如果 2i + 1 ≤ n – 1，它的左子节点的索引为 2i + 1
		int index = ((int) node << 1) + 1;
		return index >= size ? null : index;
	}

	@Override
	public Object right(Object node) {
		// 如果 2i + 2 ≤ n – 1 ，它的右子节点的索引为 2i + 2
		int index = ((int) node << 1) + 2;
		return index >= size ? null : index;
	}

	@Override
	public Object string(Object node) {
		return elements[(int) node];
	}

}
