package 栈;

import java.util.Stack;

public class _20_有效的括号 {
	public boolean isValid(String s) {
		Stack<Character> stack = new Stack<Character>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(' || c == '[' || c == '{') {
				stack.push(c);
			} else {
				if (stack.isEmpty())
					return false;

				char topChar = stack.pop();
				if (topChar != '(' && c == ')')
					return false;
				if (topChar != '[' && c == ']')
					return false;
				if (topChar != '{' && c == '}')
					return false;
			}
		}

		return stack.isEmpty();
	}

	public static void main(String[] args) {
		System.out.println((new _20_有效的括号()).isValid("()[]{}"));
		System.out.println((new _20_有效的括号()).isValid("([)]"));
	}
}
