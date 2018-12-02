package graph;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import ucb.junit.textui;

/** Unit tests for the Traversal class.
 *  @author Zhibo Fan
 */
public class TraversalTest {

    /**
     * Testing time limit.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    private DirectedGraph _directed;

    private BreadthFirstTraversal _breadth;

    private DepthFirstTraversal _depth;

    /** Create new directed and undirected graphs before each test. */
    @Before
    public void setUp() {
        _directed = new DirectedGraph();
        for (int i = 1; i <= 6; i += 1) {
            _directed.add();
        }
        _directed.add(1, 2);
        _directed.add(1, 5);
        _directed.add(1, 6);
        _directed.add(2, 4);
        _directed.add(4, 3);
        _directed.add(4, 5);
        _directed.add(6, 5);

        _breadth = new BreadthFirstTraversal(_directed) {
            char[] characters = new char[] {'0', 'A', 'B', 'C', 'D', 'E', 'F'};

            @Override
            protected boolean visit(int v) {
                System.out.println("[BFS] Visiting " + characters[v]);
                return super.visit(v);
            }

            @Override
            protected boolean postVisit(int v) {
                System.out.println("[BFS] Post visiting " + characters[v]);
                return super.postVisit(v);
            }

            @Override
            protected boolean shouldPostVisit(int v) {
                return true;
            }
        };
        _depth = new DepthFirstTraversal(_directed) {
            char[] characters = new char[] {'0', 'A', 'B', 'C', 'D', 'E', 'F'};

            @Override
            protected boolean visit(int v) {
                System.out.println("[DFS] Visiting " + characters[v]);
                return super.visit(v);
            }

            @Override
            protected boolean postVisit(int v) {
                System.out.println("[DFS] Post visiting " + characters[v]);
                return super.postVisit(v);
            }
        };
    }


    /** Run the JUnit tests in this package. */
    public static void main(String[] ignored) {
        textui.runClasses(TraversalTest.class);
    }

    @Test
    public void empty() {
        _breadth.traverse(1);
        System.out.println();
        _depth.traverse(1);
    }
}
