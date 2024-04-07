package bricker.brick_strategies;

import bricker.main.Constants;
import bricker.gameobjects.BonusPaddle;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a strategy handles collisions that trigger the creation of a bonus paddle.
 */
public class BonusPaddleCollisionStrategy implements CollisionStrategy{
    private final Vector2 windowDimension;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final CollisionStrategy basicCollisionStrategy;
    private int numOfBonusPaddles;
    private final BrickerGameManager brickerGameManager;

    /**
     * Constructor.
     * @param windowDimension    The dimensions of the game window.
     * @param imageReader        The image reader used to load images.
     * @param inputListener      The input listener for detecting user input.
     * @param brickerGameManager The game manager managing the game state.
     * @param basicCollisionStrategy The basic collision strategy.
     */
    public BonusPaddleCollisionStrategy(Vector2 windowDimension,
                                        ImageReader imageReader, UserInputListener inputListener,
                                        BrickerGameManager brickerGameManager,
                                        CollisionStrategy basicCollisionStrategy) {
        this.windowDimension = windowDimension;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.numOfBonusPaddles = Constants.INIT_NUM_OF_BONUS_PADDLE;
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the collision event between two GameObjects.
     * @param thisGameObject  The GameObject associated with this collision strategy.
     * @param otherGameObject The other GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisGameObject, GameObject otherGameObject) {
        this.basicCollisionStrategy.onCollision(thisGameObject, otherGameObject);
        if(this.numOfBonusPaddles == Constants.INIT_NUM_OF_BONUS_PADDLE){
            createBonusPaddle();
            this.numOfBonusPaddles++;
        }
    }

    /**
     * Decrements the number of bonus paddles.
     */
    public void decrementNumOfBonusPaddles(){
        this.numOfBonusPaddles--;
    }

    /*
     * Creates a new bonus paddle GameObject and adds it to the game.
     */
    private void createBonusPaddle() {
        Vector2 bonusPaddlePosition = new Vector2(this.windowDimension.x() * Constants.HALF_FACTOR,
                this.windowDimension.y() * Constants.HALF_FACTOR);
        Renderable bonusPaddleImage = imageReader.readImage(Constants.PADDLE_IMAGE_PATH,
                false);
        BonusPaddle bonusPaddle = new BonusPaddle(bonusPaddlePosition, new Vector2(Constants.PADDLE_WIDTH,
                Constants.PADDLE_HIGH),bonusPaddleImage  , this.inputListener, this.windowDimension,
                this.brickerGameManager, this);
        this.brickerGameManager.addGameObject(bonusPaddle, Layer.DEFAULT);
    }


}
