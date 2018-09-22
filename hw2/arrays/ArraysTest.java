package arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for Array.java.
 *  @author Zhibo Fan
 */

public class ArraysTest {
    @Test
    public void catenateTest() {
        int[] A0 = new int[0];
        int[] B0 = {1, 2, 3};
        int[] expected = {1, 2, 3};
        assert(expected.equals(Arrays.catenate(A0, B0)));
        int[] A1 = {1, 2, 3, 4, 5, 6};
        int[] B1 = {7, 8, 9, 10};
        int[] expected2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assert(expected2.equals(Arrays.catenate(A1, B1)));
    }

    @Test
    public void removeTest() {
        int[] remover = {6, 7};
        int[] expected = {};
        int[] result = Arrays.remove(remover, 0, 2);
        assert(expected.equals(result));
    }

    @Test
    public void naturalRunTest() {
        int[] test = {21, 16, 14, 13, 12, 10, 9, 8, 5, 4};
        int[][] expected = {{21}, {16}, {14}, {13}, {12}, {10}, {9}, {8}, {5}, {4}};
        int[] test2 = {0, 1, 2, 3, 4};
        int[][] expected2 = {{0, 1, 2, 3, 4}};
        int[][] result = Arrays.naturalRuns(test);
        int[][] result2 = Arrays.naturalRuns(test2);

        assert(expected.equals(result));
        assert(expected2.equals(result2));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
