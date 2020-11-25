package importers;

import abstracts.AbstractPlainJava;
import dataStructures.BallCharacteristicsImpl;
import dataStructures.ContentOfSuitCaseImpl;
import dataStructures.DataListContainerImpl;
import enumerations.Hardness;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link ContentOfSuitCaseImporter}.
 */
public class ContentOfSuitCaseImporterTest extends AbstractPlainJava {

    private final static Integer BALL_PRIMARY_KEY = 47;
    private final static Integer OWN_PRIMARY_KEY = 1;
    private final static String BALL_IDENTIFIER = "Berliner";
    private final static String HEAD_LINE = "Position in Koffer;;" +
            "Bezeichnung";
    private final static String DATA_LINE = OWN_PRIMARY_KEY + ";;" + BALL_IDENTIFIER;

    private final static String CSV_SPLITTER = ";;";

    private static final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    private final String testFileName = "ContentOfSuitCase.csv";
    private final List<ContentOfSuitCaseImpl> testList = new ArrayList<>(List.of());

    private ContentOfSuitCaseImporter importer;

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
        new File(getTestDataPath() + getClass().getSimpleName()).mkdirs();
        File testFile = new File(getTestDataPath() + getClass().getSimpleName() + "/" + testFileName);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(testFile)));
        writer.write(HEAD_LINE);
        writer.newLine();
        writer.write(DATA_LINE);
        writer.newLine();
        writer.close();
        importer = new ContentOfSuitCaseImporter(testFile, testList);
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
        ContentOfSuitCaseImpl result = testList.get(0);
        assertEquals("invalid primary key received",
                OWN_PRIMARY_KEY,
                result.getPrimaryKey());
        assertEquals("invalid foreign key ball received",
                BALL_PRIMARY_KEY,
                result.getForeignKeyBall());
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
        File localTestFile = new File(getTestDataPath() + getClass().getSimpleName() + "/" + testFileName);
        String[] correctHeadLineSplits = HEAD_LINE.split(CSV_SPLITTER);
        // arrange, act and assert in loop
        for (int i = 0; i < correctHeadLineSplits.length; i++) {
            // arrange
            ContentOfSuitCaseImporter localImporter = new ContentOfSuitCaseImporter(localTestFile, testList);
            List<String> invalidHeadLineSplits = new ArrayList<>(List.of(correctHeadLineSplits));
            invalidHeadLineSplits.add(i, invalidHeadLineSplits.remove(i) + "X");
            // act and assert
            assertFalse("column number extraction correct",
                    localImporter.extractColumnsOfHeadLine(invalidHeadLineSplits));
        }
    }

    @Test
    public void getForeignKeyBallByIdentifierEntryFound() {
        // act and assert
        assertEquals("entry not found",
                BALL_PRIMARY_KEY,
                importer.getForeignKeyBallByIdentifier(BALL_IDENTIFIER));
    }

    @Test
    public void getForeignKeyBallByIdentifierEntryNotFound() {
        // act and assert
        assertEquals("entry unexpected found",
                Integer.valueOf(-1),
                importer.getForeignKeyBallByIdentifier(BALL_IDENTIFIER + "X"));
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