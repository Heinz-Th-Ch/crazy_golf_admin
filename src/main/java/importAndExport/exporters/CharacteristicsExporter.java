package importAndExport.exporters;

/**
 * This is the interface of all data or characteristics exporter.
 */
public interface CharacteristicsExporter {

    /**
     * Executes the export from a list of source class to a data file, for instance a csv- or pdf-file.
     */
    void executeExport() throws Exception;

}
