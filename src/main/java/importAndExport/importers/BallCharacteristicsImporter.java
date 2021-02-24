package importAndExport.importers;

import dataStructures.BallCharacteristicsImpl;
import enumerations.Hardness;
import org.jetbrains.annotations.VisibleForTesting;
import utilities.ApplicationLoggerUtil;
import utilitites.CommonCsvValueUtility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the importer class for ball characteristics.
 */
public class BallCharacteristicsImporter extends CommonCsvValueUtility implements CsvDataImporter {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(BallCharacteristicsImporter.class);

    private final BufferedReader reader;
    private final List<BallCharacteristicsImpl> targetList;

    private Integer colPrimaryKey = -1;
    private Integer colIdentifier = -1;
    private Integer colDescription = -1;
    private Integer colHardness = -1;
    private Integer colUpThrow = -1;
    private Integer colWeight = -1;
    private Integer colAngleFactor = -1;
    private Integer colComment = -1;

    private Boolean newFileType = false;

    public BallCharacteristicsImporter(File sourceCsvFile,
                                       List<BallCharacteristicsImpl> targetList) throws IOException {
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
        List<String> headLine = new ArrayList<>(List.of(reader.readLine().split(csvSplitter)));
        logger.debug("head line from csv file read");
        if (isHeadLineUsable(headLine)) {
            extractColumnsOfHeadLine(headLine);
            importDataLines();
        }
        logger.info("importer finished");
    }

    @VisibleForTesting
    protected Hardness defineHardness(String importValue) {
        for (Hardness entry : Hardness.values()) {
            if (entry.getImportValue().equals(importValue)) {
                return entry;
            }
        }
        return Hardness.UNDEF;
    }

    @VisibleForTesting
    protected boolean extractColumnsOfHeadLine(List<String> headLine) {
        for (int i = 0; i < headLine.size(); i++) {
            String value = headLine.get(i);
            if (value.equals(tableTitlePrimaryKey)) {
                colPrimaryKey = i;
                continue;
            }
            if (value.equals(tableTitleBcIdentifier)) {
                colIdentifier = i;
                continue;
            }
            if (value.equals(tableTitleBcDescription)) {
                colDescription = i;
                continue;
            }
            if (value.equals(tableTitleBcHardness)) {
                colHardness = i;
                continue;
            }
            if (value.equals(tableTitleBcUpThrow)) {
                colUpThrow = i;
                continue;
            }
            if (value.equals(tableTitleBcWeight)) {
                colWeight = i;
                continue;
            }
            if (value.equals(tableTitleBcAngleFactor)) {
                colAngleFactor = i;
                continue;
            }
            if (value.equals(tableTitleBcComment)) {
                colComment = i;
            }
        }
        return colIdentifier != -1
                && colDescription != -1
                && colHardness != -1
                && colUpThrow != -1
                && colWeight != -1
                && colAngleFactor != -1
                && colComment != -1;
    }

    @VisibleForTesting
    protected String getOptionalData(List<String> dataColumns, Integer optionalDataColumn) {
        if (dataColumns.size() > optionalDataColumn) {
            return dataColumns.get(optionalDataColumn);
        }
        return "";
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
            List<String> dataColumns = new ArrayList<>(List.of(dataLine.split(csvSplitter)));
            if (newFileType) {
                targetList.add(new BallCharacteristicsImpl(Integer.valueOf(dataColumns.get(colPrimaryKey)),
                        dataColumns.get(colIdentifier),
                        dataColumns.get(colDescription),
                        defineHardness(dataColumns.get(colHardness)),
                        Integer.valueOf(dataColumns.get(colUpThrow)),
                        Integer.valueOf(dataColumns.get(colWeight)),
                        Double.valueOf(dataColumns.get(colAngleFactor)),
                        getOptionalData(dataColumns, colComment)));
            } else {
                targetList.add(new BallCharacteristicsImpl(targetList,
                        dataColumns.get(colIdentifier),
                        dataColumns.get(colDescription),
                        defineHardness(dataColumns.get(colHardness)),
                        Integer.valueOf(dataColumns.get(colUpThrow)),
                        Integer.valueOf(dataColumns.get(colWeight)),
                        Double.valueOf(dataColumns.get(colAngleFactor)),
                        getOptionalData(dataColumns, colComment)));
            }
            numberOfLinesImported += 1;
        }
        logger.info("{} data lines imported", numberOfLinesImported);
    }

    @VisibleForTesting
    protected boolean isHeadLineUsable(List<String> headLine) throws IOException {
        newFileType = headLine.contains(tableTitlePrimaryKey);
        if (newFileType) {
            logger.debug("csv file is a new type file");
        }
        boolean result = headLine.contains(tableTitleBcIdentifier)
                && headLine.contains(tableTitleBcDescription)
                && headLine.contains(tableTitleBcHardness)
                && headLine.contains(tableTitleBcUpThrow)
                && headLine.contains(tableTitleBcWeight)
                && headLine.contains(tableTitleBcAngleFactor)
                && headLine.contains(tableTitleBcComment);
        if (!result) {
            StringBuffer headLineElements = new StringBuffer();
            for (String element : headLine) {
                if (headLineElements.length() != 0) {
                    headLineElements.append(" / ");
                }
                headLineElements.append(element);
            }
            logger.error("head line from csv file not usable. Received head line: {}\n"
                            + "expected values: {} / {} / {} / {} / {} / {} / {}",
                    headLineElements.toString(),
                    tableTitleBcIdentifier,
                    tableTitleBcDescription,
                    tableTitleBcHardness,
                    tableTitleBcUpThrow,
                    tableTitleBcWeight,
                    tableTitleBcAngleFactor,
                    tableTitleBcComment);
        }
        return result;
    }

}