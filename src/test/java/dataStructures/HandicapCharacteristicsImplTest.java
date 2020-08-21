package dataStructures;

import abstracts.AbstractPlainJava;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HandicapCharacteristicsImplTest extends AbstractPlainJava {

    private final static Integer ORIG_INDEX = 0;

    private final static Integer ORIG_FOREIGN_SUITCASE = 0;
    private final static Integer ORIG_FOREIGN_BALL = 1;

    private final static String ORIG_POSITIONING = "positioning 1";
    private final static String ORIG_CUSHIONING = "cushioning 1";
    private final static String ORIG_MARKING = "marking 1";
    private final static String ORIG_REMARK = "remark 1";

    private final static Integer OTHER_FOREIGN_SUITCASE = 2;
    private final static Integer OTHER_FOREIGN_BALL = 3;

    private final static String OTHER_POSITIONING = "positioning 2";
    private final static String OTHER_CUSHIONING = "cushioning 2";
    private final static String OTHER_MARKING = "marking 2";
    private final static String OTHER_REMARK = "remark 2";

    private HandicapCharacteristicsImpl originalHandicapCharacteristics;
    private HandicapCharacteristicsImpl otherHandicapCharacteristics;

    @Before
    public void setup() {
        originalHandicapCharacteristics = new HandicapCharacteristicsImpl(ORIG_INDEX,
                ORIG_FOREIGN_SUITCASE,
                ORIG_FOREIGN_BALL,
                ORIG_POSITIONING,
                ORIG_CUSHIONING,
                ORIG_MARKING,
                ORIG_REMARK);
        resetOtherCharacteristics(List.of());
    }

    @Test
    public void getNextPrimaryKey() {
        List<HandicapCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalHandicapCharacteristics);
        resetOtherCharacteristics(testList);
        testList.add(otherHandicapCharacteristics);
        HandicapCharacteristicsImpl thirdCharacteristics = new HandicapCharacteristicsImpl(testList,
                originalHandicapCharacteristics);
        testList.add(thirdCharacteristics);
        assertEquals("invalid new primary key",
                3,
                HandicapCharacteristicsImpl.getNextPrimaryKey(testList));
    }

    @Test
    public void equals() {
        assertTrue("objects are not equal",
                originalHandicapCharacteristics.equals(originalHandicapCharacteristics));
        assertTrue("data is not equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
        otherHandicapCharacteristics.setForeignKeySuitCase(OTHER_FOREIGN_SUITCASE);
        assertFalse("foreignKeySuitCase is equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
        resetOtherCharacteristics(List.of());
        otherHandicapCharacteristics.setForeignKeyBall(OTHER_FOREIGN_BALL);
        assertFalse("foreignKeyBall is equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
        resetOtherCharacteristics(List.of());
        otherHandicapCharacteristics.setPositioning(OTHER_POSITIONING);
        assertFalse("positioning is equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
        resetOtherCharacteristics(List.of());
        otherHandicapCharacteristics.setCushioning(OTHER_CUSHIONING);
        assertFalse("cushioning is equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
        resetOtherCharacteristics(List.of());
        otherHandicapCharacteristics.setMarking(OTHER_MARKING);
        assertFalse("marking is equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
        resetOtherCharacteristics(List.of());
        otherHandicapCharacteristics.setRemark(OTHER_REMARK);
        assertFalse("remark is equal",
                originalHandicapCharacteristics.equals(otherHandicapCharacteristics));
    }

    @Test
    public void isUnique() {
        List<HandicapCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalHandicapCharacteristics);
        assertTrue("primary key not unique",
                originalHandicapCharacteristics.isUnique(testList));
        testList.add(otherHandicapCharacteristics);
        otherHandicapCharacteristics.setPrimaryKey(ORIG_INDEX);
        assertFalse("primary key unexpected unique",
                originalHandicapCharacteristics.isUnique(testList));
        otherHandicapCharacteristics.setPrimaryKey(otherHandicapCharacteristics.getPrimaryKey() + 1);
        assertTrue("primary key not unique",
                originalHandicapCharacteristics.isUnique(testList));
    }

    private void resetOtherCharacteristics(List<HandicapCharacteristicsImpl> list) {
        otherHandicapCharacteristics = new HandicapCharacteristicsImpl(list, originalHandicapCharacteristics);
    }

}