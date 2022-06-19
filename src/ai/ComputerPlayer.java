package ICS4UBoggle.src.ai;

import java.util.*;

import ICS4UBoggle.src.BoggleAlgorithms;

public class ComputerPlayer {
    Cell[][] cells;

    int rows; // y
    int cols; // x
    int minLength = 3;

    Stack<Cell> letterStack = new Stack<>();
    Dictionary dictionary = new Dictionary();
    HashSet<String> wordsFound;

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
     * @param boggleGrid The gameboard
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

    public ArrayList<int[]> start() {
        run();
        
        ArrayList<int[]> list = new ArrayList<>();

        for (Cell cell : letterStack) {
            list.add(new int[]{cell.x, cell.y});
        }

        return list;

    }
    /**
     * Run from here
     * @return
     */
    public Collection<Cell> run() {
        Cell startCell = getRandomCell();
        if (startCell == null) { // if all start cells have been used, return -1
            return null;
        } else {
            doesCellMakeWord(startCell);
            return letterStack;
        }
    }

    // DFS
    public boolean doesCellMakeWord(Cell cell) {
        letterStack.push(cell);
        
        String word = stackWord();
        if (BoggleAlgorithms.getIdxOfWord(dictionary.dictionary, word) != -1 && !wordsFound.contains(word) && word.length() > minLength) {
            wordsFound.add(word);
            return true;
        }
        return doesAdjacentCellsMakeWord(cell);
    }

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

    public Cell nextCell(Position pos) {
        if (pos.y >= rows || pos.y < 0
                || pos.x >= cols || pos.x < 0
                || letterStack.contains(cells[pos.y][pos.x])) {
            return null;
        }
        return cells[pos.y][pos.x];
    }

    public Cell getRandomCell() {
        if (isAllVisited()) { // if every cell has been tried, return null
            return null;
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

        return cells[randY][randX];
    }

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

    public String stackWord() {
        char[] chars = new char[letterStack.size()];

        for (int i = 0; i < letterStack.size(); i++) {
            chars[i] = letterStack.get(i).getLetter();
        }
        String word = String.valueOf(chars);

        return word;
    }

    public boolean isWordStart(String str) {
        for (String word : dictionary.dictionary) {
            if (word.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

}