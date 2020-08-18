package applications;

import enumerations.WorkingLevel;
import utilities.ApplicationLoggerUtil;

import java.util.Properties;

public class CgaFileMigrationApplication {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(CgaFileMigrationApplication.class);

    private static final int NUMBER_OF_ARGUMENTS = 1;
    private static final String PROPERTY_FILE_NAME = "CgaFileMigrationApplication.properties";
    private static final Properties properties = new Properties();
    private static WorkingLevel workingLevel;
    private static String property_file_path_and_name;

    public static void main(String... args){

    }

}
