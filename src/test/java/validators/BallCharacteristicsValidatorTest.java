package validators;

import abstracts.AbstractPlainJava;
import dataStructures.BallCharacteristicsImpl;
import enumerations.Hardness;
import org.junit.Before;
import org.junit.Test;

public class BallCharacteristicsValidatorTest extends AbstractPlainJava {

    private static final Integer CORRECT_PRIMARY_KEY = 1;
    private static final String CORRECT_IDENTIFIER = "value";
    private static final String CORRECT_DESCRIPTION = "value";
    private static final Hardness CORRECT_HARDNESS = Hardness.M;
    private static final Integer CORRECT_UP_THROW = 50;
    private static final Integer CORRECT_WEIGHT = 50;
    private static final Double CORRECT_ANGLE_FACTOR = 0.5;
    private static final String CORRECT_COMMENT = "";

    private static final Integer FALSE_PRIMARY_KEY = 0;
    private static final String FALSE_IDENTIFIER = "";
    private static final String FALSE_DESCRIPTION = "";
    private static final Hardness FALSE_HARDNESS = Hardness.UNDEF;
    private static final Integer FALSE_UP_THROW = -1;
    private static final Integer FALSE_WEIGHT = 0;
    private static final Double FALSE_ANGLE_FACTOR = 1.0;

    private final BallCharacteristicsValidator validator = new BallCharacteristicsValidator();

    private BallCharacteristicsImpl characteristics;

    @Before
    public void setUp() {
        characteristics = new BallCharacteristicsImpl(CORRECT_PRIMARY_KEY,
                CORRECT_IDENTIFIER,
                CORRECT_DESCRIPTION,
                CORRECT_HARDNESS,
                CORRECT_UP_THROW,
                CORRECT_WEIGHT,
                CORRECT_ANGLE_FACTOR,
                CORRECT_COMMENT);
    }

    @Test
    public void validate() {
    }
}