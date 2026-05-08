package domain.rover;

import domain.rover.model.Direction;
import domain.rover.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoverTest {

    // ---------------- FORWARD MOVEMENT ----------------

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

    // ---------------- BACKWARD MOVEMENT ----------------

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

    // ---------------- LEFT ROTATION ----------------

    @Test
    void shouldRotateLeftFromNorth() {

        Rover rover = new Rover(new Position(0, 0), Direction.NORTH);

        rover.turnLeft();

        assertEquals("0,0,west", rover.report());
    }

    @Test
    void shouldRotateLeftFromWest() {

        Rover rover = new Rover(new Position(0, 0), Direction.WEST);

        rover.turnLeft();

        assertEquals("0,0,south", rover.report());
    }

    @Test
    void shouldRotateLeftFromSouth() {

        Rover rover = new Rover(new Position(0, 0), Direction.SOUTH);

        rover.turnLeft();

        assertEquals("0,0,east", rover.report());
    }

    @Test
    void shouldRotateLeftFromEast() {

        Rover rover = new Rover(new Position(0, 0), Direction.EAST);

        rover.turnLeft();

        assertEquals("0,0,north", rover.report());
    }

    // ---------------- RIGHT ROTATION ----------------

    @Test
    void shouldRotateRightFromNorth() {

        Rover rover = new Rover(new Position(0, 0), Direction.NORTH);

        rover.turnRight();

        assertEquals("0,0,east", rover.report());
    }

    @Test
    void shouldRotateRightFromEast() {

        Rover rover = new Rover(new Position(0, 0), Direction.EAST);

        rover.turnRight();

        assertEquals("0,0,south", rover.report());
    }

    @Test
    void shouldRotateRightFromSouth() {

        Rover rover = new Rover(new Position(0, 0), Direction.SOUTH);

        rover.turnRight();

        assertEquals("0,0,west", rover.report());
    }

    @Test
    void shouldRotateRightFromWest() {

        Rover rover = new Rover(new Position(0, 0), Direction.WEST);

        rover.turnRight();

        assertEquals("0,0,north", rover.report());
    }
}
