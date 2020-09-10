package validators;

import com.google.common.annotations.VisibleForTesting;
import dataStructures.BallCharacteristicsImpl;
import enumerations.Hardness;
import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static enumerations.ValidatorErrorCodes.*;
import static enumerations.ValidatorErrorFields.*;

/**
 * This implementation of {@link Validator} is used to validate {@link BallCharacteristicsImpl}.
 */
public class BallCharacteristicsValidator extends AbstractCharacteristicsValidator
        implements Validator<BallCharacteristicsImpl> {

    /**
     * Validates the containing field inside a class.
     *
     * @param testableData
     * @return a {@link List} of {@link Pair} with only one with {@link ValidatorErrorCodes#OKAY} on the left and
     * {@link ValidatorErrorFields#CORRECT_DATA} on the right when the validation is okay, otherwise with all pairs
     * with the error code on the left and the symbolic name of the incorrect field on the right
     */
    @Override
    public List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> validate(BallCharacteristicsImpl testableData) {
        List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results = new ArrayList<>(List.of());

        validatePrimaryKey(results,
                testableData.getPrimaryKey());
        validateMandatoryAndContent(results,
                testableData.getIdentifier(),
                IDENTIFIER);
        validateMandatory(results,
                testableData.getDescription(),
                DESCRIPTION);
        validateHardness(results,
                testableData.getHardness());
        validateMandatoryAndNotNegative(results,
                testableData.getUpThrow(),
                UP_THROW);
        validateMandatoryAndNotNegative(results,
                testableData.getWeight(),
                WEIGHT);
        validateAngleFactor(results,
                testableData.getAngleFactor());
        validateMandatory(results,
                testableData.getComment(),
                COMMENT);

        if (results.isEmpty()) {
            results.add(Pair.of(OKAY, CORRECT_DATA));
        }

        return results;
    }

    /**
     * Validation of a angle factor value. It is mandatory and must not be negative and equal or higher one.
     *
     * @param results
     * @param angleFactor
     */
    @VisibleForTesting
    protected void validateAngleFactor(List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results,
                                       Double angleFactor) {
        if (angleFactor == null) {
            results.add(Pair.of(FIELD_NOT_DEFINED, ANGLE_FACTOR));
            return;
        }
        if (angleFactor < 0) {
            results.add(Pair.of(CONTENT_TOO_LOW, ANGLE_FACTOR));
        }
        if (!(angleFactor < 1)) {
            results.add(Pair.of(CONTENT_TOO_HIGH, ANGLE_FACTOR));
        }
    }

    /**
     * Validation of a hardness value. It is mandatory only and must not be undefined.
     *
     * @param results
     * @param hardness
     */
    @VisibleForTesting
    protected void validateHardness(List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results,
                                    Hardness hardness) {
        if (hardness == null) {
            results.add(Pair.of(FIELD_NOT_DEFINED, HARDNESS));
            return;
        }
        if (hardness == Hardness.UNDEF) {
            results.add(Pair.of(INVALID_CONTENT, HARDNESS));
        }
    }

}
