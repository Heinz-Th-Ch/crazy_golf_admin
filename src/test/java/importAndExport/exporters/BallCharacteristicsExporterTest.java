package importAndExport.exporters;

import abstracts.AbstractPlainJava;
import dataStructures.BallCharacteristicsImpl;
import enumerations.Hardness;
import importAndExport.CommonCsvValues;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link BallCharacteristicsExporter}.<br>
 * The only one test is the test for method {@link BallCharacteristicsExporter#executeExport()} because the mocking of
 * final classes is not so easy and so the test tests the whole class.
 */
public class BallCharacteristicsExporterTest extends AbstractPlainJava {

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

    private final static String DATA_LINE_1 = BALL_PRIMARY_KEY_1
            + ";"
            + BALL_IDENTIFIER_1
            + ";"
            + BALL_DESCRIPTION_1
            + ";"
            + BALL_HARDNESS_1.getImportValue()
            + ";"
            + BALL_UP_THROW_1
            + ";"
            + BALL_WEIGHT_1
            + ";"
            + BALL_ANGLE_FACTOR_1
            + ";";
    private final static String DATA_LINE_2 = BALL_PRIMARY_KEY_2
            + ";"
            + BALL_IDENTIFIER_2
            + ";"
            + BALL_DESCRIPTION_2
            + ";"
            + BALL_HARDNESS_2.getImportValue()
            + ";"
            + BALL_UP_THROW_2
            + ";"
            + BALL_WEIGHT_2
            + ";"
            + BALL_ANGLE_FACTOR_2
            + ";"
            + BALL_COMMENT_2;

    private final static String HEAD_LINE = CommonCsvValues.PRIMARY_KEY
            + ";"
            + CommonCsvValues.BC_IDENTIFIER
            + ";"
            + CommonCsvValues.BC_DESCRIPTION
            + ";"
            + CommonCsvValues.BC_HARDNESS
            + ";"
            + CommonCsvValues.BC_UP_THROW
            + ";"
            + CommonCsvValues.BC_WEIGHT
            + ";"
            + CommonCsvValues.BC_ANGLE_FACTOR
            + ";"
            + CommonCsvValues.BC_COMMENT;

    private final String testFileName = "BallCharacteristics.csv";
    private final List<BallCharacteristicsImpl> testList = new ArrayList<>(List.of());

    private File testFile;

    private BallCharacteristicsExporter exporter;

    @Before
    public void setUp() throws Exception {
        testFile = new File(createTestPath(getClass().getSimpleName()) + "/" + testFileName);
        if (testFile.exists()) {
            if (!testFile.delete()) {
                fail("initialization of test failed");
            }
        }
        // prepare data
        testList.add(new BallCharacteristicsImpl(BALL_PRIMARY_KEY_1,
                BALL_IDENTIFIER_1,
                BALL_DESCRIPTION_1,
                BALL_HARDNESS_1,
                BALL_UP_THROW_1,
                BALL_WEIGHT_1,
                BALL_ANGLE_FACTOR_1,
                ""));
        testList.add(new BallCharacteristicsImpl(BALL_PRIMARY_KEY_2,
                BALL_IDENTIFIER_2,
                BALL_DESCRIPTION_2,
                BALL_HARDNESS_2,
                BALL_UP_THROW_2,
                BALL_WEIGHT_2,
                BALL_ANGLE_FACTOR_2,
                BALL_COMMENT_2));
        exporter = new BallCharacteristicsExporter(testList, testFile);
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