package bricker.gameobjects;

import bricker.main.Constants;
import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a brick object in the game.
 * Brick is a subclass of GameObject and represents a brick that can be destroyed by collision.
 * It contains a collision strategy that defines its behavior upon collision with other objects.
 */
public class Brick extends GameObject{
    private final CollisionStrategy collisionStrategy;

    /**
     * Constructor.
     * Initializes a Brick instance with the provided top-left corner position, dimensions, renderable,
     * and collision strategy.
     * @param topLeftCorner the top-left corner position of the brick
     * @param dimensions the dimensions of the brick
     * @param renderable the renderable representing the brick
     * @param collisionStrategy the collision strategy defining the behavior of the brick upon collision
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.setTag(Constants.BRICK_TAG);
    }

    /**
     * Handles collision events with other game objects.
     * Invokes the collision strategy associated with the brick upon collision with another game object.
     * @param other the other game object involved in the collision
     * @param collision the collision information
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
    }
}
