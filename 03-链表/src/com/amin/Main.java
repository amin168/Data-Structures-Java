package com.amin;

import com.amin.single.SingleLinkedList;
import com.amin.single.SingleLinkedListWithDummyHead;

public class Main {

	static void testList(List<Integer> list) {
//		list.add(11);
//		list.add(22);
//		list.add(33);
//		list.add(44);
//
//		list.add(0, 55); // [55, 11, 22, 33, 44]
//		list.add(2, 66); // [55, 11, 66, 22, 33, 44]
//		list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]
//
//		list.remove(0); // [11, 66, 22, 33, 44, 77]
//		list.remove(2); // [11, 66, 33, 44, 77]
//		list.remove(list.size() - 1); // [11, 66, 33, 44]

		for (int i = 0; i < 5; i++) {
			list.add(0, i + 1);
		}

		System.out.println(list);

//		list.add(2, 666);
//		System.out.println(list);
//
//		list.remove(2);
//		System.out.println(list);
//
//		list.remove(0);
//		System.out.println(list);
//
//		list.remove(list.size() - 1);
//		System.out.println(list);

	}

	public static void main(String[] args) {

		// testList(new ArrayList<Integer>());
		//testList(new SingleLinkedList<Integer>());
		//testList(new SingleLinkedListWithDummyHead<Integer>());
		testList(new LinkedList<Integer>());

	}
}
