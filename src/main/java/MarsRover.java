import domain.mission.Mission;
import domain.planet.Mars;
import domain.rover.Rover;
import domain.rover.model.Direction;
import domain.rover.model.Position;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MarsRover {

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        System.out.println("Insert horizontal map size:");
        int width = reader.nextInt();

        System.out.println("Insert vertical map size:");
        int height = reader.nextInt();

        System.out.println("Insert horizontal initial rover position:");
        int x = reader.nextInt();

        System.out.println("Insert vertical initial rover position:");
        int y = reader.nextInt();

        if (x < 0 || x >= width || y < 0 || y >= height) {
            System.out.println("Invalid rover position: (" + x + "," + y + ") is outside the map bounds.");
            return;
        }

        System.out.println("Insert initial rover direction (n/e/s/w):");
        String dir = reader.next();

        System.out.println("Insert number of obstacles:");
        int obstacleCount = reader.nextInt();

        Set<Position> obstacles = new HashSet<>();

        for (int i = 0; i < obstacleCount; i++) {
            System.out.println("Insert obstacle " + (i + 1) + " horizontal position:");
            int obstacleX = reader.nextInt();

            System.out.println("Insert obstacle " + (i + 1) + " vertical position:");
            int obstacleY = reader.nextInt();

            if (obstacleX < 0 || obstacleX >= width || obstacleY < 0 || obstacleY >= height) {
                System.out.println("Invalid obstacle position: (" + obstacleX + "," + obstacleY + ") is outside the map bounds. Skipping.");
                continue;
            }

            if (obstacleX == x && obstacleY == y) {
                System.out.println("Cannot place obstacle at rover's starting position. Skipping.");
                continue;
            }

            obstacles.add(new Position(obstacleX, obstacleY));
        }

        Mission mission = new Mission(
                new Rover(new Position(x, y), parseDirection(dir)),
                new Mars(width, height, obstacles)
        );

        while (true) {

            System.out.println("Insert command (f,b,l,r):");
            String command = reader.next();

            mission.execute(command);

            System.out.println(mission.report());
        }
    }

    // ---------------- Direction parser ----------------

    private static Direction parseDirection(String input) {

        return switch (input.toLowerCase()) {
            case "n" -> Direction.NORTH;
            case "e" -> Direction.EAST;
            case "s" -> Direction.SOUTH;
            case "w" -> Direction.WEST;
            default -> throw new IllegalArgumentException("Invalid direction: " + input);
        };
    }
}
