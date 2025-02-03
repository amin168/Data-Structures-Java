package com.amin.tree;

import java.util.Comparator;

/**
 * 二叉搜索树
 * @param <E>
 */
public class BinarySearchTree<E> extends BinaryTree<E> {

    private Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void add(E element) {
        elementNotNullCheck(element);

        // 添加第一个节点
        if (root == null) {
            root = new Node<>(element, null);
            size++;
            return;
        }

        // 找到父节点
        Node<E> parent = root;
        Node<E> node = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node.element = element;
                return;
            }
        }

        // 判断插入到父节点的哪一个位置
        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
    }

    public void remove(E element) {
        remove(node(element));
    }

    private void remove(Node<E> node) {
        if (node == null) return;

        size--;

        // 删除度为2的节点
        if (node.hasTwoChildren()) {
            // 找到后继节点
            Node<E> s = successor(node);
            // 用后继节点的值覆盖度为2节点的值
            node.element = s.element;
            // 删除后继节点
            // 这里覆盖后，就会再下面的判断中删除node节点
            node = s;
        }

        // 删除node节点，node节点必然是度为1或者0
        Node<E> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) {
            // node节点是度为1的节点
            replacement.parent = node.parent;

            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1节点并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
        } else if (node.parent == null) { // node是叶子节点，并且是根节点
            root = null;
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }
    }

    public boolean contains(E element) {
        return node(element) != null;
    }

    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(element, node.element);
            if (cmp == 0) {
                return node;
            }

            if (cmp > 0) {
                node = node.right;
            } else {
                node = node.left;
            }

        }
        return null;
    }

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

}
