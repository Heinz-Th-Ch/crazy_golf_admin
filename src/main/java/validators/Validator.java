package validators;

import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * The interface is the model for all relevant validation in this application.
 */
public interface Validator<T> {

    /**
     * Validates the containing field inside a class.
     *
     * @param testableData
     * @return a {@link List} of {@link Pair} with only one with {@link ValidatorErrorCodes#OKAY} on the left and
     * {@link ValidatorErrorFields#CORRECT_DATA} on the right when the validation is okay, otherwise with all pairs
     * with the error code on the left and the symbolic name of the incorrect field on the right
     */
    List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> validate(T testableData);

}
