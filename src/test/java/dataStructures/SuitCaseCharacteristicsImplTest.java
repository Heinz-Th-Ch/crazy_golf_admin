package dataStructures;

import abstracts.AbstractPlainJava;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SuitCaseCharacteristicsImplTest extends AbstractPlainJava {

    private final static Integer ORIG_INDEX = 0;

    private final static String ORIG_IDENTIFIER = "identifier 1";
    private final static String ORIG_DESCRIPTION = "description 1";
    private final static String ORIG_OWNER = "owner 1";
    private final static int ORIG_NUMBER_OF_SLOTS = 80;

    private final static String OTHER_IDENTIFIER = "identifier 2";
    private final static String OTHER_DESCRIPTION = "description 2";
    private final static String OTHER_OWNER = "owner 2";

    private SuitCaseCharacteristicsImpl originalSuitCaseCharacteristics;
    private SuitCaseCharacteristicsImpl otherSuitCaseCharacteristics;

    @Before
    public void setup() {
        originalSuitCaseCharacteristics = new SuitCaseCharacteristicsImpl(ORIG_INDEX,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_OWNER,
                ORIG_NUMBER_OF_SLOTS);
        resetOtherCharacteristics(List.of(), true);
    }

    @Test
    public void equals() {
        assertTrue("objects are not equal", originalSuitCaseCharacteristics.equals(originalSuitCaseCharacteristics));
        assertTrue("data is not equal", originalSuitCaseCharacteristics.equals(otherSuitCaseCharacteristics));
        otherSuitCaseCharacteristics.setIdentifier(OTHER_IDENTIFIER);
        assertFalse("identifier is equal", originalSuitCaseCharacteristics.equals(otherSuitCaseCharacteristics));
        resetOtherCharacteristics(List.of(), true);
        otherSuitCaseCharacteristics.setDescription(OTHER_DESCRIPTION);
        assertFalse("description is equal", originalSuitCaseCharacteristics.equals(otherSuitCaseCharacteristics));
        resetOtherCharacteristics(List.of(), true);
        otherSuitCaseCharacteristics.setOwner(OTHER_OWNER);
        assertFalse("owner is equal", originalSuitCaseCharacteristics.equals(otherSuitCaseCharacteristics));
        resetOtherCharacteristics(List.of(), true);
        otherSuitCaseCharacteristics.setIdentifier(OTHER_IDENTIFIER);
        assertFalse("identifier is equal", originalSuitCaseCharacteristics.equals(otherSuitCaseCharacteristics));
        resetOtherCharacteristics(List.of(), false);
        assertFalse("contents is equal", originalSuitCaseCharacteristics.equals(otherSuitCaseCharacteristics));
    }

    @Test
    public void isUnique() {
        List<SuitCaseCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalSuitCaseCharacteristics);
        assertTrue("primary key not unique", originalSuitCaseCharacteristics.isUnique(testList));
        testList.add(otherSuitCaseCharacteristics);
        otherSuitCaseCharacteristics.setPrimaryKey(ORIG_INDEX);
        assertFalse("primary key unexpected unique", originalSuitCaseCharacteristics.isUnique(testList));
        otherSuitCaseCharacteristics.setPrimaryKey(otherSuitCaseCharacteristics.getPrimaryKey() + 1);
        assertFalse("data combination unexpected unique", originalSuitCaseCharacteristics.isUnique(testList));
        otherSuitCaseCharacteristics.setIdentifier(OTHER_IDENTIFIER);
        assertTrue("data combination not unique", originalSuitCaseCharacteristics.isUnique(testList));
        otherSuitCaseCharacteristics.setIdentifier(ORIG_IDENTIFIER);
        otherSuitCaseCharacteristics.setDescription(OTHER_DESCRIPTION);
        assertTrue("data combination not unique", originalSuitCaseCharacteristics.isUnique(testList));
        otherSuitCaseCharacteristics.setDescription(ORIG_DESCRIPTION);
        otherSuitCaseCharacteristics.setOwner(OTHER_OWNER);
        assertTrue("data combination not unique", originalSuitCaseCharacteristics.isUnique(testList));
    }

    @Test
    public void getNextPrimaryKey() {
        List<SuitCaseCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalSuitCaseCharacteristics);
        SuitCaseCharacteristicsImpl secondSuitCaseCharacteristics = new SuitCaseCharacteristicsImpl(testList, originalSuitCaseCharacteristics);
        testList.add(secondSuitCaseCharacteristics);
        SuitCaseCharacteristicsImpl thirdSuitCaseCharacteristics = new SuitCaseCharacteristicsImpl(testList, originalSuitCaseCharacteristics);
        testList.add(thirdSuitCaseCharacteristics);
        SuitCaseCharacteristicsImpl fourthSuitCaseCharacteristics = new SuitCaseCharacteristicsImpl(testList, originalSuitCaseCharacteristics);
        testList.add(fourthSuitCaseCharacteristics);
        assertEquals("invalid new primary key", 4, SuitCaseCharacteristicsImpl.getNextPrimaryKey(testList));
    }

    @Test
    public void getContents() {
        assertEquals("invalid size of slots", ORIG_NUMBER_OF_SLOTS, originalSuitCaseCharacteristics.getContents().size());
        Pair<Integer, Integer> limits = getLimits(originalSuitCaseCharacteristics.getContents());
        assertEquals("lower limit is wrong", 1, (int) limits.getLeft());
        assertEquals("upper limit is wrong", ORIG_NUMBER_OF_SLOTS, (int) limits.getRight());
    }

    private Pair<Integer, Integer> getLimits(List<ContentOfSuitcaseImpl> contents) {
        int lowerLimit = 9999;
        int upperLimit = -9999;
        for (ContentOfSuitcaseImpl entry : contents) {
            if (lowerLimit > entry.getPrimaryKey()) {
                lowerLimit = entry.getPrimaryKey();
            }
            if (upperLimit < entry.getPrimaryKey()) {
                upperLimit = entry.getPrimaryKey();
            }
        }
        return Pair.of(lowerLimit, upperLimit);
    }

    private void resetOtherCharacteristics(List<SuitCaseCharacteristicsImpl> list, boolean withOriginalData) {
        if (withOriginalData) {
            otherSuitCaseCharacteristics = new SuitCaseCharacteristicsImpl(list, originalSuitCaseCharacteristics);
        } else {
            otherSuitCaseCharacteristics = new SuitCaseCharacteristicsImpl(list,
                    originalSuitCaseCharacteristics.getIdentifier(),
                    originalSuitCaseCharacteristics.getDescription(),
                    originalSuitCaseCharacteristics.getOwner(),
                    List.of());
        }
    }

}