package 队列;

import java.util.LinkedList;
import java.util.Queue;

public class _225_用队列实现栈 {
	private Queue<Integer> inQueue = new LinkedList<Integer>();
	private Queue<Integer> outQueue = new LinkedList<Integer>();

	/** Push element x onto stack. */
	public void push(int x) {
		inQueue.offer(x);
	}

	/** Removes the element on top of the stack and returns that element. */
	public int pop() {
		System.out.println("before inQueue" + inQueue.toString());
		System.out.println("before outQueue" + outQueue.toString());

		while (inQueue.size() > 1) {
			outQueue.offer(inQueue.poll());
		}

		System.out.println("after inQueue" + inQueue.toString());
		System.out.println("after outQueue" + outQueue.toString());

		System.out.println();
		
		// 此时inQueue只剩一个元素就是栈顶元素
		int top = inQueue.poll();
		
		// 这里需要用中间变量，是因为避免赋值引用
		Queue<Integer> temp = inQueue;
		inQueue = outQueue;
		outQueue = temp;
		
		return top;
	}

	/** Get the top element. */
	public int top() {
		while (inQueue.size() > 1) {
			outQueue.offer(inQueue.poll());
		}
		
		// 此时inQueue只剩一个元素就是栈顶元素
		int top = inQueue.poll();
		
		outQueue.offer(top);
		
		// 这里需要用中间变量，是因为避免赋值贝引用
		Queue<Integer> temp = inQueue;
		inQueue = outQueue;
		outQueue = temp;
		
		return top;
	}

	/** Returns whether the stack is empty. */
	public boolean empty() {
		return inQueue.isEmpty() && outQueue.isEmpty();
	}

	public static void main(String[] args) {
		_225_用队列实现栈 result = new _225_用队列实现栈();

		result.push(1);
		result.push(2);
		result.push(3);
		result.push(4);
		result.push(5);
		
		result.top();
		result.pop();
		result.top();
		result.pop();
		
		System.out.println(result.top());
		

//		while (!result.empty()) {
//			result.pop();
//		}
	}
}
