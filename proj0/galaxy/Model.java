package galaxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Formatter;
import java.util.Set;
import java.util.HashSet;
import java.util.List;


import static java.util.Arrays.asList;
import static galaxy.Place.pl;

/**
 * The state of a Galaxies Puzzle.  Each cell, cell edge, and intersection of
 * edges has coordinates (x, y). For cells, x and y are positive and odd.
 * For intersections, x and y are even.  For horizontal edges, x is odd and
 * y is even.  For vertical edges, x is even and y is odd.  On a board
 * with w columns and h rows of cells, (0, 0) indicates the bottom left
 * corner of the board, and (2w, 2h) indicates the upper right corner.
 * If (x, y) are the coordinates of a cell, then (x-1, y) is its left edge,
 * (x+1, y) its right edge, (x, y-1) its bottom edge, and (x, y+1) its
 * top edge.  The four cells (x, y), (x+2, y), (x, y+2), and (x+2, y+2)
 * meet at intersection (x+1, y+1).  Cells contain nonnegative integer
 * values, or "marks". A cell containing 0 is said to be unmarked.
 *
 * @author Zhibo Fan
 */
class Model {

    /**
     * The default number of squares on a side of the board.
     */
    static final int DEFAULT_SIZE = 7;

    /**
     * Initializes an empty puzzle board of size DEFAULT_SIZE x DEFAULT_SIZE,
     * with a boundary around the periphery.
     */
    Model() {
        init(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    /**
     * Initializes an empty puzzle board of size COLS x ROWS, with a boundary
     * around the periphery.
     */
    Model(int cols, int rows) {
        init(cols, rows);
    }

    /**
     * Initializes a copy of MODEL.
     */
    Model(Model model) {
        copy(model);
    }

    /**
     * Backup arrays.
     * @param blCopy blcopy
     * @param clCopy clcopy
     * @param mlCopy mlcopy
     * @param bl bl
     * @param cl cl
     * @param ml ml
     */
    private void backup(List<List<Boolean>> blCopy, List<List<Boolean>> clCopy,
                        List<List<Integer>> mlCopy, List<List<Boolean>> bl,
                        List<List<Boolean>> cl, List<List<Integer>> ml) {
        for (int i = 0; i < bl.size(); i++) {
            List<Boolean> bline = new ArrayList<Boolean>();
            List<Boolean> cline = new ArrayList<Boolean>();
            List<Integer> mline = new ArrayList<Integer>();
            for (int j = 0; j < bl.get(i).size(); j++) {
                bline.add(bl.get(i).get(j));
                cline.add(cl.get(i).get(j));
                mline.add(ml.get(i).get(j));
            }
            blCopy.add(bline);
            clCopy.add(cline);
            mlCopy.add(mline);
        }
    }

    /**
     *  Set ghost cells for this model.
     */
    private void setGhost() {
        for (int i = -1; i <= 2 * w + 1; i++) {
            pl(i, -1).setBoundary();
            pl(i, 0).setBoundary();
            pl(i, 2 * h).setBoundary();
            pl(i, 2 * h + 1).setBoundary();
        }
        for (int i = -1; i <= 2 * h + 1; i++) {
            pl(-1, i).setBoundary();
            pl(0, i).setBoundary();
            pl(2 * w, i).setBoundary();
            pl(2 * w + 1, i).setBoundary();
        }
    }
    /**
     * Copies MODEL into me.
     */
    void copy(Model model) {
        this.w = model.getw();
        this.h = model.geth();
        List<List<Boolean>> blCopy = new ArrayList<List<Boolean>>();
        List<List<Boolean>> clCopy = new ArrayList<List<Boolean>>();
        List<List<Integer>> mlCopy = new ArrayList<List<Integer>>();
        backup(blCopy, clCopy, mlCopy,
                model.getBoundaryList(), model.getCenterList(),
                model.getMarkList());
        boundaryList = new ArrayList<List<Boolean>>();
        centerList = new ArrayList<List<Boolean>>();
        markList = new ArrayList<List<Integer>>();
        for (int c = 0; c <= 2 * w + 2; c++) {
            List<Boolean> boundaryLine = new ArrayList<Boolean>();
            List<Boolean> centerLine = new ArrayList<Boolean>();
            List<Integer> markLine = new ArrayList<Integer>();
            for (int r = 0; r <= 2 * h + 2; r++) {
                pl(c - 1, r - 1).resetBoundary();
                pl(c - 1, r - 1).resetCenter();
                pl(c - 1, r - 1).setValue(0);
                pl(c - 1, r - 1).setOwner(this);
                if ((r >= 0 && r <= 2 * h) || (c >= 0 && c <= 2 * w)) {
                    if (r == 0 || r == 2 * h || c == 0 || c == 2 * w) {
                        boundaryLine.add(true);
                    } else {
                        boundaryLine.add(false);
                    }
                    centerLine.add(false);
                    markLine.add(0);
                }
            }
            boundaryList.add(boundaryLine);
            centerList.add(centerLine);
            markList.add(markLine);
        }
        setGhost();
        for (int i = 0; i < 2 * w; i++) {
            for (int j = 0; j < 2 * h; j++) {
                boundaryList.get(i).set(j, blCopy.get(i).get(j));
                centerList.get(i).set(j, clCopy.get(i).get(j));
                markList.get(i).set(j, mlCopy.get(i).get(j));
                if (blCopy.get(i).get(j)) {
                    pl(i, j).setBoundary();
                }
                if (clCopy.get(i).get(j)) {
                    pl(i, j).setCenter();
                }
                pl(i, j).setValue(mlCopy.get(i).get(j));
            }
        }
    }

    /**
     * Sets the puzzle board size to COLS x ROWS, and clears it.
     */
    void init(int cols, int rows) {
        w = cols;
        h = rows;
        boundaryList = new ArrayList<List<Boolean>>();
        centerList = new ArrayList<List<Boolean>>();
        markList = new ArrayList<List<Integer>>();
        for (int c = 0; c <= 2 * cols + 2; c++) {
            List<Boolean> boundaryLine = new ArrayList<Boolean>();
            List<Boolean> centerLine = new ArrayList<Boolean>();
            List<Integer> markLine = new ArrayList<Integer>();
            for (int r = 0; r <= 2 * rows + 2; r++) {
                pl(c - 1, r - 1).resetBoundary();
                pl(c - 1, r - 1).resetCenter();
                pl(c - 1, r - 1).setValue(0);
                pl(c - 1, r - 1).setOwner(this);
                if ((r >= 0 && r <= 2 * rows) || (c >= 0 && c <= 2 * cols)) {
                    if (r == 0 || r == 2 * rows || c == 0 || c == 2 * cols) {
                        boundaryLine.add(true);
                    } else {
                        boundaryLine.add(false);
                    }
                    centerLine.add(false);
                    markLine.add(0);
                }
            }
            boundaryList.add(boundaryLine);
            centerList.add(centerLine);
            markList.add(markLine);
        }
        setGhost();
    }

    /**
     * Clears the board (removes centers, boundaries that are not on the
     * periphery, and marked cells) without resizing.
     */
    void clear() {
        init(cols(), rows());
    }

    /**
     * Returns the number of columns of cells in the board.
     */
    int cols() {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        return xlim() / 2;
    }

    /**
     * Returns the number of rows of cells in the board.
     */
    int rows() {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        return ylim() / 2;
    }

    /**
     * Returns the number of vertical edges and cells in a row.
     */
    int xlim() {
        return 2 * w + 1;
    }

    /**
     * Returns the number of horizontal edges and cells in a column.
     */
    int ylim() {
        return 2 * h + 1;
    }

    /**
     * Returns true iff (X, Y) is a valid cell.
     */
    boolean isCell(int x, int y) {
        return 0 <= x && x < xlim() && 0 <= y && y < ylim()
                && x % 2 == 1 && y % 2 == 1;
    }

    /**
     * Returns true iff P is a valid cell.
     */
    boolean isCell(Place p) {
        return isCell(p.x, p.y);
    }

    /**
     * Returns true iff (X, Y) is a valid edge.
     */
    boolean isEdge(int x, int y) {
        return 0 <= x && x < xlim() && 0 <= y && y < ylim() && x % 2 != y % 2;
    }

    /**
     * Returns true iff P is a valid edge.
     */
    boolean isEdge(Place p) {
        return isEdge(p.x, p.y);
    }

    /**
     * Returns true iff (X, Y) is a vertical edge.
     */
    boolean isVert(int x, int y) {
        return isEdge(x, y) && x % 2 == 0;
    }

    /**
     * Returns true iff P is a vertical edge.
     */
    boolean isVert(Place p) {
        return isVert(p.x, p.y);
    }

    /**
     * Returns true iff (X, Y) is a horizontal edge.
     */
    boolean isHoriz(int x, int y) {
        return isEdge(x, y) && y % 2 == 0;
    }

    /**
     * Returns true iff P is a horizontal edge.
     */
    boolean isHoriz(Place p) {
        return isHoriz(p.x, p.y);
    }

    /**
     * Returns true iff (X, Y) is a valid intersection.
     */
    boolean isIntersection(int x, int y) {
        return x % 2 == 0 && y % 2 == 0
                && x >= 0 && y >= 0 && x < xlim() && y < ylim();
    }

    /**
     * Returns true iff P is a valid intersection.
     */
    boolean isIntersection(Place p) {
        return isIntersection(p.x, p.y);
    }

    /**
     * Returns true iff (X, Y) is a center.
     */
    boolean isCenter(int x, int y) {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        return pl(x, y).getCenter();
    }

    /**
     * Returns true iff P is a center.
     */
    boolean isCenter(Place p) {
        return isCenter(p.x, p.y);
    }

    /**
     * Returns true iff (X, Y) is a boundary.
     */
    boolean isBoundary(int x, int y) {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        return pl(x, y).getBoundary();
    }

    /**
     * Returns true iff P is a boundary.
     */
    boolean isBoundary(Place p) {
        return isBoundary(p.x, p.y);
    }

    /**
     * Returns true iff the puzzle board is solved, given the centers and
     * boundaries that are currently on the board.
     */
    boolean solved() {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        int total;
        total = 0;
        if (centers() == null) {
            return false;
        }
        for (Place c : centers()) {
            HashSet<Place> r = findGalaxy(c);
            if (r == null) {
                return false;
            } else {
                total += r.size();
            }
        }
        return total == rows() * cols();
    }

    /**
     * Finds cells reachable from CELL and adds them to REGION.  Specifically,
     * it finds cells that are reachable using only vertical and horizontal
     * moves starting from CELL that do not cross any boundaries and
     * do not touch any cells that were initially in REGION. Requires
     * that CELL is a valid cell.
     */
    private void accreteRegion(Place cell, HashSet<Place> region) {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        assert isCell(cell);
        if (region.contains(cell)) {
            return;
        }
        region.add(cell);
        for (int i = 0; i < 4; i += 1) {
            int dx = (i % 2) * (2 * (i / 2) - 1),
                    dy = ((i + 1) % 2) * (2 * (i / 2) - 1);
            if (!pl(cell.x + dx, cell.y + dy).getBoundary()) {
                accreteRegion(cell.move(2 * dx, 2 * dy), region);
            }
        }
    }

    /**
     * Returns true iff REGION is a correctly formed galaxy. A correctly formed
     * galaxy has the following characteristics:
     * - is symmetric about CENTER,
     * - contains no interior boundaries, and
     * - contains no other centers.
     * Assumes that REGION is connected.
     */
    private boolean isGalaxy(Place center, HashSet<Place> region) {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        for (Place cell : region) {
            if (!region.contains(opposing(center, cell))) {
                return false;
            }
            for (int i = 0; i < 4; i += 1) {
                int dx = (i % 2) * (2 * (i / 2) - 1),
                        dy = ((i + 1) % 2) * (2 * (i / 2) - 1);
                Place boundary = cell.move(dx, dy),
                        nextCell = cell.move(2 * dx, 2 * dy);

                if ((boundary.getCenter() && !boundary.equals(center))
                        || (boundary.getBoundary() && region.contains(nextCell))
                        || (!boundary.getBoundary() && nextCell.getCenter())
                        && !nextCell.equals(center)) {
                    return false;
                }
            }
            for (int i = 0; i < 4; i += 1) {
                int dx = 2 * (i / 2) - 1,
                        dy = 2 * (i % 2) - 1;
                Place intersection = cell.move(dx, dy);
                if (intersection.getCenter() && !intersection.equals(center)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Returns the galaxy containing CENTER that has the following
     * characteristics:
     * - encloses CENTER completely,
     * - is symmetric about CENTER,
     * - is connected,
     * - contains no stray boundary edges, and
     * - contains no other centers aside from CENTER.
     * Otherwise, returns null. Requires that CENTER is not on the
     * periphery.
     */
    HashSet<Place> findGalaxy(Place center) {
        HashSet<Place> galaxy = new HashSet<>();

        if (center.isCell()) {
            accreteRegion(center, galaxy);
        } else if (center.isCorner()) {
            accreteRegion(center.move(1, 1), galaxy);
        } else if (center.x % 2 == 0) {
            accreteRegion(center.move(1, 0), galaxy);
        } else {
            accreteRegion(center.move(0, 1), galaxy);
        }
        if (isGalaxy(center, galaxy)) {
            return galaxy;
        } else {
            return null;
        }
    }

    /**
     * Returns the largest, unmarked region around CENTER with the
     * following characteristics:
     * - contains all cells touching CENTER,
     * - consists only of unmarked cells,
     * - is symmetric about CENTER, and
     * - is contiguous.
     * The method ignores boundaries and other centers on the current board.
     * If there is no such region, returns the empty set.
     */
    Set<Place> maxUnmarkedRegion(Place center) {
        HashSet<Place> region = new HashSet<>();
        region.addAll(unmarkedContaining(center));
        ArrayList<Place> regionList = new ArrayList<Place>(region);
        markAll(region, 1);

        while (unmarkedSymAdjacent(center, regionList).size() != 0) {
            region.addAll(unmarkedSymAdjacent(center, regionList));
            markAll(region, 1);
            regionList = new ArrayList<Place>(region);
        }
        markAll(region, 0);
        return region;
    }

    /**
     * Marks all properly formed galaxies with value V. Unmarks all cells that
     * are not contained in any of these galaxies. Requires that V is greater
     * than or equal to 0.
     */
    void markGalaxies(int v) {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        assert v >= 0;
        markAll(0);
        for (Place c : centers()) {
            HashSet<Place> region = findGalaxy(c);
            if (region != null) {
                markAll(region, v);
            }
        }
    }

    /**
     * Toggles the presence of a boundary at the edge (X, Y). That is, negates
     * the value of isBoundary(X, Y) (from true to false or vice-versa).
     * Requires that (X, Y) is an edge.
     */
    void toggleBoundary(int x, int y) {

        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        if (!isEdge(x, y)) {
            return;
        }
        pl(x, y).toggleBoundary();
        boolean isBound = boundaryList.get(x).get(y);
        boundaryList.get(x).set(y, !isBound);
    }

    /**
     * Places a center at (X, Y). Requires that X and Y are within bounds of
     * the board.
     */
    void placeCenter(int x, int y) {
        placeCenter(pl(x, y));
    }

    /**
     * Places center at P.
     */
    void placeCenter(Place p) {

        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        if (0 <= p.x && p.x <= 2 * w && 0 <= p.y && p.y <= 2 * h) {
            pl(p.x, p.y).setCenter();
            centerList.get(p.x).set(p.y, true);
        }
    }

    /**
     * Returns the current mark on cell (X, Y), or -1 if (X, Y) is not a valid
     * cell address.
     */
    int mark(int x, int y) {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        if (!isCell(x, y)) {
            return -1;
        }
        return pl(x, y).getValue();
    }

    /**
     * Returns the current mark on cell P, or -1 if P is not a valid cell
     * address.
     */
    int mark(Place p) {
        return mark(p.x, p.y);
    }

    /**
     * Marks the cell at (X, Y) with value V. Requires that V must be greater
     * than or equal to 0, and that (X, Y) is a valid cell address.
     */
    void mark(int x, int y, int v) {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        if (!isCell(x, y)) {
            throw new IllegalArgumentException("bad cell coordinates");
        }
        if (v < 0) {
            throw new IllegalArgumentException("bad mark value");
        }

        pl(x, y).setValue(v);
        markList.get(x).set(y, v);
    }

    /**
     * Marks the cell at P with value V. Requires that V must be greater
     * than or equal to 0, and that P is a valid cell address.
     */
    void mark(Place p, int v) {
        mark(p.x, p.y, v);
    }

    /**
     * Sets the marks of all cells in CELLS to V. Requires that V must be
     * greater than or equal to 0.
     */
    void markAll(Collection<Place> cells, int v) {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        assert v >= 0;
        for (Place c : cells) {
            mark(c, v);
        }
    }

    /**
     * Sets the marks of all cells to V. Requires that V must be greater than
     * or equal to 0.
     */
    void markAll(int v) {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        assert v >= 0;

        for (int c = 1; c < 2 * w; c++) {
            for (int r = 1; r < 2 * h; r++) {
                if (isCell(c, r)) {
                    mark(pl(c, r), v);
                }
            }
        }
    }

    /**
     * Returns the position of the cell that is opposite P using P0 as the
     * center, or null if that is not a valid cell address.
     */
    Place opposing(Place p0, Place p) {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }

        int dx = p0.x - p.x;
        int dy = p0.y - p.y;
        int xt = p0.x + dx;
        int yt = p0.y + dy;
        if (0 <= xt && xt <= 2 * w && 0 <= yt
                && yt <= 2 * h && isCell(xt, yt)) {
            return pl(xt, yt);
        } else {
            return null;
        }
    }

    /**
     * Returns a list of all cells "containing" PLACE if all of the cells are
     * unmarked. A cell, c, "contains" PLACE if
     * - c is PLACE itself,
     * - PLACE is a corner of c, or
     * - PLACE is an edge of c.
     * Otherwise, returns an empty list.
     */
    List<Place> unmarkedContaining(Place place) {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        if (isCell(place)) {
            if (place.isUnmarked()) {
                return asList(place);
            }
        } else if (isVert(place)) {
            if (place.move(-1, 0).isUnmarked()
                    && place.move(1, 0).isUnmarked()) {
                return asList(place.move(-1, 0), place.move(1, 0));
            }
        } else if (isHoriz(place)) {
            if (place.move(0, -1).isUnmarked()
                    && place.move(0, 1).isUnmarked()) {
                return asList(place.move(0, -1), place.move(0, 1));
            }
        } else {
            for (int i = 0; i < 4; i += 1) {
                int dx = 2 * (i / 2) - 1,
                        dy = 2 * (i % 2) - 1;
                if (!place.move(dx, dy).isUnmarked()) {
                    return Collections.emptyList();
                }
            }
            return asList(place.move(1, 1), place.move(1, -1),
                    place.move(-1, -1), place.move(-1, 1));
        }
        return Collections.emptyList();
    }

    /**
     * Returns a list of all cells, c, such that:
     * - c is unmarked,
     * - The opposite cell from c relative to CENTER exists and
     * is unmarked, and
     * - c is vertically or horizontally adjacent to a cell in REGION.
     * CENTER and all cells in REGION must be valid cell positions.
     * Each cell appears at most once in the resulting list.
     */
    List<Place> unmarkedSymAdjacent(Place center, List<Place> region) {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        ArrayList<Place> result = new ArrayList<>();
        for (Place r : region) {
            assert isCell(r);
            for (int i = 0; i < 4; i += 1) {
                int dx = (i % 2) * (2 * (i / 2) - 1),
                        dy = ((i + 1) % 2) * (2 * (i / 2) - 1);
                Place p = r.move(2 * dx, 2 * dy);
                Place opp = opposing(center, p);
                if (p.x == -1 || p.x == 2 * w + 1
                        || p.y == -1 || p.y == 2 * h + 1) {
                    continue;
                }
                if (opp == null) {
                    continue;
                }
                if (p.isUnmarked() && opp.isUnmarked()) {
                    result.add(p);
                }
            }

        }
        return result;
    }

    /**
     * Returns an unmodifiable view of the list of all centers.
     */
    List<Place> centers() {

        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        List<Place> centers = new ArrayList<Place>();
        for (int c = 1; c < 2 * w; c++) {
            for (int r = 1; r < 2 * h; r++) {
                if (pl(c, r).getCenter()) {
                    centers.add(pl(c, r));
                }
            }
        }
        if (centers.isEmpty()) {
            return centers;
        }
        return centers;
    }

    @Override
    public String toString() {
        if (pl(1, 1).getOwner() != this) {
            copy(this);
        }
        Formatter out = new Formatter();
        int w1 = xlim(), h1 = ylim();
        for (int y = h1 - 1; y >= 0; y -= 1) {
            for (int x = 0; x < w1; x += 1) {
                boolean cent = isCenter(x, y);
                boolean bound = isBoundary(x, y);
                if (isIntersection(x, y)) {
                    out.format(cent ? "o" : " ");
                } else if (isCell(x, y)) {
                    if (cent) {
                        out.format(mark(x, y) > 0 ? "O" : "o");
                    } else {
                        out.format(mark(x, y) > 0 ? "*" : " ");
                    }
                } else if (y % 2 == 0) {
                    if (cent) {
                        out.format(bound ? "O" : "o");
                    } else {
                        out.format(bound ? "=" : "-");
                    }
                } else if (cent) {
                    out.format(bound ? "O" : "o");
                } else {
                    out.format(bound ? "I" : "|");
                }
            }
            out.format("%n");
        }
        return out.toString();
    }

    /**
     * Getters and setters.
     *
     * @return w w
     */
    int getw() {
        return w;
    }

    /**
     * Getters and setters.
     *
     * @return h h
     */
    int geth() {
        return h;
    }

    /**
     * Getters and setters.
     *
     * @return arr boundaryList
     */
    List<List<Boolean>> getBoundaryList() {
        return boundaryList;
    }

    /**
     * Getters and setters.
     *
     * @return arr centerList
     */
    List<List<Boolean>> getCenterList() {
        return centerList;
    }

    /**
     * Getters and setters.
     *
     * @return arr markList
     */
    List<List<Integer>> getMarkList() {
        return markList;
    }


    /**
     * Width of the model.
     */
    private int w;
    /**
     * Height of the model.
     */
    private int h;
    /**
     * Boundary Array.
     */
    private List<List<Boolean>> boundaryList;
    /**
     * Center Array.
     */
    private List<List<Boolean>> centerList;
    /**
     * Mark Array.
     */
    private List<List<Integer>> markList;

}
