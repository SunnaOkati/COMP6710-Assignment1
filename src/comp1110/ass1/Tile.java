package comp1110.ass1;

import static comp1110.ass1.Orientation.NORTH;

public class Tile {

    private TileType tileType;         // Which tile type is it (a ... f)
    private Location location;         // The tile's current location on board
    private Orientation orientation;   // The tile's current orientation

    public Tile(String placement) {
        this.tileType = TileType.valueOf(Character.toString((placement.charAt(0) - 32)));
        this.orientation = placementToOrientation(placement);
        this.location = placementToLocation(placement);
    }

    public Location getLocation() {
        return location;
    }
    public Orientation getOrientation() {
        return orientation;
    }
    public TileType getTileType() { return tileType; }

    /**
     * Given a four-character tile placement string, decode the tile's orientation.
     *
     * You will need to read the description of the encoding in the class `Objective`.
     *
     * @param placement A string representing the placement of a tile on the game board
     *
     * @return A value of type `Orientation` corresponding to the tile's orientation on board
     */
    public static Orientation placementToOrientation(String placement) {
        // FIXME Task 3
        char orientation = placement.charAt(3);
        Orientation p = NORTH;
        switch (orientation){
            case 'N':
                p= Orientation.NORTH;
                break;
            case 'S':
                p= Orientation.SOUTH;
                break;
            case 'E':
                p= Orientation.EAST;
                break;
            case 'W':
                p= Orientation.WEST;
                break;
        }
        return p;
    }

    /**
     * Given a four-character tile placement string, decode the tile's location.
     *
     * You will need to read the description of the encoding in the class `Objective'
     *
     * @param placement A string representing the placement of a tile on the game board
     *
     * @return A value of type `Location` corresponding to the tile's location on the board
     */
    public static Location placementToLocation(String placement) {
        // FIXME Task 4
        int x = Character.getNumericValue(placement.charAt(1));
        int y = Character.getNumericValue(placement.charAt(2));
       return new Location(x, y);
    }
}
