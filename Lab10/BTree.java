

import java.util.Arrays;
import java.util.ArrayList;

class SearchResult {
    BTreeNode node;
    int pos;
    SearchResult(BTreeNode n, int p) { node = n ; pos = p; }
}

class BTreeNode {
    boolean is_leaf;
    ArrayList<Integer> keys;
    ArrayList<BTreeNode> children;
    
    BTreeNode(ArrayList<Integer> k, ArrayList<BTreeNode> c, boolean l) {
        keys = k; children = c; is_leaf = l;
    }

    // precondition: keys is a sorted array
    // returns: an integer i such that
    //   * if key is greater than all the keys, then i == keys.size()
    //   * otherwise, i is the lowest index where key <= keys.get(i)
    //   (So keys.add(i, key) produces a sorted array.)
    int key_search(int key) {
    	//System.out.println(keys);
        for (int i = 0; i != keys.size(); ++i) {
            if (key <= keys.get(i)) {
                return i;
            }
        }
        return keys.size();
    }

    public SearchResult search(int key) {
        int i = key_search(key);
        if (i != keys.size() && key == keys.get(i)) {
            return new SearchResult(this, i);
        } else if (is_leaf) {
            return null;
        } else {
            return children.get(i).search(key);
        }
    }

    void split_child(int i, int t) {
        // students: put your code here
    	int s = (this.children.get(i).keys.size())/2;
    	
    	int value = this.children.get(i).keys.get(s);
    	
    	this.keys.add(value);
    	//System.out.println(this.children.get(0).keys);
    	ArrayList<Integer> child1 = new ArrayList<Integer>();
    	for(int j=0;j<s;j++){
    		child1.add(this.children.get(i).keys.get(j));
    	}
    	ArrayList<Integer> child2 = new ArrayList<Integer>();
    	for(int j=s+1;j<this.children.get(i).keys.size();j++){
    		child2.add(this.children.get(i).keys.get(j));
    	}
    	this.children.get(i).keys = child1;
    	ArrayList<BTreeNode> children2 = new ArrayList<BTreeNode>(0);
    	this.children.add(new BTreeNode(child2,children2,true));
    	//System.out.println(children);
    	//System.out.println(this);
    }

    public void insert(int key, int t) {
        int i = key_search(key);
        //System.out.println(is_leaf);
        //System.out.println(i);
        
        if (is_leaf) {
            keys.add(i, key);
        } else {
            // if child is full, split into two children
            if (children.get(i).keys.size() == 2 * t - 1) {
                split_child(i, t);
            }
            // determine which of the two new children to insert into
            //System.out.println(keys.size());
            if (i != keys.size() && key > keys.get(i)) {
                ++i;
            }
            children.get(i).insert(key, t);
        }
        System.out.println(this);
    }

    public String toString() {
        return String.format("(%1$s, %2$s)", keys.toString(),
                             children.toString());
    }
    
}


class BTree {
    BTreeNode root;
    int t;
    
    BTree(int t_init) {
        ArrayList<Integer> keys = new ArrayList<Integer>(0);
        ArrayList<BTreeNode> children = new ArrayList<BTreeNode>(0);
        root = new BTreeNode(keys, children, true);
        t = t_init;
    }

    SearchResult search(int key) { return root.search(key); }

    void insert(int key) {
        if (root.keys.size() == 2*t - 1) { // root is full
            // create a new root with old root as its child
            ArrayList<Integer> keys = new ArrayList<Integer>(0);
            ArrayList<BTreeNode> children = new ArrayList<BTreeNode>();
            children.add(root);
            root = new BTreeNode(keys, children, false);
            
            // split the old root then insert key
            root.split_child(0, t);
            root.insert(key, t);
        } else {
            root.insert(key, t);
        }
    }

    public String toString() { return root.toString(); }

    public static void main(String[] args) {
        BTree T = new BTree(3);
        int A[] = {1,2,3,5,6,7,9,12,16,18,21,22};
        for (int i = 0; i != A.length; ++i) {
            T.insert(A[i]);
        }
        
        for (int i = 0; i != A.length; ++i) {
            SearchResult r = T.search(A[i]);
            assert r != null;
            assert r.node.keys.get(r.pos) == A[i];
            assert T.toString().equals("([3, 7], [([1, 2], []), ([5, 6], []), ([9, 12, 16, 18, 21], [])])");
        }
        
        System.out.println("passed BTree tests");
    }
    
}
