package dataStructures;

/**
 * The interface which allow to read a view to the content of a crazy golf ball suite case.
 */
public interface ContentOfSuitCase {

    /**
     * Returns the position of a ball inside the suite case.
     *
     * @return the position
     */
    Integer getPrimaryKey();

    /**
     * Returns the index of the ball in the data collection of balls.
     *
     * @return the index
     */
    Integer getForeignKey();

}
