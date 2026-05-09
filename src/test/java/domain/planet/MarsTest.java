package domain.planet;

import domain.rover.model.Position;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    // ---------------- OBSTACLE DETECTION ----------------

    @Test
    void shouldDetectObstacleAtGivenPosition() {

        Mars marsWithObstacle = new Mars(5, 5, Set.of(new Position(2, 3)));

        assertTrue(marsWithObstacle.hasObstacleAt(new Position(2, 3)));
    }

    @Test
    void shouldNotBlockFreePosition() {

        Mars marsWithObstacle = new Mars(5, 5, Set.of(new Position(2, 3)));

        assertFalse(marsWithObstacle.hasObstacleAt(new Position(1, 1)));
    }

    @Test
    void shouldNotBlockAnyPositionWhenNoObstacles() {

        assertFalse(mars.hasObstacleAt(new Position(2, 3)));
    }
}
