package importAndExport.importers;

import abstracts.AbstractPlainJava;
import dataStructures.*;
import enumerations.Hardness;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link CrazyGolfSiteCharacteristicsImporter}.
 */
public class CrazyGolfSiteCharacteristicsImporterTest extends AbstractPlainJava {

    private final static Integer OWN_PRIMARY_KEY = 1;
    private final static String OWN_SITE_NAME = "Minigolfbahn Langriet";
    private final static Integer SUIT_CASE_PRIMARY_KEY = 22;
    private final static String SUIT_CASE_IDENTIFIER = "Ballkoffer 1";
    private final static String HEAD_LINE = "Anlage;;Adresse;;Postleitzahl;;Ort;;Bezeichnung Koffer;;Datenfile Bahnen";
    private final static String EMPTY_LINE = ";;;;;;;;;;";
    private final static String DATA_LINE = "Minigolfbahn Langriet;;Langrietstrasse 95;;8212;;" +
            "Neuhausen am Rheinfall;;" + SUIT_CASE_IDENTIFIER + ";;HandicapCharacteristicsNeuhausen.csv";

    private final static Integer CONTENT_PRIMARY_KEY = 4;
    private final static String CONTENT_BALL_IDENTIFIER = "Berliner";
    private final static String CONTENT_HEAD_LINE = "Bahn;;Ball;;Setzpunkt;;Banden;;Markierung;;Bemerkungen";
    private final static String CONTENT_DATA_LINE = CONTENT_PRIMARY_KEY + ";;" + CONTENT_BALL_IDENTIFIER +
            ";;12/00;;links, 60cm nach Verbindung;;weisser Fleck in der Bahn;;leicht zügig";

    private final static Integer BALL_PRIMARY_KEY = 47;
    private final static String BALL_IDENTIFIER = "Berliner";

    private final static String CSV_SPLITTER = ";;";

    private static final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    private final String contentFileName = "HandicapCharacteristicsNeuhausen.csv";
    private final String testFileName = "CrazyGolfSiteCharacteristics.csv";
    private final List<CrazyGolfSiteCharacteristicsImpl> testList = new ArrayList<>(List.of());

    private CrazyGolfSiteCharacteristicsImporter importer;

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
        dataListContainer.getSuitCaseCharacteristics().add(new SuitCaseCharacteristicsImpl(SUIT_CASE_PRIMARY_KEY,
                SUIT_CASE_IDENTIFIER,
                "DESC",
                "OWNER",
                20));
        File testFile = new File(createTestPath(getClass().getSimpleName()) + "/" + testFileName);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(testFile)));
        writer.write(HEAD_LINE);
        writer.newLine();
        writer.write(EMPTY_LINE);
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
        importer = new CrazyGolfSiteCharacteristicsImporter(testFile, testList);
    }

    @After
    public void tearDown() {
        dataListContainer.getBallCharacteristics().clear();
        dataListContainer.getSuitCaseCharacteristics().clear();
    }

    @Test
    public void executeImport() throws IOException {
        // act
        importer.executeImport();
        // assert
        assertEquals("invalid number of records", 1, testList.size());
        CrazyGolfSiteCharacteristicsImpl result = testList.get(0);
        assertEquals("invalid primary key received",
                OWN_PRIMARY_KEY,
                result.getPrimaryKey());
        assertEquals("invalid site name received",
                OWN_SITE_NAME,
                result.getSiteName());
        HandicapCharacteristicsImpl contentResult = result.getContents().get(0);
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
            CrazyGolfSiteCharacteristicsImporter localImporter = new CrazyGolfSiteCharacteristicsImporter(localTestFile, testList);
            List<String> invalidHeadLineSplits = new ArrayList<>(List.of(correctHeadLineSplits));
            invalidHeadLineSplits.add(i, invalidHeadLineSplits.remove(i) + "X");
            // act and assert
            assertFalse("column number extraction correct",
                    localImporter.extractColumnsOfHeadLine(invalidHeadLineSplits));
        }
    }

    @Test
    public void getForeignKeySuitCaseByIdentifierEntryFound() {
        // act and assert
        assertEquals("entry not found",
                SUIT_CASE_PRIMARY_KEY,
                importer.getForeignKeySuitCaseByIdentifier(SUIT_CASE_IDENTIFIER));
    }

    @Test
    public void getForeignKeySuitCaseByIdentifierEntryNotFound() {
        // act and assert
        assertEquals("entry unexpected found",
                Integer.valueOf(-1),
                importer.getForeignKeySuitCaseByIdentifier(SUIT_CASE_IDENTIFIER + "X"));
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