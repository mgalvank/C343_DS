

import java.io.*;
import java.util.*;

class DList implements Sequence {
	DNode sentinel;

	// DNode head,tail;
	DList() {
		sentinel = new DNode(444, null, null);
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
	}

	public static void main(String args[]) {
		DList L = new DList();
		assert L.empty();

		for (int i = 0; i != 10; ++i) {
			L.insert(L.end(), i);
		}

		assert !L.empty();
		int k = 0;
		for (Iterator i = L.begin(); !i.equals(L.end()); i.advance()) {
			assert i.get() == k;
			++k;
		}
		k = 9;
		for (BidirectionalIterator i = L.end().retreat(); !i.equals(L.begin().retreat()); i.retreat()) {
			assert i.get() == k;
			--k;
		}
	}

	// insert before pos, return iterator pointing to new node
	public Iter insert(Iter pos, int e) {
		DNode newNode = new DNode(e, pos.curr, pos.curr.prev);
		newNode.prev.next = newNode;
		pos.curr.prev = newNode;
		return new Iter(newNode);

	}

	// erase the node at pos, return iterator to the next element
	public Iter erase(Iter pos) {
		pos.curr.prev.next = pos.curr.next;
		pos.curr.next.prev = pos.curr.prev;
		//pos.curr.next = null;
		//pos.curr.prev = null;
        return new Iter(pos.curr.next);
		
		 
	}

	public boolean empty() {
		return (sentinel.next == sentinel);

	}

	public class Iter implements Iterator, BidirectionalIterator {
		DNode curr;
		DNode prev;

		Iter(DNode n) {
			curr = n;
		}

		public int get() {
			return curr.data;
		}

		public Iterator advance() {
			curr = curr.next;
			return this;
		}

		public BidirectionalIterator retreat() {
			curr = curr.prev;
			return this;
		}

		public boolean equals(Iterator other) {
			return curr == ((Iter) other).curr;
		}
	}

	public Iter begin() {
		return new Iter(sentinel.next);
	}

	public Iter end() {
		return new Iter(sentinel);
	}
}