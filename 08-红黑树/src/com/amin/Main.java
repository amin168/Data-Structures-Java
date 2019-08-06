package com.amin;

import com.amin.printer.BinaryTrees;
import com.amin.tree.AVL;
import com.amin.tree.BST;
import com.amin.tree.RBTree;

public class Main {
	public static void main(String[] args) {
		testRemove();
		System.out.println("\n");
	}

	static void testAdd() {
		Integer data[] = new Integer[] { 60, 26, 72, 94, 44, 1, 17, 4 };

		RBTree<Integer> rb = new RBTree<Integer>();
		for (int i = 0; i < data.length; i++) {
			rb.add(data[i]);
			System.out.println(data[i] + "--------------------------------");
			BinaryTrees.println(rb);

		}

		BinaryTrees.println(rb);
	}

	static void testRemove() {
		Integer data[] = new Integer[] { 55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50 };

		RBTree<Integer> rb = new RBTree<Integer>();
		for (int i = 0; i < data.length; i++) {
			rb.add(data[i]);
		}

		BinaryTrees.println(rb);
		System.out.println("-----------------------------------");
		for (int i = 0; i < data.length; i++) {
			rb.remove(data[i]);
			System.out.println("【" + data[i] + "】");
			BinaryTrees.println(rb);
			System.out.println("-----------------------------------");
		}
	}
}
