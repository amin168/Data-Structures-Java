package com.amin.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.amin.printer.BinaryTreeInfo;

@SuppressWarnings(value = { "unused", "unchecked" })
public class BinaryTree<E> implements BinaryTreeInfo {
	protected int size;
	protected Node<E> root;

	protected static class Node<E> {
		E element;
		Node<E> left;
		Node<E> right;
		Node<E> parent;

		public Node(E element, Node<E> parent) {
			this.element = element;
			this.parent = parent;
		}

		/**
		 * 判断是否有叶子节点
		 */
		public boolean isLeaf() {
			return left == null && right == null;
		}

		/**
		 * 判断是否含有两个节点
		 * @return
		 */
		public boolean hasTwoChildren() {
			return left != null && right != null;
		}

		/**
		 * 判断当前节点是否，是父节点的左子节点
		 * @return
		 */
		public boolean isLeftChild() {
			return parent != null && this == parent.left;
		}

		/**
		 * 判断当前节点是否，是父节点的右子节点
		 * @return
		 */
		public boolean isRightChild() {
			return parent != null && this == parent.right;
		}

	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		root = null;
		size = 0;
	}

	/*
	 * 前序遍历
	 */
	public void preorder(Visitor<E> visitor) {
		if (visitor == null)
			return;
		preorder(root, visitor);
	}

	private void preorder(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop)
			return;

		visitor.stop = visitor.visit(node.element); // 先遍历根节点
		preorder(node.left, visitor); // 再遍历左子树
		preorder(node.right, visitor); // 再遍历右子树
	}

	/*
	 * 中序遍历
	 */
	public void inorder(Visitor<E> visitor) {
		inorder(root, visitor);
	}

	private void inorder(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop)
			return;

		inorder(node.left, visitor); // 先遍历左子树

		if (visitor.stop)
			return;
		visitor.stop = visitor.visit(node.element); // 再遍历根节点

		inorder(node.right, visitor); // 再遍历右子树
	}

	/*
	 * 后序遍历
	 */
	public void postorder(Visitor<E> visitor) {
		postorder(root, visitor);
	}

	private void postorder(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop)
			return;

		postorder(node.left, visitor); // 先遍历左子树
		postorder(node.right, visitor); // 再遍历右子树
		if (visitor.stop)
			return;

		// 遍历要右子树的时候，如果此时visitor.stop为true的话，则下面的也不需要打印
		visitor.stop = visitor.visit(node.element); // 再遍历根节点
	}

	/**
	 * 层序遍历
	 */
	public void levelOrder(Visitor<E> visitor) {
		// 从上到下、从左到右依次访问每个节点
		// 1. 将根节点入队
		// 2. 循环执行以下操作，直到队列为空
		// 将队头节点A出队，进行访问
		// 将A的左节点入队
		// 讲A的右节点入队

		if (root == null || visitor == null)
			return;

		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			visitor.stop = visitor.visit(node.element);
			if (visitor.stop)
				return;

			if (node.left != null)
				queue.offer(node.left);

			if (node.right != null)
				queue.offer(node.right);
		}
	}

	/**
	 * 判断是否为完全二叉树
	 */
	public boolean isComplete() {
		if (root == null)
			return false;
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);

		boolean leaf = false;
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			if (leaf && !node.isLeaf()) // 如果leaf要求是叶子节点，但node没有叶子节点
				return false;

			if (node.left != null)
				queue.offer(node.left);
			else if (node.right != null) {
				// node.left == null && node.right != null
				return false;
			}

			if (node.right != null) {
				queue.offer(node.right);
			} else {
				// node.right == null
				// 后面遍历的节点必须是叶子节点，这样才是完全二叉树
				leaf = true; // node.left !=null && node.right == null
			}
		}

		return true;
	}

	/**
	 * 树的高度 非递归，利用层序遍历
	 */
	public int height() {
		if (root == null)
			return 0;

		// 树的高度
		int height = 0;

		// 每一层存储元素的个数
		int levelSize = 1;

		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			levelSize--;

			if (node.left != null)
				queue.offer(node.left);

			if (node.right != null)
				queue.offer(node.right);

			if (levelSize == 0) { // 意味着即将要访问下一层
				levelSize = queue.size();
				height++;
			}
		}

		return height;
	}

	protected Node<E> createNode(E element, Node<E> parent) {
		return new Node<E>(element, parent);
	}

	/**
	 * 求前驱节点
	 */
	protected Node<E> predecessor(Node<E> node) {
		if (node == null)
			return null;

		// 左子树中最大的
		// 前驱节点在左子树当中（left.right.right.right...）
		Node<E> p = node.left;
		if (p != null) {
			while (p.right != null) {
				p = p.right;
			}
			return p;
		}

		// 从父节点、祖父节点中寻找前驱节点
		// 发现是节点的左子树就一直往上找
		while (node.parent != null && node == node.parent.left) {
			node = node.parent;
		}

		// 跳出循环，能来到这里，有以下两种情况
		// 1. node.parent == null
		// 2. node == node.parent.right，node是父节点的右子树

		return node.parent;
	}

	/**
	 * 求后继节点
	 */
	protected Node<E> successor(Node<E> node) {
		if (node == null)
			return null;

		// 右子树最小的

		// 前驱节点在左子树当中（left.right.right.right...）
		Node<E> p = node.right;
		if (p != null) {
			while (p.left != null) {
				p = p.left;
			}
			return p;
		}

		// 从父节点、祖父节点中寻找前驱节点

		// 发现是节点的右子树就一直往上找
		while (node.parent != null && node == node.parent.right) {
			node = node.parent;
		}

		return node.parent;
	}

	/**
	 * 提供遍历元素接口
	 */
	public static abstract class Visitor<E> {

		boolean stop;

		/**
		 * @return 如果stop返回true，则代表停止遍历
		 */
		public abstract boolean visit(E element);
	}

	@Override
	public Object root() {
		return root;
	}

	@Override
	public Object left(Object node) {
		return ((Node<E>) node).left;
	}

	@Override
	public Object right(Object node) {
		return ((Node<E>) node).right;
	}

	@Override
	public Object string(Object obj) {
//		Node<E> node = (Node<E>) obj;
//
//		String parentString = "NULL";
//		if (node.parent != null) {
//			parentString = node.parent.element.toString();
//		}
//
//		return node.element + "->P:(" + parentString + ")";
		return obj;
	}
}
