package applications;

import abstracts.AbstractPlainJava;
import enumerations.PropertyKeys;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * JUnit test for {@link CgaFileMigrationApplication}.
 */
public class CgaFileMigrationApplicationTest extends AbstractPlainJava {

    private final int NUMBER_OF_ARGUMENTS = 2;

    private final String LOG_PATH_PART_TWO = getClass().getSimpleName() + "/log/";
    private final String DATA_PATH_PART_TWO = getClass().getSimpleName() + "/data/";
    private final String DATA_PATH_CSV_ENLARGEMENT = "csv/";
    private final String DATA_PATH_PDF_ENLARGEMENT = "pdf/";
    private final String DATA_FILE_BALL_CHAR = "ball_char.data";
    private final String DATA_FILE_GOLF_SITE = "golf_site.data";
    private final String DATA_FILE_SUIT_CASE = "suit_case.data";

    @Before
    public void setUp() throws Exception {
        CgaFileMigrationApplication.properties.setProperty(PropertyKeys.PROPERTY_LOG_FILE_PATH.getPropertyKey(),
                getTestDataPath() + LOG_PATH_PART_TWO);
        CgaFileMigrationApplication.properties.setProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey(),
                getTestDataPath() + DATA_PATH_PART_TWO);

        CgaFileMigrationApplication.properties.setProperty(PropertyKeys.PROPERTY_CSV_FILE_ENLARGEMENT_PATH.getPropertyKey(),
                DATA_PATH_CSV_ENLARGEMENT);
        CgaFileMigrationApplication.properties.setProperty(PropertyKeys.PROPERTY_PDF_FILE_ENLARGEMENT_PATH.getPropertyKey(),
                DATA_PATH_PDF_ENLARGEMENT);

        CgaFileMigrationApplication.properties
                .setProperty(PropertyKeys.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME.getPropertyKey(),
                        DATA_FILE_BALL_CHAR);
        CgaFileMigrationApplication.properties
                .setProperty(PropertyKeys.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME.getPropertyKey(),
                        DATA_FILE_GOLF_SITE);
        CgaFileMigrationApplication.properties
                .setProperty(PropertyKeys.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME.getPropertyKey(),
                        DATA_FILE_SUIT_CASE);
    }

    @After
    public void tearDown() {
        new File(CgaFileMigrationApplication.properties
                .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME.getPropertyKey()))
                .delete();
        new File(CgaFileMigrationApplication.properties
                .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                                .getPropertyKey()))
                .delete();
        new File(CgaFileMigrationApplication.properties
                .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME.getPropertyKey()))
                .delete();

        new File(CgaFileMigrationApplication.properties
                .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())
                + CgaFileMigrationApplication.properties
                .getProperty(PropertyKeys.PROPERTY_CSV_FILE_ENLARGEMENT_PATH.getPropertyKey()))
                .delete();
        new File(CgaFileMigrationApplication.properties
                .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())
                + CgaFileMigrationApplication.properties
                .getProperty(PropertyKeys.PROPERTY_PDF_FILE_ENLARGEMENT_PATH.getPropertyKey()))
                .delete();

        new File(CgaFileMigrationApplication.properties
                .getProperty(PropertyKeys.PROPERTY_LOG_FILE_PATH.getPropertyKey()))
                .delete();
        new File(CgaFileMigrationApplication.properties
                .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()))
                .delete();
    }

    @Test
    public void checkArgumentsWithCorrectSize() {
        // act and assert
        CgaFileMigrationApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithOversizeSize() {
        // act and assert
        CgaFileMigrationApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS + 1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithUndersizedSize() {
        // act and assert
        CgaFileMigrationApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS - 1]);
    }

    @Test
    public void clearDataFilesDirectoryExists() throws IOException, InterruptedException {
        // arrange
        CgaFileMigrationApplication.createDirectories();
        CgaFileMigrationApplication.createDataFiles();
        long lastModifiedBallCharacteristics = new File(CgaFileMigrationApplication.properties
                .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME.getPropertyKey()))
                .lastModified();
        long lastModifiedCrazyGolfSiteCharacteristics = new File(CgaFileMigrationApplication.properties
                .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                                .getPropertyKey()))
                .lastModified();
        long lastModifiedSuitCaseCharacteristics = new File(CgaFileMigrationApplication.properties
                .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME.getPropertyKey()))
                .lastModified();
        Thread.sleep(1000);
        // act
        CgaFileMigrationApplication.clearDataFiles();
        // assert
        assertTrue("data file ball characteristics not newer",
                lastModifiedBallCharacteristics < new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                        CgaFileMigrationApplication.properties
                                .getProperty(PropertyKeys.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME
                                        .getPropertyKey()))
                        .lastModified());
        assertTrue("data file crazy golf site characteristics not newer",
                lastModifiedCrazyGolfSiteCharacteristics <
                        new File(CgaFileMigrationApplication.properties
                                .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                                CgaFileMigrationApplication.properties
                                        .getProperty(PropertyKeys.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                                                .getPropertyKey()))
                                .lastModified());
        assertTrue("data file suitcase characteristics not newer",
                lastModifiedSuitCaseCharacteristics < new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                        CgaFileMigrationApplication.properties
                                .getProperty(PropertyKeys.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME
                                        .getPropertyKey()))
                        .lastModified());
    }

    @Test
    public void clearDataFilesDirectoryNotExists() throws IOException {
        // act
        CgaFileMigrationApplication.clearDataFiles();
        // assert
        assertFalse("data file ball characteristics unexpected found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                        CgaFileMigrationApplication.properties
                                .getProperty(PropertyKeys.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME
                                        .getPropertyKey()))
                        .exists());
        assertFalse("data file crazy golf site characteristics unexpected found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                        CgaFileMigrationApplication.properties
                                .getProperty(PropertyKeys.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                                        .getPropertyKey()))
                        .exists());
        assertFalse("data file suitcase characteristics unexpected found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                        CgaFileMigrationApplication.properties
                                .getProperty(PropertyKeys.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME
                                        .getPropertyKey()))
                        .exists());
    }

    @Test
    public void createDataFilesDirectoryExists() throws IOException {
        // arrange
        CgaFileMigrationApplication.createDirectories();
        // act
        CgaFileMigrationApplication.createDataFiles();
        // assert
        assertTrue("data file ball characteristics not found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                        CgaFileMigrationApplication.properties
                                .getProperty(PropertyKeys.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME
                                        .getPropertyKey()))
                        .exists());
        assertTrue("data file crazy golf site characteristics not found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                        CgaFileMigrationApplication.properties
                                .getProperty(PropertyKeys.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                                        .getPropertyKey()))
                        .exists());
        assertTrue("data file suitcase characteristics not found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                        CgaFileMigrationApplication.properties
                                .getProperty(PropertyKeys.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME
                                        .getPropertyKey()))
                        .exists());
    }

    @Test
    public void createDataFilesDirectoryNotExists() throws IOException {
        // act
        CgaFileMigrationApplication.createDataFiles();
        // assert
        assertFalse("data file ball characteristics unexpected found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                        CgaFileMigrationApplication.properties
                                .getProperty(PropertyKeys.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME
                                        .getPropertyKey()))
                        .exists());
        assertFalse("data file crazy golf site characteristics unexpected found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                        CgaFileMigrationApplication.properties
                                .getProperty(PropertyKeys.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                                        .getPropertyKey()))
                        .exists());
        assertFalse("data file suitcase characteristics unexpected found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()) +
                        CgaFileMigrationApplication.properties
                                .getProperty(PropertyKeys.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME
                                        .getPropertyKey()))
                        .exists());
    }

    @Test
    public void createDirectories() throws IOException {
        // act
        CgaFileMigrationApplication.createDirectories();
        // assert
        assertTrue("log directory not found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_LOG_FILE_PATH.getPropertyKey()))
                        .exists());
        assertTrue("data directory not found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()))
                        .exists());
        assertTrue("data directory not found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())
                        + CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_CSV_FILE_ENLARGEMENT_PATH.getPropertyKey()))
                        .exists());
        assertTrue("data directory not found",
                new File(CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())
                        + CgaFileMigrationApplication.properties
                        .getProperty(PropertyKeys.PROPERTY_PDF_FILE_ENLARGEMENT_PATH.getPropertyKey()))
                        .exists());
    }

}