package com.amin.tree;

import com.amin.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree<E> implements BinaryTreeInfo {

    protected int size;
    protected Node<E> root;

    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }
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

    public static abstract class Visitor<E> {
        boolean stop;

        /**
         * @return 如果返回true，就代表停止遍历
         */
        public abstract boolean visit(E element);
    }

    // 前序遍历-非递归
    public void preorder(Visitor<E> visitor) {
        if (visitor == null || root == null) return;

        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node<E> node = stack.pop();
            if (visitor.visit(node.element)) return;

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

    }

    // 前序遍历-非递归
    public void preorder2(Visitor<E> visitor) {
        if (visitor == null || root == null) return;

        Node<E> node = root;
        Stack<Node> stack = new Stack<>();
        while (true) {
            if (node != null) {
                if (visitor.visit(node.element)) return;
                // 将右子节点入栈
                if (node.right != null) {
                    stack.push(node.right);
                }

                // 向左走
                node = node.left;
            } else if (stack.isEmpty()) {
                return;
            } else {
                node = stack.pop();
            }
        }

    }

    public void inorder(Visitor<E> visitor) {
        if (visitor == null || root == null) return;

        Node<E> node = root;
        Stack<Node> stack = new Stack<>();

        while (true) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else if (stack.isEmpty()) {
                return;
            } else { // 如果不能再左了
                node = stack.pop();
                if (visitor.visit(node.element)) return;

                // 让右节点进行中序遍历
                node = node.right;
            }
        }

    }

    public void postorder(Visitor<E> visitor) {
        if (visitor == null || root == null) return;

        // 记录上一次弹出访问的节点
        Node<E> prevNode = null;
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node<E> top = stack.peek();

            if (top.isLeaf() || (prevNode != null && prevNode.parent == top)) {
                prevNode = stack.pop();
                if (visitor.visit(prevNode.element)) return;
            } else {
                if (top.right != null) {
                    stack.push(top.right);
                }
                if (top.left != null) {
                    stack.push(top.left);
                }
            }
        }
    }

    public void levelOrder(Visitor<E> visitor) {
        if (root == null || visitor == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (visitor.visit(node.element)) return;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }

    }

    /**
     * 找到当前节点的前驱节点
     *
     * @param node
     * @return
     */
    protected Node<E> predecessor(Node<E> node) {
        if (node == null) return null;

        // 左子树中最右的节点
        Node<E> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }

            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        // 终止条件：node 在 parent 的右子树中
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        // 能来到这里，意味下面两个条件成立
        // node.parent == null
        // node == node.parent.right
        return node.parent;
    }

    /**
     * 找到当前节点的后继节点
     *
     * @param node
     * @return
     */
    protected Node<E> successor(Node<E> node) {
        if (node == null) return null;

        Node<E> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }

            return p;
        }

        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

    public boolean isComplete() {
        if (root == null) return false;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        boolean isLeaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();

            if (isLeaf && !node.isLeaf())
                return false;

            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                // node.left == null
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            } else {
                // node.right = null
                isLeaf = true;
            }
        }

        return true;
    }

    public boolean isComplete2() {
        if (root == null) return false;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();

            // 如果要求的是叶子节点(leaf == true)
            // 但 node 不是叶子节点
            if (leaf && !node.isLeaf())
                return false;

            if (node.left != null && node.right != null) {
                queue.offer(node.left);
                queue.offer(node.right);
            } else if (node.left == null && node.right != null) {
                return false;
            } else { // 后面遍历的节点都必须是叶子节点
                leaf = true;
                if (node.left != null) {
                    queue.offer(node.left);
                }
            }
        }

        return true;
    }

    public int height() {
        if (root == null) return 0;

        int height = 0;

        // 存储着每一层的元素数量
        int levelSize = 1;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();

            // 每次出队，每一层的元素要减1
            levelSize--;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }

            // 意味着即将要访问下一层
            if (levelSize == 0) {
                levelSize = queue.size();
                height++;
            }
        }

        return height;
    }

    private int height2(Node<E> node) {
        if (node == null) return 0;

        // 等于左右节点最大的那个+1
        return 1 + Math.max(height2(node.left), height2(node.right));
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> myNode = (Node<E>) node;
        String parentString = "null";
        if (myNode.parent != null) {
            parentString = myNode.parent.element.toString();
        }
        return myNode.element + "_p(" + parentString + ")";
    }
}
