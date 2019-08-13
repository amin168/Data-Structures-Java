package com.amin.map;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import com.amin.printer.BinaryTreeInfo;
import com.amin.printer.BinaryTrees;

/*
 * 自定义对象如果不实现hashCode，则是用内存地址计算hashCode
 * 如果自定义对象里面的内容相等，但没有实现hashCode，则hash值
 * 肯定不一样，所以计算出来的索引可能不一样
 */
@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class HashMap<K, V> implements Map<K, V> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;

	/**
	 * table默认的容量（16）
	 */
	private static final int DEFAULT_CAPACITY = 1 << 4;

	/**
	 * 装填因子（Load Factor）：节点总数量 / 哈希表桶数组长度，也叫做负载因子
	 * 在JDK1.8的HashMap中，如果装填因子超过0.75，就扩容为原来的2倍
	 */
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	/**
	 * 所有红黑树节点加起来的总数
	 */
	private int size;

	/**
	 * 存放红黑树的索引跟红黑树 存储红黑树的根节点
	 */
	private Node<K, V>[] table;

	public HashMap() {
		table = new Node[DEFAULT_CAPACITY];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {

		if (size == 0)
			return;

		for (int i = 0; i < table.length; i++) {
			table[i] = null; // 清空根节点的红黑树
		}

		size = 0;
	}

	@Override
	public V put(K key, V value) {
		resize();

		int index = index(key);

		// 取出index位置红黑树的根节点
		Node<K, V> root = table[index];
		if (root == null) {
			root = createNode(key, value, null);
			table[index] = root;
			size++;
			// 修复红黑树性质
			fixAfterPut(root);
			return null;
		}

		// 根节点不等于空，添加新的节点到红黑树上面
		// 添加的不是第一个节点
		// 找到父节点
		Node<K, V> parent = root;
		Node<K, V> node = root;
		int cmp = 0;
		K k1 = key;
		int h1 = hash(k1);
		Node<K, V> result = null;
		boolean searched = false; // 是否已经搜索过这个key
		do { // 改成do while循环，性能优化，因为能来到这里，root肯定不为空
				// result = compare(key, node.key, h1, node.hash);
			parent = node; // 父节点
			K k2 = node.key;
			int h2 = node.hash;
			// 这么多比较其实是为提高效率，不用盲目的扫描
			// 所以node()里面的方法也一样
			if (h1 > h2) {
				cmp = 1;
			} else if (h1 < h2) {
				cmp = -1;
			} else if (Objects.equals(k1, k2)) {
				cmp = 0;
			} else if (k1 != null & k2 != null && k1.getClass().getName() == k2.getClass().getName()
					&& k1 instanceof Comparable && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
				// cmp = ((Comparable) k1).compareTo(k2);
			} else if (searched) { // 已经扫描过了
				cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
			} else { // searched == false，还没有扫描，然后再根据内存地址大小决定左右
				// hash值相等，equals的值不相等
				// 先进行左右扫描，如果再找不到就比较内存地址
				// 性能不会差，因为会对红黑树的大小进行控制
				if ((node.left != null && (result = node(node.left, k1)) != null)
						|| (node.right != null && (result = node(node.right, k1)) != null)) {
					// 说明已经存在这个key
					node = result;
					cmp = 0;
				} else { // 不存在这个key
					searched = true;
					cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
				}
			}

			if (cmp > 0) { // element > node.element
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			} else { // 值相等，直接覆盖
				node.key = key;
				V oldValue = node.value;
				node.value = value;
				// 两个key的equals是相等的才会来到这里
				// 既然两个key的equals相等
				// 则它们key的hash也会一样
				// 所以这句话有没有都无所谓
				node.hash = h1;
				return oldValue;
			}
		} while (node != null);

		// 要插到父节点的那个位置
		Node<K, V> newNode = createNode(key, value, parent);
		if (cmp > 0)
			parent.right = newNode;
		else
			parent.left = newNode;

		size++;

		// 新添加节点之后的处理
		fixAfterPut(newNode);
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
		if (size == 0)
			return false;

		Queue<Node<K, V>> queue = new LinkedList<HashMap.Node<K, V>>();
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null)
				continue;
			queue.offer(table[i]);
			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();
				if (Objects.equals(value, node.value))
					return true;

				if (node.left != null)
					queue.offer(node.left);
				if (node.right != null)
					queue.offer(node.right);
			}
		}

		return false;
	}

	@Override
	public void traversal(Visitor<K, V> visitor) {
		if (size == 0)
			return;

		Queue<Node<K, V>> queue = new LinkedList<HashMap.Node<K, V>>();
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null)
				continue;
			queue.offer(table[i]);
			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();
				if (visitor.visit(node.key, node.value))
					return;

				if (node.left != null)
					queue.offer(node.left);
				if (node.right != null)
					queue.offer(node.right);
			}
		}

		return;
	}

	public void print() {
		if (size == 0)
			return;

		for (int i = 0; i < table.length; i++) {
			final Node<K, V> root = table[i];
			System.out.println("【index = " + i + "】");
			BinaryTrees.println(new BinaryTreeInfo() {

				@Override
				public Object string(Object node) {
					return node;
				}

				@Override
				public Object root() {
					return root;
				}

				@Override
				public Object right(Object node) {
					return ((Node<K, V>) node).right;
				}

				@Override
				public Object left(Object node) {
					// TODO Auto-generated method stub
					return ((Node<K, V>) node).left;
				}
			});
			System.out.println("---------------------------------------------------");
		}

	}

	private void resize() {
		if (size / table.length <= DEFAULT_LOAD_FACTOR)
			return;

		Node<K, V>[] oldTable = table;
		table = new Node[oldTable.length << 1];

		Queue<Node<K, V>> queue = new LinkedList<>();
		for (int i = 0; i < oldTable.length; i++) {
			Node<K, V> root = oldTable[i];
			if (root == null)
				continue;

			queue.offer(root);
			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();

				if (node.left != null)
					queue.offer(node.left);
				if (node.right != null)
					queue.offer(node.right);

				// 挪动代码要放到最后，因为如果不放到最后，在movenode()里面就会情况node的parent、left、right
				moveNode(node);
			}

		}
	}

	private void moveNode(Node<K, V> newNode) {
		// 重置，当作新的节点，所以颜色要为红色
		newNode.parent = null;
		newNode.left = null;
		newNode.right = null;
		newNode.color = RED;

		int index = index(newNode);

		// 取出index位置红黑树的根节点
		Node<K, V> root = table[index];
		if (root == null) {
			root = newNode;
			table[index] = root;

			// 修复红黑树性质
			fixAfterPut(root);
			return;
		}

		// 根节点不等于空，添加新的节点到红黑树上面
		// 添加的不是第一个节点
		// 找到父节点
		Node<K, V> parent = root;
		Node<K, V> node = root;
		int cmp = 0;
		K k1 = newNode.key;
		int h1 = newNode.hash;

		do { // 改成do while循环，性能优化，因为能来到这里，root肯定不为空

			parent = node; // 父节点
			K k2 = node.key;
			int h2 = node.hash;
			// 这么多比较其实是为提高效率，不用盲目的扫描
			// 所以node()里面的方法也一样
			if (h1 > h2) {
				cmp = 1;
			} else if (h1 < h2) {
				cmp = -1;
			}

			// 可以的注释掉的原因是，旧的数据已经存在于旧的哈希表，所以每个key肯定是唯一的
			// 所以也不用搜索
//			else if (Objects.equals(k1, k2)) {
//				cmp = 0;
//			} 

			else if (k1 != null & k2 != null && k1.getClass().getName() == k2.getClass().getName()
					&& k1 instanceof Comparable && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
				// cmp = ((Comparable) k1).compareTo(k2);
			} else {
				cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
			}

			if (cmp > 0) { // element > node.element
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			}
		} while (node != null);

		if (cmp > 0)
			parent.right = newNode;
		else
			parent.left = newNode;
		newNode.parent = parent;

		// 新添加节点之后的处理
		fixAfterPut(newNode);
	}

	protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
		return new Node<>(key, value, parent);
	}

	/**
	 * 
	 * @param willNode   想要删除的节点
	 * @param removeNode 真正要删除的节点
	 */
	protected void afterRemove(Node<K, V> willNode, Node<K, V> removeNode) {
	}

	/**
	 * 比较key大小（未用到，已经被put()跟node()里面的比较方法重写了）
	 * 
	 * @param k1
	 * @param k2
	 * @param h1 k1的hashCode
	 * @param h2 k2的hashCode
	 * @return
	 */
	private int compare(K k1, K k2, int h1, int h2) {
		// 由于经常调用这段代码，所以把hash封装到node节点上
//		int h1 = k1 == null ? 0 : k1.hashCode();
//		int h2 = k2 == null ? 0 : k2.hashCode();

		// 比较hash值
		int result = h1 - h2; // 这两个值可能相同
		if (result != 0)
			return result;

		// hash值相等，比较equals
		if (Objects.equals(k1, k2))
			return 0;

		// hash值相等，但equals的值不相等
		// 比较类名
		if (k1 != null && k2 != null && k1.getClass() == k2.getClass() && k1 instanceof Comparable) {
			String k1Cls = k1.getClass().getName();
			String k2Cls = k2.getClass().getName();
			result = k1Cls.compareTo(k2Cls);
			if (result != 0)
				return result;

			// 同一种类型并且具备可比较性
			if (k1 instanceof Comparable) {
				return ((Comparable) k1).compareTo(k2);
			}
		}

		// 同一种类型，但是不具备可比较性，equals的值又不相等
		// 或者k1不为null，k2为null
		// 或者k2不为null，k1为null
		return System.identityHashCode(k1) - System.identityHashCode(k2);
	}

	/**
	 * 添加之后，修复红黑树的性质
	 */
	private void fixAfterPut(Node<K, V> node) {
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
			fixAfterPut(grand);
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

	protected V remove(Node<K, V> node) {
		if (node == null)
			return null;

		Node<K, V> willNode = node;

		V oldValue = node.value;

		if (node.hasTwoChildren()) { // 度为2的节点
			// 找到后续节点
			Node<K, V> s = successor(node);

			// 用后继节点的值覆盖度为2的节点的值
			node.key = s.key;
			node.value = s.value;
			node.hash = s.hash;
			// 删除后继节点
			node = s;
		}

		// 删除node节点（node的度必然是1或者0）
		Node<K, V> replacement = node.left != null ? node.left : node.right;
		int index = index(node);

		if (replacement != null) { // node是度为1的节点
			// 更改parent的指向
			replacement.parent = node.parent;

			// 更改parent的left、right指向
			if (node.parent == null) { // node是度为1的节点，并且是根节点
				table[index] = replacement;
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
			fixAfterRemove(replacement);

		} else if (node.parent == null) {
			table[index] = null; // node是叶子节点，并且node是root节点

			// 删除节点之后的处理，真正被删除的是node的前驱节点或后继节点
			fixAfterRemove(node);

		} else { // node是叶子节点，但不是根节点，删除叶子节点
			if (node == node.parent.right)
				node.parent.right = null; // 把右边父节点指向node的那根线断掉
			else // node == node.parent.left
				node.parent.left = null; // 把左边父节点指向node的那根线断掉

			// 删除节点之后的处理，真正被删除的是node的前驱节点或后继节点
			fixAfterRemove(node);
		}

		size--;

		// 交给子类去实现
		afterRemove(willNode, node);

		return oldValue;
	}

	private void fixAfterRemove(Node<K, V> node) {
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
					fixAfterRemove(parent);
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
					fixAfterRemove(parent);
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

			// 这里覆盖grand、parent、child所在的根节点
			// 既然这三个节点都在一颗红黑树上面，则他们算出来的索引都一样
			table[index(grand)] = parent;
		}

		// 更新child的parent
		if (child != null) {
			child.parent = grand;
		}

		// 更新grand的parent
		grand.parent = parent;
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

	private Node<K, V> node(K k1) {
		Node<K, V> root = table[index(k1)];
		return root == null ? null : node(root, k1);
	}

	/**
	 * 找节点的方法
	 * 
	 * @param key
	 * @return
	 */
	private Node<K, V> node(Node<K, V> node, K k1) {
		int h1 = hash(k1);
		Node<K, V> result = null; // 存储查找结果的node
		int cmp = 0;
		while (node != null) {
			K k2 = node.key;
			int h2 = node.hash;

			// 先比较哈希值，决定树是往左还是往右
			if (h1 > h2) {
				node = node.right;
			} else if (h1 < h2) {
				node = node.left;
			} else if (Objects.equals(k1, k2)) {
				return node;
			} else if (k1 != null && k2 != null && k1.getClass() == k2.getClass() && k1 instanceof Comparable
					&& (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
				// 能来到这里 hash值相等，equals不相等

				node = cmp > 0 ? node.right : node.left;

//				int cmp = ((Comparable) k1).compareTo(k2);
//				if (cmp > 0) {
//					node = node.right;
//				} else if (cmp < 0) {
//					node = node.left;
//				}
//				else {
//					return node; // 发现大小等于0，就会返回node，而不是equals相等，所以这里要修改，只要equals才证明找到
//				}
			}
			// hash相等，不具备可比较性，equals的值不相等
			// k1可能为null
			// 又不能比内存大小
			else if (node.right != null && (result = node(node.right, k1)) != null) {
				return result;
			} else { // 只能往左边找
				node = node.left;
			}
//			else if (node.left != null && (result = node(node.left, k1)) != null) {
//				return result;
//			} else {
//				// 找不到
//				return null;
//			}

			// node.right != null && (result = node(node.right, k1)) != null
			// 这句代码由下面的代码演变而来

//			else if (node.right != null) {
//				node(node.right, k1); // 在右子树中找k1
//			}

			// 这段代码由以上的代码重写
			// 相当于如果内存地址不同，但里面的equals相等，则会有问题
//			int result = compare(key, node.key, h1, node.hash);
//
//			if (result == 0)
//				return node; // 将找到的节点返回
//
//			if (result > 0) { // element > node.element
//				node = node.right;
//			} else if (result < 0) {
//				node = node.left;
//			}
		}

		return null;
	}

	/**
	 * 根据key生成索引（在捅数组中的位置）
	 * 
	 * @param key
	 * @return
	 */
	private int index(K key) {
		return hash(key) & (table.length - 1);
	}

	/**
	 * 扰动计算
	 * 
	 * @param key
	 * @return
	 */
	private int hash(K key) {
		if (key == null)
			return 0;
		int hash = key.hashCode();
		return hash ^ (hash >>> 16); // 无符号右移16位，JDK里面的做法
	}

	/**
	 * 根据node生成索引（在捅数组中的位置）
	 * 
	 * @param node
	 * @return
	 */
	private int index(Node<K, V> node) {
		return node.hash & (table.length - 1);
	}

	protected static class Node<K, V> {

		/**
		 * 默认添加的节点是红色
		 */
		boolean color = RED;

		K key;
		V value;

		/**
		 * 存储key对应的hashCode
		 */
		int hash;

		Node<K, V> left;
		Node<K, V> right;
		Node<K, V> parent;

		public Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			int hash = key == null ? 0 : key.hashCode();
			// 扰动计算
			this.hash = hash ^ (hash >>> 16); // 无符号右移16位，JDK里面的做法
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

		@Override
		public String toString() {
			return "Node_" + key + "," + value;
		}
	}

}
