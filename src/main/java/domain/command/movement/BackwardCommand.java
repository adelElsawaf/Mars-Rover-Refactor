package domain.command.movement;

import domain.rover.Rover;
import domain.rover.model.Position;

public class BackwardCommand implements MovementCommand {

    @Override
    public Position getNextPosition(Rover rover) {
        return rover.getNextBackwardPosition();
    }

    @Override
    public void execute(Rover rover) {
        rover.moveTo(getNextPosition(rover));
    }
}
