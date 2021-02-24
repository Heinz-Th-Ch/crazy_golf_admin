package importAndExport.exporters;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.TextAlignment;
import dataStructures.*;
import org.apache.commons.lang3.tuple.Pair;
import utilities.ApplicationLoggerUtil;
import utilitites.CommonPdfAttributeUtility;
import utilitites.CommonPdfValueUtility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is for the moment a trial class in which we use pdf trials.
 */
public class AllCharacteristicsPdfExporter extends CommonPdfValueUtility implements CharacteristicsExporter {

    private static final ApplicationLoggerUtil logger = new ApplicationLoggerUtil(AllCharacteristicsPdfExporter.class);

    private final CommonPdfAttributeUtility pdfAttributeUtility = new CommonPdfAttributeUtility();

    private final List<BallCharacteristicsImpl> sourceListBall;
    private final List<CrazyGolfSiteCharacteristicsImpl> sourceListCrazyGolfSite;
    private final List<SuitCaseCharacteristicsImpl> sourceListSuitCase;
    private final File targetPdfFile;
    private final PdfWriter writer;

    private final Style styleHelvetica10;
    private final Style styleHelvetica12;
    private final Style styleHelvetica16;
    private final Style styleHelveticaBold12;
    private final Style styleHelveticaBold16;
    private final Style styleHelveticaBold48;
    private final Style styleHelveticaBoldItalic14;
    private final Style styleHelveticaItalic14;

    private final PdfDocument pdfDocument;
    private final Document document;
    private final CommonPdfAttributeUtility.PageNumberingHandler pageNumberingHandler = pdfAttributeUtility
            .getPageNumberingHandler();

    public AllCharacteristicsPdfExporter(List<BallCharacteristicsImpl> sourceListBall,
                                         List<CrazyGolfSiteCharacteristicsImpl> sourceListCrazyGolfSite,
                                         List<SuitCaseCharacteristicsImpl> sourceListSuitCase,
                                         File targetPdfFile) throws IOException {
        this.sourceListBall = sourceListBall;
        this.sourceListCrazyGolfSite = sourceListCrazyGolfSite;
        this.sourceListSuitCase = sourceListSuitCase;
        this.targetPdfFile = targetPdfFile;
        this.writer = new PdfWriter(this.targetPdfFile);
        // create both relevant documents
        Pair<PdfDocument, Document> creationResult = pdfAttributeUtility.createPdfOutputFile(writer,
                PageSize.A4,
                pageNumberingHandler);
        this.pdfDocument = creationResult.getLeft();
        this.document = creationResult.getRight();
        // prepare of used styles
        styleHelvetica10 = pdfAttributeUtility.createStyle(StandardFonts.HELVETICA, 10);
        styleHelvetica12 = pdfAttributeUtility.createStyle(StandardFonts.HELVETICA, 12);
        styleHelvetica16 = pdfAttributeUtility.createStyle(StandardFonts.HELVETICA, 16);
        styleHelveticaBold12 = pdfAttributeUtility.createStyle(StandardFonts.HELVETICA_BOLD);
        styleHelveticaBold16 = pdfAttributeUtility.createStyle(StandardFonts.HELVETICA_BOLD, 16);
        styleHelveticaBold48 = pdfAttributeUtility.createStyle(StandardFonts.HELVETICA_BOLD, 48);
        styleHelveticaBoldItalic14 = pdfAttributeUtility.createStyle(StandardFonts.HELVETICA_BOLDOBLIQUE, 14);
        styleHelveticaItalic14 = pdfAttributeUtility.createStyle(StandardFonts.HELVETICA_OBLIQUE, 14);
    }

    /**
     * Executes the export from a list of source class to a pdf file.
     */
    @Override
    public void executeExport() throws IOException {
        writeDocumentTitlePage();
        finalizeExport(writeBallData(),
                writeSuitCaseData(),
                writeCrazyGolfSiteData());
    }

    private void addStyle(Style style, Text... texts) {
        for (Text element : texts) {
            element.addStyle(style);
        }
    }

    private Paragraph createParagraphWithTab(Integer tabSize, Text titleText, Text dataText) {
        Paragraph paragraph = new Paragraph();
        paragraph.addTabStops(new TabStop(tabSize, TabAlignment.LEFT));
        paragraph.add(titleText);
        paragraph.add(new Tab());
        paragraph.add(dataText);
        return paragraph;
    }

    private String[] extractContentOfSuitCase(List<ContentOfSuitCaseImpl> contents,
                                              int numberOfTableDataGroups,
                                              int index,
                                              int elementDisplacement) {
        List<String> localElements = new ArrayList<>();
        for (int i = 0; i < numberOfTableDataGroups; i++) {
            if (index + (i * elementDisplacement) < contents.size()) {
                ContentOfSuitCaseImpl entry = contents.get(index + (i * elementDisplacement));
                localElements.add(entry.getPrimaryKey().toString());
                localElements.add(getBallIdentifier(entry.getForeignKeyBall()));
            } else {
                localElements.add("");
                localElements.add("");
            }
        }
        String[] returnElements = new String[localElements.size()];
        for (int i = 0; i < localElements.size(); i++) {
            returnElements[i] = localElements.get(i);
        }
        return returnElements;
    }

    private void finalizeExport(Pair<Class<?>, Class<?>> ballClasses,
                                Pair<Class<?>, Class<?>> suitCaseClasses,
                                Pair<Class<?>, Class<?>> crazyGolfSiteClasses) throws IOException {
        document.close();
        logger.info("data exported to pdf file {}. Data sources are:{}{}<{}>{}{}<{}>{}{}<{}>",
                targetPdfFile.getPath(),
                newLine,
                tabulator + ballClasses.getLeft(),
                ballClasses.getRight() == null ? "?" : ballClasses.getRight().getSimpleName(),
                newLine,
                tabulator + suitCaseClasses.getLeft(),
                suitCaseClasses.getRight() == null ? "?" : suitCaseClasses.getRight().getSimpleName(),
                newLine,
                tabulator + crazyGolfSiteClasses.getLeft(),
                crazyGolfSiteClasses.getRight() == null ? "?" : crazyGolfSiteClasses.getRight().getSimpleName());
    }

    private String getActualDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd. MMMM yyyy 'um' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    private String getBallIdentifier(Integer foreignKeyBall) {
        for (BallCharacteristicsImpl entry : sourceListBall) {
            if (entry.getPrimaryKey().equals(foreignKeyBall)) {
                return entry.getIdentifier();
            }
        }
        return notApplicable;
    }

    private String getSuitCaseIdentifier(Integer foreignKeySuitCase) {
        for (SuitCaseCharacteristicsImpl entry : sourceListSuitCase) {
            if (entry.getPrimaryKey().equals(foreignKeySuitCase)) {
                return entry.getIdentifier();
            }
        }
        return notApplicable;
    }

    /**
     * Actual page - document title page - has to be in landscape orientation.
     *
     * @return
     */
    private Pair<Class<?>, Class<?>> writeBallData() throws IOException {
        int numberOfLinesExported = 0;
        Class<?> containingClass = null;
        Table tableBall = null;
        pdfAttributeUtility.createPage(pdfDocument,
                document,
                PageSize.A4.rotate(),
                new Paragraph(pageTitleBallCharacteristics + newLine)
                        .addStyle(styleHelveticaBold16));
        for (BallCharacteristicsImpl entry : sourceListBall) {
            if (containingClass == null) {
                containingClass = entry.getClass();
                tableBall = pdfAttributeUtility.addCellsWithoutBorderToTable(pdfAttributeUtility.createTable(3,
                        3,
                        1,
                        2,
                        2,
                        2,
                        3),
                        styleHelveticaBold12,
                        CommonPdfAttributeUtility.TableTextType.TITLE_TEXT,
                        tableTitleBcIdentifier,
                        tableTitleBcDescription,
                        tableTitleBcHardness,
                        tableTitleBcUpThrow,
                        tableTitleBcWeight,
                        tableTitleBcAngleFactor,
                        tableTitleBcComment);
            }
            pdfAttributeUtility.addCellsWithBorderToTable(tableBall,
                    styleHelvetica10,
                    CommonPdfAttributeUtility.TableTextType.NORMAL_TEXT,
                    entry.getIdentifier(),
                    entry.getDescription(),
                    entry.getHardness().getText(),
                    entry.getUpThrow().toString(),
                    entry.getWeight().toString(),
                    entry.getAngleFactor().toString(),
                    entry.getComment()
            );
            numberOfLinesExported += 1;
        }
        if (numberOfLinesExported == 0) {
            document.add(new Paragraph(noDataAvailable + newLine)
                    .addStyle(styleHelveticaItalic14));
        } else {
            document.add(tableBall);
        }
        logger.info("{} data lines exported of {}", numberOfLinesExported, containingClass);
        return Pair.of(sourceListBall.getClass(), containingClass);
    }

    private void writeContentOfCrazyGolfSiteData(List<HandicapCharacteristicsImpl> contents) {
        Table tableContentOfCrazyGolfSite = pdfAttributeUtility.addCellsWithBorderToTable(pdfAttributeUtility.createTable(1,
                2,
                2,
                2,
                2,
                2),
                styleHelveticaBold12,
                CommonPdfAttributeUtility.TableTextType.TITLE_TEXT,
                tableTitleHcPrimaryKey,
                tableTitleHcForeignKeyBall,
                tableTitleHcPositioning,
                tableTitleHcCushioning,
                tableTitleHcMarking,
                tableTitleHcRemark
        );
        for (HandicapCharacteristicsImpl entry : contents) {
            pdfAttributeUtility.addCellsWithBorderToTable(tableContentOfCrazyGolfSite,
                    styleHelvetica10,
                    CommonPdfAttributeUtility.TableTextType.NORMAL_TEXT,
                    entry.getPrimaryKey().toString(),
                    getBallIdentifier(entry.getForeignKeyBall()),
                    entry.getPositioning(),
                    entry.getCushioning(),
                    entry.getMarking(),
                    entry.getRemark());
        }
        document.add(tableContentOfCrazyGolfSite);
    }

    private void writeContentOfSuitCaseData(List<ContentOfSuitCaseImpl> contents) {
        Table tableContentOfSuitCase = pdfAttributeUtility.addCellsWithBorderToTable(pdfAttributeUtility.createTable(1,
                2,
                1,
                2,
                1,
                2),
                styleHelveticaBold12,
                CommonPdfAttributeUtility.TableTextType.TITLE_TEXT,
                tableTitleCoscPrimaryKey,
                tableTitleCoscForeignKeyBall,
                tableTitleCoscPrimaryKey,
                tableTitleCoscForeignKeyBall,
                tableTitleCoscPrimaryKey,
                tableTitleCoscForeignKeyBall
        );
        int numberOfTableDataGroups = tableContentOfSuitCase.getNumberOfColumns() / 2;
        int elementDisplacement = contents.size() % numberOfTableDataGroups == 0
                ? contents.size() / numberOfTableDataGroups
                : (contents.size() / numberOfTableDataGroups) + 1;
        for (int i = 0; i < elementDisplacement; i++) {
            String[] dataGroups = extractContentOfSuitCase(contents,
                    numberOfTableDataGroups,
                    i,
                    elementDisplacement);
            pdfAttributeUtility.addCellsWithBorderToTable(tableContentOfSuitCase,
                    styleHelvetica10,
                    CommonPdfAttributeUtility.TableTextType.NORMAL_TEXT,
                    dataGroups);
        }
        document.add(tableContentOfSuitCase);
    }

    /**
     * Actual page - document title page - has to be in landscape orientation.
     *
     * @return
     */
    private Pair<Class<?>, Class<?>> writeCrazyGolfSiteData() throws IOException {
        int numberOfLinesExported = 0;
        Class<?> containingClass = null;
        pdfAttributeUtility.createPage(pdfDocument,
                document,
                PageSize.A4.rotate(),
                new Paragraph(pageTitleCrazyGolfSitesCharacteristics + newLine)
                        .addStyle(styleHelveticaBold16));
        for (int i = 0, sourceListCrazyGolfSiteSize = sourceListCrazyGolfSite.size(); i < sourceListCrazyGolfSiteSize; i++) {
            CrazyGolfSiteCharacteristicsImpl entry = sourceListCrazyGolfSite.get(i);
            if (containingClass == null) {
                containingClass = entry.getClass();
            }
            if (i > 0) {
                pdfAttributeUtility.createPage(pdfDocument,
                        document,
                        null,
                        null);
            }
            writeSubTitle(String.format(subTitleCrazyGolfSitesCharacteristics, i + 1, entry.getSiteName()));
            Text titleSiteName = new Text(tableTitleCgscSiteName);
            Text titleAddress = new Text(tableTitleCgscAddress);
            Text titlePostCode = new Text(tableTitleCgscPostCode);
            Text titleTown = new Text(tableTitleCgscTown);
            Text titleSuitCase = new Text(tableTitleCgscSuitCase);
            Text titleContent = new Text(tableTitleCgscContents);
            addStyle(styleHelveticaBold12,
                    titleSiteName,
                    titleAddress,
                    titlePostCode,
                    titleTown,
                    titleSuitCase,
                    titleContent);
            Text dataSiteName = new Text(entry.getSiteName());
            Text dataAddress = new Text(entry.getAddress());
            Text dataPostCode = new Text(entry.getPostCode());
            Text dataTown = new Text(entry.getTown());
            Text dataSuitCase = new Text(getSuitCaseIdentifier(entry.getForeignKeySuitCase()));
            addStyle(styleHelvetica12,
                    dataSiteName,
                    dataAddress,
                    dataPostCode,
                    dataTown,
                    dataSuitCase);
            document.add(createParagraphWithTab(150, titleSiteName, dataSiteName));
            document.add(createParagraphWithTab(150, titleAddress, dataAddress));
            document.add(createParagraphWithTab(150, titlePostCode, dataPostCode));
            document.add(createParagraphWithTab(150, titleTown, dataTown));
            document.add(createParagraphWithTab(150, titleSuitCase, dataSuitCase));
            document.add(new Paragraph(titleContent));
            writeContentOfCrazyGolfSiteData(entry.getContents());
            numberOfLinesExported += 1;
        }
        if (numberOfLinesExported == 0) {
            document.add(new Paragraph(noDataAvailable + newLine)
                    .addStyle(styleHelveticaItalic14));

        }
        logger.info("{} data lines exported of {}", numberOfLinesExported, containingClass);
        return Pair.of(sourceListCrazyGolfSite.getClass(), containingClass);
    }

    /**
     * Actual page - document title page - has to be in portrait orientation.
     *
     * @return
     */
    private void writeDocumentTitlePage() {
        document.add(new Paragraph(newLine + newLine + newLine + newLine)
                .addStyle(styleHelveticaBold48)
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph(documentTitle + newLine)
                .addStyle(styleHelveticaBold48)
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph(documentDate + space + getActualDate() + newLine)
                .addStyle(styleHelvetica16)
                .setTextAlignment(TextAlignment.CENTER));
    }

    /**
     * Actual page - document title page - has to be in landscape orientation.
     *
     * @return
     */
    private Pair<Class<?>, Class<?>> writeSuitCaseData() throws IOException {
        int numberOfLinesExported = 0;
        Class<?> containingClass = null;
        pdfAttributeUtility.createPage(pdfDocument,
                document,
                PageSize.A4.rotate(),
                new Paragraph(pageTitleSuitCaseCharacteristics + newLine)
                        .addStyle(styleHelveticaBold16));
        for (int i = 0; i < sourceListSuitCase.size(); i++) {
            SuitCaseCharacteristicsImpl entry = sourceListSuitCase.get(i);
            if (containingClass == null) {
                containingClass = entry.getClass();
            }
            if (i > 0) {
                pdfAttributeUtility.createPage(pdfDocument,
                        document,
                        null,
                        null);
            }
            writeSubTitle(String.format(subTitleSuitCaseCharacteristics, i + 1, entry.getIdentifier()));
            Text titleIdentifier = new Text(tableTitleSccIdentifier);
            Text titleDescription = new Text(tableTitleSccDescription);
            Text titleOwner = new Text(tableTitleSccOwner);
            Text titleContent = new Text(dataTitleSccContents);
            addStyle(styleHelveticaBold12,
                    titleIdentifier,
                    titleDescription,
                    titleOwner,
                    titleContent);
            Text dataIdentifier = new Text(entry.getIdentifier());
            Text dataDescription = new Text(entry.getDescription());
            Text dataOwner = new Text(entry.getOwner());
            addStyle(styleHelvetica12,
                    dataIdentifier,
                    dataDescription,
                    dataOwner);
            document.add(createParagraphWithTab(150, titleIdentifier, dataIdentifier));
            document.add(createParagraphWithTab(150, titleDescription, dataDescription));
            document.add(createParagraphWithTab(150, titleOwner, dataOwner));
            document.add(new Paragraph(titleContent));
            writeContentOfSuitCaseData(entry.getContents());
            numberOfLinesExported += 1;
        }
        if (numberOfLinesExported == 0) {
            document.add(new Paragraph(noDataAvailable + newLine)
                    .addStyle(styleHelveticaItalic14));

        }
        logger.info("{} data lines exported of {}", numberOfLinesExported, containingClass);
        return Pair.of(sourceListSuitCase.getClass(), containingClass);
    }

    private void writeSubTitle(String pageTitle) {
        document.add(new Paragraph(pageTitle + newLine)
                .addStyle(styleHelveticaBoldItalic14));

    }

}
