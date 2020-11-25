package importers;

import abstracts.AbstractPlainJava;
import dataStructures.BallCharacteristicsImpl;
import enumerations.Hardness;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link BallCharacteristicsImporter}.
 */
public class BallCharacteristicsImporterTest extends AbstractPlainJava {

    private final static Integer PRIMARY_KEY_1 = 1;
    private final static Integer PRIMARY_KEY_2 = 2;
    private final static String BALL_IDENTIFIER_1 = "02";
    private final static String BALL_IDENTIFIER_2 = "B+M K9";
    private final static String OPTIONAL_DATA_1 = "";
    private final static String OPTIONAL_DATA_2 = "evtl. B+M K9";
    private final static String HEAD_LINE = "Bezeichnung;;Eigenschaften;;Härte;;Höhe;;Gewicht;;Winkel;;Bemerkungen";
    private final static String DATA_LINE_1 = "02;;Stein;;H;;1;;48;;0;;";
    private final static String DATA_LINE_2 = "B+M K9;;Rohling;;W;;43;;30;;0.9;;evtl. B+M K9";

    private final static String CSV_SPLITTER = ";;";

    private final String testFileName = "BallCharacteristics.csv";
    private final List<BallCharacteristicsImpl> testList = new ArrayList<>(List.of());

    private BallCharacteristicsImporter importer;

    @Before
    public void setUp() throws Exception {
        new File(getTestDataPath() + getClass().getSimpleName()).mkdirs();
        File testFile = new File(getTestDataPath() + getClass().getSimpleName() + "/" + testFileName);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(testFile)));
        writer.write(HEAD_LINE);
        writer.newLine();
        writer.write(DATA_LINE_1);
        writer.newLine();
        writer.write(DATA_LINE_2);
        writer.newLine();
        writer.close();
        importer = new BallCharacteristicsImporter(testFile, testList);
    }

    @Test
    public void executeImport() throws IOException {
        // act
        importer.executeImport();
        // assert
        assertEquals("invalid number of records", 2, testList.size());
        BallCharacteristicsImpl result1 = testList.get(0);
        assertEquals("invalid primary key received in data record 1",
                PRIMARY_KEY_1,
                result1.getPrimaryKey());
        assertEquals("invalid identifier received in data record 1",
                BALL_IDENTIFIER_1,
                result1.getIdentifier());
        BallCharacteristicsImpl result2 = testList.get(1);
        assertEquals("invalid primary key received in data record 2",
                PRIMARY_KEY_2,
                result2.getPrimaryKey());
        assertEquals("invalid identifier received in data record 2",
                BALL_IDENTIFIER_2,
                result2.getIdentifier());
    }

    @Test
    public void defineHardnessWithAllValidHardness() {
        // act and assert im loop
        for (Hardness entry : Hardness.values()) {
            assertEquals("invalid hardness received",
                    entry,
                    importer.defineHardness(entry.getImportValue()));
        }
    }

    @Test
    public void defineHardnessWithAnInvalidHardness() {
        // act and assert
        assertEquals("invalid hardness received",
                Hardness.UNDEF,
                importer.defineHardness("funnyValue"));
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
            BallCharacteristicsImporter localImporter = new BallCharacteristicsImporter(localTestFile, testList);
            List<String> invalidHeadLineSplits = new ArrayList<>(List.of(correctHeadLineSplits));
            invalidHeadLineSplits.add(i, invalidHeadLineSplits.remove(i) + "X");
            // act and assert
            assertFalse("column number extraction correct",
                    localImporter.extractColumnsOfHeadLine(invalidHeadLineSplits));
        }
    }

    @Test
    public void getOptionalDataExists() {
        // act and assert
        assertEquals("optional data not found",
                OPTIONAL_DATA_2,
                importer.getOptionalData(new ArrayList<>(List.of(DATA_LINE_2.split(CSV_SPLITTER))),
                        6));
    }

    @Test
    public void getOptionalDataNotExists() {
        // act and assert
        assertEquals("unexpected optional data found",
                OPTIONAL_DATA_1,
                importer.getOptionalData(new ArrayList<>(List.of(DATA_LINE_1.split(CSV_SPLITTER))),
                        6));
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