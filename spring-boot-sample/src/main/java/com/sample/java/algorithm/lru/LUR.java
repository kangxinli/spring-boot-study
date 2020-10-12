package com.sample.java.algorithm.lru;

import java.util.HashMap;
import java.util.Iterator;

public class LUR<K, V> implements Iterable<K> {
    private Node head;
    private Node tail;
    private HashMap<K, Node> map;
    private int maxSize;

    private class Node {
        Node pre;
        Node next;
        K k;
        V v;

        public Node(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

    public LUR(int maxSize) {
        this.maxSize = maxSize;
        this.map = new HashMap<>(maxSize * 4 / 3);
        head = new Node(null, null);
        tail = new Node(null, null);
        head.next = tail;
        tail.pre = head;
    }

    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        Node node = map.get(key);
        unlink(node);
        appendHead(node);
        System.out.println("查询节点：" + node.k);
        return node.v;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            unlink(node);
        }
        Node node = new Node(key, value);
        map.put(key, node);
        appendHead(node);
        System.out.println("添加节点：" + node.k);
        if (map.size() > maxSize) {
            Node toRemove = removeTail();
            System.out.println("移除节点：" + toRemove.k);
            map.remove(toRemove.k);
        }
    }

    private Node removeTail() {
        Node node = tail.pre;
        Node pre = node.pre;
        tail.pre = pre;
        pre.next = tail;
        node.next = null;
        node.pre = null;
        return node;
    }

    private void appendHead(Node node) {
        Node next = head.next;
        node.next = next;
        next.pre = node;
        node.pre = head;
        head.next = node;
    }

    private void unlink(Node node) {
        Node pre = node.pre;
        Node next = node.next;
        pre.next = next;
        next.pre = pre;

        node.pre = null;
        node.next = null;
    }


    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private Node curr = head.next;

            @Override
            public boolean hasNext() {
                return curr != tail;
            }

            @Override
            public K next() {
                Node node = curr;
                curr = curr.next; //仅仅知识遍历。不删除
                return node.k;
            }
        };
    }
}


