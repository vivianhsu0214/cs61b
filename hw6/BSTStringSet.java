import java.util.*;

/**
 * Implementation of a BST based String Set.
 * @author Zhibo Fan
 */
public class BSTStringSet implements SortedStringSet, Iterable<String> {
    /** Creates a new empty set. */
    public BSTStringSet() {
        root = null;
    }

    @Override
    public boolean contains(String s) {
        Node last = find(s);
        return last != null && s.equals(last.s);
    }

    @Override
    public void put(String s) {
        root = putHelper(s, root);
    }

    private Node putHelper(String s, Node n) {
        if (n == null) {
            return new Node(s);
        }
        int cmp = s.compareTo(n.s);
        if (cmp < 0) {
            n.left = putHelper(s, n.left);
        } else {
            n.right = putHelper(s, n.right);
        }
        return n;
    }

    @Override
    public List<String> asList() {
        ArrayList<String> result = new ArrayList<>();
        for (String label : this) {
            result.add(label);
        }
        return result;
    }

    @Override
    public Iterator<String> iterator() {
        return new BSTIterator(root);
    }

    @Override
    public Iterator<String> iterator(String low, String high) {
        return new BSTRange(asList(), low, high);  // FIXME
    }

    /** Return either the node in this BST that contains S, or, if
     *  there is no such node, the node that should be the parent
     *  of that node, or null if neither exists. */
    private Node find(String s) {
        if (root == null) {
            return null;
        }
        Node p;
        p = root;
        while (true) {
            int c = s.compareTo(p.s);
            Node next;
            if (c < 0) {
                next = p.left;
            } else if (c > 0) {
                next = p.right;
            } else {
                return p;
            }
            if (next == null) {
                return p;
            } else {
                p = next;
            }
        }
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

    /** An iterator over BSTs. */
    private static class BSTIterator implements Iterator<String> {
        /** Stack of nodes to be delivered.  The values to be delivered
         *  are (a) the label of the top of the stack, then (b)
         *  the labels of the right child of the top of the stack inorder,
         *  then (c) the nodes in the rest of the stack (i.e., the result
         *  of recursively applying this rule to the result of popping
         *  the stack. */
        private Stack<Node> _toDo = new Stack<>();

        /** A new iterator over the labels in NODE. */
        BSTIterator(Node node) {
            addTree(node);
        }

        BSTIterator(Node node, String low, String high) {
            addTree(node);
        }

        @Override
        public boolean hasNext() {
            return !_toDo.isEmpty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /** Add the relevant subtrees of the tree rooted at NODE. */
        private void addTree(Node node) {
            while (node != null) {
                _toDo.push(node);
                node = node.left;
            }
        }
    }

    private static class BSTRange implements Iterator<String> {

        private String L;
        private String U;
        private int start;
        private int end;
        private List<String> treelist;

        @SuppressWarnings("unchecked")
        BSTRange(List list, String down, String up) {
            U = up;
            L = down;
            treelist = list;
            start = findStart(0, treelist.size());
            end = findEnd(start, treelist.size());
        }

        private int findStart(int start, int end) {
            int mid = (start + end) / 2;
            String pivot = treelist.get(mid);
            if (pivot.compareTo(L) < 0) {
                return findStart(mid + 1, end);
            } else if (pivot.compareTo(U) > 0) {
                return findStart(start, mid);
            } else if (start >= end) {
                return -1;
            } else if (mid == 0
                    || treelist.get(mid - 1).compareTo(L) < 0) {
                return mid;
            } else {
                return findStart(start, mid);
            }
        }

        private int findEnd(int start, int end) {
            if (start == -1) {
                return -1;
            }
            int mid = (start + end) / 2;
            String pivot = treelist.get(mid);
            if (pivot.compareTo(L) < 0) {
                return findEnd(mid + 1, end);
            } else if (pivot.compareTo(U) > 0) {
                return findEnd(start, mid);
            } else if (start >= end) {
                return -1;
            } else if (mid == end - 1
                    || treelist.get(mid + 1).compareTo(U) > 0) {
                return mid + 1;
            } else {
                return findEnd(mid + 1, end);
            }
        }

        @Override
        public boolean hasNext() {
            return start < end;
        }

        @Override
        public String next() {
            return treelist.get(start++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // ADD A CLASS, PERHAPS?

    /** Root node of the tree. */
    private Node root;
}
