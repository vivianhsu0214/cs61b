package image;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test on MatrixUtils.java.
 *  @author Zhibo Fan
 */

public class MatrixUtilsTest {

    @Test
    public void integrityTest() {
        double[][] image = {{1000000, 1000000, 1000000, 1000000},
                            {1000000, 75990, 30003, 1000000},
                            {1000000, 30002, 103046, 1000000},
                            {1000000, 29515, 38273, 1000000},
                            {1000000, 73403, 35399, 1000000},
                            {1000000, 100000, 100000, 1000000}};
        double[][] outputImage =  {{1000000, 1000000, 1000000, 1000000},
                {2000000, 1075990, 1030003, 2000000},
                {2075990, 1060005, 1133049, 2030003},
                {2060005, 1089520, 1098278, 2133049},
                {2089520, 1162923, 1124919, 2098278},
                {2162923, 2124919, 2124919, 2124919}};
        double[][] accu = MatrixUtils.accumulateVertical(image);
        assert(outputImage.equals(accu));
        int[] expected = {1, 2, 1, 1, 2, 1};
        assert(expected.equals(MatrixUtils.findSeam(outputImage, MatrixUtils.Orientation.VERTICAL)));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(MatrixUtilsTest.class));
    }
}