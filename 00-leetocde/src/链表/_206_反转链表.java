package 链表;

public class _206_反转链表 {

	// 递归的方式
	public ListNode reverseList(ListNode head) {

		// head == null 证明是空节点，head.next == null 证明只有一个节点
		if (head == null || head.next == null)
			return head;

		// 5->4->3->2-1
		// 假如传进的是4，那反转出来的结果的1->2->3->4
		// 把这里的 head.next 当成4，所以反转出来就剩5
		// 最后head.next 是指向4，所以把4.next = 5, 5.next = null
		ListNode newHead = reverseList(head.next);
		head.next.next = head;
		head.next = null;

		return newHead;

	}

	// 非递归的方式
	public ListNode reverseList2(ListNode head) {

		if (head == null || head.next == null)
			return head;

		// 因为已知条件是只能从左边开始
		// 5->4->3->2>1
		// 1. head = 5,  5.next = 4
		// 2. 先把5.next 存起来
		// 3. 把newHead 指向 5.next, 也就是5.next = null，
		// 4. newHead = head,  newHead: 5->null
		// 5. 原来的变成 4->3->2->1
		ListNode newHead = null;
		while (head != null) {
			ListNode temp = head.next;
			head.next = newHead;
			newHead = head;
			head = temp;
		}

		return newHead;

	}
}
