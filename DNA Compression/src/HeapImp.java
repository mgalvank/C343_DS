import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Comparator;
    
public class HeapImp<E> implements Heap<E> {
    private ArrayList<E> data;
    private Comparator<E> compare;
    private int heapSize;

    HeapImp(ArrayList<E> data, Comparator<E> compare) {
	this.data = data;
	this.compare = compare;
	System.out.println(data);
	buildMinHeap();
    }

    @Override
    public E minimum() {
    	return data.get(0);
    }

    @Override
    public void insert(E e) {
        data.add(e);
        increaseKey(data.size()-1);
    }
    

    @Override
    public E extractMin() {
    	E min = data.get(0);
    	data.set(0, data.get(data.size()-1));
    	data.remove(data.size()-1);
    	minHeapify(0);
    	return min;
	
    }

    private void increaseKey(int i) {
    	int parent = (int) (Math.floor(i-1/2));
    	E tmp;
    	if(compare.compare(data.get(i), data.get(parent))<0 && parent >= 0){
    		tmp = data.get(i);
    		data.set(i, data.get(parent));
    		data.set(parent, tmp);
    	    increaseKey(parent);
    	}
    	
	
    }

    @Override
    public void minHeapify(int i) {
    	int l = 2*i+1;
    	int r = 2*i+2;
    	E tmp;
    	int smallest = i;
    	if(l<data.size() && compare.compare(data.get(l),data.get(i))<0){
    		smallest = l;
    	}
    	if(r<data.size() && compare.compare(data.get(smallest),data.get(r))<0){
    		smallest = r;
    	}
    	if(i!=smallest){
    		tmp = data.get(i);
    		data.set(i, data.get(smallest));
    		data.set(smallest, tmp);
    		minHeapify(smallest);
    	}
        
    }
