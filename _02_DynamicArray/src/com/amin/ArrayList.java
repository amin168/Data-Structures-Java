package com.amin;

public class ArrayList<E> extends AbstractList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private E[] elements;

    public ArrayList(int capacity) {
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = (E[]) new Object[capacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public void add(int index, E element) {
        rangCheckForAdd(index);

        ensureCapacity(index + 1);

        // 大的先往后移
        for (int i = size - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
        }

        elements[index] = element;
        size++;
    }

    @Override
    public E get(int index) {
        rangCheck(index);
        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        rangCheck(index);

        E oldElement = elements[index];
        elements[index] = element;

        return oldElement;
    }

    @Override
    public E remove(int index) {
        rangCheck(index);

        E oldElement = elements[index];
        // 从最后往前移
        for (int i = index + 1; i < size; i++) {
            elements[i - 1] = elements[i];
        }

        // size--; 假如以前只有6个元素，size--后，得出索引为5的元素要清空
        // elements[size] = null;

        // 这句又上面两句演变过来
        elements[--size] = null;

        trim();

        return oldElement;
    }

    @Override
    public int indexOf(E element) {

        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) return i;
            }
        }

        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }

        size = 0;
    }

    /**
     * 扩容
     *
     * @param capacity
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity > capacity) {
            return;
        }

        // 扩容为1.5倍，相当于 1 + 1/2
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;

        System.out.println(oldCapacity + "扩容为" + newCapacity);
    }

    /**
     * 缩容
     */
    private void trim() {
        // 30
        int oldCapacity = elements.length;

        // 15
        int newCapacity = oldCapacity >> 1;

        // 如果元素的数量是大于缩容的一半，或者少于默认容量，则不缩容
        if (size >= newCapacity || oldCapacity <= DEFAULT_CAPACITY) {
            return;
        }

        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;

        System.out.println(oldCapacity + "缩容为" + newCapacity);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("capacity = ").append(elements.length);
        sb.append(", size = ").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
