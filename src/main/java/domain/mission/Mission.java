package domain.mission;

import domain.command.Command;
import domain.command.CommandFactory;
import domain.command.movement.MovementCommand;
import domain.planet.Planet;
import domain.rover.Rover;
import domain.rover.model.Position;

public class Mission {

    private final Rover rover;
    private final Planet planet;

    public Mission(Rover rover, Planet planet) {
        this.rover = rover;
        this.planet = planet;
    }

    public void execute(String input) {
        Command command = CommandFactory.create(input);

        if (command instanceof MovementCommand movementCommand) {
            attemptMove(movementCommand.getNextPosition(rover));
        } else {
            command.execute(rover);
        }
    }

    public String report() {
        return rover.report();
    }

    private void attemptMove(Position intendedPosition) {
        Position destination = planet.wrapAround(intendedPosition);

        if (planet.hasObstacleAt(destination)) {
            System.out.println("Obstacle detected at " + destination.x() + "," + destination.y());
        } else {
            rover.moveTo(destination);
        }
    }
}
