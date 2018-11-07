package amazons;

import org.junit.Test;
import static org.junit.Assert.*;
import ucb.junit.textui;

import java.util.Iterator;

public class BoardTest {
    @Test
    public void testSquareDir() {
        Square from = Square.sq(0);
        Square to = Square.sq(11);
        int dir = from.direction(to);
        assertEquals(1, dir);
    }

    @Test
    public void testSquareIterator() {
        Iterator<Square> it = Square.iterator();
        for (int i = 0; i < Board.SIZE * Board.SIZE; i++) {
            it.next();
        }
        assertFalse("Wrong iterator", it.hasNext());
    }

    @Test
    public void testCopy() {
        _board = Board.emptyBoard();
        Board copy = new Board(_board);
        _board.put(Piece.BLACK, Square.sq(0));
        assertEquals(Piece.BLACK, _board.get(0));
        assertEquals(Piece.EMPTY, copy.get(0));
    }

    @Test
    public void testIsLegalSquare() {
        _board = Board.emptyBoard();
        _board.put(Piece.BLACK, Square.sq(0));
        _board.put(Piece.SPEAR, Square.sq(1));
        _board.put(Piece.SPEAR, Square.sq(10));
        _board.put(Piece.SPEAR, Square.sq(11));
        assertFalse(_board.isLegal(Square.sq(0)));
    }

    @Test
    public void testIsLegalQueenMove() {
        _board = Board.emptyBoard();
        _board.put(Piece.BLACK, Square.sq(22));
        _board.put(Piece.SPEAR, Square.sq(52));
        _board.put(Piece.SPEAR, Square.sq(99));
        assertFalse(_board.isLegal(Square.sq(22), Square.sq(82)));
        assertTrue(_board.isLegal(Square.sq(22), Square.sq(44)));
    }

    @Test
    public void testIsLegalWholeMove() {
        _board = Board.emptyBoard();
        _board.put(Piece.BLACK, Square.sq(22));
        _board.put(Piece.SPEAR, Square.sq(52));
        _board.put(Piece.SPEAR, Square.sq(99));
        assertTrue(_board.isLegal(Square.sq(22),
                Square.sq(44), Square.sq(22)));
    }

    @Test
    public void testAsEmpty() {
        _board = Board.emptyBoard();
        _board.put(Piece.BLACK, Square.sq(0));
        _board.put(Piece.SPEAR, Square.sq(1));
        _board.put(Piece.SPEAR, Square.sq(10));
        _board.put(Piece.SPEAR, Square.sq(11));
        assertTrue(_board.isUnblockedMove(Square.sq(0),
                Square.sq(11), Square.sq(11)));
    }

    @Test
    public void testMoveAndUndo() {
        _board = Board.emptyBoard();
        _board.put(Piece.BLACK, Square.sq(89));
        _board.put(Piece.WHITE, Square.sq(22));
        _board.put(Piece.SPEAR, Square.sq(52));
        _board.put(Piece.SPEAR, Square.sq(99));
        _board.makeMove(Square.sq(22), Square.sq(44), Square.sq(22));
        assertEquals(Piece.WHITE, _board.get(44));
        assertEquals(Piece.SPEAR, _board.get(22));
        assertEquals(Piece.BLACK, _board.turn());

        _board.makeMove(Square.sq(89), Square.sq(88), Square.sq(89));
        _board.makeMove(Square.sq(44), Square.sq(42), Square.sq(44));
        _board.undo();
        assertEquals(Piece.WHITE, _board.get(44));
        assertEquals(Piece.EMPTY, _board.get(42));
        assertEquals(Piece.WHITE, _board.turn());
    }

    @Test
    public void testWinner() {
        _board = Board.emptyBoard();
        _board.put(Piece.BLACK, Square.sq(0));
        _board.put(Piece.SPEAR, Square.sq(1));
        _board.put(Piece.SPEAR, Square.sq(10));
        _board.put(Piece.WHITE, Square.sq(99));
        _board.makeMove(Square.sq(99), Square.sq(11), Square.sq(13));
        assertEquals(Piece.WHITE, _board.winner());
    }

    @Test
    public void testReachable() {
        _board = Board.emptyBoard();
        _board.put(Piece.BLACK, Square.sq(0));
        _board.put(Piece.SPEAR, Square.sq(1));
        _board.put(Piece.SPEAR, Square.sq(10));
        _board.put(Piece.SPEAR, Square.sq(11));
        Iterator it = _board.reachableFrom(Square.sq(0), null);
        assertFalse(it.hasNext());
        it = _board.reachableFrom(Square.sq(0), Square.sq(11));
        for (int i = 0; i < 9; i++) {
            assertTrue(it.hasNext());
            it.next();
        }
        assertFalse(it.hasNext());
    }

    @Test
    public void testLegalMove1() {
        _board = Board.emptyBoard();
        _board.put(Piece.BLACK, Square.sq(0));
        _board.put(Piece.SPEAR, Square.sq(1));
        _board.put(Piece.SPEAR, Square.sq(10));
        _board.put(Piece.SPEAR, Square.sq(22));
        _board.put(Piece.SPEAR, Square.sq(31));
        _board.put(Piece.SPEAR, Square.sq(20));
        _board.put(Piece.SPEAR, Square.sq(2));
        _board.put(Piece.SPEAR, Square.sq(12));
        Iterator it = _board.legalMoves(Piece.BLACK);
        assertTrue(it.hasNext());
        Move mv = (Move) it.next();
        assertEquals(Move.mv("a1-b2(b3)"), mv);
        assertTrue(it.hasNext());
        mv = (Move) it.next();
        assertEquals(Move.mv("a1-b2(a1)"), mv);
        assertFalse(it.hasNext());
        _board.put(Piece.SPEAR, Square.sq(11));
        it = _board.legalMoves(Piece.BLACK);
        assertFalse(it.hasNext());
    }

    @Test
    public void testLegalMove2() {
        _board = Board.emptyBoard();
        _board.put(Piece.BLACK, Square.sq(0));
        _board.put(Piece.SPEAR, Square.sq(1));
        _board.put(Piece.SPEAR, Square.sq(10));
        _board.put(Piece.SPEAR, Square.sq(22));
        _board.put(Piece.SPEAR, Square.sq(31));
        _board.put(Piece.SPEAR, Square.sq(20));
        _board.put(Piece.SPEAR, Square.sq(2));
        _board.put(Piece.SPEAR, Square.sq(15));
        Board copy = new Board(_board);
        Iterator it = _board.legalMoves(Piece.BLACK);
        for (int i = 0; i < 5; i++) {
            assertTrue(it.hasNext());
            it.next();
        }
        assertFalse(it.hasNext());
        assertEquals(copy, _board);
    }

    @Test
    public void testLegalMove3() {
        _board = Board.emptyBoard();
        _board.put(Piece.WHITE, Square.sq(0));
        _board.put(Piece.SPEAR, Square.sq(11));
        Iterator it = _board.reachableFrom(Square.sq(0), null);
        for (int i = 0; i < 18; i++) {
            assertTrue(it.hasNext());
            it.next();
        }
        assertFalse(it.hasNext());
        it = _board.legalMoves();
        for (int i = 0; i < 18 * 27 - 2 * 9 - 2 * 2; i++) {
            assertTrue(it.hasNext());
            it.next();
        }
        assertFalse(it.hasNext());
    }


    public static void main(String[] ignored) {
        textui.runClasses(BoardTest.class);
    }

    private Board _board = Board.emptyBoard();
}
