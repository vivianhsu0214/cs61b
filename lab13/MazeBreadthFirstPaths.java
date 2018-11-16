import java.util.*;

/**
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private Maze maze;
    private int s;
    private int t;
    private ArrayList<Integer> list = new ArrayList<Integer>();

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        list.add(s);
        edgeTo[s] = s;
        distTo[s] = 0;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        while (!list.isEmpty()) {
            int current = pop();
            if (current == t) {
                return;
            }
            if (!marked[current]) {
                marked[current] = true;
                for (int w : maze.adj(current)) {
                    if (!marked[w]) {
                        edgeTo[w] = current;
                        distTo[w] = distTo[current] + 1;
                        list.add(w);
                    }
                }

            }
            announce();
        }
    }


    @Override
    public void solve() {
        bfs();
    }

    private int pop() {
        if (list.size() == 0) {
            throw new IllegalStateException();
        }
        return list.remove(0);
    }
}

