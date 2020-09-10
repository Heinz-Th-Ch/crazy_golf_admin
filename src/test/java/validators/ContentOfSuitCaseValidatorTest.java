package validators;

import abstracts.AbstractPlainJava;
import dataStructures.ContentOfSuitCaseImpl;
import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static enumerations.ValidatorErrorCodes.*;
import static enumerations.ValidatorErrorFields.*;

/**
 * JUnit test for {@link ContentOfSuitCaseValidator}.
 */
public class ContentOfSuitCaseValidatorTest extends AbstractPlainJava {

    private static final Integer CORRECT_KEY = 1;

    private final ContentOfSuitCaseValidator validator = new ContentOfSuitCaseValidator();

    private List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results = new ArrayList<>(List.of());

    ContentOfSuitCaseImpl characteristics;

    @Test
    public void validateAllFieldAreOkay() {
        characteristics = new ContentOfSuitCaseImpl(CORRECT_KEY,
                CORRECT_KEY);
        results = validator.validate(characteristics);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(OKAY, CORRECT_DATA),
                results.get(0));
    }

    @Test
    public void validateAllFieldAreOkayInitialized() {
        characteristics = new ContentOfSuitCaseImpl(CORRECT_KEY,
                -1);
        results = validator.validate(characteristics);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(OKAY, CORRECT_DATA),
                results.get(0));
    }

    @Test
    public void validateAllFieldAreNotOkay() {
        characteristics = new ContentOfSuitCaseImpl(-1,
                -2);
        results = validator.validate(characteristics);
        assertEquals("no error code received",
                2,
                results.size());
        assertEquals("wrong error code received for primary key",
                Pair.of(INVALID_CONTENT, PRIMARY_KEY),
                results.get(0));
        assertEquals("wrong error code received for primary key",
                Pair.of(INVALID_CONTENT, FOREIGN_KEY_BALL),
                results.get(1));
    }

}