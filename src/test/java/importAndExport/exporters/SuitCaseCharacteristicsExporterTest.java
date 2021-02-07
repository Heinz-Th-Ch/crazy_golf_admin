package importAndExport.exporters;

import abstracts.AbstractPlainJava;
import dataStructures.ContentOfSuitCaseImpl;
import dataStructures.SuitCaseCharacteristicsImpl;
import utilitites.CommonCsvValueUtility;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link SuitCaseCharacteristicsExporter}.<br>
 * The only one test is the test for method {@link SuitCaseCharacteristicsExporter#executeExport()} because the
 * mocking of final classes is not so easy and so the test tests the whole class.
 */
public class SuitCaseCharacteristicsExporterTest extends AbstractPlainJava {

    private final static CommonCsvValueUtility COMMON_CSV_VALUES = new CommonCsvValueUtility();

    private final static Integer SUIT_CASE_PRIMARY_KEY_1 = 7;
    private final static Integer SUIT_CASE_PRIMARY_KEY_2 = 12;
    private final static String SUIT_CASE_IDENTIFIER_1 = "Koffer 1";
    private final static String SUIT_CASE_IDENTIFIER_2 = "Koffer 2";
    private final static String SUIT_CASE_DESCRIPTION_1 = "Test fuer die eine Bahn";
    private final static String SUIT_CASE_DESCRIPTION_2 = "Test fuer die andere Bahn";
    private final static String SUIT_CASE_OWNER_1 = "Ich";
    private final static String SUIT_CASE_OWNER_2 = "Du";

    private final static Integer CONTAIN_PRIMARY_KEY_1 = 2;
    private final static Integer CONTAIN_PRIMARY_KEY_2 = 4;
    private final static Integer CONTAIN_PRIMARY_KEY_3 = 28;
    private final static Integer CONTAIN_PRIMARY_KEY_4 = 48;
    private final static Integer CONTAIN_FOREIGN_KEY_1 = 45;
    private final static Integer CONTAIN_FOREIGN_KEY_2 = 718;
    private final static Integer CONTAIN_FOREIGN_KEY_3 = 51;
    private final static Integer CONTAIN_FOREIGN_KEY_4 = 71;

    private final static String TEST_CONTENTS_FILE_NAME_1 = "ContentOfSuitCase_Koffer1.csv";
    private final static String TEST_CONTENTS_FILE_NAME_2 = "ContentOfSuitCase_Koffer2.csv";

    private final static String DATA_LINE_1 = SUIT_CASE_PRIMARY_KEY_1
            + ";"
            + SUIT_CASE_IDENTIFIER_1
            + ";"
            + SUIT_CASE_DESCRIPTION_1
            + ";"
            + SUIT_CASE_OWNER_1
            + ";"
            + TEST_CONTENTS_FILE_NAME_1;
    private final static String DATA_LINE_2 = SUIT_CASE_PRIMARY_KEY_2
            + ";"
            + SUIT_CASE_IDENTIFIER_2
            + ";"
            + SUIT_CASE_DESCRIPTION_2
            + ";"
            + SUIT_CASE_OWNER_2
            + ";"
            + TEST_CONTENTS_FILE_NAME_2;

    private final static String HEAD_LINE = COMMON_CSV_VALUES.tableTitlePrimaryKey
            + ";"
            + COMMON_CSV_VALUES.tableTitleSccIdentifier
            + ";"
            + COMMON_CSV_VALUES.tableTitleSccDescription
            + ";"
            + COMMON_CSV_VALUES.tableTitleSccOwner
            + ";"
            + COMMON_CSV_VALUES.tableTitleSccContentsFile;

    private final String testFileName = "SuitCaseCharacteristics.csv";
    private final List<SuitCaseCharacteristicsImpl> testList = new ArrayList<>(List.of());

    private File testFile;
    private File testPath;

    private SuitCaseCharacteristicsExporter exporter;

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
        SuitCaseCharacteristicsImpl entry1 = new SuitCaseCharacteristicsImpl(SUIT_CASE_PRIMARY_KEY_1,
                SUIT_CASE_IDENTIFIER_1,
                SUIT_CASE_DESCRIPTION_1,
                SUIT_CASE_OWNER_1,
                1);
        entry1.getContents().clear();
        entry1.getContents().add(new ContentOfSuitCaseImpl(CONTAIN_PRIMARY_KEY_1,
                CONTAIN_FOREIGN_KEY_1));
        entry1.getContents().add(new ContentOfSuitCaseImpl(CONTAIN_PRIMARY_KEY_2,
                CONTAIN_FOREIGN_KEY_2));
        testList.add(entry1);
        SuitCaseCharacteristicsImpl entry2 = new SuitCaseCharacteristicsImpl(SUIT_CASE_PRIMARY_KEY_2,
                SUIT_CASE_IDENTIFIER_2,
                SUIT_CASE_DESCRIPTION_2,
                SUIT_CASE_OWNER_2,
                1);
        entry2.getContents().clear();
        entry2.getContents().add(new ContentOfSuitCaseImpl(CONTAIN_PRIMARY_KEY_3,
                CONTAIN_FOREIGN_KEY_3));
        entry2.getContents().add(new ContentOfSuitCaseImpl(CONTAIN_PRIMARY_KEY_4,
                CONTAIN_FOREIGN_KEY_4));
        testList.add(entry2);
        exporter = new SuitCaseCharacteristicsExporter(testList, testFile);
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