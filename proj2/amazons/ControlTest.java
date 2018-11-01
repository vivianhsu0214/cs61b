package amazons;

import org.junit.Test;
import static org.junit.Assert.*;
import ucb.junit.textui;

public class ControlTest {

    @Test
    public void tryParse() {
        String cmd = "a10-b10(c10)";
        Move mv = Move.mv(cmd);
        assertEquals(cmd, mv.toString());
    }

    public static void main(String[] ignored) {
        textui.runClasses(ControlTest.class);
    }
}
