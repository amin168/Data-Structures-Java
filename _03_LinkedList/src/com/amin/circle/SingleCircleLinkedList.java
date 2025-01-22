package com.amin.circle;

import com.amin.AbstractList;

/**
 * 单向循环链表
 */
public class SingleCircleLinkedList<E> extends AbstractList<E> {

    private Node<E> first;

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(element).append("_").append(next.element);
            return sb.toString();
        }
    }

    public SingleCircleLinkedList() {
        first = null;
        size = 0;
    }

    @Override
    public void add(int index, E element) {
        rangCheckForAdd(index);

        if (index == 0) {
            Node<E> newFirst = new Node<>(element, first);

            // 拿到最后一个节点
            Node<E> last = size == 0 ? newFirst : node(size - 1);
            last.next = newFirst;
            first = newFirst;
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
            if (size == 1) {
                first = null;
            } else {
                // 拿到最后一个节点，这里要注意获取最后一个节点的顺序问题
                Node<E> last = node(size - 1);
                first = first.next;
                last.next = first;
            }

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
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }

            string.append(node);

            node = node.next;
        }
        string.append("]");
        return string.toString();
    }
}
