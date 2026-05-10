import application.RoverApplicationService;
import domain.planet.Mars;
import domain.rover.Rover;
import domain.rover.model.Direction;
import domain.rover.model.Position;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class MarsRover {

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        int width = readInt(reader, "Insert horizontal map size:");
        int height = readInt(reader, "Insert vertical map size:");

        MarsRoverInputValidator validator = new MarsRoverInputValidator(width, height);

        Position roverPosition = readValidRoverPosition(reader, validator);
        Direction direction = readValidDirection(reader);
        Set<Position> obstacles = readObstacles(reader, validator, roverPosition);

        RoverApplicationService service = new RoverApplicationService(
                new Rover(roverPosition, direction),
                new Mars(width, height, obstacles)
        );

        runCommandLoop(reader, service);
    }

    private static Position readValidRoverPosition(Scanner reader, MarsRoverInputValidator validator) {
        while (true) {
            int x = readInt(reader, "Insert horizontal initial rover position:");
            int y = readInt(reader, "Insert vertical initial rover position:");
            try {
                validator.validateRoverPosition(x, y);
                return new Position(x, y);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid rover position, please re-insert.");
            }
        }
    }

    private static Direction readValidDirection(Scanner reader) {
        while (true) {
            System.out.println("Insert initial rover direction (n/e/s/w):");
            try {
                return Direction.from(reader.next());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid direction, please re-insert.");
            }
        }
    }

    private static Set<Position> readObstacles(Scanner reader, MarsRoverInputValidator validator, Position roverPosition) {
        int count = readInt(reader, "Insert number of obstacles:");
        Set<Position> obstacles = new HashSet<>();

        for (int i = 0; i < count; i++) {
            int x = readInt(reader, "Insert obstacle " + (i + 1) + " horizontal position:");
            int y = readInt(reader, "Insert obstacle " + (i + 1) + " vertical position:");
            try {
                validator.validateObstaclePosition(x, y, roverPosition.x(), roverPosition.y());
                obstacles.add(new Position(x, y));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Skipping.");
            }
        }

        return obstacles;
    }

    private static void runCommandLoop(Scanner reader, RoverApplicationService service) {
        while (true) {
            System.out.println("Insert command (f,b,l,r):");
            try {
                service.executeCommand(reader.next());
                System.out.println(service.report());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int readInt(Scanner reader, String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return reader.nextInt();
            } catch (InputMismatchException e) {
                reader.next();
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
