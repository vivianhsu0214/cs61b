
public class IntDList {

    protected DNode _front, _back;

    public IntDList() {
        _front = _back = null;
    }

    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /**
     *
     * @return The first value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getFront() {
        return _front._val;
    }

    /**
     *
     * @return The last value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getBack() {
        return _back._val;
    }

    /**
     *
     * @return The number of elements in this list.
     */
    public int size() {
        // Your code here
        if(_back==null || _front==null) return 0;
        DNode iterator = _front;
        int size = 1;
        for(;iterator._next!=null;iterator = iterator._next){
            size++;
        }
        return size;
    }

    /**
     *
     * @param i index of element to return, where i = 0 returns the first element,
     *          i = 1 returns the second element, i = -1 returns the last element,
     *          i = -2 returns the second to last element, and so on.
     *          You can assume i will always be a valid index, i.e 0 <= i < size
     *          for positive indices and -size <= i < 0 for negative indices.
     * @return The integer value at index i
     */
    public int get(int i) {
        // Your code here
        if(_front==null || _back==null) return 0;
        if(i>=0){
            DNode target = _front;
            for(int j=0;j<i;j++) {
                target = target._next;
            }
            return target._val;
        }
        else{
            i = -i-1;
            DNode target = _back;
            for(int j=0;j<i;j++) {
                target = target._prev;
            }
            return target._val;
        }
    }

    /**
     *
     * @param d value to be inserted in the front
     */
    public void insertFront(int d) {
        // Your code here
        if(_front==null){
            _front = new DNode(d);
            _back = _front;
        }
        else{
            _front._prev = new DNode(null,d,_front);
            _front = _front._prev;
        }
    }

    /**
     *
      * @param d value to be inserted in the back
     */
    public void insertBack(int d) {
        // Your code here
        if(_back==null){
            _back = new DNode(d);
            _front = _back;
        }
        else {
            _back._next = new DNode(_back, d, null);
            _back = _back._next;
        }
    }

    /**
     * Removes the last item in the IntDList and returns it
     * @return the item that was deleted
     */
    public int deleteBack() {
           // Your code here
        if(_back==_front){
            int result = _back._val;
            _back = _front =null;
            return result;
        }
        DNode target = _back;
        _back = _back._prev;
        _back._next = null;
        return target._val;
    }

    /**
     *
     * @return a string representation of the IntDList in the form
     *  [] (empty list) or [1, 2], etc.
     *  Hint:
     *  String a = "a";
     *  a += "b";
     *  System.out.println(a); //prints ab
     */
    public String toString() {
        // Your code here
        if(_back==null) return "[]";
        String result = "[";
        DNode iterator = _front;
        for(;iterator._next!=null;iterator = iterator._next) {
            result += String.valueOf(iterator._val);
            result += ", ";
        }
        result += String.valueOf(iterator._val);
        
        result += "]";
        return result;
    }

    /* DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information! */
    protected static class DNode {
        protected DNode _prev;
        protected DNode _next;
        protected int _val;

        protected DNode(int val) {
            this(null, val, null);
        }

        protected DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}
