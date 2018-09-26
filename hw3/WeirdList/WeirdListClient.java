/** Functions to increment and sum the elements of a WeirdList. */
class WeirdListClient {

    private static class AddFunction implements IntUnaryFunction {
        private int adder;
        AddFunction(int adder) {
            this.adder = adder;
        }
        @Override
        public int apply(int x) {
            return adder + x;
        }
    }

    private static class SumFunction implements IntUnaryFunction {
        private int sum;
        SumFunction(int sum) {
            this.sum = sum;
        }
        @Override
        public int apply(int x) {
            sum += x;
            return x;
        }
        public int getSum() {
            return sum;
        }
    }

    /** Return the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
        return L.map(new AddFunction(n)); // REPLACE THIS LINE WITH THE RIGHT ANSWER.
    }

    /** Return the sum of the elements in L. */
    static int sum(WeirdList L) {
        SumFunction sf = new SumFunction(0);
        L.map(sf);
        return sf.getSum();
    }

    /* As with WeirdList, you'll need to add an additional class or
     * perhaps more for WeirdListClient to work. Again, you may put
     * those classes either inside WeirdListClient as private static
     * classes, or in their own separate files.

     * You are still forbidden to use any of the following:
     *       if, switch, while, for, do, try, or the ?: operator.
     */
}
