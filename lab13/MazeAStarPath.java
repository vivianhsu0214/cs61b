import java.util.Comparator;
import java.util.Observable;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 *  @author Josh Hug
 */

public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private PriorityQueue<Integer> pq;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        pq = new PriorityQueue<>(maze.V(), new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(h(o1), h(o2));
            }
        });
        pq.add(s);
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t))
                + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Performs an A star search from vertex s. */
    private void astar() {
        while (!pq.isEmpty()) {
            int current = pq.poll();
            if (current == t) {
                return;
            }
            if (!marked[current]) {
                marked[current] = true;
                for (int w : maze.adj(current)) {
                    if (!marked[w]) {
                        edgeTo[w] = current;
                        distTo[w] = distTo[current] + 1;
                        pq.add(w);
                    }
                }

            }
            announce();
        }
    }

    @Override
    public void solve() {
        astar();
    }

}

