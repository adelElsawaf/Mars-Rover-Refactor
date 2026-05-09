package domain.command.movement;

import domain.command.Command;
import domain.rover.Rover;
import domain.rover.model.Position;

public interface MovementCommand extends Command {

    Position getNextPosition(Rover rover);
}
