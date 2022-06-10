package ICS4UBoggle.src.computer_player;

public class ComputerPlayer {
    Cell[][] cells;
    int rows; // y
    int cols; // x
    boolean[][] visited;
    String currentLetters;
    Dictionary dictionary = new Dictionary();
    int totalPoints;
    Position[] deltaPositions = {
            new Position(0, -1),
            new Position(1, 0),
            new Position(0, 1),
            new Position(-1, 0)
    };

    public ComputerPlayer(char[][] boggleGrid) {
        rows = boggleGrid.length;
        cols = boggleGrid[0].length;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                cells[y][x] = new Cell(boggleGrid[y][x], x, y);
            }
        }
    }

    public int start() {
        totalPoints = 0;
        Cell startCell = getRandomLoc();
        checkCell(startCell);
        return totalPoints;
    }

    public boolean checkCell(Cell cell) {
        String oldLetters = currentLetters;
        String newLetter = cell.getLetter() + "";
        currentLetters = oldLetters.concat(newLetter);

        if (dictionary.doesContainWord(currentLetters)) {
            totalPoints = currentLetters.length();
        }

        return checkNextCellAndTraverse(cell);
    }

    public boolean checkNextCellAndTraverse(Cell cell) {
        for (Position pos : deltaPositions) {
            Position nextPos = cell.add(pos);
            Cell nextCell = nextCell(nextPos);

            if (nextCell != null && checkCell(nextCell)) {
                return true;
            }
        }

        return false;
    }

    public Cell nextCell(Position pos) {
        if (pos.y >= rows || pos.y < 0
                || pos.x >= cols || pos.x < 0) {
            return null;

        } else if (!dictionary.doesContainWord(currentLetters.concat(cells[pos.y][pos.x].getLetter() + ""))) {
            return null;
        }

        return cells[pos.y][pos.x];
    }

    public Cell getRandomLoc() {
        if (isAllChecked()) { // if every cell has been tried, return null
            return null;
        }

        int randX;
        int randY;

        do {
            randX = (int) Math.floor(Math.random() * (cells[0].length) + 1);
            randY = (int) Math.floor(Math.random() * (cells.length) + 1);
        } while (visited[randY][randX]);

        return cells[randY][randX];
    }

    public boolean isAllChecked() {
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[0].length; x++) {
                if (visited[y][x] == false) {
                    return false;
                }
            }
        }

        return true;
    }

}