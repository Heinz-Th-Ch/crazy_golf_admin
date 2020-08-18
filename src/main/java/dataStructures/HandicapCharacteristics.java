package dataStructures;

/**
 * The interface which allow to read a view to characteristics of a handicap crazy golf site.
 */
public interface HandicapCharacteristics {

    /**
     * Return the primary key, which is the number of the handicap on a crazy golf course.
     *
     * @return
     */
    Integer getPrimaryKey();

    /**
     * Returns the index of the suitcase in the data collection of suitcases, which is used on this handicap.
     *
     * @return the index
     */
    Integer getForeignKeySuitCase();

    /**
     * Returns the index - position - of the ball inside the suitcase, which is used on this handicap.
     *
     * @return the index
     */
    Integer getForeignKeyBall();

    /**
     * Returns the positioning of the ball.
     *
     * @return the position to set the ball
     */
    String getPositioning();

    /**
     * Returns the kind of cushioning, if needed.
     *
     * @return the kind of cushioning
     */
    String getCushioning();

    /**
     * Returns the kind and position of marking, if existing.
     *
     * @return the marking
     */
    String getMarking();

    /**
     * Returns the remark, if existing.
     *
     * @return the remark
     */
    String getRemark();
}
