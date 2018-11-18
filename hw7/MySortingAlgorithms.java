import afu.org.checkerframework.checker.igj.qual.I;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class containing all the sorting algorithms from 61B to date.
 *
 * You may add any number instance variables and instance methods
 * to your Sorting Algorithm classes.
 *
 * You may also override the empty no-argument constructor, but please
 * only use the no-argument constructor for each of the Sorting
 * Algorithms, as that is what will be used for testing.
 *
 * Feel free to use any resources out there to write each sort,
 * including existing implementations on the web or from DSIJ.
 *
 * All implementations except Distribution Sort adopted from Algorithms,
 * a textbook by Kevin Wayne and Bob Sedgewick. Their code does not
 * obey our style conventions.
 */
public class MySortingAlgorithms {

    /**
     * Java's Sorting Algorithm. Java uses Quicksort for ints.
     */
    public static class JavaSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            Arrays.sort(array, 0, k);
        }

        @Override
        public String toString() {
            return "Built-In Sort (uses quicksort for ints)";
        }
    }

    /** Insertion sorts the provided data. */
    public static class InsertionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            k = Integer.min(k, array.length);
            for (int i = 0; i < k; i++) {
                for (int j = i; j > 0 && array[j] < array[j - 1]; j--) {
                    swap(array, j, j - 1);
                }
            }
        }

        @Override
        public String toString() {
            return "Insertion Sort";
        }
    }

    /**
     * Selection Sort for small K should be more efficient
     * than for larger K. You do not need to use a heap,
     * though if you want an extra challenge, feel free to
     * implement a heap based selection sort (i.e. heapsort).
     */
    public static class SelectionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            k = Integer.min(k, array.length);
            for (int i = 0; i < k; i++) {
                int minIdx = i;
                for (int j = i + 1; j < k; j++) {
                    if (array[j] < array[minIdx]) {
                        minIdx = j;
                    }
                }
                swap(array, i, minIdx);
            }
        }

        @Override
        public String toString() {
            return "Selection Sort";
        }
    }

    /** Your mergesort implementation. An iterative merge
      * method is easier to write than a recursive merge method.
      * Note: I'm only talking about the merge operation here,
      * not the entire algorithm, which is easier to do recursively.
      */
    public static class MergeSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int[] aux = new int[array.length];
            int hi = Integer.min(k - 1, array.length - 1);
            sort(array, aux, 0, hi);
        }

        private void sort(int[] array, int[] aux, int lo, int hi) {
            if (hi <= lo) {
                return;
            }
            int mid = lo + (hi - lo) / 2;
            sort(array, aux, lo, mid);
            sort(array, aux, mid + 1, hi);
            merge(array, aux, lo, mid, hi);
        }

        private void merge(int[] array, int[] aux, int lo, int mid, int hi) {
            for (int i = lo; i <= hi; i++) {
                aux[i] = array[i];
            }
            int i = lo;
            int j = mid + 1;
            for (int k = lo; k <= hi ; k++) {
                if (i > mid) {
                    array[k] = aux[j++];
                } else if (j > hi) {
                    array[k] = aux[i++];
                } else if (aux[j] < aux[i]) {
                    array[k] = aux[j++];
                } else {
                    array[k] = aux[i++];
                }
            }
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Merge Sort";
        }
    }

    /**
     * Your Distribution Sort implementation.
     * You should create a count array that is the
     * same size as the value of the max digit in the array.
     */
    public static class DistributionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int N = Integer.min(k, array.length);
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < N; i++) {
                max = Integer.max(max, array[i]);
            }
            int numBuckets = max + 1;
            int[] buckets = new int[numBuckets];
            for (int i = 0; i < N; i++) {
                buckets[array[i]] += 1;
            }
            int[] accumalator = new int[numBuckets];
            for (int i = 1; i < numBuckets; i++) {
                accumalator[i] = accumalator[i - 1] + buckets[i - 1];
            }
            int[] sorted = new int[N];
            for (int i = 0; i < N; i++) {
                int posn = accumalator[array[i]];
                sorted[posn] = array[i];
                accumalator[array[i]] += 1;
            }
            System.arraycopy(sorted, 0, array, 0, N);
        }
        // may want to add additional methods

        @Override
        public String toString() {
            return "Distribution Sort";
        }
    }

    /** Your Heapsort implementation.
     */
    public static class HeapSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int N = Integer.min(k, array.length);
            ArrayList<Integer> heap = new ArrayList<>();
            heap.add(Integer.MIN_VALUE);
            for (int i = 0; i < N; i++) {
                heap.add(array[i]);
                bubbleUp(heap, i + 1);
            }
            for (int i = 0; i < N; i++) {
                array[i] = pop(heap);
            }
        }

        private void bubbleUp(ArrayList<Integer> heap, int posn) {
            if (posn == 1 || heap.get(posn) > heap.get(posn / 2)) {
                return;
            } else {
                int temp = heap.get(posn);
                heap.set(posn, heap.get(posn / 2));
                heap.set(posn / 2, temp);
                bubbleUp(heap, posn / 2);
            }
        }

        private void bubbleDown(ArrayList<Integer> heap, int posn) {
            if (posn * 2 >= heap.size()) {
                return;
            } else if (posn * 2 + 1 == heap.size()) {
                if (heap.get(posn) > heap.get(posn * 2)) {
                    int temp = heap.get(posn);
                    heap.set(posn, heap.get(posn * 2));
                    heap.set(posn * 2, temp);
                }
            } else if (heap.get(posn) > heap.get(posn * 2)
                    || heap.get(posn) > heap.get(posn * 2 + 1)) {
                if (heap.get(posn * 2) < heap.get(posn * 2 + 1)) {
                    int temp = heap.get(posn);
                    heap.set(posn, heap.get(posn * 2));
                    heap.set(posn * 2, temp);
                    bubbleDown(heap, posn * 2);
                } else {
                    int temp = heap.get(posn);
                    heap.set(posn, heap.get(posn * 2 + 1));
                    heap.set(posn * 2 + 1, temp);
                    bubbleDown(heap, posn * 2 + 1);
                }
            }
        }

        private int pop(ArrayList<Integer> heap) {
            if (heap.size() < 2) {
                return Integer.MIN_VALUE;
            } else if (heap.size() == 2) {
                return heap.remove(1);
            }
            int rtn = heap.get(1);
            heap.set(1, heap.remove(heap.size() - 1));
            bubbleDown(heap, 1);
            return rtn;
        }

        @Override
        public String toString() {
            return "Heap Sort";
        }
    }

    /** Your Quicksort implementation.
     */
    public static class QuickSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            ArrayList<Integer> origin = new ArrayList<>();
            for (int i = 0; i < array.length; i++) {
                origin.add(array[i]);
            }
            quickSortHelper(origin);
            new InsertionSort().sort(array, k);
        }

        private void quickSortHelper(List<Integer> origin) {
            int end = origin.size();
            if (end <= 1) {
                return;
            }
            int pivot = origin.get(0);
            ArrayList<Integer> front = new ArrayList<>();
            ArrayList<Integer> back = new ArrayList<>();
            for (int i = 1; i < end; i++) {
                if (origin.get(i) > pivot) {
                    back.add(origin.get(i));
                } else {
                    front.add(origin.get(i));
                }
            }
            int mid = front.size();
            for (int i = 0; i < end; i++) {
                if (i < mid) {
                    origin.set(i, front.get(i));
                } else if (i == mid) {
                    origin.set(mid, pivot);
                } else {
                    origin.set(i, back.get(i - mid - 1));
                }
            }
            quickSortHelper(origin.subList(0, mid));
            quickSortHelper(origin.subList(mid + 1, end));
        }

        @Override
        public String toString() {
            return "Quicksort";
        }
    }

    /* For radix sorts, treat the integers as strings of x-bit numbers.  For
     * example, if you take x to be 2, then the least significant digit of
     * 25 (= 11001 in binary) would be 1 (01), the next least would be 2 (10)
     * and the third least would be 1.  The rest would be 0.  You can even take
     * x to be 1 and sort one bit at a time.  It might be interesting to see
     * how the times compare for various values of x. */

    /**
     * LSD Sort implementation.
     */
    public static class LSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            int N = Integer.min(k, a.length);
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < N; i++) {
                max = Integer.max(max, a[i]);
            }

            int maxDigit = 0;
            for(; Math.pow(10, maxDigit) <= max; maxDigit += 1);

            ArrayList<Integer> origin = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                origin.add(a[i]);
            }
            for (int i = 0; i < maxDigit; i++) {
                origin = digitSort(origin, i);
            }
            for (int i = 0; i < N; i++) {
                a[i] = origin.get(i);
            }
        }

        private ArrayList<Integer> digitSort(List<Integer> origin, int digit) {
            ArrayList[] buckets = new ArrayList[10];
            for (int i = 0; i < 10; i++) {
                buckets[i] = new ArrayList<Integer>();
            }
            for (int i = 0; i < origin.size(); i++) {
                int elem = origin.get(i);
                buckets[subtractDigit(elem, digit)].add(elem);
            }
            ArrayList<Integer> rtn = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                rtn.addAll(buckets[i]);
            }
            return rtn;
        }

        private int subtractDigit(int num, int n) {
            String temp = String.valueOf(num);
            if (n + 1 > temp.length()) {
                return 0;
            } else {
                int posn = temp.length() - 1 - n;
                return Integer.parseInt(temp.substring(posn, posn + 1));
            }
        }

        @Override
        public String toString() {
            return "LSD Sort";
        }
    }

    /**
     * MSD Sort implementation.
     */
    public static class MSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            int N = Integer.min(k, a.length);
            ArrayList<Integer> origin = new ArrayList<>();
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < N; i++) {
                max = Integer.max(max, a[i]);
                origin.add(a[i]);
            }
            int maxDigit = 0;
            for(; Math.pow(10, maxDigit) <= max; maxDigit += 1);
            origin = digitSort(origin, maxDigit);
            for (int i = 0; i < N; i++) {
                a[i] = origin.get(i);
            }
        }

        private ArrayList<Integer> digitSort(List<Integer> origin, int n) {
            ArrayList[] buckets = new ArrayList[10];
            for (int i = 0; i < 10; i++) {
                buckets[i] = new ArrayList<Integer>();
            }
            for (int i = 0; i < origin.size(); i++) {
                int elem = origin.get(i);
                buckets[subtractDigit(elem, n)].add(elem);
            }
            if (n > 1) {
                for (int i = 0; i < 10; i++) {
                    buckets[i] = digitSort(buckets[i], n - 1);
                }
            }
            ArrayList<Integer> rtn = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                rtn.addAll(buckets[i]);
            }
            return rtn;
        }

        private int subtractDigit(int num, int n) {
            String temp = String.valueOf(num);
            if (n > temp.length()) {
                return 0;
            }
            int posn = temp.length() - n;
            return Integer.parseInt(temp.substring(posn, posn + 1));
        }

        @Override
        public String toString() {
            return "MSD Sort";
        }
    }

    /** Exchange A[I] and A[J]. */
    private static void swap(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
