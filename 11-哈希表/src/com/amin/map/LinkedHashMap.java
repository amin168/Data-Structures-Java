package com.amin.map;

import java.util.Objects;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class LinkedHashMap<K, V> extends HashMap<K, V> {
	private LinkedNode<K, V> first;
	private LinkedNode<K, V> last;

	@Override
	public void clear() {
		super.clear();
		first = null;
		last = null;
	}

	@Override
	public boolean containsValue(V value) {
		LinkedNode<K, V> node = first;
		while (node != null) {
			if (Objects.equals(value, node.value))
				return true;
			node = node.next;
		}

		return false;
	}

	/**
	 * 
	 * @param willNode   想要删除的节点
	 * @param removeNode 真正要删除的节点
	 */
	@Override
	protected void afterRemove(Node<K, V> willNode, Node<K, V> removeNode) {
		LinkedNode<K, V> node1 = (LinkedNode<K, V>) willNode;
		LinkedNode<K, V> node2 = (LinkedNode<K, V>) removeNode;

		if (node1 != node2) {
			// 度为2的节点会执行到这里
			// 交换willNode和removeNode在链表中的位置

			// 交换prev
			LinkedNode<K, V> temp = node1.prev;
			node1.prev = node2.prev;
			node2.prev = temp;
			if (node1.prev == null)
				first = node1;
			else
				node1.prev.next = node1;

			if (node2.prev == null)
				first = node2;
			else
				node2.prev.next = node1;

			// 交换next
			temp = node1.next;
			node1.next = node2.next;
			node2.next = temp;
			if (node1.next == null)
				last = node1;
			else
				node1.next.prev = node1;

			if (node2.next == null)
				last = node2;
			else
				node2.next.prev = node1;
		}

		LinkedNode<K, V> prev = node1.prev;
		LinkedNode<K, V> next = node1.next;

		if (prev == null) { // 意味着没有前一个节点，被删除的是头节点
			first = next;
		} else {
			prev.next = next;
		}

		if (next == null) {
			last = prev;
		} else {
			next.prev = prev;
		}
	}

	@Override
	public void traversal(Visitor<K, V> visitor) {

		if (visitor == null)
			return;

		LinkedNode<K, V> node = first;
		while (node != null) {
			if (visitor.visit(node.key, node.value))
				return;
			node = node.next;
		}
	}

	@Override
	protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
		LinkedNode<K, V> node = new LinkedNode(key, value, parent);

		if (first == null)
			first = last = node;
		else {
			last.next = node;
			node.prev = last;
			last = node;
		}

		return node;
	}

	private static class LinkedNode<K, V> extends Node<K, V> {
		LinkedNode<K, V> prev;
		LinkedNode<K, V> next;

		public LinkedNode(K key, V value, Node<K, V> parent) {
			super(key, value, parent);
		}

	}
}
