package enigma;

/** Alphabet that includes enum characters only.
 * @author Zhibo Fan
 */
public class SymbolAlphabet extends Alphabet {
    /**
     * An alphabet containing given characters.
     *
     * @param chars string consists of all characters.
     */
    SymbolAlphabet(String chars) {
        _content = chars;
    }

    @Override
    int size() {
        return _content.length();
    }

    @Override
    boolean contains(char ch) {
        return _content.contains(String.valueOf(ch));
    }

    @Override
    char toChar(int index) {
        return _content.charAt(index);
    }

    @Override
    int toInt(char ch) {
        return _content.indexOf(ch);
    }

    /**
     * A string of all available characters.
     */
    private String _content;
}
