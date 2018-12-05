package graph;

import java.util.LinkedList;
import java.util.List;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Comparator;

/**
 * The shortest paths through an edge-weighted graph.
 * By overrriding methods getWeight, setWeight, getPredecessor, and
 * setPredecessor, the client can determine how to represent the weighting
 * and the search results.  By overriding estimatedDistance, clients
 * can search for paths to specific destinations using A* search.
 *
 * @author Zhibo Fan
 */
public abstract class ShortestPaths {

    /**
     * The shortest paths in G from SOURCE.
     */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /**
     * A shortest path in G from SOURCE to DEST.
     */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
    }

    /**
     * Initialize the shortest paths.  Must be called before using
     * getWeight, getPredecessor, and pathTo.
     */
    public void setPaths() {
        _path = new LinkedList<>();
        _astar = new AStarTraversal(_G, new MyPriorityQueue());
        for (Integer v : _G.vertices()) {
            setWeight(v, Double.MAX_VALUE);
            setPredecessor(v, 0);
        }
        setWeight(getSource(), 0);
        _astar.traverse(getSource());
    }

    /**
     * Returns the starting vertex.
     */
    public int getSource() {
        return _source;
    }

    /**
     * Returns the target vertex, or 0 if there is none.
     */
    public int getDest() {
        return _dest;
    }

    /**
     * Returns the current weight of vertex V in the graph.  If V is
     * not in the graph, returns positive infinity.
     */
    public abstract double getWeight(int v);

    /**
     * Set getWeight(V) to W. Assumes V is in the graph.
     */
    protected abstract void setWeight(int v, double w);

    /**
     * Returns the current predecessor vertex of vertex V in the graph, or 0 if
     * V is not in the graph or has no predecessor.
     */
    public abstract int getPredecessor(int v);

    /**
     * Set getPredecessor(V) to U.
     */
    protected abstract void setPredecessor(int v, int u);

    /**
     * Returns an estimated heuristic weight of the shortest path from vertex
     * V to the destination vertex (if any).  This is assumed to be less
     * than the actual weight, and is 0 by default.
     */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /**
     * Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     * not in the graph, returns positive infinity.
     */
    protected abstract double getWeight(int u, int v);

    /**
     * Returns a list of vertices starting at _source and ending
     * at V that represents a shortest path to V.  Invalid if there is a
     * destination vertex other than V.
     */
    public List<Integer> pathTo(int v) {
        while (v != getSource()) {
            _path.addFirst(v);
            v = getPredecessor(v);
        }
        return _path;
    }

    /**
     * Returns a list of vertices starting at the source and ending at the
     * destination vertex. Invalid if the destination is not specified.
     */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /**
     * The graph being searched.
     */
    protected final Graph _G;
    /**
     * The starting vertex.
     */
    private final int _source;
    /**
     * The target vertex.
     */
    private final int _dest;
    /**
     * The path as a LinkedList.
     */
    private LinkedList<Integer> _path;
    /**
     * The traversal object.
     */
    private AStarTraversal _astar;

    /**
     * A star traversal.
     */
    private class AStarTraversal extends Traversal {
        /**
         * Generate an astar traversal.
         * @param g graph object
         * @param fringe fringe as a priority queue
         */
        AStarTraversal(Graph g, MyPriorityQueue fringe) {
            super(g, fringe);
        }

        @Override
        public boolean visit(int v) {
            if (v == getDest()) {
                return false;
            } else {
                for (int w : _G.successors(v)) {
                    double dist = getWeight(v, w);
                    double weightV = getWeight(v);
                    double weightW = getWeight(w);
                    if (weightW > dist + weightV) {
                        setWeight(w, weightV + dist);
                        setPredecessor(w, v);
                    }
                }
            }
            return true;
        }
    }

    /**
     * A data structure of priority queue wrapping treeset.
     */
    private class MyPriorityQueue extends AbstractQueue<Integer> {

        @Override
        public int size() {
            return ts.size();
        }

        @Override
        public Iterator<Integer> iterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean offer(Integer e) {
            Vertex vertex = new Vertex(e, getWeight(e) + estimatedDistance(e));
            ts.add(vertex);
            ll.add(vertex);
            return true;
        }

        @Override
        public Integer poll() {
            Vertex vertex = ts.pollFirst();
            for (int i = 0; i < ll.size(); i++) {
                if (ll.get(i) == vertex) {
                    ll.remove(vertex);
                    break;
                }
            }
            return vertex.getVertex();
        }

        @Override
        public Integer peek() {
            return ts.first().getVertex();
        }

        /**
         * Interface for change elements' priority.
         * @param vertex vertex index
         * @param heuristic new heuristic value
         */
        public void changeValue(int vertex, int heuristic) {
            Vertex v = null;
            for (int i = 0; i < ll.size(); i++) {
                if (ll.get(i).getVertex() == vertex) {
                    v = ll.get(i);
                    break;
                }
            }
            if (v == null) {
                return;
            }
            v.setHeuristic(heuristic);
        }

        /**
         * Wrapped TreeSet.
         */
        private TreeSet<Vertex> ts
                = new TreeSet<Vertex>(new VertexComparator());

        /**
         * LinkedList storing all the Vertices
         * which are identical to those in TreeSet.
         */
        private LinkedList<Vertex> ll = new LinkedList<>();

        /**
         * Comparator for vertex.
         */
        private class VertexComparator implements Comparator<Vertex> {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return Double.compare(o1.getHeurisitc(), o2.getHeurisitc());
            }
        }

        /**
         * Vertex class, mapping a vertex number and its heuristic.
         */
        private class Vertex {
            /**
             * Vertex number.
             */
            private int _vertex;

            /**
             * Heuristic value.
             */
            private double _heuristic;

            /**
             * Constructor.
             * @param vertex vertex index
             * @param heuristic heuristic value
             */
            Vertex(int vertex, double heuristic) {
                _vertex = vertex;
                _heuristic = heuristic;
            }

            /**
             * Getter for vertex number.
             *
             * @return vertex number
             */
            public int getVertex() {
                return _vertex;
            }

            /**
             * Setter for vertex number.
             *
             * @param vertex new vertex number
             */
            public void setVertex(int vertex) {
                _vertex = vertex;
            }

            /**
             * Getter for heuristic.
             *
             * @return heuristic
             */
            public double getHeurisitc() {
                return _heuristic;
            }

            /**
             * Setter for heuristic.
             *
             * @param heurisitc new heuristic value
             */
            public void setHeuristic(double heurisitc) {
                _heuristic = heurisitc;
            }

            @Override
            public int hashCode() {
                return _vertex + (int) _heuristic;
            }

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Vertex)) {
                    return false;
                }
                return ((Vertex) o).getVertex() == _vertex;
            }
        }
    }

}
