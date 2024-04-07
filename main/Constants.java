package bricker.main;

/**
 * Constants used throughout the Bricker game.
 */
public class Constants {

    /**
     * Constructor.
     * private and empty, there is no option to create instance of this class.
     */
    private Constants(){}

    /**
     * The file path for the background image used in the game.
     */
    public static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";
    /**
     * The file path for the paddle image used in the game.
     */
    public static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    /**
     * The file path for the brick image used in the game.
     */
    public static final String BRICK_IMAGE_PATH = "assets/brick.png";
    /**
     * The file path for the ball image used in the game.
     */
    public static final String BALL_IMAGE_PATH = "assets/ball.png";
    /**
     * The file path for the sound effect played on collision.
     */
    public static final String BLOP_SOUND_PATH = "assets/blop_cut_silenced.wav";
    /**
     * The file path for the puck ball image used in the game.
     */
    public static final String PUCK_BALL_IMAGE_PATH = "assets/mockBall.png";
    /**
     * The file path for the heart image used in the game.
     */
    public static final String HEART_IMAGE_PATH = "assets/heart.png";
    /**
     * The tag for identifying bricks in the game.
     */
    public static final String BRICK_TAG = "Brick";
    /**
     * The tag for identifying walls in the game.
     */
    public static final String WALL_TAG = "Wall";
    /**
     * The tag for identifying the main ball in the game.
     */
    public static final String MAIN_BALL_TAG = "MainBall";
    /**
     * The tag for identifying the main paddle in the game.
     */
    public static final String MAIN_PADDLE_TAG = "MainPaddle";
    /**
     * The title of the game window.
     */
    public static final String GAME_WINDOW_TITLE = "Bricker";
    /**
     * The prompt displayed when the player wins the game.
     */
    public static final String WIN_PROMPT = "You Win! ";
    /**
     * The prompt displayed when the player loses the game.
     */
    public static final String LOSE_PROMPT = "You Lose! ";
    /**
     * The prompt displayed when the game prompts the player to restart.
     */
    public static final String RESTART_GAME_PROMPT = "Play again?";

    /**
     * The width of the game window.
     */
    public static final int WINDOW_WIDTH = 700;
    /**
     * The height of the game window.
     */
    public static final int WINDOW_HEIGHT = 500;
    /**
     * The size of the ball in pixels.
     */
    public static final int BALL_SIZE = 20;
    /**
     * The width of the paddle in pixels.
     */
    public static final int PADDLE_WIDTH = 200;
    /**
     * The height of the paddle in pixels.
     */
    public static final int PADDLE_HIGH = 20;
    /**
     * The velocity of the ball in pixels per second.
     */
    public static final int BALL_VELOCITY = 200;
    /**
     * The minimum coordinate value.
     */
    public static final int MIN_COORDINATE_VALUE = 0;
    /**
     * The initial value for the bricks counter.
     */
    public static final int INIT_BRICKS_COUNTER_VALUE = 0;
    /**
     * The width of the wall in pixels.
     */
    public static final int WALL_WIDTH = 1;
    /**
     * The height of the wall in pixels.
     */
    public static final int WALL_HEIGHT = 1;
    /**
     * The default number of rows for bricks.
     */
    public static final int DEFAULT_BRICK_ROWS = 7;
    /**
     * The default number of columns for bricks.
     */
    public static final int DEFAULT_BRICK_COLS = 8;
    /**
     * The height of a brick in pixels.
     */
    public static final int BRICK_HEIGHT = 15;
    /**
     * The space between static objects in pixels.
     */
    public static final int STATIC_OBJECT_SPACE = 2;
    /**
     * The number of valid arguments.
     */
    public static final int NUN_OF_VALID_ARGS = 2;
    /**
     * The initial number of hearts.
     */
    public static final int INIT_HEARTS_NUM = 3;
    /**
     * The offset for text position in pixels.
     */
    public static final int TEXT_POS_OFFSET = 5;
    /**
     * The size of UI objects in pixels.
     */
    public static final int UI_OBJECT_SIZE = 20;

    /**
     * The boundary value for the green color.
     */
    public static final int GREEN_BOUNDERY = 3;
    /**
     * The boundary value for the yellow color.
     */
    public static final int YELLOW_BOUNDERY = 2;
    /**
     * The minimum number of hearts.
     */
    public static final int MIN_HEARTS = 0;
    /**
     * The maximum number of collisions for a bonus paddle.
     */
    public static final int BONUS_PADDLE_MAX_COLLISION = 4;
    /**
     * The initial number of collisions.
     */
    public static final int INIT_COLLISIONS_NUM = 0;
    /**
     * The initial number of bonus paddles.
     */
    public static final int INIT_NUM_OF_BONUS_PADDLE = 0;
    /**
     * The maximum number of lives.
     */
    public static final int NUM_MAX_LIVES = 4;
    /**
     * The total number of collision strategies.
     */
    public static final int NUM_OF_STRATEGIES = 6;
    /**
     * The index for the basic collision strategy.
     */
    public static final int BASIC_COLLISION_STRATEGY_INDEX = 0;
    /**
     * The index for the change camera collision strategy.
     */
    public static final int CHANGE_CAMERA_STRATEGY_INDEX = 1;
    /**
     * The index for the lives collision strategy.
     */
    public static final int LIVES_COLLISION_STRATEGY_INDEX = 2;
    /**
     * The index for the bonus paddle collision strategy.
     */
    public static final int BONUS_PADDLE_STRATEGY_INDEX = 3;
    /**
     * The index for the puck collision strategy.
     */
    public static final int PUCK_COLLISION_STRATEGY_INDEX = 4;
    /**
     * The number of collisions required to reset the camera.
     */
    public static final int RESET_CAMERA_COLLISION_NUM = 4;
    /**
     * The index for the double collision strategy.
     */
    public static final int DOUBLE_COLLISION_STRATEGY_INDEX = 5;

    /**
     * The factor by which the camera is widened.
     */
    public static final float WIDEN_CAMERA_FACTOR = 1.2f;
    /**
     * The velocity of fallen hearts.
     */
    public static final float FALLEN_HEART_VELOCITY = 100;
    /**
     * The offset for the paddle.
     */
    public static final float PADDLE_OFFSET = 30;
    /**
     * The factor for half.
     */
    public static final float HALF_FACTOR = 0.5f;
    /**
     * The factor for direction.
     */
    public static final float DIRECTION_FACTOR = -1;
    /**
     * The factor for puck size.
     */
    public static final float PUCK_SIZE_FACTOR = 0.75f;
    /**
     * The movement speed.
     */
    public static final float MOVEMENT_SPEED = 300;
}
