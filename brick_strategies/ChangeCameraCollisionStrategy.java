package bricker.brick_strategies;

import bricker.main.Constants;
import bricker.gameobjects.Ball;
import bricker.gameobjects.CameraChanger;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

/**
 * Represents a strategy, that's when there is collision with brick that's contains this strategy,
 * trigger a change in the camera.
 */
public class ChangeCameraCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;
    private final WindowController windowController;
    private final CollisionStrategy basicCollisionStrategy;

    /**
     * Constructor.
     * @param brickerGameManager The game manager managing the game state.
     * @param windowController    The window controller managing the game window.
     * @param basicCollisionStrategy The basic collision strategy.
     */
    public ChangeCameraCollisionStrategy(BrickerGameManager brickerGameManager,
                                         WindowController windowController,
                                         CollisionStrategy basicCollisionStrategy) {
        this.brickerGameManager = brickerGameManager;
        this.windowController = windowController;
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * Handles the collision event between two GameObjects.
     * @param thisGameObject    The GameObject associated with this collision strategy.
     * @param otherGameObject   The other GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisGameObject, GameObject otherGameObject) {
        this.basicCollisionStrategy.onCollision(thisGameObject, otherGameObject);
        // Checks if collide with main ball and if the camera is null
        if (this.brickerGameManager.camera() == null &&
                otherGameObject.getTag().equals(Constants.MAIN_BALL_TAG)){
            CameraChanger cameraChanger = new CameraChanger(Vector2.ZERO, Vector2.ZERO, null,
                    (Ball) otherGameObject, this.brickerGameManager);
            this.brickerGameManager.addGameObject(cameraChanger, Layer.STATIC_OBJECTS);
            this.brickerGameManager.setCamera(
                    new Camera(
                            otherGameObject,
                            Vector2.ZERO,
                            windowController.getWindowDimensions().mult(Constants.WIDEN_CAMERA_FACTOR),
                            windowController.getWindowDimensions()
                    )
            );

        }
    }
}
