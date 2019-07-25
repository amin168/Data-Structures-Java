package 栈;

import java.util.Stack;

public class _232_用栈实现队列 {
	// 准备两个栈：inStack，outStack
	// 入队时，push到inStack中
	// 出队时
	// 如果outStack为空，将所有inStack元素全部push到outStack中，outStack弹出栈顶元素
	// 如果outStack不为空，outStack弹出栈顶元素
	private Stack<Integer> inStack = new Stack<Integer>();
	private Stack<Integer> outStack = new Stack<Integer>();
	
	
	/** 入队 */
    public void push(int x) {
        inStack.push(x);
    }
    
    /** 出队 */
    public int pop() {
        
    	if(outStack.isEmpty()) {
    		while(!inStack.isEmpty()) {
    			outStack.push(inStack.pop());
    		}
    	}
    	
    	return outStack.pop();
    }
    
    /** Get the top element. */
    public int top() {
    	if(outStack.isEmpty()) {
    		while(!inStack.isEmpty()) {
    			outStack.push(inStack.pop());
    		}
    	}
    	
    	return outStack.peek();
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
        return outStack.isEmpty() && inStack.isEmpty();
    }
	
}
