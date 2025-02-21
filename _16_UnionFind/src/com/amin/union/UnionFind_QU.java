package com.amin.union;

/**
 * Quick Union 实现
 */
public class UnionFind_QU extends UnionFind {

    public UnionFind_QU(int capacity) {
        super(capacity);
    }

    /**
     * 找根节点， 父节点 就是根节点
     */
    @Override
    public int find(int v) {
        rangeCheck(v);

        while (v != parents[v]) {
            v = parents[v];
        }

        return v;
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);

        if(p1 == p2) return;

        parents[p1] = p2;
    }
}
