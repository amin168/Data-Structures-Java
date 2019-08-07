package com.amin.tree;

import java.util.Comparator;

public class BBST<E> extends BST<E> {

	public BBST() {
		this(null);
	}

	public BBST(Comparator<E> comparator) {
		super(comparator);
	}

	/**
	 * 为什么不需要a，g，因为每种情况的旋转，a跟b每次旋转后的位置都是不变的，所以可以不穿，但暂时只局限于AVL和红黑树
	 * 
	 * @param r 子树的根节点
	 */
	protected void rotate(Node<E> r, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f) {
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

		// e-f
		f.left = e;
		if (e != null) {
			e.parent = f;
		}

		// b-d-f
		d.left = b;
		b.parent = d;
		d.right = f;
		f.parent = d;

	}

	/**
	 * @param r 子树的根节点
	 */
	protected void rotate(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {

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

		// e-f-g
		f.left = e;
		if (e != null) {
			e.parent = f;
		}
		f.right = g;
		if (g != null) {
			g.parent = f;
		}

		// b-d-f
		d.left = b;
		b.parent = d;
		d.right = f;
		f.parent = d;

	}

	/**
	 * 左旋转
	 * 
	 * @param node
	 */
	protected void rotateLeft(Node<E> grand) {
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
	protected void rotateRight(Node<E> grand) {
		Node<E> parent = grand.left;
		Node<E> child = parent.right;
		grand.left = child;
		parent.right = grand;

		afterRotate(grand, parent, child);

	}

	protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
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
	}

}
