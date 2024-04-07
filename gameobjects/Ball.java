package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a ball object in the game.
 * Ball is a subclass of GameObject and represents a ball that interacts with other game objects.
 * It tracks the number of collisions it has encountered and handles collision events by updating
 * its velocity and playing a collision sound.
 */
public class Ball extends GameObject {

    private final Sound collisionSound;
    private int collisionCounter;

    /**
     * Constructor.
     * Initializes a Ball instance with the provided top-left corner position, dimensions, renderable,
     * and collision sound.
     * @param topLeftCorner the position of the top-left corner of the ball
     * @param dimensions the dimensions of the ball
     * @param renderable the renderable representing the ball
     * @param collisionSound the sound to be played on collision
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }

    /**
     * Handles collision events with other game objects.
     * Increments the collision counter, updates the ball's velocity based on the collision normal,
     * and plays the collision sound.
     * @param other the other game object involved in the collision
     * @param collision the collision information
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionCounter++;
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
    }

    /**
     * Retrieves the number of collisions encountered by the ball.
     * @return the number of collisions
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }

}
