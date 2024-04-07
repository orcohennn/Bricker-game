package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;

import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Represents class manages the game logic for a Bricker game.
 * This class utilizes various strategies for handling collisions between
 * game objects and manages user input,
 * game window dimensions, and game object counters.
 *
 * @author Ido Eyali & Or Cohen
 */
public class BrickerGameManager extends GameManager {
    private final Random random;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private final Counter bricksCounter;
    private final Counter heartsCounter;
    private Ball mainBall;
    private Vector2 windowDimension;
    private StrategyMaker strategyMaker;
    private WindowController windowController;
    private final int brickRows;
    private final int brickCols;

    /**
     * Constructor.
     *
     * @param windowTitle      the title of the game window
     * @param windowDimensions the dimensions of the game window as a Vector2 (width, height)
     * @param brickRows        the number of rows of bricks in the game
     * @param brickCols        the number of columns of bricks in the game
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int brickRows,
                              int brickCols) {
        super(windowTitle, windowDimensions);
        this.brickRows = brickRows;
        this.brickCols = brickCols;
        this.bricksCounter = new Counter(Constants.INIT_BRICKS_COUNTER_VALUE);
        this.heartsCounter = new Counter(Constants.INIT_HEARTS_NUM);
        this.random = new Random();
    }

    /**
     * Initializes the game by setting up game objects, strategies, and input listeners.
     *
     * @param imageReader      the ImageReader instance for loading images
     * @param soundReader      the SoundReader instance for loading sounds
     * @param inputListener    the UserInputListener instance for handling user input
     * @param windowController the WindowController instance for managing the game window
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimension = windowController.getWindowDimensions();
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.soundReader = soundReader;
        this.imageReader = imageReader;
        //Create background and add to game objects
        createBackground();
        //Create Ui object
        addGameObject(
                new UiManager(Vector2.ZERO, this.windowDimension, null,
                        this.heartsCounter, this, this.imageReader), Layer.UI);
        //Create strategyMaker
        this.strategyMaker = new StrategyMaker(this, this.windowController, this.imageReader,
                this.heartsCounter, this.windowDimension, this.soundReader, this.inputListener);
        //Create ball and add to game objects
        createBall();
        //Create paddle and add to game objects
        createPaddle();
        //Create Bricks
        createBricks(this.brickRows, this.brickCols);
        //Create walls and add to game objects
        createWalls();
    }

    /**
     * Updates the game state on each frame, including handling collisions, user input,
     * and game over conditions.
     *
     * @param deltaTime the time elapsed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float ballHeight = this.mainBall.getCenter().y();
        String prompt = "";
        // If there are no more bricks or the user press W, the user win the game
        if (bricksCounter.value() <= Constants.INIT_BRICKS_COUNTER_VALUE || this.inputListener.
                isKeyPressed(KeyEvent.VK_W)) {
            prompt = Constants.WIN_PROMPT;
        }
        // If the ball is out of the frame initial to the center of the frame.
        if (ballHeight > this.windowDimension.y()) {
            heartsCounter.decrement();
            setBallVelocity(mainBall);
            mainBall.setCenter(this.windowDimension.mult(Constants.HALF_FACTOR));
        }
        if (heartsCounter.value() == Constants.MIN_HEARTS) {
            prompt = Constants.LOSE_PROMPT;
        }
        if (!prompt.isEmpty()) {
            gameOverHandler(prompt);
        }
        removeOutOfRangeObjects();
    }

    /**
     * Adds a game object to the game's list of active game objects on the specified layer.
     *
     * @param gameObject      the GameObject instance to add to the game
     * @param gameObjectLayer the layer on which to add the game object
     */
    public void addGameObject(GameObject gameObject, int gameObjectLayer) {
        gameObjects().addGameObject(gameObject, gameObjectLayer);
    }

    /**
     * Removes a game object from the game's list of active game objects on the specified layer.
     * If the removed object is a brick, decrements the brick counter accordingly.
     *
     * @param gameObject      the GameObject instance to remove from the game
     * @param gameObjectLayer the layer from which to remove the game object
     */
    public void removeGameObject(GameObject gameObject, int gameObjectLayer) {
        boolean objExist = gameObjects().removeGameObject(gameObject, gameObjectLayer);
        if (gameObject.getTag().equals(Constants.BRICK_TAG) && objExist) {
            this.bricksCounter.decrement();
        }
    }

    /*
     * Handles the game over condition by displaying a prompt and allowing the player to restart the game
     * or close the game window.
     */
    private void gameOverHandler(String prompt) {
        prompt += Constants.RESTART_GAME_PROMPT;
        if (windowController.openYesNoDialog(prompt)) {
            windowController.resetGame();
            // reset the hearts and bricks num to initial number
            this.heartsCounter.reset();
            this.bricksCounter.reset();
            this.heartsCounter.increaseBy(Constants.INIT_HEARTS_NUM);
        } else {
            windowController.closeWindow();
        }
    }

    /*
     * Removes game objects that have moved out of the visible game area, excluding the main ball.
     * Objects are considered out of range if their top-left corner's y-coordinate exceeds
     * the game window's height.
     * This method iterates through the list of game objects and removes
     * those that are out of range from the game.
     */
    private void removeOutOfRangeObjects() {
        for (GameObject obj : gameObjects()) {
            if (obj.getTopLeftCorner().y() > this.windowDimension.y() &&
                    !obj.getTag().equals(Constants.MAIN_BALL_TAG)) {
                gameObjects().removeGameObject(obj, Layer.DEFAULT);
            }
        }
    }

    /*
     * Creates and adds a background object to the game.
     * The background object is created using the image specified by the constant BACKGROUND_IMAGE_PATH.
     * It is positioned in the top-left corner of the game window and covers the entire window area.
     * The coordinate space of the background object is set to CAMERA_COORDINATES.
     * The background object is added to the BACKGROUND layer for rendering.
     */
    private void createBackground() {
        Renderable backgroundImage = this.imageReader.readImage(Constants.BACKGROUND_IMAGE_PATH,
                false);
        GameObject background = new GameObject(Vector2.ZERO, this.windowDimension, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.addGameObject(background, Layer.BACKGROUND);
    }

    /*
     * Creates and adds a paddle object to the game.
     * The paddle object is created using the image specified by the constant PADDLE_IMAGE_PATH.
     * It is positioned at the bottom center of the game window with the specified width and height.
     * The paddle object is associated with an input listener for user control and
     * the game window dimensions.
     * The paddle object is tagged as the main paddle and added to the DEFAULT layer for rendering.
     */
    private void createPaddle() {
        Renderable paddleImage = this.imageReader.readImage(Constants.PADDLE_IMAGE_PATH,
                false);
        Paddle paddle = new Paddle(Vector2.ZERO,
                new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HIGH), paddleImage,
                this.inputListener, this.windowDimension);
        paddle.setCenter(new Vector2(this.windowDimension.x() * Constants.HALF_FACTOR,
                this.windowDimension.y() - Constants.PADDLE_OFFSET));
        paddle.setTag(Constants.MAIN_PADDLE_TAG);
        this.addGameObject(paddle, Layer.DEFAULT);
    }

    /*
     * Creates and adds a ball object to the game.
     * The ball object is created using the image specified by the constant BALL_IMAGE_PATH.
     * It is positioned at the center of the game window with the specified size.
     * The ball object is associated with a collision sound effect and tagged as the main ball.
     * The ball's initial velocity is set using the setBallVelocity method.
     * The ball object is added to the DEFAULT layer for rendering.
     */
    private void createBall() {
        Renderable ballImage =
                this.imageReader.readImage(Constants.BALL_IMAGE_PATH, true);
        Sound collisionSound = this.soundReader.readSound(Constants.BLOP_SOUND_PATH);
        this.mainBall = new Ball(Vector2.ZERO,
                new Vector2(Constants.BALL_SIZE, Constants.BALL_SIZE), ballImage, collisionSound);
        setBallVelocity(mainBall);
        this.mainBall.setCenter(this.windowDimension.mult(Constants.HALF_FACTOR));
        this.mainBall.setTag(Constants.MAIN_BALL_TAG);
        this.addGameObject(this.mainBall, Layer.DEFAULT);
    }

    /*
     * Sets the initial velocity of the specified ball object.
     * The method generates random velocity components within the range defined by
     * the constants BALL_VELOCITY
     * and DIRECTION_FACTOR, and assigns them to the ball's velocity vector.
     * @param ball the Ball object for which to set the initial velocity
     */
    private void setBallVelocity(Ball ball) {
        float ballVelX = Constants.BALL_VELOCITY;
        float ballVelY = Constants.BALL_VELOCITY;
        if (this.random.nextBoolean()) {
            ballVelX *= Constants.DIRECTION_FACTOR;
        }
        if (this.random.nextBoolean()) {
            ballVelY *= Constants.DIRECTION_FACTOR;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /*
     * Creates and adds wall objects to the game.
     * Three wall objects are created: left, right, and top walls.
     * The left wall spans the entire height of the game window and is positioned at the
     * left edge of the window.
     * The right wall spans the entire height of the game window and is positioned at the
     * right edge of the window.
     * The top wall spans the entire width of the game window and is positioned at the
     * top edge of the window.
     * Each wall object is tagged as a wall and added to the STATIC_OBJECTS layer for rendering.
     */
    private void createWalls() {
        GameObject leftWall = new GameObject(Vector2.ZERO,
                new Vector2(Constants.WALL_WIDTH, this.windowDimension.y()), null);
        GameObject rightWall =
                new GameObject(new Vector2(this.windowDimension.x(), Constants.MIN_COORDINATE_VALUE),
                        new Vector2(Constants.WALL_WIDTH, this.windowDimension.y()), null);
        GameObject topWall = new GameObject(Vector2.ZERO,
                new Vector2(this.windowDimension.x(), Constants.WALL_HEIGHT), null);
        leftWall.setTag(Constants.WALL_TAG);
        rightWall.setTag(Constants.WALL_TAG);
        topWall.setTag(Constants.WALL_TAG);
        this.addGameObject(topWall, Layer.STATIC_OBJECTS);
        this.addGameObject(leftWall, Layer.STATIC_OBJECTS);
        this.addGameObject(rightWall, Layer.STATIC_OBJECTS);
    }

    /*
     * Creates and adds brick objects to the game.
     * Brick objects are created in a grid layout with the specified number of rows and columns.
     * Each brick object is positioned within the game window and assigned a collision
     * strategy randomly chosen
     * from the available collision strategies.
     * The dimensions of the brick objects are calculated based on the available width of the game window
     * and the specified number of columns.
     * Brick objects are added to the STATIC_OBJECTS layer for rendering.
     */
    private void createBricks(int brickRows, int brickCols) {
        Renderable brickImage = this.imageReader.readImage(Constants.BRICK_IMAGE_PATH,
                false);
        // Compute the brick place
        float availableWidth =
                this.windowDimension.x() - (Constants.STATIC_OBJECT_SPACE + Constants.STATIC_OBJECT_SPACE);
        float brickWidth = (availableWidth - Constants.STATIC_OBJECT_SPACE * (brickCols - 1)) / (brickCols);
        float y = Constants.STATIC_OBJECT_SPACE;
        float x = Constants.STATIC_OBJECT_SPACE;
        for (int i = 0; i < brickRows; i++) {
            for (int j = 0; j < brickCols; j++) {
                CollisionStrategy selectedColStrategy = this.strategyMaker.chooseStrategy();
                Brick brick = new Brick(new Vector2(x, y), new Vector2(brickWidth, Constants.BRICK_HEIGHT),
                        brickImage, selectedColStrategy);
                this.addGameObject(brick, Layer.STATIC_OBJECTS);
                this.bricksCounter.increment();
                x += (brickWidth + Constants.STATIC_OBJECT_SPACE);
            }
            y += (Constants.BRICK_HEIGHT + Constants.STATIC_OBJECT_SPACE);
            x = Constants.STATIC_OBJECT_SPACE;
        }
    }

    /**
     * The main method to start the Bricker game.
     * This method initializes the game by creating an instance of the BrickerGameManager class
     * with specified parameters such as the game window title, window dimensions, number of brick rows,
     * and number of brick columns.
     * The number of brick rows and columns can be provided as command-line arguments. If no arguments
     * are provided, the default values specified in the Constants class are used.
     * The game is then started by invoking the run method of the BrickerGameManager instance.
     *
     * @param args an array of command-line arguments specifying the number of brick rows and columns
     */
    public static void main(String[] args) {
        int brickRows = Constants.DEFAULT_BRICK_ROWS;
        int brickCols = Constants.DEFAULT_BRICK_COLS;
        if (args.length == Constants.NUN_OF_VALID_ARGS) {
            brickRows = Integer.parseInt(args[0]);
            brickCols = Integer.parseInt(args[1]);
        }
        new BrickerGameManager(Constants.GAME_WINDOW_TITLE,
                new Vector2(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT),
                brickRows, brickCols).run();
    }
}
