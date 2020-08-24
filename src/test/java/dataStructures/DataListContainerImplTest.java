package dataStructures;

import abstracts.AbstractPlainJava;
import enumerations.Hardness;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static dataStructures.DataListContainerImpl.*;

/**
 * JUnit tests of {@link DataListContainerImpl}.
 */
public class DataListContainerImplTest extends AbstractPlainJava {

    private final static String BALL_CHARACTERISTICS_IN = "DataListContainerImplTest-BallCharacteristics-Input.data";
    private final static String BALL_CHARACTERISTICS_OUT = "DataListContainerImplTest-BallCharacteristics-Output.data";
    private final static String SUIT_CASE_CHARACTERISTICS_IN = "DataListContainerImplTest-SuitCaseCharacteristics-Input.data";
    private final static String SUIT_CASE_CHARACTERISTICS_OUT = "DataListContainerImplTest-SuitCaseCharacteristics-Output.data";
    private final static String CRAZY_GOLF_SITE_CHARACTERISTICS_IN = "DataListContainerImplTest-CrazyGolfSiteCharacteristics-Input.data";
    private final static String CRAZY_GOLF_SITE_CHARACTERISTICS_OUT = "DataListContainerImplTest-CrazyGolfSiteCharacteristics-Output.data";

    private final static int ORIG_NUMBER_OF_BALLS = 33;
    private final static int ORIG_NUMBER_OF_SUIT_CASES = 2;
    private final static int ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_ONE = 80;
    private final static int ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_TWO = 15;
    private final static int ORIG_NUMBER_OF_CRAZY_GOLF_SITES = 1;
    private final static int ORIG_NUMBER_OF_HANDICAPS_ON_GOLF_SITE = 18;

    DataListContainerImpl dataListContainer = new DataListContainerImpl();

    @Before
    public void before() throws IOException {
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
        writeBallCharacteristicsInputTestData(new FileOutputStream(getTestDataPath() + BALL_CHARACTERISTICS_IN),
                ballCharacteristics);
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
        writeBallCharacteristicsInputTestData(new FileOutputStream(getTestDataPath() + SUIT_CASE_CHARACTERISTICS_IN),
                suitCaseCharacteristics);
        for (int i = 0; i < ORIG_NUMBER_OF_CRAZY_GOLF_SITES; i++) {
            crazyGolfSiteCharacteristics.add(new CrazyGolfSiteCharacteristicsImpl(i,
                    "SITE 1",
                    "ADR 1",
                    "9999",
                    "TOWN 1"));
        }
        writeBallCharacteristicsInputTestData(new FileOutputStream(getTestDataPath() + CRAZY_GOLF_SITE_CHARACTERISTICS_IN),
                crazyGolfSiteCharacteristics);
    }

    @After
    public void after() {
        ballCharacteristics.clear();
        suitCaseCharacteristics.clear();
        crazyGolfSiteCharacteristics.clear();
    }

    @Test
    public void getBallCharacteristics() {
        List<BallCharacteristicsImpl> list = dataListContainer.getBallCharacteristics();

        assertEquals("error in setup of ballCharacteristics for dummy test",
                ORIG_NUMBER_OF_BALLS,
                list.size());
        for (BallCharacteristicsImpl firstElement : list) {
            for (BallCharacteristicsImpl secondElement : list) {
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
        List<SuitCaseCharacteristicsImpl> list = dataListContainer.getSuitCaseCharacteristics();

        assertEquals("error in setup of suitCaseCharacteristics for dummy test",
                ORIG_NUMBER_OF_SUIT_CASES,
                list.size());
        for (int i = 0; i < ORIG_NUMBER_OF_SUIT_CASES; i++) {
            if (i == 0) {
                assertEquals("error in setup of suitCaseCharacteristics.contents for dummy test",
                        ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_ONE,
                        list.get(i).getContents().size());
            } else {
                assertEquals("error in setup of suitCaseCharacteristics.contents for dummy test",
                        ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_TWO,
                        list.get(i).getContents().size());
            }
        }
        for (SuitCaseCharacteristicsImpl firstElement : list) {
            for (SuitCaseCharacteristicsImpl secondElement : list) {
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
        List<CrazyGolfSiteCharacteristicsImpl> list = dataListContainer.getCrazyGolfSiteCharacteristics();

        assertEquals("error in setup of crazyGolfSiteCharacteristics for dummy test",
                ORIG_NUMBER_OF_CRAZY_GOLF_SITES,
                list.size());
        assertEquals("error in setup of crazyGolfSiteCharacteristics.contents for dummy test",
                ORIG_NUMBER_OF_HANDICAPS_ON_GOLF_SITE,
                list.get(0).getContents().size());
        for (HandicapCharacteristicsImpl firstElement : list.get(0).getContents()) {
            for (HandicapCharacteristicsImpl secondElement : list.get(0).getContents()) {
                if (firstElement != secondElement) {
                    assertNotEquals("primary key of crazyGolfSiteCharacteristics.contents not unique",
                            firstElement.getPrimaryKey(),
                            secondElement.getPrimaryKey());
                }
            }
        }
    }

    @Test
    public void loadBallCharacteristics() throws IOException, ClassNotFoundException {
        FileInputStream stream = new FileInputStream(getTestDataPath() + BALL_CHARACTERISTICS_IN);
        assertTrue("load of ballCharacteristics data failed",
                dataListContainer.loadBallCharacteristics(stream));
        assertEquals("ballCharacteristics list has an incorrect number of entries",
                ORIG_NUMBER_OF_BALLS,
                ballCharacteristics.size());
    }

    @Test
    public void storeBallCharacteristics() throws IOException {
        File testOfFile = new File(getTestDataPath() + BALL_CHARACTERISTICS_OUT);
        testOfFile.delete();
        FileOutputStream stream = new FileOutputStream(getTestDataPath() + BALL_CHARACTERISTICS_OUT);
        assertTrue("store of ballCharacteristics data failed",
                dataListContainer.storeBallCharacteristics(stream));
        assertTrue("ballCharacteristics file do not exists not anymore", testOfFile.exists());
    }

    @Test
    public void loadSuitCaseCharacteristics() throws IOException, ClassNotFoundException {
        FileInputStream stream = new FileInputStream(getTestDataPath() + SUIT_CASE_CHARACTERISTICS_IN);
        assertTrue("load of suitCaseCharacteristics data failed",
                dataListContainer.loadSuitCaseCharacteristics(stream));
        assertEquals("suitCaseCharacteristics list has an incorrect number of entries",
                ORIG_NUMBER_OF_SUIT_CASES,
                suitCaseCharacteristics.size());
        assertEquals("content in suitCaseCharacteristics one has an incorrect number of entries",
                ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_ONE,
                suitCaseCharacteristics.get(0).getContents().size());
        assertEquals("content in suitCaseCharacteristics two has an incorrect number of entries",
                ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_TWO,
                suitCaseCharacteristics.get(1).getContents().size());
    }

    @Test
    public void storeSuitCaseCharacteristics() throws IOException {
        File testOfFile = new File(getTestDataPath() + SUIT_CASE_CHARACTERISTICS_OUT);
        testOfFile.delete();
        FileOutputStream stream = new FileOutputStream(getTestDataPath() + SUIT_CASE_CHARACTERISTICS_OUT);
        assertTrue("store of suitCaseCharacteristics data failed",
                dataListContainer.storeSuitCaseCharacteristics(stream));
        assertTrue("suitCaseCharacteristics file do not exists not anymore", testOfFile.exists());
    }

    @Test
    public void loadCrazyGolfSiteCharacteristics() throws IOException, ClassNotFoundException {
        FileInputStream stream = new FileInputStream(getTestDataPath() + CRAZY_GOLF_SITE_CHARACTERISTICS_IN);
        assertTrue("load of crazyGolfSiteCharacteristics data failed",
                dataListContainer.loadCrazyGolfSiteCharacteristics(stream));
        assertEquals("crazyGolfSiteCharacteristics list has an incorrect number of entries",
                ORIG_NUMBER_OF_CRAZY_GOLF_SITES,
                crazyGolfSiteCharacteristics.size());
        assertEquals("content in crazyGolfSiteCharacteristics list has an incorrect number of entries",
                ORIG_NUMBER_OF_HANDICAPS_ON_GOLF_SITE,
                crazyGolfSiteCharacteristics.get(0).getContents().size());
    }

    @Test
    public void storeCrazyGolfSiteCharacteristics() throws IOException {
        File testOfFile = new File(getTestDataPath() + CRAZY_GOLF_SITE_CHARACTERISTICS_OUT);
        testOfFile.delete();
        FileOutputStream stream = new FileOutputStream(getTestDataPath() + CRAZY_GOLF_SITE_CHARACTERISTICS_OUT);
        assertTrue("store of crazyGolfSiteCharacteristics data failed",
                dataListContainer.storeCrazyGolfSiteCharacteristics(stream));
        assertTrue("crazyGolfSiteCharacteristics file do not exists not anymore", testOfFile.exists());
    }

    private void writeBallCharacteristicsInputTestData(FileOutputStream fos, List<?> data) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(fos);
        stream.writeObject(data);
        stream.close();
    }
}