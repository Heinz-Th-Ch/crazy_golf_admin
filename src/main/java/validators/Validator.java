package validators;

import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;

/**
 * The interface is the model for all relevant validators in this application.
 */
public interface Validator <T>{

    /**
     * Validates the containing field inside a class.
     *
     * @param testableData
     * @return a {@link Pair} with {@link ValidatorErrorCodes#OKAY} on the left and an empty String on the right when
     * the validation is okay, otherwise with the real error code on the left and the symbolic name of the incorrect
     * field
     */
    Pair<ValidatorErrorCodes, ValidatorErrorFields> validate(T testableData);

}
