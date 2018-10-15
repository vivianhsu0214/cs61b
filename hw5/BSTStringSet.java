import java.util.List;
import java.util.ArrayList;

/**
 * Implementation of a BST based String Set.
 * @author Zhibo Fan
 */
public class BSTStringSet implements StringSet {
    /** Creates a new empty set. */
    public BSTStringSet() {
        root = null;
    }

    @Override
    public void put(String s) {
        root = put(s, root);
    }

    public Node put(String s, Node root) {
        if (root == null) {
            return new Node(s);
        }
        int cmp = s.compareTo(s);
        if(cmp < 0) {
            return put(s, root.left);
        } else if (cmp > 0) {
            return put(s, root.right);
        }
        return root;
    }

    @Override
    public boolean contains(String s) {
        return contains(s, root);
    }

    public boolean contains(String s, Node root) {
        if (root == null) {
            return false;
        }
        int cmp = s.compareTo(root.s);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return contains(s, root.left);
        } else {
            return contains(s, root.right);
        }
    }

    @Override
    public List<String> asList() {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(root);
        while(!queue.isEmpty()) {
            Node current = queue.remove(0);
            result.add(current.s);
            if (current.left != null) {
                queue.add(root.left);
            }
            if (current.right != null) {
                queue.add(root.right);
            }
        }
        for (int i = 0; i < result.size(); i++) {
            for (int j = i; j < result.size(); j++) {
                if (result.get(i).compareTo(result.get(j)) < 0) {
                    String temp = result.get(i);
                    result.set(i, result.get(j));
                    result.set(j, temp);
                }
            }
        }
        return result;
    }

    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }
    }

    /** Root node of the tree. */
    private Node root;
}
