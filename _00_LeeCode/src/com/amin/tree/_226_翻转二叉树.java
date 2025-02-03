package com.amin.tree;

import java.util.LinkedList;
import java.util.Queue;

public class _226_翻转二叉树 {

    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return root;
    }

//    /**
//     * 后序遍历
//     * @param node
//     * @return
//     */
//    public TreeNode invertTree(TreeNode node) {
//        if (node == null) return null;
//
//        invertTree(node.left);
//        invertTree(node.right);
//
//        var temp = node.left;
//        node.left = node.right;
//        node.right = temp;
//
//        return node;
//    }


//    /**
//     * 中序遍历
//     * @param node
//     * @return
//     */
//    public TreeNode invertTree(TreeNode node) {
//        if (node == null) return null;
//
//        invertTree(node.left);
//
//        var temp = node.left;
//        node.left = node.right;
//        node.right = temp;
//
//        invertTree(node.left);
//
//        return node;
//    }


//    /**
//     * 前序遍历
//     * @param node
//     * @return
//     */
//    public TreeNode invertTree(TreeNode node) {
//        if (node == null) return null;
//
//        var temp = node.left;
//        node.left = node.right;
//        node.right = temp;
//
//        invertTree(node.left);
//        invertTree(node.right);
//
//        return node;
//    }


}
