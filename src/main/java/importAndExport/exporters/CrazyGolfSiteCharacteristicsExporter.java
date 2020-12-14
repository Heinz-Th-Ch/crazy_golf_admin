package importAndExport.exporters;

import dataStructures.*;
import importAndExport.CommonCsvValues;
import utilities.ApplicationLoggerUtil;

import java.io.*;
import java.util.List;

/**
 * This is the exporter class for crazy golf site characteristics.
 */
public class CrazyGolfSiteCharacteristicsExporter extends AbstractCsvDataExporter {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(CrazyGolfSiteCharacteristicsExporter.class);

    private final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    private final List<CrazyGolfSiteCharacteristicsImpl> sourceList;
    private final File targetCsvFile;
    private final BufferedWriter writer;

    private final String pathOfCsvFile;
    private final String contentsFileName = "HandicapCharacteristics_{&sitename}.csv";

    public CrazyGolfSiteCharacteristicsExporter(List<CrazyGolfSiteCharacteristicsImpl> sourceList,
                                                File targetCsvFile) throws FileNotFoundException {
        this.sourceList = sourceList;
        this.targetCsvFile = targetCsvFile;
        this.pathOfCsvFile = targetCsvFile.getPath()
                .substring(0, targetCsvFile.getPath().lastIndexOf(new File("/").getPath()) + 1);
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

    private String getContentsFileName(String siteName) {
        char[] tmpSiteName = siteName.trim().toCharArray();
        StringBuffer newSiteName = new StringBuffer();
        for (int i = 0; i < tmpSiteName.length; i++) {
            if (tmpSiteName[i] == ' ') {
                if (tmpSiteName[i + 1] != ' ') {
                    newSiteName.append(String.valueOf(tmpSiteName[i++ + 1]).toUpperCase());
                }
            } else {
                newSiteName.append(tmpSiteName[i]);
            }
        }
        return contentsFileName.replace(CommonValues.SITE_NAME_PLACE_HOLDER,
                newSiteName.toString());
    }

    private String getSuitCaseIdentifier(Integer foreignKeySuitCase) {
        for (SuitCaseCharacteristicsImpl entry : dataListContainer.getSuitCaseCharacteristics()) {
            if (entry.getPrimaryKey().equals(foreignKeySuitCase)) {
                return entry.getIdentifier();
            }
        }
        return NOT_APPLICABLE;
    }

    private void writeContentsList(List<HandicapCharacteristicsImpl> contents,
                                   File contentsFile) throws IOException {
        HandicapCharacteristicsExporter contentsExporter = new HandicapCharacteristicsExporter(contents, contentsFile);
        contentsExporter.executeExport();
    }

    private Class<?> writeData() throws IOException {
        int numberOfLinesExported = 0;
        Class<?> containingClass = null;
        for (CrazyGolfSiteCharacteristicsImpl entry : sourceList) {
            if (containingClass == null) {
                containingClass = entry.getClass();
            }
            String contentsFileName = getContentsFileName(entry.getSiteName());
            StringBuffer dataLine = new StringBuffer()
                    .append(entry.getPrimaryKey()).append(CSV_SPLITTER)
                    .append(entry.getSiteName()).append(CSV_SPLITTER)
                    .append(entry.getAddress()).append(CSV_SPLITTER)
                    .append(entry.getPostCode()).append(CSV_SPLITTER)
                    .append(entry.getTown()).append(CSV_SPLITTER)
                    .append(getSuitCaseIdentifier(entry.getForeignKeySuitCase())).append(CSV_SPLITTER)
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
                .append(PRIMARY_KEY).append(CSV_SPLITTER)
                .append(CGSC_SITE_NAME).append(CSV_SPLITTER)
                .append(CGSC_ADDRESS).append(CSV_SPLITTER)
                .append(CGSC_POST_CODE).append(CSV_SPLITTER)
                .append(CGSC_TOWN).append(CSV_SPLITTER)
                .append(CGSC_SUIT_CASE).append(CSV_SPLITTER)
                .append(CGSC_CONTENTS_FILE);
        writer.write(headLine.toString());
        writer.newLine();
        logger.debug("head line to csv file written");
    }

}
