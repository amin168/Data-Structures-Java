package com.amin;

import com.amin.printer.BinaryTrees;
import com.amin.tree.AVLTree;
import com.amin.tree.BinarySearchTree;
import com.amin.tree.BinaryTree;
import com.amin.tree.RBTree;

public class Main {
    public static void main(String[] args) {
        test4();
    }

    static void test4() {
        Integer data[] = new Integer[]{
                41, 38, 28, 43, 92, 7, 80, 46, 16, 93, 24, 58, 37, 15
        };

        RBTree<Integer> tree = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
            tree.add(data[i]);
        }

        BinaryTrees.println(tree);

        for (int i = 0; i < data.length; i++) {
            tree.remove(data[i]);
            System.out.println("----------------------------------");
            System.out.println("【" + data[i] + "】");
            BinaryTrees.println(tree);
        }
    }

    static void test3() {
        Integer data[] = new Integer[]{
                41, 38, 28, 43, 92, 7, 80, 46, 16, 93, 24, 58, 37, 15
        };

        RBTree<Integer> tree = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
            tree.add(data[i]);
        }

        BinaryTrees.println(tree);
    }

    static void test2() {
        Integer data[] = new Integer[]{
                99, 69, 14, 78, 53, 79, 26, 64, 4, 6, 91, 96, 74, 24, 19, 30, 40
        };

        AVLTree<Integer> tree = new AVLTree<>();
        for (int i = 0; i < data.length; i++) {
            tree.add(data[i]);
        }

        BinaryTrees.println(tree);
    }

    static void test1() {
        Integer data[] = new Integer[]{
                7, 4, 2, 1, 3, 5, 9, 8, 11, 10, 12
        };

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        bst.preorder(new BinaryTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return false;
            }
        });

        System.out.println();

        bst.inorder(new BinaryTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return false;
            }
        });

        System.out.println();

        bst.postorder(new BinaryTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return false;
            }
        });

        System.out.println();

        bst.levelOrder(new BinaryTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + " ");
                return false;
            }
        });

        System.out.println();

        System.out.println(bst.height());

        BinaryTrees.println(bst);

        bst.remove(1);
        bst.remove(3);
        bst.remove(10);
        bst.remove(12);

        BinaryTrees.println(bst);
    }
}
