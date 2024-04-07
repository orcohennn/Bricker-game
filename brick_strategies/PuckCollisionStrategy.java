package bricker.brick_strategies;

import bricker.main.Constants;
import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * Represents a collision strategy for handling collisions with making puck objects.
 * This strategy is responsible for creating new puck balls upon collision with a puck object.
 */
public class PuckCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager brickerGameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final CollisionStrategy basicCollisionStrategy;
    private Random random;

    /**
     * Constructor.
     * Initializes the strategy with the provided BrickerGameManager, ImageReader, and SoundReader.
     * @param brickerGameManager the BrickerGameManager instance
     * @param imageReader the ImageReader instance for loading images
     * @param soundReader the SoundReader instance for loading sounds
     * @param basicCollisionStrategy The basic collision strategy.
     */
    public PuckCollisionStrategy(BrickerGameManager brickerGameManager,
                                 ImageReader imageReader, SoundReader soundReader ,
                                 CollisionStrategy basicCollisionStrategy){
        this.brickerGameManager = brickerGameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.random = new Random();
    }

    /**
     * Handles the collision event between a puck object and another game object.
     * Removes the puck object from the game, and creates two new puck balls at its position.
     * @param thisGameObject the puck GameObject involved in the collision
     * @param otherGameObject the other GameObject involved in the collision
     */
    @Override
    public void onCollision(GameObject thisGameObject, GameObject otherGameObject) {
        basicCollisionStrategy.onCollision(thisGameObject, otherGameObject);
        createPuckBall(thisGameObject.getCenter());
        createPuckBall(thisGameObject.getCenter());
    }

    /*
     * Sets the velocity of the puck ball.
     */
    private void setPuckBallVelocity(GameObject puckBall) {
        double angle = this.random.nextDouble() * Math.PI;
        float velocityX = (float)Math.cos(angle) * Constants.BALL_VELOCITY;
        float velocityY = (float)Math.sin(angle) * Constants.BALL_VELOCITY;
        puckBall.setVelocity(new Vector2(velocityX, velocityY));
    }

    /*
     * Creates a new puck ball at the specified position.
     */
    private void createPuckBall(Vector2 brickCenter) {
        Renderable ballImage =
                this.imageReader.readImage(Constants.PUCK_BALL_IMAGE_PATH, true);
        Sound collisionSound = this.soundReader.readSound(Constants.BLOP_SOUND_PATH);
        GameObject puckBall =
                new Ball(Vector2.ZERO, new Vector2(Constants.BALL_SIZE * Constants.PUCK_SIZE_FACTOR,
                Constants.BALL_SIZE * Constants.PUCK_SIZE_FACTOR), ballImage, collisionSound);
        puckBall.setCenter(brickCenter);
        setPuckBallVelocity(puckBall);
        this.brickerGameManager.addGameObject(puckBall, Layer.DEFAULT);
    }
}
