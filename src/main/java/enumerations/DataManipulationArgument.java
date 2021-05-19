package enumerations;

/**
 * This enum contains all valid arguments of {@link applications.CgaDataManipulationApplication}.
 */
public enum DataManipulationArgument {

    /**
     * all data will be saved at least at the end of the application.
     */
    AUTO_SAVE,
    /**
     * all data will be saved depending of a response after a save question.
     */
    MANUALLY_SAVE

}
