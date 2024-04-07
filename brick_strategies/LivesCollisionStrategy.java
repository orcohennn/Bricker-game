package bricker.brick_strategies;

import bricker.main.Constants;
import bricker.gameobjects.FallenHeart;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a collision strategy for handling collisions that affect player lives.
 * This strategy is responsible for creating fallen heart objects upon collision with
 * a life-affecting GameObject.
 */
public class LivesCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;
    private final ImageReader imageReader;
    private final Counter heartsCounter;
    private final CollisionStrategy basicCollisionStrategy;

    /**
     * Constructor.
     * Initializes the strategy with the provided BrickerGameManager, ImageReader, and Counter.
     * @param brickerGameManager the BrickerGameManager instance
     * @param imageReader the ImageReader instance for loading images
     * @param heartsCounter the Counter for tracking the number of hearts/lives
     * @param basicCollisionStrategy The basic collision strategy.
     */
    public LivesCollisionStrategy(BrickerGameManager brickerGameManager, ImageReader imageReader,
                                  Counter heartsCounter, CollisionStrategy basicCollisionStrategy){
        this.brickerGameManager = brickerGameManager;
        this.imageReader = imageReader;
        this.heartsCounter = heartsCounter;
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * Handles the collision event between a life-affecting GameObject and another GameObject.
     * Removes the life-affecting GameObject from the game and creates a fallen heart object at its position.
     * @param thisGameObject the life-affecting GameObject involved in the collision
     * @param otherGameObject the other GameObject involved in the collision
     */
    @Override
    public void onCollision(GameObject thisGameObject, GameObject otherGameObject) {
        this.basicCollisionStrategy.onCollision(thisGameObject, otherGameObject);
        createFallenHeart(thisGameObject.getCenter());
    }

    /*
     * Creates a fallen heart object at the specified position.
     */
    private void createFallenHeart(Vector2 brickCenter){
        Renderable heartImage =  this.imageReader.readImage(Constants.HEART_IMAGE_PATH,
                true);
        FallenHeart fallenHeart = new FallenHeart(Vector2.ZERO,
                new Vector2(Constants.UI_OBJECT_SIZE, Constants.UI_OBJECT_SIZE),
                heartImage, this.heartsCounter, this.brickerGameManager);
        fallenHeart.setCenter(brickCenter);
        this.brickerGameManager.addGameObject(fallenHeart, Layer.DEFAULT);
    }
}
