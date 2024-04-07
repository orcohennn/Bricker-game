package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;

/**
 * Represent a strategy that handles basic collision events between GameObjects.
 */
public class BasicCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;

    /**
     * Constructs a BasicCollisionStrategy.
     * @param brickerGameManager The game manager managing the game state.
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the collision event between two GameObjects.
     * @param thisGameObject  The GameObject associated with this collision strategy.
     * @param otherGameObject The other GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisGameObject, GameObject otherGameObject) {
        brickerGameManager.removeGameObject(thisGameObject, Layer.STATIC_OBJECTS);
    }
}
