package importAndExport.exporters;

import utilities.ApplicationLoggerUtil;
import utilitites.CommonCsvValueUtility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * This is the abstract class of all csv data or characteristics exporter.
 */
abstract public class AbstractCsvDataExporter extends CommonCsvValueUtility implements CharacteristicsExporter {

    protected void finalizeExport(File targetCsvFile,
                                  BufferedWriter writer,
                                  Class<?> mainClass,
                                  Class<?> containingClass,
                                  ApplicationLoggerUtil logger) throws IOException {
        writer.close();
        logger.info("data of {}<{}> exported to csv file {}. Total space: {}, free space: {}",
                mainClass,
                containingClass == null ? "?" : containingClass.getSimpleName(),
                targetCsvFile.getPath(),
                targetCsvFile.getTotalSpace(),
                targetCsvFile.getFreeSpace());
    }

}
