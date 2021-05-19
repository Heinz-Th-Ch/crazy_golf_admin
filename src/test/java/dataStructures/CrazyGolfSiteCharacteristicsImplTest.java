package dataStructures;

import abstracts.AbstractPlainJava;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit tests of {@link CrazyGolfSiteCharacteristicsImpl}.
 */
public class CrazyGolfSiteCharacteristicsImplTest extends AbstractPlainJava {

    private final static String[] FIELD_NAMES = {"primaryKey",
            "foreignKeySuitCase",
            "siteName",
            "address",
            "postCode",
            "town",
            "contents"
    };
    private final static int NUMBER_OF_FIELDS = 7;

    private final static Integer ORIG_INDEX = 0;

    private final static Integer ORIG_FOREIGN_KEY = 22;

    private final static String ORIG_SITE_NAME = "site name 1";
    private final static String ORIG_ADDRESS = "address 1";
    private final static String ORIG_POST_CODE = "post code 1";
    private final static String ORIG_TOWN = "town 1";
    private final static int ORIG_AND_ONLY_NUMBER_OF_HANDICAPS = 18;

    private final static String OTHER_SITE_NAME = "site name 2";
    private final static String OTHER_ADDRESS = "address 2";
    private final static String OTHER_POST_CODE = "post code 2";
    private final static String OTHER_TOWN = "town 2";

    private final static Integer NULL_INDEX = null;
    private final static List<CrazyGolfSiteCharacteristicsImpl> NULL_LIST = null;

    private CrazyGolfSiteCharacteristicsImpl originalCrazyGolfSiteCharacteristics;
    private CrazyGolfSiteCharacteristicsImpl otherCrazyGolfSiteCharacteristics;

    @Before
    public void setup() {
        originalCrazyGolfSiteCharacteristics = new CrazyGolfSiteCharacteristicsImpl(ORIG_INDEX,
                ORIG_FOREIGN_KEY,
                ORIG_SITE_NAME,
                ORIG_ADDRESS,
                ORIG_POST_CODE,
                ORIG_TOWN);
        resetOtherCharacteristics(List.of(), true);
    }

    @Test
    public void getNextPrimaryKey() {
        // arrange
        List<CrazyGolfSiteCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalCrazyGolfSiteCharacteristics);
        CrazyGolfSiteCharacteristicsImpl secondCrazyGolfSiteCharacteristics = new CrazyGolfSiteCharacteristicsImpl(testList,
                originalCrazyGolfSiteCharacteristics);
        testList.add(secondCrazyGolfSiteCharacteristics);
        CrazyGolfSiteCharacteristicsImpl thirdCrazyGolfSiteCharacteristics = new CrazyGolfSiteCharacteristicsImpl(testList,
                originalCrazyGolfSiteCharacteristics);
        testList.add(thirdCrazyGolfSiteCharacteristics);
        CrazyGolfSiteCharacteristicsImpl fourthCrazyGolfSiteCharacteristics = new CrazyGolfSiteCharacteristicsImpl(testList,
                originalCrazyGolfSiteCharacteristics);
        testList.add(fourthCrazyGolfSiteCharacteristics);
        // act and assert
        assertEquals("invalid new primary key",
                4,
                CrazyGolfSiteCharacteristicsImpl.getNextPrimaryKey(testList));
    }

    @Test
    public void equals() {
        // act and assert 1
        assertTrue("objects are not equal",
                originalCrazyGolfSiteCharacteristics.equals(originalCrazyGolfSiteCharacteristics));
        assertTrue("data is not equal",
                originalCrazyGolfSiteCharacteristics.equals(otherCrazyGolfSiteCharacteristics));
        // arrange 2
        otherCrazyGolfSiteCharacteristics.setSiteName(OTHER_SITE_NAME);
        // act and assert 2
        assertFalse("site name is equal",
                originalCrazyGolfSiteCharacteristics.equals(otherCrazyGolfSiteCharacteristics));
        // arrange 3
        resetOtherCharacteristics(List.of(), true);
        otherCrazyGolfSiteCharacteristics.setAddress(OTHER_ADDRESS);
        // act and assert 3
        assertFalse("address is equal",
                originalCrazyGolfSiteCharacteristics.equals(otherCrazyGolfSiteCharacteristics));
        // arrange 4
        resetOtherCharacteristics(List.of(), true);
        otherCrazyGolfSiteCharacteristics.setPostCode(OTHER_POST_CODE);
        // act and assert 4
        assertFalse("post code is equal",
                originalCrazyGolfSiteCharacteristics.equals(otherCrazyGolfSiteCharacteristics));
        // arrange 5
        resetOtherCharacteristics(List.of(), true);
        otherCrazyGolfSiteCharacteristics.setTown(OTHER_TOWN);
        // act and assert 5
        assertFalse("town is equal",
                originalCrazyGolfSiteCharacteristics.equals(otherCrazyGolfSiteCharacteristics));
        // arrange 6
        resetOtherCharacteristics(List.of(), false);
        // act and assert 6
        assertFalse("contents is equal",
                originalCrazyGolfSiteCharacteristics.equals(otherCrazyGolfSiteCharacteristics));
    }

    @Test
    public void isUnique() {
        // arrange 1
        List<CrazyGolfSiteCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalCrazyGolfSiteCharacteristics);
        // act and assert 1
        assertTrue("primary key not unique",
                originalCrazyGolfSiteCharacteristics.isUnique(testList));
        // arrange 2
        testList.add(otherCrazyGolfSiteCharacteristics);
        otherCrazyGolfSiteCharacteristics.setPrimaryKey(ORIG_INDEX);
        // act and assert 2
        assertFalse("primary key unexpected unique",
                originalCrazyGolfSiteCharacteristics.isUnique(testList));
        // arrange 3
        otherCrazyGolfSiteCharacteristics.setPrimaryKey(otherCrazyGolfSiteCharacteristics.getPrimaryKey() + 1);
        // act and assert 3
        assertFalse("data combination unexpected unique",
                originalCrazyGolfSiteCharacteristics.isUnique(testList));
        // arrange 4
        otherCrazyGolfSiteCharacteristics.setSiteName(OTHER_SITE_NAME);
        // act and assert 4
        assertTrue("data combination not unique",
                originalCrazyGolfSiteCharacteristics.isUnique(testList));
        // arrange 5
        otherCrazyGolfSiteCharacteristics.setSiteName(ORIG_SITE_NAME);
        otherCrazyGolfSiteCharacteristics.setPostCode(OTHER_POST_CODE);
        // act and assert 5
        assertTrue("data combination not unique",
                originalCrazyGolfSiteCharacteristics.isUnique(testList));
        // arrange 6
        otherCrazyGolfSiteCharacteristics.setPostCode(ORIG_POST_CODE);
        otherCrazyGolfSiteCharacteristics.setTown(OTHER_TOWN);
        // act and assert 6
        assertTrue("data combination not unique",
                originalCrazyGolfSiteCharacteristics.isUnique(testList));
    }


    @Test
    public void getContents() {
        // act and assert
        assertEquals("invalid size of slots",
                ORIG_AND_ONLY_NUMBER_OF_HANDICAPS,
                originalCrazyGolfSiteCharacteristics.getContents().size());
        Pair<Integer, Integer> limits = getLimits(originalCrazyGolfSiteCharacteristics.getContents());
        assertEquals("lower limit is wrong",
                1,
                (int) limits.getLeft());
        assertEquals("upper limit is wrong",
                ORIG_AND_ONLY_NUMBER_OF_HANDICAPS,
                (int) limits.getRight());
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForIndex() {
        // act and assert
        new CrazyGolfSiteCharacteristicsImpl(NULL_INDEX,
                ORIG_FOREIGN_KEY,
                ORIG_SITE_NAME,
                ORIG_ADDRESS,
                ORIG_POST_CODE,
                ORIG_TOWN);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForForeignKeySuitCase() {
        // act and assert
        new CrazyGolfSiteCharacteristicsImpl(ORIG_INDEX,
                NULL_INDEX,
                ORIG_SITE_NAME,
                ORIG_ADDRESS,
                ORIG_POST_CODE,
                ORIG_TOWN);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForSiteName() {
        // act and assert
        new CrazyGolfSiteCharacteristicsImpl(ORIG_INDEX,
                ORIG_FOREIGN_KEY,
                null,
                ORIG_ADDRESS,
                ORIG_POST_CODE,
                ORIG_TOWN);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForAddress() {
        // act and assert
        new CrazyGolfSiteCharacteristicsImpl(ORIG_INDEX,
                ORIG_FOREIGN_KEY,
                ORIG_SITE_NAME,
                null,
                ORIG_POST_CODE,
                ORIG_TOWN);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForPostCode() {
        // act and assert
        new CrazyGolfSiteCharacteristicsImpl(ORIG_INDEX,
                ORIG_FOREIGN_KEY,
                ORIG_SITE_NAME,
                ORIG_ADDRESS,
                null,
                ORIG_TOWN);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForTown() {
        // act and assert
        new CrazyGolfSiteCharacteristicsImpl(ORIG_INDEX,
                ORIG_FOREIGN_KEY,
                ORIG_SITE_NAME,
                ORIG_ADDRESS,
                ORIG_POST_CODE,
                null);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForListWithCorrectObject(){
        // act and assert
        new CrazyGolfSiteCharacteristicsImpl(NULL_LIST,
                originalCrazyGolfSiteCharacteristics);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForCorrectListAndNullObject(){
        // act and assert
        new CrazyGolfSiteCharacteristicsImpl(new ArrayList<>(List.of()),
                null);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForListWithCorrectDetailAndObject(){
        // act and assert
        new CrazyGolfSiteCharacteristicsImpl(NULL_LIST,
                ORIG_FOREIGN_KEY,
                ORIG_SITE_NAME,
                ORIG_ADDRESS,
                ORIG_POST_CODE,
                ORIG_TOWN,
                new ArrayList<>(List.of()));
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForCorrectListWithCorrectDetailAndNullObject(){
        // act and assert
        new CrazyGolfSiteCharacteristicsImpl(new ArrayList<>(List.of()),
                ORIG_FOREIGN_KEY,
                ORIG_SITE_NAME,
                ORIG_ADDRESS,
                ORIG_POST_CODE,
                ORIG_TOWN,
                null);
    }

    private Pair<Integer, Integer> getLimits(List<HandicapCharacteristicsImpl> contents) {
        int lowerLimit = 9999;
        int upperLimit = -9999;
        for (HandicapCharacteristicsImpl entry : contents) {
            if (lowerLimit > entry.getPrimaryKey()) {
                lowerLimit = entry.getPrimaryKey();
            }
            if (upperLimit < entry.getPrimaryKey()) {
                upperLimit = entry.getPrimaryKey();
            }
        }
        return Pair.of(lowerLimit, upperLimit);
    }

    @Test
    public void numberAndOrderOfField() {
        // act
        Field[] fields = CrazyGolfSiteCharacteristicsImpl.class.getDeclaredFields();
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

    private void resetOtherCharacteristics(List<CrazyGolfSiteCharacteristicsImpl> list, boolean withOriginalData) {
        if (withOriginalData) {
            otherCrazyGolfSiteCharacteristics = new CrazyGolfSiteCharacteristicsImpl(list,
                    originalCrazyGolfSiteCharacteristics);
        } else {
            otherCrazyGolfSiteCharacteristics = new CrazyGolfSiteCharacteristicsImpl(list,
                    originalCrazyGolfSiteCharacteristics.getForeignKeySuitCase(),
                    originalCrazyGolfSiteCharacteristics.getSiteName(),
                    originalCrazyGolfSiteCharacteristics.getAddress(),
                    originalCrazyGolfSiteCharacteristics.getPostCode(),
                    originalCrazyGolfSiteCharacteristics.getTown(),
                    List.of());
        }
    }
}