package domain.mission;

import domain.command.Command;
import domain.command.CommandFactory;
import domain.command.movement.MovementCommand;
import domain.planet.Planet;
import domain.rover.Rover;
import domain.rover.model.Position;

import java.util.Optional;

public class Mission {

    private final Rover rover;
    private final Planet planet;

    public Mission(Rover rover, Planet planet) {
        this.rover = rover;
        this.planet = planet;
    }

    public Optional<String> execute(String input) {
        Command command = CommandFactory.create(input);

        if (command instanceof MovementCommand movementCommand) {
            return attemptMove(movementCommand.getNextPosition(rover));
        }

        command.execute(rover);
        return Optional.empty();
    }

    public String report() {
        return rover.report();
    }

    private Optional<String> attemptMove(Position intendedPosition) {
        Position destination = planet.wrapAround(intendedPosition);

        if (planet.hasObstacleAt(destination)) {
            return Optional.of("Obstacle detected at " + destination.x() + "," + destination.y());
        }

        rover.moveTo(destination);
        return Optional.empty();
    }
}
