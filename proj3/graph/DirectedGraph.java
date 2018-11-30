package graph;

/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Zhibo Fan
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        if (!_vertex.contains(v)) {
            return 0;
        }
        int rtn = 0;
        for (int[] edge : _edges) {
            if (edge[1] == v) {
                rtn += 1;
            }
        }
        return rtn;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        return new VertexIteration(false, v);
    }

}
