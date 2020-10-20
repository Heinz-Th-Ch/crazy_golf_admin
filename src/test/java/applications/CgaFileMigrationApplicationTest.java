package applications;

import abstracts.AbstractPlainJava;
import dataStructures.CommonValues;
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
    private final String DATA_FILE_BALL_CHAR = "ball_char.data";
    private final String DATA_FILE_GOLF_SITE = "golf_site.data";
    private final String DATA_FILE_SUIT_CASE = "suit_case.data";

    @Before
    public void setUp() throws Exception {
        CgaFileMigrationApplication.properties.setProperty(CommonValues.PROPERTY_LOG_FILE_PATH,
                getTestDataPath() + LOG_PATH_PART_TWO);
        CgaFileMigrationApplication.properties.setProperty(CommonValues.PROPERTY_DATA_FILE_PATH,
                getTestDataPath() + DATA_PATH_PART_TWO);

        CgaFileMigrationApplication.properties.setProperty(CommonValues.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME,
                DATA_FILE_BALL_CHAR);
        CgaFileMigrationApplication.properties.setProperty(CommonValues.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME,
                DATA_FILE_GOLF_SITE);
        CgaFileMigrationApplication.properties.setProperty(CommonValues.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME,
                DATA_FILE_SUIT_CASE);
    }

    @After
    public void tearDown() {
        new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME))
                .delete();
        new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME))
                .delete();
        new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME))
                .delete();

        new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_LOG_FILE_PATH))
                .delete();
        new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH))
                .delete();
    }

    @Test
    public void createDirectories() throws IOException {
        CgaFileMigrationApplication.createDirectories();
        assertTrue("log directory not found",
                new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_LOG_FILE_PATH))
                        .exists());
        assertTrue("data directory not found",
                new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH))
                        .exists());
    }

    @Test
    public void createDataFilesDirectoryExists() throws IOException {
        CgaFileMigrationApplication.createDirectories();
        CgaFileMigrationApplication.createDataFiles();
        assertTrue("data file ball characteristics not found",
                new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                        CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME))
                        .exists());
        assertTrue("data file crazy golf site characteristics not found",
                new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                        CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME))
                        .exists());
        assertTrue("data file suitcase characteristics not found",
                new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                        CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME))
                        .exists());
    }

    @Test
    public void createDataFilesDirectoryNotExists() throws IOException {
        CgaFileMigrationApplication.createDataFiles();
        assertFalse("data file ball characteristics unexpected found",
                new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                        CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME))
                        .exists());
        assertFalse("data file crazy golf site characteristics unexpected found",
                new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                        CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME))
                        .exists());
        assertFalse("data file suitcase characteristics unexpected found",
                new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                        CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME))
                        .exists());
    }

    @Test
    public void clearDataFilesDirectoryExists() throws IOException, InterruptedException {
        CgaFileMigrationApplication.createDirectories();
        CgaFileMigrationApplication.createDataFiles();
        long lastModifiedBallCharacteristics = new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME))
                .lastModified();
        long lastModifiedCrazyGolfSiteCharacteristics = new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME))
                .lastModified();
        long lastModifiedSuitCaseCharacteristics = new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME))
                .lastModified();
        Thread.sleep(1000);
        CgaFileMigrationApplication.clearDataFiles();
        assertTrue("data file ball characteristics not newer",
                lastModifiedBallCharacteristics < new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                        CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME))
                        .lastModified());
        assertTrue("data file crazy golf site characteristics not newer",
                lastModifiedCrazyGolfSiteCharacteristics < new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                        CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME))
                        .lastModified());
        assertTrue("data file suitcase characteristics not newer",
                lastModifiedSuitCaseCharacteristics < new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                        CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME))
                        .lastModified());
    }

    @Test
    public void clearDataFilesDirectoryNotExists() throws IOException {
        CgaFileMigrationApplication.clearDataFiles();
        assertFalse("data file ball characteristics unexpected found",
                new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                        CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME))
                        .exists());
        assertFalse("data file crazy golf site characteristics unexpected found",
                new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                        CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME))
                        .exists());
        assertFalse("data file suitcase characteristics unexpected found",
                new File(CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_FILE_PATH) +
                        CgaFileMigrationApplication.properties.getProperty(CommonValues.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME))
                        .exists());
    }

    @Test
    public void checkArgumentsWithCorrectSize() {
        CgaFileMigrationApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithUndersizedSize() {
        CgaFileMigrationApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS - 1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentsWithOversizeSize() {
        CgaFileMigrationApplication.checkArguments(new String[NUMBER_OF_ARGUMENTS + 1]);
    }

}