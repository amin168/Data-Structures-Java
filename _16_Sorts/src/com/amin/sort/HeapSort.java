package com.amin.sort;

public class HeapSort<T extends Comparable<T>> extends Sort<T> {
    private int heapSize;


    @Override
    protected void sort() {
        heapSize = array.length;

        // 原地建堆
        for (int i = (heapSize >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }

        while (heapSize > 1) {
            // 交换堆顶元素和尾部元素
            swap(0, --heapSize);

            // 对 0 位置进行 下滤
            siftDown(0);

            // swap(0, heapSize - 1);
            // heapSize--;

        }
    }


    /**
     * 让 index 位置的元素下滤
     *
     * @param index
     */
    private void siftDown(int index) {
        T element = array[index];
        int half = heapSize >> 1;

        // 第一个叶子节点的索引 == 非叶子节点的数量
        // index < 第一个叶子节点的索引
        // 非叶子节点的数量：floor (n/2)
        // 必须保证 index 位置是非叶子节点，因为这样才能有节点去交换
        while (index < half) {
            // index 的节点有2种情况
            // 1. 只有左子节点
            // 2. 有左右子节点

            // 默认为左子节点
            int childIndex = (index << 1) + 1;
            T child = array[childIndex];

            // 右子节点
            int rightIndex = childIndex + 1;

            // 选出左右子节点最大的那个
            // rightIndex < size 证明索引在数组的合理范围之内
            if (rightIndex < heapSize && cmp(array[rightIndex], child) > 0) {
                child = array[childIndex = rightIndex];
            }

            if (cmp(element, child) >= 0) break;

            // 将子节点存放到 index 位置
            array[index] = child;

            // 重新设置 index
            index = childIndex;
        }
        array[index] = element;

    }

}
