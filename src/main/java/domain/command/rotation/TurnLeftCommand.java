package domain.command.rotation;

import domain.command.Command;
import domain.rover.Rover;

public class TurnLeftCommand implements Command {

    @Override
    public void execute(Rover rover) {
        rover.turnLeft();
    }
}
