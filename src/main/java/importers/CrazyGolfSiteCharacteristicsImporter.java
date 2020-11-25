package importers;

import dataStructures.CrazyGolfSiteCharacteristicsImpl;
import dataStructures.DataListContainerImpl;
import dataStructures.HandicapCharacteristicsImpl;
import dataStructures.SuitCaseCharacteristicsImpl;
import org.jetbrains.annotations.VisibleForTesting;
import utilities.ApplicationLoggerUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the importer class for crazy golf site characteristics.
 */
public class CrazyGolfSiteCharacteristicsImporter implements CsvDataImporter {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(CrazyGolfSiteCharacteristicsImporter.class);

    private final static String SITE_NAME = "Anlage";
    private final static String ADDRESS = "Adresse";
    private final static String POST_CODE = "Postleitzahl";
    private final static String TOWN = "Ort";
    private final static String SUIT_CASE = "Bezeichnung Koffer";
    private final static String CONTENTS_FILE = "Datenfile Bahnen";

    private final static String EMPTY_LINE = ";;;;;;;;;;";

    private final static String CSV_SPLITTER = ";";

    private static final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    private final BufferedReader reader;
    private final List<CrazyGolfSiteCharacteristicsImpl> targetList;
    private final String pathOfCsvFile;

    private Integer colSiteName = -1;
    private Integer colAddress = -1;
    private Integer colPostCode = -1;
    private Integer colTown = -1;
    private Integer colSuitCase = -1;
    private Integer colContentsFile = -1;

    public CrazyGolfSiteCharacteristicsImporter(File sourceCsvFile,
                                                List<CrazyGolfSiteCharacteristicsImpl> targetList) throws IOException {
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
            if (value.equals(SITE_NAME)) {
                colSiteName = i;
                continue;
            }
            if (value.equals(ADDRESS)) {
                colAddress = i;
                continue;
            }
            if (value.equals(POST_CODE)) {
                colPostCode = i;
                continue;
            }
            if (value.equals(TOWN)) {
                colTown = i;
                continue;
            }
            if (value.equals(SUIT_CASE)) {
                colSuitCase = i;
                continue;
            }
            if (value.equals(CONTENTS_FILE)) {
                colContentsFile = i;
            }
        }
        return colSiteName != -1
                && colAddress != -1
                && colPostCode != -1
                && colTown != -1
                && colSuitCase != -1
                && colContentsFile != -1;
    }

    @VisibleForTesting
    protected Integer getForeignKeySuitCaseByIdentifier(String suitCaseIdentifier) {
        List<SuitCaseCharacteristicsImpl> characteristics = dataListContainer.getSuitCaseCharacteristics();
        for (SuitCaseCharacteristicsImpl entry : characteristics) {
            if (entry.getIdentifier().equals(suitCaseIdentifier)) {
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
            List<HandicapCharacteristicsImpl> contentsList = new ArrayList<>(List.of());
            HandicapCharacteristicsImporter contentsImporter = new HandicapCharacteristicsImporter(
                    new File(pathOfCsvFile + dataColumns.get(colContentsFile)),
                    contentsList);
            contentsImporter.executeImport();
            targetList.add(new CrazyGolfSiteCharacteristicsImpl(targetList,
                    getForeignKeySuitCaseByIdentifier(dataColumns.get(colSuitCase)),
                    dataColumns.get(colSiteName),
                    dataColumns.get(colAddress),
                    dataColumns.get(colPostCode),
                    dataColumns.get(colTown),
                    contentsList));
            numberOfLinesImported += 1;
        }
        logger.info("{} data lines imported", numberOfLinesImported);
    }

    @VisibleForTesting
    protected boolean isHeadLineUsable(List<String> headLine) throws IOException {
        boolean result = headLine.contains(SITE_NAME)
                && headLine.contains(ADDRESS)
                && headLine.contains(POST_CODE)
                && headLine.contains(TOWN)
                && headLine.contains(SUIT_CASE)
                && headLine.contains(CONTENTS_FILE);
        if (!result) {
            logger.error("head line from csv file not usable");
        }
        return result;
    }

}
