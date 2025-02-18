package com.amin.sort;

public class QuickSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        sort(0, array.length);
    }

    /**
     * 对 [begin, end) 范围的元素进行快速排序
     *
     * @param begin
     * @param end
     */
    private void sort(int begin, int end) {
        if (end - begin < 2) return;

        // 确定轴点位置
        int mid = pivotIndex(begin, end);
        sort(begin, mid);
        sort(mid + 1, end);
    }

    /**
     * 构造出 [begin, end) 范围的轴点元素
     *
     * @param begin
     * @param end
     * @return
     */
    private int pivotIndex(int begin, int end) {
        // 随机选一个元素跟 begin 位置交换
        // 这是为了降低最坏情况
        swap(begin, begin + (int)(Math.random() * (end - begin)));

        // Math.random() -> [0, 1)
        // Math.random() * (end - begin) -> [0, end - begin)
        // begin + Math.random() * (end - begin) -> [0 + begin, end - begin + begin)

        // 备份 begin 位置的元素
        T pivot = array[begin];

        // 指向最后一个元素
        end--;

        while (begin < end) {
            while (begin < end) {
                if (cmp(pivot, array[end]) < 0) { // 右边元素 > 轴点元素
                    end--;
                } else { // 右边元素 <= 轴点元素
                    array[begin++] = array[end];
                    break;
                }
            }

            while (begin < end) {
                if (cmp(pivot, array[begin]) > 0) { // 左边元素 > 轴点元素
                    begin++;
                } else { // 左边元素 >= 轴点元素
                    array[end--] = array[begin];
                    break;
                }
            }
        }

        // 将轴点元素放入最终位置
        array[begin] = pivot;

        // 返回轴点元素的位置
        return begin;
    }
}
