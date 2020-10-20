package enumerations;

/**
 * This enum contains the sub function codes of the {@link applications.CgaFileMigrationApplication}.
 */
public enum FileMigrationFunction {

    /**
     * To remove all data files and replace them by the needed empty data files.
     */
    CLEAR,

    /**
     * To prepare all needed paths for several crazy golf applications and create each the needed empty data files.
     */
    INIT,

    /**
     * To prepare all needed paths for several crazy golf applications.
     */
    PREPARE


}
