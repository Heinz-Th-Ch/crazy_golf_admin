package dataStructures;

import enumerations.Hardness;

/**
 * The interface which allow to read a view to characteristics of a crazy golf ball.
 */
public interface BallCharacteristics {

    /**
     * Returns the index of the ball in the data collection.
     *
     * @return the index
     */
    Integer getPrimaryKey();

    /**
     * Returns the identifier respectively the name of the ball.
     *
     * @return the name
     */
    String getIdentifier();

    /**
     * Returns the description of a crazy golf ball.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Returns the hardness of a crazy golf ball.
     *
     * @return the hardness
     */
    Hardness getHardness();

    /**
     * Returns the up throw of a crazy golf ball.
     *
     * @return the up throw
     */
    Integer getUpThrow();

    /**
     * Returns the weight of a crazy golf ball.
     *
     * @return the weight
     */
    Integer getWeight();

    /**
     * Returns the angle factor of a crazy golf ball.
     *
     * @return the angle factor
     */
    Double getAngleFactor();

    /**
     * Returns the comment of a crazy golf ball.
     *
     * @return the comment
     */
    String getComment();

}
