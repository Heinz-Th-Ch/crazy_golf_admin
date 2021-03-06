package importAndExport.exporters;

import dataStructures.CommonValues;
import dataStructures.ContentOfSuitCaseImpl;
import dataStructures.SuitCaseCharacteristicsImpl;
import utilities.ApplicationLoggerUtil;

import java.io.*;
import java.util.List;

/**
 * This is the exporter class for suit case characteristics.
 */
public class SuitCaseCharacteristicsExporter extends AbstractCsvDataExporter {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(SuitCaseCharacteristicsExporter.class);

    private final List<SuitCaseCharacteristicsImpl> sourceList;
    private final File targetCsvFile;
    private final BufferedWriter writer;

    private final String pathOfCsvFile;
    private final String contentsFileName = "ContentOfSuitCase_{&suitcasename}.csv";

    public SuitCaseCharacteristicsExporter(List<SuitCaseCharacteristicsImpl> sourceList, File targetCsvFile) throws FileNotFoundException {
        this.sourceList = sourceList;
        this.targetCsvFile = targetCsvFile;
        this.pathOfCsvFile = targetCsvFile.getParentFile().getPath() + new File("/").getPath();
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

    private String getContentsFileName(String identifier) {
        char[] tmpIdentifier = identifier.trim().toCharArray();
        StringBuffer newIdentifier = new StringBuffer();
        for (int i = 0; i < tmpIdentifier.length; i++) {
            if (tmpIdentifier[i] == ' ') {
                if (tmpIdentifier[i + 1] != ' ') {
                    newIdentifier.append(String.valueOf(tmpIdentifier[i++ + 1]).toUpperCase());
                }
            } else {
                newIdentifier.append(tmpIdentifier[i]);
            }
        }
        return contentsFileName.replace(CommonValues.SUIT_CASE_NAME_PLACE_HOLDER,
                newIdentifier.toString());
    }

    private void writeContentsList(List<ContentOfSuitCaseImpl> contents,
                                   File contentsFile) throws IOException {
        ContentOfSuitCaseExporter contentsExporter = new ContentOfSuitCaseExporter(contents, contentsFile);
        contentsExporter.executeExport();
    }

    private Class<?> writeData() throws IOException {
        int numberOfLinesExported = 0;
        Class<?> containingClass = null;
        for (SuitCaseCharacteristicsImpl entry : sourceList) {
            if (containingClass == null) {
                containingClass = entry.getClass();
            }
            String contentsFileName = getContentsFileName(entry.getIdentifier());
            StringBuffer dataLine = new StringBuffer()
                    .append(entry.getPrimaryKey()).append(csvSplitter)
                    .append(entry.getIdentifier()).append(csvSplitter)
                    .append(entry.getDescription()).append(csvSplitter)
                    .append(entry.getOwner()).append(csvSplitter)
                    .append(contentsFileName);
            writer.write(dataLine.toString());
            writer.newLine();
            numberOfLinesExported += 1;
            writeContentsList(entry.getContents(), new File(pathOfCsvFile + contentsFileName));
        }

        logger.info("{} data lines exported", numberOfLinesExported);
        return containingClass;
    }

    private void writeHeadLine() throws IOException {
        StringBuffer headLine = new StringBuffer()
                .append(tableTitlePrimaryKey).append(csvSplitter)
                .append(tableTitleSccIdentifier).append(csvSplitter)
                .append(tableTitleSccDescription).append(csvSplitter)
                .append(tableTitleSccOwner).append(csvSplitter)
                .append(tableTitleSccContentsFile);
        writer.write(headLine.toString());
        writer.newLine();
        logger.debug("head line to csv file written");
    }

}
