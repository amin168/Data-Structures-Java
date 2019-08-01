package com.amin;

import com.amin.printer.BinaryTrees;
import com.amin.tree.AVL;
import com.amin.tree.BST;

public class Main {
	public static void main(String[] args) {
		testAdd();
		System.out.println("\n");

		// testTraversal();
		// System.out.println("\n");

		// testTraversalWithStop();
		// System.out.println("\n");

	}

	static void testAdd() {
		// 如果要构建一棵完全二叉树，就按照层序遍历的顺序添加
		// Integer data[] = new Integer[] { 7, 4, 9, 2, 1};

		Integer data[] = new Integer[] { 78, 83, 44, 20, 88, 62, 2, 5, 89, 39, 63, 51, 25 };

		AVL<Integer> avl = new AVL<Integer>();
		for (int i = 0; i < data.length; i++) {
			avl.add(data[i]);
		}

		BinaryTrees.println(avl);
		
		avl.remove(2);
		avl.remove(5);
		System.out.println("------------------------------------------------");
		
		BinaryTrees.println(avl);
	}
}
