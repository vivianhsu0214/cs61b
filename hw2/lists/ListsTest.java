package lists;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test cases about Lists.java.
 *
 * @author Zhibo Fan
 */

public class ListsTest {


    // It might initially seem daunting to try to set up
    // IntListList expected.
    //
    // There is an easy way to get the IntListList that you want in just
    // few lines of code! Make note of the IntListList.list method that
    // takes as input a 2D array.
    @Test
    public void oneSubListTest() {
        int[][] temp = {{1, 2, 3}};
        IntListList expected = IntListList.list(temp);
        IntList test = IntList.list(1, 2, 3);
        assertEquals(expected, Lists.naturalRuns(test));
    }

    @Test
    public void destructiveTest() {
        IntList expected = IntList.list(1);
        IntList test = IntList.list(1, 0);
        Lists.naturalRuns(test);
        assertEquals(expected, test);
    }

    @Test
    public void IntegrityTest() {
        int[][] temp = {{1, 3, 7}, {5}, {4, 6, 9, 10}, {10, 11}};
        IntListList expected = IntListList.list(temp);
        int[] test_temp = {1, 3, 7, 5, 4, 6, 9, 10, 10, 11};
        IntList test = IntList.list(test_temp);
        assertEquals(expected, Lists.naturalRuns(test));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
