package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;

/**
 * A partial implementation of Graph containing elements common to
 * directed and undirected graphs.
 *
 * @author Zhibo Fan
 */
abstract class GraphObj extends Graph {

    /**
     * A new, empty Graph.
     */
    GraphObj() {
    }

    @Override
    public int vertexSize() {
        return _vertex.size();
    }

    @Override
    public int maxVertex() {
        if (_vertex.size() == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for (int v : _vertex) {
            max = Integer.max(max, v);
        }
        return max;
    }

    @Override
    public int edgeSize() {
        return _edges.size();
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (!_vertex.contains(v)) {
            return 0;
        }
        int rtn = 0;
        for (int[] edge : _edges) {
            if (isDirected() && edge[0] == v) {
                rtn += 1;
            } else if (!isDirected()
                    && (edge[0] == v || edge[1] == v)) {
                rtn += 1;
            }
        }
        return rtn;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return _vertex.contains(u);
    }

    @Override
    public boolean contains(int u, int v) {
        if (!(contains(u) && contains(v))) {
            return false;
        }
        for (int[] edge : _edges) {
            if ((edge[0] == u && edge[1] == v)
                    || (edge[1] == u && edge[0] == v && !isDirected())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int add() {
        if (_vertex.size() == 0) {
            _vertex.add(1);
            return 1;
        }
        int[] sorted = new int[_vertex.size()];
        for (int i = 0; i < _vertex.size(); i++) {
            sorted[i] = _vertex.get(i);
        }
        Arrays.sort(sorted);
        if (sorted[0] != 1) {
            _vertex.add(1);
            return 1;
        } else {
            int last = sorted[0];
            for (int i = 1; i < _vertex.size(); last = sorted[i++]) {
                if (sorted[i] > last + 1) {
                    _vertex.add(last + 1);
                    return last + 1;
                }
            }
        }
        int newVertex = sorted[sorted.length - 1] + 1;
        _vertex.add(newVertex);
        return newVertex;
    }

    @Override
    public int add(int u, int v) {
        int[] newEdge = new int[]{u, v};
        if (!(_vertex.contains(u) && _vertex.contains(v))) {
            return 0;
        }
        if (!isDirected() && contains(u, v)) {
            return 0;
        } else if (isDirected()) {
            if (_edges.contains(newEdge)) {
                return 0;
            }
        }
        _edges.add(newEdge);
        return _edges.size();
    }

    @Override
    public void remove(int v) {
        if (_vertex.contains(v)) {
            _vertex.remove(Integer.valueOf(v));
            ArrayList<int[]> toRemove = new ArrayList<>();
            for (int i = 0; i < edgeSize(); i++) {
                if (_edges.get(i)[0] == v
                        || _edges.get(i)[1] == v) {
                    toRemove.add(_edges.get(i));
                }
            }
            for (int[] edge : toRemove) {
                _edges.remove(edge);
            }
        }
    }

    @Override
    public void remove(int u, int v) {
        int[] edge = new int[]{u, v};
        if (isDirected() && _edges.contains(edge)) {
            _edges.remove(edge);
        } else if (!isDirected()) {
            int[] anotherEdge = new int[]{v, u};
            if (_edges.contains(edge)) {
                _edges.remove(edge);
            }
            if (_edges.contains(anotherEdge)) {
                _edges.remove(anotherEdge);
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        return new VertexIteration();
    }

    @Override
    public Iteration<Integer> successors(int v) {
        return new VertexIteration(true, v);
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        return new EdgeIteration();
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!contains(v)) {
            throw new IllegalArgumentException("error: vertex not from Graph");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        if (!isDirected()) {
            for (int i = 0; i < edgeSize(); i++) {
                if ((_edges.get(i)[0] == u && _edges.get(i)[1] == v)
                        || (_edges.get(i)[0] == v && _edges.get(i)[1] == u)) {
                    return i + 1;
                }
            }
        } else {
            int[] edge = new int[]{u, v};
            for (int i = 0; i < edgeSize(); i++) {
                if (_edges.get(i)[0] == edge[0]
                        && _edges.get(i)[1] == edge[1]) {
                    return i + 1;
                }
            }
        }
        return 0;
    }

    /**
     * Iteration class of vertex.
     */
    protected class VertexIteration extends Iteration<Integer> {
        /**
         * Initialize an iterator of all vertices.
         */
        public VertexIteration() {
            iter = _vertex.iterator();
        }

        /**
         * Initialize an iterator of a successor or predecessor of a vertex.
         *
         * @param isFrom is it a successor or predecessor iterator
         * @param u      the vertex
         */
        public VertexIteration(boolean isFrom, int u) {
            if (!isDirected()) {
                for (int[] edge : _edges) {
                    if (edge[0] == u) {
                        wrapped.add(edge[1]);
                    } else if (edge[1] == u) {
                        wrapped.add(edge[0]);
                    }
                }
            } else {
                for (int[] edge : _edges) {
                    if (isFrom && edge[0] == u) {
                        wrapped.add(edge[1]);
                    } else if (!isFrom && edge[1] == u) {
                        wrapped.add(edge[0]);
                    }
                }
            }
            iter = wrapped.iterator();
        }

        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        @Override
        public Integer next() {
            return iter.next();
        }

        /**
         * Wrapped array.
         */
        private ArrayList<Integer> wrapped = new ArrayList<>();

        /**
         * Wrapped iterator of the arraylist.
         */
        private Iterator<Integer> iter;
    }

    /**
     * Iteration for edges.
     */
    protected class EdgeIteration extends Iteration<int[]> {
        /**
         * Initialize an iterator of all edges.
         */
        public EdgeIteration() {
            iter = _edges.iterator();
        }

        /**
         * Initialize an iterator of in-degree or out-degree edges.
         *
         * @param isFrom from the vertex u or to u
         * @param u      the vertex
         */
        public EdgeIteration(boolean isFrom, int u) {
            if (!isDirected()) {
                for (int[] edge : _edges) {
                    if (edge[0] == u || edge[1] == u) {
                        wantedEdges.add(edge);
                    }
                }
            } else {
                for (int[] edge : _edges) {
                    if (isFrom && edge[0] == u) {
                        wantedEdges.add(edge);
                    } else if (!isFrom && edge[1] == u) {
                        wantedEdges.add(edge);
                    }
                }
            }
            iter = wantedEdges.iterator();
        }

        @Override
        public int[] next() {
            return iter.next();
        }

        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        /**
         * Wrapped iterator.
         */
        private Iterator<int[]> iter;

        /**
         * A list of legal edges.
         */
        private ArrayList<int[]> wantedEdges = new ArrayList<>();
    }

    /**
     * A list of edges.
     */
    protected ArrayList<int[]> _edges = new ArrayList<>();

    /**
     * A list of vertex.
     */
    protected ArrayList<Integer> _vertex = new ArrayList<>();

}
