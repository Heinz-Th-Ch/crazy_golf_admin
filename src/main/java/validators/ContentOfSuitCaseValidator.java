package validators;

import dataStructures.ContentOfSuitCaseImpl;
import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static enumerations.ValidatorErrorCodes.*;
import static enumerations.ValidatorErrorFields.*;

/**
 * This implementation of {@link Validator} is used to validate {@link ContentOfSuitCaseImpl}.
 */
public class ContentOfSuitCaseValidator extends AbstractCharacteristicsValidator
        implements Validator<ContentOfSuitCaseImpl> {

    /**
     * Validates the containing field inside a class.
     *
     * @param testableData
     * @return a {@link List} of {@link Pair} with only one with {@link ValidatorErrorCodes#OKAY} on the left and
     * {@link ValidatorErrorFields#CORRECT_DATA} on the right when the validation is okay, otherwise with all pairs
     * with the error code on the left and the symbolic name of the incorrect field on the right
     */
    @Override
    public List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> validate(ContentOfSuitCaseImpl testableData) {
        List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results = new ArrayList<>(List.of());

        validatePrimaryKey(results,
                testableData.getPrimaryKey());
        validateMandatoryAndNotLowerDefinedValue(results,
                testableData.getForeignKeyBall(),
                -1,
                FOREIGN_KEY_BALL);

        if (results.isEmpty()) {
            results.add(Pair.of(OKAY, CORRECT_DATA));
        }

        return results;
    }

}
