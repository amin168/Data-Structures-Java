package com.amin;

import java.util.Comparator;

import com.amin.heap.BinaryHeap;
import com.amin.printer.BinaryTrees;

public class Main {

	static void test1() {
		// 3, 92, 97, 27, 91, 28, 81, 76, 95
		int[] data = { 3, 92, 97, 27, 91, 28, 81, 76, 95 };
		BinaryHeap<Integer> heap = new BinaryHeap<Integer>();
		for (int i = 0; i < data.length; i++) {
			heap.add(data[i]);
		}
		BinaryTrees.println(heap);

		System.out.println("-------------------");
		heap.replace(-1);

		BinaryTrees.println(heap);
	}

	static void test2() {
		// 3, 92, 97, 27, 91, 28, 81, 76, 95
		Integer[] data = { 3, 92, 97, 27, 91, 28, 81, 76, 95 };
		BinaryHeap<Integer> heap = new BinaryHeap<>(data);
		BinaryTrees.println(heap);
	}

	/**
	 * TopK面试题
	 */
	static void TopK() {
		// 新建小顶堆
		BinaryHeap<Integer> heap = new BinaryHeap<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		});

		// 找出最大的前K个数
		int k = 3;
		Integer[] data = { 32, 48, 37, 20, 62, 22, 38, 89, 26, 79, 100, 7, 43, 65, 9, 60, 68, 4, 54, 50 };
		for (int i = 0; i < data.length; i++) {
			if (heap.size() < k) { // 前k个数添加到小顶堆
				heap.add(data[i]); // logk
			} else if (data[i] > heap.get()) {
				// 如果是第k+1个数，并且大于大顶堆顶元素
				heap.replace(data[i]); // logk
			}
		}

		BinaryTrees.print(heap);

	}

	public static void main(String[] args) {
		TopK();
	}

}
