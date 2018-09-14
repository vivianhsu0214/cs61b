package galaxy;

/**
 * An (X, Y)  position on a Galaxies puzzle board.  We require that
 * X, Y >= -1 (the -1 allows for a "ghost region" of cells around the
 * outside.)
 *
 * @author P. N. Hilfinger
 */
class Place {

    /**
     * The position (X0, Y0), where X0, Y0 >= -1.
     */
    private Place(int x0, int y0) {
        x = x0;
        y = y0;
        value = 0;
        boundary = false;
        center = false;
    }

    /**
     * Return the position (X, Y).  This is a factory method that
     * creates a new Place only if needed by caching those that are
     * created.
     */
    static Place pl(int x, int y) {
        assert x >= -1 && y >= -1;
        if (y + 1 >= _places.length || x + 1 >= _places.length) {
            int s;
            s = _places.length;
            while (s <= x + 1 || s <= y + 1) {
                s *= 2;
            }
            Place[][] newPlaces = new Place[s][s];
            for (int i = 0; i < _places.length; i += 1) {
                System.arraycopy(_places[i], 0, newPlaces[i], 0,
                        _places.length);
            }
            _places = newPlaces;
        }
        if (_places[x + 1][y + 1] == null) {
            _places[x + 1][y + 1] = new Place(x, y);
        }
        return _places[x + 1][y + 1];
    }

    /**
     * Return true iff this is a cell.
     */
    boolean isCell() {
        return x % 2 == 1 && y % 2 == 1;
    }

    /**
     * Return true iff this is a cell corner.
     */
    boolean isCorner() {
        return x % 2 == 0 && y % 2 == 0;
    }

    /**
     * Return true iff this is a cell edge.
     */
    boolean isEdge() {
        return x % 2 != y % 2;
    }

    /**
     * If I represent (x, y), return the Place (x + DX, y + DY), assuming
     * that is a valid Place.
     */
    Place move(int dx, int dy) {
        return pl(x + dx, y + dy);
    }

    /**
     * Return the Manhattan distance between OTHER and me.
     */
    int dist(Place other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    /**
     * Getters and setters.
     * @return
     */
    int getValue() {
        return value;
    }

    /**
     * Getters and setters.
     * @param v v
     */
    void setValue(int v) {
        value = v;
    }

    /**
     * Getters and setters.
     * @return
     */
    boolean getBoundary() {
        return boundary;
    }

    /**
     * Getters and setters.
     */
    void toggleBoundary() {
        boundary = !boundary;
    }

    /**
     * Getters and setters.
     */
    void setBoundary() {
        boundary = true;
    }

    /**
     * Getters and setters.
     */
    void resetBoundary() {
        boundary = false;
    }

    /**
     * Getters and setters.
     * @return
     */
    boolean getCenter() {
        return center;
    }

    /**
     * Getters and setters.
     */
    void setCenter() {
        center = true;
    }

    /**
     * Getters and setters.
     */
    void resetCenter() {
        center = false;
    }

    /**
     * Getters and setters.
     * @return
     */
    boolean isUnmarked() {
        return value == 0;
    }

    /**
     * Getters and setters.
     * @return
     */
    Model getOwner() {
        return owner;
    }

    /**
     * Getters and setters.
     * @param model model
     */
    void setOwner(Model model) {
        owner = model;
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Place)) {
            return false;
        }
        Place other = (Place) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return (x << 16) + y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    /**
     * Coordinates of this Place and Value.
     */
    protected final int x, y;
    /**
     * Marks of Places.
     */
    private int value;
    /**
     * Boundary booleans.
     */
    private boolean boundary;
    /**
     * Center booleans.
     */
    private boolean center;
    /**
     * Recording the static variable PLACE[][] 's owner model.
     */
    private Model owner;
    /**
     * Places already generated.
     */
    private static Place[][] _places = new Place[10][10];


}
