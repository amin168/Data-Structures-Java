package 二叉树;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/invert-binary-tree/
 */
public class _226_翻转二叉树 {

	// 利用先序遍历
//	public TreeNode invertTree(TreeNode root) {
//        if (root == null) return root;
//        
//        TreeNode node = root.left;
//        root.left = root.right;
//        root.right = node;
//        
//        invertTree(root.left);
//        invertTree(root.right);
//        
//        return root;
//        
//    }

	// 利用中序遍历
//	public TreeNode invertTree(TreeNode root) {
//		if (root == null)
//			return root;
//
//		invertTree(root.left);
//		
//		TreeNode node = root.left;
//		root.left = root.right;
//		root.right = node;
//	
//		invertTree(root.left);
//
//		return root;
//	}

	// 利用后序遍历
//	public TreeNode invertTree(TreeNode root) {
//		if (root == null)
//			return root;
//
//		invertTree(root.left);
//		invertTree(root.right);
//		
//		TreeNode node = root.left;
//		root.left = root.right;
//		root.right = node;
//
//		return root;
//	}

	// 利用层序遍历
	public TreeNode invertTree(TreeNode root) {
		if (root == null)
			return root;

		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			TreeNode temp = node.left;
			node.left = node.right;
			node.right = temp;

			if (node.left != null)
				queue.offer(node.left);

			if (node.right != null)
				queue.offer(node.right);
		}
		return root;
	}

}
