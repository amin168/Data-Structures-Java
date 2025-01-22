package stack;

import java.util.Stack;

public class _232_用栈实现队列 {

    // https://leetcode.cn/problems/implement-queue-using-stacks/description/

    private Stack<Integer> inStack = new Stack<>();
    private Stack<Integer> outStack = new Stack<>();

    public _232_用栈实现队列() {

    }

    public void push(int x) {
        inStack.push(x);
    }

    public int pop() {
        checkOutStack();

        return outStack.pop();
    }

    public int peek() {
        checkOutStack();

        return outStack.peek();
    }

    public boolean empty() {
        return inStack.isEmpty() && outStack.empty();
    }

    private void checkOutStack() {
        if(outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push((inStack.pop()));
            }
        }
    }
}
