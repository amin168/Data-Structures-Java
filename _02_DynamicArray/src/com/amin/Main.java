package com.amin;

public class Main {
    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList();

//        list.add(1);
//        list.add(88);
//        list.add(3);
//        list.add(99);
//        list.add(5);
//        list.add(4);
//
//        list.add(list.size() - 1, 100);

        System.out.println(list);

        for (int i = 0; i < 50; i++) {
            list.add(i);
        }

        System.out.println(list);

        for (int i = 0; i < 50; i++) {
            list.remove(0);
        }

        System.out.println(list);
    }
}
