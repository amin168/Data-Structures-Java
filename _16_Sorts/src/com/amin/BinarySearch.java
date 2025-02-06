package com.amin;

public class BinarySearch {

    /**
     * 查找 v 在有序数组中的位置
     *
     * @param array
     * @param v
     * @return
     */
    public static int indexOf(int[] array, int v) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (v < array[mid]) {
                end = mid;
            } else if (v >= array[mid]) {
                begin = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    /**
     * 查找v在有序数组array中待插入位置
     */
    public static int search(int[] array, int v) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (v < array[mid]) {
                end = mid;
            } else {
                // 这里包含了 v <= array[mid] 的情况
                // 因为我们是要找 v 的插入位置
                // 找到第一个大于 v 的位置

                begin = mid + 1;
            }
        }

        return begin;
    }

}
