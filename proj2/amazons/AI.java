package amazons;

import java.util.Iterator;

import static amazons.Piece.*;

/**
 * A Player that automatically generates moves.
 *
 * @author Zhibo Fan
 */
class AI extends Player {

    /**
     * A position magnitude indicating a win (for white if positive, black
     * if negative).
     */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /**
     * A magnitude greater than a normal value.
     */
    private static final int INFTY = Integer.MAX_VALUE;

    /**
     * A number of how many steps later
     * should the depth of the game tree change.
     */
    private static final int STEP_THRESH = 60;

    /**
     * A new AI with no piece or controller (intended to produce
     * a template).
     */
    AI() {
        this(null, null);
    }

    /**
     * A new AI playing PIECE under control of CONTROLLER.
     */
    AI(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new AI(piece, controller);
    }

    @Override
    String myMove() {
        Move move = findMove();
        _controller.reportMove(move);
        return move.toString();
    }

    /**
     * Return a move for me from the current position, assuming there
     * is a move.
     */
    private Move findMove() {
        Board b = new Board(board());
        findMove(b, maxDepth(b), true, 1, -INFTY, INFTY);
        return _lastFoundMove;
    }

    /**
     * The move found by the last call to one of the ...FindMove methods
     * below.
     */
    private Move _lastFoundMove;

    /**
     * Find a move from position BOARD and return its value, recording
     * the move found in _lastFoundMove iff SAVEMOVE. The move
     * should have maximal value or have value > BETA if SENSE==1,
     * and minimal value or value < ALPHA if SENSE==-1. Searches up to
     * DEPTH levels.  Searching at level 0 simply returns a static estimate
     * of the board value and does not set _lastMoveFound.
     */
    private int findMove(Board board, int depth, boolean saveMove, int sense,
                         int alpha, int beta) {
        if (depth == 0 || board.winner() != EMPTY) {
            return staticScore(board);
        }
        Move decision = null;
        int v;
        if (sense == 1) {
            v = Integer.MIN_VALUE + 1;
            Iterator<Move> it = board.legalMoves(_myPiece);
            while (it.hasNext()) {
                Move successor = it.next();
                int thisValue = findMove(board, depth,
                        false, -sense, alpha, beta);
                if (thisValue >= v) {
                    v = thisValue;
                    decision = successor;
                }
                if (v >= beta) {
                    break;
                }
                alpha = Integer.max(alpha, v);
            }
        } else {
            v = Integer.MAX_VALUE - 1;
            Iterator<Move> it = board.legalMoves(opponent());
            while (it.hasNext()) {
                Move successor = it.next();
                int thisValue = findMove(board, depth - 1,
                        false, -sense, alpha, beta);
                if (thisValue <= v) {
                    v = thisValue;
                    decision = successor;
                }
                if (v <= alpha) {
                    break;
                }
                beta = Integer.min(beta, v);
            }
        }
        if (saveMove) {
            _lastFoundMove = decision;
        }
        return v;
    }

    /**
     * Return a heuristically determined maximum search depth
     * based on characteristics of BOARD.
     */
    private int maxDepth(Board board) {
        int N = board.numMoves();
        return (N > STEP_THRESH ? 3 : 2);
    }


    /**
     * Return a heuristic value for BOARD.
     */
    private int staticScore(Board board) {
        Piece winner = board.winner();
        if (winner != myPiece()) {
            return -WINNING_VALUE;
        } else if (winner == myPiece()) {
            return WINNING_VALUE;
        }
        int opp = 0;
        int mine = board.SIZE * board.SIZE;
        for (int i = 0; i < board.SIZE * board.SIZE; i++) {
            if (board.get(i) == myPiece()) {
                int valid = 0;
                Iterator it = board.reachableFrom(Square.sq(i), null);
                while (it.hasNext()) {
                    it.next();
                    valid += 1;
                }
                mine = Integer.min(mine, valid);
            } else if (board.get(i) == myPiece()) {
                int valid = 0;
                Iterator it = board.reachableFrom(Square.sq(i), null);
                while (it.hasNext()) {
                    it.next();
                    valid += 1;
                }
                opp = Integer.max(opp, valid);
            }
        }
        return - opp;
//        int count = 0;
//        Iterator i = board.legalMoves();
//        while(i.hasNext()) {
//            i.next();
//            count += 1;
//        }
//        return count;
    }

    /**
     * Returns my opponent.
     *
     * @return opponent piece
     */
    private Piece opponent() {
        return (_myPiece == BLACK ? WHITE : BLACK);
    }
}
