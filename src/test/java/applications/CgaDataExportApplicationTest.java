package applications;

import abstracts.AbstractPlainJava;
import dataStructures.*;
import enumerations.Hardness;
import enumerations.PropertyKeys;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * JUnit test for {@link CgaDataExportApplication}.
 */
public class CgaDataExportApplicationTest extends AbstractPlainJava {

    private final static int NUMBER_OF_ARGUMENTS = 2;

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
    private final static String BALL_FILE_NAME = "BallCharacteristics.csv";

    private final static Integer SUIT_CASE_PRIMARY_KEY_1 = 7;
    private final static Integer SUIT_CASE_PRIMARY_KEY_2 = 12;
    private final static String SUIT_CASE_IDENTIFIER_1 = "Koffer 1";
    private final static String SUIT_CASE_IDENTIFIER_2 = "Koffer 2";
    private final static String SUIT_CASE_DESCRIPTION_1 = "Test fuer die eine Bahn";
    private final static String SUIT_CASE_DESCRIPTION_2 = "Test fuer die andere Bahn";
    private final static String SUIT_CASE_OWNER_1 = "Ich";
    private final static String SUIT_CASE_OWNER_2 = "Du";
    private final static Integer SUIT_CASE_CONTAIN_PRIMARY_KEY_1 = 2;
    private final static Integer SUIT_CASE_CONTAIN_PRIMARY_KEY_2 = 4;
    private final static Integer SUIT_CASE_CONTAIN_PRIMARY_KEY_3 = 28;
    private final static Integer SUIT_CASE_CONTAIN_PRIMARY_KEY_4 = 48;
    private final static Integer SUIT_CASE_CONTAIN_FOREIGN_KEY_1 = 45;
    private final static Integer SUIT_CASE_CONTAIN_FOREIGN_KEY_2 = 718;
    private final static Integer SUIT_CASE_CONTAIN_FOREIGN_KEY_3 = 451;
    private final static Integer SUIT_CASE_CONTAIN_FOREIGN_KEY_4 = 71;
    private final static String SUIT_CASE_FILENAME = "SuitCaseCharacteristics.csv";
    private final static String SUIT_CASE_CONTENTS_FILENAME_1 = "ContentOfSuitCase_Koffer1.csv";
    private final static String SUIT_CASE_CONTENTS_FILENAME_2 = "ContentOfSuitCase_Koffer2.csv";

    private final static Integer CRAZY_GOLF_SITE_PRIMARY_KEY_1 = 8;
    private final static Integer CRAZY_GOLF_SITE_PRIMARY_KEY_2 = 21;
    private final static Integer CRAZY_GOLF_SITE_FOREIGN_KEY_1 = 7;
    private final static Integer CRAZY_GOLF_SITE_FOREIGN_KEY_2 = 12;
    private final static String CRAZY_GOLF_SITE_SITE_NAME_1 = "Ort1";
    private final static String CRAZY_GOLF_SITE_SITE_NAME_2 = "Ort2";
    private final static String CRAZY_GOLF_SITE_ADDRESS_1 = "Langriet";
    private final static String CRAZY_GOLF_SITE_ADDRESS_2 = "Muehlimatt";
    private final static String CRAZY_GOLF_SITE_POST_CODE_1 = "CH-8212";
    private final static String CRAZY_GOLF_SITE_POST_CODE_2 = "8047";
    private final static String CRAZY_GOLF_SITE_TOWN_1 = "Neuhausen am Rheinfall";
    private final static String CRAZY_GOLF_SITE_TOWN_2 = "Dietikon";
    private final static Integer CRAZY_GOLF_SITE_CONTAIN_PRIMARY_KEY_1 = 1;
    private final static Integer CRAZY_GOLF_SITE_CONTAIN_PRIMARY_KEY_2 = 18;
    private final static Integer CRAZY_GOLF_SITE_CONTAIN_PRIMARY_KEY_3 = 1;
    private final static Integer CRAZY_GOLF_SITE_CONTAIN_PRIMARY_KEY_4 = 18;
    private final static Integer CRAZY_GOLF_SITE_CONTAIN_FOREIGN_KEY_1 = 45;
    private final static Integer CRAZY_GOLF_SITE_CONTAIN_FOREIGN_KEY_2 = 718;
    private final static Integer CRAZY_GOLF_SITE_CONTAIN_FOREIGN_KEY_3 = 451;
    private final static Integer CRAZY_GOLF_SITE_CONTAIN_FOREIGN_KEY_4 = 71;
    private final static String CRAZY_GOLF_SITE_CONTAIN_POSITIONING_1 = "12/00";
    private final static String CRAZY_GOLF_SITE_CONTAIN_POSITIONING_2 = "15/30";
    private final static String CRAZY_GOLF_SITE_CONTAIN_POSITIONING_3 = "12/00";
    private final static String CRAZY_GOLF_SITE_CONTAIN_POSITIONING_4 = "15/30";
    private final static String CRAZY_GOLF_SITE_CONTAIN_CUSHIONING_1 = "links, 60 cm nach Verbindung";
    private final static String CRAZY_GOLF_SITE_CONTAIN_CUSHIONING_2 = "";
    private final static String CRAZY_GOLF_SITE_CONTAIN_CUSHIONING_3 = "rechts, nach 9. Platte";
    private final static String CRAZY_GOLF_SITE_CONTAIN_CUSHIONING_4 = "rechts, auf Steinplatte";
    private final static String CRAZY_GOLF_SITE_CONTAIN_MARKING_1 = "Fleck in Bande";
    private final static String CRAZY_GOLF_SITE_CONTAIN_MARKING_2 = "Fleck nach 80cm auf der Bahn";
    private final static String CRAZY_GOLF_SITE_CONTAIN_MARKING_3 = "";
    private final static String CRAZY_GOLF_SITE_CONTAIN_MARKING_4 = "";
    private final static String CRAZY_GOLF_SITE_CONTAIN_REMARK_1 = "mittelstark";
    private final static String CRAZY_GOLF_SITE_CONTAIN_REMARK_2 = "";
    private final static String CRAZY_GOLF_SITE_CONTAIN_REMARK_3 = "leicht";
    private final static String CRAZY_GOLF_SITE_CONTAIN_REMARK_4 = "mit Drall nach rechts";
    private final static String CRAZY_GOLF_SITE_FILENAME = "CrazyGolfSiteCharacteristics.csv";
    private final static String CRAZY_GOLF_SITE_CONTENTS_FILENAME_1 = "HandicapCharacteristics_Ort1.csv";
    private final static String CRAZY_GOLF_SITE_CONTENTS_FILENAME_2 = "HandicapCharacteristics_Ort2.csv";

    private final String pathDelimiter = (new File("/")).getPath();
    private final String pathAdditionalPart = "additional/";

    private final DataListContainer dataListContainer = new DataListContainerImpl();

    private File testPath;

    @Before
    public void setUp() throws Exception {
        // arrange
        testPath = new File(createTestPath(getClass().getSimpleName()));
        File additionalPath = new File(testPath + pathDelimiter + pathAdditionalPart);
        if (!additionalPath.exists()){
            additionalPath.mkdirs();
        }
        CgaDataExportApplication.properties
                .setProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey(),
                        testPath.getPath() + pathDelimiter);
        CgaDataExportApplication.properties
                .setProperty(PropertyKeys.PROPERTY_CSV_FILE_ENLARGEMENT_PATH.getPropertyKey(),
                        pathAdditionalPart);
        CgaDataExportApplication.properties
                .setProperty(PropertyKeys.PROPERTY_CSV_BALL_CHARACTERISTICS_FILE_NAME.getPropertyKey(),
                        BALL_FILE_NAME);
        CgaDataExportApplication.properties
                .setProperty(PropertyKeys.PROPERTY_CSV_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME.getPropertyKey(),
                        CRAZY_GOLF_SITE_FILENAME);
        CgaDataExportApplication.properties
                .setProperty(PropertyKeys.PROPERTY_CSV_SUITCASE_CHARACTERISTICS_FILE_NAME.getPropertyKey(),
                        SUIT_CASE_FILENAME);
        // prepare data ball characteristics
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
        // prepare data suitcase characteristics and its contents
        SuitCaseCharacteristicsImpl scc_entry1 = new SuitCaseCharacteristicsImpl(SUIT_CASE_PRIMARY_KEY_1,
                SUIT_CASE_IDENTIFIER_1,
                SUIT_CASE_DESCRIPTION_1,
                SUIT_CASE_OWNER_1,
                1);
        scc_entry1.getContents().clear();
        scc_entry1.getContents().add(new ContentOfSuitCaseImpl(SUIT_CASE_CONTAIN_PRIMARY_KEY_1,
                SUIT_CASE_CONTAIN_FOREIGN_KEY_1));
        scc_entry1.getContents().add(new ContentOfSuitCaseImpl(SUIT_CASE_CONTAIN_PRIMARY_KEY_2,
                SUIT_CASE_CONTAIN_FOREIGN_KEY_2));
        dataListContainer.getSuitCaseCharacteristics().add(scc_entry1);
        SuitCaseCharacteristicsImpl scc_entry2 = new SuitCaseCharacteristicsImpl(SUIT_CASE_PRIMARY_KEY_2,
                SUIT_CASE_IDENTIFIER_2,
                SUIT_CASE_DESCRIPTION_2,
                SUIT_CASE_OWNER_2,
                1);
        scc_entry2.getContents().clear();
        scc_entry2.getContents().add(new ContentOfSuitCaseImpl(SUIT_CASE_CONTAIN_PRIMARY_KEY_3,
                SUIT_CASE_CONTAIN_FOREIGN_KEY_3));
        scc_entry2.getContents().add(new ContentOfSuitCaseImpl(SUIT_CASE_CONTAIN_PRIMARY_KEY_4,
                SUIT_CASE_CONTAIN_FOREIGN_KEY_4));
        dataListContainer.getSuitCaseCharacteristics().add(scc_entry2);
        // prepare data crazy golf site charahcteristics and its contents
        CrazyGolfSiteCharacteristicsImpl cgcs_entry1 = new CrazyGolfSiteCharacteristicsImpl(CRAZY_GOLF_SITE_PRIMARY_KEY_1,
                CRAZY_GOLF_SITE_FOREIGN_KEY_1,
                CRAZY_GOLF_SITE_SITE_NAME_1,
                CRAZY_GOLF_SITE_ADDRESS_1,
                CRAZY_GOLF_SITE_POST_CODE_1,
                CRAZY_GOLF_SITE_TOWN_1);
        cgcs_entry1.getContents().clear();
        cgcs_entry1.getContents().add(new HandicapCharacteristicsImpl(CRAZY_GOLF_SITE_CONTAIN_PRIMARY_KEY_1,
                CRAZY_GOLF_SITE_CONTAIN_FOREIGN_KEY_1,
                CRAZY_GOLF_SITE_CONTAIN_POSITIONING_1,
                CRAZY_GOLF_SITE_CONTAIN_CUSHIONING_1,
                CRAZY_GOLF_SITE_CONTAIN_MARKING_1,
                CRAZY_GOLF_SITE_CONTAIN_REMARK_1));
        cgcs_entry1.getContents().add(new HandicapCharacteristicsImpl(CRAZY_GOLF_SITE_CONTAIN_PRIMARY_KEY_2,
                CRAZY_GOLF_SITE_CONTAIN_FOREIGN_KEY_2,
                CRAZY_GOLF_SITE_CONTAIN_POSITIONING_2,
                CRAZY_GOLF_SITE_CONTAIN_CUSHIONING_2,
                CRAZY_GOLF_SITE_CONTAIN_MARKING_2,
                CRAZY_GOLF_SITE_CONTAIN_REMARK_2));
        dataListContainer.getCrazyGolfSiteCharacteristics().add(cgcs_entry1);
        CrazyGolfSiteCharacteristicsImpl cgsc_entry2 = new CrazyGolfSiteCharacteristicsImpl(CRAZY_GOLF_SITE_PRIMARY_KEY_2,
                CRAZY_GOLF_SITE_FOREIGN_KEY_2,
                CRAZY_GOLF_SITE_SITE_NAME_2,
                CRAZY_GOLF_SITE_ADDRESS_2,
                CRAZY_GOLF_SITE_POST_CODE_2,
                CRAZY_GOLF_SITE_TOWN_2);
        cgsc_entry2.getContents().clear();
        cgsc_entry2.getContents().add(new HandicapCharacteristicsImpl(CRAZY_GOLF_SITE_CONTAIN_PRIMARY_KEY_3,
                CRAZY_GOLF_SITE_CONTAIN_FOREIGN_KEY_3,
                CRAZY_GOLF_SITE_CONTAIN_POSITIONING_3,
                CRAZY_GOLF_SITE_CONTAIN_CUSHIONING_3,
                CRAZY_GOLF_SITE_CONTAIN_MARKING_3,
                CRAZY_GOLF_SITE_CONTAIN_REMARK_3));
        cgsc_entry2.getContents().add(new HandicapCharacteristicsImpl(CRAZY_GOLF_SITE_CONTAIN_PRIMARY_KEY_4,
                CRAZY_GOLF_SITE_CONTAIN_FOREIGN_KEY_4,
                CRAZY_GOLF_SITE_CONTAIN_POSITIONING_4,
                CRAZY_GOLF_SITE_CONTAIN_CUSHIONING_4,
                CRAZY_GOLF_SITE_CONTAIN_MARKING_4,
                CRAZY_GOLF_SITE_CONTAIN_REMARK_4));
        dataListContainer.getCrazyGolfSiteCharacteristics().add(cgsc_entry2);
    }

    @After
    public void tearDown() throws Exception {
        dataListContainer.getBallCharacteristics().clear();
        dataListContainer.getCrazyGolfSiteCharacteristics().clear();
        dataListContainer.getSuitCaseCharacteristics().clear();
    }

    @Test
    public void checkArgumentsWithCorrectSize() {
        // act and assert
        CgaDataExportApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithOversizeSize() {
        // act and assert
        CgaDataExportApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS + 1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithUndersizedSize() {
        // act and assert
        CgaDataExportApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS - 1]);
    }

    @Test
    public void exportBallCharacteristics() throws IOException {
        // arrange
        File testFile = new File(testPath + pathDelimiter + pathAdditionalPart + BALL_FILE_NAME);
        testFile.delete();
        // act
        CgaDataExportApplication.exportBallCharacteristics();
        // assert
        assertTrue("csv file for ball characteristics not found", testFile.exists());
    }

    @Test
    public void exportCrazyGolfSiteCharacteristics() throws IOException {
        // arrange
        File testFile1 = new File(testPath + pathDelimiter + pathAdditionalPart
                + CRAZY_GOLF_SITE_FILENAME);
        testFile1.delete();
        File testFile2 = new File(testPath + pathDelimiter + pathAdditionalPart
                + CRAZY_GOLF_SITE_CONTENTS_FILENAME_1);
        testFile2.delete();
        File testFile3 = new File(testPath + pathDelimiter + pathAdditionalPart
                + CRAZY_GOLF_SITE_CONTENTS_FILENAME_2);
        testFile3.delete();
        // act
        CgaDataExportApplication.exportCrazyGolfSiteCharacteristics();
        // assert
        assertTrue("csv file for crazy golf site characteristics not found", testFile1.exists());
        assertTrue("csv file for contents of crazy golf site characteristics 1 not found", testFile2.exists());
        assertTrue("csv file for contents of crazy golf site characteristics 2 not found", testFile3.exists());
    }

    @Test
    public void exportSuitCaseCharacteristics() throws IOException {
        // arrange
        File testFile1 = new File(testPath + pathDelimiter + pathAdditionalPart
                + SUIT_CASE_FILENAME);
        testFile1.delete();
        File testFile2 = new File(testPath + pathDelimiter + pathAdditionalPart
                + SUIT_CASE_CONTENTS_FILENAME_1);
        testFile2.delete();
        File testFile3 = new File(testPath + pathDelimiter + pathAdditionalPart
                + SUIT_CASE_CONTENTS_FILENAME_2);
        testFile3.delete();
        // act
        CgaDataExportApplication.exportSuitCaseCharacteristics();
        // assert
        assertTrue("csv file for suitcase characteristics not found", testFile1.exists());
        assertTrue("csv file for contents of suitcase characteristics 1 not found", testFile2.exists());
        assertTrue("csv file for contents of suitcase characteristics 2 not found", testFile3.exists());
    }
}