import java.util.*;

/** A partition of a set of contiguous integers that allows (a) finding whether
 *  two integers are in the same partition set and (b) replacing two partitions
 *  with their union.  At any given time, for a structure partitioning
 *  the integers 1-N, each partition is represented by a unique member of that
 *  partition, called its representative.
 *  @author Zhibo Fan
 */
public class UnionFind {

    /** A union-find structure consisting of the sets { 1 }, { 2 }, ... { N }.
     */
    public UnionFind(int N) {
        ArrayList<UnionNode> list = new ArrayList<>();
        for (int i = 1; i < N + 1; i++) {
            UnionNode n = new UnionNode(i, null);
            list.add(n);
        }
        union = new UnionTree(list);
    }

    /** Return the representative of the partition currently containing V.
     *  Assumes V is contained in one of the partitions.  */
    public int find(int v) {
        UnionTree temp = new UnionTree(union);
        for (int i = 1; i < v + 1; i++) {
            temp.compress(i);
        }
        UnionNode n = temp.getNode(v);
        while (n.getParent() != null) {
            n = n.getParent();
        }
        return n.getValue();
    }

    /** Return true iff U and V are in the same partition. */
    public boolean samePartition(int u, int v) {
        return find(u) == find(v);
    }

    /** Union U and V into a single partition, returning its representative. */
    public int union(int u, int v) {
        int root = find(u);
        int subRoot = find(v);
        union.getNode(subRoot).changeParent(union.getNode(root));
        return root;
    }

    private UnionTree union;

    private class UnionNode{
        UnionNode(int value) {
            _value = value;
        }

        UnionNode(int value, UnionNode parent) {
            _value = value;
            _parent = parent;
        }

        public void changeParent(UnionNode parent) {
            _parent = parent;
        }

        public int getValue() {
            return _value;
        }

        public UnionNode getParent() {
            return _parent;
        }

        public int getParentValue() {
            return _parent._value;
        }
        
        private int _value;
        private UnionNode _parent;
    }

    private class UnionTree{
        UnionTree(UnionNode ... nodes) {
            for (UnionNode n : nodes) {
                _set.put(n._value, n);
            }
        }

        UnionTree(List<UnionNode> nodes) {
            for (UnionNode n : nodes) {
                _set.put(n._value, n);
            }
        }

        UnionTree(UnionTree origin) {
            for (Integer v : origin._set.keySet()) {
                UnionNode n = origin._set.get(v);
                _set.put(v, new UnionNode(v, n._parent));
            }
        }

        public void add(UnionNode n) {
            _set.put(n._value, n);
        }

        public UnionNode getNode(int value) {
            if (_set.containsKey(value)) {
                return _set.get(value);
            } else {
                return null;
            }
        }

        public void compress(int value) {
            UnionNode n = getNode(value);
            if (n == null || n._parent == null) {
                return;
            }
            n._parent = n._parent;
        }

        private HashMap<Integer, UnionNode> _set
                = new HashMap<>();
    }
}
