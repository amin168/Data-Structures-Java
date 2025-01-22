package com.amin.tree;

import java.util.Comparator;

public class RBTree<E> extends BalanceBinarySearchTree<E> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null) {
            // 因为默认添加的节点是红色，所以需要染黑
            black(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) {
            return;
        }

        // 叔父节点
        Node<E> uncle = parent.sibling();
        // 祖父节点
        Node<E> grand = parent.parent;

        if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
            black(parent);
            black(uncle);

            //把祖父节点当做新添加的节点
            afterAdd(red(grand));
            return;
        }

        // 叔父节点不是红色
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent);
                red(grand);
                rotateRight(grand);
            } else { //LR
                black(node);
                red(grand);
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else {
            if (node.isLeftChild()) { // RL
                black(node);
                red(grand);
                rotateRight(parent);
                rotateLeft(grand);
            } else { // RR
                black(parent);
                red(grand);
                rotateLeft(grand);
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        // 如果删除的节点是红色
//        if (isRed(node)) {
//            return;
//        }

        // 如果删除的节点是红色
        // 或者 用于取代node的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<E> parent = node.parent;
        // 删除的是根节点
        if (parent == null) return;

        // 删除的是黑色叶子节点，也有可能是底下 afterRemove(parent, null) 【下溢】过来的代码
        // 判断被删除的 node 节点是左还是右
        // 因为 afterRemove 之前，已经将 node.parent.left 或 node.parent.right 清空了
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在父节点左边，兄弟节点在右边

            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色节点， 父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);

                // 可能导致父节点下溢，把 parent 当做被删除的节点处理即可
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点必然至少有一个红色子节点，向兄弟借元素
                // 兄弟节点的左边是黑色，兄弟要先左旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);

                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }

        } else { // 被删除的节点在右父节点边，兄弟节点在左边

            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色节点， 父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);

                // 可能导致父节点下溢，把 parent 当做被删除的节点处理即可
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点必然至少有一个红色子节点，向兄弟借元素
                // 兄弟节点的左边是黑色，兄弟要先左旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);

                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }
//    protected void afterRemove(Node<E> node, Node<E> replacement) {
//        // 如果删除的节点是红色
//        if (isRed(node)) {
//            return;
//        }
//
//        // 用于取代node的子节点是红色
//        if (isRed(replacement)) {
//            black(replacement);
//            return;
//        }
//
//        Node<E> parent = node.parent;
//        // 删除的是根节点
//        if (parent == null) return;
//
//        // 删除的是黑色叶子节点，也有可能是底下 afterRemove(parent, null) 【下溢】过来的代码
//        // 判断被删除的 node 节点是左还是右
//        // 因为 afterRemove 之前，已经将 node.parent.left 或 node.parent.right 清空了
//        boolean left = parent.left == null || node.isLeftChild();
//        Node<E> sibling = left ? parent.right : parent.left;
//        if (left) { // 被删除的节点在父节点左边，兄弟节点在右边
//
//            if (isRed(sibling)) { // 兄弟节点是红色
//                black(sibling);
//                red(parent);
//                rotateLeft(parent);
//                // 更换兄弟
//                sibling = parent.right;
//            }
//
//            // 兄弟节点必然是黑色
//            if (isBlack(sibling.left) && isBlack(sibling.right)) {
//                // 兄弟节点没有1个红色节点， 父节点要向下跟兄弟节点合并
//                boolean parentBlack = isBlack(parent);
//                black(parent);
//                red(sibling);
//
//                // 可能导致父节点下溢，把 parent 当做被删除的节点处理即可
//                if (parentBlack) {
//                    afterRemove(parent, null);
//                }
//            } else { // 兄弟节点必然至少有一个红色子节点，向兄弟借元素
//                // 兄弟节点的左边是黑色，兄弟要先左旋转
//                if (isBlack(sibling.right)) {
//                    rotateRight(sibling);
//
//                    sibling = parent.right;
//                }
//
//                color(sibling, colorOf(parent));
//                black(sibling.right);
//                black(parent);
//                rotateLeft(parent);
//            }
//
//        } else { // 被删除的节点在右父节点边，兄弟节点在左边
//
//            if (isRed(sibling)) { // 兄弟节点是红色
//                black(sibling);
//                red(parent);
//                rotateRight(parent);
//                // 更换兄弟
//                sibling = parent.left;
//            }
//
//            // 兄弟节点必然是黑色
//            if (isBlack(sibling.left) && isBlack(sibling.right)) {
//                // 兄弟节点没有1个红色节点， 父节点要向下跟兄弟节点合并
//                boolean parentBlack = isBlack(parent);
//                black(parent);
//                red(sibling);
//
//                // 可能导致父节点下溢，把 parent 当做被删除的节点处理即可
//                if (parentBlack) {
//                    afterRemove(parent, null);
//                }
//            } else { // 兄弟节点必然至少有一个红色子节点，向兄弟借元素
//                // 兄弟节点的左边是黑色，兄弟要先左旋转
//                if (isBlack(sibling.left)) {
//                    rotateLeft(sibling);
//
//                    sibling = parent.left;
//                }
//
//                color(sibling, colorOf(parent));
//                black(sibling.left);
//                black(parent);
//                rotateRight(parent);
//            }
//        }
//    }

    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return null;
        ((RBNode<E>) node).color = color;
        return node;
    }

    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<E>(element, parent);
    }

    private static class RBNode<E> extends Node<E> {

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
