import java.util.function.BiPredicate;

public class BinarySearchTree<Key> implements SearchTree<Key> {
    private BSTNode<Key> root;
    BiPredicate<Key,Key> lthn;

    public BinarySearchTree(BiPredicate<Key,Key> lt) {
        lthn = lt;
        root = null;
    }

    public Node<Key> search(Key key) {
        if (root == null) {
            return null;
        } else {
            return root.search(key, lthn);
        }
    }
    
    public Node<Key> insert(Key key) {
        if (this.root == null) {
            this.root = new BSTNode<Key>(key);
            return root;
        } else {
            return this.root.insert(key, lthn);
        }
    }



    public void delete(Key key) {
        if (root != null) {
            root.delete(key, lthn);
        }
    }

}