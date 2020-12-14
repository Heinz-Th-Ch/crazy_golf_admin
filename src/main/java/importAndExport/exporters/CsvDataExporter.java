package importAndExport.exporters;

import java.io.IOException;

/**
 * This is the interface of all data or characteristics exporter.
 */
public interface CsvDataExporter {

    /**
     * Executes the export from a list of source class to a csv file.
     */
    void executeExport() throws IOException;

}
