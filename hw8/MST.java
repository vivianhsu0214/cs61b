import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

/** Minimal spanning tree utility.
 *  @author Zhibo Fan
 */
public class MST {

    /** Given an undirected, weighted, connected graph whose vertices are
     *  numbered 1 to V, and an array E of edges, returns an array of edges
     *  in E that form a minimal spanning tree of the input graph.
     *  Each edge in E is a three-element int array of the form (u, v, w),
     *  where 0 < u < v <= V are vertex numbers, and 0 <= w is the weight
     *  of the edge. The result is an array containing edges from E.
     *  Neither E nor the arrays in it may be modified.  There may be
     *  multiple edges between vertices.  The objects in the returned array
     *  are a subset of those in E (they do not include copies of the
     *  original edges, just the original edges themselves.) */
    public static int[][] mst(int V, int[][] E) {
        int[][] replica = Arrays.copyOf(E, E.length);
        Arrays.sort(replica, EDGE_WEIGHT_COMPARATOR);
        int ec = 0;
        int i = 0;
        int[][] r = new int[V-1][3];
        UnionFind uf = new UnionFind(V);
        while (ec < V - 1) {
            int[] rep = replica[i++];
            int u = rep[0], v = rep[1];
            if (!uf.samePartition(u, v)) {
                uf.union(u, v);
                r[ec++] = rep;
            }
        }
        return r;
    }

    /** An ordering of edges by weight. */
    private static final Comparator<int[]> EDGE_WEIGHT_COMPARATOR =
        new Comparator<int[]>() {
            @Override
            public int compare(int[] e0, int[] e1) {
                return e0[2] - e1[2];
            }
        };

}
