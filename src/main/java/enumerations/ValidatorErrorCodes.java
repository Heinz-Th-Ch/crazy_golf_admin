package enumerations;

/**
 * The enum contains all relevant validation error code in this application.
 */
public enum ValidatorErrorCodes {

    OKAY("000"),

    FIELD_NOT_DEFINED("001"),
    EMPTY_FIELD("002"),
    INVALID_CONTENT("003"),
    CONTENT_TOO_LOW("004"),
    CONTENT_TOO_HIGH("005"),
    INVALID_POST_CODE_SYNTAX("006"),
    INVALID_COUNTRY_CODE("007"),
    INVALID_POST_CODE("008"),
    INVALID_CONTENT_AT_DATA_DEPENDENCY("009"),
    LIST_NOT_DEFINED("010"),
    EMPTY_LIST("011"),
    ;

    private final String errorCode;

    ValidatorErrorCodes(String errorCode) {
        this.errorCode = errorCode;
    }
}
