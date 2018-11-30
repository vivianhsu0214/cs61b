package graph;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Graph class.
 *
 * @author Zhibo Fan
 */
public class GraphTest {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void addTest() {
        DirectedGraph g = new DirectedGraph();
        assertEquals(0, g.maxVertex());
        g.add();
        assertEquals(1, g.maxVertex());
        for (int i = 0; i < 3; i++) {
            g.add();
        }
        assertEquals(4, g.maxVertex());
        g.remove(2);
        assertEquals(2, g.add());
    }

    @Test
    public void addEdgeDirectiveTest() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        assertEquals(0, g.add(1, 6));
        assertEquals(1, g.add(1, 2));
        assertTrue(g.contains(1, 2));
        assertFalse(g.contains(2, 1));
        assertEquals(2, g.add(2, 1));
    }

    @Test
    public void addEdgeUndirectiveTest() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        assertEquals(0, g.add(1, 6));
        assertEquals(1, g.add(1, 2));
        assertTrue(g.contains(1, 2));
        assertTrue(g.contains(2, 1));
        assertEquals(0, g.add(2, 1));
    }

    @Test
    public void degreeDirectiveTest() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);
        g.add(2, 1);
        assertEquals(3, g.outDegree(1));
        assertEquals(1, g.inDegree(1));
    }

    @Test
    public void degreeUndirectiveTest() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);
        g.add(2, 1);
        assertEquals(3, g.degree(1));
        assertEquals(3, g.inDegree(1));
        assertEquals(3, g.outDegree(1));
    }

    @Test
    public void vertexIterationTest() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        g.remove(2);
        Iteration<Integer> iter = g.vertices();
        for (int i = 0; i < 4; i++) {
            assertTrue(iter.hasNext());
            int nextInt = iter.next();
            assertNotEquals(2, nextInt);
        }
        assertFalse(iter.hasNext());
        iter = g.vertices();
        for (int v : iter) {
            assertNotEquals(2, v);
        }
    }

    @Test
    public void edgeIterationTest() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);
        g.add(2, 1);
        Iteration<int[]> iter = g.edges();
        for (int i = 0; i < 4; i++) {
            assertTrue(iter.hasNext());
            int[] edge = (int[]) iter.next();
            assertTrue(g.contains(edge[0], edge[1]));
        }
        assertFalse(iter.hasNext());
        iter = g.edges();
        for (int[] edge : iter) {
            assertTrue(g.contains(edge[0], edge[1]));
        }
    }

    @Test
    public void successorDirectiveTest() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);
        g.add(2, 1);
        Iteration<Integer> vIter = g.successors(1);
        for (int i = 0; i < 3; i++) {
            assertTrue(vIter.hasNext());
            vIter.next();
        }
        assertFalse(vIter.hasNext());
        vIter = g.predecessors(1);
        assertTrue(vIter.hasNext());
        assertEquals(Integer.valueOf(2), vIter.next());
        assertFalse(vIter.hasNext());
    }

    @Test
    public void successorUndirectiveTest() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        g.add(1, 3);
        g.add(1, 4);
        g.add(2, 1);
        Iteration<Integer> vIter = g.successors(1);
        for (int i = 0; i < 3; i++) {
            assertTrue(vIter.hasNext());
            vIter.next();
        }
        assertFalse(vIter.hasNext());
    }

}
