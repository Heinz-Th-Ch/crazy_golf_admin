package enumerations;

/**
 * The enum contains all relevant fields which an be invalid during the validation in this application.
 */
public enum ValidatorErrorFields {

    CORRECT_FIELD("");

    private final String fieldName;

    ValidatorErrorFields(String fieldName) {
        this.fieldName = fieldName;
    }
}
