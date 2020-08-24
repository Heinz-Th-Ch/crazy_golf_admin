package dataStructures;

import abstracts.AbstractPlainJava;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit tests of {@link CrazyGolfSiteCharacteristicsImpl}.
 */
public class CrazyGolfSiteCharacteristicsImplTest extends AbstractPlainJava {

    private final static Integer ORIG_INDEX = 0;

    private final static String ORIG_SITE_NAME = "site name 1";
    private final static String ORIG_ADDRESS = "address 1";
    private final static String ORIG_POST_CODE = "post code 1";
    private final static String ORIG_TOWN = "town 1";
    private final static int ORIG_AND_ONLY_NUMBER_OF_HANDICAPS = 18;


    private final static String OTHER_SITE_NAME = "site name 2";
    private final static String OTHER_ADDRESS = "address 2";
    private final static String OTHER_POST_CODE = "post code 2";
    private final static String OTHER_TOWN = "town 2";

    private CrazyGolfSiteCharacteristicsImpl originalCrazyGolfSiteCharacteristics;
    private CrazyGolfSiteCharacteristicsImpl otherCrazyGolfSiteCharacteristics;

    @Before
    public void setup() {
        originalCrazyGolfSiteCharacteristics = new CrazyGolfSiteCharacteristicsImpl(ORIG_INDEX,
                ORIG_SITE_NAME,
                ORIG_ADDRESS,
                ORIG_POST_CODE,
                ORIG_TOWN);
        resetOtherCharacteristics(List.of(), true);
    }

    @Test
    public void getNextPrimaryKey() {
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
        assertEquals("invalid new primary key",
                4,
                CrazyGolfSiteCharacteristicsImpl.getNextPrimaryKey(testList));
    }

    @Test
    public void equals() {
        assertTrue("objects are not equal",
                originalCrazyGolfSiteCharacteristics.equals(originalCrazyGolfSiteCharacteristics));
        assertTrue("data is not equal",
                originalCrazyGolfSiteCharacteristics.equals(otherCrazyGolfSiteCharacteristics));
        otherCrazyGolfSiteCharacteristics.setSiteName(OTHER_SITE_NAME);
        assertFalse("site name is equal",
                originalCrazyGolfSiteCharacteristics.equals(otherCrazyGolfSiteCharacteristics));
        resetOtherCharacteristics(List.of(), true);
        otherCrazyGolfSiteCharacteristics.setAddress(OTHER_ADDRESS);
        assertFalse("address is equal",
                originalCrazyGolfSiteCharacteristics.equals(otherCrazyGolfSiteCharacteristics));
        resetOtherCharacteristics(List.of(), true);
        otherCrazyGolfSiteCharacteristics.setPostCode(OTHER_POST_CODE);
        assertFalse("post code is equal",
                originalCrazyGolfSiteCharacteristics.equals(otherCrazyGolfSiteCharacteristics));
        resetOtherCharacteristics(List.of(), true);
        otherCrazyGolfSiteCharacteristics.setTown(OTHER_TOWN);
        assertFalse("town is equal",
                originalCrazyGolfSiteCharacteristics.equals(otherCrazyGolfSiteCharacteristics));
        resetOtherCharacteristics(List.of(), false);
        assertFalse("contents is equal",
                originalCrazyGolfSiteCharacteristics.equals(otherCrazyGolfSiteCharacteristics));
    }

    @Test
    public void isUnique() {
        List<CrazyGolfSiteCharacteristicsImpl> testList = new ArrayList<>(List.of());
        testList.add(originalCrazyGolfSiteCharacteristics);
        assertTrue("primary key not unique",
                originalCrazyGolfSiteCharacteristics.isUnique(testList));
        testList.add(otherCrazyGolfSiteCharacteristics);
        otherCrazyGolfSiteCharacteristics.setPrimaryKey(ORIG_INDEX);
        assertFalse("primary key unexpected unique",
                originalCrazyGolfSiteCharacteristics.isUnique(testList));
        otherCrazyGolfSiteCharacteristics.setPrimaryKey(otherCrazyGolfSiteCharacteristics.getPrimaryKey() + 1);
        assertFalse("data combination unexpected unique",
                originalCrazyGolfSiteCharacteristics.isUnique(testList));
        otherCrazyGolfSiteCharacteristics.setSiteName(OTHER_SITE_NAME);
        assertTrue("data combination not unique",
                originalCrazyGolfSiteCharacteristics.isUnique(testList));
        otherCrazyGolfSiteCharacteristics.setSiteName(ORIG_SITE_NAME);
        otherCrazyGolfSiteCharacteristics.setPostCode(OTHER_POST_CODE);
        assertTrue("data combination not unique",
                originalCrazyGolfSiteCharacteristics.isUnique(testList));
        otherCrazyGolfSiteCharacteristics.setPostCode(ORIG_POST_CODE);
        otherCrazyGolfSiteCharacteristics.setTown(OTHER_TOWN);
        assertTrue("data combination not unique",
                originalCrazyGolfSiteCharacteristics.isUnique(testList));
    }


    @Test
    public void getContents() {
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

    private void resetOtherCharacteristics(List<CrazyGolfSiteCharacteristicsImpl> list, boolean withOriginalData) {
        if (withOriginalData) {
            otherCrazyGolfSiteCharacteristics = new CrazyGolfSiteCharacteristicsImpl(list,
                    originalCrazyGolfSiteCharacteristics);
        } else {
            otherCrazyGolfSiteCharacteristics = new CrazyGolfSiteCharacteristicsImpl(list,
                    originalCrazyGolfSiteCharacteristics.getSiteName(),
                    originalCrazyGolfSiteCharacteristics.getAddress(),
                    originalCrazyGolfSiteCharacteristics.getPostCode(),
                    originalCrazyGolfSiteCharacteristics.getTown(),
                    List.of());
        }
    }
}