package enumerations;

/**
 * This enum contains the sub function codes of the {@link applications.CgaFileMigrationApplication}.
 */
public enum FileMigrationFunction {

    /**
     * To prepare all needed paths for several crazy golf applications.
     */
    PREPARE,

    /**
     * To prepare all needed paths for several crazy golf applications and create each the needed empty data files.
     */
    INIT,

    /**
     * To remove all data files and replace them by the needed empty data files.
     */
    CLEAR

}
