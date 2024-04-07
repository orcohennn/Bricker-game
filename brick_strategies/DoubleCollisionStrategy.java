package bricker.brick_strategies;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.util.Counter;

import java.util.Random;

/**
 * Represents a collision strategy for handling double collisions.
 * This strategy randomly selects two collision strategies from the provided array of strategies
 * and executes them in sequence when a collision occurs. Additionally, in the case where the index
 * of this strategy is randomly chosen, it selects a third strategy to execute.
 */
public class DoubleCollisionStrategy implements CollisionStrategy{
    private final CollisionStrategy[] strategies;
    private final Random random;

    /**
     * Constructor.
     * Initializes the strategy with the provided array of collision strategies and a counter
     * for tracking the number of bricks.
     * @param strategies the array of collision strategies to choose from
     */
    public DoubleCollisionStrategy(CollisionStrategy[] strategies) {
        this.random = new Random();
        this.strategies = strategies;
    }

    /**
     * Handles the collision event between two GameObjects.
     * Randomly selects two collision strategies from the array and executes them in sequence.
     * If the index of this strategy is randomly chosen, it selects a third strategy to execute.
     * @param thisGameObject the first GameObject involved in the collision
     * @param otherGameObject the second GameObject involved in the collision
     */
    @Override
    public void onCollision(GameObject thisGameObject, GameObject otherGameObject) {
        int firstStrategyNum, secondStrategyNum;
        int thirdStrategyNum = Constants.NUM_OF_STRATEGIES;
        firstStrategyNum = random.nextInt(Constants.NUM_OF_STRATEGIES);
        if (firstStrategyNum == Constants.DOUBLE_COLLISION_STRATEGY_INDEX){
            firstStrategyNum = random.nextInt(Constants.DOUBLE_COLLISION_STRATEGY_INDEX);
            secondStrategyNum = random.nextInt(Constants.DOUBLE_COLLISION_STRATEGY_INDEX);
            thirdStrategyNum = random.nextInt(Constants.DOUBLE_COLLISION_STRATEGY_INDEX);
        }
        else{
            secondStrategyNum = random.nextInt(Constants.NUM_OF_STRATEGIES);
        }
        if (secondStrategyNum == Constants.DOUBLE_COLLISION_STRATEGY_INDEX){
            secondStrategyNum = random.nextInt(Constants.DOUBLE_COLLISION_STRATEGY_INDEX);
            thirdStrategyNum = random.nextInt(Constants.DOUBLE_COLLISION_STRATEGY_INDEX);
        }
        this.strategies[firstStrategyNum].onCollision(thisGameObject, otherGameObject);
        this.strategies[secondStrategyNum].onCollision(thisGameObject, otherGameObject);
        if (thirdStrategyNum != Constants.NUM_OF_STRATEGIES){
            this.strategies[thirdStrategyNum].onCollision(thisGameObject, otherGameObject);
        }
    }
}
