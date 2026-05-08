package domain.rover;

import domain.rover.Rover;
import domain.rover.model.Direction;
import domain.rover.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoverTest {

    // ---------------- MOVEMENT ----------------

    @Test
    void shouldMoveForwardNorth() {

        Rover rover = new Rover(new Position(0, 0), Direction.NORTH);

        rover.moveForward();

        assertEquals("0,1,north", rover.report());
    }

    @Test
    void shouldMoveForwardSouth() {

        Rover rover = new Rover(new Position(0, 0), Direction.SOUTH);

        rover.moveForward();

        assertEquals("0,-1,south", rover.report());
    }

    @Test
    void shouldMoveForwardEast() {

        Rover rover = new Rover(new Position(0, 0), Direction.EAST);

        rover.moveForward();

        assertEquals("1,0,east", rover.report());
    }

    @Test
    void shouldMoveForwardWest() {

        Rover rover = new Rover(new Position(0, 0), Direction.WEST);

        rover.moveForward();

        assertEquals("-1,0,west", rover.report());
    }

    // ---------------- BACKWARD ----------------

    @Test
    void shouldMoveBackwardNorth() {

        Rover rover = new Rover(new Position(0, 0), Direction.NORTH);

        rover.moveBackward();

        assertEquals("0,-1,north", rover.report());
    }

    @Test
    void shouldMoveBackwardSouth() {

        Rover rover = new Rover(new Position(0, 0), Direction.SOUTH);

        rover.moveBackward();

        assertEquals("0,1,south", rover.report());
    }

    @Test
    void shouldMoveBackwardEast() {

        Rover rover = new Rover(new Position(0, 0), Direction.EAST);

        rover.moveBackward();

        assertEquals("-1,0,east", rover.report());
    }

    @Test
    void shouldMoveBackwardWest() {

        Rover rover = new Rover(new Position(0, 0), Direction.WEST);

        rover.moveBackward();

        assertEquals("1,0,west", rover.report());
    }

    // ---------------- ROTATION ----------------

    @Test
    void shouldRotateLeft() {

        Rover rover = new Rover(new Position(0, 0), Direction.NORTH);

        rover.turnLeft();

        assertEquals("0,0,west", rover.report());

        rover.turnLeft();
        assertEquals("0,0,south", rover.report());
    }

    @Test
    void shouldRotateRight() {

        Rover rover = new Rover(new Position(0, 0), Direction.NORTH);

        rover.turnRight();

        assertEquals("0,0,east", rover.report());

        rover.turnRight();
        assertEquals("0,0,south", rover.report());
    }
}