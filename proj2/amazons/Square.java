package amazons;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static amazons.Utils.*;

/**
 * Represents a position on an Amazons board.  Positions are numbered
 * from 0 (lower-left corner) to 99 (upper-right corner).  Squares
 * are immutable and unique: there is precisely one square created for
 * each distinct position.  Clients create squares using the factory method
 * sq, not the constructor.  Because there is a unique Square object for each
 * position, you can freely use the cheap == operator (rather than the
 * .equals method) to compare Squares, and the program does not waste time
 * creating the same square over and over again.
 *
 * @author Zhibo Fan
 */
final class Square {

    /**
     * The regular expression for a square designation (e.g.,
     * a3). For convenience, it is in parentheses to make it a
     * group.  This subpattern is intended to be incorporated into
     * other pattern that contain square designations (such as
     * patterns for moves).
     */
    static final String SQ = "([a-j](?:10|[1-9]))";

    /**
     * Return my row position, where 0 is the bottom row.
     */
    int row() {
        return _row;
    }

    /**
     * Return my column position, where 0 is the leftmost column.
     */
    int col() {
        return _col;
    }

    /**
     * Return my index position (0-99).  0 represents square a1, and 99
     * is square j10.
     */
    int index() {
        return _index;
    }

    /**
     * Return true iff THIS - TO is a valid queen move.
     */
    boolean isQueenMove(Square to) {
        boolean result = this != to
                && Square.exists(to)
                && (to.col() == col()
                || to.row() == row()
                || Math.abs(to.col() - col()) == Math.abs(to.row() - row()));
        return result;
    }

    /**
     * Definitions of direction for queenMove.  DIR[k] = (dcol, drow)
     * means that to going one step from (col, row) in direction k,
     * brings us to (col + dcol, row + drow).
     */
    static final int[][] DIR = {
            {0, 1}, {1, 1}, {1, 0}, {1, -1},
            {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}
    };

    /**
     * Return the Square that is STEPS>0 squares away from me in direction
     * DIR, or null if there is no such square.
     * DIR = 0 for north, 1 for northeast, 2 for east, etc., up to 7 for west.
     * If DIR has another value, return null. Thus, unless the result
     * is null the resulting square is a queen move away rom me.
     */
    Square queenMove(int dir, int steps) {
        if (dir > 7 || dir < 0) {
            return null;
        }
        int dx = DIR[dir][0] * steps;
        int dy = DIR[dir][1] * steps;
        Square to = Square.sq(col() + dx, row() + dy);
        return (Square.exists(to) ? to : null);
    }

    /**
     * Return the direction (an int as defined in the documentation
     * for queenMove) of the queen move THIS-TO.
     */
    int direction(Square to) {
        if (!isQueenMove(to)) {
            return -1;
        }
        int dx = Integer.compare(to.col(), col());
        int dy = Integer.compare(to.row(), row());
        int dir = -1;
        for (int i = 0; i < 8; i += 1) {
            if (DIR[i][0] == dx && DIR[i][1] == dy) {
                dir = i;
                break;
            }
        }
        return dir;
    }

    /**
     * Return the steps of a movement.
     * @param to target square
     * @return absolute steps
     */
    int steps(Square to) {
        int dir = direction(to);
        if (dir != 0 && dir != 4) {
            return Math.abs(to.col() - col());
        } else {
            return Math.abs(to.row() - row());
        }
    }

    @Override
    public String toString() {
        return _str;
    }

    @Override
    public  boolean equals(Object to) {
        return to instanceof Square
                && index() == ((Square) to).index();
    }

    /**
     * Return true iff COL ROW is a legal square.
     */
    static boolean exists(int col, int row) {
        return row >= 0 && col >= 0 && row < Board.SIZE && col < Board.SIZE;
    }

    /**
     * Return true iff SQUARE is a legal square.
     *
     * @param square the input square
     * @return whether a square is valid or not
     */
    static boolean exists(Square square) {
        if (square == null) {
            return false;
        }
        return exists(square.col(), square.row());
    }

    /**
     * Return true iff square of the INDEX is a legal square.
     *
     * @param index the square of the index
     * @return whether square of that index is valid
     */
    static boolean exists(int index) {
        return exists(index % 10,index / 10);
    }

    /**
     * Return the (unique) Square denoting COL ROW.
     */
    static Square sq(int col, int row) {
        if (!exists(row, col)) {
            throw error("row or column out of bounds");
        }
        return sq(10 * row + col);
    }

    /**
     * Return the (unique) Square denoting the position with index INDEX.
     */
    static Square sq(int index) {
        return SQUARES[index];
    }

    /**
     * Return the (unique) Square denoting the position COL ROW, where
     * COL ROW is the standard text format for a square (e.g., a4).
     */
    static Square sq(String col, String row) {
        assert col.matches("[a-j]");
        assert row.matches("[1-9]|10");
        char colChar = col.charAt(0);
        return sq(colChar - 'a', Integer.parseInt(row) - 1);
    }

    /**
     * Return the (unique) Square denoting the position in POSN, in the
     * standard text format for a square (e.g. a4). POSN must be a
     * valid square designation.
     */
    static Square sq(String posn) {
        assert posn.matches(SQ);
        String[] temp = posn.split("");
        String num = "";
        for (int i = 1; i < temp.length; i++) {
            num += temp[i];
        }
        return sq(temp[0], num);
    }

    /**
     * Return an iterator over all Squares.
     */
    static Iterator<Square> iterator() {
        return SQUARE_LIST.iterator();
    }

    /**
     * Return the Square with index INDEX.
     */
    private Square(int index) {
        _index = index;
        _row = index / 10;
        _col = index % 10;
        char rowChar = (char)(_row + '1');
        char colChar = (char)(_col + 'a');
        _str = String.valueOf(colChar) + (rowChar == ':' ? "10"
                : String.valueOf(rowChar));
        assert _str.matches(SQ);
    }

    /**
     * The cache of all created squares, by index.
     */
    private static final Square[] SQUARES =
            new Square[Board.SIZE * Board.SIZE];

    /**
     * SQUARES viewed as a List.
     */
    private static final List<Square> SQUARE_LIST = Arrays.asList(SQUARES);

    static {
        for (int i = Board.SIZE * Board.SIZE - 1; i >= 0; i -= 1) {
            SQUARES[i] = new Square(i);
        }
    }

    /**
     * My index position.
     */
    private final int _index;

    /**
     * My row and column (redundant, since these are determined by _index).
     */
    private final int _row, _col;

    /**
     * My String denotation.
     */
    private final String _str;

}
