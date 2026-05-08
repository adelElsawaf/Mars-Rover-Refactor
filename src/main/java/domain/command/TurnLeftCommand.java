package domain.command;

import domain.rover.Rover;

public class TurnLeftCommand implements Command {

    @Override
    public void execute(Rover rover) {
        rover.turnLeft();
    }
}
