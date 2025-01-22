package com.amin;

public interface List<E> {
    static final int ELEMENT_NOT_FOUND = -1;

    int size();

    boolean isEmpty();

    boolean contains(E element);

    void add(E element);

    void add(int index, E element);

    E get(int index);

    /**
     * 设置index位置的元素
     *
     * @param index
     * @param element
     * @return 原来的元素ֵ
     */
    E set(int index, E element);

    /**
     * 删除index位置的元素
     *
     * @param index
     * @return 原来的元素ֵ
     */
    E remove(int index);

    int indexOf(E element);

    void clear();

}
