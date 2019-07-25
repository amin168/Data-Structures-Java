package 链表;

public class _203_移除链表元素 {

	// 递归
	public ListNode removeElements(ListNode head, int val) {
		if (head == null)
			return null;
//		ListNode nodes = removeElements(head.next, val);
//		if (head.val == val) {
//			return nodes;
//		} else {
//			head.next = nodes;
//			return head;
//		}
		
		head.next = removeElements(head.next, val);
		return head.val == val ? head.next : head;
	}

	// 利用虚拟头结点
//	public ListNode removeElements(ListNode head, int val) {
//		ListNode dummyHead = new ListNode(-1);
//		dummyHead.next = head;
//
//		ListNode prev = dummyHead;
//		while (prev.next != null) {
//			if (prev.val == val) {
//				ListNode delNode = prev.next;
//				prev = prev.next;
//				delNode.next = null;
//			} else {
//				prev = prev.next;
//			}
//		}
//		return dummyHead.next;
//	}

//	public ListNode removeElements(ListNode head, int val) {
//		while (head != null && head.val == val) {
//			ListNode delNode = head;
//			head = head.next;
//			delNode.next = null;
//		}
//
//		if (head == null)
//			return null;
//
//		ListNode prev = head;
//		while (prev.next != null) {
//			if (prev.val == val) {
//				ListNode delNode = prev.next;
//				prev = prev.next;
//				delNode.next = null;
//			} else {
//				prev = prev.next;
//			}
//		}
//
//		return head;
//	}
}
