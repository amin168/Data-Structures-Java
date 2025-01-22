package amin.tree;

import java.util.Comparator;

public class BalanceBinarySearchTree<E> extends BinarySearchTree<E> {

    public BalanceBinarySearchTree() {
        this(null);
    }

    public BalanceBinarySearchTree(Comparator<E> comparator) {
        super(comparator);
    }

    protected void rotateLeft(BinaryTree.Node<E> grand) {
        BinaryTree.Node<E> parent = grand.right;
        BinaryTree.Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;

        // 维护 parent 和 height
        afterRotate(grand, parent, child);
    }

    protected void rotateRight(BinaryTree.Node<E> grand) {
        BinaryTree.Node<E> parent = grand.left;
        BinaryTree.Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;

        // 维护 parent 和 height
        afterRotate(grand, parent, child);
    }

    protected void afterRotate(BinaryTree.Node<E> grand, BinaryTree.Node<E> parent, BinaryTree.Node<E> child) {
        // 让parent称为子树的根节点
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            // 父节点可能是 null，grand 也就是根节点
            root = parent;
        }

        // 更新 child 的 parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新 grand 的 parent
        parent.parent = grand.parent;
        grand.parent = parent;

    }

}
