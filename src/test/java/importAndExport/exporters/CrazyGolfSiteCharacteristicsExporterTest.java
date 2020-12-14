package importAndExport.exporters;

import abstracts.AbstractPlainJava;
import dataStructures.*;
import enumerations.Hardness;
import importAndExport.CommonCsvValues;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link CrazyGolfSiteCharacteristicsExporter}.<br>
 * The only one test is the test for method {@link CrazyGolfSiteCharacteristicsExporter#executeExport()} because the
 * mocking of final classes is not so easy and so the test tests the whole class.
 */
public class CrazyGolfSiteCharacteristicsExporterTest extends AbstractPlainJava {

    private final static Integer CRAZY_GOLF_SITE_PRIMARY_KEY_1 = 8;
    private final static Integer CRAZY_GOLF_SITE_PRIMARY_KEY_2 = 21;
    private final static Integer CRAZY_GOLF_SITE_FOREIGN_KEY_1 = 7;
    private final static Integer CRAZY_GOLF_SITE_FOREIGN_KEY_2 = 12;
    private final static String CRAZY_GOLF_SITE_SITE_NAME_1 = "Neuhausen am Rheinfall";
    private final static String CRAZY_GOLF_SITE_SITE_NAME_2 = "Dietikon ";
    private final static String CRAZY_GOLF_SITE_ADDRESS_1 = "Langriet";
    private final static String CRAZY_GOLF_SITE_ADDRESS_2 = "Muehlimatt";
    private final static String CRAZY_GOLF_SITE_POST_CODE_1 = "CH-8212";
    private final static String CRAZY_GOLF_SITE_POST_CODE_2 = "8047";
    private final static String CRAZY_GOLF_SITE_TOWN_1 = "Neuhausen am Rheinfall";
    private final static String CRAZY_GOLF_SITE_TOWN_2 = "Dietikon";

    private final static Integer HANDICAP_PRIMARY_KEY_1 = 1;
    private final static Integer HANDICAP_PRIMARY_KEY_2 = 18;
    private final static Integer HANDICAP_PRIMARY_KEY_3 = 1;
    private final static Integer HANDICAP_PRIMARY_KEY_4 = 18;
    private final static Integer HANDICAP_FOREIGN_KEY_1 = 45;
    private final static Integer HANDICAP_FOREIGN_KEY_2 = 718;
    private final static Integer HANDICAP_FOREIGN_KEY_3 = 451;
    private final static Integer HANDICAP_FOREIGN_KEY_4 = 71;
    private final static String HANDICAP_POSITIONING_1 = "12/00";
    private final static String HANDICAP_POSITIONING_2 = "15/30";
    private final static String HANDICAP_POSITIONING_3 = "12/00";
    private final static String HANDICAP_POSITIONING_4 = "15/30";
    private final static String HANDICAP_CUSHIONING_1 = "links, 60 cm nach Verbindung";
    private final static String HANDICAP_CUSHIONING_2 = "";
    private final static String HANDICAP_CUSHIONING_3 = "rechts, nach 9. Platte";
    private final static String HANDICAP_CUSHIONING_4 = "rechts, auf Steinplatte";
    private final static String HANDICAP_MARKING_1 = "Fleck in Bande";
    private final static String HANDICAP_MARKING_2 = "Fleck nach 80cm auf der Bahn";
    private final static String HANDICAP_MARKING_3 = "";
    private final static String HANDICAP_MARKING_4 = "";
    private final static String HANDICAP_REMARK_1 = "mittelstark";
    private final static String HANDICAP_REMARK_2 = "";
    private final static String HANDICAP_REMARK_3 = "leicht";
    private final static String HANDICAP_REMARK_4 = "mit Drall nach rechts";

    private final static Integer BALL_PRIMARY_KEY_1 = 45;
    private final static Integer BALL_PRIMARY_KEY_2 = 718;
    private final static Integer BALL_PRIMARY_KEY_3 = 451;
    private final static Integer BALL_PRIMARY_KEY_4 = 71;
    private final static String BALL_IDENTIFIER_1 = "02";
    private final static String BALL_IDENTIFIER_2 = "B+M K9";
    private final static String BALL_IDENTIFIER_3 = "Wagner 27";
    private final static String BALL_IDENTIFIER_4 = "Bago 8";
    private final static String BALL_DESCRIPTION_1 = "Stein";
    private final static String BALL_DESCRIPTION_2 = "Rohling";
    private final static String BALL_DESCRIPTION_3 = "Stewerding";
    private final static String BALL_DESCRIPTION_4 = "";
    private final static Hardness BALL_HARDNESS_1 = Hardness.H;
    private final static Hardness BALL_HARDNESS_2 = Hardness.S;
    private final static Hardness BALL_HARDNESS_3 = Hardness.H;
    private final static Hardness BALL_HARDNESS_4 = Hardness.S;
    private final static Integer BALL_UP_THROW_1 = 1;
    private final static Integer BALL_UP_THROW_2 = 43;
    private final static Integer BALL_UP_THROW_3 = 28;
    private final static Integer BALL_UP_THROW_4 = 7;
    private final static Integer BALL_WEIGHT_1 = 48;
    private final static Integer BALL_WEIGHT_2 = 30;
    private final static Integer BALL_WEIGHT_3 = 28;
    private final static Integer BALL_WEIGHT_4 = 31;
    private final static Double BALL_ANGLE_FACTOR_1 = 0d;
    private final static Double BALL_ANGLE_FACTOR_2 = 0.9;
    private final static Double BALL_ANGLE_FACTOR_3 = 0.1;
    private final static Double BALL_ANGLE_FACTOR_4 = 0.8;
    private final static String BALL_COMMENT_2 = "evtl. B+M K9";
    private final static String BALL_COMMENT_3 = "auf Temperatur achten";

    private final static Integer SUIT_CASE_PRIMARY_KEY_1 = 7;
    private final static Integer SUIT_CASE_PRIMARY_KEY_2 = 12;
    private final static String SUIT_CASE_IDENTIFIER_1 = "Koffer 1";
    private final static String SUIT_CASE_IDENTIFIER_2 = "Koffer 2";
    private final static String SUIT_CASE_DESCRIPTION_1 = "Test für die eine Bahn";
    private final static String SUIT_CASE_DESCRIPTION_2 = "Test für die andere Bahn";
    private final static String SUIT_CASE_OWNER_1 = "Ich";
    private final static String SUIT_CASE_OWNER_2 = "Du";

    private final static String TEST_CONTENTS_FILE_NAME_1 = "HandicapCharacteristics_NeuhausenAmRheinfall.csv";
    private final static String TEST_CONTENTS_FILE_NAME_2 = "HandicapCharacteristics_Dietikon.csv";

    private final static String DATA_LINE_1 = CRAZY_GOLF_SITE_PRIMARY_KEY_1
            + ";"
            + CRAZY_GOLF_SITE_SITE_NAME_1
            + ";"
            + CRAZY_GOLF_SITE_ADDRESS_1
            + ";"
            + CRAZY_GOLF_SITE_POST_CODE_1
            + ";"
            + CRAZY_GOLF_SITE_TOWN_1
            + ";"
            + SUIT_CASE_IDENTIFIER_1
            + ";"
            + TEST_CONTENTS_FILE_NAME_1;
    private final static String DATA_LINE_2 = CRAZY_GOLF_SITE_PRIMARY_KEY_2
            + ";"
            + CRAZY_GOLF_SITE_SITE_NAME_2
            + ";"
            + CRAZY_GOLF_SITE_ADDRESS_2
            + ";"
            + CRAZY_GOLF_SITE_POST_CODE_2
            + ";"
            + CRAZY_GOLF_SITE_TOWN_2
            + ";"
            + SUIT_CASE_IDENTIFIER_2
            + ";"
            + TEST_CONTENTS_FILE_NAME_2;
    private final static String HEAD_LINE = CommonCsvValues.PRIMARY_KEY
            + ";"
            + CommonCsvValues.CGSC_SITE_NAME
            + ";"
            + CommonCsvValues.CGSC_ADDRESS
            + ";"
            + CommonCsvValues.CGSC_POST_CODE
            + ";"
            + CommonCsvValues.CGSC_TOWN
            + ";"
            + CommonCsvValues.CGSC_SUIT_CASE
            + ";"
            + CommonCsvValues.CGSC_CONTENTS_FILE;

    private final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    private final String testFileName = "CrazyGolfSiteCharacteristics.csv";
    private final List<CrazyGolfSiteCharacteristicsImpl> testList = new ArrayList<>(List.of());

    private File testFile;
    private File testPath;

    private CrazyGolfSiteCharacteristicsExporter exporter;

    @Before
    public void setUp() throws Exception {
        testPath = new File(createTestPath(getClass().getSimpleName()));
        // arrange
        for (File file : testPath.listFiles()) {
            if (!file.delete()) {
                fail("arrange of test failed");
            }
        }
        testFile = new File(testPath + "/" + testFileName);
        // prepare data
        CrazyGolfSiteCharacteristicsImpl entry1 = new CrazyGolfSiteCharacteristicsImpl(CRAZY_GOLF_SITE_PRIMARY_KEY_1,
                CRAZY_GOLF_SITE_FOREIGN_KEY_1,
                CRAZY_GOLF_SITE_SITE_NAME_1,
                CRAZY_GOLF_SITE_ADDRESS_1,
                CRAZY_GOLF_SITE_POST_CODE_1,
                CRAZY_GOLF_SITE_TOWN_1);
        entry1.getContents().clear();
        entry1.getContents().add(new HandicapCharacteristicsImpl(HANDICAP_PRIMARY_KEY_1,
                HANDICAP_FOREIGN_KEY_1,
                HANDICAP_POSITIONING_1,
                HANDICAP_CUSHIONING_1,
                HANDICAP_MARKING_1,
                HANDICAP_REMARK_1));
        entry1.getContents().add(new HandicapCharacteristicsImpl(HANDICAP_PRIMARY_KEY_2,
                HANDICAP_FOREIGN_KEY_2,
                HANDICAP_POSITIONING_2,
                HANDICAP_CUSHIONING_2,
                HANDICAP_MARKING_2,
                HANDICAP_REMARK_2));
        testList.add(entry1);
        CrazyGolfSiteCharacteristicsImpl entry2 = new CrazyGolfSiteCharacteristicsImpl(CRAZY_GOLF_SITE_PRIMARY_KEY_2,
                CRAZY_GOLF_SITE_FOREIGN_KEY_2,
                CRAZY_GOLF_SITE_SITE_NAME_2,
                CRAZY_GOLF_SITE_ADDRESS_2,
                CRAZY_GOLF_SITE_POST_CODE_2,
                CRAZY_GOLF_SITE_TOWN_2);
        entry2.getContents().clear();
        entry2.getContents().add(new HandicapCharacteristicsImpl(HANDICAP_PRIMARY_KEY_3,
                HANDICAP_FOREIGN_KEY_3,
                HANDICAP_POSITIONING_3,
                HANDICAP_CUSHIONING_3,
                HANDICAP_MARKING_3,
                HANDICAP_REMARK_3));
        entry2.getContents().add(new HandicapCharacteristicsImpl(HANDICAP_PRIMARY_KEY_4,
                HANDICAP_FOREIGN_KEY_4,
                HANDICAP_POSITIONING_4,
                HANDICAP_CUSHIONING_4,
                HANDICAP_MARKING_4,
                HANDICAP_REMARK_4));
        testList.add(entry2);
        // prepare data for foreign key to ball characteristics
        dataListContainer.getBallCharacteristics().add(new BallCharacteristicsImpl(BALL_PRIMARY_KEY_1,
                BALL_IDENTIFIER_1,
                BALL_DESCRIPTION_1,
                BALL_HARDNESS_1,
                BALL_UP_THROW_1,
                BALL_WEIGHT_1,
                BALL_ANGLE_FACTOR_1,
                ""));
        dataListContainer.getBallCharacteristics().add(new BallCharacteristicsImpl(BALL_PRIMARY_KEY_2,
                BALL_IDENTIFIER_2,
                BALL_DESCRIPTION_2,
                BALL_HARDNESS_2,
                BALL_UP_THROW_2,
                BALL_WEIGHT_2,
                BALL_ANGLE_FACTOR_2,
                BALL_COMMENT_2));
        dataListContainer.getBallCharacteristics().add(new BallCharacteristicsImpl(BALL_PRIMARY_KEY_3,
                BALL_IDENTIFIER_3,
                BALL_DESCRIPTION_3,
                BALL_HARDNESS_3,
                BALL_UP_THROW_3,
                BALL_WEIGHT_3,
                BALL_ANGLE_FACTOR_3,
                BALL_COMMENT_3));
        dataListContainer.getBallCharacteristics().add(new BallCharacteristicsImpl(BALL_PRIMARY_KEY_4,
                BALL_IDENTIFIER_4,
                BALL_DESCRIPTION_4,
                BALL_HARDNESS_4,
                BALL_UP_THROW_4,
                BALL_WEIGHT_4,
                BALL_ANGLE_FACTOR_4,
                ""));
        // prepare data for foreign key to suit case characteristics
        dataListContainer.getSuitCaseCharacteristics().add(new SuitCaseCharacteristicsImpl(SUIT_CASE_PRIMARY_KEY_1,
                SUIT_CASE_IDENTIFIER_1,
                SUIT_CASE_DESCRIPTION_1,
                SUIT_CASE_OWNER_1,
                1));
        dataListContainer.getSuitCaseCharacteristics().add(new SuitCaseCharacteristicsImpl(SUIT_CASE_PRIMARY_KEY_2,
                SUIT_CASE_IDENTIFIER_2,
                SUIT_CASE_DESCRIPTION_2,
                SUIT_CASE_OWNER_2,
                1));
        exporter = new CrazyGolfSiteCharacteristicsExporter(testList, testFile);
    }

    @After
    public void tearDown() throws Exception {
        dataListContainer.getBallCharacteristics().clear();
        dataListContainer.getSuitCaseCharacteristics().clear();
    }

    @Test
    public void executeExport() throws IOException {
        // act
        exporter.executeExport();
        // arrange
        assertTrue("expected file not exists", testFile.exists());
        assertContentOfFile();
        assertTrue("expected contents file 1 not exists",
                new File(testPath + "/" + TEST_CONTENTS_FILE_NAME_1).exists());
        assertTrue("expected contents file 2 not exists",
                new File(testPath + "/" + TEST_CONTENTS_FILE_NAME_2).exists());
    }

    private void assertContentOfFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(testFile)));
        String headLine = reader.readLine();
        assertEquals("invalid head line received", HEAD_LINE, headLine);
        String dataLine1 = reader.readLine();
        assertEquals("invalid data line 1 received", DATA_LINE_1, dataLine1);
        String dataLine2 = reader.readLine();
        assertEquals("invalid data line 2 received", DATA_LINE_2, dataLine2);
    }

}