package applications;

import dataStructures.CommonValues;
import dataStructures.DataListContainerImpl;
import enumerations.DataImportFunction;
import enumerations.PropertyKeys;
import enumerations.WorkingLevel;
import importers.BallCharacteristicsImporter;
import importers.CrazyGolfSiteCharacteristicsImporter;
import importers.SuitCaseCharacteristicsImporter;
import org.jetbrains.annotations.VisibleForTesting;
import utilities.ApplicationLoggerUtil;
import utilities.PropertiesUtil;

import java.io.*;
import java.util.Properties;

/**
 * This is the data import application of crazy golf administration.<br>
 * It is used to prepare all in the application needed data files.<br><br>
 * The import functions are reading CSV-File and convert the received data to the several data classes like<br>
 * <ol>
 *     <li>BallCharacteristics.csv to class {@link dataStructures.BallCharacteristics}</li>
 *     <li>SuitCaseCharacteristics.csv to class {@link dataStructures.SuitCaseCharacteristics}</li>
 *     <li>CrazyGolfSiteCharacteristics.csv to class {@link dataStructures.CrazyGolfSiteCharacteristics}</li>
 * </ol>
 */
public class CgaDataImportApplication {

    @VisibleForTesting
    protected static final Properties properties = new Properties();
    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(CgaDataImportApplication.class);
    private static final int NUMBER_OF_ARGUMENTS = 2;
    private static final String PROPERTY_FILE_NAME = "CgaDataImportApplication.properties";
    private static final String RESOURCES = "resources";
    private static final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    @VisibleForTesting
    protected static DataImportFunction function;
    private static WorkingLevel workingLevel;
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
        if (dataCanBeLoad()) {
            importBallCharacteristics();
            importSuitCaseCharacteristics();
            importCrazyGolfSiteCharacteristics();
            storeImportedData();
        }
        logger.info("application ended");
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

    @VisibleForTesting
    protected static boolean dataCanBeLoad() {
        return function == DataImportFunction.OVERWRITE
                || dataListContainer.getBallCharacteristics().isEmpty()
                || dataListContainer.getCrazyGolfSiteCharacteristics().isEmpty()
                || dataListContainer.getSuitCaseCharacteristics().isEmpty();
    }

    @VisibleForTesting
    protected static void importBallCharacteristics() throws IOException {
        if (function == DataImportFunction.OVERWRITE
                || dataListContainer.getBallCharacteristics().isEmpty()) {
            if (function == DataImportFunction.OVERWRITE) {
                dataListContainer.getBallCharacteristics().clear();
            }
            BallCharacteristicsImporter importer = new BallCharacteristicsImporter(
                    new File(properties.getProperty(PropertyKeys.PROPERTY_CSV_FILE_PATH.getPropertyKey())
                            + properties.getProperty(PropertyKeys.PROPERTY_CSV_BALL_CHARACTERISTICS_FILE_NAME
                            .getPropertyKey())),
                    dataListContainer.getBallCharacteristics());
            importer.executeImport();
            dataListContainer.setBallCharacteristicsChanged();
        }
    }

    @VisibleForTesting
    protected static void importCrazyGolfSiteCharacteristics() throws IOException {
        if (function == DataImportFunction.OVERWRITE
                || dataListContainer.getCrazyGolfSiteCharacteristics().isEmpty()) {
            if (function == DataImportFunction.OVERWRITE) {
                dataListContainer.getCrazyGolfSiteCharacteristics().clear();
            }
            CrazyGolfSiteCharacteristicsImporter importer = new CrazyGolfSiteCharacteristicsImporter(
                    new File(properties.getProperty(PropertyKeys.PROPERTY_CSV_FILE_PATH.getPropertyKey())
                            + properties.getProperty(PropertyKeys.PROPERTY_CSV_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                            .getPropertyKey())),
                    dataListContainer.getCrazyGolfSiteCharacteristics());
            importer.executeImport();
            dataListContainer.setCrazyGolfSiteCharacteristicsChanged();
        }
    }

    @VisibleForTesting
    protected static void importSuitCaseCharacteristics() throws IOException {
        if (function == DataImportFunction.OVERWRITE
                || dataListContainer.getSuitCaseCharacteristics().isEmpty()) {
            if (function == DataImportFunction.OVERWRITE) {
                dataListContainer.getSuitCaseCharacteristics().clear();
            }
            SuitCaseCharacteristicsImporter importer = new SuitCaseCharacteristicsImporter(
                    new File(properties.getProperty(PropertyKeys.PROPERTY_CSV_FILE_PATH.getPropertyKey())
                            + properties.getProperty(PropertyKeys.PROPERTY_CSV_SUITCASE_CHARACTERISTICS_FILE_NAME
                            .getPropertyKey())),
                    dataListContainer.getSuitCaseCharacteristics());
            importer.executeImport();
            dataListContainer.setSuitCaseCharacteristicsChanged();
        }
    }

    private static void storeImportedData() throws IOException {
        String actualDataPath = properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey());
        if (dataListContainer.isBallCharacteristicsChanged()) {
            File ballCharacteristicsFile = new File(actualDataPath +
                    properties.getProperty(PropertyKeys.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME.getPropertyKey()));
            if (dataListContainer.storeBallCharacteristics(new FileOutputStream(ballCharacteristicsFile))) {
                logger.info("ball characteristics stored in file {}", ballCharacteristicsFile);
            }
            dataListContainer.resetBallCharacteristicsChanged();
        }
        if (dataListContainer.isCrazyGolfSiteCharacteristicsChanged()) {
            File crazyGolfSiteCharacteristicsFile = new File(actualDataPath +
                    properties.getProperty(PropertyKeys.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                            .getPropertyKey()));
            if (dataListContainer
                    .storeCrazyGolfSiteCharacteristics(new FileOutputStream(crazyGolfSiteCharacteristicsFile))) {
                logger.info("crazy golf site characteristics file {} created", crazyGolfSiteCharacteristicsFile);
            }
            dataListContainer.resetCrazyGolfSiteCharacteristicsChanged();
        }
        if (dataListContainer.isSuitCaseCharacteristicsChanged()) {
            File suitCaseCharacteristicsFile = new File(actualDataPath +
                    properties.getProperty(PropertyKeys.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME.getPropertyKey()));
            if (dataListContainer.storeSuitCaseCharacteristics(new FileOutputStream(suitCaseCharacteristicsFile))) {
                logger.info("suit case characteristics stored in file {}", suitCaseCharacteristicsFile);
            }
            dataListContainer.resetSuitCaseCharacteristicsChanged();
        }
    }

    private static void loadExistingData() throws IOException, ClassNotFoundException {
        String actualDataPath = properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey());
        File ballCharacteristicsFile = new File(actualDataPath +
                properties.getProperty(PropertyKeys.PROPERTY_DATA_BALL_CHARACTERISTICS_FILE_NAME.getPropertyKey()));
        if (dataListContainer.loadBallCharacteristics(new FileInputStream(ballCharacteristicsFile))){
            logger.info("ball characteristics loaded from file {}", ballCharacteristicsFile);
        }
        File crazyGolfSiteCharacteristicsFile = new File(actualDataPath +
                properties.getProperty(PropertyKeys.PROPERTY_DATA_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                        .getPropertyKey()));
        if (dataListContainer.loadCrazyGolfSiteCharacteristics(new FileInputStream(crazyGolfSiteCharacteristicsFile))){
            logger.info("crazy golf site characteristics loaded from file {}", crazyGolfSiteCharacteristicsFile);
        }
        File suitCaseCharacteristicsFile = new File(actualDataPath +
                properties.getProperty(PropertyKeys.PROPERTY_DATA_SUITCASE_CHARACTERISTICS_FILE_NAME.getPropertyKey()));
        if (dataListContainer.loadSuitCaseCharacteristics(new FileInputStream(suitCaseCharacteristicsFile))){
            logger.info("suit case characteristics loaded from file {}", suitCaseCharacteristicsFile);
        }
        logger.info("application initial load done");
    }

    private static void setActualValues(String[] arguments) {
        workingLevel = WorkingLevel.valueOf(arguments[0]);
        function = DataImportFunction.valueOf(arguments[1]);
        property_file_path_and_name = RESOURCES + "/" + workingLevel.getDirectoryName() + "/" + PROPERTY_FILE_NAME;
    }

}
