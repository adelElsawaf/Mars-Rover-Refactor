package domain.planet;

import domain.rover.model.Position;

import java.util.Collections;
import java.util.Set;

public class Mars implements Planet {

    private final int width;
    private final int height;
    private final Set<Position> obstacles;

    public Mars(int width, int height) {
        this(width, height, Collections.emptySet());
    }

    public Mars(int width, int height, Set<Position> obstacles) {
        this.width = width;
        this.height = height;
        this.obstacles = obstacles;
    }

    @Override
    public Position wrapAround(Position position) {
        return new Position(
                Math.floorMod(position.x(), width),
                Math.floorMod(position.y(), height)
        );
    }

    @Override
    public boolean hasObstacleAt(Position position) {
        return obstacles.contains(position);
    }
}
