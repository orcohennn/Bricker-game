package bricker.gameobjects;

import bricker.main.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a camera changer object in the game.
 * CameraChanger is a subclass of GameObject and represents an object that
 * changes the camera's focus in the game.
 * It monitors the collision counter of the main ball and resets the
 * camera focus after a certain number of collisions.
 */
public class CameraChanger extends GameObject {
    private final Ball mainBall;
    private final BrickerGameManager brickerGameManager;
    private final int startBallCollisionCounter;


    /**
     * Constructor.
     * Initializes a CameraChanger instance with the provided top-left corner position,
     * dimensions, renderable,
     * main ball, and BrickerGameManager instance.
     * @param topLeftCorner the top-left corner position of the camera changer
     * @param dimensions the dimensions of the camera changer
     * @param renderable the renderable representing the camera changer
     * @param mainBall the main ball object in the game
     * @param brickerGameManager the instance of BrickerGameManager associated with the camera changer
     */
    public CameraChanger(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                         Ball mainBall, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.startBallCollisionCounter = mainBall.getCollisionCounter();
        this.mainBall = mainBall;
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Updates the CameraChanger object.
     * Monitors the collision counter of the main ball and
     * resets the camera focus if the number of collisions
     * exceeds a certain threshold.
     * @param deltaTime the time elapsed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.startBallCollisionCounter + Constants.RESET_CAMERA_COLLISION_NUM <=
                this.mainBall.getCollisionCounter()){
            this.brickerGameManager.setCamera(null);
            this.brickerGameManager.removeGameObject(this, Layer.STATIC_OBJECTS);
        }
    }
}
