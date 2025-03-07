package com.amin;

import java.util.HashMap;

public class Trie<V> {
    private Node<V> root;

    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public V get(String key) {
        Node<V> node = node(key);
        return node != null && node.word ? node.value : null;
    }

    public V add(String key, V value) {
        keyCheck(key);

        if (root == null) {
            root = new Node<>(null);
        }

        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            char c = key.charAt(i);
            boolean emptyChildren = node.children == null;
            Node<V> childNode = emptyChildren ? null : node.children.get(c);
            if (childNode == null) {
                childNode = new Node<>(node);
                childNode.character = c;
                node.children = emptyChildren ? new HashMap<>() : node.children;
                node.children.put(c, childNode);
            }
            node = childNode;
        }


        if (node.word) {
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        node.word = true;
        node.value = value;
        size++;

        return null;
    }


    public V remove(String key) {
        // 找到最后一个节点
        Node<V> node = node(key);

        // 如果不是单词结尾，不用做任何处理
        if (node == null || !node.word) return null;
        size--;
        V oldValue = node.value;

        // 如果还有子节点
        if (node.children != null && !node.children.isEmpty()) {
            node.word = false;
            node.value = null;
            return oldValue;
        }

        // 如果没有子节点
        Node<V> parent = null;
        while ((parent = node.parent) != null) {
            parent.children.remove(node.character);
            if (parent.word || !parent.children.isEmpty()) break;
            node = parent;
        }


        return oldValue;
    }

    public boolean startsWith(String prefix) {
        return node(prefix) != null;
    }

    private Node<V> node(String key) {
        keyCheck(key);

        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            if (node == null || node.children == null || node.children.isEmpty()) return null;
            char c = key.charAt(i);
            node = node.children.get(c);
        }

        return node;
    }


    public void keyCheck(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }


    private static class Node<V> {
        Node<V> parent;

        HashMap<Character, Node<V>> children;

        Character character;

        V value;

        boolean word;

        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }
}
