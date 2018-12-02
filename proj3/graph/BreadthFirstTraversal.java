package graph;

import java.util.Queue;
import java.util.ArrayDeque;

/**
 * Implements a breadth-first traversal of a graph.  Generally, the
 * client will extend this class, overriding the visit method as desired
 * (by default, it does nothing).
 *
 * @author Zhibo Fan
 */
public class BreadthFirstTraversal extends Traversal {

    /**
     * A breadth-first Traversal of G.
     */
    protected BreadthFirstTraversal(Graph G) {
        super(G, queue);
    }

    @Override
    protected boolean visit(int v) {
        return super.visit(v);
    }

    /**
     * A queue for DFS.
     */
    private static Queue<Integer> queue = new ArrayDeque<>();

}
