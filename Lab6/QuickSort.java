
import java.util.Arrays;

interface Iterator<E> {
	    E get();
	    void set(E e);
	    Iterator<E> advance();
	    boolean equals(Iterator<E> other);
	    Iterator<E> clone();
	}

	class Node<E> {
	    Node(E d, Node<E> n) { data = d; next = n; }
	    E data;
	    Node<E> next;
	}

	class ListIter<E> implements Iterator<E> {
	    Node<E> curr;
	    ListIter(Node<E> n) { curr = n; }
	    public E get() { return curr.data; }
	    public void set(E e) { curr.data = e; }
	    public Iterator<E> advance() { curr = curr.next; return this; }
	    public boolean equals(Iterator<E> other) { 
	        return curr == ((ListIter<E>)other).curr; 
	    }
	    public Iterator<E> clone() {
	        return new ListIter<E>(curr);
	    }
	}

	class List<E> {
	    Node<E> head;
	    List() { head = null; }
	    public void addFirst(E d) {
	        head = new Node<E>(d, head);
	    }
	    public Iterator<E> begin() { return new ListIter<E>(head); }
	    public Iterator<E> end() { return new ListIter<E>(null); }
	} // List

	class ArrayIter<E> implements Iterator<E> {
	    Object[] data;
	    int pos;
	    ArrayIter(Object[] d, int p) { data = d; pos = p; }
	    public E get() { return (E)data[pos]; }
	    public void set(E e) { data[pos] = e; }
	    public Iterator<E> advance() {
	        ++pos;
	        return this;
	    }
	    public boolean equals(Iterator<E> other) { 
	        return pos == ((ArrayIter<E>)other).pos; 
	    }
	    public Iterator<E> clone() {
	        return new ArrayIter<E>(data, pos);
	    }
	}

	class Array<E> {
	    Object[] data;
	    int num_elements;

	    Array() {
	        data = new Object[10];
	        num_elements = 0;
	    }
	    public void add(E d) {
	        if (num_elements >= data.length) {
	            resize(data.length * 2);
	        }
	        data[num_elements] = d;
	        ++num_elements;
	    }

	    void resize(int s) {
	        Object[] new_data = new Object[s];
	        for (int i = 0; i != num_elements; ++i) {
	            new_data[i] = data[i];
	        }
	        data = new_data;
	    }

	    public ArrayIter<E> begin() { return new ArrayIter<E>(data, 0); }
	    public ArrayIter<E> end() { return new ArrayIter<E>(data, num_elements); }
	} 

	public class QuickSort {
	    

	    static <E extends Comparable<? super E>> 
	    void quicksort(Iterator<E> begin, Iterator<E> end) {
			if (!begin.equals(end)) {
				Iterator<E> pivot = partition(begin, end);
				quicksort(begin, pivot);
				pivot.advance();
				quicksort(pivot, end);
			}
	    }
	    

	    static <E extends Comparable<? super E>> Iterator<E> 
	    partition(Iterator<E> begin, Iterator<E> end) {
	        Iterator<E> pivot = begin.clone();
	        Iterator<E> pivot2 = begin.clone();
	        pivot2.advance();
	        while (!pivot2.equals(end)){
	        	pivot2.advance();
	        	pivot.advance();
	        }
	        Iterator<E> I = begin.clone();
	        Iterator<E> J = begin.clone();
	        while (!J.equals(pivot)){
	        	if(J.get().compareTo(pivot.get()) < 0){
	        		swap(J, I);
	        		I.advance();
	        	
	        	} J.advance();
	        }swap(I, pivot); 
	        return I;
	      }

	    
	    
	    static <E extends Comparable<? super E>> void swap(Iterator<E> J, Iterator<E> I){
	    	E temp = J.get();
	    	J.set(I.get());
	    	I.set(temp);
	    }
	    
	    
	    public static void main(String[] args) {
	    	int[] A_orig = {2,8,7,1,3,5,6,4};
	    	int[] A_sorted = A_orig.clone();
	    	Arrays.sort(A_sorted);

	    	Array<Integer> L = new Array<Integer>();
	    	for (int i = 0; i != A_orig.length; ++i) {
	    	    L.add(A_orig[i]);
	    	}
	    	quicksort(L.begin(), L.end());
	    	int[] A = new int[A_orig.length];
	    	int k = 0;
	    	for (Iterator<Integer> i = L.begin(); ! i.equals(L.end()); i.advance()) {
	    	    A[k] = i.get(); ++k;  
	    	}
	    	assert Arrays.equals(A_sorted, A);
	       
	       
	    }

	}
