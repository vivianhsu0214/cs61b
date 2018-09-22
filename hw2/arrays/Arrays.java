package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author
 */
class Arrays {
    /* C. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        /* *Replace this body with the solution. */
        if(A == null || B == null) {
            return null;
        }
        int[] result = new int[A.length+B.length];
        for (int i = 0; i < A.length; i++) {
            result[i] = A[i];
        }
        for (int i = 0; i < B.length; i++) {
            result[i + A.length] = B[i];
        }
        return result;
    }

    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        /* *Replace this body with the solution. */
        if(A == null) {
            return null;
        }
        if(start >= A.length) {
            return A;
        }
        if(start + len >= A.length) {
            len = A.length - start;
        }
        int[] result = new int[A.length - len];
        for(int i = 0; i < start; i++) {
            result[i] = A[i];
        }
        if(start + len < A.length) {
            for(int i = start + len; i < A.length; i++) {
                result[i - len] = A[i];
            }
        }
        return result;
    }

    /* E. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        /* *Replace this body with the solution. */
        if (A == null) {
            return null;
        }

        if(A.length == 0) {
            int[][] result = {{}};
            return result;
        }

        int height = 1;
        for (int i = 0; i < A.length - 1; i++) {
            if (A[i] >= A[i + 1]) {
                height += 1;
            }
        }

        int[][] result = new int[height][];
        int width = 1;
        int index = 0;

        for (int i = 0; i < height; i++) {
            for (int j = index; j < A.length - 1; j++) {
                if (A[j] >= A[j + 1]) {
                    result[i] = new int[width];
                    for(int k = 0; k < width; k++) {
                        result[i][k] = A[index + k];
                    }
                    index = j + 1;
                    width = 1;
                    break;
                }
                width += 1;
            }
        }

        result[height - 1] = new int[width];
        for(int k = 0; k < width; k++) {
            result[height - 1][k] = A[index + k];
        }

        return result;
    }

}
