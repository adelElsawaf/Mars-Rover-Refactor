import domain.rover.Rover;
import domain.rover.model.Direction;
import domain.rover.model.Position;

import java.util.Scanner;

public class MarsRover {

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        System.out.println("Insert horizontal map size:");
        int sizex = reader.nextInt();

        System.out.println("Insert vertical map size:");
        int sizey = reader.nextInt();

        System.out.println("Insert horizontal initial rover position:");
        int x = reader.nextInt();

        System.out.println("Insert vertical initial rover position:");
        int y = reader.nextInt();

        System.out.println("Insert initial rover direction (n/e/s/w):");
        String dir = reader.next();

        Rover rover = new Rover(
                new Position(x, y),
                parseDirection(dir)
        );

        while (true) {

            System.out.println("Insert command (f,b,l,r):");
            String command = reader.next();

            executeCommand(rover, command);

            System.out.println(rover.report());
        }
    }

    // ---------------- EXECUTION LAYER (now domain-based) ----------------

    public static void executeCommand(Rover rover, String command) {

        switch (command.toLowerCase()) {

            case "f" -> rover.moveForward();

            case "b" -> rover.moveBackward();

            case "l" -> rover.turnLeft();

            case "r" -> rover.turnRight();

            default -> throw new IllegalArgumentException("Invalid command");
        }
    }

    // ---------------- Direction parser ----------------

    private static Direction parseDirection(String input) {

        return switch (input.toLowerCase()) {

            case "n" -> Direction.NORTH;

            case "e" -> Direction.EAST;

            case "s" -> Direction.SOUTH;

            case "w" -> Direction.WEST;

            default -> throw new IllegalArgumentException("Invalid direction");
        };
    }
}