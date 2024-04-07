package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Arrays;
import java.util.Random;

/**
 * Represents strategy maker class handles the creation and selection of collision strategies for a game.
 * Collision strategies define how game objects interact when they collide.
 */
public class StrategyMaker {
    private final BrickerGameManager brickerGameManager;
    private final WindowController windowController;
    private final ImageReader imageReader;
    private final Counter heartsCounter;
    private final Vector2 windowDimension;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final Random random;
    private final CollisionStrategy[] collisionStrategies;

    /**
     * Constructor.
     * @param brickerGameManager The game manager.
     * @param windowController The window controller.
     * @param imageReader The image reader.
     * @param heartsCounter The counter for player hearts/lives.
     * @param windowDimension The dimensions of the game window.
     * @param soundReader The sound reader.
     * @param inputListener The user input listener.
     */
    public StrategyMaker(BrickerGameManager brickerGameManager, WindowController windowController,
                  ImageReader imageReader, Counter heartsCounter, Vector2 windowDimension,
                  SoundReader soundReader, UserInputListener inputListener){
        this.random = new Random();
        this.windowController = windowController;
        this.imageReader = imageReader;
        this.heartsCounter = heartsCounter;
        this.windowDimension = windowDimension;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.collisionStrategies = new CollisionStrategy[Constants.NUM_OF_STRATEGIES];
        this.brickerGameManager = brickerGameManager;
        initCollisionStrategies();
    }

    /**
     * Randomly selects and returns a collision strategy.
     * 1/2 unique or basic, then for any basic 1/10 (1/2 * 1/5).
     * @return A collision strategy.
     */
    public CollisionStrategy chooseStrategy(){
        if (random.nextBoolean()) {
            return this.collisionStrategies[Constants.BASIC_COLLISION_STRATEGY_INDEX];
        }
        else { // return unique strategy
            return this.collisionStrategies[random.nextInt(Constants.CHANGE_CAMERA_STRATEGY_INDEX,
                    Constants.NUM_OF_STRATEGIES)];
        }
    }

    /*
     * Initializes all collision strategies and insert to the array.
     */
    private void initCollisionStrategies() {
        this.collisionStrategies[Constants.BASIC_COLLISION_STRATEGY_INDEX] =
                new BasicCollisionStrategy(this.brickerGameManager);

        this.collisionStrategies[Constants.CHANGE_CAMERA_STRATEGY_INDEX] =
                new ChangeCameraCollisionStrategy(this.brickerGameManager,
                        this.windowController,
                        this.collisionStrategies[Constants.BASIC_COLLISION_STRATEGY_INDEX]);

        this.collisionStrategies[Constants.LIVES_COLLISION_STRATEGY_INDEX] =
                new LivesCollisionStrategy(this.brickerGameManager,
                        this.imageReader, this.heartsCounter,
                        this.collisionStrategies[Constants.BASIC_COLLISION_STRATEGY_INDEX]);

        this.collisionStrategies[Constants.BONUS_PADDLE_STRATEGY_INDEX] = new
                BonusPaddleCollisionStrategy(this.windowDimension, this.imageReader,
                this.inputListener, this.brickerGameManager,
                this.collisionStrategies[Constants.BASIC_COLLISION_STRATEGY_INDEX]);

        this.collisionStrategies[Constants.PUCK_COLLISION_STRATEGY_INDEX] =
                new PuckCollisionStrategy(this.brickerGameManager, this.imageReader,
                        this.soundReader,
                        this.collisionStrategies[Constants.BASIC_COLLISION_STRATEGY_INDEX]);

        this.collisionStrategies[Constants.DOUBLE_COLLISION_STRATEGY_INDEX] =
                new DoubleCollisionStrategy(Arrays.copyOfRange(this.collisionStrategies,
                        Constants.BASIC_COLLISION_STRATEGY_INDEX,
                        Constants.DOUBLE_COLLISION_STRATEGY_INDEX));
    }
}
