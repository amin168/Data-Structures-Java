package com.amin.tree;

import java.util.Comparator;

public class AVL<E> extends BBST<E> {
	public AVL() {
		this(null);
	}

	public AVL(Comparator<E> comparator) {
		super(comparator);
	}

	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new AVLNode<E>(element, parent);
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
	
	@Override
	protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
		super.afterRotate(grand, parent, child);
		
		// 更新高度，先更新矮的节点
		updateHeight(grand);
		updateHeight(parent);
	}
	
	@Override
	protected void rotate(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {
		super.rotate(r, a, b, c, d, e, f, g);
		
		updateHeight(b);
		updateHeight(f);
		updateHeight(d);
	}

	@Override
	protected void rotate(Node<E> r, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f) {
		super.rotate(r, b, c, d, e, f);
		
		updateHeight(b);
		updateHeight(f);
		updateHeight(d);
	}
	
	/**
	 * 恢复平衡
	 * 
	 * @param grand (grandparent) 高度最低的那个不平衡点
	 */
	private void rebalance(Node<E> grand) {
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
	 * 恢复平衡
	 * 
	 * @param grand (grandparent) 高度最低的那个不平衡点
	 */
	private void rebalance2(Node<E> grand) {
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
