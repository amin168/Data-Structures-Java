package com.amin.union;

import java.util.Arrays;

/**
 * Quick Union 基于 Rank 的优化：矮的树，嫁接到高的树
 */
public class UnionFind_QU_Rank extends UnionFind_QU {

    private final int[] ranks;

    public UnionFind_QU_Rank(int capacity) {
        super(capacity);

        ranks = new int[capacity];
        Arrays.fill(ranks, 1);
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);

        if (p1 == p2) return;

        if (ranks[p1] < ranks[p2]) {
            parents[p1] = p2;
        } else if (ranks[p1] > ranks[p2]) {
            parents[p2] = p1;
        } else {
            // 如果相同，那一边嫁接都可以
            parents[p1] = p2;
            ranks[p2] += 1;
        }

    }
}
