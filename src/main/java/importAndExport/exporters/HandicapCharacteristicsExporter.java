package importAndExport.exporters;

import dataStructures.BallCharacteristicsImpl;
import dataStructures.DataListContainerImpl;
import dataStructures.HandicapCharacteristicsImpl;
import utilities.ApplicationLoggerUtil;

import java.io.*;
import java.util.List;

/**
 * This is the exporter class for crazy golf site characteristics.
 */
public class HandicapCharacteristicsExporter extends AbstractCsvDataExporter {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(HandicapCharacteristicsExporter.class);

    private final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    private final List<HandicapCharacteristicsImpl> sourceList;
    private final File targetCsvFile;
    private final BufferedWriter writer;

    public HandicapCharacteristicsExporter(List<HandicapCharacteristicsImpl> sourceList,
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

    private String getBallIdentifier(Integer foreignKeyBall) {
        for (BallCharacteristicsImpl entry : dataListContainer.getBallCharacteristics()) {
            if (entry.getPrimaryKey().equals(foreignKeyBall)) {
                return entry.getIdentifier();
            }
        }
        return notApplicable;
    }

    private Class<?> writeData() throws IOException {
        int numberOfLinesExported = 0;
        Class<?> containingClass = null;
        for (HandicapCharacteristicsImpl entry : sourceList) {
            if (containingClass == null) {
                containingClass = entry.getClass();
            }
            StringBuffer dataLine = new StringBuffer()
                    .append(entry.getPrimaryKey()).append(csvSplitter)
                    .append(getBallIdentifier(entry.getForeignKeyBall())).append(csvSplitter)
                    .append(entry.getPositioning()).append(csvSplitter)
                    .append(entry.getCushioning()).append(csvSplitter)
                    .append(entry.getMarking()).append(csvSplitter)
                    .append(entry.getRemark());
            writer.write(dataLine.toString());
            writer.newLine();
            numberOfLinesExported += 1;
        }
        logger.info("{} data lines exported", numberOfLinesExported);
        return containingClass;
    }

    private void writeHeadLine() throws IOException {
        StringBuffer headLine = new StringBuffer()
                .append(tableTitleHcPrimaryKey).append(csvSplitter)
                .append(tableTitleHcForeignKeyBall).append(csvSplitter)
                .append(tableTitleHcPositioning).append(csvSplitter)
                .append(tableTitleHcCushioning).append(csvSplitter)
                .append(tableTitleHcMarking).append(csvSplitter)
                .append(tableTitleHcRemark);
        writer.write(headLine.toString());
        writer.newLine();
        logger.debug("head line to csv file written");
    }

}
