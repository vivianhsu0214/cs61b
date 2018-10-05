package enigma;

import static enigma.EnigmaException.*;

/**
 * Represents a permutation of a range of integers starting at 0 corresponding
 * to the characters of an alphabet.
 *
 * @author Zhibo Fan
 */
class Permutation {

    /**
     * Set this Permutation to that specified by CYCLES, a string in the
     * form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     * is interpreted as a permutation in cycle notation.  Characters in the
     * alphabet that are not included in any cycle map to themselves.
     * Whitespace is ignored.
     */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycle = cycles.replaceAll("(\\w)+", "");
    }

    /**
     * Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     * c0c1...cm.
     */
    private void addCycle(String cycle) {
        _cycle += cycle;
    }

    /**
     * Return the value of P modulo the size of this permutation.
     */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /**
     * Returns the size of the alphabet I permute.
     */
    int size() {
        return _alphabet.size();
    }

    /**
     * Return the result of applying this permutation to P modulo the
     * alphabet size.
     */
    int permute(int p) {
        int cIn = wrap(p);
        char pIn = _alphabet.toChar(cIn);
        char pOut = permute(pIn);
        int cOut = _alphabet.toInt(pOut);
        return cOut;
    }

    /**
     * Return the result of applying the inverse of this permutation
     * to  C modulo the alphabet size.
     */
    int invert(int c) {
        int cIn = wrap(c);
        char pIn = _alphabet.toChar(cIn);
        char pOut = invert(pIn);
        int cOut = _alphabet.toInt(pOut);
        return cOut;
    }

    /**
     * Return the result of applying this permutation to the index of P
     * in ALPHABET, and converting the result to a character of ALPHABET.
     */
    char permute(char p) {
        char result = p;
        if (_cycle.equals("") || !_alphabet.contains(p)) {
            return result;
        }
        int idx = _cycle.indexOf(String.valueOf(p));
        if (idx != -1) {
            int nextIdx = idx + 1;
            if (_cycle.charAt(nextIdx) != ')') {
                result = _cycle.charAt(nextIdx);
            } else {
                int frontIdx = idx;
                while (_cycle.charAt(frontIdx) != '(') {
                    frontIdx -= 1;
                }
                result = _cycle.charAt(frontIdx + 1);
            }
        }
        return result;
    }

    /**
     * Return the result of applying the inverse of this permutation to C.
     */
    char invert(char c) {
        char result = c;
        if (_cycle.equals("") || !_alphabet.contains(c)) {
            return result;
        }
        int idx = _cycle.indexOf(String.valueOf(c));
        if (idx != -1) {
            int lastIdx = idx - 1;
            if (_cycle.charAt(lastIdx) != '(') {
                result = _cycle.charAt(lastIdx);
            } else {
                int backIdx = idx;
                while (_cycle.charAt(backIdx) != ')') {
                    backIdx += 1;
                }
                result = _cycle.charAt(backIdx - 1);
            }
        }
        return result;
    }

    /**
     * Return the alphabet used to initialize this Permutation.
     */
    Alphabet alphabet() {
        return _alphabet;
    }

    /**
     * Return true iff this permutation is a derangement (i.e., a
     * permutation for which no value maps to itself).
     */
    boolean derangement() {
        for (int i = 0; i < _alphabet.size(); i += 1) {
            char x = _alphabet.toChar(i);
            if (x == permute(x)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Alphabet of this permutation.
     */
    private Alphabet _alphabet;

    /**
     * Cycle of the characters.
     */
    private String _cycle;

}
