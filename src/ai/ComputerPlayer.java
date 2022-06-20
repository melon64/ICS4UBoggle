package ICS4UBoggle.src.ai;

/**
 * Names: Adarsh P, Larris X, Felix X, and Hubert X
 * Date: June 15, 2022
 * Description: A program that contains the AI/Computer Player logic
 */

import java.util.*;
import ICS4UBoggle.src.BoggleAlgorithms;

public class ComputerPlayer {
    private Cell[][] cells;

    private int rows; // y
    private int cols; // x
    private int minLength = 3;

    private ArrayList<String> dictionary = BoggleAlgorithms.getDictionaryFromFile();
    private Stack<Cell> letterStack = new Stack<>();
    private HashSet<String> wordsFound;

    Position[] deltaPositions = {
            new Position(0, -1), // N
            new Position(1, -1), // NE
            new Position(1, 0), // E
            new Position(1, 1), // SE
            new Position(0, 1), // S
            new Position(-1, 1), // SW
            new Position(-1, 0), // W
            new Position(-1, -1) // NW
    };

    /**
     * @param boggleGrid The letters that are currently on the board
     * @param usedWords The list of already guessed words
     * @param minLength The minimum length required for guessed words
     */
    public ComputerPlayer(char[][] boggleGrid, ArrayList<String> usedWords, int minLength) {
        rows = boggleGrid.length;
        cols = boggleGrid[0].length;

        cells = new Cell[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                cells[y][x] = new Cell(Character.toLowerCase(boggleGrid[y][x]), x, y);
            }
        }

        this.minLength = minLength; 
        wordsFound = new HashSet<>(usedWords);
    }

    /**
     * Gets the computer player's word path through coordinates
     * 
     * @return A list of coordinates that contains the path to form the word
     */
    public ArrayList<int[]> getComputerWordPath() {
        getComputerWord();
        
        ArrayList<int[]> list = new ArrayList<>();
        String word = "";
        
        for (Cell cell : letterStack) {
            list.add(new int[]{cell.x, cell.y});
            word = word += cell.getLetter();
        }

        return list;
    }

    /**
     * Gets the computer player's word formed by cells
     * 
     * @return A collection of letters guessed and their coordinates
     */
    public Collection<Cell> getComputerWord() {
        Cell startCell = getRandomCell();
        letterStack.clear();
        if (startCell == null) { // if all start cells have been used, return -1
            return null;
        } else {
            doesCellMakeWord(startCell);
            return letterStack;
        }
    }

    /**
     * This method checks if a word can be formed at a cell
     * 
     * @param cell Current cell
     * @return A boolean for if a word can be formed starting at the word
     */
    public boolean doesCellMakeWord(Cell cell) {
        letterStack.push(cell);
        
        String word = stackWord();
        if (BoggleAlgorithms.getIdxOfWord(dictionary, word) != -1 && !wordsFound.contains(word) && word.length() > minLength) {
            wordsFound.add(word);
            return true;
        }
        return doesAdjacentCellsMakeWord(cell);
    }

    /**
     * This method iterates through adjacent cells to check if the word can be extended
     * 
     * @param cell Current cell
     * @return A boolean for if word is valid
     */
    public boolean doesAdjacentCellsMakeWord(Cell cell) {
        if (isWordStart(stackWord())) {
            for (Position pos : deltaPositions) {
                Position nextPos = cell.add(pos);
                Cell nextCell = nextCell(nextPos);

                if (nextCell != null && doesCellMakeWord(nextCell)) {
                    return true;
                }
            }
        }
        letterStack.pop();
        return false;
    }

    /**
     * This method gets the next traversable cell
     * 
     * @param pos Current position
     * @return A cell at the new position
     */
    public Cell nextCell(Position pos) {
        if (pos.y >= rows || pos.y < 0
                || pos.x >= cols || pos.x < 0
                || letterStack.contains(cells[pos.y][pos.x])) {
            return null;
        }
        return cells[pos.y][pos.x];
    }

    /**
     * Gets a random cell that has not been visited
     *
     * @return An unvisited cell
     */
    public Cell getRandomCell() {
        if (isAllVisited()) { // if every cell has been tried, reset the visited status
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[0].length; j++) {
                    cells[i][j].setVisited();
                }
            }
        }

        int randX;
        int randY;

        int max = cells.length-1;
        int min = 0;
        int range = max - min + 1;

        do { // get a random unvisted cell
            randX = (int) (Math.random() * range) + min;
            randY = (int) (Math.random() * range) + min;
        } while (cells[randY][randX].isVisited());

        cells[randY][randX].setVisited();
        return cells[randY][randX];
    }

    /**
     * Checks if all cells have been visited
     * 
     * @return A boolean representing if all cells have been visited
     */
    public boolean isAllVisited() {
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[0].length; x++) {
                if (cells[y][x].isVisited() == false) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Creates a string from the selected characters
     * 
     * @return The word formed from the characters
     */
    public String stackWord() {
        char[] chars = new char[letterStack.size()];

        for (int i = 0; i < letterStack.size(); i++) {
            chars[i] = letterStack.get(i).getLetter();
        }
        String word = String.valueOf(chars);

        return word;
    }

    /**
     * Checks if the current word exists as a prefix in the dictionary
     * 
     * @param currWord The current word to check for
     * @return A boolean representing if the current word can form a word in the dictionary
     */
    public boolean isWordStart(String currWord) {
        for (String word : dictionary) {
            if (word.startsWith(currWord)) {
                return true;
            }
        }
        return false;
    }

}