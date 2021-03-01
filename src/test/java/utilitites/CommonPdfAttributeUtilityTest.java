package utilitites;

import abstracts.AbstractPlainJava;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfType1Font;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.Background;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.property.UnitValue;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * JUnit test for {@link CommonPdfAttributeUtility}.
 */
public class CommonPdfAttributeUtilityTest extends AbstractPlainJava {

    private final static int NUMBER_OF_METHODS = 12;

    private final static String DEFAULT_FONT = StandardFonts.COURIER;
    private final static Integer DEFAULT_FONT_SIZE = 12;
    private final static Color DEFAULT_FONT_COLOR = ColorConstants.BLACK;
    private final static Color DEFAULT_BACKGROUND_COLOR = ColorConstants.WHITE;
    private final static String DEFAULT_PDF_ENCODING = PdfEncodings.CP1252;
    private final static String TEST_FONT_01 = StandardFonts.HELVETICA_BOLD;
    private final static String TEST_FONT_02 = StandardFonts.COURIER_BOLDOBLIQUE;
    private final static String TEST_FONT_03 = StandardFonts.TIMES_BOLDITALIC;
    private final static String TEST_FONT_04 = StandardFonts.SYMBOL;
    private final static String TEST_FONT_05 = StandardFonts.HELVETICA_OBLIQUE;
    private final static String TEST_PDF_ENCODING_01 = PdfEncodings.CP1250;
    private final static String TEST_PDF_ENCODING_02 = PdfEncodings.CP1253;
    private final static String TEST_PDF_ENCODING_03 = PdfEncodings.CP1257;
    private final static String TEST_PDF_ENCODING_04 = PdfEncodings.UTF8;
    private final static Integer TEST_FONT_SIZE_01 = 8;
    private final static Integer TEST_FONT_SIZE_02 = 18;
    private final static Integer TEST_FONT_SIZE_03 = 11;
    private final static Color TEST_FONT_COLOR_01 = ColorConstants.RED;
    private final static Color TEST_FONT_COLOR_02 = ColorConstants.CYAN;
    private final static Color TEST_BACKGROUND_COLOR_01 = ColorConstants.GREEN;
    private final static Integer BACKGROUND_PROPERTY = 6;
    private final static Integer FONT_PROPERTY = 20;
    private final static Integer FONT_COLOR_PROPERTY = 21;
    private final static Integer FONT_SIZE_PROPERTY = 24;
    private final static String TEST_VALUE = "testValue";

    private final static PageSize PAGE_SIZE = PageSize.A10.rotate();
    private final static PageSize PAGE_SIZE_TWO = PageSize.A5.rotate();
    private final static Paragraph PAGE_TITLE = new Paragraph("page title");

    private final String testFileName = "PdfUtilityTest.pdf";
    private CommonPdfAttributeUtility utility;
    private File testFile;
    private PdfWriter writer;

    @Before
    public void setUp() throws Exception {
        testFile = new File(createTestPath(getClass().getSimpleName()) + "/" + testFileName);
        if (testFile.exists()) {
            if (!testFile.delete()) {
                fail("initialization of test failed");
            }
        }
        utility = new CommonPdfAttributeUtility();
        writer = new PdfWriter(testFile);
    }

    @After
    public void tearDown() throws Exception {
        writer.flush();
        writer.close();
    }

    @Test
    public void addCellsWithBorderToTable() throws IOException {
        // arrange
        Integer[] columnSizes = {1, 2, 3};
        String[] columnValues = {"text 1", "text 2", "text 3"};
        Table table = utility.createTable(columnSizes);
        Style style = new Style().setFont(PdfFontFactory.createFont(StandardFonts.COURIER));
        // act
        utility.addCellsWithBorderToTable(table,
                style,
                CommonPdfAttributeUtility.TableTextType.NORMAL_TEXT,
                columnValues
        );
        // assert
        for (int i = 0; i < columnSizes.length; i++) {
            Cell cell = table.getCell(0, i);
            Text result = (Text) ((Paragraph) cell.getChildren().get(0)).getChildren().get(0);
            assertEquals(String.format("invalid value in cell %d", i + 1),
                    columnValues[i],
                    result.getText());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void addCellsWithBorderToTableWithDifferentColumnsAndValues() throws IOException {
        // arrange
        Integer[] columnSizes = {1, 2, 3};
        String[] columnValues = {"text 1", "text 2"};
        Table table = utility.createTable(columnSizes);
        Style style = new Style().setFont(PdfFontFactory.createFont(StandardFonts.COURIER));
        // act and assert
        utility.addCellsWithBorderToTable(table,
                style,
                CommonPdfAttributeUtility.TableTextType.NORMAL_TEXT,
                columnValues
        );
    }

    @Test
    public void addCellsWithoutBorderToTable() throws IOException {
        // arrange
        Integer[] columnSizes = {1, 2, 3};
        String[] columnValues = {"text 1", "text 2", "text 3"};
        Table table = utility.createTable(columnSizes);
        Style style = new Style().setFont(PdfFontFactory.createFont(StandardFonts.COURIER));
        // act
        utility.addCellsWithoutBorderToTable(table,
                style,
                CommonPdfAttributeUtility.TableTextType.NORMAL_TEXT,
                columnValues
        );
        // assert
        for (int i = 0; i < columnSizes.length; i++) {
            Cell cell = table.getCell(0, i);
            Text result = (Text) ((Paragraph) cell.getChildren().get(0)).getChildren().get(0);
            assertEquals(String.format("invalid value in cell %d", i + 1),
                    columnValues[i],
                    result.getText());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void addCellsWithoutBorderToTableWithDifferentColumnsAndValues() throws IOException {
        // arrange
        Integer[] columnSizes = {1, 2, 3};
        String[] columnValues = {"text 1", "text 2", "text 3", "text 4"};
        Table table = utility.createTable(columnSizes);
        Style style = new Style().setFont(PdfFontFactory.createFont(StandardFonts.COURIER));
        // act and assert
        utility.addCellsWithoutBorderToTable(table,
                style,
                CommonPdfAttributeUtility.TableTextType.NORMAL_TEXT,
                columnValues
        );
    }

    @Test
    public void createPageFullyQualified() {
        // arrange
        Pair<PdfDocument, Document> results = utility.createPdfOutputFile(writer,
                PAGE_SIZE,
                utility.getPageNumberingHandler());
        PdfDocument pdfDocument = results.getLeft();
        Document document = results.getRight();
        int numberOfPages = document.getRenderer().getCurrentArea().getPageNumber();
        // act
        utility.createPage(pdfDocument,
                document,
                PAGE_SIZE_TWO,
                PAGE_TITLE);
        // assert
        assertEquals("invalid page size found",
                PAGE_SIZE_TWO,
                pdfDocument.getDefaultPageSize());
        assertEquals("invalid page number found",
                numberOfPages + 1,
                document.getRenderer().getCurrentArea().getPageNumber());
    }

    @Test
    public void createPageMinimalQualified() {
        // arrange
        Pair<PdfDocument, Document> results = utility.createPdfOutputFile(writer,
                PAGE_SIZE,
                utility.getPageNumberingHandler());
        PdfDocument pdfDocument = results.getLeft();
        Document document = results.getRight();
        int numberOfPages = document.getRenderer().getCurrentArea().getPageNumber();
        // act
        utility.createPage(pdfDocument,
                document,
                null,
                null);
        // assert
        assertEquals("invalid page size found",
                PAGE_SIZE,
                pdfDocument.getDefaultPageSize());
        assertEquals("invalid page number found",
                numberOfPages + 1,
                document.getRenderer().getCurrentArea().getPageNumber());
    }

    @Test
    public void createPageWithPageSize() {
        // arrange
        Pair<PdfDocument, Document> results = utility.createPdfOutputFile(writer,
                PAGE_SIZE,
                utility.getPageNumberingHandler());
        PdfDocument pdfDocument = results.getLeft();
        Document document = results.getRight();
        int numberOfPages = document.getRenderer().getCurrentArea().getPageNumber();
        // act
        utility.createPage(pdfDocument,
                document,
                PAGE_SIZE_TWO,
                null);
        // assert
        assertEquals("invalid page size found",
                PAGE_SIZE_TWO,
                pdfDocument.getDefaultPageSize());
        assertEquals("invalid page number found",
                numberOfPages + 1,
                document.getRenderer().getCurrentArea().getPageNumber());
    }

    @Test
    public void createPageWithPageTitle() {
        // arrange
        Pair<PdfDocument, Document> results = utility.createPdfOutputFile(writer,
                PAGE_SIZE,
                utility.getPageNumberingHandler());
        PdfDocument pdfDocument = results.getLeft();
        Document document = results.getRight();
        int numberOfPages = document.getRenderer().getCurrentArea().getPageNumber();
        // act
        utility.createPage(pdfDocument,
                document,
                null,
                PAGE_TITLE);
        // assert
        assertEquals("invalid page size found",
                PAGE_SIZE,
                pdfDocument.getDefaultPageSize());
        assertEquals("invalid page number found",
                numberOfPages + 1,
                document.getRenderer().getCurrentArea().getPageNumber());
    }

    @Test
    public void createPdfOutputFileFullyQualified() {
        // act
        Pair<PdfDocument, Document> results = utility.createPdfOutputFile(writer,
                PAGE_SIZE,
                utility.getPageNumberingHandler());
        PdfDocument pdfDocument = results.getLeft();
        Document document = results.getRight();
        // assert
        assertTrue("no event handler found",
                pdfDocument.hasEventHandler("StartPdfPage"));
        assertEquals("invalid page size found",
                PAGE_SIZE,
                pdfDocument.getDefaultPageSize());
    }

    @Test
    public void createPdfOutputFileMinimalQualified() {
        // act
        Pair<PdfDocument, Document> results = utility.createPdfOutputFile(writer,
                null);
        PdfDocument pdfDocument = results.getLeft();
        Document document = results.getRight();
        // assert
        assertFalse("unexpected event handler found",
                pdfDocument.hasEventHandler("StartPdfPage"));
        assertEquals("invalid page size found",
                PageSize.A4,
                pdfDocument.getDefaultPageSize());
    }

    @Test
    public void createPdfOutputFileWithHandler() {
        // act
        Pair<PdfDocument, Document> results = utility.createPdfOutputFile(writer,
                null,
                utility.getPageNumberingHandler());
        PdfDocument pdfDocument = results.getLeft();
        Document document = results.getRight();
        // assert
        assertTrue("no event handler found",
                pdfDocument.hasEventHandler("StartPdfPage"));
        assertEquals("invalid page size found",
                PageSize.A4,
                pdfDocument.getDefaultPageSize());
    }

    @Test
    public void createPdfOutputFileWithPageSize() {
        // act
        Pair<PdfDocument, Document> results = utility.createPdfOutputFile(writer,
                PAGE_SIZE);
        PdfDocument pdfDocument = results.getLeft();
        Document document = results.getRight();
        // assert
        assertFalse("unexpected event handler found",
                pdfDocument.hasEventHandler("StartPdfPage"));
        assertEquals("invalid page size found",
                PAGE_SIZE,
                pdfDocument.getDefaultPageSize());
    }

    @Test
    public void createStyleWithoutArguments() throws IOException {
        // act
        Style style = utility.createStyle();
        // assert
        assertWholeStyle(style,
                DEFAULT_FONT,
                DEFAULT_PDF_ENCODING,
                DEFAULT_FONT_SIZE,
                DEFAULT_FONT_COLOR,
                DEFAULT_BACKGROUND_COLOR);
    }

    @Test
    public void createStyleWithArgumentFont() throws IOException {
        // act
        Style style = utility.createStyle(TEST_FONT_01);
        // assert
        assertWholeStyle(style,
                TEST_FONT_01,
                DEFAULT_PDF_ENCODING,
                DEFAULT_FONT_SIZE,
                DEFAULT_FONT_COLOR,
                DEFAULT_BACKGROUND_COLOR);
    }

    @Test
    public void createStyleWithArguments_Font_Encoding() throws IOException {
        // act
        Style style = utility.createStyle(TEST_FONT_02,
                TEST_PDF_ENCODING_01);
        // assert
        assertWholeStyle(style,
                TEST_FONT_02,
                TEST_PDF_ENCODING_01,
                DEFAULT_FONT_SIZE,
                DEFAULT_FONT_COLOR,
                DEFAULT_BACKGROUND_COLOR);
    }

    @Test
    public void createStyleWithArguments_Font_Encoding_Size() throws IOException {
        // act
        Style style = utility.createStyle(TEST_FONT_03,
                TEST_PDF_ENCODING_02,
                TEST_FONT_SIZE_01);
        // assert
        assertWholeStyle(style,
                TEST_FONT_03,
                TEST_PDF_ENCODING_02,
                TEST_FONT_SIZE_01,
                DEFAULT_FONT_COLOR,
                DEFAULT_BACKGROUND_COLOR);
    }

    @Test
    public void createStyleWithArguments_Font_Encoding_Size_Foreground() throws IOException {
        // act
        Style style = utility.createStyle(TEST_FONT_04,
                TEST_PDF_ENCODING_03,
                TEST_FONT_SIZE_02,
                TEST_FONT_COLOR_01);
        // assert
        assertWholeStyle(style,
                TEST_FONT_04,
                TEST_PDF_ENCODING_03,
                TEST_FONT_SIZE_02,
                TEST_FONT_COLOR_01,
                DEFAULT_BACKGROUND_COLOR);
    }

    @Test
    public void createStyleWithArguments_Font_Encoding_Size_Foreground_Background() throws IOException {
        // act
        Style style = utility.createStyle(TEST_FONT_05,
                TEST_PDF_ENCODING_04,
                TEST_FONT_SIZE_03,
                TEST_FONT_COLOR_02,
                TEST_BACKGROUND_COLOR_01);
        // assert
        assertWholeStyle(style,
                TEST_FONT_05,
                TEST_PDF_ENCODING_04,
                TEST_FONT_SIZE_03,
                TEST_FONT_COLOR_02,
                TEST_BACKGROUND_COLOR_01);
    }

    @Test
    public void createTable() {
        // arrange
        Integer[] columnSizes = {1, 2, 3};
        // act
        Table result = utility.createTable(columnSizes);
        // assert
        assertEquals("invalid number of columns",
                columnSizes.length,
                result.getNumberOfColumns());
        for (int i = 0; i < columnSizes.length; i++) {
            Integer size = columnSizes[i];
            assertEquals("invalid width of column",
                    columnSizes[i],
                    Integer.valueOf((int) result.getColumnWidth(i).getValue()));
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void createTableWithNoColumn() {
        // act and assert
        Table result = utility.createTable();
    }

    @Test
    public void testNumberOfMethods() {
        // act and assert
        assertEquals("invalid number of methods",
                NUMBER_OF_METHODS,
                CommonPdfAttributeUtility.class.getDeclaredMethods().length);
    }

    private void assertWholeStyle(Style style,
                                  String expectedFont,
                                  String expectedPdfEncoding,
                                  Integer expectedFontSize,
                                  Color expectedFontColor,
                                  Color expectedBackgroundColor) {
        PdfType1Font font = style.getProperty(FONT_PROPERTY);
        assertEquals("invalid font",
                expectedFont,
                font.getFontProgram().getFontNames().getFontName());
        assertEquals("invalid encoding",
                expectedPdfEncoding,
                font.getFontEncoding().getBaseEncoding());
        UnitValue size = style.getProperty(FONT_SIZE_PROPERTY);
        assertEquals("invalid size",
                expectedFontSize,
                Integer.valueOf((int) size.getValue()));
        TransparentColor fontColor = style.getProperty(FONT_COLOR_PROPERTY);
        assertEquals("invalid font color",
                expectedFontColor,
                fontColor.getColor());
        Background background = style.getProperty(BACKGROUND_PROPERTY);
        assertEquals("invalid background color",
                expectedBackgroundColor,
                background.getColor());
    }

    private final static class IEventHandlerForTest implements IEventHandler {
        private final String testValue = TEST_VALUE;

        @Override
        public void handleEvent(Event event) {
        }

        public String getTestValue() {
            return testValue;
        }
    }

}