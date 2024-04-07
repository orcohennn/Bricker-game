package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * Represents a paddle object in a game. The paddle can be controlled by user input
 * to move left and right within the game window.
 */
public class Paddle extends GameObject {
    private final UserInputListener inputListener;
    private final Vector2 windowDimension;

    /**
     * Constructor.
     * Initializes a paddle object with the provided top-left corner position, dimensions, renderable,
     * user input listener, and window dimensions.
     * @param topLeftCorner the top-left corner position of the paddle
     * @param dimensions the dimensions of the paddle
     * @param renderable the renderable component of the paddle
     * @param inputListener the user input listener for controlling the paddle
     * @param windowDimension the dimensions of the game window
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimension) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimension = windowDimension;
    }

    /**
     * Updates the state of the paddle.
     * @param deltaTime  The time elapsed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        paddleMovementManage();
    }

    /*
     * Manages the movement of the paddle based on user input.
     */
    private void paddleMovementManage() {
        float paddleWidth = this.getDimensions().x();
        Vector2 movementDir = Vector2.ZERO;
        Vector2 topLeftCorner = getTopLeftCorner();
        // Check for left arrow key press
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);
        }
        // Check for right arrow key press
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        // Set velocity based on movement direction and speed
        setVelocity(movementDir.mult(Constants.MOVEMENT_SPEED));
        // Ensure paddle stays within the bounds of the window
        if (topLeftCorner.x() < Constants.MIN_COORDINATE_VALUE){
            setTopLeftCorner(new Vector2(Constants.MIN_COORDINATE_VALUE, topLeftCorner.y()));
        }
        if (windowDimension.x() < topLeftCorner.x() + paddleWidth){
            setTopLeftCorner(new Vector2(windowDimension.x() - paddleWidth, topLeftCorner.y()));
        }
    }
}
