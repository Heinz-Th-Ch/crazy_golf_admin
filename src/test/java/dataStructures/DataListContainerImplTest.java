package dataStructures;

import abstracts.AbstractPlainJava;
import enumerations.Hardness;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DataListContainerImplTest extends AbstractPlainJava {

    private final static int ORIG_NUMBER_OF_BALLS = 33;
    private final static int ORIG_NUMBER_OF_SUIT_CASES = 2;
    private final static int ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_ONE = 80;
    private final static int ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_TWO = 15;
    private final static int ORIG_NUMBER_OF_CRAZY_GOLF_SITES = 1;
    private final static int ORIG_NUMBER_OF_HANDICAPS_ON_GOLF_SITE = 18;

    private final List<BallCharacteristicsImpl> ballCharacteristics = new ArrayList<>(List.of());
    private final List<SuitCaseCharacteristicsImpl> suitCaseCharacteristics = new ArrayList<>(List.of());
    private final List<CrazyGolfSiteCharacteristicsImpl> crazyGolfSiteCharacteristics = new ArrayList<>(List.of());

    @Before
    public void setup() {
        for (int i = 0; i < ORIG_NUMBER_OF_BALLS; i++) {
            ballCharacteristics.add(new BallCharacteristicsImpl(ballCharacteristics,
                    "ID 1",
                    "DESC 1",
                    Hardness.H,
                    50,
                    37,
                    0.5,
                    "CMT 1"));
        }
        for (int i = 0; i < ORIG_NUMBER_OF_SUIT_CASES; i++) {
            if (i == 0) {
                suitCaseCharacteristics.add(new SuitCaseCharacteristicsImpl(i,
                        "ID 1",
                        "DESC 1",
                        "OWN 1",
                        ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_ONE));
            } else {
                suitCaseCharacteristics.add(new SuitCaseCharacteristicsImpl(i,
                        "ID 1",
                        "DESC 1",
                        "OWN 1",
                        ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_TWO));
            }
        }
        for (int i = 0; i < ORIG_NUMBER_OF_CRAZY_GOLF_SITES; i++) {
            crazyGolfSiteCharacteristics.add(new CrazyGolfSiteCharacteristicsImpl(i,
                    "SITE 1",
                    "ADDR 1",
                    "9999",
                    "TOWN 1"));
        }
    }

    @Test
    public void getBallCharacteristics() {
        assertEquals("error in setup of ballCharacteristics for dummy test",
                ORIG_NUMBER_OF_BALLS,
                ballCharacteristics.size());
        for (BallCharacteristicsImpl firstElement : ballCharacteristics) {
            for (BallCharacteristicsImpl secondElement : ballCharacteristics) {
                if (firstElement != secondElement) {
                    assertNotEquals("primary key of ballCharacteristics not unique",
                            firstElement.getPrimaryKey(),
                            secondElement.getPrimaryKey());
                }
            }
        }
    }

    @Test
    public void getSuitCaseCharacteristics() {
        assertEquals("error in setup of suitCaseCharacteristics for dummy test",
                ORIG_NUMBER_OF_SUIT_CASES,
                suitCaseCharacteristics.size());
        for (int i = 0; i < ORIG_NUMBER_OF_SUIT_CASES; i++) {
            if (i == 0) {
                assertEquals("error in setup of suitCaseCharacteristics.contents for dummy test",
                        ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_ONE,
                        suitCaseCharacteristics.get(i).getContents().size());
            } else {
                assertEquals("error in setup of suitCaseCharacteristics.contents for dummy test",
                        ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_TWO,
                        suitCaseCharacteristics.get(i).getContents().size());
            }
        }
        for (SuitCaseCharacteristicsImpl firstElement : suitCaseCharacteristics) {
            for (SuitCaseCharacteristicsImpl secondElement : suitCaseCharacteristics) {
                if (firstElement != secondElement) {
                    assertNotEquals("primary key of suitCaseCharacteristics not unique",
                            firstElement.getPrimaryKey(),
                            secondElement.getPrimaryKey());

                    for (ContentOfSuitCaseImpl firstContent : firstElement.getContents()) {
                        for (ContentOfSuitCaseImpl secondContent : firstElement.getContents()) {
                            if (firstContent != secondContent) {
                                assertNotEquals("primary key of suitCaseCharacteristics.contents not unique",
                                        firstElement.getPrimaryKey(),
                                        secondElement.getPrimaryKey());
                            }
                        }
                    }

                }
            }
        }
    }

    @Test
    public void getCrazyGolfSiteCharacteristics() {
        assertEquals("error in setup of crazyGolfSiteCharacteristics for dummy test",
                ORIG_NUMBER_OF_CRAZY_GOLF_SITES,
                crazyGolfSiteCharacteristics.size());
        assertEquals("error in setup of crazyGolfSiteCharacteristics.contents for dummy test",
                ORIG_NUMBER_OF_HANDICAPS_ON_GOLF_SITE,
                crazyGolfSiteCharacteristics.get(0).getContents().size());
        for (HandicapCharacteristicsImpl firstElement : crazyGolfSiteCharacteristics.get(0).getContents()) {
            for (HandicapCharacteristicsImpl secondElement : crazyGolfSiteCharacteristics.get(0).getContents()) {
                if (firstElement != secondElement) {
                    assertNotEquals("primary key of crazyGolfSiteCharacteristics.contents not unique",
                            firstElement.getPrimaryKey(),
                            secondElement.getPrimaryKey());
                }
            }
        }
    }

}