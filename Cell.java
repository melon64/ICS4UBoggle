public class Cell extends Position {
    private boolean isChecked;
    private char letter;

    public Cell(char letter, int x, int y) {
        super(x, y);
        this.letter = letter;
    }

    public void checked() {
        isChecked = true;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public char getLetter() {
        return letter;
    }
}