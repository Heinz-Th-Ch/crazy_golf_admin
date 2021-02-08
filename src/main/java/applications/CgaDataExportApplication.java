package applications;

import dataStructures.CommonValues;
import dataStructures.DataListContainerImpl;
import enumerations.DataExportFunction;
import enumerations.PropertyKeys;
import enumerations.WorkingLevel;
import importAndExport.exporters.BallCharacteristicsExporter;
import importAndExport.exporters.CrazyGolfSiteCharacteristicsExporter;
import importAndExport.exporters.SuitCaseCharacteristicsExporter;
import org.jetbrains.annotations.VisibleForTesting;
import utilities.ApplicationLoggerUtil;
import utilities.PropertiesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This is the data export application of crazy golf administration.<br>
 * The export functions are creating several types of output files like<br>
 * <ol>
 *     <li>CSV-Files for a possible re-import into a new crazy golf administration application</li>
 *     <li>PDF-Files for a pretty documentation of all crazy golf administration data and its print out</li>
 * </ol>
 * The real names of the CSV- and PDF-Files and the corresponding path are defined in the application relevant properties.
 */
public class CgaDataExportApplication {

    @VisibleForTesting
    protected static final Properties properties = new Properties();
    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(CgaDataExportApplication.class);
    private static final int NUMBER_OF_ARGUMENTS = 2;
    private static final String PROPERTY_FILE_NAME = "CgaDataExportApplication.properties";
    private static final String RESOURCES = "resources";
    private static final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    @VisibleForTesting
    protected static DataExportFunction function;
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
        switch (function) {
            case CSV:
                exportBallCharacteristics();
                exportSuitCaseCharacteristics();
                exportCrazyGolfSiteCharacteristics();
                break;
            case PDF:
                break;
            default:
                logger.warn("function {} is not yet implemented!", function);
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

    @VisibleForTesting
    protected static void exportBallCharacteristics() throws IOException {
        File targetCsvFile = new File(properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH
                .getPropertyKey())
                + properties.getProperty(PropertyKeys.PROPERTY_CSV_FILE_ENLARGEMENT_PATH.getPropertyKey())
                + properties.getProperty(PropertyKeys.PROPERTY_CSV_BALL_CHARACTERISTICS_FILE_NAME.getPropertyKey()));
        BallCharacteristicsExporter exporter = new BallCharacteristicsExporter(
                dataListContainer.getBallCharacteristics(),
                targetCsvFile);
        exporter.executeExport();
    }

    @VisibleForTesting
    protected static void exportCrazyGolfSiteCharacteristics() throws IOException {
        File targetCsvFile = new File(properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH
                .getPropertyKey())
                + properties.getProperty(PropertyKeys.PROPERTY_CSV_FILE_ENLARGEMENT_PATH.getPropertyKey())
                + properties.getProperty(PropertyKeys.PROPERTY_CSV_CRAZY_GOLF_SITE_CHARACTERISTICS_FILE_NAME
                .getPropertyKey()));
        CrazyGolfSiteCharacteristicsExporter exporter = new CrazyGolfSiteCharacteristicsExporter(
                dataListContainer.getCrazyGolfSiteCharacteristics(),
                targetCsvFile);
        exporter.executeExport();
    }

    @VisibleForTesting
    protected static void exportSuitCaseCharacteristics() throws IOException {
        File targetCsvFile = new File(properties.getProperty(PropertyKeys.PROPERTY_DATA_FILE_PATH.getPropertyKey())
                + properties.getProperty(PropertyKeys.PROPERTY_CSV_FILE_ENLARGEMENT_PATH.getPropertyKey())
                + properties.getProperty(PropertyKeys.PROPERTY_CSV_SUITCASE_CHARACTERISTICS_FILE_NAME
                .getPropertyKey()));
        SuitCaseCharacteristicsExporter exporter = new SuitCaseCharacteristicsExporter(
                dataListContainer.getSuitCaseCharacteristics(),
                targetCsvFile);
        exporter.executeExport();
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

    private static void setActualValues(String[] arguments) {
        workingLevel = WorkingLevel.valueOf(arguments[0]);
        function = DataExportFunction.valueOf(arguments[1]);
        property_file_path_and_name = RESOURCES + "/" + workingLevel.getDirectoryName() + "/" + PROPERTY_FILE_NAME;
    }

}
