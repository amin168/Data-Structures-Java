package com.amin;

/**
 * 双向链表
 */
public class LinkedList<E> extends AbstractList<E> {

    private Node<E> first;

    private Node<E> last;

    private static class Node<E> {
        Node<E> prev;
        E element;
        Node<E> next;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder();
            if (prev != null)
                sb.append(prev.element);
            else
                sb.append("NULL");

            sb.append("_").append(element).append("_");

            if (next != null)
                sb.append(next.element);
            else
                sb.append("NULL");

            return sb.toString();
        }
    }

    @Override
    public void add(int index, E element) {
        rangCheckForAdd(index);

        if (index == size) { // 往最后插入
            Node<E> oldLast = last;
            last = new Node<>(oldLast, element, null);
            if (oldLast == null) {
                first = last;
            } else {
                oldLast.next = last;
            }

        } else {
            Node<E> next = node(index); // 添加后，当前的元素会变为next，所以这里取名为next
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(prev, element, next);
            next.prev = node;

            if (prev == null) { // index == 0
                first = node;
            } else {
                prev.next = node;
            }
        }

        size++;
    }

    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);
        E oldElement = node.element;
        node.element = element;

        return oldElement;
    }

    @Override
    public E remove(int index) {
        Node<E> node = node(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;

        if (prev == null) { // index  == 0
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) { // index == size - 1
            last = prev;
        } else {
            next.prev = prev;
        }

        size--;
        E element = node.element;
        node = null;

        return element;
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
        last = null;
        size = 0;
    }

    private Node<E> node(int index) {
        rangCheck(index);

        // size / 2
        int rang = size >> 1;
        Node<E> node;
        if (index < rang) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("size = ").append(size).append(", head [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0)
                sb.append(", ");
            sb.append(node);
            node = node.next;
        }

        sb.append("] tail");

        return sb.toString();
    }
}
