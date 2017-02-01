package test;

import java.io.*;
import java.util.*;

public class LinkList {

	intNode head, tail;
	int size = 0;

	public void add(int data) {

		intNode node = new intNode(data);

		if (tail == null) {
			head = node;
			tail = node;
		} else {
			tail.next = node;
			tail = node;
		}
		size++;
	}

	public intNode delete(int data) {
		intNode nodetoreturn = null;
		if (size == 0)
			return null;
		if (size == 1) {
			nodetoreturn = head;
			head = null;
			tail = null;
			size--;
			return nodetoreturn;

		}

		intNode nodebefore = findbefore(data);
		if (nodebefore.data == 0) {
			head = head.next;
		} else if (nodebefore != null) {
			if (tail.data == data) {

				tail = nodebefore;
				nodebefore.next = null;
				size--;
			}

			else {

				nodebefore.next = nodebefore.next.next;
				size--;
			}

		}
		return null;
	}

	public intNode findbefore(int data) {

		if (head.data == data) {
			return new intNode();
		}

		intNode node = head;

		while (node.next != null) {

			if (node.next.data == data) {
				return node;
			}
			node = node.next;

		}
		return null;

	}

	public intNode find(int data) {
		if (head == null) {
			return null;
		}

		if (head.data == data) {
			return head;
		}

		intNode node = head;

		while (node.next != null) {
			node = node.next;
			if (node.data == data) {
				return node;

			}

		}
		return null;

	}

	void traverse() {
		intNode node = head;
		System.out.print(" " + node);
		while (node.next != null) {
			node = node.next;
			System.out.print(" " + node);

		}

	}

}
