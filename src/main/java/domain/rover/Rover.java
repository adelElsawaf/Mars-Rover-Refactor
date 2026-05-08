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
        moveForward(DEFAULT_STEP_SIZE);
    }

    public void moveForward(int stepSize) {
        move(stepSize);
    }

    public void moveBackward() {
        moveBackward(DEFAULT_STEP_SIZE);
    }

    public void moveBackward(int stepSize) {
        move(-stepSize);
    }

    public void turnLeft() {
        direction = direction.left();
    }

    public void turnRight() {
        direction = direction.right();
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
        position = new Position(
                position.x() + direction.moveX(stepSize),
                position.y() + direction.moveY(stepSize)
        );
    }
}