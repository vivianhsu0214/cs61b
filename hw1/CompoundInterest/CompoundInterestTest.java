import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers.

        assertEquals(0, 0); */
        assertEquals(0,CompoundInterest.numYears(1998));
        assertEquals(0,CompoundInterest.numYears(2018));
        assertEquals(2,CompoundInterest.numYears(2020));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(12.544,CompoundInterest.futureValue(10,12,2020),tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(11.8026496,CompoundInterest.futureValueReal(10,12,2020,3),tolerance);
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(16550,CompoundInterest.totalSavings(5000,2020,10),tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(15571.895,CompoundInterest.totalSavingsReal(5000,2020,10 ,3),tolerance);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
