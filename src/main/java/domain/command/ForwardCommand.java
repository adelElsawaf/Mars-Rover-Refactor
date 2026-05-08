package domain.command;

import domain.rover.Rover;

public class ForwardCommand implements Command {

    @Override
    public void execute(Rover rover) {
        rover.moveForward();
    }
}
