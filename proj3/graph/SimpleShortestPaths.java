package graph;

import java.util.HashMap;

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Zhibo Fan
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        _predecessor = new HashMap<>();
        _weight = new HashMap<>();
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        if (_G.contains(v)) {
            return _weight.get(v);
        } else {
            return Double.MAX_VALUE;
        }
    }

    @Override
    protected void setWeight(int v, double w) {
        _weight.put(v, w);
    }

    @Override
    public int getPredecessor(int v) {
        if (_G.contains(v)) {
            return _predecessor.get(v);
        } else {
            return 0;
        }
    }

    @Override
    protected void setPredecessor(int v, int u) {
        _predecessor.put(v, u);
    }

    /**
     * The hash map stores the predecessor of each vertex.
     */
    private HashMap<Integer, Integer> _predecessor;

    /**
     * The hash map stores the weight of each vertex.
     */
    private HashMap<Integer, Double> _weight;

}
