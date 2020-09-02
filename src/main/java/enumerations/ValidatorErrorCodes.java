package enumerations;

/**
 * The enum contains all relevant validation error code in this application.
 */
public enum ValidatorErrorCodes {

    OKAY("000"),

    EMPTY_FIELD("001"),
    INVALID_CONTENT("002");

    private final String errorCode;

    ValidatorErrorCodes(String errorCode) {
        this.errorCode = errorCode;
    }
}
