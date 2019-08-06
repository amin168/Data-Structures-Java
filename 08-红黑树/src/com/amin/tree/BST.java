package com.amin.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.amin.printer.BinaryTreeInfo;

@SuppressWarnings(value = { "unused", "unchecked" })
/**
 * 二分搜索树 每个节点的值 大于其左子树所有节点的值 小于其右子树所有节点的值
 */
public class BST<E> extends BinaryTree<E> {
	private Comparator<E> comparator; // 比较器

	public BST() {
		this(null);
	}

	public BST(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	public void add(E element) {
		elementNotNullCheck(element);

		// 添加的第一个节点
		if (root == null) {
			root = createNode(element, null);
			size++;

			// 新添加节点之后的处理
			afterAdd(root);
			return;
		}

		// 添加的不是第一个节点
		// 找到父节点
		Node<E> parent = root;
		Node<E> node = root;
		int result = 0;
		while (node != null) {
			result = compare(element, node.element);
			parent = node; // 父节点
			if (result > 0) { // element > node.element
				node = node.right;
			} else if (result < 0) {
				node = node.left;
			} else { // 值相等，直接覆盖
				node.element = element;
				return;
			}
		}

		// 要插到父节点的那个位置
		Node<E> newNode = createNode(element, parent);
		if (result > 0)
			parent.right = newNode;
		else
			parent.left = newNode;

		size++;

		// 新添加节点之后的处理
		afterAdd(newNode);
	}

	/**
	 * 添加node之后的调整
	 * 
	 * @param node 新添加的节点
	 */
	protected void afterAdd(Node<E> node) {

	}

	/**
	 * 移除node之后的调整
	 * 
	 * @param node 被删除的几点 或者 用于取代被删除节点的子节点
	 */
	protected void afterRemove(Node<E> node) {

	}

	public boolean contains(E element) {
		return node(element) != null;
	}

	public void remove(E element) {
		remove(node(element));
	}

	private void remove(Node<E> node) {
		if (node == null)
			return;

		if (node.hasTwoChildren()) { // 度为2的节点
			// 找到后续节点
			Node<E> s = successor(node);

			// 用后继节点的值覆盖度为2的节点的值
			node.element = s.element;

			// 删除后继节点
			node = s;
		}

		// 删除node节点（node的度必然是1或者0）
		Node<E> replacement = node.left != null ? node.left : node.right;

		if (replacement != null) { // node是度为1的节点
			// 更改parent的指向
			replacement.parent = node.parent;

			// 更改parent的left、right指向
			if (node.parent == null) { // node是度为1的节点，并且是根节点
				root = replacement;
			} else if (node == node.parent.left) { // 如果要删除的是左子节点
				node.parent.left = replacement;
			} else { // node == node.parent.right
				node.parent.right = replacement;
			}

			// 删除节点之后的处理，真正被删除的是node的前驱节点或后继节点
			// afterRemove(node, replacement);

			// 这里为什么可以传replacement，因为上面replacement.parent = node.parent
			// 所以AVL树是一直顺着parent去找
			// 而红黑树
			afterRemove(replacement);

		} else if (node.parent == null) {
			root = null; // node是叶子节点，并且node是root节点

			// 删除节点之后的处理，真正被删除的是node的前驱节点或后继节点
			afterRemove(node);

		} else { // node是叶子节点，但不是根节点，删除叶子节点
			if (node == node.parent.right)
				node.parent.right = null; // 把右边父节点指向node的那根线断掉
			else // node == node.parent.left
				node.parent.left = null; // 把左边父节点指向node的那根线断掉

			// 删除节点之后的处理，真正被删除的是node的前驱节点或后继节点
			afterRemove(node);
		}

		size--;
	}

	private Node<E> node(E element) {
		Node<E> node = root;
		while (node != null) {
			int result = compare(element, node.element);
			if (result == 0)
				return node;
			if (result > 0)
				node = node.right;
			else // result <0
				node = node.left;
		}

		return null;
	}

	/**
	 * @return 返回值等于0，代表e1和e2相等；返回值大于0，代表e1大于e2；返回值小于0，代表e1小于e2
	 */
	private int compare(E e1, E e2) {
		if (comparator != null) {
			return comparator.compare(e1, e2);
		}
		return ((Comparable<E>) e1).compareTo(e2);
	}

	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}

	/**
	 * 如果要自己实现打印树状结构
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toString(root, sb, "");
		return sb.toString();
	}

	private void toString(Node<E> node, StringBuilder sb, String prefix) {
		if (node == null)
			return;

		toString(node.left, sb, prefix + "L---");
		sb.append(prefix).append(node.element).append("\n");
		toString(node.right, sb, prefix + "R---");
	}

}
