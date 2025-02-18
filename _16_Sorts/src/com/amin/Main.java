package com.amin;

import com.amin.sort.*;
import com.amin.tools.Asserts;
import com.amin.tools.Integers;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        Integer[] array = {7, 3, 5, 8, 6, 7, 4, 5};
//        Integer[] array = Integers.tailAscOrder(1,10, 5);

        Integer[] array = Integers.random(30000, 1, 30000);

        testSorts(array,
//                new BubbleSort1(),
//                new BubbleSort2(),
//                new BubbleSort3(),
                new SelectionSort(),
                new HeapSort(),
//                new InsertionSort1(),
//                new InsertionSort2(),
//                new InsertionSort3(),
                new MergeSort(),
                new QuickSort(),
                new ShellSort());
    }

    static void testSorts(Integer[] array, Sort... sorts) {
        for (Sort sort : sorts) {
            Integer[] newArray = Integers.copy(array);
            sort.sort(newArray);
            Asserts.test(Integers.isAscOrder(newArray));
        }
        Arrays.sort(sorts);

        for (Sort sort : sorts) {
            System.out.println(sort);
        }
    }
}