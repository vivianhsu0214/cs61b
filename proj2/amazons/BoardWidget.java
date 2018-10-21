package amazons;

import ucb.gui2.Pad;

import java.awt.*;
import java.io.IOException;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import static amazons.Piece.*;
import static amazons.Square.sq;
import static amazons.Move.mv;

// This skeleton provides a bit of suggested structure.   It's up to you to
// discover what needs to be added.  You are NOT required to reproduce the
// GUI of the staff program.

/**
 * A widget that displays an Amazons game.
 *
 * @author Zhibo Fan
 */
class BoardWidget extends Pad {

    /* Parameters controlling sizes, speeds, colors, and fonts. */

    /**
     * Colors of empty squares and grid lines.
     */
    static final Color
            SPEAR_COLOR = new Color(64, 64, 64),
            LIGHT_SQUARE_COLOR = new Color(238, 207, 161),
            DARK_SQUARE_COLOR = new Color(205, 133, 63);

    /**
     * Locations of images of white and black queens.
     */
    private static final String
            WHITE_QUEEN_IMAGE = "wq4.png",
            BLACK_QUEEN_IMAGE = "bq4.png";

    /**
     * Size parameters.
     */
    private static final int
            SQUARE_SIDE = 30,
            BOARD_SIDE = SQUARE_SIDE * 10;

    /**
     * A graphical representation of an Amazons board that sends commands
     * derived from mouse clicks to COMMANDS.
     */
    BoardWidget(ArrayBlockingQueue<String> commands) {
        _commands = commands;
        setMouseHandler("click", this::mouseClicked);
        setPreferredSize(BOARD_SIDE, BOARD_SIDE);

        try {
            _whiteQueen = ImageIO.read(Utils.getResource(WHITE_QUEEN_IMAGE));
            _blackQueen = ImageIO.read(Utils.getResource(BLACK_QUEEN_IMAGE));
        } catch (IOException excp) {
            System.err.println("Could not read queen images.");
            System.exit(1);
        }
        _acceptingMoves = false;
        _moveSquares = new LinkedList<Square>();
    }

    /**
     * Draw the bare board G.
     */
    private void drawGrid(Graphics2D g) {
        g.setColor(LIGHT_SQUARE_COLOR);
        g.fillRect(0, 0, BOARD_SIDE, BOARD_SIDE);
        g.setStroke(new BasicStroke(1.0f));
        g.setColor(Color.BLACK);
        for (int i = 1; i < 10; i++) {
            g.drawLine(i * SQUARE_SIDE, 0, i * SQUARE_SIDE, BOARD_SIDE);
            g.drawLine(0, i * SQUARE_SIDE, BOARD_SIDE, i * SQUARE_SIDE);
        }
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        drawGrid(g);
        for (int i = 0; i < _board.SIZE * _board.SIZE; i++) {
            if (_board.get(i) == BLACK) {
                drawQueen(g, Square.sq(i), BLACK);
            } else if (_board.get(i) == WHITE) {
                drawQueen(g, Square.sq(i), WHITE);
            } else if (_board.get(i) == SPEAR) {
                g.setColor(SPEAR_COLOR);
                g.fillRect(cx(i % 10), cy(i / 10), SQUARE_SIDE, SQUARE_SIDE);
            }
        }
        g.setColor(DARK_SQUARE_COLOR);
        for(Square sq : _moveSquares) {
            g.fillRect(cx(sq), cy(sq), SQUARE_SIDE, SQUARE_SIDE);
        }
    }

    /**
     * Draw a queen for side PIECE at square S on G.
     */
    private void drawQueen(Graphics2D g, Square s, Piece piece) {
        g.drawImage(piece == WHITE ? _whiteQueen : _blackQueen,
                cx(s.col()) + 2, cy(s.row()) + 4, null);
    }

    /**
     * Handle a click on S.
     */
    private void click(Square s) {
        _moveSquares.add(s);
        if (_moveSquares.size() == 3) {
            String cmd = "";
            cmd += _moveSquares.get(0).toString() + "-";
            cmd += _moveSquares.get(1).toString() + "(";
            cmd += _moveSquares.get(2).toString() + ")";
            _commands.offer(cmd);
            _moveSquares.clear();
        }
        repaint();
    }

    /**
     * Handle mouse click event E.
     */
    private synchronized void mouseClicked(String unused, MouseEvent e) {
        int xpos = e.getX(), ypos = e.getY();
        int x = xpos / SQUARE_SIDE,
                y = (BOARD_SIDE - ypos) / SQUARE_SIDE;
        if (_acceptingMoves
                && x >= 0 && x < Board.SIZE && y >= 0 && y < Board.SIZE) {
            click(sq(x, y));
        }
    }

    /**
     * Revise the displayed board according to BOARD.
     */
    synchronized void update(Board board) {
        _board.copy(board);
        repaint();
    }

    /**
     * Turn on move collection iff COLLECTING, and clear any current
     * partial selection.   When move collection is off, ignore clicks on
     * the board.
     */
    void setMoveCollection(boolean collecting) {
        _acceptingMoves = collecting;
        repaint();
    }

    /**
     * Return x-pixel coordinate of the left corners of column X
     * relative to the upper-left corner of the board.
     */
    private int cx(int x) {
        return x * SQUARE_SIDE;
    }

    /**
     * Return y-pixel coordinate of the upper corners of row Y
     * relative to the upper-left corner of the board.
     */
    private int cy(int y) {
        return (Board.SIZE - y - 1) * SQUARE_SIDE;
    }

    /**
     * Return x-pixel coordinate of the left corner of S
     * relative to the upper-left corner of the board.
     */
    private int cx(Square s) {
        return cx(s.col());
    }

    /**
     * Return y-pixel coordinate of the upper corner of S
     * relative to the upper-left corner of the board.
     */
    private int cy(Square s) {
        return cy(s.row());
    }

    /**
     * Queue on which to post move commands (from mouse clicks).
     */
    private ArrayBlockingQueue<String> _commands;
    /**
     * Board being displayed.
     */
    private final Board _board = new Board();

    /**
     * Image of white queen.
     */
    private BufferedImage _whiteQueen;
    /**
     * Image of black queen.
     */
    private BufferedImage _blackQueen;

    /**
     * True iff accepting moves from user.
     */
    private boolean _acceptingMoves;

    /**
     * List of clicked squares.
     */
    private LinkedList<Square> _moveSquares;
}
