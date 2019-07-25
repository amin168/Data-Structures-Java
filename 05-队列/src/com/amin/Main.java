package com.amin;

import com.amin.loop.LoopDeque;
import com.amin.loop.LoopQueue;
import com.amin.loop.LoopQueueWithoutWasting;
import com.amin.loop.LoopQueueWithTail;
import com.amin.loop.LoopQueueWithoutSize;;

public class Main {

	public static void main(String[] args) {
		 testQueue();
		// testDeque();
		//testCircleQueue();
		//testCircleDeque();
		//testLoopQueueWithTail();
		//testLoopQueueWithOutWasting();
		//testLoopQueueWithoutSize();
	}

	public static void testQueue() {
		LinkedQueue<Integer> q = new LinkedQueue<Integer>();
		q.enQueue(1);
		q.enQueue(2);
		q.enQueue(3);
		q.enQueue(4);

		System.out.println(q);
	}

	public static void testDeque() {
		Deque<Integer> q = new Deque<Integer>();
		for (int i = 0; i < 6; i++) {
			q.enQueueFront(i + 1);
			q.enQueueRear(i + 100);
			// 6 5 4 3 2 1 100 101 102 103 104 105
		}
		System.out.println(q);
	}
	
	public static void testCircleQueue() {
		LoopQueue q = new LoopQueue();
		for (int i = 0; i < 10; i++) {
			q.enQueue(i);
		}
		
		System.out.println(q);
		
		for (int i = 0; i < 5; i++) {
			q.deQueue();
		}
		
		System.out.println(q);
		
		for (int i = 15; i < 25; i++) {
			q.enQueue(i);
		}
		
		System.out.println(q);
	}
	
	public static void testCircleDeque() {
		LoopDeque<Integer> q = new LoopDeque<Integer>();
		
		// 头5 4 3 2 1  100 101 102 103 104 105 106 8 7 6 尾
		
		// 头 8 7 6  5 4 3 2 1  100 101 102 103 104 105 106 107 108 109 null null 10 9 尾
		for (int i = 0; i < 10; i++) {
			q.enQueueFront(i + 1);
			q.enQueueRear(i + 100);
		}
		
		System.out.println(q);
	}
	
	public static void testLoopQueueWithTail() {
		LoopQueueWithTail<Integer> queue = new LoopQueueWithTail<>();
		for(int i = 0 ; i < 10 ; i ++){
            queue.enQueue(i);
        }
		System.out.println(queue);
		
		for (int i = 0; i < 8; i++) {
			queue.deQueue();
		}
		
		System.out.println(queue);
	}
	
	public static void testLoopQueueWithOutWasting() {
		LoopQueueWithoutWasting<Integer> queue = new LoopQueueWithoutWasting<>();
//		for(int i = 0 ; i < 10 ; i ++){
//            queue.enQueue(i);
//        }
//		System.out.println(queue);
//		
//		for (int i = 0; i < 8; i++) {
//			queue.deQueue();
//		}
//		
		System.out.println(queue);
		
	}
	
	public static void testLoopQueueWithoutSize() {
		LoopQueueWithoutSize<Integer> queue = new LoopQueueWithoutSize<>();
		System.out.println(queue);
		
		for(int i = 0 ; i < 10 ; i ++){
            queue.enQueue(i);
        }
		System.out.println(queue);
		
		for (int i = 0; i < 8; i++) {
			queue.deQueue();
		}
		
		System.out.println(queue);
		
		queue.deQueue();
		queue.deQueue();
		
		System.out.println(queue);
		
	}

}
