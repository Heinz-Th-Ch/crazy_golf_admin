package validators;

import abstracts.AbstractPlainJava;
import dataStructures.BallCharacteristicsImpl;
import enumerations.Hardness;
import enumerations.ValidatorErrorCodes;
import enumerations.ValidatorErrorFields;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static enumerations.ValidatorErrorCodes.*;
import static enumerations.ValidatorErrorFields.*;

/**
 * JUnit test for {@link BallCharacteristicsValidator}.
 */
public class BallCharacteristicsValidatorTest extends AbstractPlainJava {

    private static final Integer CORRECT_PRIMARY_KEY = 1;
    private static final String CORRECT_IDENTIFIER = "value";
    private static final String CORRECT_DESCRIPTION = "value";
    private static final Hardness CORRECT_HARDNESS = Hardness.M;
    private static final Integer CORRECT_UP_THROW = 50;
    private static final Integer CORRECT_WEIGHT = 50;
    private static final Double CORRECT_ANGLE_FACTOR = 0.5;
    private static final String CORRECT_COMMENT = "";

    private final BallCharacteristicsValidator validator = new BallCharacteristicsValidator();

    private List<Pair<ValidatorErrorCodes, ValidatorErrorFields>> results = new ArrayList<>(List.of());

    private BallCharacteristicsImpl characteristics;

    @Test
    public void validateAllFieldAreOkay() {
        characteristics = new BallCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_IDENTIFIER,
                CORRECT_DESCRIPTION,
                CORRECT_HARDNESS,
                CORRECT_UP_THROW,
                CORRECT_WEIGHT,
                CORRECT_ANGLE_FACTOR,
                CORRECT_COMMENT);
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
        characteristics = new BallCharacteristicsImpl(-1,
                "",
                "",
                Hardness.UNDEF,
                -1,
                -1,
                1.,
                "");
        characteristics.setDescription(null);
        characteristics.setComment(null);
        results = validator.validate(characteristics);
        assertEquals("no error code received",
                8,
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
        assertEquals("wrong error code received for hardness",
                Pair.of(INVALID_CONTENT, HARDNESS),
                results.get(3));
        assertEquals("wrong error code received for upThrow",
                Pair.of(INVALID_CONTENT, UP_THROW),
                results.get(4));
        assertEquals("wrong error code received for weight",
                Pair.of(INVALID_CONTENT, WEIGHT),
                results.get(5));
        assertEquals("wrong error code received for angle factor",
                Pair.of(CONTENT_TOO_HIGH, ANGLE_FACTOR),
                results.get(6));
        assertEquals("wrong error code received for comment",
                Pair.of(FIELD_NOT_DEFINED, COMMENT),
                results.get(7));
    }

    @Test
    public void validateAngleFactorIsOkay() {
        validator.validateAngleFactor(results,
                0.);
        validator.validateAngleFactor(results,
                0.99);
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validateAngleFactorIsNull() {
        validator.validateAngleFactor(results,
                null);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, ANGLE_FACTOR),
                results.get(0));
    }

    @Test
    public void validateAngleFactorIsTooLow() {
        validator.validateAngleFactor(results,
                -.01);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(CONTENT_TOO_LOW, ANGLE_FACTOR),
                results.get(0));
    }

    @Test
    public void validateAngleFactorIsTooHigh() {
        validator.validateAngleFactor(results,
                1.);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(CONTENT_TOO_HIGH, ANGLE_FACTOR),
                results.get(0));
    }

    @Test
    public void validateHardnessIsOkay() {
        validator.validateHardness(results,
                Hardness.M);
        assertTrue("unexpected error code received", results.isEmpty());
    }

    @Test
    public void validateHardnessIsNull() {
        validator.validateHardness(results,
                null);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(FIELD_NOT_DEFINED, HARDNESS),
                results.get(0));
    }

    @Test
    public void validateHardnessIsInvalid() {
        validator.validateHardness(results,
                Hardness.UNDEF);
        assertEquals("no error code received",
                1,
                results.size());
        assertEquals("wrong error code received",
                Pair.of(INVALID_CONTENT, HARDNESS),
                results.get(0));
    }

}