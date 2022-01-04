package ir.ac.aut.ceit.alg.graph;


public class ListNode {
    private int data = -1;
    private ListNode next = null;

    public ListNode(int data , ListNode next) {
        this.data = data;
        this.next = next;
    }

    public ListNode getNext() {
        return next;
    }

    public int getData() {
        return data;
    }
}
