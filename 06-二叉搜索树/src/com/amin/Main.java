package com.amin;

import com.amin.BinarySearchTree.Visitor;
import com.amin.printer.BinaryTrees;

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

		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
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

	static void testTraversal() {
		Integer data[] = new Integer[] { 7, 4, 9, 2, 5, 8, 11, 3, 12, 1 };

		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}

		bst.preorderTraversal();
		System.out.println("\n");

		bst.inorderTraversal();
		System.out.println("\n");

		bst.postorderTraversal();
		System.out.println("\n");

		bst.levelOrderTraversal();

	}

	static void testTraversalWithStop() {
		Integer data[] = new Integer[] { 7, 4, 9, 2, 5, 8, 11, 3, 12, 1 };

		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}

		bst.preorder(new Visitor<Integer>() {
			@Override
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 2 ? true : false;
			}
		});
		System.out.println("\n");

		bst.inorder(new Visitor<Integer>() {
			@Override
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 3 ? true : false;
			}
		});
		System.out.println("\n");

		bst.postorder(new Visitor<Integer>() {
			@Override
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 2 ? true : false;
			}
		});
		System.out.println("\n");

		bst.levelOrder(new Visitor<Integer>() {
			@Override
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 9 ? true : false;
			}
		});
	}
}
