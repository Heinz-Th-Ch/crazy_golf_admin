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
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class CommonPdfAttributeUtility {

    private final static String DEFAULT_FONT = StandardFonts.COURIER;
    private final static Integer DEFAULT_FONT_SIZE = 12;
    private final static Color DEFAULT_FONT_COLOR = ColorConstants.BLACK;
    private final static Color DEFAULT_BACKGROUND_COLOR = ColorConstants.WHITE;

    /**
     * Defines a new page in A4 landscape format, in german A4 QUER.
     *
     * @return
     */
    public AreaBreak createPageLandscape() {
        AreaBreak areaBreak = new AreaBreak();
        areaBreak.setPageSize(PageSize.A4.rotate());
        return areaBreak;
    }

    /**
     * Defines a new page in A4 portrait format, in german A4.
     *
     * @return
     */
    public AreaBreak createPagePortrait() {
        AreaBreak areaBreak = new AreaBreak();
        areaBreak.setPageSize(PageSize.A4);
        return areaBreak;
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

    public Table addTableCellsWithBorder(Table table,
                                         Style style,
                                         String... texts) {
        if (table.getNumberOfColumns() != texts.length) {
            throw new IllegalArgumentException(String.format("invalid number of table titles. Number of columns: %d, number of titles: %d",
                    table.getNumberOfColumns(),
                    texts.length));
        }
        for (String text : texts) {
            Cell cell = new Cell();
            cell.add(new Paragraph(text).addStyle(style));
            table.addCell(cell);
        }
        return table;
    }

    public Table addTableCellsWithoutBorder(Table table,
                                            Style style,
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
            table.addCell(cell);
        }
        return table;
    }

    public static class PageNumberingHandler implements IEventHandler {
        protected Integer skipNumberOfPages = 1;

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            int pageNum = docEvent.getDocument().getPageNumber(page);
            if (pageNum == skipNumberOfPages) {
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
            canvas.showText(String.format("Seite %d", pageNum - skipNumberOfPages));
            canvas.endText();
            canvas.release();
        }

    }

}
