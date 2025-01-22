package link;

/**
 * https://leetcode.cn/problems/reverse-linked-list/
 */
public class _206_反转链表 {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;

        // head.next 指向 newHead 里面的东西
        // newHead 指向 head
        // head 指向之前的 next

        ListNode newHead = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        }

        return newHead;
    }

    /**
     * 递归版本
     *
     * @param head
     * @return
     */
    public ListNode reverseList_with_recursion(ListNode head) {

        if (head == null || head.next == null) return head;

        ListNode newHead = reverseList_with_recursion(head.next);

        // 5 4 3 2 1
        // head = 5
        // newHead = 4 3 2 1


        // head.next = 4
        // 4的next 指向 5
        head.next.next = head;

        head.next = null;

        return newHead;
    }
}
