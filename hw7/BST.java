import java.util.LinkedList;
import java.util.Iterator;

/** A binary search tree with arbitrary Objects as keys.
 *  @author Zhibo Fan
 */
public class BST {
    /** Root of tree. */
    private BSTNode root;

    /** A BST containing the elements in the sorted list LIST. */
    public BST(LinkedList list) {
        root = linkedListToTree(list.iterator(), list.size());
    }

    /**
     * Provide a descriptive comment for this method here.
     */
    private BSTNode linkedListToTree(Iterator iter, int n) {
        // YOUR CODE HERE
        BSTNode newRoot = new BSTNode();
        if (n == 0) {
            return null;
        } else if (n == 1) {
            newRoot.item = iter.next();
        } else {
            newRoot.left = linkedListToTree(iter, n / 2);
            newRoot.item = iter.next();
            newRoot.right = linkedListToTree(iter, (n - 1) / 2);
        }
        return newRoot;
    }

    /**
     * Prints the tree to the console.
     */
    private void print() {
        print(root, 0);
    }

    /** Print NODE and its subtrees, indented D levels.  */
    private void print(BSTNode node, int d) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < d; i++) {
            System.out.print("  ");
        }
        System.out.println(node.item);
        print(node.left, d + 1);
        print(node.right, d + 1);
    }

    /**
     * Node for BST.
     */
    static class BSTNode {

        /** Item. */
        protected Object item;

        /** Left child. */
        protected BSTNode left;

        /** Right child. */
        protected BSTNode right;
    }
}
