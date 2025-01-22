package com.amin;

import com.amin.list.LinkedList;
import com.amin.list.List;

/**
 * 队列，先进先出
 *
 * @param <E>
 */
public class Queue<E> {
    private List<E> list = new LinkedList();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void clear() {
        list.clear();
    }

    /**
     * 入队
     */
    public void enQueue(E element) {
        list.add(element);
    }

    /**
     * 出队
     */
    public E deQueue() {
        return list.remove(0);
    }

    /**
     * 获取队列的头元素
     *
     * @return
     */
    public E front() {
        return list.get(0);
    }
}
