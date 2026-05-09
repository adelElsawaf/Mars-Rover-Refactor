package domain.planet;

import domain.rover.model.Position;

public interface Planet {

    Position wrapAround(Position position);

    boolean hasObstacleAt(Position position);
}
