package domain.command;

public class CommandFactory {

    private CommandFactory() {
    }

    public static Command create(String command) {

        return switch (command.toLowerCase()) {

            case "f" -> new ForwardCommand();

            case "b" -> new BackwardCommand();

            case "l" -> new TurnLeftCommand();

            case "r" -> new TurnRightCommand();

            default -> throw new IllegalArgumentException("Invalid command: " + command);
        };
    }
}
