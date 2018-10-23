import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
        int cmp = s.compareTo(root.s);
        if(cmp < 0) {
            root.left = put(s, root.left);
        } else if (cmp > 0) {
            root.right = put(s, root.right);
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
        return asListHelper(root);
    }

    private MyList asListHelper(Node n) {
        if (n == null) {
            return new MyList();
        }
        MyList cur = new MyList();
        cur.add(n.s);
        MyList l, r;
        l = asListHelper(n.left);
        r = asListHelper(n.right);
        l.addAll(cur);
        l.addAll(r);
        return l;
    }

    public class MyList implements List {
        private MyList() {
            _size = 0;
            _array = new String[MINIMUM_SIZE];
        }

        public Object pop() {
            return remove(_size - 1);
        }

        @Override
        public int size() {
            return _size;
        }

        @Override
        public boolean isEmpty() {
            return _size == 0;
        }

        private void expand() {
            String[] temp = new String[_array.length * 2];
            System.arraycopy(_array, 0, temp, 0, _array.length);
            _array = temp;
        }

        private boolean needExpand() {
            return _array.length == _size;
        }

        private void shrink() {
            String[] temp = new String[_size / 2];
            System.arraycopy(_array, 0, temp, 0, temp.length);
            _array = temp;
        }

        private boolean needShrink() {
            return _array.length > MINIMUM_SIZE
                    && _array.length <= _size / 2;
        }

        private String transfer(Object o) {
            return (String) o;
        }

        @Override
        public boolean contains(Object o) {
            for (int i = 0; i < _size; i++) {
                if (_array[i].equals(o)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Iterator iterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object[] toArray() {
            Object[] array = new Object[_size];
            System.arraycopy(_array, 0, array, 0, _size);
            return array;
        }

        @Override
        public Object[] toArray(Object[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Object o) {
            if (!(o instanceof String)) {
                return false;
            }
            if (needExpand()) {
                expand();
            }
            String obj = transfer(o);
            _array[_size] = obj;
            _size += 1;
            return true;
        }

        @Override
        public boolean remove(Object o) {
            if (_size == 0 || !contains(o)) {
                return false;
            }
            int index = 0;
            for (int i = 0; i < _size; i++) {
                if (_array[i].equals(o)) {
                    index = i;
                    break;
                }
            }
            System.arraycopy(_array, index + 1,
                    _array, index, _size - index - 1);
            _array[_size - 1] = null;
            _size -= 1;
            if (needShrink()) {
                shrink();
            }
            return true;
        }

        @Override
        public boolean containsAll(Collection c) {
            for (Object o : c) {
                if (!contains(o)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean addAll(Collection c) {
            Object[] temp = c.toArray();
            for (int i = 0; i < c.size(); i += 1) {
                Object o = temp[i];
                if (add(o)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean addAll(int index, Collection c) {
            int counter = 0;
            for (Object o : c) {
                if (counter < index) {
                    counter += 1;
                    continue;
                }
                if (add(o)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean removeAll(Collection c) {
            for (Object o : c) {
                if (!remove(o)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean retainAll(Collection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            _size = 0;
            _array = new String[MINIMUM_SIZE];
        }

        @Override
        public Object get(int index) {
            if (index >= _size) {
                return null;
            }
            return _array[index];
        }

        @Override
        public Object set(int index, Object element) {
            if (!(element instanceof String)) {
                return null;
            }
            if (index >= _size) {
                if (needExpand()) {
                    expand();
                }
                _size = index + 1;
            }
            _array[index] = (String) element;
            return element;
        }

        @Override
        public void add(int index, Object element) {
            if (!(element instanceof String)) {
                return;
            }
            if (index >= _size) {
                if (needExpand()) {
                    expand();
                }
                _size = index + 1;
            }
            _array[index] = (String) element;
        }

        @Override
        public Object remove(int index) {
            if (index >= _size) {
                return null;
            }
            Object result = _array[index];
            return (remove(result) ? result : null);
        }

        @Override
        public int indexOf(Object o) {
            for (int i = 0; i < _size; i++) {
                if (_array[i].equals(o)) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            for (int i = _size; i > 0; i++) {
                if (_array[i - 1].equals(o)) {
                    return i - 1;
                }
            }
            return -1;
        }

        @Override
        public ListIterator listIterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ListIterator listIterator(int index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List subList(int fromIndex, int toIndex) {
            throw new UnsupportedOperationException();
        }

        private int _size;

        private int MINIMUM_SIZE = 4;

        private String[] _array;
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
