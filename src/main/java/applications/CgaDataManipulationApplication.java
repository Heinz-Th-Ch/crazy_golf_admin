package applications;

import dataStructures.CommonValues;
import dataStructures.DataListContainerImpl;
import enumerations.DataManipulationArgument;
import enumerations.PropertyKeys;
import enumerations.WorkingLevel;
import utilities.ApplicationLoggerUtil;
import utilities.PropertiesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * This is the data manipulation application of crazy golf application.<br><br>
 * There are three groups of data manipulation types like
 * <ol>
 *     <li>manipulation of all ball characteristics data</li>
 *     <li>manipulation of all suit case characteristics data</li>
 *     <li>manipulation of all crazy golf site characteristics data</li>
 * </ol>
 * Each of these manipulation group contains the following data operations
 * <ol>
 *     <li>to LIST data entries of a group conditional on several criteria</li>
 *     <li>to SELECT a single data entry of a group, defined by primary key or identification</li>
 *     <li>to INSERT a single data entry of a group</li>
 *     <li>to UPDATE a single data entry of a group, defined by primary key</li>
 *     <li>to DELETE a single data entry of a group, defined by primary key or identification</li>
 * </ol>
 */
public class CgaDataManipulationApplication {

    protected static final Properties properties = new Properties();
    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(CgaDataManipulationApplication.class);
    private static final int NUMBER_OF_ARGUMENTS = 2;
    private static final String RESOURCES = "resources";
    private static final String PROPERTY_FILE_NAME = "CgaDataManipulationApplication.properties";
    private static final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    private static WorkingLevel workingLevel;
    private static DataManipulationArgument saveState;
    private static String property_file_path_and_name;

    public static void main(String... args) throws IOException, ClassNotFoundException {
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
        logger.setLogOutputStream(properties.getProperty(PropertyKeys.PROPERTY_LOG_FILE_PATH.getPropertyKey()),
                properties.getProperty(PropertyKeys.PROPERTY_LOG_FILE_NAME.getPropertyKey()));
        logger.info("application initialized");
        loadExistingData();
        logger.info("application started");
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

    private static void checkArguments(String[] args) {
        if (args.length == NUMBER_OF_ARGUMENTS)
            return;
        throw new IllegalArgumentException(String.format("illegal number of arguments. Expected: %d, received: %d",
                NUMBER_OF_ARGUMENTS,
                args.length));
    }

    private static void loadExistingData() throws IOException, ClassNotFoundException {
        String actualDataPath = properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey());
        File ballCharacteristicsFile = new File(actualDataPath +
                properties.getProperty(PropertyKeys.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME.getPropertyKey()));
        if (dataListContainer.loadBallCharacteristics(new FileInputStream(ballCharacteristicsFile))) {
            logger.info("ball characteristics loaded from file {}", ballCharacteristicsFile);
        }
        File crazyGolfSiteCharacteristicsFile = new File(actualDataPath +
                properties.getProperty(PropertyKeys.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                        .getPropertyKey()));
        if (dataListContainer.loadCrazyGolfSiteCharacteristics(new FileInputStream(crazyGolfSiteCharacteristicsFile))) {
            logger.info("crazy golf site characteristics loaded from file {}", crazyGolfSiteCharacteristicsFile);
        }
        File suitCaseCharacteristicsFile = new File(actualDataPath +
                properties.getProperty(PropertyKeys.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME.getPropertyKey()));
        if (dataListContainer.loadSuitCaseCharacteristics(new FileInputStream(suitCaseCharacteristicsFile))) {
            logger.info("suit case characteristics loaded from file {}", suitCaseCharacteristicsFile);
        }
        logger.info("application initial load done");
    }

    private static void setActualValues(String[] args) {
        workingLevel = WorkingLevel.valueOf(args[0]);
        saveState = DataManipulationArgument.valueOf(args[1]);
        property_file_path_and_name = RESOURCES + "/" + workingLevel.getDirectoryName() + "/" + PROPERTY_FILE_NAME;
    }

}
