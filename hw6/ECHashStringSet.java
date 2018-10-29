// REPLACE THIS STUB WITH THE CORRECT SOLUTION.
// The current contents of this file are merely to allow things to compile
// out of the box.

import java.util.List;
import java.util.LinkedList;

/** A set of String values.
 *  @author Zhibo Fan
 */
class ECHashStringSet implements StringSet {
    private static double MIN_LOAD = 0.2;
    private static double MAX_LOAD = 5;

    @SuppressWarnings("unchecked")
    public ECHashStringSet() {
        _storage = new LinkedList[5];
        for (int i = 0; i < 5; i++) {
            _storage[i] = new LinkedList<String>();
        }
        _size = 0;
    }

    @Override
    public boolean contains(String s) {
        return _storage[storeIndex(s)].contains(s);
    }

    @Override
    public void put(String s) {
        if (loadFactor() >= MAX_LOAD) {
            resize();
        }
        int idx = storeIndex(s);
        _storage[idx].add(s);
        _size += 1;
    }

    @Override
    public List<String> asList() {
        LinkedList<String> all = new LinkedList<String>();
        for(LinkedList<String> store : _storage) {
            all.addAll(store);
        }
        return all;
    }

    private double loadFactor() {
        return (double)_size / (double)_storage.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        List store = asList();
        _storage = new LinkedList[_storage.length * 2];

        for (int i = 0; i < _storage.length; i++) {
            _storage[i] = new LinkedList<String>();
        }

        for (Object obj : store) {
            String elem = (String) obj;
            int idx = storeIndex(elem);
            _storage[idx].add(elem);
        }
    }

    private int storeIndex(String s) {
        return (s.hashCode() & 0x7fffffff) % _storage.length;
    }

    public int size() {
        return _size;
    }

    private LinkedList<String>[] _storage;

    private int _size;
}
