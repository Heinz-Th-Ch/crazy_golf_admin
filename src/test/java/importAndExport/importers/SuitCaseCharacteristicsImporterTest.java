package importAndExport.importers;

import abstracts.AbstractPlainJava;
import dataStructures.BallCharacteristicsImpl;
import dataStructures.ContentOfSuitCaseImpl;
import dataStructures.DataListContainerImpl;
import dataStructures.SuitCaseCharacteristicsImpl;
import enumerations.Hardness;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link SuitCaseCharacteristicsImporter}.
 */
public class SuitCaseCharacteristicsImporterTest extends AbstractPlainJava {

    private final static Integer OWN_PRIMARY_KEY = 1;
    private final static String OWN_IDENTIFIER = "Ballkoffer 1";
    private final static String HEAD_LINE = "Bezeichnung;;Eigenschaften;;Besitzer;;Datenfile Inhalt";
    private final static String DATA_LINE = OWN_IDENTIFIER +
            ";;Hauptballkoffer;;Heinz Pfister;;ContentOfSuitCase.csv";

    private final static Integer CONTENT_PRIMARY_KEY = 1;
    private final static Integer BALL_PRIMARY_KEY = 47;
    private final static String BALL_IDENTIFIER = "Berliner";
    private final static String CONTENT_HEAD_LINE = "Position in Koffer;;" +
            "Bezeichnung";
    private final static String CONTENT_DATA_LINE = OWN_PRIMARY_KEY + ";;" + BALL_IDENTIFIER;

    private final static String CSV_SPLITTER = ";;";

    private static final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    private final String contentFileName = "ContentOfSuitCase.csv";
    private final String testFileName = "SuitCaseCharacteristics.csv";
    private final List<SuitCaseCharacteristicsImpl> testList = new ArrayList<>(List.of());

    private SuitCaseCharacteristicsImporter importer;

    @Before
    public void setUp() throws Exception {
        dataListContainer.getBallCharacteristics().add(new BallCharacteristicsImpl(BALL_PRIMARY_KEY,
                BALL_IDENTIFIER,
                "description",
                Hardness.H,
                52,
                37,
                0.4,
                "comment"));
        File testFile = new File(createTestPath(getClass().getSimpleName()) + "/" + testFileName);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(testFile)));
        writer.write(HEAD_LINE);
        writer.newLine();
        writer.write(DATA_LINE);
        writer.newLine();
        writer.close();
        File contentFile = new File(createTestPath(getClass().getSimpleName())
                + "/"
                + contentFileName);
        BufferedWriter contentWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(contentFile)));
        contentWriter.write(CONTENT_HEAD_LINE);
        contentWriter.newLine();
        contentWriter.write(CONTENT_DATA_LINE);
        contentWriter.newLine();
        contentWriter.close();
        importer = new SuitCaseCharacteristicsImporter(testFile, testList);
    }

    @After
    public void tearDown() {
        dataListContainer.getBallCharacteristics().clear();
    }

    @Test
    public void executeImport() throws IOException {
        // act
        importer.executeImport();
        // assert
        assertEquals("invalid number of records", 1, testList.size());
        SuitCaseCharacteristicsImpl result = testList.get(0);
        assertEquals("invalid primary key received",
                OWN_PRIMARY_KEY,
                result.getPrimaryKey());
        assertEquals("invalid identifier received",
                OWN_IDENTIFIER,
                result.getIdentifier());
        assertEquals("invalid number of records", 1, result.getContents().size());
        ContentOfSuitCaseImpl contentResult = result.getContents().get(0);
        assertEquals("invalid primary key received",
                CONTENT_PRIMARY_KEY,
                contentResult.getPrimaryKey());
        assertEquals("invalid foreign key ball received",
                BALL_PRIMARY_KEY,
                contentResult.getForeignKeyBall());
    }

    @Test
    public void extractColumnsOfHeadLineWithValidHeadLine() {
        // act and assert
        assertTrue("column number extraction failed",
                importer.extractColumnsOfHeadLine(new ArrayList<>(List.of(HEAD_LINE.split(CSV_SPLITTER)))));
    }

    @Test
    public void extractColumnsOfHeadLineWithHeadLinePartsAreInvalid() throws IOException {
        // arrange
        File localTestFile = new File(createTestPath(getClass().getSimpleName())
                + "/"
                + testFileName);
        String[] correctHeadLineSplits = HEAD_LINE.split(CSV_SPLITTER);
        // arrange, act and assert in loop
        for (int i = 0; i < correctHeadLineSplits.length; i++) {
            // arrange
            SuitCaseCharacteristicsImporter localImporter = new SuitCaseCharacteristicsImporter(localTestFile, testList);
            List<String> invalidHeadLineSplits = new ArrayList<>(List.of(correctHeadLineSplits));
            invalidHeadLineSplits.add(i, invalidHeadLineSplits.remove(i) + "X");
            // act and assert
            assertFalse("column number extraction correct",
                    localImporter.extractColumnsOfHeadLine(invalidHeadLineSplits));
        }
    }

    @Test
    public void isHeadLineUsableWithValidHeadLine() throws IOException {
        // act and assert
        assertTrue("invalid headline received",
                importer.isHeadLineUsable(new ArrayList<>(List.of(HEAD_LINE.split(CSV_SPLITTER)))));
    }

    @Test
    public void isHeadLineUsableWithWithHeadLinePartsAreInvalid() throws IOException {
        // arrange
        String[] correctHeadLineSplits = HEAD_LINE.split(CSV_SPLITTER);
        // arrange, act and assert in loop
        for (int i = 0; i < correctHeadLineSplits.length; i++) {
            // arrange
            List<String> invalidHeadLineSplits = new ArrayList<>(List.of(correctHeadLineSplits));
            invalidHeadLineSplits.add(i, invalidHeadLineSplits.remove(i) + "X");
            // act and assert
            assertFalse("invalid headline received",
                    importer.isHeadLineUsable(invalidHeadLineSplits));
        }
    }
}