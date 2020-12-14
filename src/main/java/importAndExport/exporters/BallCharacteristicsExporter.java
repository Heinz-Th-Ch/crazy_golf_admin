package importAndExport.exporters;

import dataStructures.BallCharacteristicsImpl;
import utilities.ApplicationLoggerUtil;

import java.io.*;
import java.util.List;

/**
 * This is the exporter class for ball characteristics.
 */
public class BallCharacteristicsExporter extends AbstractCsvDataExporter {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(BallCharacteristicsExporter.class);

    private final List<BallCharacteristicsImpl> sourceList;
    private final File targetCsvFile;
    private final BufferedWriter writer;

    public BallCharacteristicsExporter(List<BallCharacteristicsImpl> sourceList,
                                       File targetCsvFile) throws FileNotFoundException {
        this.sourceList = sourceList;
        this.targetCsvFile = targetCsvFile;
        this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.targetCsvFile)));
    }

    /**
     * Executes the export from a list of source class to a csv file.
     */
    @Override
    public void executeExport() throws IOException {
        writeHeadLine();
        finalizeExport(targetCsvFile,
                writer,
                sourceList.getClass(),
                writeData(),
                logger);
    }

    private Class<?> writeData() throws IOException {
        int numberOfLinesExported = 0;
        Class<?> containingClass = null;
        for (BallCharacteristicsImpl entry : sourceList) {
            if (containingClass == null) {
                containingClass = entry.getClass();
            }
            StringBuffer dataLine = new StringBuffer()
                    .append(entry.getPrimaryKey()).append(CSV_SPLITTER)
                    .append(entry.getIdentifier()).append(CSV_SPLITTER)
                    .append(entry.getDescription()).append(CSV_SPLITTER)
                    .append(entry.getHardness().getImportValue()).append(CSV_SPLITTER)
                    .append(entry.getUpThrow()).append(CSV_SPLITTER)
                    .append(entry.getWeight()).append(CSV_SPLITTER)
                    .append(entry.getAngleFactor()).append(CSV_SPLITTER)
                    .append(entry.getComment());
            writer.write(dataLine.toString());
            writer.newLine();
            numberOfLinesExported += 1;
        }
        logger.info("{} data lines exported", numberOfLinesExported);
        return containingClass;
    }

    private void writeHeadLine() throws IOException {
        StringBuffer headLine = new StringBuffer()
                .append(PRIMARY_KEY).append(CSV_SPLITTER)
                .append(BC_IDENTIFIER).append(CSV_SPLITTER)
                .append(BC_DESCRIPTION).append(CSV_SPLITTER)
                .append(BC_HARDNESS).append(CSV_SPLITTER)
                .append(BC_UP_THROW).append(CSV_SPLITTER)
                .append(BC_WEIGHT).append(CSV_SPLITTER)
                .append(BC_ANGLE_FACTOR).append(CSV_SPLITTER)
                .append(BC_COMMENT);
        writer.write(headLine.toString());
        writer.newLine();
        logger.debug("head line to csv file written");
    }

}
