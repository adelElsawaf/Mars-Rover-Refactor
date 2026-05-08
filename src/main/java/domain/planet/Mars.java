package domain.planet;

import domain.rover.model.Position;

public class Mars implements Planet {

    private final int width;
    private final int height;

    public Mars(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Position wrapAround(Position position) {
        return new Position(
                Math.floorMod(position.x(), width),
                Math.floorMod(position.y(), height)
        );
    }
}
