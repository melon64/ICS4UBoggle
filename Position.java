public class Position {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position add(Position pos) {
        return new Position(pos.x + x, pos.y + y);
    }
}
