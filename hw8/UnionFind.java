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
        _union = new int[N + 1];
        _sizes = new int[N + 1];
        for (int i = 1; i < N + 1; i++) {
            _union[i] = i;
            _sizes[i] = 1;
        }
        _compressed = new int[Integer.SIZE - Integer.numberOfLeadingZeros(N)];
    }

    /** Return the representative of the partition currently containing V.
     *  Assumes V is contained in one of the partitions.  */
    public int find(int v) {
        int count = 0;
        while (_union[v] != v) {
            _compressed[count++] = v;
            v = _union[v];
        }
        while (count > 0) {
            _union[_compressed[--count]] = v;
        }
        return v;
    }

    /** Return true iff U and V are in the same partition. */
    public boolean samePartition(int u, int v) {
        return find(u) == find(v);
    }

    /** Union U and V into a single partition, returning its representative. */
    public int union(int u, int v) {
        u = find(u);
        v = find(v);
        if (u == v) {
            return u;
        } else if (_sizes[u] < _sizes[v]) {
            _union[u] = v;
            _sizes[v] += _sizes[u];
            return v;
        } else {
            _union[v] = u;
            _sizes[u] += _sizes[v];
            return v;
        }
    }

    private int[] _union;

    private int[] _sizes;

    private int[] _compressed;


}
