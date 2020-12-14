package importAndExport.exporters;

import dataStructures.BallCharacteristicsImpl;
import dataStructures.DataListContainerImpl;
import dataStructures.HandicapCharacteristicsImpl;
import importAndExport.CommonCsvValues;
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
        return NOT_APPLICABLE;
    }

    private Class<?> writeData() throws IOException {
        int numberOfLinesExported = 0;
        Class<?> containingClass = null;
        for (HandicapCharacteristicsImpl entry : sourceList) {
            if (containingClass == null) {
                containingClass = entry.getClass();
            }
            StringBuffer dataLine = new StringBuffer()
                    .append(entry.getPrimaryKey()).append(CSV_SPLITTER)
                    .append(getBallIdentifier(entry.getForeignKeyBall())).append(CSV_SPLITTER)
                    .append(entry.getPositioning()).append(CSV_SPLITTER)
                    .append(entry.getCushioning()).append(CSV_SPLITTER)
                    .append(entry.getMarking()).append(CSV_SPLITTER)
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
                .append(HC_PRIMARY_KEY).append(CSV_SPLITTER)
                .append(HC_FOREIGN_KEY_BALL).append(CSV_SPLITTER)
                .append(HC_POSITIONING).append(CSV_SPLITTER)
                .append(HC_CUSHIONING).append(CSV_SPLITTER)
                .append(HC_MARKING).append(CSV_SPLITTER)
                .append(HC_REMARK);
        writer.write(headLine.toString());
        writer.newLine();
        logger.debug("head line to csv file written");
    }

}
