public class MarsRoverInputValidator {

    private final int width;
    private final int height;

    public MarsRoverInputValidator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void validateRoverPosition(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException(
                    "Rover position (" + x + "," + y + ") is outside the map bounds."
            );
        }
    }

    public void validateObstaclePosition(int obstacleX, int obstacleY, int roverX, int roverY) {
        if (obstacleX < 0 || obstacleX >= width || obstacleY < 0 || obstacleY >= height) {
            throw new IllegalArgumentException(
                    "Obstacle position (" + obstacleX + "," + obstacleY + ") is outside the map bounds."
            );
        }
        if (obstacleX == roverX && obstacleY == roverY) {
            throw new IllegalArgumentException(
                    "Obstacle cannot be placed at the rover's starting position."
            );
        }
    }
}
