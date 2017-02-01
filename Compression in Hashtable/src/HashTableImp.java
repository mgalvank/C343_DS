import java.util.function.BiFunction;
import java.util.Set;
import java.util.LinkedList;
import java.util.Random;

class Entry<K, V> {
	Entry(K k, V v) {
		key = k;
		value = v;
	}

	final K key;
	V value;
	int hash;
}

@SuppressWarnings("unchecked")
public class HashTableImp<K, V> implements HashTable<K, V> {
	// an array of linked lists to handle chaining
	private Object[] array;
	// private LinkedList<Entry<K, V>>[] array;
	private int itemCount;
	private BiFunction<K, Integer, Integer> h;

	private int hash(K k) {
		return this.h.apply(k, array.length);
	}

	public HashTableImp(int initialCapacity, HashMethod hm) {
		// This cast violates type safety of the program but we wanted
		// you to implement hash table on top of a data structure that
		// does not do dynamic resizing (arrays instead of ArrayList)
		// so you can do the hash table growing yourself.
		// Do NOT do it in the future.
		this.array = new Object[initialCapacity];
		for (int i = 0; i < initialCapacity; i++) {
			array[i] = new LinkedList<Entry<K, V>>();
		}
		this.itemCount = 0;
		switch (hm) {
		case Div:
			// division method
			h = (k, m) -> {
				return k.hashCode() % m;
			};
			break;
		// MAD method
		case MAD:
			h = (k, m) -> {
				return mad(k, m);
			};
			break;
		}
	}

	// Implement all the following stubs

	// Multiply-Add-and-Divide (MAD) hashing method
	private int mad(K k, int m) {
		Random rand = new Random();
		int p = m + 1;
		while (!isPrime(p)) {
			p++;
		}
		int a = rand.nextInt(p - 1) + 1;
		int b = rand.nextInt(p - 1) + 1;
		int x;
		x = ((a * k.hashCode() + b) % p) % m;
		return x;
	}

	private boolean isPrime(int p) { // helper. Check if a # is prime
		if (p % 2 == 0) {
			return false;
		}
		for (int i = 3; i * i <= p; i += 2) {
			if (p % i == 0) {
				return false;
			}
		}
		return true;
	}

	// this method needs to be called appropriately in the put method
	private void growTable() {
		LinkedList<Entry<K, V>>[] temp = (LinkedList<Entry<K, V>>[]) new LinkedList<?>[array.length * 2];
		for (int i = 0; i < array.length - 1; i++) {//simply makes a new list twice the length and transfers everything over
			temp[i] = (LinkedList<Entry<K, V>>) array[i]; //added cast here
		}
		array = temp;
	}

	@Override
	public boolean containsKey(K key) {
		if (itemCount == 0) {
			return false;
		} else {
			for (int i = 0; i < array.length - 1; i++) {
				LinkedList<Entry<K, V>> temp = (LinkedList<Entry<K, V>>) array[hash(key)];
				if (temp.get(i).key.equals(key)) {
					return true;
				}
			}
		}
		return false;
	}

    @Override    
    public V get(K key) {
		LinkedList<Entry<K, V>> temp = (LinkedList<Entry<K, V>>) array[hash(key)];
		if (containsKey(key)) {
			for (int i = 0; i < temp.size(); i++) {
				if (temp.get(i).key.equals(key)) {
					return temp.get(i).value;
				}
			}
		}
		return null;
	}

    @Override    
    public V put(K key, V value){
    	return null;
    }

    // not used in this client code so make sure it works correctly by
    // implementing unit tests.
    @Override    
    public boolean isEmpty() {
		if(itemCount == 0){	
			return false;
		}
		return true;
	}

    @Override    
	public Set<K> keySet(){
		return null;
    }

    // not used in this client code so make sure it works correctly by
    // implementing unit tests.
    @Override    
	public V remove(K key){
		return null;
    }
}
