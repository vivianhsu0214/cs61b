import java.util.Observable;
import java.util.Stack;

/**
 *  @author Josh Hug
 */

public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */


    private Maze maze;
    private Stack<Integer> stack = new Stack<Integer>();
    private int num = 0;
    private int[] fakeEdge;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        stack.push(0);
        distTo[0] = 0;
        fakeEdge = new int[m.N() *  m.N()];
        fakeEdge[0] = 0;
    }

    @Override
    public void solve() {
        while (!stack.isEmpty()) {
            announce();
            int current = stack.pop();

            if (num == maze.V()) {
                return;
            }
            if (!marked[current]) {
                marked[current] = true;
                for (int w : maze.adj(current)) {
                    if (fakeEdge[current] != w && marked[w]) {
                        int iter = current;
                        while (fakeEdge[iter] != w) {
                            edgeTo[iter] = fakeEdge[iter];
                            iter = edgeTo[iter];
                        }
                        edgeTo[iter] = w;
                        edgeTo[w] = current;
                        announce();
                        return;
                    } else if (!marked[w]) {
                        fakeEdge[w] = current;
                        distTo[w] = distTo[current] + 1;
                        stack.add(w);
                    }
                }
            }
        }
    }

    // Helper methods go here
}

