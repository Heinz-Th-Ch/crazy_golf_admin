package dataStructures;

import abstracts.AbstractPlainJava;
import enumerations.Hardness;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;

/**
 * JUnit tests of {@link DataListContainerImpl}.
 */
public class DataListContainerImplTest extends AbstractPlainJava {

    private final static String BALL_CHARACTERISTICS_IN = "BallCharacteristics-Input.data";
    private final static String BALL_CHARACTERISTICS_OUT = "BallCharacteristics-Output.data";
    private final static String SUIT_CASE_CHARACTERISTICS_IN = "SuitCaseCharacteristics-Input.data";
    private final static String SUIT_CASE_CHARACTERISTICS_OUT = "SuitCaseCharacteristics-Output.data";
    private final static String CRAZY_GOLF_SITE_CHARACTERISTICS_IN = "CrazyGolfSiteCharacteristics-Input.data";
    private final static String CRAZY_GOLF_SITE_CHARACTERISTICS_OUT = "CrazyGolfSiteCharacteristics-Output.data";

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
            dataListContainer.getBallCharacteristics().add(
                    new BallCharacteristicsImpl(dataListContainer.getBallCharacteristics(),
                            "ID 1",
                            "DESC 1",
                            Hardness.H,
                            50,
                            37,
                            0.5,
                            "CMT 1"));
        }
        writeBallCharacteristicsInputTestData(new FileOutputStream(definePathAndFile(BALL_CHARACTERISTICS_IN)),
                dataListContainer.getBallCharacteristics());
        for (int i = 0; i < ORIG_NUMBER_OF_SUIT_CASES; i++) {
            if (i == 0) {
                dataListContainer.getSuitCaseCharacteristics().add(new SuitCaseCharacteristicsImpl(i,
                        "ID 1",
                        "DESC 1",
                        "OWN 1",
                        ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_ONE));
            } else {
                dataListContainer.getSuitCaseCharacteristics().add(new SuitCaseCharacteristicsImpl(i,
                        "ID 1",
                        "DESC 1",
                        "OWN 1",
                        ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_TWO));
            }
        }
        writeBallCharacteristicsInputTestData(new FileOutputStream(definePathAndFile(SUIT_CASE_CHARACTERISTICS_IN)),
                dataListContainer.getSuitCaseCharacteristics());
        for (int i = 0; i < ORIG_NUMBER_OF_CRAZY_GOLF_SITES; i++) {
            dataListContainer.getCrazyGolfSiteCharacteristics().add(new CrazyGolfSiteCharacteristicsImpl(i,
                    i + 1,
                    "SITE 1",
                    "ADR 1",
                    "9999",
                    "TOWN 1"));
        }
        writeBallCharacteristicsInputTestData(new FileOutputStream(definePathAndFile(CRAZY_GOLF_SITE_CHARACTERISTICS_IN)),
                dataListContainer.getCrazyGolfSiteCharacteristics());
    }

    @After
    public void tearDown() {
        dataListContainer.getBallCharacteristics().clear();
        dataListContainer.getCrazyGolfSiteCharacteristics().clear();
        dataListContainer.getSuitCaseCharacteristics().clear();
    }


    @Test
    public void getBallCharacteristics() {
        // act
        List<BallCharacteristicsImpl> list = dataListContainer.getBallCharacteristics();
        // assert
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
        // act
        List<SuitCaseCharacteristicsImpl> list = dataListContainer.getSuitCaseCharacteristics();
        // assert
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
        // act
        List<CrazyGolfSiteCharacteristicsImpl> list = dataListContainer.getCrazyGolfSiteCharacteristics();
        // assert
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
        // arrange
        FileInputStream stream = new FileInputStream(definePathAndFile(BALL_CHARACTERISTICS_IN));
        // act and assert
        assertTrue("load of ballCharacteristics data failed",
                dataListContainer.loadBallCharacteristics(stream));
        // assert
        assertEquals("ballCharacteristics list has an incorrect number of entries",
                ORIG_NUMBER_OF_BALLS,
                dataListContainer.getBallCharacteristics().size());
    }

    @Test
    public void storeBallCharacteristics() throws IOException {
        // arrange
        File testOfFile = new File(definePathAndFile(BALL_CHARACTERISTICS_OUT));
        testOfFile.delete();
        FileOutputStream stream = new FileOutputStream(testOfFile);
        // act and assert
        assertTrue("store of ballCharacteristics data failed",
                dataListContainer.storeBallCharacteristics(stream));
        // assert
        assertTrue("ballCharacteristics file do not exists not anymore", testOfFile.exists());
    }

    @Test
    public void loadSuitCaseCharacteristics() throws IOException, ClassNotFoundException {
        // arrange
        FileInputStream stream = new FileInputStream(definePathAndFile(SUIT_CASE_CHARACTERISTICS_IN));
        // act and assert
        assertTrue("load of suitCaseCharacteristics data failed",
                dataListContainer.loadSuitCaseCharacteristics(stream));
        // assert
        assertEquals("suitCaseCharacteristics list has an incorrect number of entries",
                ORIG_NUMBER_OF_SUIT_CASES,
                dataListContainer.getSuitCaseCharacteristics().size());
        assertEquals("content in suitCaseCharacteristics one has an incorrect number of entries",
                ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_ONE,
                dataListContainer.getSuitCaseCharacteristics().get(0).getContents().size());
        assertEquals("content in suitCaseCharacteristics two has an incorrect number of entries",
                ORIG_NUMBER_OF_SLOTS_IN_SUIT_CASES_TWO,
                dataListContainer.getSuitCaseCharacteristics().get(1).getContents().size());
    }

    @Test
    public void storeSuitCaseCharacteristics() throws IOException {
        // arrange
        File testOfFile = new File(definePathAndFile(SUIT_CASE_CHARACTERISTICS_OUT));
        testOfFile.delete();
        FileOutputStream stream = new FileOutputStream(testOfFile);
        // act and assert
        assertTrue("store of suitCaseCharacteristics data failed",
                dataListContainer.storeSuitCaseCharacteristics(stream));
        // assert
        assertTrue("suitCaseCharacteristics file do not exists not anymore", testOfFile.exists());
    }

    @Test
    public void loadCrazyGolfSiteCharacteristics() throws IOException, ClassNotFoundException {
        // arrange
        FileInputStream stream = new FileInputStream(definePathAndFile(CRAZY_GOLF_SITE_CHARACTERISTICS_IN));
        // act and assert
        assertTrue("load of crazyGolfSiteCharacteristics data failed",
                dataListContainer.loadCrazyGolfSiteCharacteristics(stream));
        // assert
        assertEquals("crazyGolfSiteCharacteristics list has an incorrect number of entries",
                ORIG_NUMBER_OF_CRAZY_GOLF_SITES,
                dataListContainer.getCrazyGolfSiteCharacteristics().size());
        assertEquals("content in crazyGolfSiteCharacteristics list has an incorrect number of entries",
                ORIG_NUMBER_OF_HANDICAPS_ON_GOLF_SITE,
                dataListContainer.getCrazyGolfSiteCharacteristics().get(0).getContents().size());
    }

    @Test
    public void storeCrazyGolfSiteCharacteristics() throws IOException {
        // arrange
        File testOfFile = new File(definePathAndFile(CRAZY_GOLF_SITE_CHARACTERISTICS_OUT));
        testOfFile.delete();
        FileOutputStream stream = new FileOutputStream(testOfFile);
        // act and assert
        assertTrue("store of crazyGolfSiteCharacteristics data failed",
                dataListContainer.storeCrazyGolfSiteCharacteristics(stream));
        // assert
        assertTrue("crazyGolfSiteCharacteristics file do not exists not anymore", testOfFile.exists());
    }

    @NotNull
    private String definePathAndFile(String fileName) throws IOException {
        return createTestPath(getClass().getSimpleName())
                + "/"
                + fileName;
    }

    private void writeBallCharacteristicsInputTestData(FileOutputStream fos, List<?> data) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(fos);
        stream.writeObject(data);
        stream.close();
    }
}