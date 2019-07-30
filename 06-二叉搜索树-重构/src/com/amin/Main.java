package com.amin;

import com.amin.printer.BinaryTrees;
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
		//如果要构建一棵完全二叉树，就按照层序遍历的顺序添加
		//Integer data[] = new Integer[] { 7, 4, 9, 2, 1};
		
		Integer data[] = new Integer[] { 7, 4, 9, 2, 5, 8, 11, 3, 12, 1 };

		BST<Integer> bst = new BST<Integer>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		
//		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
//		for (int i = 0; i < 40; i++) {
//			bst.add((int)(Math.random() * 100));
//		}

		BinaryTrees.print(bst);

		System.out.println("\n");
//		System.out.println("height:" + bst.height());
//		System.out.println("是否为完成二叉树:" + bst.isComplete());

		bst.remove(9);
		
		BinaryTrees.print(bst);
		
		bst.remove(7);
		
		System.out.println("\n");
		BinaryTrees.print(bst);
	}
}
