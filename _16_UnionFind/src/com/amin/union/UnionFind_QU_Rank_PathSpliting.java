package com.amin.union;

/**
 * Quick Union 基于 Rank 的优化：矮的树，嫁接到高的树
 * 在此基础上再利用路径分裂：使路径上的每个节点都指向其父节点
 */
public class UnionFind_QU_Rank_PathSpliting extends UnionFind_QU_Rank {

    public UnionFind_QU_Rank_PathSpliting(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);

        while (v != parents[v]) {
            int p = parents[v];
            parents[v] = parents[parents[v]];
            v = p;
        }

        return v;
    }
}
