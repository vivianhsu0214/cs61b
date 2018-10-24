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

    public ECHashStringSet() {
        _storage = new LinkedList[(int)(1 / MIN_LOAD)];
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
        if (_storage[idx] == null) {
            _storage[idx] = new LinkedList<String>();
        }
        _storage[idx].add(s);
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
        double items = 0.0;
        for (LinkedList store : _storage) {
            items = items + store.size();
        }
        return items / _size;
    }

    private void resize() {
        LinkedList[] old = _storage;
        _storage = new LinkedList[old.length * 2];
        for (LinkedList<String> store : old) {
            if (store != null) {
                for (String s : store) {
                    put(s);
                }
            }
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
