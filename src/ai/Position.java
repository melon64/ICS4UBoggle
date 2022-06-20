package ICS4UBoggle.src.ai;

/**
 * Names: Adarsh P, Larris X, Felix X, and Hubert X
 * Date: June 15, 2022
 * Description: A program that defines a position object
 */

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This method returns the sum of two coordiantes
     * 
     * @param pos   Current position
     * @return      New position
     */
    public Position add(Position pos) {
        return new Position(pos.x + x, pos.y + y);
    }
}
