package com.amin;

public interface Stack<E> {
	int getSize();

	boolean isEmpty();

	void push(E e);

	E pop();

	// 查看栈顶元素
	E peek();
}
