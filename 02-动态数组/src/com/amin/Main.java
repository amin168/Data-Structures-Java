package com.amin;

public class Main {

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			list.add(i + 1);
		}
		System.out.println(list);
		list.add(11);
		
		System.out.println(list);
		
		for (int i = 0; i < 8; i++) {
			list.remove(0);
		}
		
		System.out.println(list);
	}
}
