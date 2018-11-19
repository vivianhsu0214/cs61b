import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** HW #7, Count inversions.
 *  @author Zhibo Fan
 */
public class Inversions {

    /** A main program for testing purposes.  Prints the number of inversions
     *  in the sequence ARGS. */
    public static void main(String[] args) {
        System.out.println(inversions(Arrays.asList(args)));
    }

    /** Return the number of inversions of T objects in ARGS. */
    public static <T extends Comparable<? super T>>
        int inversions(List<T> args) {
        return sort(args);
    }

    private static <T extends Comparable<? super T>>
        int sort(List<T> array) {
            ArrayList<T> aux = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                aux.add(null);
            }
            return sort(array, aux, 0, array.size()-1);
        }

    private static <T extends Comparable<? super T>>
        int sort(List<T> array, List<T> aux, int lo, int hi) {
            if (hi <= lo) {
                return 0;
            }
            int mid = lo + (hi - lo) / 2;
            int inversions = sort(array, aux, lo, mid)
                             + sort(array, aux, mid + 1, hi);
            return inversions + merge(array, aux, lo, mid, hi);
        }

    private static <T extends Comparable<? super T>>
        int merge(List<T> array, List<T> aux, int lo, int mid, int hi) {
            int inversions = 0;
            for (int i = lo; i <= hi; i++) {
                aux.set(i, array.get(i));
            }
            int i = lo;
            int j = mid + 1;
            for (int k = lo; k <= hi ; k++) {
                if (i > mid) {
                    array.set(k, aux.get(j++));
                } else if (j > hi) {
                    array.set(k, aux.get(i++));
                } else if (aux.get(j).compareTo(aux.get(i)) < 0) {
                    array.set(k, aux.get(j++));
                    inversions += mid - lo + 1;
                } else {
                    array.set(k, aux.get(i++));
                }
            }
            return inversions;
        }

}
