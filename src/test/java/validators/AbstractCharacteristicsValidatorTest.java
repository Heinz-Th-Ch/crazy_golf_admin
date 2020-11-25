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
        // act
        validator.validateMandatoryAndContent(results,
                "",
                COMMENT);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(EMPTY_FIELD, COMMENT),
                results.get(0));
    }

    @Test
    public void validateMandatoryAndContentIsNull() {
        // act
        validator.validateMandatoryAndContent(results,
                null,
                COMMENT);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, COMMENT),
                results.get(0));
    }

    @Test
    public void validateMandatoryAndContentIsOkay() {
        // act
        validator.validateMandatoryAndContent(results,
                "dummyText",
                COMMENT);
        // assert
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validateMandatoryAndNotLowerDefinedValueIsNull() {
        // act
        validator.validateMandatoryAndNotLowerDefinedValue(results,
                null,
                -1,
                FOREIGN_KEY_SUIT_CASE);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
    }

    @Test
    public void validateMandatoryAndNotLowerDefinedValueIsOkay() {
        // act
        validator.validateMandatoryAndNotLowerDefinedValue(results,
                0,
                0,
                FOREIGN_KEY_BALL);
        // assert
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validateMandatoryAndNotLowerDefinedValueIsTooLow() {
        // act
        validator.validateMandatoryAndNotLowerDefinedValue(results,
                -1,
                0,
                FOREIGN_KEY_SUIT_CASE);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_CONTENT, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
    }

    @Test
    public void validateMandatoryAndNotNegativeIsNegative() {
        // act
        validator.validateMandatoryAndNotNegative(results,
                -1,
                FOREIGN_KEY_SUIT_CASE);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_CONTENT, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
    }

    @Test
    public void validateMandatoryAndNotNegativeIsNull() {
        // act
        validator.validateMandatoryAndNotNegative(results,
                null,
                FOREIGN_KEY_SUIT_CASE);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
    }

    @Test
    public void validateMandatoryAndNotNegativeIsOkay() {
        // act
        validator.validateMandatoryAndNotNegative(results,
                0,
                FOREIGN_KEY_BALL);
        // assert
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validateMandatoryIsNull() {
        // act
        validator.validateMandatory(results,
                null,
                COMMENT);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, COMMENT),
                results.get(0));
    }

    @Test
    public void validateMandatoryIsOkay() {
        // act
        validator.validateMandatory(results,
                "",
                COMMENT);
        // assert
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validatePrimaryKeyIsNegative() {
        // act
        validator.validatePrimaryKey(results,
                -1);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_CONTENT, PRIMARY_KEY),
                results.get(0));
    }

    @Test
    public void validatePrimaryKeyIsNull() {
        // act
        validator.validatePrimaryKey(results,
                null);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, PRIMARY_KEY),
                results.get(0));
    }

    @Test
    public void validatePrimaryKeyIsOkay() {
        // act
        validator.validatePrimaryKey(results,
                0);
        // assert
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validateForeignKeyIsTooLow() {
        // act
        validator.validateForeignKey(results,
                -2,
                FOREIGN_KEY_SUIT_CASE);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_CONTENT, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
    }

    @Test
    public void validateForeignKeyIsNull() {
        // act
        validator.validateForeignKey(results,
                null,
                FOREIGN_KEY_BALL);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, FOREIGN_KEY_BALL),
                results.get(0));
    }

    @Test
    public void validateForeignKeyAreOkay() {
        // act 1
        validator.validateForeignKey(results,
                -1,
                FOREIGN_KEY_BALL);
        // assert 1
        assertTrue("unexpected error code received", results.isEmpty());
        // act 2
        validator.validateForeignKey(results,
                1,
                FOREIGN_KEY_SUIT_CASE);
        // assert 2
        assertTrue("unexpected error code received", results.isEmpty());
    }

}