package importAndExport.importers;

import dataStructures.BallCharacteristicsImpl;
import dataStructures.DataListContainerImpl;
import dataStructures.HandicapCharacteristicsImpl;
import importAndExport.CommonCsvValues;
import org.jetbrains.annotations.VisibleForTesting;
import utilities.ApplicationLoggerUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the importer class for crazy golf site characteristics.
 */
public class HandicapCharacteristicsImporter extends CommonCsvValues implements CsvDataImporter {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(HandicapCharacteristicsImporter.class);

    private final static String EMPTY_LINE = ";;;;;;;;;;";

    private static final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    private final BufferedReader reader;
    private final List<HandicapCharacteristicsImpl> targetList;

    private Integer colPrimaryKey = -1;
    private Integer colForeignKeyBall = -1;
    private Integer colPositioning = -1;
    private Integer colCushioning = -1;
    private Integer colMarking = -1;
    private Integer colRemark = -1;

    public HandicapCharacteristicsImporter(File sourceCsvFile,
                                           List<HandicapCharacteristicsImpl> targetList) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceCsvFile)));
        this.targetList = targetList;
        logger.info("importer initialized. csv source file: {}",
                sourceCsvFile.getName());
    }

    /**
     * Executes the import from a csv file into a list of a target class.
     */
    @Override
    public void executeImport() throws IOException {
        List<String> headLine = new ArrayList<>(List.of(reader.readLine().split(CSV_SPLITTER)));
        logger.debug("head line from csv file read");
        if (isHeadLineUsable(headLine)) {
            extractColumnsOfHeadLine(headLine);
            importDataLines();
        }
        logger.info("importer finished");
    }

    @VisibleForTesting
    protected boolean extractColumnsOfHeadLine(List<String> headLine) {
        for (int i = 0; i < headLine.size(); i++) {
            String value = headLine.get(i);
            if (value.equals(HC_PRIMARY_KEY)) {
                colPrimaryKey = i;
                continue;
            }
            if (value.equals(HC_FOREIGN_KEY_BALL)) {
                colForeignKeyBall = i;
                continue;
            }
            if (value.equals(HC_POSITIONING)) {
                colPositioning = i;
                continue;
            }
            if (value.equals(HC_CUSHIONING)) {
                colCushioning = i;
                continue;
            }
            if (value.equals(HC_MARKING)) {
                colMarking = i;
                continue;
            }
            if (value.equals(HC_REMARK)) {
                colRemark = i;
            }
        }
        return colPrimaryKey != -1
                && colForeignKeyBall != -1
                && colPositioning != -1
                && colCushioning != -1
                && colMarking != -1
                && colRemark != -1;
    }

    @VisibleForTesting
    protected Integer getForeignKeyBallByIdentifier(String ballIdentifier) {
        List<BallCharacteristicsImpl> characteristics = dataListContainer.getBallCharacteristics();
        for (BallCharacteristicsImpl entry : characteristics) {
            if (entry.getIdentifier().equals(ballIdentifier)) {
                return entry.getPrimaryKey();
            }
        }
        return -1;
    }

    private void importDataLines() throws IOException {
        int numberOfLinesImported = 0;
        String dataLine = "";
        while (dataLine != null) {
            dataLine = reader.readLine();
            if (dataLine == null || dataLine.equals(EMPTY_LINE)) {
                continue;
            }
            logger.debug("data line read: {}", dataLine);
            List<String> dataColumns = new ArrayList<>(List.of(dataLine.split(CSV_SPLITTER)));
            targetList.add(new HandicapCharacteristicsImpl(Integer.valueOf(dataColumns.get(colPrimaryKey)),
                    getForeignKeyBallByIdentifier(dataColumns.get(colForeignKeyBall)),
                    dataColumns.get(colPositioning),
                    dataColumns.get(colCushioning),
                    dataColumns.get(colMarking),
                    dataColumns.get(colRemark)));
            numberOfLinesImported += 1;
        }
        logger.info("{} data lines imported", numberOfLinesImported);
    }

    @VisibleForTesting
    protected boolean isHeadLineUsable(List<String> headLine) throws IOException {
        boolean result = headLine.contains(HC_PRIMARY_KEY)
                && headLine.contains(HC_FOREIGN_KEY_BALL)
                && headLine.contains(HC_POSITIONING)
                && headLine.contains(HC_CUSHIONING)
                && headLine.contains(HC_MARKING)
                && headLine.contains(HC_REMARK);
        if (!result) {
            StringBuffer headLineElements = new StringBuffer();
            for (String element : headLine) {
                if (headLineElements.length() != 0) {
                    headLineElements.append(" / ");
                }
                headLineElements.append(element);
            }
            logger.error("head line from csv file not usable. Head line: {}", headLineElements.toString());
        }
        return result;
    }

}
