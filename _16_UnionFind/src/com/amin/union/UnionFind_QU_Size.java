package com.amin.union;

import java.util.Arrays;

/**
 * Quick Union 基于 size 的优化：元素少的树，嫁接到元素多的树
 */
public class UnionFind_QU_Size extends UnionFind_QU {

    private final int[] sizes;

    public UnionFind_QU_Size(int capacity) {
        super(capacity);

        sizes = new int[capacity];
        Arrays.fill(sizes, 1);
    }

    /**
     * 将 v1 的根节点 嫁接到 v2 的根节点上
     */
    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);

        if (p1 == p2) return;

        if (sizes[p1] < sizes[p2]) {
            parents[p1] = p2;
            sizes[p2] += sizes[p1];
        } else {
            parents[p2] = p1;
            sizes[p1] += sizes[p2];
        }
    }
}
