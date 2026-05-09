package domain.rover;

import domain.rover.model.Direction;
import domain.rover.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoverTest {

    // ---------------- FORWARD MOVEMENT ----------------

    @Test
    void shouldMoveForwardWhenFacingNorth() {

        Rover rover = new Rover(new Position(0, 0), Direction.NORTH);

        rover.moveTo(rover.getNextForwardPosition());

        assertEquals("0,1,north", rover.report());
    }

    @Test
    void shouldMoveForwardWhenFacingSouth() {

        Rover rover = new Rover(new Position(0, 0), Direction.SOUTH);

        rover.moveTo(rover.getNextForwardPosition());

        assertEquals("0,-1,south", rover.report());
    }

    @Test
    void shouldMoveForwardWhenFacingEast() {

        Rover rover = new Rover(new Position(0, 0), Direction.EAST);

        rover.moveTo(rover.getNextForwardPosition());

        assertEquals("1,0,east", rover.report());
    }

    @Test
    void shouldMoveForwardWhenFacingWest() {

        Rover rover = new Rover(new Position(0, 0), Direction.WEST);

        rover.moveTo(rover.getNextForwardPosition());

        assertEquals("-1,0,west", rover.report());
    }

    // ---------------- BACKWARD MOVEMENT ----------------

    @Test
    void shouldMoveBackwardWhenFacingNorth() {

        Rover rover = new Rover(new Position(0, 0), Direction.NORTH);

        rover.moveTo(rover.getNextBackwardPosition());

        assertEquals("0,-1,north", rover.report());
    }

    @Test
    void shouldMoveBackwardWhenFacingSouth() {

        Rover rover = new Rover(new Position(0, 0), Direction.SOUTH);

        rover.moveTo(rover.getNextBackwardPosition());

        assertEquals("0,1,south", rover.report());
    }

    @Test
    void shouldMoveBackwardWhenFacingEast() {

        Rover rover = new Rover(new Position(0, 0), Direction.EAST);

        rover.moveTo(rover.getNextBackwardPosition());

        assertEquals("-1,0,east", rover.report());
    }

    @Test
    void shouldMoveBackwardWhenFacingWest() {

        Rover rover = new Rover(new Position(0, 0), Direction.WEST);

        rover.moveTo(rover.getNextBackwardPosition());

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
