package enumerations;

/**
 * This enum contains the function codes of the {@link applications.CgaDataImportApplication}.
 */
public enum DataImportFunction {

    /**
     * To import only when the target list is empty.
     */
    INITIAL,

    /**
     * To import always regardless if the target list is empty or not.
     */
    OVERWRITE

}
