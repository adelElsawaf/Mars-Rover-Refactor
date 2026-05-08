package domain.rover;

import domain.rover.model.Direction;
import domain.rover.model.Position;

public class Rover {

    private static final int DEFAULT_STEP_SIZE = 1;

    private Position position;
    private Direction direction;

    public Rover(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public void moveForward() {
        move(DEFAULT_STEP_SIZE);
    }

    public void moveBackward() {
        move(-DEFAULT_STEP_SIZE);
    }

    public void turnLeft() {
        direction = direction.left();
    }

    public void turnRight() {
        direction = direction.right();
    }

    public void moveTo(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public String report() {
        return position.x()
                + ","
                + position.y()
                + ","
                + direction.name().toLowerCase();
    }

    private void move(int stepSize) {
        position = position.movedBy(
                direction.moveX(stepSize),
                direction.moveY(stepSize)
        );
    }
}
