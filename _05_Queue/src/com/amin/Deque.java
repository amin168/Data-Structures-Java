package com.amin;

import com.amin.list.LinkedList;
import com.amin.list.List;

/**
 * 双端队列
 *
 * @param <E>
 */
public class Deque<E> {
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
     * 从队尾入队
     */
    public void enQueueRear(E element) {
        list.add(element);
    }

    /**
     * 从队头出队
     */
    public E deQueueFront() {
        return list.remove(0);
    }

    /**
     * 从队头入队
     */
    public void enQueueFront(E element) {
        list.add(0, element);
    }

    /**
     * 从队尾出队
     */
    public E deQueueRear() {
        return list.remove(list.size() - 1);
    }

    /**
     * 获取队列的头元素
     *
     * @return
     */
    public E front() {
        return list.get(0);
    }

    /**
     * 获取队尾元素
     *
     * @return
     */
    public E rear() {
        return list.get(list.size() - 1);
    }
}
