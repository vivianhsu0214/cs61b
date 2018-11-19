import java.util.ArrayList;

/** HW #7, Distribution counting for large numbers.
 *  @author Zhibo Fan
 */
public class SortInts {

    /** Sort A into ascending order.  Assumes that 0 <= A[i] < n*n for all
     *  i, and that the A[i] are distinct. */
    static void sort(long[] A) {
        long max = Long.MIN_VALUE;
        for (int i = 0; i < A.length; i++) {
            max = Long.max(max, A[i]);
        }
        int n = (int) (Math.sqrt(max + 1) + 1);
        ArrayList[] buckets = new ArrayList[n];
        distribute(A, buckets, n);
        gather(A, buckets);
        insertionSort(A);
    }

    private static void distribute(long[] A, ArrayList[] buckets, int n) {
        for (int i = 0; i < A.length; i++) {
            long elem = A[i];
            int posn = (int) elem / n;
            if (buckets[posn] == null) {
                buckets[posn] = new ArrayList<Long>();
            }
            buckets[posn].add(elem);
        }
    }

    private static void gather(long[] A, ArrayList[] buckets) {
        int pointer = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] == null) {
                continue;
            }
            for (int j = 0; j < buckets[i].size(); j++) {
                A[pointer++] = (long) buckets[i].get(j);
            }
        }
    }

    private static void insertionSort(long[] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = i; j > 0 && A[j] < A[j - 1]; j--) {
                long tmp = A[j];
                A[j] = A[j-1];
                A[j-1] = tmp;
            }
        }
    }

}

