# Mars Rover — Solution

## How to build and run

**Run tests:**

```bash
mvn test
```

**Build executable jar:**

```bash
mvn package
```

**Run the application:**

```bash
java -jar target/mars-rover.jar
```

---

## What the application does

The application simulates a rover navigating a two-dimensional map of Mars.

On startup, the user provides:

- Map dimensions (width and height)
- Rover starting position (x, y) and direction (n / e / s / w)
- A list of obstacle positions

The user then sends commands one at a time:

- `f` — move forward
- `b` — move backward
- `l` — rotate left 90°
- `r` — rotate right 90°

After each command, the rover reports its current position and direction.
If the rover reaches the edge of the map it wraps around to the opposite side (Mars is a sphere).
If an obstacle is in the way, the rover stays in place and reports the blocked position.

---

## Architecture

The solution is organised into four clear layers:

```
Infrastructure
  MarsRover.java               entry point: reads console input, drives the setup flow

  MarsRoverInputValidator      validates map bounds and obstacle positions;
                               instance-based so map context is set once, not repeated

Application
  application/
    RoverApplicationService    orchestrates the domain per use case;
                               receives built domain objects, exposes executeCommand/report

Domain
  domain/mission/
    Mission                    aggregate root: coordinates Rover and Planet,
                               enforces wrap → obstacle check → move invariant

  domain/rover/
    Rover                      entity: owns position and direction state
    model/Position             value object: immutable 2D coordinate (record)
    model/Direction            value object: cardinal direction with movement vectors
                               and Direction.from() named constructor

  domain/planet/
    Planet                     interface: contract for any navigable surface
    Mars                       implementation: floorMod wrapping, obstacle set lookup

  domain/command/
    Command                    base interface: execute(Rover)
    movement/
      MovementCommand          sub-interface: adds getNextPosition(Rover)
      ForwardCommand           implements MovementCommand — delegates to rover
      BackwardCommand          implements MovementCommand — delegates to rover
    rotation/
      TurnLeftCommand          implements Command — delegates to rover
      TurnRightCommand         implements Command — delegates to rover
    CommandFactory             maps input strings to command objects (Simple Factory)
```

---

## Design patterns used

### Command Pattern

Each user input maps to a command object (`ForwardCommand`, `TurnLeftCommand`, etc.).
`Mission` dispatches commands without knowing their concrete type — open to new commands
without any changes to `Mission`.

### Factory Pattern

`CommandFactory` is the single place that maps strings to command instances.
Adding a new command is one new class and one new `case` in the factory.

---

## Key design decisions

### `Planet` as interface (Open/Closed Principle)

`Planet` is an interface rather than a concrete class. `Mars` is the current implementation.
A new planetary surface (different size, different wrapping rules) is a new class with zero
changes to `Mission`, `Rover`, or any command. The system is open for extension, closed
for modification.

### `Mission` as aggregate root

`Rover` and `Planet` are private to `Mission`. No external code accesses them directly.
`Mission` enforces a single invariant on every movement: wrap the intended position first,
then check for obstacles, then commit `moveTo`. This order never changes regardless of
which command triggered it.

### `MovementCommand` sub-interface

Movement commands expose `getNextPosition(Rover)` so `Mission` can apply planet rules
before the rover actually moves. Rotation commands have no position side effect so they
go straight through `execute`. The `instanceof MovementCommand` check in `Mission` is the
only place in the system that distinguishes the two command families.

### `Optional<String>` from `Mission.execute`

The domain communicates obstacle events as a return value — not a `System.out` call.
`RoverApplicationService` decides what to do with it (`ifPresent(System.out::println)`).
This keeps the domain layer free of output side effects.

### Immutable `Position` record

Every position calculation returns a new `Position`. No shared mutable state between
commands or between the wrap step and the obstacle check step.

### Instance-based `MarsRoverInputValidator`

The validator holds `width` and `height` as constructor state. Methods only receive
the per-call variables (`x`, `y`). When multiple rovers are added, the same validator
instance reuses the map context without repeating it on every call.

---

## Functional requirements


| Requirement                      | Covered by                                          |
| -------------------------------- | --------------------------------------------------- |
| Move forward                     | `ForwardCommand` → `Rover.getNextForwardPosition`   |
| Move backward                    | `BackwardCommand` → `Rover.getNextBackwardPosition` |
| Rotate left 90°                  | `TurnLeftCommand` → `Rover.turnLeft`                |
| Rotate right 90°                 | `TurnRightCommand` → `Rover.turnRight`              |
| Wrap at map edge                 | `Mars.wrapAround` using `Math.floorMod`             |
| Detect obstacle (bonus)          | `Mars.hasObstacleAt` checked before every move      |
| Stay on obstacle hit (bonus)     | `Mission.attemptMove` skips `moveTo` if blocked     |
| Report obstacle position (bonus) | `Mission.execute` returns `Optional<String>`        |


---

## Test coverage


| Test class      | Covers                                                                                                                                                          |
| --------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `RoverTest`     | forward and backward movement in all four directions; left and right rotation                                                                                   |
| `MissionTest`   | wrapping all four edges; backward movement and wrapping; obstacle blocking forward and backward; obstacle at wrapped position; rotation; multi-command sequence |
| `MarsTest`      | wrap arithmetic for all four edges; no-wrap case; obstacle present, absent, and no obstacles                                                                    |
| `DirectionTest` | `Direction.from` for all four inputs; case-insensitivity; invalid input exception                                                                               |


All tests run without starting the main application (`mvn test`).

---

## Extensibility

**Adding a new command (e.g. spin 180°):**

1. Create `SpinCommand implements Command` (or `MovementCommand` if it changes position)
2. Add `rover.spin()` to `Rover`
3. Add `case "spin" -> new SpinCommand()` to `CommandFactory`

Zero changes to `Mission`, `Planet`, or any existing command.

**Supporting a different planet surface:**

1. Implement the `Planet` interface with new wrapping and obstacle rules
2. Pass the new instance to `RoverApplicationService`

Zero changes to `Mission`, `Rover`, or any command.