package dataStructures;

import abstracts.AbstractPlainJava;
import exceptions.NegativeNumberException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit tests of {@link SuitCaseCharacteristicsImpl}.
 */
public class SuitCaseCharacteristicsImplTest extends AbstractPlainJava {

    private final static Integer ORIG_INDEX = 0;

    private final static String ORIG_IDENTIFIER = "identifier 1";
    private final static String ORIG_DESCRIPTION = "description 1";
    private final static String ORIG_OWNER = "owner 1";
    private final static int ORIG_NUMBER_OF_SLOTS = 80;

    private final static String OTHER_IDENTIFIER = "identifier 2";
    private final static String OTHER_DESCRIPTION = "description 2";
    private final static String OTHER_OWNER = "owner 2";

    private final static Integer NULL_INDEX = null;
    private final static Integer NULL_NUMBER_OF_SLOTS = null;
    private final static List<SuitCaseCharacteristicsImpl> NULL_LIST = null;

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
    public void getNextPrimaryKey() {
        // arrange
        List<SuitCaseCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalSuitCaseCharacteristics);
        SuitCaseCharacteristicsImpl secondSuitCaseCharacteristics = new SuitCaseCharacteristicsImpl(testList,
                originalSuitCaseCharacteristics);
        testList.add(secondSuitCaseCharacteristics);
        SuitCaseCharacteristicsImpl thirdSuitCaseCharacteristics = new SuitCaseCharacteristicsImpl(testList,
                originalSuitCaseCharacteristics);
        testList.add(thirdSuitCaseCharacteristics);
        SuitCaseCharacteristicsImpl fourthSuitCaseCharacteristics = new SuitCaseCharacteristicsImpl(testList,
                originalSuitCaseCharacteristics);
        testList.add(fourthSuitCaseCharacteristics);
        // act and assert
        assertEquals("invalid new primary key",
                4,
                SuitCaseCharacteristicsImpl.getNextPrimaryKey(testList));
    }

    @Test
    public void equals() {
        // act and assert 1
        assertTrue("objects are not equal",
                originalSuitCaseCharacteristics.equals(originalSuitCaseCharacteristics));
        assertTrue("data is not equal",
                originalSuitCaseCharacteristics.equals(otherSuitCaseCharacteristics));
        // arrange 2
        otherSuitCaseCharacteristics.setIdentifier(OTHER_IDENTIFIER);
        // act and assert 2
        assertFalse("identifier is equal",
                originalSuitCaseCharacteristics.equals(otherSuitCaseCharacteristics));
        // arrange 3
        resetOtherCharacteristics(List.of(), true);
        otherSuitCaseCharacteristics.setDescription(OTHER_DESCRIPTION);
        // act and assert 3
        assertFalse("description is equal",
                originalSuitCaseCharacteristics.equals(otherSuitCaseCharacteristics));
        // arrange 4
        resetOtherCharacteristics(List.of(), true);
        otherSuitCaseCharacteristics.setOwner(OTHER_OWNER);
        // act and assert 4
        assertFalse("owner is equal",
                originalSuitCaseCharacteristics.equals(otherSuitCaseCharacteristics));
        // arrange 5
        resetOtherCharacteristics(List.of(), true);
        otherSuitCaseCharacteristics.setIdentifier(OTHER_IDENTIFIER);
        // act and assert 5
        assertFalse("identifier is equal",
                originalSuitCaseCharacteristics.equals(otherSuitCaseCharacteristics));
        // arrange 6
        resetOtherCharacteristics(List.of(), false);
        // act and assert 6
        assertFalse("contents is equal",
                originalSuitCaseCharacteristics.equals(otherSuitCaseCharacteristics));
    }

    @Test
    public void isUnique() {
        // arrange 1
        List<SuitCaseCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalSuitCaseCharacteristics);
        // act and assert 1
        assertTrue("primary key not unique",
                originalSuitCaseCharacteristics.isUnique(testList));
        // arrange 2
        testList.add(otherSuitCaseCharacteristics);
        otherSuitCaseCharacteristics.setPrimaryKey(ORIG_INDEX);
        // act and assert 2
        assertFalse("primary key unexpected unique",
                originalSuitCaseCharacteristics.isUnique(testList));
        // arrange 3
        otherSuitCaseCharacteristics.setPrimaryKey(otherSuitCaseCharacteristics.getPrimaryKey() + 1);
        // act and assert 3
        assertFalse("data combination unexpected unique",
                originalSuitCaseCharacteristics.isUnique(testList));
        // arrange 4
        otherSuitCaseCharacteristics.setIdentifier(OTHER_IDENTIFIER);
        // act and assert 4
        assertTrue("data combination not unique",
                originalSuitCaseCharacteristics.isUnique(testList));
        // arrange 5
        otherSuitCaseCharacteristics.setIdentifier(ORIG_IDENTIFIER);
        otherSuitCaseCharacteristics.setDescription(OTHER_DESCRIPTION);
        // act and assert 5
        assertTrue("data combination not unique",
                originalSuitCaseCharacteristics.isUnique(testList));
        // arrange 6
        otherSuitCaseCharacteristics.setDescription(ORIG_DESCRIPTION);
        otherSuitCaseCharacteristics.setOwner(OTHER_OWNER);
        // act and assert 6
        assertTrue("data combination not unique",
                originalSuitCaseCharacteristics.isUnique(testList));
    }

    @Test
    public void getContents() {
        // act and assert
        assertEquals("invalid size of slots",
                ORIG_NUMBER_OF_SLOTS,
                originalSuitCaseCharacteristics.getContents().size());
        Pair<Integer, Integer> limits = getLimits(originalSuitCaseCharacteristics.getContents());
        assertEquals("lower limit is wrong",
                1,
                (int) limits.getLeft());
        assertEquals("upper limit is wrong",
                ORIG_NUMBER_OF_SLOTS,
                (int) limits.getRight());
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForIndex() {
        // act and assert
        new SuitCaseCharacteristicsImpl(NULL_INDEX,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_OWNER,
                ORIG_NUMBER_OF_SLOTS);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForIdentifier() {
        // act and assert
        new SuitCaseCharacteristicsImpl(ORIG_INDEX,
                null,
                ORIG_DESCRIPTION,
                ORIG_OWNER,
                ORIG_NUMBER_OF_SLOTS);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForDescription() {
        // act and assert
        new SuitCaseCharacteristicsImpl(ORIG_INDEX,
                ORIG_IDENTIFIER,
                null,
                ORIG_OWNER,
                ORIG_NUMBER_OF_SLOTS);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForOwner() {
        // act and assert
        new SuitCaseCharacteristicsImpl(ORIG_INDEX,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                null,
                ORIG_NUMBER_OF_SLOTS);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForNumberOfSlots() {
        // act and assert
        new SuitCaseCharacteristicsImpl(ORIG_INDEX,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_OWNER,
                NULL_NUMBER_OF_SLOTS);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForListWithCorrectObject() {
        // act and assert
        new SuitCaseCharacteristicsImpl(NULL_LIST,
                originalSuitCaseCharacteristics);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForCorrectListAndNullObject() {
        // act and assert
        new SuitCaseCharacteristicsImpl(new ArrayList<>(List.of()),
                null);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForListAndCorrectDetailsAndCorrectObject() {
        // act and assert
        new SuitCaseCharacteristicsImpl(NULL_LIST,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_OWNER,
                new ArrayList<>(List.of()));
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForCorrectListAndCorrectDetailsAndNullObject() {
        // act and assert
        new SuitCaseCharacteristicsImpl(new ArrayList<>(List.of()),
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_OWNER,
                null);
    }

    @Test(expected = NegativeNumberException.class)
    public void checkNotNegativeForNumberOfSlots() {
        // act and assert
        new SuitCaseCharacteristicsImpl(ORIG_INDEX,
                ORIG_IDENTIFIER,
                ORIG_DESCRIPTION,
                ORIG_OWNER,
                -1);
    }

    private Pair<Integer, Integer> getLimits(List<ContentOfSuitCaseImpl> contents) {
        int lowerLimit = Integer.MAX_VALUE;
        int upperLimit = Integer.MIN_VALUE;
        for (ContentOfSuitCaseImpl entry : contents) {
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
            otherSuitCaseCharacteristics = new SuitCaseCharacteristicsImpl(list,
                    originalSuitCaseCharacteristics);
        } else {
            otherSuitCaseCharacteristics = new SuitCaseCharacteristicsImpl(list,
                    originalSuitCaseCharacteristics.getIdentifier(),
                    originalSuitCaseCharacteristics.getDescription(),
                    originalSuitCaseCharacteristics.getOwner(),
                    List.of());
        }
    }

}