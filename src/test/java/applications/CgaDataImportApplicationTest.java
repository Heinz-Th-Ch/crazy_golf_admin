package applications;

import abstracts.AbstractPlainJava;
import dataStructures.*;
import enumerations.DataImportFunction;
import enumerations.Hardness;
import enumerations.PropertyKeys;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * JUnit test for {@link CgaDataImportApplication}.
 */
public class CgaDataImportApplicationTest extends AbstractPlainJava {

    private final static int NUMBER_OF_ARGUMENTS = 2;

    private final static String BALL_IDENTIFIER_1 = "02";
    private final static String BALL_IDENTIFIER_2 = "Berliner";
    private final static String BALL_IDENTIFIER_3 = "B+M K9";
    private final static String BALL_IDENTIFIER_4 = "Special Ball for Test";

    private final static String BCI_HEAD_LINE = "Bezeichnung;;Eigenschaften;;Härte;;Höhe;;Gewicht;;Winkel;;Bemerkungen";
    private final static String BCI_DATA_LINE_1 = BALL_IDENTIFIER_1 + ";;Stein;;H;;1;;48;;0;;";
    private final static String BCI_DATA_LINE_2 = BALL_IDENTIFIER_2 + ";;rel. schnell;;W;;43;;30;;0.9;;Testball";
    private final static String BCI_DATA_LINE_3 = BALL_IDENTIFIER_3 + ";;Rohling;;W;;43;;30;;0.9;;evtl. B+M K9";
    private final static String BCI_FILE_NAME = "BallCharacteristics.csv";

    private final static String SCCI_IDENTIFIER_1 = "Ballkoffer 1";
    private final static String SCCI_IDENTIFIER_2 = "Nummer 1";
    private final static String SCCI_IDENTIFIER_3 = "Nummer 2";
    private final static String SCCI_HEAD_LINE = "Bezeichnung;;Eigenschaften;;Besitzer;;Datenfile Inhalt";
    private final static String SCCI_DATA_LINE = SCCI_IDENTIFIER_1 +
            ";;Hauptballkoffer;;Heinz Pfister;;ContentOfSuitCase.csv";
    private final static Integer SCCI_CONTENT_PRIMARY_KEY = 1;
    private final static String SCCI_CONTENT_HEAD_LINE = "Position in Koffer;;" +
            "Bezeichnung";
    private final static String SCCI_CONTENT_DATA_LINE = SCCI_CONTENT_PRIMARY_KEY + ";;" + BALL_IDENTIFIER_2;
    private final static String SCCI_CONTENT_FILENAME = "ContentOfSuitCase.csv";
    private final static String SCCI_FILENAME = "SuitCaseCharacteristics.csv";

    private final static String CGSCI_SITE_NAME_1 = "Minigolfbahn Langriet";
    private final static String CGSCI_SITE_NAME_2 = "Minigolfbahn Nummer 2";
    private final static String CGSCI_SITE_NAME_3 = "Minigolfbahn Nummer 3";
    private final static String CGSCI_SUIT_CASE_IDENTIFIER = "Ballkoffer 1";
    private final static String CGSCI_HEAD_LINE = "Anlage;;Adresse;;Postleitzahl;;Ort;;Bezeichnung Koffer;;Datenfile Bahnen";
    private final static String CGSCI_EMPTY_LINE = ";;;;;;;;;;";
    private final static String CGSCI_DATA_LINE = CGSCI_SITE_NAME_1 + ";;Langrietstrasse 95;;8212;;" +
            "Neuhausen am Rheinfall;;" + CGSCI_SUIT_CASE_IDENTIFIER + ";;HandicapCharacteristicsNeuhausen.csv";
    private final static Integer CGSCI_CONTENT_NUMBER_OF_HANDICAPS = 18;
    private final static Integer CGSCI_CONTENT_PRIMARY_KEY = 4;
    private final static String CGSCI_CONTENT_HEAD_LINE = "Bahn;;Ball;;Setzpunkt;;Banden;;Markierung;;Bemerkungen";
    private final static String CGSCI_CONTENT_DATA_LINE = CGSCI_CONTENT_PRIMARY_KEY + ";;" + BALL_IDENTIFIER_2 +
            ";;12/00;;links, 60cm nach Verbindung;;weisser Fleck in der Bahn;;leicht zügig";
    private final static String CGSCI_CONTENT_FILENAME = "HandicapCharacteristicsNeuhausen.csv";
    private final static String CGSCI_FILENAME = "CrazyGolfSiteCharacteristics.csv";

    private final String pathDelimiter = (new File("/")).getPath();

    private final DataListContainer dataListContainer = new DataListContainerImpl();

    private File csvDataPath;

    @Before
    public void setUp() throws Exception {
        csvDataPath = new File(createTestPath(getClass().getSimpleName()));

        File bciFile = new File(csvDataPath + pathDelimiter + BCI_FILE_NAME);
        BufferedWriter bciWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(bciFile)));
        bciWriter.write(BCI_HEAD_LINE);
        bciWriter.newLine();
        bciWriter.write(BCI_DATA_LINE_1);
        bciWriter.newLine();
        bciWriter.write(BCI_DATA_LINE_2);
        bciWriter.newLine();
        bciWriter.write(BCI_DATA_LINE_3);
        bciWriter.newLine();
        bciWriter.close();
        File scciFile = new File(csvDataPath + pathDelimiter + SCCI_FILENAME);
        BufferedWriter scciWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(scciFile)));
        scciWriter.write(SCCI_HEAD_LINE);
        scciWriter.newLine();
        scciWriter.write(SCCI_DATA_LINE);
        scciWriter.newLine();
        scciWriter.close();
        File scciContentFile = new File(csvDataPath + pathDelimiter + SCCI_CONTENT_FILENAME);
        BufferedWriter scciContentWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(scciContentFile)));
        scciContentWriter.write(SCCI_CONTENT_HEAD_LINE);
        scciContentWriter.newLine();
        scciContentWriter.write(SCCI_CONTENT_DATA_LINE);
        scciContentWriter.newLine();
        scciContentWriter.close();
        File cgsciFile = new File(csvDataPath + pathDelimiter + CGSCI_FILENAME);
        BufferedWriter cgsciWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cgsciFile)));
        cgsciWriter.write(CGSCI_HEAD_LINE);
        cgsciWriter.newLine();
        cgsciWriter.write(CGSCI_EMPTY_LINE);
        cgsciWriter.newLine();
        cgsciWriter.write(CGSCI_DATA_LINE);
        cgsciWriter.newLine();
        cgsciWriter.close();
        File cgsciContentFile = new File(csvDataPath + pathDelimiter + CGSCI_CONTENT_FILENAME);
        BufferedWriter cgsciContentWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cgsciContentFile)));
        cgsciContentWriter.write(CGSCI_CONTENT_HEAD_LINE);
        cgsciContentWriter.newLine();
        cgsciContentWriter.write(CGSCI_CONTENT_DATA_LINE);
        cgsciContentWriter.newLine();
        cgsciContentWriter.close();

        CgaDataImportApplication.properties
                .setProperty(PropertyKeys.PROPERTY_CSV_FILE_PATH.getPropertyKey(),
                        csvDataPath.getPath() + pathDelimiter);
        CgaDataImportApplication.properties
                .setProperty(PropertyKeys.PROPERTY_CSV_BALL_CHARACTERISTICS_FILE_NAME.getPropertyKey(),
                        BCI_FILE_NAME);
        CgaDataImportApplication.properties
                .setProperty(PropertyKeys.PROPERTY_CSV_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME.getPropertyKey(),
                        CGSCI_FILENAME);
        CgaDataImportApplication.properties
                .setProperty(PropertyKeys.PROPERTY_CSV_SUITCASE_CHARACTERISTICS_FILE_NAME.getPropertyKey(),
                        SCCI_FILENAME);
    }

    @After
    public void tearDown() {
        dataListContainer.getBallCharacteristics().clear();
        dataListContainer.getCrazyGolfSiteCharacteristics().clear();
        dataListContainer.getSuitCaseCharacteristics().clear();
        dataListContainer.resetBallCharacteristicsChanged();
        dataListContainer.resetCrazyGolfSiteCharacteristicsChanged();
        dataListContainer.resetSuitCaseCharacteristicsChanged();
    }

    @Test
    public void checkArgumentsWithCorrectSize() {
        // act and assert
        CgaDataImportApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithOversizeSize() {
        // act and assert
        CgaDataImportApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS + 1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithUndersizedSize() {
        // act and assert
        CgaDataImportApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS - 1]);
    }

    @Test
    public void dataCanBeLoadNo() {
        // arrange
        CgaDataImportApplication.function = DataImportFunction.INITIAL;
        dataListContainer.getBallCharacteristics().add(null);
        dataListContainer.getCrazyGolfSiteCharacteristics().add(null);
        dataListContainer.getSuitCaseCharacteristics().add(null);
        // act and assert
        assertFalse("load unexpected accepted", CgaDataImportApplication.dataCanBeLoad());
    }

    @Test
    public void dataCanBeLoadNoBecauseFunctionButYesBecauseEmptyBallCharacteristics() {
        // arrange
        CgaDataImportApplication.function = DataImportFunction.INITIAL;
        dataListContainer.getCrazyGolfSiteCharacteristics().add(null);
        dataListContainer.getSuitCaseCharacteristics().add(null);
        // act and assert
        assertTrue("load unexpected forbidden", CgaDataImportApplication.dataCanBeLoad());
    }

    @Test
    public void dataCanBeLoadNoBecauseFunctionButYesBecauseEmptyCrazyGolfSiteCharacteristics() {
        // arrange
        CgaDataImportApplication.function = DataImportFunction.INITIAL;
        dataListContainer.getBallCharacteristics().clear();
        dataListContainer.getSuitCaseCharacteristics().clear();
        // act and assert
        assertTrue("load unexpected forbidden", CgaDataImportApplication.dataCanBeLoad());
    }

    @Test
    public void dataCanBeLoadNoBecauseFunctionButYesBecauseEmptySuitCaseCharacteristics() {
        // arrange
        CgaDataImportApplication.function = DataImportFunction.INITIAL;
        dataListContainer.getBallCharacteristics().clear();
        dataListContainer.getCrazyGolfSiteCharacteristics().clear();
        // act and assert
        assertTrue("load unexpected forbidden", CgaDataImportApplication.dataCanBeLoad());
    }

    @Test
    public void dataCanBeLoadYesBecauseFunction() {
        // arrange
        CgaDataImportApplication.function = DataImportFunction.OVERWRITE;
        // act and assert
        assertTrue("load unexpected forbidden", CgaDataImportApplication.dataCanBeLoad());
    }

    @Test
    public void importBallCharacteristicsInitialFunctionAndEmptyList() throws IOException {
        // arrange
        CgaDataImportApplication.function = DataImportFunction.INITIAL;
        // act
        CgaDataImportApplication.importBallCharacteristics();
        // assert
        assertTrue("data not changed", dataListContainer.isBallCharacteristicsChanged());
        assertBallCharacteristics(BALL_IDENTIFIER_1,
                BALL_IDENTIFIER_2,
                BALL_IDENTIFIER_3);
    }

    @Test
    public void importBallCharacteristicsInitialFunctionAndNotEmptyList() throws IOException {
        // arrange
        dataListContainer.getBallCharacteristics().add(new BallCharacteristicsImpl(1,
                BALL_IDENTIFIER_4,
                "",
                Hardness.H,
                1,
                2,
                .5,
                ""));
        CgaDataImportApplication.function = DataImportFunction.INITIAL;
        // act
        CgaDataImportApplication.importBallCharacteristics();
        // assert
        assertFalse("data unexpected changed", dataListContainer.isBallCharacteristicsChanged());
        assertBallCharacteristics(BALL_IDENTIFIER_4);
    }

    @Test
    public void importBallCharacteristicsOverwriteFunctionAndNotEmptyList() throws IOException {
        // arrange
        dataListContainer.getBallCharacteristics().add(null);
        CgaDataImportApplication.function = DataImportFunction.OVERWRITE;
        // act
        CgaDataImportApplication.importBallCharacteristics();
        // assert
        assertTrue("data not changed", dataListContainer.isBallCharacteristicsChanged());
        assertBallCharacteristics(BALL_IDENTIFIER_1,
                BALL_IDENTIFIER_2,
                BALL_IDENTIFIER_3);
    }

    @Test
    public void importCrazyGolfSiteCharacteristicsInitialFunctionAndEmptyList() throws IOException {
        // arrange
        CgaDataImportApplication.importBallCharacteristics();
        CgaDataImportApplication.importSuitCaseCharacteristics();
        CgaDataImportApplication.function = DataImportFunction.INITIAL;
        // act
        CgaDataImportApplication.importCrazyGolfSiteCharacteristics();
        // assert
        assertTrue("data not changed", dataListContainer.isCrazyGolfSiteCharacteristicsChanged());
        assertCrazyGolfSiteCharacteristics(Pair.of(CGSCI_SITE_NAME_1, 1));
        assertEquals("invalid content of record 1 of sub list",
                CGSCI_CONTENT_PRIMARY_KEY,
                dataListContainer.getCrazyGolfSiteCharacteristics().get(0).getContents().get(0).getPrimaryKey());
    }

    @Test
    public void importCrazyGolfSiteCharacteristicsInitialFunctionAndNotEmptyList() throws IOException {
        // arrange
        dataListContainer.getCrazyGolfSiteCharacteristics().add(new CrazyGolfSiteCharacteristicsImpl(1,
                1,
                CGSCI_SITE_NAME_2,
                "",
                "",
                ""));
        dataListContainer.getCrazyGolfSiteCharacteristics().add(new CrazyGolfSiteCharacteristicsImpl(2,
                2,
                CGSCI_SITE_NAME_3,
                "",
                "",
                ""));
        CgaDataImportApplication.function = DataImportFunction.INITIAL;
        // act
        CgaDataImportApplication.importCrazyGolfSiteCharacteristics();
        // assert
        assertFalse("data unexpected changed", dataListContainer.isCrazyGolfSiteCharacteristicsChanged());
        assertCrazyGolfSiteCharacteristics(Pair.of(CGSCI_SITE_NAME_2, CGSCI_CONTENT_NUMBER_OF_HANDICAPS),
                Pair.of(CGSCI_SITE_NAME_3, CGSCI_CONTENT_NUMBER_OF_HANDICAPS));

    }

    @Test
    public void importCrazyGolfSiteCharacteristicsOverwriteFunctionAndNotEmptyList() throws IOException {
        // arrange
        CgaDataImportApplication.importBallCharacteristics();
        CgaDataImportApplication.importSuitCaseCharacteristics();
        dataListContainer.getCrazyGolfSiteCharacteristics().add(null);
        CgaDataImportApplication.function = DataImportFunction.OVERWRITE;
        // act
        CgaDataImportApplication.importCrazyGolfSiteCharacteristics();
        // assert
        assertTrue("data not changed", dataListContainer.isCrazyGolfSiteCharacteristicsChanged());
        assertCrazyGolfSiteCharacteristics(Pair.of(CGSCI_SITE_NAME_1, 1));
        assertEquals("invalid content of record 1 of sub list",
                CGSCI_CONTENT_PRIMARY_KEY,
                dataListContainer.getCrazyGolfSiteCharacteristics().get(0).getContents().get(0).getPrimaryKey());
    }

    @Test
    public void importSuitCaseCharacteristicsInitialFunctionAndEmptyList() throws IOException {
        // arrange
        CgaDataImportApplication.importBallCharacteristics();
        CgaDataImportApplication.function = DataImportFunction.INITIAL;
        // act
        CgaDataImportApplication.importSuitCaseCharacteristics();
        // assert
        assertTrue("data not changed", dataListContainer.isSuitCaseCharacteristicsChanged());
        assertSuitCaseCharacteristics(Pair.of(SCCI_IDENTIFIER_1, 1));
        assertEquals("invalid content of record 1 of sub list",
                SCCI_CONTENT_PRIMARY_KEY,
                dataListContainer.getSuitCaseCharacteristics().get(0).getContents().get(0).getPrimaryKey());
    }

    @Test
    public void importSuitCaseCharacteristicsInitialFunctionAndNotEmptyList() throws IOException {
        // arrange
        dataListContainer.getSuitCaseCharacteristics().add(new SuitCaseCharacteristicsImpl(1,
                SCCI_IDENTIFIER_2,
                "",
                "",
                1));
        dataListContainer.getSuitCaseCharacteristics().add(new SuitCaseCharacteristicsImpl(2,
                SCCI_IDENTIFIER_3,
                "",
                "",
                18));
        CgaDataImportApplication.function = DataImportFunction.INITIAL;
        // act
        CgaDataImportApplication.importSuitCaseCharacteristics();
        // assert
        assertFalse("data unexpected changed", dataListContainer.isSuitCaseCharacteristicsChanged());
        assertSuitCaseCharacteristics(Pair.of(SCCI_IDENTIFIER_2, 1),
                Pair.of(SCCI_IDENTIFIER_3, 18));

    }

    @Test
    public void importSuitCaseCharacteristicsOverwriteFunctionAndNotEmptyList() throws IOException {
        // arrange
        CgaDataImportApplication.importBallCharacteristics();
        dataListContainer.getSuitCaseCharacteristics().add(null);
        CgaDataImportApplication.function = DataImportFunction.OVERWRITE;
        // act
        CgaDataImportApplication.importSuitCaseCharacteristics();
        // assert
        assertTrue("data not changed", dataListContainer.isSuitCaseCharacteristicsChanged());
        assertSuitCaseCharacteristics(Pair.of(SCCI_IDENTIFIER_1, 1));
        assertEquals("invalid content of record 1 of sub list",
                SCCI_CONTENT_PRIMARY_KEY,
                dataListContainer.getSuitCaseCharacteristics().get(0).getContents().get(0).getPrimaryKey());
    }

    private void assertBallCharacteristics(String... ballIdentifiers) {
        assertEquals("invalid size of list",
                ballIdentifiers.length,
                dataListContainer.getBallCharacteristics().size());
        for (int i = 0; i < ballIdentifiers.length; i++) {
            assertEquals(String.format("invalid content of record %d of list", i + 1),
                    ballIdentifiers[i],
                    dataListContainer.getBallCharacteristics().get(i).getIdentifier());
        }
    }

    @SafeVarargs
    private void assertCrazyGolfSiteCharacteristics(Pair<String, Integer>... expectedData) {
        assertEquals("invalid size of list",
                expectedData.length,
                dataListContainer.getCrazyGolfSiteCharacteristics().size());
        for (int i = 0; i < expectedData.length; i++) {
            assertEquals(String.format("invalid content of record %d of list", i + 1),
                    expectedData[i].getLeft(),
                    dataListContainer.getCrazyGolfSiteCharacteristics().get(i).getSiteName());
            assertEquals(String.format("invalid size of sub list %d", i + 1),
                    expectedData[i].getRight().intValue(),
                    dataListContainer.getCrazyGolfSiteCharacteristics().get(i).getContents().size());

        }
    }

    @SafeVarargs
    private void assertSuitCaseCharacteristics(Pair<String, Integer>... expectedData) {
        assertEquals("invalid size of list",
                expectedData.length,
                dataListContainer.getSuitCaseCharacteristics().size());
        for (int i = 0; i < expectedData.length; i++) {
            assertEquals(String.format("invalid content of record %d of list", i + 1),
                    expectedData[i].getLeft(),
                    dataListContainer.getSuitCaseCharacteristics().get(i).getIdentifier());
            assertEquals(String.format("invalid size of sub list %d", i + 1),
                    expectedData[i].getRight().intValue(),
                    dataListContainer.getSuitCaseCharacteristics().get(i).getContents().size());

        }
    }

}