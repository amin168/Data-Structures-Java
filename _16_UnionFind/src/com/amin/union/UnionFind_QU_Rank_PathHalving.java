package com.amin.union;

/**
 * Quick Union 基于 Rank 的优化：矮的树，嫁接到高的树
 * 在此基础上再利用路径减半：使路径上的每隔一个节点就指向其祖父节点
 */
public class UnionFind_QU_Rank_PathHalving extends UnionFind_QU_Rank {

    public UnionFind_QU_Rank_PathHalving(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);

        while (v != parents[v]) {
            parents[v] = parents[parents[v]];
            v = parents[v];
        }

        return v;
    }
}
