package amazons;

import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

import static amazons.Piece.*;

/**
 * The state of an Amazons Game.
 *
 * @author Zhibo Fan
 */
class Board {

    /**
     * The number of squares on a side of the board.
     */
    static final int SIZE = 10;

    /**
     * The number of queens on each side.
     */
    static final int QUEENS = 4;

    /**
     * Initializes a game board with SIZE squares on a side in the
     * initial position.
     */
    Board() {
        init();
    }

    /**
     * Initializes a copy of MODEL.
     */
    Board(Board model) {
        copy(model);
    }

    /**
     * Return an empty board.
     *
     * @return a board without anything
     */
    static Board emptyBoard() {
        Board b = new Board();
        for (int i = 0; i < SIZE * SIZE; i++) {
            b._spear[i] = false;
            b._white[i] = false;
            b._black[i] = false;
        }
        return b;
    }

    /**
     * Clear the board.
     */
    void clear() {
        for (int i = 0; i < SIZE * SIZE; i++) {
            _spear[i] = false;
            _white[i] = false;
            _black[i] = false;
        }
        _winner = EMPTY;
        _movement = new Stack<String>();
        _turn = WHITE;
    }

    /**
     * Clear a given square.
     * Used for tests.
     *
     * @param index index of the square
     */
    void clear(int index) {
        _spear[index] = false;
        _black[index] = false;
        _white[index] = false;
    }

    /**
     * Copies MODEL into me.
     */
    void copy(Board model) {
        _turn = model.turn();
        _winner = model.winner();
        _black = new boolean[SIZE * SIZE];
        _white = new boolean[SIZE * SIZE];
        _spear = new boolean[SIZE * SIZE];
        _movement = new Stack<String>();
        for (int i = 0; i < SIZE * SIZE; i++) {
            _black[i] = model._black[i];
            _white[i] = model._white[i];
            _spear[i] = model._spear[i];
        }
        for (String m : model._movement) {
            _movement.push(m);
        }
    }

    /**
     * Clears the board to the initial position.
     */
    void init() {
        _turn = WHITE;
        _winner = EMPTY;
        _black = new boolean[SIZE * SIZE];
        _white = new boolean[SIZE * SIZE];
        _spear = new boolean[SIZE * SIZE];
        _movement = new Stack<String>();
        put(BLACK, Square.sq(Integer.parseInt("60")));
        put(BLACK, Square.sq(Integer.parseInt("93")));
        put(BLACK, Square.sq(Integer.parseInt("96")));
        put(BLACK, Square.sq(Integer.parseInt("69")));
        put(WHITE, Square.sq(Integer.parseInt("30")));
        put(WHITE, Square.sq(Integer.parseInt("39")));
        put(WHITE, Square.sq(Integer.parseInt("3")));
        put(WHITE, Square.sq(Integer.parseInt("6")));
    }

    /**
     * Return the Piece whose move it is (WHITE or BLACK).
     */
    Piece turn() {
        return _turn;
    }

    /**
     * Return the number of moves (that have not been undone) for this
     * board.
     */
    int numMoves() {
        return _movement.size();
    }

    /**
     * Return the winner in the current position, or null if the game is
     * not yet finished.
     */
    Piece winner() {
        return _winner;
    }

    /**
     * Return the contents the square at S.
     */
    final Piece get(Square s) {
        int index = s.index();
        if (_black[index]) {
            return BLACK;
        } else if (_white[index]) {
            return WHITE;
        } else if (_spear[index]) {
            return SPEAR;
        }
        return EMPTY;
    }

    /**
     * Return the contents of the square at (COL, ROW), where
     * 0 <= COL, ROW <= 9.
     */
    final Piece get(int col, int row) {
        return get(row * 10 + col);
    }

    /**
     * Return the contents of the square at COL ROW.
     */
    final Piece get(char col, char row) {
        throw new UnsupportedOperationException();
    }

    /**
     * Return the contents of the square of INDEX.
     *
     * @param index the index
     * @return contents as piece
     */
    final Piece get(int index) {
        assert boardCheck();
        if (_black[index]) {
            return BLACK;
        } else if (_white[index]) {
            return WHITE;
        } else if (_spear[index]) {
            return SPEAR;
        } else {
            return EMPTY;
        }
    }

    /**
     * Set square S to P.
     */
    final void put(Piece p, Square s) {
        int index = s.index();
        if (p.equals(BLACK)) {
            _black[index] = true;
        } else if (p.equals(WHITE)) {
            _white[index] = true;
        } else if (p.equals(SPEAR)) {
            _spear[index] = true;
        } else {
            _black[index] = false;
            _white[index] = false;
            _spear[index] = false;
        }
    }

    /**
     * Set square (COL, ROW) to P.
     */
    final void put(Piece p, int col, int row) {
        assert Square.exists(col, row);
        put(p, Square.sq(col, row));
    }

    /**
     * Set square COL ROW to P.
     */
    final void put(Piece p, char col, char row) {
        put(p, col - 'a', row - '1');
    }

    /**
     * Return true iff FROM - TO is an unblocked queen move on the current
     * board, ignoring the contents of ASEMPTY, if it is encountered.
     * For this to be true, FROM-TO must be a queen move and the
     * squares along it, other than FROM and ASEMPTY, must be
     * empty. ASEMPTY may be null, in which case it has no effect.
     */
    boolean isUnblockedMove(Square from, Square to, Square asEmpty) {
        int type = 0;
        if (asEmpty != null) {
            int emptyIndex = asEmpty.index();
            type = (_black[emptyIndex] ? 1
                    : (_white[emptyIndex] ? 2
                            : (_spear[emptyIndex] ? 3 : 0)));
            _black[emptyIndex] = false;
            _white[emptyIndex] = false;
            _spear[emptyIndex] = false;
        }
        boolean result = isLegal(from, to);
        if (type == 1) {
            _black[asEmpty.index()] = true;
        } else if (type == 2) {
            _white[asEmpty.index()] = true;
        } else if (type == 3) {
            _spear[asEmpty.index()] = true;
        }
        return result;
    }

    /**
     * Return true iff FROM is a valid starting square for a move.
     */
    boolean isLegal(Square from) {
        int col = from.col();
        int row = from.row();
        return (Square.exists(col - 1, row - 1)
                && get(col - 1, row - 1) == EMPTY)
                || (Square.exists(col - 1, row)
                && get(col - 1, row) == EMPTY)
                || (Square.exists(col - 1, row + 1)
                && get(col - 1, row + 1) == EMPTY)
                || (Square.exists(col, row - 1)
                && get(col, row - 1) == EMPTY)
                || (Square.exists(col, row + 1)
                && get(col, row + 1) == EMPTY)
                || (Square.exists(col + 1, row - 1)
                && get(col + 1, row - 1) == EMPTY)
                || (Square.exists(col + 1, row)
                && get(col + 1, row) == EMPTY)
                || (Square.exists(col + 1, row + 1)
                && get(col + 1, row + 1) == EMPTY);
    }

    /**
     * Return true iff FROM-TO is a valid first part of move, ignoring
     * spear throwing.
     */
    boolean isLegal(Square from, Square to) {
        boolean result = true;
        int dir = from.direction(to);
        if (!isLegal(from) || dir == -1) {
            return false;
        }
        int type = (_black[from.index()] ? 1
                : (_white[from.index()] ? 2 : 0));
        int minx = Math.min(from.col(), to.col());
        int miny = Math.min(from.row(), to.row());
        int maxx = Math.max(from.col(), to.col());
        int maxy = Math.max(from.row(), to.row());
        _black[from.index()] = false;
        _white[from.index()] = false;
        if (dir % 4 == 0) {
            for (int i = miny; i <= maxy; i++) {
                if (get(minx, i) != EMPTY) {
                    result = false;
                }
            }
        } else if (dir % 4 == 2) {
            for (int i = minx; i <= maxx; i++) {
                if (get(i, miny) != EMPTY) {
                    result = false;
                }
            }
        } else if (dir % 4 == 1) {
            for (int i = minx, j = miny; i <= maxx; i++, j++) {
                if (get(i, j) != EMPTY) {
                    result = false;
                }
            }
        } else {
            for (int i = minx, j = maxy; i <= maxx; i++, j--) {
                if (get(i, j) != EMPTY) {
                    result = false;
                }
            }
        }
        _black[from.index()] = type == 1;
        _white[from.index()] = type == 2;
        return result && Square.exists(from) && Square.exists(to);
    }

    /**
     * Return true iff FROM-TO(SPEAR) is a legal move in the current
     * position.
     */
    boolean isLegal(Square from, Square to, Square spear) {
        return isUnblockedMove(from, to, null)
                && isUnblockedMove(to, spear, from);
    }

    /**
     * Return true iff MOVE is a legal move in the current
     * position when really playing the game.
     */
    boolean isLegal(Move move) {
        return move != null
                && get(move.from().index()) == turn()
                && isLegal(move.from(), move.to(), move.spear());
    }

    /**
     * Move FROM-TO(SPEAR), assuming this is a legal move.
     */
    void makeMove(Square from, Square to, Square spear) {
        if (_winner != EMPTY) {
            return;
        }
        if (!isLegal(from, to, spear)) {
            return;
        }
        if (turn() == BLACK) {
            _black[from.index()] = false;
            _black[to.index()] = true;
        } else {
            _white[from.index()] = false;
            _white[to.index()] = true;
        }
        _spear[spear.index()] = true;
        Move m = Move.mv(from, to, spear);
        _movement.push(m.toString());
        _turn = (turn() == BLACK ? WHITE : BLACK);
        updateWinner();
    }

    /**
     * Update winner after movement.
     */
    private void updateWinner() {
        if (_winner == WHITE || _winner == BLACK) {
            return;
        }
        boolean isWin = true;
        for (int i = 0; i < SIZE * SIZE; i++) {
            if (_black[i] && isLegal(Square.sq(i))
                    && _turn == BLACK) {
                isWin = false;
            } else if (_white[i] && isLegal(Square.sq(i))
                    && _turn == WHITE) {
                isWin = false;
            }
        }
        if (isWin) {
            _winner = (_turn == BLACK ? WHITE : BLACK);
        }
    }

    /**
     * Move according to MOVE, assuming it is a legal move.
     */
    void makeMove(Move move) {
        makeMove(move.from(), move.to(), move.spear());
    }

    /**
     * Undo one move.  Has no effect on the initial board.
     */
    void undo() {
        if (_movement.size() < 2) {
            return;
        }
        Move m = Move.mv(_movement.pop());
        _spear[m.spear().index()] = false;
        if (turn() == BLACK) {
            _white[m.from().index()] = true;
            _white[m.to().index()] = false;
        } else {
            _black[m.from().index()] = true;
            _black[m.to().index()] = false;
        }
        _turn = (_turn == BLACK ? WHITE : BLACK);
        _winner = EMPTY;
        updateWinner();
    }

    /**
     * Return an Iterator over the Squares that are reachable by an
     * unblocked queen move from FROM. Does not pay attention to what
     * piece (if any) is on FROM, nor to whether the game is finished.
     * Treats square ASEMPTY (if non-null) as if it were EMPTY.  (This
     * feature is useful when looking for Moves, because after moving a
     * piece, one wants to treat the Square it came from as empty for
     * purposes of spear throwing.)
     */
    Iterator<Square> reachableFrom(Square from, Square asEmpty) {
        return new ReachableFromIterator(from, asEmpty);
    }

    /**
     * Return an Iterator over all legal moves on the current board.
     */
    Iterator<Move> legalMoves() {
        return new LegalMoveIterator(_turn);
    }

    /**
     * Return an Iterator over all legal moves on the current board for
     * SIDE (regardless of whose turn it is).
     */
    Iterator<Move> legalMoves(Piece side) {
        return new LegalMoveIterator(side);
    }

    /**
     * An iterator used by reachableFrom.
     */
    private class ReachableFromIterator implements Iterator<Square> {

        /**
         * Iterator of all squares reachable by queen move from FROM,
         * treating ASEMPTY as empty.
         */
        ReachableFromIterator(Square from, Square asEmpty) {
            _from = from;
            _dir = -1;
            _steps = 1;
            _asEmpty = asEmpty;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _dir < 8 && _dir != -1;
        }

        @Override
        public Square next() {
            int dx = Square.DIR[_dir][0] * _steps;
            int dy = Square.DIR[_dir][1] * _steps;
            toNext();
            return Square.sq(_from.col() + dx, _from.row() + dy);
        }

        /**
         * Advance _dir and _steps, so that the next valid Square is
         * _steps steps in direction _dir from _from.
         */
        private void toNext() {
            boolean valid = _dir != -1;
            for (int i = _dir + 1; i < 8; i++) {
                int dx = Square.DIR[i][0] * _steps;
                int dy = Square.DIR[i][1] * _steps;
                if (!Square.exists(_from.col() + dx, _from.row() + dy)) {
                    continue;
                }
                Square to = Square.sq(_from.col() + dx, _from.row() + dy);
                if (Square.exists(to) && isUnblockedMove(_from, to, _asEmpty)) {
                    _dir = i;
                    return;
                }
            }
            _dir = -1;
            if (!valid) {
                return;
            }
            _steps += 1;
            toNext();
        }

        /**
         * Starting square.
         */
        private Square _from;
        /**
         * Current direction.
         */
        private int _dir;
        /**
         * Current distance.
         */
        private int _steps;
        /**
         * Square treated as empty.
         */
        private Square _asEmpty;
    }

    /**
     * An iterator used by legalMoves.
     */
    private class LegalMoveIterator implements Iterator<Move> {

        /**
         * All legal moves for SIDE (WHITE or BLACK).
         */
        LegalMoveIterator(Piece side) {
            _startingSquares = Square.iterator();
            _spearThrows = NO_SQUARES;
            _pieceMoves = NO_SQUARES;
            _fromPiece = side;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _start != null;
        }

        @Override
        public Move next() {
            Move mv = Move.mv(_start, _nextSquare, _spearThrows.next());
            if (!_spearThrows.hasNext()) {
                toNext();
            }
            return mv;
        }

        /**
         * Advance so that the next valid Move is
         * _start-_nextSquare(sp), where sp is the next value of
         * _spearThrows.
         */
        private void toNext() {
            if (!_pieceMoves.hasNext()) {
                _start = null;
                while (_startingSquares.hasNext()) {
                    int index = _startingSquares.next().index();
                    if (((_fromPiece == BLACK && _black[index])
                            || (_fromPiece == WHITE && _white[index]))
                            && isLegal(Square.sq(index))) {
                        _start = Square.sq(index);
                        break;
                    }
                }
                if (_start == null) {
                    return;
                }
                _pieceMoves = reachableFrom(_start, null);
            }
            _nextSquare = _pieceMoves.next();
            _spearThrows = reachableFrom(_nextSquare, _start);
        }

        /**
         * Color of side whose moves we are iterating.
         */
        private Piece _fromPiece;
        /**
         * Current starting square.
         */
        private Square _start;
        /**
         * Remaining starting squares to consider.
         */
        private Iterator<Square> _startingSquares;
        /**
         * Current piece's new position.
         */
        private Square _nextSquare;
        /**
         * Remaining moves from _start to consider.
         */
        private Iterator<Square> _pieceMoves;
        /**
         * Remaining spear throws from _piece to consider.
         */
        private Iterator<Square> _spearThrows;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Board)) {
            return false;
        }
        boolean result = true;
        Board another = (Board) obj;
        for (int i = 0; i < SIZE * SIZE; i++) {
            result &= _black[i] == another._black[i]
                    && _white[i] == another._white[i]
                    && _spear[i] == another._spear[i];
        }
        result &= turn() == another.turn();
        result &= winner() == another.winner();
        if (_movement.size() != another._movement.size()) {
            return false;
        }
        for (int i = 0; i < _movement.size(); i++) {
            result &= _movement.get(i) == another._movement.get(i);
        }
        return result;
    }

    @Override
    public String toString() {
        return toStringHelper(SIZE - 1);
    }

    @Override
    public int hashCode() {
        return _winner.hashCode() + _turn.hashCode();
    }

    /**
     * A helper function of toString.
     *
     * @param row the line to print
     * @return string
     */
    private String toStringHelper(int row) {
        String result = "";
        if (row < 0) {
            return result;
        }
        for (int i = 0; i < SIZE; i++) {
            int index = row * 10 + i;
            result += (i == 0 ? "   " : "");
            if (_black[index]) {
                result += "B";
            } else if (_white[index]) {
                result += "W";
            } else if (_spear[index]) {
                result += "S";
            } else {
                result += "-";
            }
            result += (i == SIZE - 1 ? "\n" : " ");
        }
        return result + toStringHelper(row - 1);
    }

    /**
     * Check if the booleans are consistent.
     *
     * @return whether the board model is consistent
     */
    public boolean boardCheck() {
        for (int i = 0; i < SIZE * SIZE; i++) {
            if ((_black[i] && _white[i])
                    || (_black[i] && _spear[i])
                    || (_spear[i] && _white[i])) {
                System.out.println(i);
                System.out.println("black: " + _black[i]);
                System.out.println("white: " + _white[i]);
                System.out.println("spear: " + _spear[i]);
                return false;
            }
        }
        return true;
    }
    /**
     * Set current turn.
     * @param turn which turn to modify
     */
    public void setTurn(Piece turn) {
        _turn = turn;
    }

    /**
     * Getter for white.
     *
     * @param index index of the square
     * @return result
     */
    public boolean getWhite(int index) {
        return _black[index];
    }

    /**
     * An empty iterator for initialization.
     */
    private static final Iterator<Square> NO_SQUARES =
            Collections.emptyIterator();

    /**
     * Piece whose turn it is (BLACK or WHITE).
     */
    private Piece _turn;
    /**
     * Cached value of winner on this board, or EMPTY if it has not been
     * computed.
     */
    private Piece _winner;

    /**
     * Boolean list of black amazons.
     */
    private boolean[] _black;
    /**
     * Boolean list of white amazons.
     */
    private boolean[] _white;
    /**
     * Boolean list of arrows.
     */
    private boolean[] _spear;
    /**
     * Info of all moves.
     */
    private Stack<String> _movement;
}
