package com.amin.single;

import com.amin.AbstractList;

// 单链表
public class SingleLinkedList<E> extends AbstractList<E> {

	private Node<E> first;
	
	public SingleLinkedList(){
        first = null;
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
	public void clear() {
		size = 0;
		first = null;
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
	public void add(int index, E element) {

		rangeCheckForAdd(index);

		if (index == 0) {
			first = new Node<E>(element, first);
		} else {
			Node<E> prev = node(index - 1);
			Node<E> newNode = new Node<E>(element, prev.next);
			prev.next = newNode;
		}
		size++;

	}

	@Override
	public E remove(int index) {

		rangeCheck(index);

		E oldElement = first.element;

		if (index == 0) {
			first = first.next;
		} else {
			Node<E> prev = node(index - 1);
			Node<E> retNode = prev.next;
			oldElement = retNode.element;
			prev.next = prev.next.next;
			retNode = null;
		}

		size--;

		return oldElement;
	}

	@Override
	public int indexOf(E element) {
		Node<E> node = first;
		if (element == null) {
			for (int i = 0; i < size; i++) {
				if (node.element == null)
					return i;

				node = node.next;
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (element.equals(node.element))
					return i;

				node = node.next;
			}
		}

		return ELEMENT_NOT_FOUND;
	}

	private Node<E> node(int index) {
		rangeCheck(index);

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
			sb.append(node.element + "->");
			node = node.next;
		}
		sb.append("NULL ]");

		return sb.toString();
	}

}
