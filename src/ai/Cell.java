package ICS4UBoggle.src.ai;

/**
 * Names: Adarsh P, Larris X, Felix X, and Hubert X
 * Date: June 15, 2022
 * Description: A program that defines a cell object
 */

public class Cell extends Position {
    private boolean isChecked;
    private char letter;

    public Cell(char letter, int x, int y) {
        super(x, y);
        this.letter = letter;
    }

    /**
     * Toggles the visited state of the cell
     */
    public void setVisited() {
        isChecked = !isChecked;
    }

    /**
     * Gets the visited state of the cell 
     * 
     * @return The boolean isChecked
     */
    public boolean isVisited() {
        return isChecked;
    }

    /**
     * Gets the cell's letter 
     * 
     * @return The char letter assiociated with the cell
     */
    public char getLetter() {
        return letter;
    }
}