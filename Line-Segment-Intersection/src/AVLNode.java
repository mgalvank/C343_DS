import java.util.function.BiPredicate;

class AVLNode<Key> implements Node<Key> {

    AVLNode<Key> l;
    AVLNode<Key> r;
    AVLNode<Key> p;
    Key key;
    int h;

    public AVLNode(Key key, int h) {
        this.l = null;
        this.r = null;
        this.p = null;
        this.key = key;
        this.h = h;
    }

    @Override
    public Node<Key> after() {
        AVLNode<Key> n = (AVLNode<Key>) this;
            
        if (n.r != null) {
            return n.r.min();
        }

        AVLNode<Key> y = n.p;
        AVLNode<Key> x = n;
        while (y != null && x == y.r) {
            x = y;
            y = y.p;
        }
        return y;
    }

    @Override
    public Node<Key> before() {
        if (this.l != null) {
            return this.l.max();
        }

        AVLNode<Key> y = this.p;
        AVLNode<Key> x = this;

        while (y != null && x == y.l) {
            x = y;
            y = y.p;
        }
        return y;
    }

    Node<Key> min() {
        if (this.l == null)
            return this;
        else
            return this.l.min();
    }

    Node<Key> max() {
        if (this.r == null) {
            return this;
        } else return this.r.max();

    }


    public Key getKey() {
        return this.key;
    }

}
