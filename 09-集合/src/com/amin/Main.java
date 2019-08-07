package com.amin;

import com.amin.Times.Task;
import com.amin.file.FileInfo;
import com.amin.file.Files;
import com.amin.set.ListSet;
import com.amin.set.Set;
import com.amin.set.Set.Visitor;
import com.amin.set.TreeSet;

public class Main {
	public static void main(String[] args) {
		test2();
	}

	private void test1() {
		Set<Integer> listSet = new ListSet<Integer>();

		listSet.add(1);
		listSet.add(2);
		listSet.add(3);
		listSet.add(1);
		listSet.add(5);
		listSet.add(5);
		listSet.add(6);
		listSet.add(7);

		listSet.traversal(new Visitor<Integer>() {
			@Override
			public boolean visit(Integer element) {
				System.out.println(element);
				return false;
			}
		});

		System.out.println("---------------");

		Set<Integer> treeSet = new TreeSet<Integer>();
		treeSet.add(1);
		treeSet.add(2);
		treeSet.add(3);
		treeSet.add(1);
		treeSet.add(5);
		treeSet.add(5);
		treeSet.add(6);
		treeSet.add(7);

		treeSet.traversal(new Visitor<Integer>() {
			@Override
			public boolean visit(Integer element) {
				System.out.println(element);
				return false;
			}
		});
	}

	private static void test2() {
		FileInfo fileInfo = Files.read("C:\\Program Files\\Java\\jdk1.8.0_201\\src\\java\\lang", new String[] { "java" });
		System.out.println("文件数量：" + fileInfo.getFiles());
		System.out.println("代码行数：" + fileInfo.getLines());

		String[] words = fileInfo.words();
		System.out.println("单词数量：" + words.length);

		Times.test("TreeSet", new Task() {

			@Override
			public void execute() {
				testSet(new TreeSet<String>(), words);
			}
		});

	}

	private static void testSet(Set<String> set, String[] words) {
		for (int i = 0; i < words.length; i++) {
			set.add(words[i]);
		}
		for (int i = 0; i < words.length; i++) {
			set.contains(words[i]);
		}
		for (int i = 0; i < words.length; i++) {
			set.remove(words[i]);
		}
	}
}
