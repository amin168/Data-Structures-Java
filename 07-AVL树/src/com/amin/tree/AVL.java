package com.amin.tree;

import java.util.Comparator;

public class AVL<E> extends BST<E> {
	public AVL() {
		this(null);
	}

	public AVL(Comparator<E> comparator) {
		super(comparator);
	}

	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new AVLNode(element, parent);
	}

	@Override
	protected void afterAdd(Node<E> node) {
		while ((node = node.parent) != null) {
			if (isBalanced(node)) {
				// 更新高度
				updateHeight(node);
			} else {
				// 恢复平衡
				// 能来到这里，node必然是不平衡的，而且是高度最低的
				rebalance(node);

				// 整棵树恢复平衡
				break;
			}
		}
	}
	
	@Override
	protected void afterRemove(Node<E> node) {
		while ((node = node.parent) != null) {
			if (isBalanced(node)) {
				// 更新高度
				updateHeight(node);
			} else {
				// 恢复平衡
				// 能来到这里，node必然是不平衡的，而且是高度最低的
				rebalance(node);

				// 整棵树恢复平衡
				// break; 因为删除之后，可能导致整棵树都失衡，所以这样不用break
			}
		}
	}

	/**
	 * 恢复平衡
	 * 
	 * @param grand (grandparent) 高度最低的那个不平衡点
	 */
	public void rebalance(Node<E> grand) {
		AVLNode<E> avlNodeGrand = ((AVLNode<E>) grand);
		AVLNode<E> parent = (AVLNode<E>) avlNodeGrand.tallerChild();
		Node<E> node = parent.tallerChild();
		if (parent.isLeftChild()) { // L
			if (node.isLeftChild()) { // LL – 右旋转（单旋）
				// rotate(grand, node.left, node, node.right, parent, parent.right, grand,
				// grand.right);
				rotate(grand, node, node.right, parent, parent.right, grand);
			} else { // LR – 先RR左旋转， 再LL右旋转（双旋）
				// rotate(grand, parent.left, parent, node.left, node, node.right, grand,
				// grand.right);
				rotate(grand, parent, node.left, node, node.right, grand);
			}
		} else { // R
			if (node.isLeftChild()) { // RL – 先LL右旋转， 再RR左旋转（双旋）
				// rotate(grand, grand.left, grand, node.left, node, node.right, parent,
				// parent.right);
				rotate(grand, grand, node.left, node, node.right, parent);
			} else { // RR – 左旋转（单旋）
				// rotate(grand, grand.left, grand, parent.left, parent, node.left, node,
				// node.right);
				rotate(grand, grand, parent.left, parent, node.left, node);
			}
		}
	}

	/**
	 * 为什么不需要a，g，因为每种情况的旋转，a跟b每次旋转后的位置都是不变的，所以可以不穿，但暂时只局限于AVL和红黑树
	 * 
	 * @param r 子树的根节点
	 */
	private void rotate(Node<E> r, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f) {
		d.parent = r.parent;
		if (r.isLeftChild()) {
			r.parent.left = d;
		} else if (r.isRightChild()) {
			r.parent.right = d;
		} else {
			root = d;
		}

		// b-c
		b.right = c;
		if (c != null) {
			c.parent = b;
		}
		updateHeight(b);

		// e-f
		f.left = e;
		if (e != null) {
			e.parent = f;
		}
		updateHeight(f);

		// b-d-f
		d.left = b;
		b.parent = d;
		d.right = f;
		f.parent = d;
		updateHeight(d);
	}

	/**
	 * @param r 子树的根节点
	 */
	private void rotate(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {

		d.parent = r.parent;
		if (r.isLeftChild()) {
			r.parent.left = d;
		} else if (r.isRightChild()) {
			r.parent.right = d;
		} else {
			root = d;
		}

		// a-b-c
		b.left = a;
		if (a != null) {
			a.parent = b;
		}
		b.right = c;
		if (c != null) {
			c.parent = b;
		}
		updateHeight(b);

		// e-f-g
		f.left = e;
		if (e != null) {
			e.parent = f;
		}
		f.right = g;
		if (g != null) {
			g.parent = f;
		}
		updateHeight(f);

		// b-d-f
		d.left = b;
		b.parent = d;
		d.right = f;
		f.parent = d;
		updateHeight(d);

	}

	/**
	 * 恢复平衡
	 * 
	 * @param grand (grandparent) 高度最低的那个不平衡点
	 */
	public void rebalance2(Node<E> grand) {
		AVLNode<E> avlNodeGrand = ((AVLNode<E>) grand);
		AVLNode<E> parent = (AVLNode<E>) avlNodeGrand.tallerChild();
		Node<E> node = parent.tallerChild();
		if (parent.isLeftChild()) { // L
			if (node.isLeftChild()) { // LL – 右旋转（单旋）
				rotateRight(grand);
			} else { // LR – 先RR左旋转， 再LL右旋转（双旋）
				rotateLeft(parent);
				rotateRight(grand);
			}
		} else { // R
			if (node.isLeftChild()) { // RL – 先LL右旋转， 再RR左旋转（双旋）
				rotateRight(parent);
				rotateLeft(grand);
			} else { // RR – 左旋转（单旋）
				rotateLeft(grand);
			}
		}
	}

	/**
	 * 左旋转
	 * 
	 * @param node
	 */
	private void rotateLeft(Node<E> grand) {
		Node<E> parent = grand.right;
		Node<E> child = parent.left;
		grand.right = child;
		parent.left = grand;

		afterRotate(grand, parent, child);

	}

	/**
	 * 右旋转
	 * 
	 * @param node
	 */
	private void rotateRight(Node<E> grand) {
		Node<E> parent = grand.left;
		Node<E> child = parent.right;
		grand.left = child;
		parent.right = grand;

		afterRotate(grand, parent, child);

	}

	private void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
		// 让parent成为子树的根节点
		parent.parent = grand.parent;
		if (grand.isLeftChild()) { // 如果grand原本是父节点的左子树
			grand.parent.left = parent;
		} else if (grand.isRightChild()) { // 如果grand原本是父节点的右子树
			grand.parent.right = parent;
		} else { // 如果grand是原本是根节点
			root = parent;
		}

		// 更新child的parent
		if (child != null) {
			child.parent = grand;
		}

		// 更新grand的parent
		grand.parent = parent;

		// 更新高度，先更新矮的节点
		updateHeight(grand);
		updateHeight(parent);
	}

	/**
	 * 判断该节点是否平衡
	 * 
	 * @param node
	 * @return
	 */
	private boolean isBalanced(Node<E> node) {
		// 每个节点的平衡因子只可能是 1、 0、 -1（绝对值 ≤ 1，如果超过 1，称之为“失衡”）
		return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
	}

	private void updateHeight(Node<E> node) {
		((AVLNode<E>) node).updateHeight();
	}

	private static class AVLNode<E> extends Node<E> {
		int height = 1; // 默认叶子节点的高度就是1

		public AVLNode(E element, Node<E> parent) {
			super(element, parent);
		}

		/**
		 * 计算平衡因子
		 * 
		 * @return
		 */
		public int balanceFactor() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
			return leftHeight - rightHeight;
		}

		/**
		 * 更新该节点的高度
		 */
		public void updateHeight() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
			height = 1 + Math.max(leftHeight, rightHeight);
		}

		/**
		 * 高度比较高的那个子节点
		 * 
		 * @return
		 */
		public Node<E> tallerChild() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
			if (leftHeight > rightHeight)
				return left;
			if (leftHeight < rightHeight)
				return right;

			// 如果高度一样的话
			// 如果自己父节点的左子孩子
			return isLeftChild() ? left : right;

		}

		@Override
		public String toString() {
			String parentString = "NULL";
			if (parent != null) {
				parentString = parent.element.toString();
			}

			return element + "->P:(" + parentString + "), H: (" + height + ")";
		}

	}

}
