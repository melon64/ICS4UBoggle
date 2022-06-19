package ICS4UBoggle.src.ai;

public class Cell extends Position {
    private boolean isChecked;
    private char letter;

    public Cell(char letter, int x, int y) {
        super(x, y);
        this.letter = letter;
    }

    public void visited() {
        isChecked = true;
    }

    public boolean isVisited() {
        return isChecked;
    }

    public char getLetter() {
        return letter;
    }
}