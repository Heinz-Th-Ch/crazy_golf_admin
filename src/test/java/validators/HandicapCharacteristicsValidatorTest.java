package validators;

import abstracts.AbstractPlainJava;
import dataStructures.HandicapCharacteristicsImpl;
import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static enumerations.ValidatorErrorCodes.*;
import static enumerations.ValidatorErrorFields.*;

/**
 * JUnit test for {@link HandicapCharacteristicsValidator}.
 */
public class HandicapCharacteristicsValidatorTest extends AbstractPlainJava {

    private static final Integer CORRECT_PRIMARY_KEY = 1;
    private static final Integer CORRECT_FOREIGN_KEY_DONE = 1;
    private static final Integer CORRECT_FOREIGN_KEY_INIT = -1;
    private static final String CORRECT_POSITIONING_DONE = "value";
    private static final String CORRECT_POSITIONING_INIT = "";
    private static final String CORRECT_EMPTY = "";

    private final HandicapCharacteristicsValidator validator = new HandicapCharacteristicsValidator();

    private List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results = new ArrayList<>(List.of());

    private HandicapCharacteristicsImpl characteristics;

    @Test
    public void validateAllFieldAreOkayOnlyInitialized() {
        characteristics = new HandicapCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_FOREIGN_KEY_INIT,
                CORRECT_FOREIGN_KEY_INIT,
                CORRECT_POSITIONING_INIT,
                CORRECT_EMPTY,
                CORRECT_EMPTY,
                CORRECT_EMPTY);
        results= validator.validate(characteristics);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(OKAY, CORRECT_DATA),
                results.get(0));
    }

    @Test
    public void validateAllFieldAreOkayCompleteDefined() {
        characteristics = new HandicapCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_FOREIGN_KEY_DONE,
                CORRECT_FOREIGN_KEY_DONE,
                CORRECT_POSITIONING_DONE,
                CORRECT_EMPTY,
                CORRECT_EMPTY,
                CORRECT_EMPTY);
        results= validator.validate(characteristics);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(OKAY, CORRECT_DATA),
                results.get(0));
    }

    @Test
    public void validateAllFieldAreNotOkay() {
        characteristics = new HandicapCharacteristicsImpl(-1,
                -2,
                -2,
                CORRECT_POSITIONING_DONE,
                CORRECT_EMPTY,
                CORRECT_EMPTY,
                CORRECT_EMPTY);
        characteristics.setPositioning(null);
        characteristics.setCushioning(null);
        characteristics.setMarking(null);
        characteristics.setRemark(null);
        results= validator.validate(characteristics);
        assertEquals("no error code received",
                7,
                results.size());
        assertEquals("wrong error code received for primary key",
                Pair.of(INVALID_CONTENT, PRIMARY_KEY),
                results.get(0));
        assertEquals("wrong error code received for foreign key suit case",
                Pair.of(INVALID_CONTENT, FOREIGN_KEY_SUIT_CASE),
                results.get(1));
        assertEquals("wrong error code received for foreign key ball",
                Pair.of(INVALID_CONTENT, FOREIGN_KEY_BALL),
                results.get(2));
        assertEquals("wrong error code received for positioning",
                Pair.of(FIELD_NOT_DEFINED, POSITIONING),
                results.get(3));
        assertEquals("wrong error code received for cushioning",
                Pair.of(FIELD_NOT_DEFINED, CUSHIONING),
                results.get(4));
        assertEquals("wrong error code received for marking",
                Pair.of(FIELD_NOT_DEFINED, MARKING),
                results.get(5));
        assertEquals("wrong error code received for remark",
                Pair.of(FIELD_NOT_DEFINED, REMARK),
                results.get(6));
    }

    @Test
    public void validateAllFieldAreNotOkayOnlyPartiallyDefinedForForeignKeySuitCase() {
        characteristics = new HandicapCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_FOREIGN_KEY_DONE,
                CORRECT_FOREIGN_KEY_INIT,
                CORRECT_POSITIONING_INIT,
                CORRECT_EMPTY,
                CORRECT_EMPTY,
                CORRECT_EMPTY);
        results= validator.validate(characteristics);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received for foreign key ball",
                Pair.of(INVALID_CONTENT_AT_DATA_DEPENDENCY, FOREIGN_KEY_BALL),
                results.get(0));
    }

    @Test
    public void validateAllFieldAreNotOkayOnlyPartiallyDefinedForForeignKeyBall() {
        characteristics = new HandicapCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_FOREIGN_KEY_INIT,
                CORRECT_FOREIGN_KEY_DONE,
                CORRECT_POSITIONING_INIT,
                CORRECT_EMPTY,
                CORRECT_EMPTY,
                CORRECT_EMPTY);
        results= validator.validate(characteristics);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received for foreign key suit case",
                Pair.of(INVALID_CONTENT_AT_DATA_DEPENDENCY, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
    }

    @Test
    public void validateAllFieldAreNotOkayOnlyPartiallyDefinedForBothForeignKeys() {
        characteristics = new HandicapCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_FOREIGN_KEY_DONE,
                CORRECT_FOREIGN_KEY_DONE,
                CORRECT_POSITIONING_INIT,
                CORRECT_EMPTY,
                CORRECT_EMPTY,
                CORRECT_EMPTY);
        results= validator.validate(characteristics);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received for positioning",
                Pair.of(EMPTY_FIELD, POSITIONING),
                results.get(0));
    }

    @Test
    public void validateForeignKeysAreOkayOnlyInitialized() {
        validator.validateForeignKeys(results,
                -1,
                -1);
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validateForeignKeysAreOkayCompleteDefined() {
        validator.validateForeignKeys(results,
                0,
                0);
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validateForeignKeysAreNull() {
        validator.validateForeignKeys(results,
                null,
                null);
        assertEquals("no error code received",
                2,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, FOREIGN_KEY_BALL),
                results.get(1));
    }

    @Test
    public void validateForeignKeysAreToLow() {
        validator.validateForeignKeys(results,
                -2,
                -2);
        assertEquals("no error code received",
                2,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_CONTENT, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
        assertEquals("wrong error code received",
                Pair.of(INVALID_CONTENT, FOREIGN_KEY_BALL),
                results.get(1));
    }

    @Test
    public void validateForeignKeysOnlyForeignKeySuitCaseIsSet() {
        validator.validateForeignKeys(results,
                0,
                -1);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_CONTENT_AT_DATA_DEPENDENCY, FOREIGN_KEY_BALL),
                results.get(0));
    }

    @Test
    public void validateForeignKeysOnlyForeignKeyBallIsSet() {
        validator.validateForeignKeys(results,
                -1,
                0);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_CONTENT_AT_DATA_DEPENDENCY, FOREIGN_KEY_SUIT_CASE),
                results.get(0));
    }

}