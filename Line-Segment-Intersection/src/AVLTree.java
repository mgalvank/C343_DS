import java.util.function.BiPredicate;

public class AVLTree<Key> implements SearchTree<Key> {
    private AVLNode<Key> root;
    private BiPredicate<Key,Key> lthn;

    AVLTree(BiPredicate<Key,Key> lt) {
        lthn = lt;
        root = null;
    }
    

    public Node<Key> search(Key key) {
        if (root == null) {
            return null;
        } else {
            return shelper(root, key);
        }
    }

    public Node<Key> insert(Key key) {
        if (this.root == null) {
            this.root = new AVLNode<Key>(key, 0);
            return root;
        } else  return insrthelper(root, key);

    }
    

    public void delete(Key key) {
        if (root != null) {
            dhelper(root, key);
        }
    }

    private int h(AVLNode<Key> n) {
        if (n == null) { return -1; }
        else return n.h;
    }

    private void lrot(AVLNode<Key> x) {
        AVLNode<Key> y = x.r;
        AVLNode<Key> Z= y.l;
        
        x.r = Z;
        Z.p = x;
        y.l = x;
        x.p = y;
        
        x.h = 1 + Math.max(h(x.l), h(x.r));
        y.h = 1 + Math.max(h(y.l), h(y.r));

        if (x.p != null && x.p.r == x) {
            x.p.r = y;
        } else if (x.p != null && x.p.l == x) {
            x.p.l = y;
        } else  root = y;
  
    }

    private void rrot(AVLNode<Key> y) {
        AVLNode<Key> x = y.l;
        AVLNode<Key> Z= x.r;
        
        x.r = y;
        y.p = x;
        y.l = Z;
        Z.p = y;
        
        x.h = 1 + Math.max(h(x.l), h(x.r));
        y.h = 1 + Math.max(h(y.l), h(y.r));

        if (y.p != null && y.p.r == y) {
            y.p.r = x;
        } else if (y.p != null && y.p.l == y) {
            y.p.l = x;
        } else root = x;
    }
        
    private int balance(AVLNode<Key> node){
        int x, y;
        if (node.r != null) {
            x = h(node.r);
        } else {
            x = -1;
        }
        if (node.l != null) {
            y = h(node.l);
        } else y = -1;
        return (x - y);
    }

    private void rbal(AVLNode<Key> node){
        if (balance(node) < -1 || balance(node) > 1) {
            if (h(node.l) > h(node.r)) {
                if (h(node.l.l) > h(node.l.r)) {
                    rrot(node);
                    if (node.p != null) {
                        rbal(node.p);
                    }
                } else {
                    lrot(node.l);
                    rrot(node);
                    if (node.p != null) {
                        rbal(node.p);
                    }
                }
            } else {
                if (h(node.r) > h(node.l)) {
                    lrot(node);
                    if (node.p != null) {
                        rbal(node.p);
                    }
                } else {
                    rrot(node.r);
                    lrot(node);
                    if (node.p != null) {
                        rbal(node.p);
                    }
                }
            }
        }
    }
    
    Node<Key> shelper(AVLNode<Key> node, Key key) {
        if (lthn.test(node.key, key)) {
            if (node.r != null) {
                return shelper(node.r, key);
            } else {
                return null;
            }
        } else if (lthn.test(key, node.key)) {
            if (node.l != null) {
                return shelper(node.l, key);
            } else {
                return null; 
            }
        } else {
            return node;
        }
    }

    private Node<Key> insrthelper(AVLNode<Key> node, Key key) {
        if (node.key == null) {
            node.key = key;
            return node;
        }
        if (lthn.test(node.key, key)) {
            if (node.r == null) {
                AVLNode<Key> temp = new AVLNode<Key>(key, 0);
                node.r = temp;
                temp.p = node;
                rbal(temp);
                return temp;
            } else {
                return insrthelper(node.r, key);
            }
        } else {
            if (node.l == null) {
                AVLNode<Key> temp = new AVLNode<Key>(key, 0);
                node.l = temp;
                temp.p = node;
                rbal(node);
                return temp;
            } else {
                return insrthelper(node.l, key);
            }
        }               
    }



    void dhelper(AVLNode<Key> node, Key key) {
        if (lthn.test(node.key, key)) {
            if (node.l != null) {
                dhelper(node.l, key);
            }
        } else if (lthn.test(key, node.key)) {
            if (node.r != null) {
                dhelper(node.r, key);
            }
        } else {
            if (node.l != null && node.r != null) {
                AVLNode<Key> temp = (AVLNode<Key>) node.r.min();
                node.key = temp.key;
                dhelper(node.r, key);
            } else if (node.p.l == node) {
            	if(node.l != null) {
            		node.p.l = node.l;
            	}else node.p.l = node.r;
            } else if (node.p.r == node) {
            	if(node.l != null){
            		node.p.r = node.l;
            	} else node.p.r = node.r;
            }
        }
    }

    
}