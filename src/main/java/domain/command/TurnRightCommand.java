package domain.command;

import domain.rover.Rover;

public class TurnRightCommand implements Command {

    @Override
    public void execute(Rover rover) {
        rover.turnRight();
    }
}
