import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /** Sample test that verifies correctness of the IntList.list static
     *  method. The main point of this is to convince you that
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.
     *
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with
     *  IntList empty = IntList.list().
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A.
     */

    @Test
    public void testDcatenate() {
        IntList A = IntList.list(1,2,3);
        IntList B = IntList.list(4,5);
        IntList C = IntList.list(1,2,3,4,5);
        assertEquals(C,IntList.dcatenate(A,B));
        assertEquals(A,C);
    }

    /** Tests that subtail works properly. Again, don't use new.
     *
     *  Make sure to test that subtail does not modify the list.
     */

    @Test
    public void testSubtail() {
        IntList L = IntList.list(1,2,3,4,5);
        IntList replica = IntList.list(1,2,3,4,5);
        IntList L0 = IntList.subTail(L,0);

        IntList L2 = IntList.list(3,4,5);
        IntList empty = IntList.subTail(L,6);
        IntList empty1 = IntList.subTail(L,-1);
        assertEquals(L,L0);
        assertEquals(L,replica);
        assertEquals(L2,IntList.subTail(L,2));
        assertEquals(empty,null);
        assertEquals(empty1,null);
    }

    /** Tests that sublist works properly. Again, don't use new.
     *
     *  Make sure to test that sublist does not modify the list.
     */

    @Test
    public void testsublist() {
        IntList L = IntList.list(1,2,3,4,5);
        IntList replica = IntList.list(1,2,3,4,5);
        IntList L0 = IntList.sublist(L,0,5);
        IntList L2 = IntList.list(3,4);
        IntList empty = IntList.sublist(L,6,1);
        IntList empty1 = IntList.sublist(L,6,-1);
        IntList empty2 = IntList.sublist(L,4,3);
        assertEquals(replica,L);
        assertEquals(L0,L);
        assertEquals(L2,IntList.sublist(L,2,2));
        assertEquals(empty,null);
        assertEquals(empty1,null);
        assertEquals(empty2,null);
    }

    /** Tests that dsublist works properly. Again, don't use new.
     *
     *  As with testDcatenate, it is not safe to assume that list passed
     *  to dsublist is the same after any call to dsublist
     */

    @Test
    public void testDsublist() {
        IntList L = IntList.list(1,2,3,4,5);
        IntList A = IntList.list(3,4);
        IntList empty = IntList.dsublist(L,0,100);
        IntList empty1 = IntList.dsublist(L,0,-1);
        IntList empty2 = IntList.dsublist(L,6,1);
        assertEquals(A,IntList.dsublist(L,2,2));
        assertEquals(IntList.list(1,2,3,4),L);
        assertEquals(empty,null);
        assertEquals(empty1,null);
        assertEquals(empty2,null);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(IntListTest.class));
    }
}
