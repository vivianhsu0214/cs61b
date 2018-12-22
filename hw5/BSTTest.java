import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BSTTest {
    @Test
    public void testIntegration() {
        BSTStringSet set = new BSTStringSet();
        set.put("2");
        set.put("1");
        set.put("3");
        set.put("4");
        List<String> temp = set.asList();
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < temp.size(); i++) {
            result.add(temp.get(i));
        }
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("1");
        expected.add("2");
        expected.add("3");
        expected.add("4");
        assertEquals(expected, result);
    }
}