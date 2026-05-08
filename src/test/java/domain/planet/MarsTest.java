package domain.planet;

import domain.rover.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarsTest {

    private final Mars mars = new Mars(5, 5);

    // ---------------- HORIZONTAL WRAPPING ----------------

    @Test
    void shouldWrapEastEdge() {
        assertEquals(new Position(0, 0), mars.wrapAround(new Position(5, 0)));
    }

    @Test
    void shouldWrapWestEdge() {
        assertEquals(new Position(4, 0), mars.wrapAround(new Position(-1, 0)));
    }

    // ---------------- VERTICAL WRAPPING ----------------

    @Test
    void shouldWrapNorthEdge() {
        assertEquals(new Position(0, 0), mars.wrapAround(new Position(0, 5)));
    }

    @Test
    void shouldWrapSouthEdge() {
        assertEquals(new Position(0, 4), mars.wrapAround(new Position(0, -1)));
    }

    // ---------------- NO WRAPPING NEEDED ----------------

    @Test
    void shouldNotWrapPositionInsideBounds() {
        assertEquals(new Position(2, 3), mars.wrapAround(new Position(2, 3)));
    }
}
