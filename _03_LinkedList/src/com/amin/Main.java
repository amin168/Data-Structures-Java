package com.amin;

import com.amin.circle.CircleLinkedList;
import com.amin.circle.SingleCircleLinkedList;
import com.amin.single.SingleLinkedList;
import com.amin.single.SingleLinkedListWithDummyHead;

public class Main {
    static void testList(List<Integer> list) {
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(44);
        System.out.println(list);

        list.add(0, 55); // [55, 11, 22, 33, 44]
        System.out.println(list);

        list.add(2, 66); // [55, 11, 66, 22, 33, 44]
        System.out.println(list);

        list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]
        System.out.println(list);

        list.remove(0); // [11, 66, 22, 33, 44, 77]
        System.out.println(list);

        list.remove(2); // [11, 66, 33, 44, 77]
        System.out.println(list);

        list.remove(list.size() - 1); // [11, 66, 33, 44]
        System.out.println(list);

//        for (int i = 0; i < 5; i++) {
//            list.add(0, i + 1);
//        }
//
//        System.out.println(list);
//
        list.add(2, 666);
        System.out.println(list);

        list.remove(2);
        System.out.println(list);

        list.remove(0);
        System.out.println(list);

        list.remove(list.size() - 1);
        System.out.println(list);

    }

    static void josephus() {
        CircleLinkedList<Integer> list = new CircleLinkedList<>();
        for (int i = 1; i < 9; i++) {
            list.add(i);
        }

        System.out.println(list);

        // 指向头节点(指向1)
        list.reset();

        while (!list.isEmpty()) {
            // 数3次
            list.next();
            list.next();
            System.out.println(list.remove());
        }

    }

    public static void main(String[] args) {
        josephus();
//        testList(new SingleLinkedList<Integer>());
//        testList(new SingleLinkedListWithDummyHead<Integer>());
//        testList(new LinkedList<Integer>());
//        testList(new SingleCircleLinkedList<Integer>());
//        testList(new CircleLinkedList<Integer>());

        // System.out.println(2 >> 1);
    }
}
