package importers;

import dataStructures.BallCharacteristicsImpl;
import dataStructures.ContentOfSuitCaseImpl;
import dataStructures.DataListContainerImpl;
import org.jetbrains.annotations.VisibleForTesting;
import utilities.ApplicationLoggerUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the importer class for contents of a suit case.
 */
public class ContentOfSuitCaseImporter implements CsvDataImporter {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(ContentOfSuitCaseImporter.class);

    private final static String PRIMARY_KEY = "Position in Koffer";
    private final static String FOREIGN_KEY_BALL = "Bezeichnung";

    private final static String CSV_SPLITTER = ";";

    private static final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    private final BufferedReader reader;
    private final List<ContentOfSuitCaseImpl> targetList;

    protected Integer colPrimaryKey = -1;
    protected Integer colForeignKeyBall = -1;

    public ContentOfSuitCaseImporter(File sourceCsvFile,
                                     List<ContentOfSuitCaseImpl> targetList) throws IOException {
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
            if (value.equals(PRIMARY_KEY)) {
                colPrimaryKey = i;
                continue;
            }
            if (value.equals(FOREIGN_KEY_BALL)) {
                colForeignKeyBall = i;
            }
        }
        return colPrimaryKey != -1
                && colForeignKeyBall != -1;
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
            if (dataLine == null) {
                continue;
            }
            logger.debug("data line read: {}", dataLine);
            List<String> dataColumns = new ArrayList<>(List.of(dataLine.split(CSV_SPLITTER)));
            targetList.add(new ContentOfSuitCaseImpl(Integer.valueOf(dataColumns.get(colPrimaryKey)),
                    getForeignKeyBallByIdentifier(dataColumns.get(colForeignKeyBall))));
            numberOfLinesImported += 1;
        }
        logger.info("{} data lines imported", numberOfLinesImported);
    }

    @VisibleForTesting
    protected boolean isHeadLineUsable(List<String> headLine) throws IOException {
        boolean result = headLine.contains(PRIMARY_KEY)
                && headLine.contains(FOREIGN_KEY_BALL);
        if (!result) {
            logger.error("head line from csv file not usable");
        }
        return result;
    }

}
