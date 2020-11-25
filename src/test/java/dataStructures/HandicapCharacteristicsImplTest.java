package dataStructures;

import abstracts.AbstractPlainJava;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit tests of {@link HandicapCharacteristicsImpl}.
 */
public class HandicapCharacteristicsImplTest extends AbstractPlainJava {

    private final static Integer ORIG_INDEX = 0;

    private final static Integer ORIG_FOREIGN_BALL = 1;

    private final static String ORIG_POSITIONING = "positioning 1";
    private final static String ORIG_CUSHIONING = "cushioning 1";
    private final static String ORIG_MARKING = "marking 1";
    private final static String ORIG_REMARK = "remark 1";

    private final static Integer OTHER_FOREIGN_BALL = 3;

    private final static String OTHER_POSITIONING = "positioning 2";
    private final static String OTHER_CUSHIONING = "cushioning 2";
    private final static String OTHER_MARKING = "marking 2";
    private final static String OTHER_REMARK = "remark 2";

    private final static Integer NULL_INDEX = null;
    private final static List<HandicapCharacteristicsImpl> NULL_LIST = null;

    private HandicapCharacteristicsImpl originalHandicapCharacteristics;
    private HandicapCharacteristicsImpl otherHandicapCharacteristics;

    @Before
    public void setup() {
        originalHandicapCharacteristics = new HandicapCharacteristicsImpl(ORIG_INDEX,
                ORIG_FOREIGN_BALL,
                ORIG_POSITIONING,
                ORIG_CUSHIONING,
                ORIG_MARKING,
                ORIG_REMARK);
        resetOtherCharacteristics(List.of());
    }

    @Test
    public void getNextPrimaryKey() {
        // arrage
        List<HandicapCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalHandicapCharacteristics);
        resetOtherCharacteristics(testList);
        testList.add(otherHandicapCharacteristics);
        HandicapCharacteristicsImpl thirdCharacteristics = new HandicapCharacteristicsImpl(testList,
                originalHandicapCharacteristics);
        testList.add(thirdCharacteristics);
        // act and assert
        assertEquals("invalid new primary key",
                3,
                HandicapCharacteristicsImpl.getNextPrimaryKey(testList));
    }

    @Test
    public void equals() {
        // act and assert 1
        assertTrue("objects are not equal",
                originalHandicapCharacteristics.equals(originalHandicapCharacteristics));
        assertTrue("data is not equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
        // arrange 2
        otherHandicapCharacteristics.setForeignKeyBall(OTHER_FOREIGN_BALL);
        // act and assert 2
        assertFalse("foreignKeyBall is equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
        // arrange 3
        resetOtherCharacteristics(List.of());
        otherHandicapCharacteristics.setPositioning(OTHER_POSITIONING);
        // act and assert 3
        assertFalse("positioning is equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
        // arrange 4
        resetOtherCharacteristics(List.of());
        otherHandicapCharacteristics.setCushioning(OTHER_CUSHIONING);
        // act and assert 4
        assertFalse("cushioning is equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
        // arrange 5
        resetOtherCharacteristics(List.of());
        otherHandicapCharacteristics.setMarking(OTHER_MARKING);
        // act and assert 5
        assertFalse("marking is equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
        // arrange 6
        resetOtherCharacteristics(List.of());
        otherHandicapCharacteristics.setRemark(OTHER_REMARK);
        // act and assert 6
        assertFalse("remark is equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
    }

    @Test
    public void isUnique() {
        // arrange 1
        List<HandicapCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalHandicapCharacteristics);
        // act and assert 1
        assertTrue("primary key not unique",
                originalHandicapCharacteristics.isUnique(testList));
        // arrange 2
        testList.add(otherHandicapCharacteristics);
        otherHandicapCharacteristics.setPrimaryKey(ORIG_INDEX);
        // act and assert 2
        assertFalse("primary key unexpected unique",
                originalHandicapCharacteristics.isUnique(testList));
        // arrange 3
        otherHandicapCharacteristics.setPrimaryKey(otherHandicapCharacteristics.getPrimaryKey() + 1);
        // act and assert 3
        assertTrue("primary key not unique",
                originalHandicapCharacteristics.isUnique(testList));
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForIndex() {
        // act and assert
        new HandicapCharacteristicsImpl(NULL_INDEX,
                ORIG_FOREIGN_BALL,
                ORIG_POSITIONING,
                ORIG_CUSHIONING,
                ORIG_MARKING,
                ORIG_REMARK);
    }
    
    @Test(expected = NullPointerException.class)
    public void checkNotNullForForeignKeyBall() {
        // act and assert
        new HandicapCharacteristicsImpl(ORIG_INDEX,
                null,
                ORIG_POSITIONING,
                ORIG_CUSHIONING,
                ORIG_MARKING,
                ORIG_REMARK);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForPositioning() {
        // act and assert
        new HandicapCharacteristicsImpl(ORIG_INDEX,
                ORIG_FOREIGN_BALL,
                null,
                ORIG_CUSHIONING,
                ORIG_MARKING,
                ORIG_REMARK);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForCushioning() {
        // act and assert
        new HandicapCharacteristicsImpl(ORIG_INDEX,
                ORIG_FOREIGN_BALL,
                ORIG_POSITIONING,
                null,
                ORIG_MARKING,
                ORIG_REMARK);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForMarking() {
        // act and assert
        new HandicapCharacteristicsImpl(ORIG_INDEX,
                ORIG_FOREIGN_BALL,
                ORIG_POSITIONING,
                ORIG_CUSHIONING,
                null,
                ORIG_REMARK);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForRemark() {
        // act and assert
        new HandicapCharacteristicsImpl(ORIG_INDEX,
                ORIG_FOREIGN_BALL,
                ORIG_POSITIONING,
                ORIG_CUSHIONING,
                ORIG_MARKING,
                null);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForListWithCorrectObject(){
        // act and assert
        new HandicapCharacteristicsImpl(NULL_LIST,
                originalHandicapCharacteristics);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForCorrectListAndNullObject(){
        // act and assert
        new HandicapCharacteristicsImpl(new ArrayList<>(List.of()),
                null);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForListOnly(){
        // act and assert
        new HandicapCharacteristicsImpl(NULL_LIST);
    }

    private void resetOtherCharacteristics(List<HandicapCharacteristicsImpl> list) {
        otherHandicapCharacteristics = new HandicapCharacteristicsImpl(list, originalHandicapCharacteristics);
    }

}