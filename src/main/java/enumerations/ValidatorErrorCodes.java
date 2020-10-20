package enumerations;

/**
 * The enum contains all relevant validation error code in this application.
 */
public enum ValidatorErrorCodes {

    OKAY("000"),

    CONTENT_TOO_HIGH("005"),
    CONTENT_TOO_LOW("004"),
    EMPTY_FIELD("002"),
    EMPTY_LIST("011"),
    FIELD_NOT_DEFINED("001"),
    INVALID_CONTENT("003"),
    INVALID_CONTENT_AT_DATA_DEPENDENCY("009"),
    INVALID_COUNTRY_CODE("007"),
    INVALID_POST_CODE("008"),
    INVALID_POST_CODE_SYNTAX("006"),
    LIST_NOT_DEFINED("010"),
    ;

    private final String errorCode;

    ValidatorErrorCodes(String errorCode) {
        this.errorCode = errorCode;
    }
}
