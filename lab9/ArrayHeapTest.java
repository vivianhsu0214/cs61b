import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayHeapTest {

    /** Basic test of adding, checking, and removing two elements from a heap */
    @Test
    public void simpleTest() {
        ArrayHeap<String> pq = new ArrayHeap<>();
        pq.insert("Qir", 2);
        pq.insert("Kat", 1);
        assertEquals(2, pq.size());

        String first = pq.removeMin();
        assertEquals("Kat", first);
        assertEquals(1, pq.size());

        String second = pq.removeMin();
        assertEquals("Qir", second);
        assertEquals(0, pq.size());
    }

    static ArrayHeap<String> getTest() {
        ArrayHeap<String> pq = new ArrayHeap<>();
        pq.insert("A", 1);
        pq.insert("B", 2);
        pq.insert("C", 3);
        pq.insert("D", 4);
        pq.insert("E", 5);
        pq.insert("F", 6);
        pq.insert("G", 7);
        pq.insert("H", 8);
        pq.insert("I", 9);
        pq.insert("J", 10);
        pq.insert("K", 11);
        pq.insert("L", 12);
        pq.insert("M", 13);
        pq.insert("N", 14);
        pq.insert("O", 15);
        return pq;
    }

    @Test
    public void testRemoveAndInsert() {
        ArrayHeap<String> pq = getTest();
        pq.insert("A-B", 1.5);
        pq.removeMin();
        assertEquals("A-B", pq.removeMin());
    }

    @Test
    public void testChangePriority() {
        ArrayHeap<String> pq = getTest();
        pq.changePriority("I", 0);
        assertEquals( "I", pq.peek().item());
        pq.changePriority("H", 2.5);
        for (int i = 0; i < 3; i++) {
            pq.removeMin();
        }
        assertEquals("H", pq.removeMin());
        assertEquals("C", pq.removeMin());
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArrayHeapTest.class));
    }
}
