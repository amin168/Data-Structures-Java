package com.amin.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings({ "unchecked", "unused" })
public class TreeMap<K, V> implements Map<K, V> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;

	private int size;
	private Node<K, V> root;
	private Comparator<K> comparator; // 比较器

	public TreeMap() {
		this(null);
	}

	public TreeMap(Comparator<K> comparator) {
		this.comparator = comparator;
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

	@Override
	public V put(K key, V value) {
		keyNotNullCheck(key);

		// 添加的第一个节点
		if (root == null) {
			root = new Node<>(key, value, null);
			size++;

			// 新添加节点之后的处理
			afterPut(root);
			return null;
		}

		// 添加的不是第一个节点
		// 找到父节点
		Node<K, V> parent = root;
		Node<K, V> node = root;
		int result = 0;
		while (node != null) {
			result = compare(key, node.key);
			parent = node; // 父节点
			if (result > 0) { // element > node.element
				node = node.right;
			} else if (result < 0) {
				node = node.left;
			} else { // 值相等，直接覆盖
				node.key = key;
				V oldValue = node.value;
				node.value = value;
				return oldValue;
			}
		}

		// 要插到父节点的那个位置
		Node<K, V> newNode = new Node<>(key, value, parent);
		if (result > 0)
			parent.right = newNode;
		else
			parent.left = newNode;

		size++;

		// 新添加节点之后的处理
		afterPut(newNode);
		return null;
	}

	@Override
	public V get(K key) {
		Node<K, V> node = node(key);
		return node == null ? null : node.value;
	}

	@Override
	public V remove(K key) {
		return remove(node(key));
	}

	@Override
	public boolean containsKey(K key) {
		return node(key) != null;
	}

	@Override
	public boolean containsValue(V value) {

		Queue<Node<K, V>> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node<K, V> node = queue.poll();

			if (valEquals(node.value, value))
				return true;

			if (node.left != null)
				queue.offer(node.left);
			if (node.right != null)
				queue.offer(node.right);
		}

		return false;
	}

	@Override
	public void traversal(Visitor<K, V> visitor) {
		if (visitor == null)
			return;
		traversal(root, visitor);
	}

	private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
		if (node == null || visitor.stop)
			return;

		traversal(node.left, visitor);
		if (visitor.stop)
			return;
		visitor.visit(node.key, node.value);
		traversal(node.right, visitor);
	}

	private boolean valEquals(V v1, V v2) {
		return v1 == null ? v2 == null : v1.equals(v2);
	}

	/**
	 * 添加之后，修复红黑树的性质
	 */
	protected void afterPut(Node<K, V> node) {
		Node<K, V> parent = node.parent;

		// 添加的是根节点（也包括上溢到根节点），就直接染黑
		if (parent == null) {
			black(node);
			return;
		}

		// 如果添加父节点是黑色，直接返回
		if (isBlack(parent))
			return;

		// 叔父节点
		Node<K, V> uncle = parent.sibling();

		// 祖父节点
		Node<K, V> grand = red(parent.parent);

		// 如果叔父节点是红色 属于上溢的情况，只需要染色 - LL
		if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
			// 1. 将parent、uncle染成black
			// 2. grand向上合并，染成红色，当作新添加的节点进行处理
			black(parent);
			black(uncle);
			// 把祖父节点当做是新添加的节点
			afterPut(grand);
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

	private V remove(Node<K, V> node) {
		if (node == null)
			return null;

		V oldValue = node.value;

		if (node.hasTwoChildren()) { // 度为2的节点
			// 找到后续节点
			Node<K, V> s = successor(node);

			// 用后继节点的值覆盖度为2的节点的值
			node.key = node.key;
			node.value = node.value;

			// 删除后继节点
			node = s;
		}

		// 删除node节点（node的度必然是1或者0）
		Node<K, V> replacement = node.left != null ? node.left : node.right;

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

		return oldValue;
	}

	protected void afterRemove(Node<K, V> node) {
		// 这里去除了replacement

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

		Node<K, V> parent = node.parent;
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
		Node<K, V> sibling = left ? parent.right : parent.left;

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
				red(sibling);
				black(parent);
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

	private Node<K, V> node(K key) {
		Node<K, V> node = root;
		while (node != null) {
			int result = compare(key, node.key);
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
	 * 左旋转
	 * 
	 * @param node
	 */
	private void rotateLeft(Node<K, V> grand) {
		Node<K, V> parent = grand.right;
		Node<K, V> child = parent.left;
		grand.right = child;
		parent.left = grand;

		afterRotate(grand, parent, child);

	}

	/**
	 * 右旋转
	 * 
	 * @param node
	 */
	private void rotateRight(Node<K, V> grand) {
		Node<K, V> parent = grand.left;
		Node<K, V> child = parent.right;
		grand.left = child;
		parent.right = grand;

		afterRotate(grand, parent, child);

	}

	private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
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

	/**
	 * 求前驱节点
	 */
	private Node<K, V> predecessor(Node<K, V> node) {
		if (node == null)
			return null;

		// 左子树中最大的
		// 前驱节点在左子树当中（left.right.right.right...）
		Node<K, V> p = node.left;
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
	private Node<K, V> successor(Node<K, V> node) {
		if (node == null)
			return null;

		// 右子树最小的

		// 前驱节点在左子树当中（left.right.right.right...）
		Node<K, V> p = node.right;
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
	 * 给节点染色
	 */
	private Node<K, V> color(Node<K, V> node, boolean color) {
		if (node == null)
			return node;
		node.color = color;
		return node;
	}

	/**
	 * 给节点染成红色
	 */
	private Node<K, V> red(Node<K, V> node) {
		return color(node, RED);
	}

	/**
	 * 给节点染成黑色
	 */
	private Node<K, V> black(Node<K, V> node) {
		return color(node, BLACK);
	}

	/**
	 * 判断节点是什么颜色，如果节点是空，有可能就是假想出来的节点
	 */
	private boolean colorOf(Node<K, V> node) {
		return node == null ? BLACK : node.color;
	}

	private boolean isRed(Node<K, V> node) {
		return colorOf(node) == RED;
	}

	private boolean isBlack(Node<K, V> node) {
		return colorOf(node) == BLACK;
	}

	private void keyNotNullCheck(K key) {
		if (key == null) {
			throw new IllegalArgumentException("key must not be null");
		}
	}

	private int compare(K e1, K e2) {
		if (comparator != null) {
			return comparator.compare(e1, e2);
		}
		return ((Comparable<K>) e1).compareTo(e2);
	}

	private static class Node<K, V> {

		/**
		 * 默认添加的节点是红色
		 */
		boolean color = RED;

		K key;
		V value;

		Node<K, V> left;
		Node<K, V> right;
		Node<K, V> parent;

		public Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			this.value = value;
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
		 * 
		 * @return
		 */
		public boolean hasTwoChildren() {
			return left != null && right != null;
		}

		/**
		 * 判断当前节点是否，是父节点的左子节点
		 * 
		 * @return
		 */
		public boolean isLeftChild() {
			return parent != null && this == parent.left;
		}

		/**
		 * 判断当前节点是否，是父节点的右子节点
		 * 
		 * @return
		 */
		public boolean isRightChild() {
			return parent != null && this == parent.right;
		}

		/**
		 * 拿到当前节点的叔父(uncle)节点
		 * 
		 * @return
		 */
		public Node<K, V> sibling() {
			if (isLeftChild())
				return parent.right;

			if (isRightChild())
				return parent.left;

			return null;
		}
	}

}
