package com.amin.union;

import java.util.Arrays;

/**
 * Quick Union 基于 Rank 的优化：矮的树，嫁接到高的树
 * 在此基础上再利用路径压缩：
 * 在 find 时使路径上的所有节点都指向根节点，从而降低树的高度
 */
public class UnionFind_QU_Rank_PathCompression extends UnionFind_QU_Rank {

    public UnionFind_QU_Rank_PathCompression(int capacity) {
        super(capacity);
    }


    @Override
    public int find(int v) {
        rangeCheck(v);

        // v == 1, parent[v] == 2
        if(parents[v] != v) {
            parents[v] = find(parents[v]);
        }

        return parents[v];
    }
}
