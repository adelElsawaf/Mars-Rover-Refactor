package domain.mission;

import domain.planet.Mars;
import domain.planet.Planet;
import domain.rover.Rover;
import domain.rover.model.Direction;
import domain.rover.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MissionTest {

    // ---------------- WRAPPING ----------------

    @Test
    void shouldWrapWhenRoverMovesOverEastEdge() {

        Mission mission = missionOn(new Mars(5, 5), 4, 0, Direction.EAST);

        mission.execute("f");

        assertEquals("0,0,east", mission.report());
    }

    @Test
    void shouldWrapWhenRoverMovesOverWestEdge() {

        Mission mission = missionOn(new Mars(5, 5), 0, 0, Direction.WEST);

        mission.execute("f");

        assertEquals("4,0,west", mission.report());
    }

    @Test
    void shouldWrapWhenRoverMovesOverNorthEdge() {

        Mission mission = missionOn(new Mars(5, 5), 0, 4, Direction.NORTH);

        mission.execute("f");

        assertEquals("0,0,north", mission.report());
    }

    @Test
    void shouldWrapWhenRoverMovesOverSouthEdge() {

        Mission mission = missionOn(new Mars(5, 5), 0, 0, Direction.SOUTH);

        mission.execute("f");

        assertEquals("0,4,south", mission.report());
    }

    // ---------------- ROTATION ----------------

    @Test
    void shouldRotateLeftWithoutAffectingPosition() {

        Mission mission = missionOn(new Mars(5, 5), 0, 0, Direction.NORTH);

        mission.execute("l");

        assertEquals("0,0,west", mission.report());
    }

    @Test
    void shouldRotateRightWithoutAffectingPosition() {

        Mission mission = missionOn(new Mars(5, 5), 0, 0, Direction.NORTH);

        mission.execute("r");

        assertEquals("0,0,east", mission.report());
    }

    // ---------------- SEQUENCE ----------------

    @Test
    void shouldExecuteMultipleCommandsInSequence() {

        Mission mission = missionOn(new Mars(5, 5), 0, 0, Direction.NORTH);

        mission.execute("f");
        mission.execute("r");
        mission.execute("f");

        assertEquals("1,1,east", mission.report());
    }

    // ---------------- HELPER ----------------

    private Mission missionOn(Planet planet, int x, int y, Direction direction) {
        return new Mission(new Rover(new Position(x, y), direction), planet);
    }
}
