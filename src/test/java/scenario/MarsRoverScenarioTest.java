package scenario;

import domain.mission.Mission;
import domain.planet.Mars;
import domain.rover.Rover;
import domain.rover.model.Direction;
import domain.rover.model.Position;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarsRoverScenarioTest {

    // ---------------- Helpers ----------------

    private static Mission mission(int width, int height, int x, int y, String direction) {
        return mission(width, height, x, y, direction, Set.of());
    }

    private static Mission mission(int width, int height, int x, int y, String direction, Set<Position> obstacles) {
        return new Mission(
                new Rover(new Position(x, y), Direction.from(direction)),
                new Mars(width, height, obstacles));
    }

    private static List<String> run(Mission mission, String... commands) {
        List<String> messages = new ArrayList<>();
        for (String command : commands) mission.execute(command).ifPresent(messages::add);
        return messages;
    }

    private static Set<Position> obstacles(int... coords) {
        Set<Position> set = new HashSet<>();
        for (int i = 0; i < coords.length; i += 2)
            set.add(new Position(coords[i], coords[i + 1]));
        return set;
    }

    private static Set<Position> allCellsExcept(int width, int height, int excludeX, int excludeY) {
        Set<Position> set = new HashSet<>();
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                if (!(x == excludeX && y == excludeY))
                    set.add(new Position(x, y));
        return set;
    }

    // ============================================================
    // Wrapping
    // ============================================================

    @Nested
    class Wrapping {

        @Test
        void shouldWrapAroundToWestWhenMovingForwardOverEastEdge() {
            Mission mission = mission(5, 5, 4, 2, "e");
            run(mission, "f");
            assertEquals("0,2,east", mission.report());
        }

        @Test
        void shouldWrapAroundToEastWhenMovingForwardOverWestEdge() {
            Mission mission = mission(5, 5, 0, 2, "w");
            run(mission, "f");
            assertEquals("4,2,west", mission.report());
        }

        @Test
        void shouldWrapAroundToSouthWhenMovingForwardOverNorthEdge() {
            Mission mission = mission(5, 5, 2, 4, "n");
            run(mission, "f");
            assertEquals("2,0,north", mission.report());
        }

        @Test
        void shouldWrapAroundToNorthWhenMovingForwardOverSouthEdge() {
            Mission mission = mission(5, 5, 2, 0, "s");
            run(mission, "f");
            assertEquals("2,4,south", mission.report());
        }

        @Test
        void shouldWrapAroundWhenMovingBackwardFromEastEdge() {
            Mission mission = mission(5, 5, 4, 2, "w");
            run(mission, "b");
            assertEquals("0,2,west", mission.report());
        }

        @Test
        void shouldWrapAroundWhenMovingBackwardFromWestEdge() {
            Mission mission = mission(5, 5, 0, 2, "e");
            run(mission, "b");
            assertEquals("4,2,east", mission.report());
        }

        @Test
        void shouldWrapAroundWhenMovingBackwardFromNorthEdge() {
            Mission mission = mission(5, 5, 2, 4, "s");
            run(mission, "b");
            assertEquals("2,0,south", mission.report());
        }

        @Test
        void shouldWrapAroundWhenMovingBackwardFromSouthEdge() {
            Mission mission = mission(5, 5, 2, 0, "n");
            run(mission, "b");
            assertEquals("2,4,north", mission.report());
        }

        @Test
        void shouldReturnToStartAfterFullHorizontalLapEastward() {
            Mission mission = mission(5, 5, 0, 0, "e");
            run(mission, "f", "f", "f", "f", "f");
            assertEquals("0,0,east", mission.report());
        }

        @Test
        void shouldReturnToStartAfterFullVerticalLapNorthward() {
            Mission mission = mission(5, 5, 0, 0, "n");
            run(mission, "f", "f", "f", "f", "f");
            assertEquals("0,0,north", mission.report());
        }

        @Test
        void shouldReturnToStartAfterFullHorizontalLapWestward() {
            Mission mission = mission(5, 5, 4, 0, "w");
            run(mission, "f", "f", "f", "f", "f");
            assertEquals("4,0,west", mission.report());
        }

        @Test
        void shouldReturnToStartAfterFullVerticalLapSouthward() {
            Mission mission = mission(5, 5, 0, 4, "s");
            run(mission, "f", "f", "f", "f", "f");
            assertEquals("0,4,south", mission.report());
        }

        @Test
        void shouldWrapAtTopRightCornerWhenFacingNorth() {
            Mission mission = mission(5, 5, 4, 4, "n");
            run(mission, "f");
            assertEquals("4,0,north", mission.report());
        }

        @Test
        void shouldWrapAtBottomLeftCornerWhenFacingWest() {
            Mission mission = mission(5, 5, 0, 0, "w");
            run(mission, "f");
            assertEquals("4,0,west", mission.report());
        }

        @Test
        void shouldWrapAtBottomLeftCornerWhenFacingSouth() {
            Mission mission = mission(5, 5, 0, 0, "s");
            run(mission, "f");
            assertEquals("0,4,south", mission.report());
        }

        @Test
        void shouldWrapAtTopRightCornerWhenFacingEast() {
            Mission mission = mission(5, 5, 4, 4, "e");
            run(mission, "f");
            assertEquals("0,4,east", mission.report());
        }

        @Test
        void shouldWrapEastEdgeOnNonSquareGrid() {
            Mission mission = mission(10, 3, 9, 1, "e");
            run(mission, "f");
            assertEquals("0,1,east", mission.report());
        }

        @Test
        void shouldWrapNorthEdgeOnNonSquareGrid() {
            Mission mission = mission(10, 3, 5, 2, "n");
            run(mission, "f");
            assertEquals("5,0,north", mission.report());
        }

        @Test
        void shouldStayInPlaceOnSingleCellGridWhenMovingForward() {
            Mission mission = mission(1, 1, 0, 0, "n");
            run(mission, "f");
            assertEquals("0,0,north", mission.report());
        }

        @Test
        void shouldStayInPlaceOnSingleCellGridWhenMovingBackward() {
            Mission mission = mission(1, 1, 0, 0, "n");
            run(mission, "b");
            assertEquals("0,0,north", mission.report());
        }

        @Test
        void shouldWrapAroundImmediatelyOnSingleColumnGrid() {
            Mission mission = mission(1, 5, 0, 2, "e");
            run(mission, "f");
            assertEquals("0,2,east", mission.report());
        }

        @Test
        void shouldWrapAroundImmediatelyOnSingleRowGrid() {
            Mission mission = mission(5, 1, 2, 0, "n");
            run(mission, "f");
            assertEquals("2,0,north", mission.report());
        }
    }

    // ============================================================
    // Obstacle Detection
    // ============================================================

    @Nested
    class ObstacleDetection {

        @Test
        void shouldBlockForwardMovementWhenObstacleIsAheadNorth() {
            Mission mission = mission(5, 5, 2, 2, "n", obstacles(2, 3));
            List<String> messages = run(mission, "f");
            assertEquals("2,2,north", mission.report());
            assertEquals(List.of("Obstacle detected at 2,3"), messages);
        }

        @Test
        void shouldBlockForwardMovementWhenObstacleIsAheadSouth() {
            Mission mission = mission(5, 5, 2, 2, "s", obstacles(2, 1));
            List<String> messages = run(mission, "f");
            assertEquals("2,2,south", mission.report());
            assertEquals(List.of("Obstacle detected at 2,1"), messages);
        }

        @Test
        void shouldBlockForwardMovementWhenObstacleIsAheadEast() {
            Mission mission = mission(5, 5, 2, 2, "e", obstacles(3, 2));
            List<String> messages = run(mission, "f");
            assertEquals("2,2,east", mission.report());
            assertEquals(List.of("Obstacle detected at 3,2"), messages);
        }

        @Test
        void shouldBlockForwardMovementWhenObstacleIsAheadWest() {
            Mission mission = mission(5, 5, 2, 2, "w", obstacles(1, 2));
            List<String> messages = run(mission, "f");
            assertEquals("2,2,west", mission.report());
            assertEquals(List.of("Obstacle detected at 1,2"), messages);
        }

        @Test
        void shouldBlockBackwardMovementWhenObstacleIsBehindNorth() {
            Mission mission = mission(5, 5, 2, 2, "n", obstacles(2, 1));
            List<String> messages = run(mission, "b");
            assertEquals("2,2,north", mission.report());
            assertEquals(List.of("Obstacle detected at 2,1"), messages);
        }

        @Test
        void shouldBlockBackwardMovementWhenObstacleIsBehindSouth() {
            Mission mission = mission(5, 5, 2, 2, "s", obstacles(2, 3));
            List<String> messages = run(mission, "b");
            assertEquals("2,2,south", mission.report());
            assertEquals(List.of("Obstacle detected at 2,3"), messages);
        }

        @Test
        void shouldBlockBackwardMovementWhenObstacleIsBehindEast() {
            Mission mission = mission(5, 5, 2, 2, "e", obstacles(1, 2));
            List<String> messages = run(mission, "b");
            assertEquals("2,2,east", mission.report());
            assertEquals(List.of("Obstacle detected at 1,2"), messages);
        }

        @Test
        void shouldBlockBackwardMovementWhenObstacleIsBehindWest() {
            Mission mission = mission(5, 5, 2, 2, "w", obstacles(3, 2));
            List<String> messages = run(mission, "b");
            assertEquals("2,2,west", mission.report());
            assertEquals(List.of("Obstacle detected at 3,2"), messages);
        }

        @Test
        void shouldNotBlockMovementWhenObstacleIsToTheSide() {
            Mission mission = mission(5, 5, 2, 2, "n", obstacles(3, 2));
            List<String> messages = run(mission, "f");
            assertEquals("2,3,north", mission.report());
            assertTrue(messages.isEmpty());
        }

        @Test
        void shouldBlockForwardMovementWhenObstacleIsAtWrappedNorthPosition() {
            Mission mission = mission(5, 5, 2, 4, "n", obstacles(2, 0));
            List<String> messages = run(mission, "f");
            assertEquals("2,4,north", mission.report());
            assertEquals(List.of("Obstacle detected at 2,0"), messages);
        }

        @Test
        void shouldBlockForwardMovementWhenObstacleIsAtWrappedSouthPosition() {
            Mission mission = mission(5, 5, 2, 0, "s", obstacles(2, 4));
            List<String> messages = run(mission, "f");
            assertEquals("2,0,south", mission.report());
            assertEquals(List.of("Obstacle detected at 2,4"), messages);
        }

        @Test
        void shouldBlockForwardMovementWhenObstacleIsAtWrappedEastPosition() {
            Mission mission = mission(5, 5, 4, 2, "e", obstacles(0, 2));
            List<String> messages = run(mission, "f");
            assertEquals("4,2,east", mission.report());
            assertEquals(List.of("Obstacle detected at 0,2"), messages);
        }

        @Test
        void shouldBlockForwardMovementWhenObstacleIsAtWrappedWestPosition() {
            Mission mission = mission(5, 5, 0, 2, "w", obstacles(4, 2));
            List<String> messages = run(mission, "f");
            assertEquals("0,2,west", mission.report());
            assertEquals(List.of("Obstacle detected at 4,2"), messages);
        }

        @Test
        void shouldNotReportObstacleWhenRotatingNearIt() {
            Mission mission = mission(5, 5, 2, 2, "n", obstacles(2, 3));
            List<String> messages = run(mission, "l");
            assertEquals("2,2,west", mission.report());
            assertTrue(messages.isEmpty());
        }

        @Test
        void shouldMoveFreelySinceRotatedAwayFromObstacle() {
            Mission mission = mission(5, 5, 2, 2, "n", obstacles(2, 3));
            List<String> messages = run(mission, "r", "f");
            assertEquals("3,2,east", mission.report());
            assertTrue(messages.isEmpty());
        }

        @Test
        void shouldReportObstacleThenMoveInNewDirectionAfterRotation() {
            Mission mission = mission(5, 5, 2, 2, "n", obstacles(2, 3));
            List<String> messages = run(mission, "f", "r", "f");
            assertEquals("3,2,east", mission.report());
            assertEquals(List.of("Obstacle detected at 2,3"), messages);
        }

        @Test
        void shouldReportObstacleTwiceWhenHittingItConsecutively() {
            Mission mission = mission(5, 5, 2, 2, "n", obstacles(2, 3));
            List<String> messages = run(mission, "f", "f");
            assertEquals("2,2,north", mission.report());
            assertEquals(List.of("Obstacle detected at 2,3", "Obstacle detected at 2,3"), messages);
        }

        @Test
        void shouldOnlyBlockOnObstacleDirectlyAhead() {
            Mission mission = mission(5, 5, 0, 0, "n", obstacles(1, 0, 0, 1, 2, 2));
            List<String> messages = run(mission, "f");
            assertEquals("0,0,north", mission.report());
            assertEquals(List.of("Obstacle detected at 0,1"), messages);
        }

        @Test
        void shouldBlockOnSecondObstacleAfterRoutingAroundFirst() {
            Mission mission = mission(5, 5, 0, 0, "n", obstacles(0, 1, 1, 2));
            List<String> messages = run(mission, "r", "f", "l", "f", "f");
            assertEquals("1,1,north", mission.report());
            assertEquals(List.of("Obstacle detected at 1,2"), messages);
        }

        @Test
        void shouldBlockAllMovementWhenRoverIsSurroundedOnAllFourSides() {
            Mission mission = mission(5, 5, 2, 2, "n", obstacles(2, 3, 2, 1, 3, 2, 1, 2));
            List<String> messages = run(mission, "f", "b", "r", "f", "l", "l", "f");
            assertEquals("2,2,west", mission.report());
            assertEquals(List.of(
                    "Obstacle detected at 2,3",
                    "Obstacle detected at 2,1",
                    "Obstacle detected at 3,2",
                    "Obstacle detected at 1,2"), messages);
        }

        @Test
        void shouldBlockBackwardMovementWhenObstacleIsAtWrappedPosition() {
            Mission mission = mission(5, 5, 2, 4, "s", obstacles(2, 0));
            List<String> messages = run(mission, "b");
            assertEquals("2,4,south", mission.report());
            assertEquals(List.of("Obstacle detected at 2,0"), messages);
        }
    }

    // ============================================================
    // Grid Edge Cases
    // ============================================================

    @Nested
    class GridEdgeCases {

        @Test
        void shouldAllowOnlyRotationOnSingleCellGrid() {
            Mission mission = mission(1, 1, 0, 0, "n");
            run(mission, "f", "b", "l", "f", "r", "f");
            assertEquals("0,0,north", mission.report());
        }

        @Test
        void shouldWrapAfterTwoStepsOnTwoByTwoGrid() {
            Mission mission = mission(2, 2, 0, 0, "e");
            run(mission, "f", "f");
            assertEquals("0,0,east", mission.report());
        }

        @Test
        void shouldMoveWithoutWrappingOnLargeGrid() {
            Mission mission = mission(100, 100, 50, 50, "n");
            run(mission, "f", "f", "f");
            assertEquals("50,53,north", mission.report());
        }

        @Test
        void shouldWrapAtNorthEdgeOnLargeGrid() {
            Mission mission = mission(100, 100, 50, 99, "n");
            run(mission, "f");
            assertEquals("50,0,north", mission.report());
        }

        @Test
        void shouldWrapWhenStartingAtMaxPosition() {
            Mission mission = mission(5, 5, 4, 4, "n");
            run(mission, "f");
            assertEquals("4,0,north", mission.report());
        }
    }

    // ============================================================
    // Command Sequences
    // ============================================================

    @Nested
    class CommandSequences {

        @Test
        void shouldReturnToStartAfterSquareLoop() {
            Mission mission = mission(5, 5, 2, 2, "n");
            run(mission, "f", "r", "f", "r", "f", "r", "f", "r");
            assertEquals("2,2,north", mission.report());
        }

        @Test
        void shouldReachExpectedPositionAfterZigzagSequence() {
            Mission mission = mission(10, 10, 0, 0, "n");
            run(mission, "f", "r", "f", "l", "f", "r", "f", "l");
            assertEquals("2,2,north", mission.report());
        }

        @Test
        void shouldStayInPlaceAfterAlternatingForwardAndBackward() {
            Mission mission = mission(5, 5, 2, 2, "n");
            run(mission, "f", "b", "f", "b");
            assertEquals("2,2,north", mission.report());
        }

        @Test
        void shouldMoveOppositeDirectionAfterTurningAround() {
            Mission mission = mission(5, 5, 2, 2, "n");
            run(mission, "l", "l", "f");
            assertEquals("2,1,south", mission.report());
        }

        @Test
        void shouldTraverseEntireGridWidthThenTurn() {
            Mission mission = mission(5, 5, 0, 0, "e");
            run(mission, "f", "f", "f", "f", "l", "f");
            assertEquals("4,1,north", mission.report());
        }

        @Test
        void shouldBlockTwiceAndContinueAfterRotation() {
            Mission mission = mission(5, 5, 0, 0, "n", obstacles(0, 2));
            List<String> messages = run(mission, "f", "f", "f", "r", "f");
            assertEquals("1,1,east", mission.report());
            assertEquals(List.of("Obstacle detected at 0,2", "Obstacle detected at 0,2"), messages);
        }

        @Test
        void shouldBlockAtWrappedPositionAfterCrossingEdge() {
            Mission mission = mission(5, 5, 4, 2, "e", obstacles(0, 2));
            List<String> messages = run(mission, "f");
            assertEquals("4,2,east", mission.report());
            assertEquals(List.of("Obstacle detected at 0,2"), messages);
        }

        @Test
        void shouldReturnToOriginalDirectionAfterTurningLeftThenRight() {
            Mission mission = mission(5, 5, 2, 2, "n");
            run(mission, "l", "r");
            assertEquals("2,2,north", mission.report());
        }

        @Test
        void shouldReturnToOriginalDirectionAfterTurningRightThenLeft() {
            Mission mission = mission(5, 5, 2, 2, "n");
            run(mission, "r", "l");
            assertEquals("2,2,north", mission.report());
        }

        @Test
        void shouldReturnToStartAfterTracingGridPerimeter() {
            Mission mission = mission(3, 3, 0, 0, "e");
            run(mission, "f", "f", "l", "f", "f", "l", "f", "f", "l", "f", "f", "l");
            assertEquals("0,0,east", mission.report());
        }
    }

    // ============================================================
    // Obstacle Edge Cases
    // ============================================================

    @Nested
    class ObstacleEdgeCases {

        @Test
        void shouldTrapRoverWhenEveryNeighbouringCellIsAnObstacle() {
            Mission mission = mission(5, 5, 2, 2, "n", allCellsExcept(5, 5, 2, 2));
            List<String> messages = run(mission, "f", "b", "r", "f", "l", "l", "f");
            assertEquals("2,2,west", mission.report());
            assertEquals(List.of(
                    "Obstacle detected at 2,3",
                    "Obstacle detected at 2,1",
                    "Obstacle detected at 3,2",
                    "Obstacle detected at 1,2"), messages);
        }

        @Test
        void shouldDeduplicateObstaclesAndBlockCorrectly() {
            Mission mission = mission(5, 5, 0, 0, "n", obstacles(0, 1, 1, 0));
            List<String> messages = run(mission, "f");
            assertEquals("0,0,north", mission.report());
            assertEquals(List.of("Obstacle detected at 0,1"), messages);
        }

        @Test
        void shouldFullySurroundRoverOnSmallGrid() {
            Mission mission = mission(3, 3, 1, 1, "n", allCellsExcept(3, 3, 1, 1));
            List<String> messages = run(mission, "f", "b", "r", "f", "l", "l", "f");
            assertEquals("1,1,west", mission.report());
            assertEquals(List.of(
                    "Obstacle detected at 1,2",
                    "Obstacle detected at 1,0",
                    "Obstacle detected at 2,1",
                    "Obstacle detected at 0,1"), messages);
        }

        @Test
        void shouldIgnoreInvalidObstaclesAndOnlyBlockOnValidOnes() {
            Mission mission = mission(5, 5, 2, 2, "n", obstacles(2, 3, 1, 2));
            List<String> messages = run(mission, "f", "l", "f");
            assertEquals("2,2,west", mission.report());
            assertEquals(List.of(
                    "Obstacle detected at 2,3",
                    "Obstacle detected at 1,2"), messages);
        }
    }

    // ============================================================
    // Invalid Input
    // ============================================================

    @Nested
    class InvalidInput {

        @Test
        void shouldThrowExceptionForUnknownCommand() {
            Mission mission = mission(5, 5, 2, 2, "n");
            assertThrows(IllegalArgumentException.class, () -> mission.execute("x"));
            assertEquals("2,2,north", mission.report());
        }

        @Test
        void shouldNotAffectStateOnInvalidCommandAndThenAllowValidCommand() {
            Mission mission = mission(5, 5, 2, 2, "n");
            assertThrows(IllegalArgumentException.class, () -> mission.execute("x"));
            run(mission, "f");
            assertEquals("2,3,north", mission.report());
        }
    }
}
