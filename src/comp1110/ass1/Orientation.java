package comp1110.ass1;

public enum Orientation {
    NORTH, EAST, SOUTH, WEST;

    /**
     * Return the single character associated with a `Orientation`, which is the first character of
     * the direction name, as an upper case character ('N', 'E', 'S', 'W')
     *
     * @return A char value equivalent to the `Orientation` enum
     */

    public char toChar() {
        char c = ' ';
        if (Orientation.NORTH.equals(this))
            c = 'N';
        if (Orientation.SOUTH.equals(this))
            c= 'S';
        if (Orientation.EAST.equals(this))
            c = 'E';
        if (Orientation.WEST.equals(this))
            c= 'W';
        return c;
    }
}
