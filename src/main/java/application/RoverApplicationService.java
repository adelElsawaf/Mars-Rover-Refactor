package application;

import domain.mission.Mission;
import domain.planet.Planet;
import domain.rover.Rover;

public class RoverApplicationService {

    private final Mission mission;

    public RoverApplicationService(Rover rover, Planet planet) {
        this.mission = new Mission(rover, planet);
    }

    public void executeCommand(String command) {
        mission.execute(command).ifPresent(System.out::println);
    }

    public String report() {
        return mission.report();
    }
}
