package amazons;

import org.junit.Test;

import static org.junit.Assert.*;

import ucb.junit.textui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Junit tests for our Board iterators.
 *
 * @author Zhibo Fan
 */
public class IteratorTests {

    /**
     * Run the JUnit tests in this package.
     */
    public static void main(String[] ignored) {
        textui.runClasses(IteratorTests.class);
    }

    /**
     * Tests reachableFromIterator to make sure it returns all reachable
     * Squares. This method may need to be changed based on
     * your implementation.
     */
    @Test
    public void testReachableFrom() {
        Board b = Board.emptyBoard();
        buildBoard(b, reachableFromTestBoard);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(5, 5), null);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            assertTrue(reachableFromTestSquares.contains(s));
            numSquares += 1;
            squares.add(s);
        }
        assertEquals(reachableFromTestSquares.size(), numSquares);
        assertEquals(reachableFromTestSquares.size(), squares.size());
    }

    private void buildBoard(Board b, Piece[][] target) {
        for (int col = 0; col < Board.SIZE; col++) {
            for (int row = 0; row < Board.SIZE; row++) {
                Piece piece = target[row][col];
                b.put(piece, Square.sq(col, row));
            }
        }
    }

    static final Piece E = Piece.EMPTY;

    static final Piece W = Piece.WHITE;

    static final Piece B = Piece.BLACK;

    static final Piece S = Piece.SPEAR;

    static final Piece[][] reachableFromTestBoard =
        {
            {E, E, E, E, E, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, W, W},
            {E, E, E, E, E, E, E, S, E, S},
            {E, E, E, S, S, S, S, E, E, S},
            {E, E, E, S, E, E, E, E, B, E},
            {E, E, E, S, E, W, E, E, B, E},
            {E, E, E, S, S, S, B, W, B, E},
            {E, E, E, E, E, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
        };

    static final Set<Square> reachableFromTestSquares =
        new HashSet<>(Arrays.asList(
            Square.sq(5, 4),
            Square.sq(4, 4),
            Square.sq(4, 5),
            Square.sq(6, 5),
            Square.sq(7, 5),
            Square.sq(6, 4),
            Square.sq(7, 3),
            Square.sq(8, 2)));

}
