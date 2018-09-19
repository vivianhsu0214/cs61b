package lists;

/* NOTE: The file Utils.java contains some functions that may be useful
 * in testing your answers. */

/**
 * HW #2, Problem #1.
 */

/** List problem.
 *  @author Zhibo Fan
 */
class Lists {
    /** Return the list of lists formed by breaking up L into "natural runs":
     *  that is, maximal strictly ascending sublists, in the same order as
     *  the original.  For example, if L is (1, 3, 7, 5, 4, 6, 9, 10, 10, 11),
     *  then result is the four-item list
     *            ((1, 3, 7), (5), (4, 6, 9, 10), (10, 11)).
     *  Destructive: creates no new IntList items, and may modify the
     *  original list pointed to by L. */
    static IntListList naturalRuns(IntList L) {
        /* *Replace this body with the solution. */
        if (L == null) {
            return null;
        }
        IntListList result = new IntListList();
        IntListList re_iterator = result;
        IntList iterator = L;
        while (iterator.tail != null) {
            if (iterator.head >= iterator.tail.head) {
                IntList temp = iterator.tail;
                iterator.tail = null;
                if (result.head == null) {
                    result.head = L;
                } else {
                    re_iterator.tail = new IntListList(L, null);
                    re_iterator = re_iterator.tail;
                }
                L = temp;
                iterator = L;
            } else {
                iterator = iterator.tail;
            }
        }
        if (result.head == null) {
            result.head = L;
        } else {
            re_iterator.tail = new IntListList(L, null);
        }
        return result;
    }
}
