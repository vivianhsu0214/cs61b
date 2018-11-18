import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/** HW #7, Sorting ranges.
 *  @author Zhibo Fan
  */
public class Intervals {
    /** Assuming that INTERVALS contains two-element arrays of integers,
     *  <x,y> with x <= y, representing intervals of ints, this returns the
     *  total length covered by the union of the intervals. */
    public static int coveredLength(List<int[]> intervals) {
        int[] starts = new int[intervals.size()];
        int[] ends = new int[intervals.size()];
        int[] all = new int[intervals.size() * 2];
        for (int i = 0; i < intervals.size(); i++) {
            starts[i] = intervals.get(i)[0];
            ends[i] = intervals.get(i)[1];
            all[2 * i] = intervals.get(i)[0];
            all[2 * i + 1] = intervals.get(i)[1];
        }
        MySortingAlgorithms.QuickSort sa = new MySortingAlgorithms.QuickSort();
        sa.sort(starts, intervals.size());
        sa.sort(ends, intervals.size());
        sa.sort(all, intervals.size() * 2);
        int i, j;
        i = j = 0;
        int startIdx = 0;
        int length = 0;
        int counter = 0;
        for (int k = 0; k < all.length; k++) {
            if (i >= intervals.size()) {
                k = all.length;
                length += ends[intervals.size() - 1] - starts[startIdx];
                break;
            } else if (all[k] == starts[i]) {
                i += 1;
                counter += 1;
            } else if (all[k] == ends[j]) {
                j += 1;
                counter -= 1;
            }
            if (counter == 0) {
                length += all[k] - starts[startIdx];
                startIdx = i;
            }
        }
        return length;
    }

    /** Test intervals. */
    static final int[][] INTERVALS = {
        {19, 30},  {8, 15}, {3, 10}, {6, 12}, {4, 5},
    };
    /** Covered length of INTERVALS. */
    static final int CORRECT = 23;

    /** Performs a basic functionality test on the coveredLength method. */
    @Test
    public void basicTest() {
        assertEquals(CORRECT, coveredLength(Arrays.asList(INTERVALS)));
    }

    /** Runs provided JUnit test. ARGS is ignored. */
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(Intervals.class));
    }

}
