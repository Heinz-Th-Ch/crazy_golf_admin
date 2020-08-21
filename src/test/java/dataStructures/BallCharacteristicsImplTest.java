package dataStructures;

import abstracts.AbstractPlainJava;
import enumerations.Hardness;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BallCharacteristicsImplTest extends AbstractPlainJava {

    private final static Integer ORIG_INDEX = 0;

    private final static String ORIG_IDENTIFIER = "identifier 1";
    private final static String ORIG_DESCRIPTION = "description 1";
    private final static Hardness ORIG_HARDNESS = Hardness.M;
    private final static Integer ORIG_UP_THROW = 11;
    private final static Integer ORIG_WEIGHT = 12;
    private final static Double ORIG_ANGLE_FACTOR = 0.5;
    private final static String ORIG_COMMENT = "comment 1";

    private final static String OTHER_IDENTIFIER = "identifier 2";
    private final static String OTHER_DESCRIPTION = "description 2";
    private final static Hardness OTHER_HARDNESS = Hardness.SH;
    private final static Integer OTHER_UP_THROW = 21;
    private final static Integer OTHER_WEIGHT = 22;
    private final static Double OTHER_ANGLE_FACTOR = 0.6;
    private final static String OTHER_COMMENT = "comment 2";

    private BallCharacteristicsImpl originalCharacteristics;
    private BallCharacteristicsImpl otherCharacteristics;

    @Before
    public void setup() {
        originalCharacteristics = new BallCharacteristicsImpl(ORIG_INDEX,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_HARDNESS,
                ORIG_UP_THROW,
                ORIG_WEIGHT,
                ORIG_ANGLE_FACTOR,
                ORIG_COMMENT);
        resetOtherCharacteristics(List.of());
    }

    @Test
    public void getNextPrimaryKey() {
        List<BallCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalCharacteristics);
        resetOtherCharacteristics(testList);
        testList.add(otherCharacteristics);
        BallCharacteristicsImpl thirdCharacteristics = new BallCharacteristicsImpl(testList,
                originalCharacteristics);
        testList.add(thirdCharacteristics);
        assertEquals("invalid new primary key",
                3,
                BallCharacteristicsImpl.getNextPrimaryKey(testList));
    }

    @Test
    public void equals() {
        assertTrue("objects are not equal",
                originalCharacteristics.equals(originalCharacteristics));
        assertTrue("data is not equal",
                originalCharacteristics.equals(otherCharacteristics));
        otherCharacteristics.setIdentifier(OTHER_IDENTIFIER);
        assertFalse("identifier is equal",
                originalCharacteristics.equals(otherCharacteristics));
        resetOtherCharacteristics(List.of());
        otherCharacteristics.setDescription(OTHER_DESCRIPTION);
        assertFalse("description is equal",
                originalCharacteristics.equals(otherCharacteristics));
        resetOtherCharacteristics(List.of());
        otherCharacteristics.setHardness(OTHER_HARDNESS);
        assertFalse("hardness is equal",
                originalCharacteristics.equals(otherCharacteristics));
        resetOtherCharacteristics(List.of());
        otherCharacteristics.setUpThrow(OTHER_UP_THROW);
        assertFalse("upThrow is equal",
                originalCharacteristics.equals(otherCharacteristics));
        resetOtherCharacteristics(List.of());
        otherCharacteristics.setWeight(OTHER_WEIGHT);
        assertFalse("weight is equal",
                originalCharacteristics.equals(otherCharacteristics));
        resetOtherCharacteristics(List.of());
        otherCharacteristics.setAngleFactor(OTHER_ANGLE_FACTOR);
        assertFalse("angleFactor is equal",
                originalCharacteristics.equals(otherCharacteristics));
        resetOtherCharacteristics(List.of());
        otherCharacteristics.setComment(OTHER_COMMENT);
        assertFalse("comment is equal",
                originalCharacteristics.equals(otherCharacteristics));
    }

    @Test
    public void isUnique() {
        List<BallCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalCharacteristics);
        assertTrue("primary key not unique",
                originalCharacteristics.isUnique(testList));
        testList.add(otherCharacteristics);
        otherCharacteristics.setPrimaryKey(ORIG_INDEX);
        assertFalse("primary key unexpected unique",
                originalCharacteristics.isUnique(testList));
        otherCharacteristics.setPrimaryKey(otherCharacteristics.getPrimaryKey() + 1);
        assertFalse("data combination unexpected unique",
                originalCharacteristics.isUnique(testList));
        otherCharacteristics.setIdentifier(OTHER_IDENTIFIER);
        assertTrue("data combination not unique",
                originalCharacteristics.isUnique(testList));
        otherCharacteristics.setIdentifier(ORIG_IDENTIFIER);
        otherCharacteristics.setDescription(OTHER_DESCRIPTION);
        assertTrue("data combination not unique",
                originalCharacteristics.isUnique(testList));
    }

    private void resetOtherCharacteristics(List<BallCharacteristicsImpl> list) {
        otherCharacteristics = new BallCharacteristicsImpl(list, originalCharacteristics);
    }

}