package validators;

import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static enumerations.ValidatorErrorCodes.*;
import static enumerations.ValidatorErrorFields.PRIMARY_KEY;

/**
 * This abstract implementation of {@link Validator} is used to validate common fields of several data structure
 * classes.
 */
public abstract class AbstractCharacteristicsValidator {

    /**
     * Validation of a string value. It is mandatory only.
     *
     * @param results
     * @param stringValue
     * @param errorField
     */
    protected void validateMandatory(List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results,
                                  String stringValue,
                                  ValidatorErrorFields errorField) {
        if (stringValue == null) {
            results.add(Pair.of(FIELD_NOT_DEFINED, errorField));
        }
    }

    /**
     * Validation of a string value. It is mandatory only and must not be empty.
     *
     * @param results
     * @param stringValue
     * @param errorField
     */
    protected void validateMandatoryAndContent(List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results,
                                            String stringValue,
                                            ValidatorErrorFields errorField) {
        if (stringValue == null) {
            results.add(Pair.of(FIELD_NOT_DEFINED, errorField));
            return;
        }
        if (stringValue.isEmpty()) {
            results.add(Pair.of(EMPTY_FIELD, errorField));
        }
    }

    /**
     * Validation of a integer value. It is mandatory and must not be lower a defined value.
     *
     * @param results
     * @param integerValue
     * @param lowestValue
     * @param errorField
     */
    protected void validateMandatoryAndNotLowerDefinedValue(List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results,
                                                         Integer integerValue,
                                                         Integer lowestValue,
                                                         ValidatorErrorFields errorField) {
        if (integerValue == null) {
            results.add(Pair.of(FIELD_NOT_DEFINED, errorField));
            return;
        }
        if (integerValue < lowestValue) {
            results.add(Pair.of(INVALID_CONTENT, errorField));
        }
    }

    /**
     * Validation of a integer value. It is mandatory and must not be negative.
     *
     * @param results
     * @param integerValue
     * @param errorField
     */
    protected void validateMandatoryAndNotNegative(List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results,
                                                Integer integerValue,
                                                ValidatorErrorFields errorField) {
        if (integerValue == null) {
            results.add(Pair.of(FIELD_NOT_DEFINED, errorField));
            return;
        }
        if (integerValue < 0) {
            results.add(Pair.of(INVALID_CONTENT, errorField));
        }
    }

    /**
     * Validation of a primary key. It is mandatory and must not be negative.
     *
     * @param results
     * @param integerValue
     */
    protected void validatePrimaryKey(List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results,
                                   Integer integerValue) {
        validateMandatoryAndNotNegative(results, integerValue, PRIMARY_KEY);
    }

}
