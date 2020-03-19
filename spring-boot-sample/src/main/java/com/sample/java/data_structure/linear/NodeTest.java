package com.sample.java.data_structure.linear;

import java.util.LinkedList;

/**
 * 链表反转
 *
 */
public class NodeTest {
	
	/**
	 * 单向链表
	 *
	 */
	static class Node {
	    Integer data;
	    Node next;
	}

	/**
	 * 1->2->3->4->5->6
	 * @return
	 */
	static Node readyNode() {
	    Node linkNode1 = new Node();
	    linkNode1.data = 1;
	    Node linkNode2 = new Node();
	    linkNode2.data = 2;
	    Node linkNode3 = new Node();
	    linkNode3.data = 3;
	    Node linkNode4 = new Node();
	    linkNode4.data = 4;
	    Node linkNode5 = new Node();
	    linkNode5.data = 5;
	    Node linkNode6 = new Node();
	    linkNode6.data = 6;
	    linkNode1.next = linkNode2;
	    linkNode2.next = linkNode3;
	    linkNode3.next = linkNode4;
	    linkNode4.next = linkNode5;
	    linkNode5.next = linkNode6;
	    return linkNode1;
	}
	
	/**
	 * 递归
	 * @param node
	 * @return
	 */
	static Node reverseLinkedList(Node node) {
	    if (node == null || node.next == null) {
	    	return node;
	    } else {
	    	Node headNode = reverseLinkedList(node.next);
	    	node.next.next = node;
	    	node.next = null;
	    	return headNode;
	    }
	}
	
	/**
	 * 遍历
	 * @param node
	 * @return
	 */
	static Node reverseforeachLinkedList(Node node) {
	    Node previousNode = null;
	    Node currentNode = node;
	    Node headNode = null;
	    while (currentNode != null) {
	        Node nextNode = currentNode.next;
	        if (nextNode == null) {
	            headNode = currentNode;
	        }
	        currentNode.next = previousNode;
	        previousNode = currentNode;
	        currentNode = nextNode;
	    }
	    return headNode;
	}
	
	static LinkedList<Object> reverseLinkedList(LinkedList<Object> linkedList) {
	    LinkedList<Object> newLinkedList = new LinkedList<Object>();
	    for (Object object : linkedList) {
	        newLinkedList.add(0, object);
	    }
	    return newLinkedList;
	}
	
	public static void main(String[] args) {
		Node node = readyNode();
		while (node != null) {
			System.out.println(node.data);
			node = node.next;
		}
		
		Node reverseNode = reverseLinkedList(readyNode());
		while (reverseNode != null) {
			System.out.println(reverseNode.data);
			reverseNode = reverseNode.next;
		}
		
//		Node reverseforeachNode = reverseforeachLinkedList(readyNode());
//		while (reverseforeachNode != null) {
//			System.out.println(reverseforeachNode.data);
//			reverseforeachNode = reverseforeachNode.next;
//		}
	}

}
