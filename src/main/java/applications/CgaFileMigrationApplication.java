package applications;

import dataStructures.CommonValues;
import enumerations.FileMigrationFunction;
import enumerations.WorkingLevel;
import utilities.ApplicationLoggerUtil;
import utilities.PropertiesUtil;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class CgaFileMigrationApplication {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(CgaFileMigrationApplication.class);

    private static final int NUMBER_OF_ARGUMENTS = 2;
    private static final String PROPERTY_FILE_NAME = "CgaFileMigrationApplication.properties";
    private static final Properties properties = new Properties();
    private static WorkingLevel workingLevel;
    private static FileMigrationFunction function;
    private static String property_file_path_and_name;

    public static void main(String... args) throws IOException {
        logger.info("application starting");
        checkArguments(args);
        setActualValues(args);
        logger.debug("application runs in {} mode", workingLevel.getWorkingMode());
        try {
            PropertiesUtil.loadProperties(properties, property_file_path_and_name);
            adjustProperties();
        } catch (IOException e) {
            logger.error("load and preparation of properties from {} failed. {}", property_file_path_and_name, e.getStackTrace());
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
                createFiles();
                break;
            case CLEAR:
                clearDirectories();
                break;
        }
        logger.setLogOutputStream(properties.getProperty(CommonValues.PROPERTY_LOG_FILE_PATH),
                properties.getProperty(CommonValues.PROPERTY_LOG_FILE_NAME));
        logger.info("application ended");
    }

    private static void createDirectories() throws IOException {
        if (new File(properties.getProperty(CommonValues.PROPERTY_LOG_FILE_PATH)).mkdirs()) {
            logger.info("directory path {} created", properties.getProperty(CommonValues.PROPERTY_LOG_FILE_PATH));
        }
    }

    private static void createFiles() {

    }

    private static void clearDirectories() {

    }

    private static void adjustProperties() {
        properties.setProperty(CommonValues.PROPERTY_LOG_FILE_PATH,
                properties.getProperty(CommonValues.PROPERTY_LOG_FILE_PATH).replace(CommonValues.DIRECTORY_PLACE_HOLDER,
                        workingLevel.getDirectoryName()));
    }

    private static void checkArguments(String[] args) {
        if (args.length == NUMBER_OF_ARGUMENTS)
            return;
        throw new IllegalArgumentException(String.format("illegal number of arguments. Expected: %d, received: %d",
                NUMBER_OF_ARGUMENTS,
                args.length));
    }

    private static void setActualValues(String[] arguments) {
        workingLevel = WorkingLevel.valueOf(arguments[0]);
        function = FileMigrationFunction.valueOf(arguments[1]);
        property_file_path_and_name = workingLevel.getDirectoryName() + "/" + PROPERTY_FILE_NAME;
    }


}
