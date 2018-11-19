/** HW #7, Two-sum problem.
 * @author Zhibo Fan
 */
public class Sum {

    /** Returns true iff A[i]+B[j] = M for some i and j. */
    public static boolean sumsTo(int[] A, int[] B, int m) {
        MySortingAlgorithms.MergeSort sa = new MySortingAlgorithms.MergeSort();
        sa.sort(A, A.length);
        sa.sort(B, B.length);
        for(int i = 0, j = B.length; i < A.length && j >= 0;) {
            if (A[i] + B[j] == m) {
                return true;
            } else if (A[i] + B[j] < m) {
                i++;
            } else {
                j++;
            }
        }
        return false;
    }

}
