import java.util.Iterator;
import utils.Filter;

/** A kind of Filter that lets through every other VALUE element of
 *  its input sequence, starting with the first.
 *  @author You
 */
class AlternatingFilter<Value> extends Filter<Value> {

    /** A filter of values from INPUT that lets through every other
     *  value. */
    AlternatingFilter(Iterator<Value> input) {
        super(input); //FIXME?
        _even = false;
    }

    @Override
    protected boolean keep() {
        _even = !_even;
        return _even;
    }

    /**
     * If the index of the object is even or not
     */
    private boolean _even;
}
