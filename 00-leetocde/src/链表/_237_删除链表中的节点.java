package 链表;

public class _237_删除链表中的节点 {
	public void deleteNode(ListNode node) {
		// 因为传给我们的node是当前的节点
    	// 通常的做法是拿node.prev的节点做删除，但是现在的情况不允许
    	// 所以我们可以让node.next的节点值，赋值给node，然后用node.next = node.next.next
		
		node.val = node.next.val;
		node.next = node.next.next;
	}
}
