package com.amin;

import com.amin.Times.Task;
import com.amin.file.FileInfo;
import com.amin.file.Files;
import com.amin.map.HashMap;
import com.amin.map.LinkedHashMap;
import com.amin.map.Map;
import com.amin.map.TreeMap;
import com.amin.map.Map.Visitor;
import com.amin.model.Key;
import com.amin.model.Person;
import com.amin.model.SubKey1;
import com.amin.model.SubKey2;

public class Main {

//	public static void test() {
//		Person p1 = new Person(18, 1.80f, "jack");
//		Person p2 = new Person(18, 1.80f, "jack");
//
//		Map<Object, Integer> map = new HashMap<Object, Integer>();
//		map.put(p1, 1);
//		map.put(p2, 2);
//		map.put("jack", 3);
//		map.put("rose", 1);
//		map.put("jack", 1);
//		map.put(null, 6);
//		System.out.println(map.size());
//
////		map.traversal(new Visitor<Object, Integer>() {
////
////			@Override
////			public boolean visit(Object key, Integer value) {
////				System.out.println(key + ", " + value);
////				return false;
////			}
////		});
//
////		Asserts.test(map.get("jack") == 1);
////		Asserts.test(map.get("rose") == 1);
////		Asserts.test(map.get(p2) == 2);
////		Asserts.test(map.get(null) == 6);
////		
////		Asserts.test(map.remove("jack") == 1);
////		Asserts.test(map.remove("rose") == 1);
////		Asserts.test(map.remove(p2) == 2);
////		Asserts.test(map.remove(null) == 6);
//
//		Asserts.test(map.containsKey("jack"));
//		Asserts.test(map.containsKey("rose"));
//		Asserts.test(map.containsKey(p2));
//		Asserts.test(map.containsKey(null));
//
//		Asserts.test(map.containsValue(1));
//		Asserts.test(map.containsValue(2));
//		Asserts.test(map.containsValue(6));
//	}
//
//	static void test2() {
//		HashMap<Object, Integer> map = new HashMap<Object, Integer>();
//		for (int i = 1; i <= 19; i++) {
//			map.put(new Key(i), i);
//		}
//
//		map.print();
//
//		System.out.println(map.size());
//
//		map.put(new Key(4), 100);
//
//		Asserts.test(map.size() == 19);
//		Asserts.test(map.get(new Key(18)) == 18);
//		Asserts.test(map.get(new Key(4)) == 100);
//	}

	static void test2(HashMap<Object, Integer> map) {
		for (int i = 1; i <= 20; i++) {
			map.put(new Key(i), i);
		}
		for (int i = 5; i <= 7; i++) {
			map.put(new Key(i), i + 5);
		}
		Asserts.test(map.size() == 20);
		Asserts.test(map.get(new Key(4)) == 4);
		Asserts.test(map.get(new Key(5)) == 10);
		Asserts.test(map.get(new Key(6)) == 11);
		Asserts.test(map.get(new Key(7)) == 12);
		Asserts.test(map.get(new Key(8)) == 8);
	}

	static void test3(HashMap<Object, Integer> map) {
		map.put(null, 1); // 1
		map.put(new Object(), 2); // 2
		map.put("jack", 3); // 3
		map.put(10, 4); // 4
		map.put(new Object(), 5); // 5
		map.put("jack", 6);
		map.put(10, 7);
		map.put(null, 8);
		map.put(10, null);
		Asserts.test(map.size() == 5);
		Asserts.test(map.get(null) == 8);
		Asserts.test(map.get("jack") == 6);
		Asserts.test(map.get(10) == null);
		Asserts.test(map.get(new Object()) == null);
		Asserts.test(map.containsKey(10));
		Asserts.test(map.containsKey(null));
		Asserts.test(map.containsValue(null));
		Asserts.test(map.containsValue(1) == false);
	}

	static void test4(HashMap<Object, Integer> map) {
		map.put("jack", 1);
		map.put("rose", 2);
		map.put("jim", 3);
		map.put("jake", 4);
		map.remove("jack");
		map.remove("jim");
		for (int i = 1; i <= 10; i++) {
			map.put("test" + i, i);
			map.put(new Key(i), i);
		}
		for (int i = 5; i <= 7; i++) {
			Asserts.test(map.remove(new Key(i)) == i);
		}
		for (int i = 1; i <= 3; i++) {
			map.put(new Key(i), i + 5);
		}
		Asserts.test(map.size() == 19);
		Asserts.test(map.get(new Key(1)) == 6);
		Asserts.test(map.get(new Key(2)) == 7);
		Asserts.test(map.get(new Key(3)) == 8);
		Asserts.test(map.get(new Key(4)) == 4);
		Asserts.test(map.get(new Key(5)) == null);
		Asserts.test(map.get(new Key(6)) == null);
		Asserts.test(map.get(new Key(7)) == null);
		Asserts.test(map.get(new Key(8)) == 8);
		map.traversal(new Visitor<Object, Integer>() {
			public boolean visit(Object key, Integer value) {
				System.out.println(key + "_" + value);
				return false;
			}
		});
	}

	static void test5(HashMap<Object, Integer> map) {
		for (int i = 1; i <= 20; i++) {
			map.put(new SubKey1(i), i);
		}
		map.put(new SubKey2(1), 5);
		Asserts.test(map.get(new SubKey1(1)) == 5);
		Asserts.test(map.get(new SubKey2(1)) == 5);
		Asserts.test(map.size() == 20);
	}

	static void test1() {
		FileInfo fileInfo = Files.read("C:\\Program Files\\Java\\jdk1.8.0_201\\src\\java\\util",
				new String[] { "java" });
		String[] words = fileInfo.words();

		System.out.println("总行数：" + fileInfo.getLines());
		System.out.println("单词总数：" + words.length);
		System.out.println("-------------------------------------");

//		java.util.HashMap<String, Integer> map = new java.util.HashMap<String, Integer>();
//		for (String word : words) {
//			Integer count = map.get(word);
//			count = count == null ? 0 : count;
//			map.put(word, count + 1);
//		}
//		System.out.println(map.size());

		test1Map(new HashMap<String, Integer>(), words);
		test1Map(new TreeMap<String, Integer>(), words);
		test1Map(new LinkedHashMap<String, Integer>(), words);
	}

	static void test1Map(Map<String, Integer> map, String[] words) {
		Times.test(map.getClass().getName(), new Task() {
			@Override
			public void execute() {
				for (String word : words) {
					Integer count = map.get(word);
					count = count == null ? 0 : count;
					map.put(word, count + 1);
				}
				System.out.println(map.size()); // 11378

				int count = 0;
				for (String word : words) {
					Integer i = map.get(word);
					count += i == null ? 0 : i;
					map.remove(word); // 测试没通过，问题出现在remove，因为覆盖不完整，没有把度为2节点的hash覆盖
				}
				Asserts.test(count == words.length);
				Asserts.test(map.size() == 0);
			}
		});
	}

	public static void main(String[] args) {
//		test1();
//		test2(new HashMap<>());
//		test3(new HashMap<>());
//		test4(new HashMap<>());
//		test5(new HashMap<>());

		test2(new LinkedHashMap<Object, Integer>());
		test3(new LinkedHashMap<Object, Integer>());
		test4(new LinkedHashMap<Object, Integer>());
		test5(new LinkedHashMap<Object, Integer>());
	}

}
