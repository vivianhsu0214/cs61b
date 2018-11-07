package amazons;

/** A Player that takes input as text commands from the standard input.
 *  @author Zhibo Fan
 */
class TextPlayer extends Player {

    /** A new TextPlayer with no piece or controller (intended to produce
     *  a template). */
    TextPlayer() {
        this(null, null);
    }

    /** A new TextPlayer playing PIECE under control of CONTROLLER. */
    private TextPlayer(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new TextPlayer(piece, controller);
    }

    @Override
    String myMove() {
        while (true) {
            String line = _controller.readLine();
            if (line != null) {
                line = line.toLowerCase();
            }
            if (line == null) {
                return "quit";
            } else if (line.trim().charAt(0) == '#') {
                continue;
            } else if (line.equals("new") || line.equals("dump")
                    || line.equals("quit")
                    || line.matches("seed\\s+\\(\\d+\\)$")
                    || line.equals("undo")
                    || line.matches("manual\\s+(black|white)$")
                    || line.matches("auto\\s+(black|white)$")
                    || line.matches(("seed\\s+(\\d+)"))
                    || _controller.board().isLegal(Move.mv(line))) {
                return line;
            } else if (!_controller.board().isLegal(Move.mv(line))) {
                System.out.println(line);
                _controller.reportError("Invalid move. "
                                        + "Please try again.");
                continue;

            } else {
                return null;
            }
        }
    }
}
