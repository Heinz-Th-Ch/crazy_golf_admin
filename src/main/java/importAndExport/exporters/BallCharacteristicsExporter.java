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
                    .append(entry.getPrimaryKey()).append(csvSplitter)
                    .append(entry.getIdentifier()).append(csvSplitter)
                    .append(entry.getDescription()).append(csvSplitter)
                    .append(entry.getHardness().getImportValue()).append(csvSplitter)
                    .append(entry.getUpThrow()).append(csvSplitter)
                    .append(entry.getWeight()).append(csvSplitter)
                    .append(entry.getAngleFactor()).append(csvSplitter)
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
                .append(tableTitlePrimaryKey).append(csvSplitter)
                .append(tableTitleBcIdentifier).append(csvSplitter)
                .append(tableTitleBcDescription).append(csvSplitter)
                .append(tableTitleBcHardness).append(csvSplitter)
                .append(tableTitleBcUpThrow).append(csvSplitter)
                .append(tableTitleBcWeight).append(csvSplitter)
                .append(tableTitleBcAngleFactor).append(csvSplitter)
                .append(tableTitleBcComment);
        writer.write(headLine.toString());
        writer.newLine();
        logger.debug("head line to csv file written");
    }

}
