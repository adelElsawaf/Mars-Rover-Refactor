package domain.mission;

import domain.command.CommandFactory;
import domain.planet.Planet;
import domain.rover.Rover;

public class Mission {

    private final Rover rover;
    private final Planet planet;

    public Mission(Rover rover, Planet planet) {
        this.rover = rover;
        this.planet = planet;
    }

    public void execute(String command) {
        CommandFactory.create(command).execute(rover);
        rover.moveTo(planet.wrapAround(rover.getPosition()));
    }

    public String report() {
        return rover.report();
    }
}
