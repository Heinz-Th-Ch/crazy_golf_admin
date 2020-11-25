package validators;

import dataStructures.HandicapCharacteristicsImpl;
import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static enumerations.ValidatorErrorCodes.OKAY;
import static enumerations.ValidatorErrorFields.*;

/**
 * This implementation of {@link Validator} is used to validate {@link HandicapCharacteristicsImpl}.
 */
public class HandicapCharacteristicsValidator extends AbstractCharacteristicsValidator
        implements Validator<HandicapCharacteristicsImpl> {

    /**
     * Validates the containing field inside a class.
     *
     * @param testableData
     * @return a {@link List} of {@link Pair} with only one with {@link ValidatorErrorCodes#OKAY} on the left and
     * {@link ValidatorErrorFields#CORRECT_DATA} on the right when the validation is okay, otherwise with all pairs
     * with the error code on the left and the symbolic name of the incorrect field on the right
     */
    @Override
    public List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> validate(HandicapCharacteristicsImpl testableData) {
        List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results = new ArrayList<>(List.of());

        validatePrimaryKey(results,
                testableData.getPrimaryKey());
        validateForeignKey(results,
                testableData.getForeignKeyBall(),
                FOREIGN_KEY_BALL);
        validateMandatory(results,
                testableData.getPositioning(),
                POSITIONING);
        validateMandatory(results,
                testableData.getCushioning(),
                CUSHIONING);
        validateMandatory(results,
                testableData.getMarking(),
                MARKING);
        validateMandatory(results,
                testableData.getRemark(),
                REMARK);

        if (results.isEmpty()) {
            if (testableData.getForeignKeyBall() >= 0) {
                validateMandatoryAndContent(results,
                        testableData.getPositioning(),
                        POSITIONING);
            }
        }

        if (results.isEmpty()) {
            results.add(Pair.of(OKAY, CORRECT_DATA));
        }

        return results;
    }

}
