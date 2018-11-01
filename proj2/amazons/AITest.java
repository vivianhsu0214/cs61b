package amazons;

import org.junit.Test;
import static org.junit.Assert.*;
import ucb.junit.textui;

import static amazons.Square.sq;
import static amazons.Piece.*;

public class AITest {
    @Test
    public void notStubborn() {
        GUI gui = new GUI("AmazonTest");
        Controller control = new Controller(gui, null,
                new TextReporter(), new GUIPlayer(gui), new AI());
        System.out.println("Amazons 61B, version test");
        try {
            control.play();
            System.exit(0);
        } catch (IllegalStateException excp) {
            System.err.printf("Internal error: %s%n", excp.getMessage());
            System.exit(1);
        }
    }



    @Test
    public void basicSanity() {

    }

    @Test
    public void evaluateWell() {

    }

    @Test
    public void testAlphaBetaPrune() {

    }

    public static void main(String[] ignored) {
        textui.runClasses(AITest.class);
    }

    /**
     * A board where movements are stricted.
     */
    static Board testBoard;

    static {
        testBoard = Board.emptyBoard();
        testBoard.put(BLACK, sq(0));
        testBoard.put(BLACK, sq(9));
        testBoard.put(BLACK, sq(90));
        testBoard.put(BLACK, sq(99));
        testBoard.put(WHITE, sq(44));
        testBoard.put(WHITE, sq(45));
        testBoard.put(WHITE, sq(54));
        testBoard.put(WHITE, sq(55));
        testBoard.put(SPEAR, sq(33));
        testBoard.put(SPEAR, sq(34));
        testBoard.put(SPEAR, sq(35));
        testBoard.put(SPEAR, sq(36));
        testBoard.put(SPEAR, sq(43));
        testBoard.put(SPEAR, sq(46));
        testBoard.put(SPEAR, sq(53));
        testBoard.put(SPEAR, sq(56));
        testBoard.put(SPEAR, sq(63));
        testBoard.put(SPEAR, sq(64));
        testBoard.put(SPEAR, sq(65));
        testBoard.put(SPEAR, sq(66));
    }
}
