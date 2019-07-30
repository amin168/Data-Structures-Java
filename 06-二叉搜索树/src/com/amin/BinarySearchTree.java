package com.amin;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.amin.printer.BinaryTreeInfo;

@SuppressWarnings(value = { "unused", "unchecked" })
/**
 * 二分搜索树 每个节点的值 大于其左子树所有节点的值 小于其右子树所有节点的值
 */
public class BinarySearchTree<E> implements BinaryTreeInfo {
	private int size;
	private Node<E> root;
	private Comparator<E> comparator; // 比较器

	public BinarySearchTree() {
		this(null);
	}

	public BinarySearchTree(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	private static class Node<E> {
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

		public boolean hasTwoChildren() {
			return left != null && right != null;
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

	public void add(E element) {
		elementNotNullCheck(element);

		// 添加的第一个节点
		if (root == null) {
			root = new Node<E>(element, null);
			size++;
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
		Node<E> newNode = new Node<>(element, parent);
		if (result > 0)
			parent.right = newNode;
		else
			parent.left = newNode;
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

		} else if (node.parent == null) {
			root = null; // node是叶子节点，并且node是root节点
		} else { // node是叶子节点，但不是根节点
			if (node == node.parent.right)
				node.parent.right = null;
			else // node == node.parent.left
				node.parent.left = null;
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

	/*
	 * 前序遍历
	 */
	public void preorderTraversal() {
		preorderTraversal(root);
	}

	private void preorderTraversal(Node<E> node) {
		if (node == null)
			return;

		System.out.print(node.element + " "); // 先遍历根节点
		preorderTraversal(node.left); // 再遍历左子树
		preorderTraversal(node.right); // 再遍历右子树
	}

	/*
	 * 中序遍历
	 */
	public void inorderTraversal() {
		inorderTraversal(root);
	}

	private void inorderTraversal(Node<E> node) {
		if (node == null)
			return;

		inorderTraversal(node.left); // 先遍历左子树
		System.out.print(node.element + " "); // 再遍历根节点
		inorderTraversal(node.right); // 再遍历右子树
	}

	/*
	 * 后序遍历
	 */
	public void postorderTraversal() {
		postorderTraversal(root);
	}

	private void postorderTraversal(Node<E> node) {
		if (node == null)
			return;

		postorderTraversal(node.left); // 先遍历左子树
		postorderTraversal(node.right); // 再遍历右子树
		System.out.print(node.element + " "); // 再遍历根节点
	}

	/**
	 * 层序遍历
	 */
	public void levelOrderTraversal() {
		// 从上到下、从左到右依次访问每个节点
		// 1. 将根节点入队
		// 2. 循环执行以下操作，直到队列为空
		// 将队头节点A出队，进行访问
		// 将A的左节点入队
		// 讲A的右节点入队

		if (root == null)
			return;

		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			System.out.print(node.element + " ");

			if (node.left != null)
				queue.offer(node.left);

			if (node.right != null)
				queue.offer(node.right);
		}
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

//	/**
//	 * 判断是否为完全二叉树
//	 */
//	public boolean isComplete() {
//		if (root == null)
//			return false;
//		Queue<Node<E>> queue = new LinkedList<>();
//		queue.offer(root);
//
//		boolean leaf = false;
//		while (!queue.isEmpty()) {
//			Node<E> node = queue.poll();
//			if (leaf && !node.isLeaf()) // 如果leaf要求是叶子节点，但node没有叶子节点
//				return false;
//
//			if (node.left != null && node.right != null) {
//				queue.offer(node.left);
//				queue.offer(node.right);
//			} else if (node.left == null && node.right != null) {
//				return false;
//			} else { // 后面遍历的节点必须是叶子节点，这样才是完全二叉树
//						// 如果node.left != null && node.right == null 或者node.left == null && node.right
//						// == null
//				leaf = true;
//
//				/**
//				 *	 		7
//				 *	 	 4     9
//				 * 	  2
//				 * 1
//				 */
//				 //当二叉树是以上情况的时候，如果不加下面的判断，会出现误判
//				if (node.left != null) {
//					queue.offer(node.left);
//				}
//			}
//		}
//
//		return true;
//	}

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

	public int height2() {
		return heightWithRecursion(root);
	}

	/**
	 * 求某一个节点的高度，递归实现
	 * 
	 * @param node
	 * @return
	 */
	private int heightWithRecursion(Node<E> node) {
		// 当前节点的高度，就等于该节点的左右节点，最大的高度 + 1
		if (node == null)
			return 0;
		return 1 + Math.max(heightWithRecursion(node.left), heightWithRecursion(node.right));
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

	/**
	 * 求前驱节点
	 */
	private Node<E> predecessor(Node<E> node) {
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
	private Node<E> successor(Node<E> node) {
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
		Node<E> node = (Node<E>) obj;

		String parentString = "NULL";
		if (node.parent != null) {
			parentString = node.parent.element.toString();
		}

		return node.element + "->P:(" + parentString + ")";
//		return node.element;
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
