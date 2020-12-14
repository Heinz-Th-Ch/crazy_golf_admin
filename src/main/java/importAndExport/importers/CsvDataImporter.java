package importAndExport.importers;

import java.io.IOException;

/**
 * This is the interface of all data or characteristics importer.
 */
public interface CsvDataImporter {

    /**
     * Executes the import from a csv file into a list of a target class.
     */
    void executeImport() throws IOException;

}
