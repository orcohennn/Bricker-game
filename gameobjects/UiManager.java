package bricker.gameobjects;

import bricker.main.Constants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Stack;

/**
 * Represents UiManager class.
 * This class extends the GameObject class and manages UI elements such as hearts display.
 * It tracks the number of hearts remaining, updates the UI accordingly, and handles the display of hearts.
 * The constructor initializes the UiManager with the provided parameters and sets up the hearts counter,
 * heart image, and initializes the hearts stack. It also creates a text renderable representing the
 * current number of hearts and adds it to the game's UI layer.
 */
public class UiManager extends GameObject {

    private final Counter heartsCounter;
    private final BrickerGameManager brickerGameManager;
    private final Renderable heartImage;
    private final Vector2 dimensions;
    private final TextRenderable numHeartsText;
    private final Stack<GameObject> heartsStack;
    private int nextHeartsPlace;

    /**
     * Constructor.
     * Initializes the UiManager with the provided top-left corner position, dimensions,
     * renderable, hearts counter,
     * BrickerGameManager instance, and image reader.
     * The constructor sets up the hearts counter, heart image, and initializes the hearts stack.
     * It also creates a text renderable representing the current number of
     * hearts and adds it to the game's UI layer.
     * @param topLeftCorner the top-left corner position of the UI manager
     * @param dimensions the dimensions of the UI manager
     * @param renderable the renderable component of the UI manager
     * @param heartsCounter the counter for tracking the number of hearts
     * @param brickerGameManager the instance of BrickerGameManager associated with the UI manager
     * @param imageReader the image reader used to load the heart image
     */
    public UiManager(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                     Counter heartsCounter,
                     BrickerGameManager brickerGameManager, ImageReader imageReader) {
        super(topLeftCorner, dimensions, renderable);
        this.heartsCounter = heartsCounter;
        this.numHeartsText = new TextRenderable(String.valueOf(heartsCounter.value()));
        this.brickerGameManager = brickerGameManager;
        this.heartsStack = new Stack<>();
        this.dimensions = dimensions;
        this.heartImage = imageReader.readImage(Constants.HEART_IMAGE_PATH,
                true);
        GameObject heartsTextGameObject = new GameObject(new Vector2(Constants.TEXT_POS_OFFSET,
                dimensions.y() - Constants.UI_OBJECT_SIZE - Constants.TEXT_POS_OFFSET) ,
                new Vector2(Constants.UI_OBJECT_SIZE, Constants.UI_OBJECT_SIZE), numHeartsText);
        brickerGameManager.addGameObject(heartsTextGameObject, Layer.UI);
        initializeHearts();
    }

    /**
     * Updates the UiManager, including the hearts display and text color.
     * This method overrides the update method from the GameObject class and is
     * responsible for updating
     * the UI manager based on changes in the game state.
     * It checks for changes in the number of hearts and updates the
     * hearts display and text color accordingly.
     * Additionally, it ensures that the hearts stack in the UI remains
     * synchronized with the hearts counter.
     * @param deltaTime the time elapsed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // Check if the number of hearts change and update the text color accordingly
        heartsColorSwitch(this.heartsCounter.value());
        // Check if the number of hearts change and update the hearts stack accordingly
        if (heartsStack.size() > this.heartsCounter.value()){
            brickerGameManager.removeGameObject(heartsStack.pop(), Layer.UI);
            this.nextHeartsPlace -= Constants.UI_OBJECT_SIZE + Constants.TEXT_POS_OFFSET;
        }
        if (heartsStack.size() < this.heartsCounter.value()){
            addNewHeart();
        }
        this.numHeartsText.setString(String.valueOf(heartsCounter.value()));
    }

    /*
     * Initializes the hearts display.
     * This method initializes the hearts display by adding heart objects to
     * the UI layer based on the initial
     * number of hearts specified by the hearts counter. It iterates through the number of hearts and adds
     * heart objects using the addNewHeart method.
     */
    private void initializeHearts() {
        this.nextHeartsPlace = Constants.UI_OBJECT_SIZE + Constants.TEXT_POS_OFFSET;
        for (int i = 0; i < this.heartsCounter.value(); i++) {
            addNewHeart();
        }
    }

    /*
     * Adds a new heart object to the hearts display.
     * This method adds a new heart object to the hearts display on the UI layer.
     * It creates a new GameObject representing the heart at the specified position,
     * using the heart image.
     * The new heart object is pushed onto the hearts stack and added to the UI
     * layer via the BrickerGameManager.
     * This method ensures that the maximum number of lives is not exceeded
     * before adding a new heart.
     */
    private void addNewHeart() {
        if(this.heartsCounter.value() <= Constants.NUM_MAX_LIVES){
            GameObject newHeart = new GameObject(new Vector2(this.nextHeartsPlace, this.dimensions.y() -
                    Constants.UI_OBJECT_SIZE - Constants.TEXT_POS_OFFSET),
                    new Vector2(Constants.UI_OBJECT_SIZE, Constants.UI_OBJECT_SIZE), this.heartImage);
            this.heartsStack.push(newHeart);
            this.brickerGameManager.addGameObject(newHeart, Layer.UI);
            this.nextHeartsPlace += Constants.UI_OBJECT_SIZE + Constants.TEXT_POS_OFFSET;
        }
    }

    /*
     * Switches the color of the hearts text based on the number of hearts.
     * This method changes the color of the hearts text based on the current number of hearts.
     * If the number of hearts is equal to or above a certain threshold, the text color is set to green.
     * If the number of hearts is equal to another threshold, the text color is set to yellow.
     * Otherwise, the text color is set to red.
     */
    private void heartsColorSwitch(int heartsCounterValue) {
        if (heartsCounterValue >= Constants.GREEN_BOUNDERY) {

            this.numHeartsText.setColor(Color.GREEN);
                return;
        }
        if (heartsCounterValue == Constants.YELLOW_BOUNDERY) {
            this.numHeartsText.setColor(Color.YELLOW);
        }
        else {
            this.numHeartsText.setColor(Color.RED);
        }
    }
}

