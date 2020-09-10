package validators;

import abstracts.AbstractPlainJava;
import dataStructures.ContentOfSuitCaseImpl;
import dataStructures.SuitCaseCharacteristicsImpl;
import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static enumerations.ValidatorErrorCodes.*;
import static enumerations.ValidatorErrorFields.*;

/**
 * JUnit test for {@link SuitCaseCharacteristicsValidator}.
 */
public class SuitCaseCharacteristicsValidatorTest extends AbstractPlainJava {

    private static final Integer CORRECT_PRIMARY_KEY = 1;
    private static final String CORRECT_STRING_VALUE = "value";
    private static final Integer CORRECT_SLOTS = 10;

    private final SuitCaseCharacteristicsValidator validator = new SuitCaseCharacteristicsValidator();

    private List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results = new ArrayList<>(List.of());

    private SuitCaseCharacteristicsImpl characteristics;

    @Test
    public void validateAllFieldAreOkay() {
        characteristics = new SuitCaseCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_STRING_VALUE,
                CORRECT_STRING_VALUE,
                CORRECT_STRING_VALUE,
                CORRECT_SLOTS);
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
        characteristics = new SuitCaseCharacteristicsImpl(-1,
                "",
                CORRECT_STRING_VALUE,
                "",
                0);
        characteristics.setDescription(null);
        results = validator.validate(characteristics);
        assertEquals("no error code received",
                5,
                results.size());
        assertEquals("wrong error code received for primary key",
                Pair.of(INVALID_CONTENT, PRIMARY_KEY),
                results.get(0));
        assertEquals("wrong error code received for identifier",
                Pair.of(EMPTY_FIELD, IDENTIFIER),
                results.get(1));
        assertEquals("wrong error code received for description",
                Pair.of(FIELD_NOT_DEFINED, DESCRIPTION),
                results.get(2));
        assertEquals("wrong error code received for owner",
                Pair.of(EMPTY_FIELD, OWNER),
                results.get(3));
        assertEquals("wrong error code received for contents",
                Pair.of(EMPTY_LIST, CONTENTS),
                results.get(4));
    }

    @Test
    public void validateListMandatoryAndContentIsOkay() {
        validator.validateListMandatoryAndContent(results,
                new ArrayList<>(List.of(new ContentOfSuitCaseImpl(CORRECT_PRIMARY_KEY,
                        CORRECT_PRIMARY_KEY))),
                CONTENTS);
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validateListMandatoryAndContentIsNull() {
        validator.validateListMandatoryAndContent(results,
                null,
                CONTENTS);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(LIST_NOT_DEFINED, CONTENTS),
                results.get(0));
    }

    @Test
    public void validateListMandatoryAndContentIsEmpty() {
        validator.validateListMandatoryAndContent(results,
                new ArrayList<>(List.of()),
                CONTENTS);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(EMPTY_LIST, CONTENTS),
                results.get(0));
    }

}