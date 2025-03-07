package com.amin.heap;


import com.amin.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * 二叉堆（大顶堆）
 * @param <E>
 */
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {
    private E[] elements;

    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(E[] elements, Comparator<E> comparator) {
        super(comparator);

        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
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
        for (int i = 0; i < size; i++) {
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
        emptyCheck();

        E root = elements[0];
        elements[0] = elements[size - 1];
        elements[size - 1] = null;
        size--;

        siftDown(0);

        return root;
    }

    /**
     * 批量建堆
     */
    private void heapify() {
        // 自上而下的上滤
        // for (int i = 1; i < size; i++) {
        //    siftUp(i);
        //}

        // 自下而上的下滤
        // size/2 -1
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }

    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);

        if (size == 0) {
            elements[0] = element;
            size++;
            return null;
        }

        E root = elements[0];
        elements[0] = element;
        siftDown(0);

        return root;
    }

    /**
     * 让 index 位置的元素下滤
     *
     * @param index
     */
    private void siftDown(int index) {
        E element = elements[index];
        int half = size >> 1;

        // 第一个叶子节点的索引 == 非叶子节点的数量
        // index < 第一个叶子节点的索引
        // 非叶子节点的数量：floor (n/2)
        // 必须保证 index 位置是非叶子节点，因为这样才能有节点去交换
        while (index < half) {
            // index 的节点有2种情况
            // 1. 只有左子节点
            // 2. 有左右子节点

            // 默认为左子节点
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];

            // 右子节点
            int rightIndex = childIndex + 1;
            E right = elements[rightIndex];

            // 选出左右子节点最大的那个
            // rightIndex < size 证明索引在数组的合理范围之内
            if (rightIndex < size && compare(right, child) > 0) {
                childIndex = rightIndex;
                child = right;
            }

            if (compare(element, child) >= 0) break;

            // 将子节点存放到 index 位置
            elements[index] = child;

            // 重新设置 index
            index = childIndex;
        }
        elements[index] = element;

    }

    /**
     * 让 index 位置的元素上滤
     *
     * @param index
     */
    private void siftUp(int index) {
        E element = elements[index];
        while (index > 0) {
            // 父节点的索引 floor (i-1)/2, 代码默认为向下取整
            int parentIndex = (index - 1) >> 1;
            E parent = elements[parentIndex];

            // element <= parent
            if (compare(element, parent) <= 0) break;

            // 交换 element 跟 parent
//            E tmp = elements[index];
//            elements[index] = elements[parentIndex];
//            elements[parentIndex] = tmp;
//            parentIndex = index;

            // 将父节点交换在 index 位置
            elements[index] = parent;

            index = parentIndex;
        }
        elements[index] = element;
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

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
        // 左子节点 2i+1
        int index = ((int) node << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        // 右子节点 2i+2
        int index = ((int) node << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        return elements[(int) node];
    }
}
