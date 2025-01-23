package com.amin;

import com.amin.queue.PriorityQueue;

public class Main {
    public static void main(String[] args) {

        PriorityQueue queue = new PriorityQueue();
        queue.enQueue(88);
        queue.enQueue(44);
        queue.enQueue(53);
        queue.enQueue(41);
        queue.enQueue(16);

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }
}