package bricker.gameobjects;

import bricker.main.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a fallen heart object in the game.
 * FallenHeart is a subclass of GameObject and represents a heart that has fallen from the UI.
 * When collided with the main paddle, it increments the hearts counter and removes itself from the game.
 */
public class FallenHeart extends GameObject {
    private final Counter heartsCounter;
    private final BrickerGameManager brickerGameManager;

    /**
     * Constructor.
     * Initializes a FallenHeart instance with the provided top-left corner position,
     * dimensions, renderable,
     * hearts counter, and BrickerGameManager instance.
     * @param topLeftCorner the top-left corner position of the fallen heart
     * @param dimensions the dimensions of the fallen heart
     * @param renderable the renderable representing the fallen heart
     * @param heartsCounter the counter for tracking the number of hearts
     * @param brickerGameManager the instance of BrickerGameManager associated with the fallen heart
     */
    public FallenHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       Counter heartsCounter, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.heartsCounter = heartsCounter;
        this.brickerGameManager = brickerGameManager;
        this.setVelocity(new Vector2(this.getVelocity().x(), Constants.FALLEN_HEART_VELOCITY));
    }

    /**
     * Handles the collision event when the fallen heart collides with another GameObject.
     * When collided with the main paddle,
     * increments the hearts counter and removes the fallen heart from the game.
     * @param other the GameObject with which the fallen heart collided
     * @param collision the collision information
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(this.heartsCounter.value() < Constants.NUM_MAX_LIVES){
            this.heartsCounter.increment();
        }
        this.brickerGameManager.removeGameObject(this, Layer.DEFAULT);
    }

    /**
     * Determines whether the fallen heart should collide with another GameObject.
     * Fallen hearts should only collide with the main paddle.
     * @param other the GameObject to check collision with
     * @return true if the fallen heart should collide with the specified GameObject, false otherwise
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(Constants.MAIN_PADDLE_TAG);
    }
}
