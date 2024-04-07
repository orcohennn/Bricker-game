package bricker.gameobjects;

import bricker.main.Constants;
import bricker.brick_strategies.BonusPaddleCollisionStrategy;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a bonus paddle object in the game.
 * BonusPaddle extends the Paddle class and represents a special paddle that grants bonuses to the player.
 * It tracks the number of collisions it has with other game objects and removes itself after a certain
 * number of collisions, invoking the associated bonus paddle collision strategy.
 */
public class BonusPaddle extends Paddle{
    private final BrickerGameManager brickerGameManager;
    private final BonusPaddleCollisionStrategy bonusPaddleCollisionStrategy;
    private int numOfCollisions;

    /**
     * Constructor.
     * Initializes a BonusPaddle instance with the provided top-left corner position,
     * dimensions, renderable,
     * input listener, window dimensions, BrickerGameManager instance, bonus paddle collision strategy, and
     * number of collisions.
     * @param topLeftCorner the position of the top-left corner of the paddle
     * @param dimensions the dimensions of the paddle
     * @param renderable the renderable representing the paddle
     * @param inputListener the input listener for detecting user input
     * @param windowDimension the dimensions of the game window
     * @param brickerGameManager the instance of BrickerGameManager associated with the bonus paddle
     * @param bonusPaddleCollisionStrategy the collision strategy for the bonus paddle
     */
    public BonusPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener, Vector2 windowDimension,
                       BrickerGameManager brickerGameManager,
                       BonusPaddleCollisionStrategy bonusPaddleCollisionStrategy) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimension);
        this.brickerGameManager = brickerGameManager;
        this.bonusPaddleCollisionStrategy = bonusPaddleCollisionStrategy;
        this.numOfCollisions = Constants.INIT_COLLISIONS_NUM;
    }

    /**
     * Handles collision events with other game objects.
     * Increments the number of collisions when colliding with objects other than walls.
     * @param other the other game object involved in the collision
     * @param collision the collision information
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(!other.getTag().equals(Constants.WALL_TAG)){
            this.numOfCollisions++;
        }
    }

    /**
     * Updates the bonus paddle's state.
     * Removes the bonus paddle from the game when it reaches the maximum number of collisions,
     * invoking the associated bonus paddle collision strategy.
     * @param deltaTime the time elapsed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.numOfCollisions == Constants.BONUS_PADDLE_MAX_COLLISION){
            this.brickerGameManager.removeGameObject(this, Layer.DEFAULT);
            this.bonusPaddleCollisionStrategy.decrementNumOfBonusPaddles();
        }
    }
}
