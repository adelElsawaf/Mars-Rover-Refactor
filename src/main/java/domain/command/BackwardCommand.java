package domain.command;

import domain.rover.Rover;

public class BackwardCommand implements Command {

    @Override
    public void execute(Rover rover) {
        rover.moveBackward();
    }
}
