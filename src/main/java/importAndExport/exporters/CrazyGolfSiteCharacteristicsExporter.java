package importAndExport.exporters;

import dataStructures.*;
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
        return notApplicable;
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
                    .append(entry.getPrimaryKey()).append(csvSplitter)
                    .append(entry.getSiteName()).append(csvSplitter)
                    .append(entry.getAddress()).append(csvSplitter)
                    .append(entry.getPostCode()).append(csvSplitter)
                    .append(entry.getTown()).append(csvSplitter)
                    .append(getSuitCaseIdentifier(entry.getForeignKeySuitCase())).append(csvSplitter)
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
                .append(tableTitleCgscSiteName).append(csvSplitter)
                .append(tableTitleCgscAddress).append(csvSplitter)
                .append(tableTitleCgscPostCode).append(csvSplitter)
                .append(tableTitleCgscTown).append(csvSplitter)
                .append(tableTitleCgscSuitCase).append(csvSplitter)
                .append(tableTitleCgscContentsFile);
        writer.write(headLine.toString());
        writer.newLine();
        logger.debug("head line to csv file written");
    }

}
