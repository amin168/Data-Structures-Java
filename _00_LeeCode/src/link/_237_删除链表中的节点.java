package link;

/**
 * https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
 * 解题思路：用后一个的值，覆盖node的值
 */
public class _237_删除链表中的节点 {
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
