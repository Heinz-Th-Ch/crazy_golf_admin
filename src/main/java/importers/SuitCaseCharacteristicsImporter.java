package importers;

import dataStructures.ContentOfSuitCaseImpl;
import dataStructures.SuitCaseCharacteristicsImpl;
import org.jetbrains.annotations.VisibleForTesting;
import utilities.ApplicationLoggerUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the importer class for suit case characteristics.
 */
public class SuitCaseCharacteristicsImporter implements CsvDataImporter {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(SuitCaseCharacteristicsImporter.class);

    private final static String IDENTIFIER = "Bezeichnung";
    private final static String DESCRIPTION = "Eigenschaften";
    private final static String OWNER = "Besitzer";
    private final static String CONTENTS_FILE = "Datenfile Inhalt";

    private final static String EMPTY_LINE = ";;;;;;";

    private final static String CSV_SPLITTER = ";";

    private final BufferedReader reader;
    private final List<SuitCaseCharacteristicsImpl> targetList;
    private final String pathOfCsvFile;

    private Integer colIdentifier = -1;
    private Integer colDescription = -1;
    private Integer colOwner = -1;
    private Integer colContentsFile = -1;

    public SuitCaseCharacteristicsImporter(File sourceCsvFile,
                                           List<SuitCaseCharacteristicsImpl> targetList) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceCsvFile)));
        this.targetList = targetList;
        this.pathOfCsvFile = sourceCsvFile.getPath()
                .substring(0, sourceCsvFile.getPath().lastIndexOf(new File("/").getPath()) + 1);
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
            if (value.equals(IDENTIFIER)) {
                colIdentifier = i;
                continue;
            }
            if (value.equals(DESCRIPTION)) {
                colDescription = i;
                continue;
            }
            if (value.equals(OWNER)) {
                colOwner = i;
                continue;
            }
            if (value.equals(CONTENTS_FILE)) {
                colContentsFile = i;
            }
        }
        return colIdentifier != -1
                && colDescription != -1
                && colOwner != -1
                && colContentsFile != -1;
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
            List<ContentOfSuitCaseImpl> contentsList = new ArrayList<>(List.of());
            ContentOfSuitCaseImporter contentsImporter = new ContentOfSuitCaseImporter(
                    new File(pathOfCsvFile + dataColumns.get(colContentsFile)),
                    contentsList);
            contentsImporter.executeImport();
            targetList.add(new SuitCaseCharacteristicsImpl(targetList,
                    dataColumns.get(colIdentifier),
                    dataColumns.get(colDescription),
                    dataColumns.get(colOwner),
                    contentsList));
            numberOfLinesImported += 1;
        }
        logger.info("{} data lines imported", numberOfLinesImported);
    }

    @VisibleForTesting
    protected boolean isHeadLineUsable(List<String> headLine) throws IOException {
        boolean result = headLine.contains(IDENTIFIER)
                && headLine.contains(DESCRIPTION)
                && headLine.contains(OWNER)
                && headLine.contains(CONTENTS_FILE);
        if (!result) {
            logger.error("head line from csv file not usable");
        }
        return result;
    }

}
