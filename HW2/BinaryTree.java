package BST;

import java.util.Stack;

class Node {
 
    int data;
    Node left, right;
 
    Node(int d) {
        data = d;
        left = right = null;
    }
}
 
class BinaryTree {
    static int count = 0;
    static Node root;
   	
    public static int smallcount(Node node,int k){
    	
    	if(node == null){
    		return count;
    	}
    	
    	if(node.left != null){
    		smallcount(node.left,k);
    	}
    	
    	if(node.data <= k){
    		count++;
    	}
    	
    	if(node.data > k){
    		return count;
    	}
    	
    	if(node.right != null){
    		smallcount(node.right,k);
    	}
    	return count; 	
    }
    
 
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        int k = 22;
        tree.root = new Node(20);
        tree.root.left = new Node(8);
        tree.root.right = new Node(22);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(12);
 
        smallcount(root,k);
        System.out.println("The number of nodes having key values less than or equal to "+k+" is :- "+count);
        

        
    }
}