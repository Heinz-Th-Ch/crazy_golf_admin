package validators;

import dataStructures.ContentOfSuitCaseImpl;
import dataStructures.SuitCaseCharacteristicsImpl;
import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

import static enumerations.ValidatorErrorCodes.*;
import static enumerations.ValidatorErrorFields.*;

/**
 * This implementation of {@link Validator} is used to validate {@link SuitCaseCharacteristicsImpl}.
 */
public class SuitCaseCharacteristicsValidator extends AbstractCharacteristicsValidator
        implements Validator<SuitCaseCharacteristicsImpl> {

    /**
     * Validates the containing field inside a class.
     *
     * @param testableData
     * @return a {@link List} of {@link Pair} with only one with {@link ValidatorErrorCodes#OKAY} on the left and
     * {@link ValidatorErrorFields#CORRECT_DATA} on the right when the validation is okay, otherwise with all pairs
     * with the error code on the left and the symbolic name of the incorrect field on the right
     */
    @Override
    public List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> validate(SuitCaseCharacteristicsImpl testableData) {
        List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results = new ArrayList<>(List.of());

        validatePrimaryKey(results,
                testableData.getPrimaryKey());
        validateMandatoryAndContent(results,
                testableData.getIdentifier(),
                IDENTIFIER);
        validateMandatory(results,
                testableData.getDescription(),
                DESCRIPTION);
        validateMandatoryAndContent(results,
                testableData.getOwner(),
                OWNER);
        validateListMandatoryAndContent(results,
                testableData.getContents(),
                CONTENTS);

        if (results.isEmpty()) {
            results.add(Pair.of(OKAY, CORRECT_DATA));
        }

        return results;
    }

    @VisibleForTesting
    protected void validateListMandatoryAndContent(List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results,
                                                   List<ContentOfSuitCaseImpl> list,
                                                   ValidatorErrorFields errorField) {
        if (list == null) {
            results.add(Pair.of(LIST_NOT_DEFINED, errorField));
            return;
        }
        if (list.isEmpty()) {
            results.add(Pair.of(EMPTY_LIST, errorField));
        }
    }

}
