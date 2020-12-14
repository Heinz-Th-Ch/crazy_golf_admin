package importAndExport.exporters;

import abstracts.AbstractPlainJava;
import dataStructures.BallCharacteristicsImpl;
import dataStructures.DataListContainerImpl;
import dataStructures.HandicapCharacteristicsImpl;
import enumerations.Hardness;
import importAndExport.CommonCsvValues;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static importAndExport.CommonCsvValues.NOT_APPLICABLE;

/**
 * JUnit test for {@link HandicapCharacteristicsExporter}.<br>
 * The only one test is the test for method {@link HandicapCharacteristicsExporter#executeExport()} because the mocking
 * of final classes is not so easy and so the test tests the whole class.
 */
public class HandicapCharacteristicsExporterTest extends AbstractPlainJava {

    private final static Integer HANDICAP_PRIMARY_KEY_1 = 1;
    private final static Integer HANDICAP_PRIMARY_KEY_2 = 18;
    private final static Integer HANDICAP_FOREIGN_KEY_1 = 45;
    private final static Integer HANDICAP_FOREIGN_KEY_2 = 718;
    private final static String HANDICAP_POSITIONING_1 = "12/00";
    private final static String HANDICAP_POSITIONING_2 = "15/30";
    private final static String HANDICAP_CUSHIONING_1 = "links, 60 cm nach Verbindung";
    private final static String HANDICAP_CUSHIONING_2 = "";
    private final static String HANDICAP_MARKING_1 = "Fleck in Bande";
    private final static String HANDICAP_MARKING_2 = "Fleck nach 80cm auf der Bahn";
    private final static String HANDICAP_REMARK_1 = "mittelstark";
    private final static String HANDICAP_REMARK_2 = "";

    private final static Integer BALL_PRIMARY_KEY_1 = 45;
    private final static Integer BALL_PRIMARY_KEY_2 = 718;
    private final static String BALL_IDENTIFIER_1 = "02";
    private final static String BALL_IDENTIFIER_2 = "B+M K9";
    private final static String BALL_DESCRIPTION_1 = "Stein";
    private final static String BALL_DESCRIPTION_2 = "Rohling";
    private final static Hardness BALL_HARDNESS_1 = Hardness.H;
    private final static Hardness BALL_HARDNESS_2 = Hardness.S;
    private final static Integer BALL_UP_THROW_1 = 1;
    private final static Integer BALL_UP_THROW_2 = 43;
    private final static Integer BALL_WEIGHT_1 = 48;
    private final static Integer BALL_WEIGHT_2 = 30;
    private final static Double BALL_ANGLE_FACTOR_1 = 0d;
    private final static Double BALL_ANGLE_FACTOR_2 = 0.9;
    private final static String BALL_COMMENT_2 = "evtl. B+M K9";

    private final static String DATA_LINE_1 = HANDICAP_PRIMARY_KEY_1
            + ";"
            + BALL_IDENTIFIER_1
            + ";"
            + HANDICAP_POSITIONING_1
            + ";"
            + HANDICAP_CUSHIONING_1
            + ";"
            + HANDICAP_MARKING_1
            + ";"
            + HANDICAP_REMARK_1;
    private final static String DATA_LINE_2 = HANDICAP_PRIMARY_KEY_2
            + ";"
            + BALL_IDENTIFIER_2
            + ";"
            + HANDICAP_POSITIONING_2
            + ";"
            + HANDICAP_CUSHIONING_2
            + ";"
            + HANDICAP_MARKING_2
            + ";"
            + HANDICAP_REMARK_2;
    private final static String HEAD_LINE = CommonCsvValues.HC_PRIMARY_KEY
            + ";"
            + CommonCsvValues.HC_FOREIGN_KEY_BALL
            + ";"
            + CommonCsvValues.HC_POSITIONING
            + ";"
            + CommonCsvValues.HC_CUSHIONING
            + ";"
            + CommonCsvValues.HC_MARKING
            + ";"
            + CommonCsvValues.HC_REMARK;

    private final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    private final String testFileName = "HandicapCharacteristics.csv";
    private final List<HandicapCharacteristicsImpl> testList = new ArrayList<>(List.of());

    private File testFile;

    private HandicapCharacteristicsExporter exporter;

    @Before
    public void setUp() throws Exception {
        testFile = new File(createTestPath(getClass().getSimpleName()) + "/" + testFileName);
        if (testFile.exists()) {
            if (!testFile.delete()) {
                fail("initialization of test failed");
            }
        }
        // prepare data
        testList.add(new HandicapCharacteristicsImpl(HANDICAP_PRIMARY_KEY_1,
                HANDICAP_FOREIGN_KEY_1,
                HANDICAP_POSITIONING_1,
                HANDICAP_CUSHIONING_1,
                HANDICAP_MARKING_1,
                HANDICAP_REMARK_1));
        testList.add(new HandicapCharacteristicsImpl(HANDICAP_PRIMARY_KEY_2,
                HANDICAP_FOREIGN_KEY_2,
                HANDICAP_POSITIONING_2,
                HANDICAP_CUSHIONING_2,
                HANDICAP_MARKING_2,
                HANDICAP_REMARK_2));
        // prepare data for foreign key
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
        exporter = new HandicapCharacteristicsExporter(testList, testFile);
    }

    @After
    public void tearDown() throws Exception {
        dataListContainer.getBallCharacteristics().clear();
    }

    @Test
    public void executeExport() throws IOException {
        // act
        exporter.executeExport();
        // arrange
        assertTrue("expected file not exists", testFile.exists());
        assertContentOfFile();
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