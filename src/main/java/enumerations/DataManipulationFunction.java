package enumerations;

/**
 * This enum contains all data manipulation functions for {@link applications.CgaDataManipulationApplication}.
 */
public enum DataManipulationFunction {

    /**
     * Is the command, to leave the data manipulation loop.
     */
    BACK,

    /**
     * Deletes a single data entry, defined by primary key or identification.
     */
    DELETE,
    /**
     * Inserts a new data entry, after a test of clearness.
     */
    INSERT,
    /**
     * Select a list of data entries, defined by
     * <ul>
     *     <li>primary key - returns zero or one entry</li>
     *     <li>fully qualified identification - returns zero or one entry</li>
     *     <li>partially qualified identification - returns from zero to many entries</li>
     * </ul>
     */
    LIST,
    /**
     * Select a list of ball characteristics data entries, defined by
     * <ul>
     *     <li>primary key - returns zero or one entry</li>
     *     <li>fully qualified identification - returns zero or one entry</li>
     *     <li>partially qualified identification - returns from zero to many entries</li>
     * </ul>
     * This function is only used in all data manipulation groups without the one who for ball characteristics data.
     */
    LIST_BALLS,
    /**
     * Selects a ball characteristics data entry, defined by primary key or identification.<br>
     * This function is only used in all data manipulation groups without the one who for ball characteristics data.
     */
    PICK_BALL,
    /**
     * Selects a single data entry, defined by primary key or identification.
     */
    SELECT,
    /**
     * Updates an existing data entry, after a test of clearness.
     */
    UPDATE;

    public static String mainCommands() {
        StringBuffer buffer = new StringBuffer();
        for (DataManipulationFunction value : DataManipulationFunction.values()) {
            if (value == LIST_BALLS || value == PICK_BALL) {
                continue;
            }
            if (buffer.length() > 0) {
                buffer.append("/");
            }
            buffer.append(value);
        }
        return buffer.toString();
    }

}
