package validators;

import dataStructures.BallCharacteristicsImpl;
import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;

import static enumerations.ValidatorErrorCodes.OKAY;
import static enumerations.ValidatorErrorFields.CORRECT_FIELD;

/**
 * This implementation of {@link Validator} is used to validate {@link BallCharacteristicsImpl}.
 */
public class BallCharacteristicsValidator implements Validator<BallCharacteristicsImpl> {

    /**
     * Validates the containing field inside a class.
     *
     * @param testableData
     * @return a {@link Pair} with {@link ValidatorErrorCodes#OKAY} on the left and an empty String on the right when
     * the validation is okay, otherwise with the real error code on the left and the symbolic name of the incorrect
     * field
     */
    @Override
    public Pair<ValidatorErrorCodes, ValidatorErrorFields> validate(BallCharacteristicsImpl testableData) {
        return Pair.of(OKAY, CORRECT_FIELD);
    }

}
