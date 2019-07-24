package com.amin;

public interface Queue<E> {
	int size();
	void clear();
    boolean isEmpty();
    void enQueue(E e);
    E deQueue();
    E front();
}
