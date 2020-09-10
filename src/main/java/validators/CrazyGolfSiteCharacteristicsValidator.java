package validators;

import com.google.common.annotations.VisibleForTesting;
import dataStructures.CrazyGolfSiteCharacteristicsImpl;
import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static enumerations.ValidatorErrorCodes.*;
import static enumerations.ValidatorErrorFields.*;

/**
 * This implementation of {@link Validator} is used to validate {@link CrazyGolfSiteCharacteristicsImpl}.
 */

public class CrazyGolfSiteCharacteristicsValidator extends AbstractCharacteristicsValidator
        implements Validator<CrazyGolfSiteCharacteristicsImpl> {

    /**
     * Validates the containing field inside a class.
     *
     * @param testableData
     * @return a {@link List} of {@link Pair} with only one with {@link ValidatorErrorCodes#OKAY} on the left and
     * {@link ValidatorErrorFields#CORRECT_DATA} on the right when the validation is okay, otherwise with all pairs
     * with the error code on the left and the symbolic name of the incorrect field on the right
     */
    @Override
    public List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> validate(CrazyGolfSiteCharacteristicsImpl testableData) {
        List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results = new ArrayList<>(List.of());

        validatePrimaryKey(results,
                testableData.getPrimaryKey());
        validateMandatoryAndContent(results,
                testableData.getSiteName(),
                SITE_NAME);
        validateMandatoryAndContent(results,
                testableData.getAddress(),
                ADDRESS);
        validatePostCode(results,
                testableData.getPostCode());
        validateMandatoryAndContent(results,
                testableData.getTown(),
                TOWN);

        if (results.isEmpty()) {
            results.add(Pair.of(OKAY, CORRECT_DATA));
        }

        return results;
    }

    /**
     * Validation of a post code. It is mandatory, must not be empty and must either be numeric or corresponds to an iso countery code dependent mask.
     *
     * @param results
     * @param postCode
     */
    @VisibleForTesting
    protected void validatePostCode(List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results, String postCode) {
        validateMandatoryAndContent(results,
                postCode,
                POST_CODE);
        if (results.isEmpty()) {
            if (!NumberUtils.isDigits(postCode)) {
                String[] postCodeParts = postCode.split("-");
                if (postCodeParts.length != 2) {
                    results.add(Pair.of(INVALID_POST_CODE_SYNTAX, POST_CODE));
                    return;
                }
                if (Arrays.stream(Locale.getISOCountries()).noneMatch(s -> s.equals(postCodeParts[0]))) {
                    results.add(Pair.of(INVALID_COUNTRY_CODE, POST_CODE));
                }
                if (!NumberUtils.isDigits(postCodeParts[1])) {
                    results.add(Pair.of(INVALID_POST_CODE, POST_CODE));
                }
            }
        }
    }

}
