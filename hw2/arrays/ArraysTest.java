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
        int[] remover = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] expected = {0, 1, 2, 3, 9, 10};
        assert(expected.equals(Arrays.remove(remover, 5, 5)));
        int[] expected2 = {0, 1, 2, 3};
        assert(expected2.equals(Arrays.remove(remover, 5, 7)));
        assert(expected2.equals(Arrays.remove(remover, 5, 100)));
    }

    @Test
    public void naturalRunTest() {
        int[] test = {1, 3, 7, 5, 4, 6, 9, 10};
        int[][] expected = {{1, 3, 7}, {5}, {4, 6, 9, 10}};
        int[] test2 = {1,3};
        int[][] expected2 = {{1,3}};
        assert(expected.equals(Arrays.naturalRuns(test)));
        assert(expected2.equals(Arrays.naturalRuns(test2)));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
