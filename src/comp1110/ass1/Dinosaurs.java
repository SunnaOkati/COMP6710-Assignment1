package comp1110.ass1;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static comp1110.ass1.Orientation.NORTH;
import static comp1110.ass1.Orientation.SOUTH;
import static comp1110.ass1.State.*;

public class Dinosaurs {

    /* The objective represents the problem to be solved in this instance of the game. */
    private Objective objective;

    /*
     * States at each of the board's 20 locations (corners), initialized
     * to represent the empty board.  EMPTY states may be overwritten
     * by non-empty states (RED, GREEN), when a tile in that state is
     * placed at the same corner.
     *
     * Notice that this is initialized with all perimeter states, and
     * all inner locations that must be water are initialized as water
     * and all inner locations that must be land are initialized as
     * empty.
     */
    private State[][] boardstates = {
            {EMPTY, WATER, EMPTY, WATER, EMPTY},
            {WATER, EMPTY, WATER, EMPTY, WATER},
            {EMPTY, WATER, EMPTY, WATER, EMPTY},
            {WATER, EMPTY, WATER, EMPTY, WATER}
    };

    /*
     * Tiles occupying the board.   Indices refer to the square
     * to the lower-right of the given location (remember, locations
     * refer to corners, not to tiles).   So (0,0) refers to the
     * square in the top-left corner, (3,0) refers to the sqaure
     * in the top-right corner, (0,2) refers to the square in the
     * bottom-left corner, and (3,2) refers to the square in the
     * bottom-right corner.
     *
     * Each entry in the array points to the Tile instance occupying
     * the square, or null if the square is empty.   Since tiles
     * are two squares big, each placed tile should have two array
     * entries referring to it.
     *
     * Since this data structure only reflects placed tiles, it is
     * initially empty (all entries are null).
     */
    private Tile[][] tiles = new Tile[3][4];

    /**
     * Construct a game with a given objective
     *
     * @param objective The objective of this game.
     */
    public Dinosaurs(Objective objective) {
        this.objective = objective;
    }

    /**
     * Construct a game for a given level of difficulty.
     * This chooses a new objective and creates a new instance of
     * the game at the given level of difficulty.
     *
     * @param difficulty The difficulty of the game.
     */
    public Dinosaurs(int difficulty) {
        this(Objective.newObjective(difficulty));
    }

    public Objective getObjective() {
        return objective;
    }

    /**
     * @param boardState A string consisting of 4*N characters, representing
     * initial tile placements (initial game state).
     */
    public void initializeBoardState(String boardState) {
        for (int i = 0; i < boardState.length()/4; i++) {
            String placement = boardState.substring(i * 4, ((i + 1) * 4));
            addTileToBoard(placement);
        }
    }


    /**
     * Print out the board state.   May be useful for debugging.
     */
    private void printBoardState() {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                System.out.print((boardstates[r][c] == null ? ' ' : boardstates[r][c].name().charAt(0)) + " ");
            }
            System.out.println();
        }
    }


    /**
     * Print out tile state.   May be useful for debugging.
     */
    private void printTileState() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                System.out.print(tiles[r][c] == null ? '.' : tiles[r][c].getTileType().name().charAt(0)+"");
            }
            System.out.println();
        }
    }

    /**
     * Check whether a tile placement fits inside the game board.
     *
     * @param placement A String representing a tile placement.
     * @return  True if the tile is completely within the board, and false otherwise.
     */
    public static boolean isPlacementOnBoard(String placement) {
        // FIXME Task 5
        int x = Character.getNumericValue(placement.charAt(1));
        int y = Character.getNumericValue(placement.charAt(2));
        char orientation = placement.charAt(3);
        int row, column = 0;

        if (orientation == 'E' || orientation == 'W'){
            row = 2;
            column = 1;
        }
        else
        {
            row = 1;
            column = 2;
        }

        if ((x >=0) && ((x + row) < 5) && (y >=0) && ((y + column) < 4)){
            return true;
        }

        return false;
    }


    /**
     * Add a new tile placement to the board state, updating
     * all relevant data structures accordingly.  If you add
     * additional data structures, you will need to update
     * this.
     *
     * @param placement The placement to add.
     */
    private void addTileToBoard(String placement) {
        /* create the tile, and figure out its location and orientation */
        Tile tile = new Tile(placement);

        /* update the tile data structure for the two squares that compose this tile */
        updateTiles(tile);

        /* update the states for each of the six points on the tile */
        updateBoardStates(tile);
    }


    /**
     * Update the tile data structure with a new tile placement.
     *
     * Each entry in the data structure corresponds to a square, and
     * each tile is composed of two squares.   So each time a tile
     * is added, two entries in the data structure need to be updated
     * to point to the new tile.
     *
     * Squares that are covered by a tile will have their data structure
     * entry point to the covering tile.
     *
     * Squares that are not covered by a tile will point to null.
     *
     * @param tile The tile being placed
     */
    private void updateTiles(Tile tile) {
        Location location = tile.getLocation();
        Orientation orientation = tile.getOrientation();
        tiles[location.getY()][location.getX()] = tile;
        if (orientation == NORTH || orientation == SOUTH)
            tiles[location.getY()+1][location.getX()] = tile;  // vertical orientation
        else
            tiles[location.getY()][location.getX()+1] = tile;  // horizontal orientation
    }


    /**
     * Check whether a proposed tile placement overlaps with any previous
     * placements.
     *
     * You will need to use both the placement about to be made, and
     * the existing board state (specifically, the tiles data structure),
     * which is kept up to date when addTileToBoard is called.
     *
     * @param placement A string consisting of 4 characters,
     *                  representing a tile placement
     * @return  False if the proposed tile placement does not overlap with
     * the already placed tiles, and True if there is any overlap.
     */
    public boolean doesPlacementOverlap(String placement) {
        // FIXME Task 6
        int x = Character.getNumericValue(placement.charAt(1));
        int y = Character.getNumericValue(placement.charAt(2));
        char orientation = placement.charAt(3);
        int row = 0, column = 0;

        if (orientation == 'E' || orientation == 'W'){
            row = 1;
        }
        else
        {
            column = 1;
        }

        if ((tiles[y][x] != null) || (tiles[y + column][x + row]!= null)){
            return true;
        }
        return false;
    }


    /**
     * Update the boardstates data structure due to a valid (correct) new
     * tile placement.
     *
     * Each point in the boardstates data structure corresponds to one of
     * the twenty board locations, each of which is a place where the corner
     * of a tile may be placed (see the game description for a diagram, and
     * more information).
     *
     * Each entry in the boardstates data structure is a State, which may
     * be null (unassigned), WATER, EMPTY, GREEN, or RED.
     *
     * When a valid tile placement is made, some locations may change.   For
     * example null locations may become non-null due to a tile placement,
     * and some EMPTY locations may become GREEN or RED.
     *
     * Notice that when a tile is placed, six locations will change, since
     * a tile covers two squares, it will affect two locations at each end
     * and two locations in its middle.  The affected locations will depend
     * on the location of the tile (its top-left corner), and the tile's
     * orientation.
     *
     * @param tile The tile being placed
     */
    private void updateBoardStates(Tile tile) {
        // FIXME Task 7 (part I)
    }

    /**
     * Given an island location, return its current state.
     *
     * The current state of an island is the state of the dinosaur(s)
     * which are directly or indirectly connected to the island.
     *
     * For example, after applying the placement string "c00N",
     * the islands at location(0,0) and location(1,1) become RED,
     * as they are connected to a RED dinosaur.
     *
     * When an island is not connected to any dinosaurs,
     * its state is EMPTY.
     *
     * @param location  A location on the game board.
     * @return          An object of type `enum State`, representing
     * the given location.
     */
    public State getLocationState(Location location) {
        // FIXME Task 7 (part II)
        return null;
    }

    /**
     * Check whether the locations of land and water on a tile placement
     * are consistent with its surrounding tiles or board, given the current
     * state of the board due to previous placements.
     *
     * Important: The test for this method is not concerned with dinosaur color.
     * Thus it is simply a matter of ensuring that water meets water and land
     * meets land (whether or not the land is occupied by a dinosaur).
     *
     * For example, the placement string "a00N" is not consistent
     * since it puts the water at top-left of tile 'a', next to the island
     * at the top-left of the game board.
     *
     * You will need to use both the placement about to be made, and
     * the existing board state which is kept up to date when
     * addTileToBoard is called.
     *
     * @param placement A string consisting of 4 characters,
     *                  representing a tile placement
     * @return True if the placement is consistent with the board and placed
     * tiles, and False if it is inconsistent.
     */
    public boolean isPlacementConsistent(String placement) {
        // FIXME Task 9
        return false;
    }

    /**
     * Check whether a tile placement would cause a collision between
     * green and red dinosaurs.
     *
     * @param placement A string consisting of 4 characters,
     *                  representing a tile placement
     * @return True if the placement would cause a collision
     * between red and green dinosaurs.
     */
    public boolean isPlacementDangerous(String placement) {
        // FIXME Task 10
        return false;
    }

    /**
     * Check whether the given tile placement violates the game objective,
     * specifically:
     *
     * 1 - All required island connections are either connected or not occupied,
     *   and
     * 2 - All other pairs of islands are either disconnected or not occupied
     *
     * @param placement A string consisting of 4 characters,
     *                  representing a tile placement
     * @return True if the placement violates the game objective,
     * and false otherwise.
     */
    public boolean violatesObjective(String placement) {
        // FIXME Task 11
        return false;
    }

    /**
     * Given a target location, find the set of actions which:
     * 1 - occupy the target location
     * 2 - satisfy all of the game requirements(e.g. objectives)
     *
     * Notice that this question is an advanced question and is entirely
     * optional.   You will need to use the HashSet data type, which is
     * not covered until lecture unit J14.
     *
     * @param targetLoc A location (x,y) on the game board.
     * @return A set of strings, each representing a tile placement
     */
    public Set<String> findCandidatePlacements(Location targetLoc) {
        // FIXME Task 12
        return new HashSet<>();
    }

    /**
     * Find the solutions to the game (the current Dinosaurs object).
     *
     * Notice that this question is an advanced question and is entirely
     * optional.   You will need to use advanced data types and will
     * need to understand how to perform a search, most likely using
     * recursion, which is not covered until lecture unit C1.
     *
     * @return A set of strings, each representing a placement of all tiles,
     * which satisfies all of the game objectives.
     */
    public Set<String> getSolutions() {
        // FIXME Task 13
        return new LinkedHashSet<>();
    }
}
