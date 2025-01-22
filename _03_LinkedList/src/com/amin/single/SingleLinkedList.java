package com.amin.single;

import com.amin.AbstractList;

/**
 * 单向链表
 */
public class SingleLinkedList<E> extends AbstractList<E> {

    private Node<E> first;

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    public SingleLinkedList() {
        first = null;
        size = 0;
    }

    @Override
    public void add(int index, E element) {
        rangCheckForAdd(index);

        if (index == 0) {
            first = new Node<>(element, first);
        } else {
            Node<E> prev = node(index - 1);
            Node<E> node = new Node<>(element, prev.next);
            prev.next = node;
        }

        size++;
    }

    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {

        Node<E> old = node(index);
        E oldElement = old.element;
        old.element = element;

        return oldElement;
    }

    @Override
    public E remove(int index) {

        E oldElement = first.element;
        if (index == 0) {
            first = first.next;
        } else {
            Node<E> prev = node(index - 1);
            Node<E> deleteNode = prev.next;
            oldElement = deleteNode.element;
            prev.next = deleteNode.next;
        }

        size--;

        return oldElement;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = first;
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
        first = null;
        size = 0;
    }

    private Node<E> node(int index) {
        rangCheck(index);

        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        return node;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("size = ").append(size).append(", [");
        Node<E> node = first;
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
