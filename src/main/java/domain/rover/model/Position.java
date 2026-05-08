package domain.rover.model;

public record Position(int x, int y) {

    public Position movedBy(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }
}
