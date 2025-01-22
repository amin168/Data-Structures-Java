package com.amin.single;

import com.amin.AbstractList;

/**
 * 单向链表
 * 增加虚拟头节点
 */
public class SingleLinkedListWithDummyHead<E> extends AbstractList<E> {

    private Node<E> dummyHead;

    public SingleLinkedListWithDummyHead() {
        dummyHead = new Node<>(null, null);
        size = 0;
    }

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(int index, E element) {
        rangCheckForAdd(index);

        Node<E> prev = index == 0 ? dummyHead : node(index - 1);
        prev.next = new Node<>(element, prev.next);

        size++;
    }

    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {

        rangCheck(index);

        Node<E> node = node(index);
        E oldElement = node.element;
        node.element = element;
        return oldElement;
    }

    @Override
    public E remove(int index) {

        rangCheck(index);

        Node<E> prev = index == 0 ? dummyHead : node(index - 1);
        Node<E> delete = prev.next;
        E oldElement = delete.element;
        prev.next = delete.next;

        size--;

        return oldElement;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = dummyHead;

        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) {
                    return i;
                }
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) {
                    return i;
                }
                node = node.next;
            }
        }

        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        dummyHead = null;
        size = 0;
    }

    private Node<E> node(int index) {
        rangCheck(index);

        Node<E> node = dummyHead.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        return node;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("size = ").append(size).append(", [");
        Node<E> node = dummyHead;
        while (node != null) {
            if (node.next != null) {
                sb.append(node.element + "->");
            } else {
                sb.append(node.element);
            }
            node = node.next;
        }

        sb.append("]");

        return sb.toString();
    }
}
