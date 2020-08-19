package applications;

import dataStructures.CommonValues;
import enumerations.WorkingLevel;
import utilities.ApplicationLoggerUtil;
import utilities.PropertiesUtil;

import java.io.IOException;
import java.util.Properties;

public class CgaMainApplication {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(CgaMainApplication.class);

    private static final int NUMBER_OF_ARGUMENTS = 1;
    private static final String PROPERTY_FILE_NAME = "CgaMainApplication.properties";
    private static final Properties properties = new Properties();
    private static WorkingLevel workingLevel;
    private static String property_file_path_and_name;

    public static void main(String... args) throws IOException {
        logger.info("application starting");
        checkArguments(args);
        setWorkingLevelValues(args[0]);
        logger.debug("application runs in {} mode", workingLevel.getWorkingMode());
        try {
            PropertiesUtil.loadProperties(properties, property_file_path_and_name);
            adjustProperties();
        } catch (IOException e) {
            logger.error("load of properties from {} failed. {}", property_file_path_and_name, e.getStackTrace());
            throw e;
        }
        logger.info("application initialized");
        logger.setLogOutputStream(properties.getProperty(CommonValues.PROPERTY_LOG_FILE_PATH),
                properties.getProperty(CommonValues.PROPERTY_LOG_FILE_NAME));
        logger.info("application started");
        logger.info("application ended");
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

    private static void setWorkingLevelValues(String argument) {
        workingLevel = WorkingLevel.valueOf(argument);
        property_file_path_and_name = workingLevel.getDirectoryName() + "/" + PROPERTY_FILE_NAME;
    }

}
