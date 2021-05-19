package dataStructures;

import abstracts.AbstractPlainJava;
import enumerations.Hardness;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit tests of {@link BallCharacteristicsImpl}.
 */
public class BallCharacteristicsImplTest extends AbstractPlainJava {

    private final static String[] FIELD_NAMES = {"primaryKey",
            "identifier",
            "description",
            "hardness",
            "upThrow",
            "weight",
            "angleFactor",
            "comment"
    };
    private final static int NUMBER_OF_FIELDS = 8;

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
    private final static Hardness OTHER_HARDNESS = Hardness.VH;
    private final static Integer OTHER_UP_THROW = 21;
    private final static Integer OTHER_WEIGHT = 22;
    private final static Double OTHER_ANGLE_FACTOR = 0.6;
    private final static String OTHER_COMMENT = "comment 2";

    private final static Integer NULL_INDEX = null;
    private final static List<BallCharacteristicsImpl> NULL_LIST = null;

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
        // arrange
        List<BallCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalCharacteristics);
        resetOtherCharacteristics(testList);
        testList.add(otherCharacteristics);
        BallCharacteristicsImpl thirdCharacteristics = new BallCharacteristicsImpl(testList,
                originalCharacteristics);
        testList.add(thirdCharacteristics);
        // act and assert
        assertEquals("invalid new primary key",
                Integer.valueOf(3),
                BallCharacteristicsImpl.getNextPrimaryKey(testList));
    }

    @Test
    public void equals() {
        // act and assert 1
        assertTrue("objects are not equal",
                originalCharacteristics.equals(originalCharacteristics));
        assertTrue("data is not equal",
                originalCharacteristics.equals(otherCharacteristics));
        // arrange 2
        otherCharacteristics.setIdentifier(OTHER_IDENTIFIER);
        // act and assert 2
        assertFalse("identifier is equal",
                originalCharacteristics.equals(otherCharacteristics));
        // arrange 3
        resetOtherCharacteristics(List.of());
        otherCharacteristics.setDescription(OTHER_DESCRIPTION);
        // act and assert 3
        assertFalse("description is equal",
                originalCharacteristics.equals(otherCharacteristics));
        // arrange 4
        resetOtherCharacteristics(List.of());
        otherCharacteristics.setHardness(OTHER_HARDNESS);
        // act and assert 4
        assertFalse("hardness is equal",
                originalCharacteristics.equals(otherCharacteristics));
        // arrange 5
        resetOtherCharacteristics(List.of());
        otherCharacteristics.setUpThrow(OTHER_UP_THROW);
        // act and assert
        assertFalse("upThrow is equal",
                originalCharacteristics.equals(otherCharacteristics));
        // arrange 6
        resetOtherCharacteristics(List.of());
        otherCharacteristics.setWeight(OTHER_WEIGHT);
        // act and assert 6
        assertFalse("weight is equal",
                originalCharacteristics.equals(otherCharacteristics));
        // arrange 7
        resetOtherCharacteristics(List.of());
        otherCharacteristics.setAngleFactor(OTHER_ANGLE_FACTOR);
        // act and assert 7
        assertFalse("angleFactor is equal",
                originalCharacteristics.equals(otherCharacteristics));
        // arrange 8
        resetOtherCharacteristics(List.of());
        otherCharacteristics.setComment(OTHER_COMMENT);
        // act and assert 8
        assertFalse("comment is equal",
                originalCharacteristics.equals(otherCharacteristics));
    }

    @Test
    public void isUnique() {
        // arrange 1
        List<BallCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalCharacteristics);
        // act and assert 1
        assertTrue("primary key not unique",
                originalCharacteristics.isUnique(testList));
        // arrange 2
        testList.add(otherCharacteristics);
        otherCharacteristics.setPrimaryKey(ORIG_INDEX);
        // act and assert 2
        assertFalse("primary key unexpected unique",
                originalCharacteristics.isUnique(testList));
        // arrange 3
        otherCharacteristics.setPrimaryKey(otherCharacteristics.getPrimaryKey() + 1);
        // act and assert 3
        assertFalse("data combination unexpected unique",
                originalCharacteristics.isUnique(testList));
        // arrange 4
        otherCharacteristics.setIdentifier(OTHER_IDENTIFIER);
        // act and assert 4
        assertTrue("data combination not unique",
                originalCharacteristics.isUnique(testList));
        // arrange 5
        otherCharacteristics.setIdentifier(ORIG_IDENTIFIER);
        otherCharacteristics.setDescription(OTHER_DESCRIPTION);
        // act and assert 5
        assertTrue("data combination not unique",
                originalCharacteristics.isUnique(testList));
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForIndex() {
        // act and assert
        new BallCharacteristicsImpl(NULL_INDEX,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_HARDNESS,
                ORIG_UP_THROW,
                ORIG_WEIGHT,
                ORIG_ANGLE_FACTOR,
                ORIG_COMMENT);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForIdentifier() {
        // act and assert
        new BallCharacteristicsImpl(ORIG_INDEX,
                null,
                ORIG_DESCRIPTION,
                ORIG_HARDNESS,
                ORIG_UP_THROW,
                ORIG_WEIGHT,
                ORIG_ANGLE_FACTOR,
                ORIG_COMMENT);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForDescription() {
        // act and assert
        new BallCharacteristicsImpl(ORIG_INDEX,
                ORIG_IDENTIFIER,
                null,
                ORIG_HARDNESS,
                ORIG_UP_THROW,
                ORIG_WEIGHT,
                ORIG_ANGLE_FACTOR,
                ORIG_COMMENT);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForHardness() {
        // act and assert
        new BallCharacteristicsImpl(ORIG_INDEX,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                null,
                ORIG_UP_THROW,
                ORIG_WEIGHT,
                ORIG_ANGLE_FACTOR,
                ORIG_COMMENT);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForUpThrow() {
        // act and assert
        new BallCharacteristicsImpl(ORIG_INDEX,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_HARDNESS,
                null,
                ORIG_WEIGHT,
                ORIG_ANGLE_FACTOR,
                ORIG_COMMENT);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForWeight() {
        // act and assert
        new BallCharacteristicsImpl(ORIG_INDEX,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_HARDNESS,
                ORIG_UP_THROW,
                null,
                ORIG_ANGLE_FACTOR,
                ORIG_COMMENT);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForAngleFactor() {
        // act and assert
        new BallCharacteristicsImpl(ORIG_INDEX,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_HARDNESS,
                ORIG_UP_THROW,
                ORIG_WEIGHT,
                null,
                ORIG_COMMENT);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForComment() {
        // act and assert
        new BallCharacteristicsImpl(ORIG_INDEX,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_HARDNESS,
                ORIG_UP_THROW,
                ORIG_WEIGHT,
                ORIG_ANGLE_FACTOR,
                null);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForListWithCorrectObject() {
        // act and assert
        new BallCharacteristicsImpl(NULL_LIST,
                originalCharacteristics);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForCorrectListAndNullObject() {
        // act and assert
        new BallCharacteristicsImpl(new ArrayList<>(List.of()),
                null);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForListAndCorrectDetails() {
        // act and assert
        new BallCharacteristicsImpl(NULL_LIST,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_HARDNESS,
                ORIG_UP_THROW,
                ORIG_WEIGHT,
                ORIG_ANGLE_FACTOR,
                ORIG_COMMENT);
    }

    @Test
    public void numberAndOrderOfField() {
        // act
        Field[] fields = BallCharacteristicsImpl.class.getDeclaredFields();
        // assert
        assertEquals("unexpected number of fields",
                NUMBER_OF_FIELDS,
                fields.length);
        for (int i = 0; i < fields.length; i++) {
            assertEquals("field not in correct sequence",
                    FIELD_NAMES[i],
                    fields[i].getName());
        }
    }

    private void resetOtherCharacteristics(List<BallCharacteristicsImpl> list) {
        otherCharacteristics = new BallCharacteristicsImpl(list, originalCharacteristics);
    }

}