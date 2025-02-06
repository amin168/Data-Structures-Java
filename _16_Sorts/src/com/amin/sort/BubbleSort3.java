package com.amin.sort;

public class BubbleSort3<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            // 如果序列尾部已经局部有序，可以记录最后一次交换的位置，减少比较次数

            // sortIndex 的初始值在数组完全有序的时候有用
            int sortIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(begin, begin - 1) < 0) {
                    swap(begin, begin - 1);

                    sortIndex = begin;
                }
            }

            end = sortIndex;
        }

    }
}
