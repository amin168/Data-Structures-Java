package com.amin.single;

import com.amin.AbstractList;

// 单链表
// 增加虚拟头节点
public class SingleLinkedListWithDummyHead<E> extends AbstractList<E> {

	private Node<E> dummyHead;
	
	public SingleLinkedListWithDummyHead(){
		dummyHead = new Node<E>(null, null);
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
		dummyHead = null;
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
		
		Node<E> prev = index == 0 ? dummyHead:node(index -1);
		prev.next = new Node<E>(element,prev.next);

		size++;

	}

	@Override
	public E remove(int index) {

		rangeCheck(index);
		
		Node<E> prev = index == 0 ? dummyHead: node(index-1);
		
		Node<E> node = prev.next;
		E oldElement = node.element;
		
		prev.next = node.next;
		node = null;
		
		size--;

		return oldElement;
	}

	@Override
	public int indexOf(E element) {
		Node<E> node = dummyHead;
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

		Node<E> node = dummyHead.next;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("size=").append(size).append(", [");
		Node<E> node = dummyHead.next;
		while (node != null) {
			sb.append(node.element + "->");
			node = node.next;
		}
		sb.append("NULL ]");

		return sb.toString();
	}

}
