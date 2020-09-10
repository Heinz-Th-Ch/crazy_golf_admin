package validators;

import abstracts.AbstractPlainJava;
import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static enumerations.ValidatorErrorCodes.*;
import static enumerations.ValidatorErrorFields.*;

/**
 * JUnit test for {@link AbstractCharacteristicsValidator}.
 */
public class AbstractCharacteristicsValidatorTest extends AbstractPlainJava {

    private final List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results = new ArrayList<>(List.of());

    private final AbstractCharacteristicsValidator validator = new AbstractCharacteristicsValidator() {
    };

    @Test
    public void validateMandatoryAndContentIsEmpty() {
        validator.validateMandatoryAndContent(results,
                "",
                COMMENT);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(EMPTY_FIELD, COMMENT),
                results.get(0));
    }

    @Test
    public void validateMandatoryAndContentIsNull() {
        validator.validateMandatoryAndContent(results,
                null,
                COMMENT);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, COMMENT),
                results.get(0));
    }

    @Test
    public void validateMandatoryAndContentIsOkay() {
        validator.validateMandatoryAndContent(results,
                "dummyText",
                COMMENT);
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validateMandatoryAndNotLowerDefinedValueIsNull() {
        validator.validateMandatoryAndNotLowerDefinedValue(results,
                null,
                -1,
                FOREIGN_KEY_SUIT_CASE);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
    }

    @Test
    public void validateMandatoryAndNotLowerDefinedValueIsOkay() {
        validator.validateMandatoryAndNotLowerDefinedValue(results,
                0,
                0,
                FOREIGN_KEY_BALL);
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validateMandatoryAndNotLowerDefinedValueIsTooLow() {
        validator.validateMandatoryAndNotLowerDefinedValue(results,
                -1,
                0,
                FOREIGN_KEY_SUIT_CASE);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_CONTENT, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
    }

    @Test
    public void validateMandatoryAndNotNegativeIsNegative() {
        validator.validateMandatoryAndNotNegative(results,
                -1,
                FOREIGN_KEY_SUIT_CASE);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_CONTENT, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
    }

    @Test
    public void validateMandatoryAndNotNegativeIsNull() {
        validator.validateMandatoryAndNotNegative(results,
                null,
                FOREIGN_KEY_SUIT_CASE);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
    }

    @Test
    public void validateMandatoryAndNotNegativeIsOkay() {
        validator.validateMandatoryAndNotNegative(results,
                0,
                FOREIGN_KEY_BALL);
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validateMandatoryIsNull() {
        validator.validateMandatory(results,
                null,
                COMMENT);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, COMMENT),
                results.get(0));
    }

    @Test
    public void validateMandatoryIsOkay() {
        validator.validateMandatory(results,
                "",
                COMMENT);
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validatePrimaryKeyIsNegative() {
        validator.validatePrimaryKey(results,
                -1);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_CONTENT, PRIMARY_KEY),
                results.get(0));
    }

    @Test
    public void validatePrimaryKeyIsNull() {
        validator.validatePrimaryKey(results,
                null);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, PRIMARY_KEY),
                results.get(0));
    }

    @Test
    public void validatePrimaryKeyIsOkay() {
        validator.validatePrimaryKey(results,
                0);
        assertTrue("unexpected error code received", results.isEmpty());
    }

}