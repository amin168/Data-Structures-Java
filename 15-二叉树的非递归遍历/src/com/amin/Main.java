package com.amin;

import com.amin.printer.BinaryTrees;
import com.amin.tree.BST;
import com.amin.tree.BinaryTree.Visitor;

public class Main {

	public static void main(String[] args) {

		Integer data[] = new Integer[] { 7, 4, 9, 2, 5, 8, 11 };
		BST<Integer> bst = new BST<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}

		BinaryTrees.println(bst);

		StringBuilder sb = new StringBuilder();
		Visitor<Integer> visitor = new Visitor<Integer>() {
			@Override
			public boolean visit(Integer element) {
				sb.append(element).append(" ");
				return false;
			}
		};

		sb.delete(0, sb.length());
		bst.preorder(visitor);
		Asserts.test(sb.toString().equals("7 4 2 5 9 8 11 "));

		sb.delete(0, sb.length());
		bst.inorder(visitor);
		Asserts.test(sb.toString().equals("2 4 5 7 8 9 11 "));

		sb.delete(0, sb.length());
		bst.postorder(visitor);
		Asserts.test(sb.toString().equals("2 5 4 8 11 9 7 "));
	}

}
