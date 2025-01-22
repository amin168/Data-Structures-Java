package com.amin;

import com.amin.printer.BinaryTrees;
import com.amin.tree.BinarySearchTree;
import com.amin.tree.BinaryTree;

public class Main {
    public static void main(String[] args) {
        test1();
    }

    static void test1() {
        Integer data[] = new Integer[]{
                5, 3, 4, 1, 2, 8, 9, 6, 7
        };

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
//        bst.preorder(new BinaryTree.Visitor<Integer>() {
//            @Override
//            public boolean visit(Integer element) {
//                System.out.print(element + " ");
//                return false;
//            }
//        });
//
//        System.out.println();
//
//        bst.inorder(new BinaryTree.Visitor<Integer>() {
//            @Override
//            public boolean visit(Integer element) {
//                System.out.print(element + " ");
//                return false;
//            }
//        });
//
//        System.out.println();
//
//        bst.postorder(new BinaryTree.Visitor<Integer>() {
//            @Override
//            public boolean visit(Integer element) {
//                System.out.print(element + " ");
//                return false;
//            }
//        });
//
//        System.out.println();
//
//        bst.levelOrder(new BinaryTree.Visitor<Integer>() {
//            @Override
//            public boolean visit(Integer element) {
//                System.out.print(element + " ");
//                return false;
//            }
//        });
//
//        System.out.println();
//
//        System.out.println(bst.height());
//
//        BinaryTrees.println(bst);
//
        BinaryTrees.println(bst);

        bst.remove(5);
//        bst.remove(3);
//        bst.remove(10);
//        bst.remove(12);

        BinaryTrees.println(bst);
    }
}
