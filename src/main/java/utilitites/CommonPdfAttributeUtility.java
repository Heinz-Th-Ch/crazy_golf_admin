package utilitites;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommonPdfAttributeUtility {

    private final static String DEFAULT_FONT = StandardFonts.COURIER;
    private final static Integer DEFAULT_FONT_SIZE = 12;
    private final static Color DEFAULT_FONT_COLOR = ColorConstants.BLACK;
    private final static Color DEFAULT_BACKGROUND_COLOR = ColorConstants.WHITE;

    public Table addCellsWithBorderToTable(Table table,
                                           Style style,
                                           TableTextType textType,
                                           String... texts) {
        if (table.getNumberOfColumns() != texts.length) {
            throw new IllegalArgumentException(String.format("invalid number of table titles. Number of columns: %d, number of titles: %d",
                    table.getNumberOfColumns(),
                    texts.length));
        }
        for (String text : texts) {
            Cell cell = new Cell();
            cell.add(new Paragraph(text).addStyle(style));
            if (textType == TableTextType.TITLE_TEXT) {
                table.addHeaderCell(cell);
            } else {
                table.addCell(cell);
            }
        }
        return table;
    }

    public Table addCellsWithoutBorderToTable(Table table,
                                              Style style,
                                              TableTextType textType,
                                              String... texts) {
        if (table.getNumberOfColumns() != texts.length) {
            throw new IllegalArgumentException(String.format("invalid number of table titles. Number of columns: %d, number of titles: %d",
                    table.getNumberOfColumns(),
                    texts.length));
        }
        for (String text : texts) {
            Cell cell = new Cell();
            cell.setBorder(null);
            cell.add(new Paragraph(text).addStyle(style));
            if (textType == TableTextType.TITLE_TEXT) {
                table.addHeaderCell(cell);
            } else {
                table.addCell(cell);
            }
        }
        return table;
    }

    /**
     * Creates a new page.<br>
     * When ever {@link PageSize} is null, the former defined {@link PageSize} is used again otherwise the new
     * {@link PageSize} will be set.
     *
     * @param pdfDocument
     * @param document
     * @param pageSize
     * @param pageTitle
     */
    public void createPage(PdfDocument pdfDocument,
                           Document document,
                           @Nullable PageSize pageSize,
                           @Nullable Paragraph pageTitle) {
        if (pageSize != null) {
            pdfDocument.setDefaultPageSize(pageSize);
        }
        document.add(new AreaBreak());
        if (pageTitle != null) {
            document.add(pageTitle);
        }
    }

    /**
     * Creates a new pdf output file with predefined {@link PdfWriter}, {@link PageSize} and {@link IEventHandler}.<br>
     * {@link PageSize} is {@code @Nullable} and {@link IEventHandler} is an array, the corresponding rules are:
     * <ol>
     * <li>when ever {@link PageSize} has a null value then the method uses an application relevant default page
     * size of {@link PageSize#A4}.</li>
     * <li>depending on the length of the array of {@link IEventHandler}, there will all available {@link IEventHandler}
     * added to the {@link PdfDocument}.<br>
     * <b>ATTENTION</b>: The sequence of the handlers is very grave.</li>
     * </ol>
     *
     * @param pdfWriter
     * @param pageSize
     * @param eventHandler
     * @return
     */
    public Pair<PdfDocument, Document> createPdfOutputFile(PdfWriter pdfWriter,
                                                           @Nullable PageSize pageSize,
                                                           IEventHandler... eventHandler) {
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        for (IEventHandler newIEventHandler : eventHandler) {
            pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, newIEventHandler);
        }
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(pageSize == null ? PageSize.A4 : pageSize);
        return Pair.of(pdfDocument, document);
    }

    /**
     * Defines the style for {@link StandardFonts}.<br>
     * The default font which is used now is {@link StandardFonts#COURIER}.<br>
     * Is calling next method {@link #createStyle(String)}.
     *
     * @return
     * @throws IOException
     */
    public Style createStyle() throws IOException {
        return createStyle(DEFAULT_FONT);
    }

    /**
     * Defines the style for {@link StandardFonts}.<br>
     * The default font size which is used now is {@link #DEFAULT_FONT_SIZE}.<br>
     * Is calling next method {@link #createStyle(String, Integer)}.
     *
     * @param fontName
     * @return
     * @throws IOException
     */
    public Style createStyle(String fontName) throws IOException {
        return createStyle(fontName, null);
    }

    /**
     * Defines the style for {@link StandardFonts}.<br>
     * The default font color which is used now is {@link #DEFAULT_FONT_SIZE}.<br>
     * Is calling next method {@link #createStyle(String, Integer, Color)}.
     *
     * @param fontName
     * @param fontSize
     * @return
     * @throws IOException
     */
    public Style createStyle(String fontName,
                             @Nullable Integer fontSize) throws IOException {
        return createStyle(fontName, fontSize, null);
    }

    /**
     * Defines the style for {@link StandardFonts}.<br>
     * The default background color which is used now is {@link #DEFAULT_BACKGROUND_COLOR}.<br>
     * Is calling next method {@link #createStyle(String, Integer, Color, Color)}.
     *
     * @param fontName
     * @param fontSize
     * @param fontColor
     * @return
     * @throws IOException
     */
    public Style createStyle(String fontName,
                             @Nullable Integer fontSize,
                             @Nullable Color fontColor) throws IOException {
        return createStyle(fontName, fontSize, fontColor, null);
    }

    /**
     * Defines the style for {@link StandardFonts}.<br>
     * May be the last, highest defined, method.
     *
     * @param fontName
     * @param fontSize
     * @param fontColor
     * @param backgroundColor
     * @return
     * @throws IOException
     */
    public Style createStyle(String fontName,
                             @Nullable Integer fontSize,
                             @Nullable Color fontColor,
                             @Nullable Color backgroundColor) throws IOException {
        Style style = new Style();
        PdfFont font = PdfFontFactory.createFont(fontName);
        Integer localFontSize = DEFAULT_FONT_SIZE;
        if (fontSize != null) {
            localFontSize = fontSize;
        }
        Color localFontColor = DEFAULT_FONT_COLOR;
        if (fontColor != null) {
            localFontColor = fontColor;
        }
        Color localBackgroundColor = DEFAULT_BACKGROUND_COLOR;
        if (backgroundColor != null) {
            localBackgroundColor = backgroundColor;
        }
        style.setFont(font)
                .setFontSize(localFontSize)
                .setFontColor(localFontColor)
                .setBackgroundColor(localBackgroundColor);
        return style;
    }

    public Table createTable(Integer... relativeColumnWidth) {
        float[] columnWidths = new float[relativeColumnWidth.length];
        for (int i = 0; i < relativeColumnWidth.length; i++) {
            columnWidths[i] = relativeColumnWidth[i];
        }
        Table table = new Table(columnWidths);
        table.setWidth(UnitValue.createPercentValue(100));
        return table;
    }

    /**
     * Returns a new instance of {@link PageNumberingHandler}.
     *
     * @return
     */
    public final PageNumberingHandler getPageNumberingHandler() {
        return new PageNumberingHandler();
    }

    public enum TableTextType {
        NORMAL_TEXT,
        TITLE_TEXT
    }

    public class PageNumberingHandler implements IEventHandler {
        protected Integer skipNumberOfPages = 1;
        protected Integer numberOfPage = 0;

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            numberOfPage = docEvent.getDocument().getPageNumber(page);
            if (numberOfPage.equals(skipNumberOfPages)) {
                return;
            }
            Rectangle pageSize = page.getPageSize();
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.beginText();
            try {
                canvas.setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 12);
            } catch (IOException e) {
                e.printStackTrace();
            }
            canvas.moveText(pageSize.getWidth() - 100, 10);
            canvas.showText(String.format("Seite %d", numberOfPage - skipNumberOfPages));
            canvas.endText();
            canvas.release();
        }

        public Integer getNumberOfPage() {
            return numberOfPage;
        }
    }

}
