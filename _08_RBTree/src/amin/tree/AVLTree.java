package amin.tree;

import java.util.Comparator;

public class AVLTree<E> extends BalanceBinarySearchTree<E> {
    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                updateHeight(node);
            } else {
                // 恢复平衡
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
                updateHeight(node);
            } else {
                // 恢复平衡
                rebalance(node);
            }
        }
    }

    /**
     * 恢复平衡
     *
     * @param grand 高度最低的那一个不平衡节点
     */
    private void  rebalance(Node<E> grand) {
        // 求出左右子树，高度比较高的那一个
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> node = ((AVLNode<E>) parent).tallerChild();

        if (parent.isLeftChild()) {
            if (node.isLeftChild()) { // LL
                // 对 G 进行右旋转
                rotateRight(grand);
            } else { // LR
                // 先对 P 进行左旋转
                rotateLeft(parent);
                // 再对 G 进行右旋转
                rotateRight(grand);
            }
        } else {
            if (node.isLeftChild()) { // RL
                // 先对 P 进行右旋转
                rotateRight(parent);
                // 再对 G 进行左旋转
                rotateLeft(grand);
            } else { // RR
                // 对 G 进行左旋转
                rotateLeft(grand);
            }
        }

    }


    @Override
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotate(grand, parent, child);

        // 先更新比较矮，再更新比较高的
        updateHeight(grand);
        updateHeight(parent);
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }

    private boolean isBalanced(Node<E> node) {
        AVLNode<E> avlNode = (AVLNode<E>) node;
        return Math.abs(avlNode.balanceFactor()) <= 1;
    }

    private void updateHeight(Node<E> node) {
        AVLNode<E> avlNode = (AVLNode<E>) node;
        avlNode.updateHeight();
    }

    private static class AVLNode<E> extends Node<E> {
        int height = 1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;

            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            if (leftHeight > rightHeight) return left;
            if (leftHeight < rightHeight) return right;
            return isLeftChild() ? left : right;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")_h(" + height + ")";
        }
    }


}
