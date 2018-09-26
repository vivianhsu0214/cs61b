import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Zhibo Fan
 */
public class TrReader extends Reader {
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     *  in STR unchanged.  FROM and TO must have the same length. */
    private String str;
    private int k;

    public TrReader(Reader str, String from, String to) throws IOException {
        this.str = new String();
        this.k = 0;
        int rec = str.read();
        while(rec != -1) {
            char current = (char) rec;
            int idxFrom = from.indexOf(from.valueOf(current));
            if(idxFrom != -1) {
                current = to.charAt(idxFrom);
            }
            String appendix = String.valueOf(current);
            this.str += appendix;
            rec = str.read();
        }
        // FILL IN
    }


    @Override
    public int read(char cbuf[], int off, int len) throws IOException {
        if(k == str.length() || off >= str.length()) {
            return -1;
        }
        if(str.length() - k <= len) {
            len = str.length() - k;
        }
        str.getChars(k, k + len, cbuf, off);
        k += len;
        return len;
    }

    @Override
    public void close() throws IOException {
        str = null;
    }
    // FILL IN
    // NOTE: Until you fill in the right methods, the compiler will
    //       reject this file, saying that you must declare TrReader
    //     abstract.  Don't do that; define the right methods instead!
}


