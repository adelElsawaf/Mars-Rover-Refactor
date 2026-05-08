package domain.command;

import domain.rover.Rover;

public interface Command {
    void execute(Rover rover);
}
