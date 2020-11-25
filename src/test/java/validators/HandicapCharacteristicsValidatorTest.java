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
        // arrange
        characteristics = new HandicapCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_FOREIGN_KEY_INIT,
                CORRECT_POSITIONING_INIT,
                CORRECT_EMPTY,
                CORRECT_EMPTY,
                CORRECT_EMPTY);
        // act
        results= validator.validate(characteristics);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(OKAY, CORRECT_DATA),
                results.get(0));
    }

    @Test
    public void validateAllFieldAreOkayCompleteDefined() {
        // arrange
        characteristics = new HandicapCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_FOREIGN_KEY_DONE,
                CORRECT_POSITIONING_DONE,
                CORRECT_EMPTY,
                CORRECT_EMPTY,
                CORRECT_EMPTY);
        // act
        results= validator.validate(characteristics);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(OKAY, CORRECT_DATA),
                results.get(0));
    }

    @Test
    public void validateAllFieldAreNotOkay() {
        // arrange
        characteristics = new HandicapCharacteristicsImpl(-1,
                -2,
                CORRECT_POSITIONING_DONE,
                CORRECT_EMPTY,
                CORRECT_EMPTY,
                CORRECT_EMPTY);
        characteristics.setPositioning(null);
        characteristics.setCushioning(null);
        characteristics.setMarking(null);
        characteristics.setRemark(null);
        // act
        results= validator.validate(characteristics);
        // assert
        assertEquals("no error code received",
                6,
                results.size());
        assertEquals("wrong error code received for primary key",
                Pair.of(INVALID_CONTENT, PRIMARY_KEY),
                results.get(0));
        assertEquals("wrong error code received for foreign key ball",
                Pair.of(INVALID_CONTENT, FOREIGN_KEY_BALL),
                results.get(1));
        assertEquals("wrong error code received for positioning",
                Pair.of(FIELD_NOT_DEFINED, POSITIONING),
                results.get(2));
        assertEquals("wrong error code received for cushioning",
                Pair.of(FIELD_NOT_DEFINED, CUSHIONING),
                results.get(3));
        assertEquals("wrong error code received for marking",
                Pair.of(FIELD_NOT_DEFINED, MARKING),
                results.get(4));
        assertEquals("wrong error code received for remark",
                Pair.of(FIELD_NOT_DEFINED, REMARK),
                results.get(5));
    }

    @Test
    public void validateAllFieldAreNotOkayOnlyPartiallyDefinedForForeignKeyBall() {
        // arrange
        characteristics = new HandicapCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_FOREIGN_KEY_DONE,
                CORRECT_POSITIONING_INIT,
                CORRECT_EMPTY,
                CORRECT_EMPTY,
                CORRECT_EMPTY);
        // act
        results= validator.validate(characteristics);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received for foreign key suit case",
                Pair.of(EMPTY_FIELD, POSITIONING),
                results.get(0));
    }

    @Test
    public void validateAllFieldAreNotOkayOnlyPartiallyDefinedForBothForeignKeys() {
        // arrange
        characteristics = new HandicapCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_FOREIGN_KEY_DONE,
                CORRECT_POSITIONING_INIT,
                CORRECT_EMPTY,
                CORRECT_EMPTY,
                CORRECT_EMPTY);
        // act
        results= validator.validate(characteristics);
        // assert
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received for positioning",
                Pair.of(EMPTY_FIELD, POSITIONING),
                results.get(0));
    }

}