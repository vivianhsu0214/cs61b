import org.apache.commons.collections.BinaryHeap;

import java.util.*;

public class Graph {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    // Initialize a graph with the given number of vertices and no edges.
    @SuppressWarnings("unchecked")
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    // Add to the graph a directed edge from vertex v1 to vertex v2,
    // with the given edge information. If the edge already exists,
    // replaces the current edge with a new edge with edgeInfo.
    public void addEdge(int v1, int v2, int edgeWeight) {
        if (!isAdjacent(v1, v2)) {
            LinkedList<Edge> v1Neighbors = adjLists[v1];
            v1Neighbors.add(new Edge(v1, v2, edgeWeight));
        } else {
            LinkedList<Edge> v1Neighbors = adjLists[v1];
            for (Edge e : v1Neighbors) {
                if (e.to() == v2) {
                    e.edgeWeight = edgeWeight;
                }
            }
        }
    }

    // Add to the graph an undirected edge from vertex v1 to vertex v2,
    // with the given edge information. If the edge already exists,
    // replaces the current edge with a new edge with edgeInfo.
    public void addUndirectedEdge(int v1, int v2, int edgeWeight) {
        addEdge(v1, v2, edgeWeight);
        addEdge(v2, v1, edgeWeight);
    }

    // Return true if there is an edge from vertex "from" to vertex "to";
    // return false otherwise.
    public boolean isAdjacent(int from, int to) {
        for (Edge e : adjLists[from]) {
            if (e.to() == to) {
                return true;
            }
        }
        return false;
    }

    // Returns a list of all the neighboring  vertices 'u'
    // such that the edge (VERTEX, 'u') exists in this graph.
    public List<Integer> neighbors(int vertex) {
        ArrayList<Integer> neighbors = new ArrayList<>();
        for (Edge e : adjLists[vertex]) {
            neighbors.add(e.to());
        }
        return neighbors;
    }

    // Runs Dijkstra's algorithm starting from vertex 'startVertex' and returns
    // an integer array consisting of the shortest distances from 'startVertex'
    // to all other vertices.
    public int[] dijkstras(int startVertex) {
        boolean[] marked = new boolean[vertexCount];
        int[] rtn = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            rtn[i] = Integer.MAX_VALUE;
        }
        Heap hp = new Heap();
        hp.set(startVertex, 0);
        while (hp.size() != 0) {
            Heap.Node current = hp.pop();
            int curVal = current.value;
            int curPri = current.weight;

            boolean flag = false;
            for (int i = 0; i < marked.length; i++) {
                if (!marked[i]) {
                    break;
                } else if (i == marked.length - 1) {
                    flag = true;
                }
            }
            if (flag) {
                break;
            }

            if (!marked[curVal]) {
                marked[curVal] = true;
                rtn[curVal] = curPri;
                for (int successor : neighbors(curVal)) {
                    hp.set(successor, getEdge(curVal, successor).info() + curPri);
                }
            }
        }
        return rtn;
    }

    // Returns the Edge object corresponding to the listed vertices, v1 and v2.
    // You may find this helpful to implement!
    private Edge getEdge(int v1, int v2) {
        for (int i = 0; i < adjLists[v1].size(); i++) {
            if (adjLists[v1].get(i).to == v2) {
                return adjLists[v1].get(i);
            }
        }
        return null;
    }

    private class Edge {

        private int from;
        private int to;
        private int edgeWeight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.edgeWeight = weight;
        }

        public int to() {
            return to;
        }

        public int info() {
            return edgeWeight;
        }

        public String toString() {
            return "(" + from + "," + to + ",dist=" + edgeWeight + ")";
        }

    }

    private class Heap {
        private ArrayList<Node> list = new ArrayList<Node>();

        public Heap() {
            list.add(new Node(-1, -1));
        }

        private void bubbleup(int index) {
            assert(index > 0);
            if (index == 1) {
                return;
            }
            if (list.get(index).weight < list.get(index / 2).weight) {
                Node temp = list.get(index);
                list.set(index, list.get(index / 2));
                list.set(index / 2, temp);
                bubbleup(index / 2);
            }
        }

        private void bubbledown(int index) {
            assert(index > 0);
            int target = index * 2;
            if (target >= list.size() || list.get(target) == null) {
                return;
            } else if (target + 1 == list.size() || list.get(target + 1) == null) {
                if (list.get(index).weight > list.get(target).weight) {
                    Node temp = list.get(index);
                    list.set(index, list.get(index * 2));
                    list.set(index * 2, temp);
                }
            } else {
                if (list.get(target).weight < list.get(target + 1).weight) {
                    target += 1;
                    Node temp = list.get(index);
                    list.set(index, list.get(target));
                    list.set(target, temp);
                    bubbledown(target);
                }
            }
        }

        public void add(int v, int w) {
            list.add(new Node(v, w));
            bubbleup(list.size() - 1);
        }

        public boolean set(int v, int w) {
            for (Node n : list) {
                if (n.value == v && n.weight > w) {
                    n.weight = w;
                    return true;
                } else if (n.value == v) {
                    return false;
                }
            }
            add(v, w);
            return false;
        }

        public Node peek() {
            return list.get(1);
        }

        public Node pop() {
            Node rtn = peek();
            list.set(1, list.get(list.size() - 1));
            list.remove(list.size() - 1);
            bubbledown(1);
            return rtn;
        }

        public int size() {
            return list.size() - 1;
        }

        public class Node {
            int value;
            int weight;
            Node(int v, int w) {
                value = v;
                weight = w;
            }
        }
    }

    public static void main(String[] args) {
        // Put some tests here!

        Graph g1 = new Graph(5);
        g1.addEdge(0, 1, 1);
        g1.addEdge(0, 2, 1);
        g1.addEdge(0, 4, 1);
        g1.addEdge(1, 2, 1);
        g1.addEdge(2, 0, 1);
        g1.addEdge(2, 3, 1);
        g1.addEdge(4, 3, 1);
        int[] result = g1.dijkstras(2);


        Graph g2 = new Graph(5);
        g2.addEdge(0, 1, 1);
        g2.addEdge(0, 2, 1);
        g2.addEdge(0, 4, 1);
        g2.addEdge(1, 2, 1);
        g2.addEdge(2, 3, 1);
        g2.addEdge(4, 3, 1);
        result = g2.dijkstras(0);
        System.out.println("finished");
    }
}
