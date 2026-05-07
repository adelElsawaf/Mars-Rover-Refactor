import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarsRoverTest {

    @Test
    void shouldMoveForwardNorth() {
        String result = MarsRover.executeCommand(0, 0, "n", "f");
        assertEquals("0,1,n", result);
    }

    @Test
    void shouldMoveForwardSouth() {
        String result = MarsRover.executeCommand(0, 0, "s", "f");
        assertEquals("0,-1,s", result);
    }

    @Test
    void shouldMoveForwardEast() {
        String result = MarsRover.executeCommand(0, 0, "e", "f");
        assertEquals("1,0,e", result);
    }

    @Test
    void shouldMoveForwardWest() {
        String result = MarsRover.executeCommand(0, 0, "w", "f");
        assertEquals("-1,0,w", result);
    }


    @Test
    void shouldMoveBackwardNorth() {
        String result = MarsRover.executeCommand(0, 0, "n", "b");
        assertEquals("0,-1,n", result);
    }

    @Test
    void shouldMoveBackwardSouth() {
        String result = MarsRover.executeCommand(0, 0, "s", "b");
        assertEquals("0,1,s", result);
    }

    @Test
    void shouldMoveBackwardEast() {
        String result = MarsRover.executeCommand(0, 0, "e", "b");
        assertEquals("-1,0,e", result);
    }

    @Test
    void shouldMoveBackwardWest() {
        String result = MarsRover.executeCommand(0, 0, "w", "b");
        assertEquals("1,0,w", result);
    }

    @Test
    void shouldRotateLeftFromNorth() {
        String result = MarsRover.executeCommand(0, 0, "n", "l");
        assertEquals("0,0,w", result);
    }

    @Test
    void shouldRotateLeftFromWest() {
        String result = MarsRover.executeCommand(0, 0, "w", "l");
        assertEquals("0,0,s", result);
    }

    @Test
    void shouldRotateLeftFromSouth() {
        String result = MarsRover.executeCommand(0, 0, "s", "l");
        assertEquals("0,0,e", result);
    }

    @Test
    void shouldRotateLeftFromEast() {
        String result = MarsRover.executeCommand(0, 0, "e", "l");
        assertEquals("0,0,n", result);
    }

    @Test
    void shouldRotateRightFromNorth() {
        String result = MarsRover.executeCommand(0, 0, "n", "r");
        assertEquals("0,0,e", result);
    }

    @Test
    void shouldRotateRightFromEast() {
        String result = MarsRover.executeCommand(0, 0, "e", "r");
        assertEquals("0,0,s", result);
    }

    @Test
    void shouldRotateRightFromSouth() {
        String result = MarsRover.executeCommand(0, 0, "s", "r");
        assertEquals("0,0,w", result);
    }

    @Test
    void shouldRotateRightFromWest() {
        String result = MarsRover.executeCommand(0, 0, "w", "r");
        assertEquals("0,0,n", result);
    }
}