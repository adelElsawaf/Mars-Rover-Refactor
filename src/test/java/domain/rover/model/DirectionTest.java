package domain.rover.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DirectionTest {

    // ---------------- PARSING ----------------

    @Test
    void shouldParseNorthFromLowerCase() {
        assertEquals(Direction.NORTH, Direction.from("n"));
    }

    @Test
    void shouldParseEastFromLowerCase() {
        assertEquals(Direction.EAST, Direction.from("e"));
    }

    @Test
    void shouldParseSouthFromLowerCase() {
        assertEquals(Direction.SOUTH, Direction.from("s"));
    }

    @Test
    void shouldParseWestFromLowerCase() {
        assertEquals(Direction.WEST, Direction.from("w"));
    }

    @Test
    void shouldParseDirectionCaseInsensitive() {
        assertEquals(Direction.NORTH, Direction.from("N"));
    }

    @Test
    void shouldThrowForInvalidDirection() {
        assertThrows(IllegalArgumentException.class, () -> Direction.from("x"));
    }
}
