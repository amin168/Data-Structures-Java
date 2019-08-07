package com.amin.tree;

import java.util.Comparator;

public class RBTree<E> extends BBST<E> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;

	public RBTree() {
		this(null);
	}

	public RBTree(Comparator<E> comparator) {
		super(comparator);
	}

	/**
	 * 添加之后，修复红黑树的性质
	 */
	@Override
	protected void afterAdd(Node<E> node) {
		Node<E> parent = node.parent;

		// 添加的是根节点（也包括上溢到根节点），就直接染黑
		if (parent == null) {
			black(node);
			return;
		}

		// 如果添加父节点是黑色，直接返回
		if (isBlack(parent))
			return;

		// 叔父节点
		Node<E> uncle = parent.sibling();

		// 祖父节点
		Node<E> grand = red(parent.parent);

		// 如果叔父节点是红色 属于上溢的情况，只需要染色 - LL
		if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
			// 1. 将parent、uncle染成black
			// 2. grand向上合并，染成红色，当作新添加的节点进行处理
			black(parent);
			black(uncle);
			// 把祖父节点当做是新添加的节点
			afterAdd(grand);
			return;
		}

		// 叔父节点不是红色
		if (parent.isLeftChild()) { // L
			if (node.isLeftChild()) { // LL
				// 1. parent染成black，grand染成red
				// 2. grand进行右旋操作
				black(parent);
			} else { // LR
				// 1. 自己染成black，grand染成red
				// 2. 进行双旋操作
				// 3. parent左旋转，grand右旋转
				black(node);
				rotateLeft(parent);
			}
			rotateRight(grand);
		} else { // R
			if (node.isLeftChild()) { // RL
				// 1. 自己染成black，grand染成red
				// 2. 进行双旋操作
				// 3. parent右旋转，grand左旋转
				black(node);
				rotateRight(parent);
			} else { // RR
				// 1. parent染成black，grand染成red
				// 2. grand进行右旋操作
				black(parent);
			}
			rotateLeft(grand);
		}
	}

	@Override
	protected void afterRemove(Node<E> node) {

		// 如果删除的是red节点，直接删除，不用任何操作
//		if (isRed(node)) {
//			return;
//		}

		// 如果用于取代node的子节点是红色
//		if (isRed(replacement)) {
//			// 将用于替代的子节点染成black就可以保持红黑树的性质
//			black(replacement);
//			return;
//		}

		// 如果删除的是red节点 或者 用于取代node的子节点是红色
		if (isRed(node)) {
			// 将用于替代的子节点染成black就可以保持红黑树的性质
			black(node);
			return;
		}

		Node<E> parent = node.parent;
		// 删除的是根节点
		if (parent == null)
			return;

		// 删除的是黑色叶子节点【下溢的情况】
		// node是被删除的叶子节点
		// 在调用下面的代码时候，node的parent.left或者parent.right已经被清空了
		// 因此node.sibling()是没法拿到的

		// 所以，我们可以反过来判断，如果parent.left == null的话，被删除的节点以前是在左边的
		// 判断被删除的node是左还是右，就看父节点清空了谁
		// 这里有一点不严谨的地方是，node有可能是下面afterRemove(parent, null)传进来的，所以要加上node.isLeftChild()
		boolean left = parent.left == null || node.isLeftChild();

		// 如果node以前是左，那么兄弟节点就在右边
		Node<E> sibling = left ? parent.right : parent.left;

		if (left) { // 被删除的节点在左边，兄弟节点在右边

			// 下面的情况，跟left == false的情况完全相反，即做对称处理
			if (isRed(sibling)) {
				black(sibling);
				red(parent);
				rotateLeft(parent);
				sibling = parent.right;
			}
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				boolean parentBlack = isBlack(parent);
				red(sibling);
				black(parent);
				if (parentBlack) {
					afterRemove(parent);
				}
			} else {
				if (isBlack(sibling.right)) {
					rotateRight(sibling);
					sibling = parent.right;
				}

				color(sibling, colorOf(parent));
				black(sibling.right);
				black(parent);
				rotateLeft(parent);
			}

		} else { // 被删除的节点在右边，兄弟节点在左边

			// 这里很想之前做二叉树删除的时候，处理度为2情况
			// 先处理度为2，后面再统一处理度为1和0的情况，因为度2的情况，可以统一转化成度为1和0的情况
			if (isRed(sibling)) { // 兄弟节点是红色，要把兄弟节点转化成黑色的情况去处理
				black(sibling);
				red(parent);
				rotateRight(parent); // 将父节点进行右旋转，把兄弟节点的儿子，转化成node的兄弟节点
				// 更换兄弟
				sibling = parent.left;
			}

			// 下面的情况：兄弟节点必然是黑色

			// sibling的左跟右都是黑色子节点，如果left 或者 right 是null都认定为黑色
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				// 兄弟节点没有1个红色子节点，父节点向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent);
				}
			} else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
				// 兄弟左边的节点是黑色（有可能是null节点），兄弟要先旋转
				if (isBlack(sibling.left)) {
					rotateLeft(sibling);
					sibling = parent.left; // 因为左旋转后，sibling位置已经发生变化，所以需要重新赋值
				}

				// 将sibling然成parent的颜色
				color(sibling, colorOf(parent));
				black(sibling.left);
				black(parent);
				rotateRight(parent);
			}
		}
	}

//	protected void afterRemove(Node<E> node, Node<E> replacement) {
//
//		// 如果删除的是red节点，直接删除，不用任何操作
//		if (isRed(node)) {
//			return;
//		}
//
//		// 如果用于取代node的子节点是红色
//		if (isRed(replacement)) {
//			// 将用于替代的子节点染成black就可以保持红黑树的性质
//			black(replacement);
//			return;
//		}
//
//		Node<E> parent = node.parent;
//		// 删除的是根节点
//		if (parent == null)
//			return;
//
//		// 删除的是黑色叶子节点【下溢的情况】
//		// node是被删除的叶子节点
//		// 在调用下面的代码时候，node的parent.left或者parent.right已经被清空了
//		// 因此node.sibling()是没法拿到的
//
//		// 所以，我们可以反过来判断，如果parent.left == null的话，被删除的节点以前是在左边的
//		// 判断被删除的node是左还是右，就看父节点清空了谁
//		// 这里有一点不严谨的地方是，node有可能是下面afterRemove(parent, null)传进来的，所以要加上node.isLeftChild()
//		boolean left = parent.left == null || node.isLeftChild();
//
//		// 如果node以前是左，那么兄弟节点就在右边
//		Node<E> sibling = left ? parent.right : parent.left;
//
//		if (left) { // 被删除的节点在左边，兄弟节点在右边
//
//			// 下面的情况，跟left == false的情况完全相反，即做对称处理
//			if (isRed(sibling)) {
//				black(sibling);
//				red(parent);
//				rotateLeft(parent);
//				sibling = parent.right;
//			}
//			if (isBlack(sibling.left) && isBlack(sibling.right)) {
//				boolean parentBlack = isBlack(parent);
//				red(sibling);
//				black(parent);
//				if (parentBlack) {
//					afterRemove(parent, null);
//				}
//			} else {
//				if (isBlack(sibling.right)) {
//					rotateRight(sibling);
//					sibling = parent.right;
//				}
//
//				color(sibling, colorOf(parent));
//				black(sibling.right);
//				black(parent);
//				rotateLeft(parent);
//			}
//
//		} else { // 被删除的节点在右边，兄弟节点在左边
//
//			// 这里很想之前做二叉树删除的时候，处理度为2情况
//			// 先处理度为2，后面再统一处理度为1和0的情况，因为度2的情况，可以统一转化成度为1和0的情况
//			if (isRed(sibling)) { // 兄弟节点是红色，要把兄弟节点转化成黑色的情况去处理
//				black(sibling);
//				red(parent);
//				rotateRight(parent); // 将父节点进行右旋转，把兄弟节点的儿子，转化成node的兄弟节点
//				// 更换兄弟
//				sibling = parent.left;
//			}
//
//			// 下面的情况：兄弟节点必然是黑色
//
//			// sibling的左跟右都是黑色子节点，如果left 或者 right 是null都认定为黑色
//			if (isBlack(sibling.left) && isBlack(sibling.right)) {
//				// 兄弟节点没有1个红色子节点，父节点向下跟兄弟节点合并
//				boolean parentBlack = isBlack(parent);
//				red(sibling);
//				black(parent);
//				if (parentBlack) {
//					afterRemove(parent, null);
//				}
//			} else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
//				// 兄弟左边的节点是黑色（有可能是null节点），兄弟要先旋转
//				if (isBlack(sibling.left)) {
//					rotateLeft(sibling);
//					sibling = parent.left; // 因为左旋转后，sibling位置已经发生变化，所以需要重新赋值
//				}
//
//				// 将sibling然成parent的颜色
//				color(sibling, colorOf(parent));
//				black(sibling.left);
//				black(parent);
//				rotateRight(parent);
//			}
//		}
//	}

	/**
	 * 给节点染色
	 */
	private Node<E> color(Node<E> node, boolean color) {
		if (node == null)
			return node;
		((RBNode<E>) node).color = color;
		return node;
	}

	/**
	 * 给节点染成红色
	 */
	private Node<E> red(Node<E> node) {
		return color(node, RED);
	}

	/**
	 * 给节点染成黑色
	 */
	private Node<E> black(Node<E> node) {
		return color(node, BLACK);
	}

	/**
	 * 判断节点是什么颜色，如果节点是空，有可能就是假想出来的节点
	 */
	private boolean colorOf(Node<E> node) {
		return node == null ? BLACK : ((RBNode<E>) node).color;
	}

	private boolean isRed(Node<E> node) {
		return colorOf(node) == RED;
	}

	private boolean isBlack(Node<E> node) {
		return colorOf(node) == BLACK;
	}

	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new RBNode<>(element, parent);
	}

	private static class RBNode<E> extends Node<E> {

		/**
		 * 默认添加的节点是红色
		 */
		boolean color = RED;

		public RBNode(E element, Node<E> parent) {
			super(element, parent);
		}

		@Override
		public String toString() {
			String str = "";
			if (color == RED) {
				str = "R_";
			}
			return str + element.toString();
		}

	}

}
