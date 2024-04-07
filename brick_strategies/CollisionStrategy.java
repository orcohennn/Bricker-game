package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Represents a strategy for handling collisions between GameObjects.
 */
public interface CollisionStrategy {
    /**
     * Handles the collision event between two GameObjects.
     * @param thisGameObject The first GameObject involved in the collision.
     * @param otherGameObject The second GameObject involved in the collision.
     */
    void onCollision(GameObject thisGameObject, GameObject otherGameObject);
}
