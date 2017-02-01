import java.util.function.BiPredicate;

class BSTNode<Key> implements Node<Key> {

    private BSTNode<Key> l;
    private BSTNode<Key> r;
    private BSTNode<Key> p;
    Key key;

    public BSTNode(Key k) {
        this.l = null;
        this.r = null;
        this.p = null;
        this.key = k;
    }

 
    public Node<Key> before() {
        if (this.l != null) {
            return this.l.max();
        }

        BSTNode<Key> y = this.p;
        BSTNode<Key> x = this;
        while (y != null && x == y.l) {
            x = y;
            y = y.p;
        }
        return y;
    }

    public Node<Key> after() {
        if (this.r != null) {
            return this.r.min();
        }
        BSTNode<Key> y = this.p;
        BSTNode<Key> x = this;
        while (y != null && x == y.r) {
            x = y;
            y = y.p;
        }
        return y;

    }   
 
    private Node<Key> min() {
        if (this.l == null)
            return this;
        else
            return this.l.min();
    }

    private Node<Key> max() {
        if (this.r == null) {
            return this;
        } else return this.r.max();

    }

    public Key getKey() {
        return this.key;
    }


    Node<Key> search(Key key, BiPredicate<Key,Key> lthn) {
        if (lthn.test(this.key, key)) {
            if (this.r != null) {
                return this.r.search(key, lthn);
            }else return null;

        }else if(lthn.test(key, this.key)) {
        		if (this.l != null) {
        			return this.l.search(key, lthn);
        		} else return null; 
        } else return this;
        
    }
    
    Node<Key> insert(Key key, BiPredicate<Key,Key> lthn) {
        if (lthn.test(this.key, key)) {
            if (this.r == null) {
                BSTNode<Key> n = new BSTNode<Key>(key);
                this.r = n;
                n.p = this;
                return n;
            } else return this.r.insert(key, lthn);

        } else {
            if (this.l == null) {
                BSTNode<Key> n = new BSTNode<Key>(key);
                this.l = n;
                n.p = this;
                return n;
            } else return this.l.insert(key, lthn);

        }
    }
    
    public void delete(Key key, BiPredicate<Key,Key> lthn) {
        if (lthn.test(this.key, key)) {
            if (this.l != null) {
                l.delete(key, lthn);
            }
        } else if (lthn.test(key, this.key)) {
            if (this.r != null) {
                r.delete(key, lthn);
            }
        } else {
            if (this.l != null && this.r != null) {
                BSTNode<Key> n = (BSTNode<Key>)this.r.min(); 
                this.key = n.key;
                this.r.delete(key, lthn);
            } else if (this.p.l == this) {
            		  if(this.l != null){	
            			  this.p.l = this.l;
            		  }
            		  else this.p.l = this.r;

            } else if (this.p.r == this) {
            		  if(this.l != null){
            			  this.p.r =  this.l;
            		  }
            		  else this.p.r = this.r;
            }
        }

    }

}