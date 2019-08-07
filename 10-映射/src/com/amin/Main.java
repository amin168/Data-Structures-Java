package com.amin;

import com.amin.Times.Task;
import com.amin.file.FileInfo;
import com.amin.file.Files;
import com.amin.map.Map;
import com.amin.map.Map.Visitor;
import com.amin.set.TreeSet;
import com.amin.map.TreeMap;

public class Main {

	public static void main(String[] args) {
//		testTreeMap();
		testTreeSet();
	}

	private static void testTreeMap() {
		Map<String, Integer> map = new TreeMap<>();
		map.put("c", 2);
		map.put("a", 5);
		map.put("b", 6);
		map.put("a", 8);

		map.traversal(new Visitor<String, Integer>() {
			@Override
			public boolean visit(String key, Integer value) {
				System.out.println("key: " + key + ", value: " + value);
				return false;
			}
		});
	}

	private static void testTreeSet() {
		FileInfo fileInfo = Files.read("C:\\Program Files\\Java\\jdk1.8.0_201\\src\\java\\lang",
				new String[] { "java" });
		System.out.println("文件数量：" + fileInfo.getFiles());
		System.out.println("代码行数：" + fileInfo.getLines());

		String[] words = fileInfo.words();
		System.out.println("单词数量：" + words.length);

		Map<String, Integer> map = new TreeMap<String, Integer>();
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			Integer count = map.get(word);
			count = (count == null) ? 1 : (count + 1);
			map.put(word, count);
		}

		map.traversal(new Visitor<String, Integer>() {
			@Override
			public boolean visit(String key, Integer value) {
				System.out.println("key: " + key + ", value: " + value);
				return false;
			}
		});

	}

}
