package applications;

import dataStructures.CommonValues;
import dataStructures.DataListContainerImpl;
import enumerations.FileMigrationFunction;
import enumerations.PropertyKeys;
import enumerations.WorkingLevel;
import org.jetbrains.annotations.VisibleForTesting;
import utilities.ApplicationLoggerUtil;
import utilities.PropertiesUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This is the file migration application of crazy golf administration.<br>
 * It is used to prepare all applications directories and files. It is a requirement, that the application can work
 * without any problems.
 */
public class CgaFileMigrationApplication {

    @VisibleForTesting
    protected static final Properties properties = new Properties();
    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(CgaFileMigrationApplication.class);
    private static final int NUMBER_OF_ARGUMENTS = 2;
    private static final String PROPERTY_FILE_NAME = "CgaFileMigrationApplication.properties";
    private static final String RESOURCES = "resources";
    private static final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    private static WorkingLevel workingLevel;
    private static String property_file_path_and_name;

    private static FileMigrationFunction function;

    public static void main(String... args) throws IOException {
        logger.info("application starting");
        checkArguments(args);
        setActualValues(args);
        logger.debug("application runs in {} mode", workingLevel.getWorkingMode());
        try {
            PropertiesUtil.loadProperties(properties, property_file_path_and_name);
            adjustProperties();
        } catch (IOException e) {
            logger.error("load and preparation of properties from {} failed. {}",
                    property_file_path_and_name,
                    e.getStackTrace());
            throw e;
        }
        logger.info("application initialized");
        logger.info("application started");
        switch (function) {
            case PREPARE:
                createDirectories();
                break;
            case INIT:
                createDirectories();
                createDataFiles();
                break;
            case CLEAR:
                clearDataFiles();
                break;
        }
        logger.setLogOutputStream(properties.getProperty(PropertyKeys.PROPERTY_LOG_FILE_PATH.getPropertyKey()),
                properties.getProperty(PropertyKeys.PROPERTY_LOG_FILE_NAME.getPropertyKey()));
        logger.info("application ended");
    }

    @VisibleForTesting
    protected static void createDirectories() throws IOException {
        if (new File(properties.getProperty(PropertyKeys.PROPERTY_LOG_FILE_PATH.getPropertyKey())).mkdirs()) {
            logger.info("log directory path {} created",
                    properties.getProperty(PropertyKeys.PROPERTY_LOG_FILE_PATH.getPropertyKey()));
        }
        if (new File(properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())).mkdirs()) {
            logger.info("data directory path {} created",
                    properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()));
        }
        if (new File(properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())
                + properties.getProperty(PropertyKeys.PROPERTY_CSV_FILE_ENLARGEMENT_PATH.getPropertyKey())).mkdirs()) {
            logger.info("data directory path {} created",
                    properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())
                            + properties.getProperty(PropertyKeys.PROPERTY_CSV_FILE_ENLARGEMENT_PATH.getPropertyKey()));
        }
        if (new File(properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())
                + properties.getProperty(PropertyKeys.PROPERTY_PDF_FILE_ENLARGEMENT_PATH.getPropertyKey())).mkdirs()) {
            logger.info("data directory path {} created",
                    properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())
                            + properties.getProperty(PropertyKeys.PROPERTY_PDF_FILE_ENLARGEMENT_PATH.getPropertyKey()));
        }
    }

    @VisibleForTesting
    protected static void createDataFiles() throws IOException {
        if (!new File(properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())).exists()) {
            logger.error("data directory path {} don't exist. File creation is not possible",
                    properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()));
            return;
        }

        String actualDataPath = properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey());

        File ballCharacteristicsFile = new File(actualDataPath +
                properties.getProperty(PropertyKeys.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME.getPropertyKey()));
        if (!ballCharacteristicsFile
                .exists()) {
            if (dataListContainer.storeBallCharacteristics(new FileOutputStream(ballCharacteristicsFile))) {
                logger.info("ball characteristics file {} created", ballCharacteristicsFile);
            }
        }

        File crazyGolfSiteCharacteristicsFile = new File(actualDataPath +
                properties.getProperty(PropertyKeys.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                        .getPropertyKey()));
        if (!crazyGolfSiteCharacteristicsFile
                .exists()) {
            if (dataListContainer
                    .storeCrazyGolfSiteCharacteristics(new FileOutputStream(crazyGolfSiteCharacteristicsFile))) {
                logger.info("crazy golf site characteristics file {} created", crazyGolfSiteCharacteristicsFile);
            }
        }

        File suitCaseCharacteristicsFile = new File(actualDataPath +
                properties.getProperty(PropertyKeys.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME.getPropertyKey()));
        if (!suitCaseCharacteristicsFile
                .exists()) {
            if (dataListContainer.storeSuitCaseCharacteristics(new FileOutputStream(suitCaseCharacteristicsFile))) {
                logger.info("suitcase characteristics file {} created", suitCaseCharacteristicsFile);
            }
        }

    }

    @VisibleForTesting
    protected static void clearDataFiles() throws IOException {
        if (!new File(properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())).exists()) {
            logger.error("data directory path {} don't exist. Clear of files is not possible",
                    properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey()));
            return;
        }

        String actualDataPath = properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey());

        File ballCharacteristicsFile = new File(actualDataPath +
                properties.getProperty(PropertyKeys.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME.getPropertyKey()));
        if (dataListContainer.storeBallCharacteristics(new FileOutputStream(ballCharacteristicsFile))) {
            logger.info("ball characteristics file {} cleared", ballCharacteristicsFile);
        }

        File crazyGolfSiteCharacteristicsFile = new File(actualDataPath +
                properties.getProperty(PropertyKeys.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                        .getPropertyKey()));
        if (dataListContainer
                .storeCrazyGolfSiteCharacteristics(new FileOutputStream(crazyGolfSiteCharacteristicsFile))) {
            logger.info("crazy golf site characteristics file {} cleared", crazyGolfSiteCharacteristicsFile);
        }

        File suitCaseCharacteristicsFile = new File(actualDataPath +
                properties.getProperty(PropertyKeys.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME.getPropertyKey()));
        if (dataListContainer.storeSuitCaseCharacteristics(new FileOutputStream(suitCaseCharacteristicsFile))) {
            logger.info("suitcase characteristics file {} cleared", suitCaseCharacteristicsFile);
        }

    }

    private static void adjustProperties() {
        properties.setProperty(PropertyKeys.PROPERTY_LOG_FILE_PATH.getPropertyKey(),
                properties.getProperty(PropertyKeys.PROPERTY_LOG_FILE_PATH.getPropertyKey())
                        .replace(CommonValues.DIRECTORY_PLACE_HOLDER,
                                workingLevel.getDirectoryName()));
        properties.setProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey(),
                properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())
                        .replace(CommonValues.DIRECTORY_PLACE_HOLDER,
                                workingLevel.getDirectoryName()));
    }

    @VisibleForTesting
    protected static void checkArguments(String[] args) {
        if (args.length == NUMBER_OF_ARGUMENTS)
            return;
        throw new IllegalArgumentException(String.format("illegal number of arguments. Expected: %d, received: %d",
                NUMBER_OF_ARGUMENTS,
                args.length));
    }

    private static void setActualValues(String[] arguments) {
        workingLevel = WorkingLevel.valueOf(arguments[0]);
        function = FileMigrationFunction.valueOf(arguments[1]);
        property_file_path_and_name = RESOURCES + "/" + workingLevel.getDirectoryName() + "/" + PROPERTY_FILE_NAME;
    }

}
