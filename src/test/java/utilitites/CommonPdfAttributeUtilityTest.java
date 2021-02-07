package utilitites;

import abstracts.AbstractPlainJava;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfType1Font;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.Background;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.property.UnitValue;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * JUnit test for {@link CommonPdfAttributeUtility}.
 */
public class CommonPdfAttributeUtilityTest extends AbstractPlainJava {

    private final static int NUMBER_OF_METHODS = 10;

    private final static String DEFAULT_FONT = StandardFonts.COURIER;
    private final static Integer DEFAULT_FONT_SIZE = 12;
    private final static Color DEFAULT_FONT_COLOR = ColorConstants.BLACK;
    private final static Color DEFAULT_BACKGROUND_COLOR = ColorConstants.WHITE;
    private final static String TEST_FONT_01 = StandardFonts.HELVETICA_BOLD;
    private final static String TEST_FONT_02 = StandardFonts.COURIER_BOLDOBLIQUE;
    private final static String TEST_FONT_03 = StandardFonts.TIMES_BOLDITALIC;
    private final static String TEST_FONT_04 = StandardFonts.SYMBOL;
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

    private CommonPdfAttributeUtility utility;

    @Before
    public void setUp() throws Exception {
        utility = new CommonPdfAttributeUtility();
    }

    @Test
    public void addTableCellsWithBorder() throws IOException {
        // arrange
        Integer[] columnSizes = {1, 2, 3};
        String[] columnValues = {"text 1", "text 2", "text 3"};
        Table table = utility.createTable(columnSizes);
        Style style = new Style().setFont(PdfFontFactory.createFont(StandardFonts.COURIER));
        // act
        utility.addTableCellsWithBorder(table,
                style,
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
    public void addTableCellsWithBorderWithDifferentColumnsAndValues() throws IOException {
        // arrange
        Integer[] columnSizes = {1, 2, 3};
        String[] columnValues = {"text 1", "text 2"};
        Table table = utility.createTable(columnSizes);
        Style style = new Style().setFont(PdfFontFactory.createFont(StandardFonts.COURIER));
        // act and assert
        utility.addTableCellsWithBorder(table,
                style,
                columnValues
        );
    }

    @Test
    public void addTableCellsWithoutBorder() throws IOException {
        // arrange
        Integer[] columnSizes = {1, 2, 3};
        String[] columnValues = {"text 1", "text 2", "text 3"};
        Table table = utility.createTable(columnSizes);
        Style style = new Style().setFont(PdfFontFactory.createFont(StandardFonts.COURIER));
        // act
        utility.addTableCellsWithoutBorder(table,
                style,
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
    public void addTableCellsWithoutBorderWithDifferentColumnsAndValues() throws IOException {
        // arrange
        Integer[] columnSizes = {1, 2, 3};
        String[] columnValues = {"text 1", "text 2", "text 3", "text 4"};
        Table table = utility.createTable(columnSizes);
        Style style = new Style().setFont(PdfFontFactory.createFont(StandardFonts.COURIER));
        // act and assert
        utility.addTableCellsWithoutBorder(table,
                style,
                columnValues
        );
    }

    @Test
    public void createPageLandscape() {
        // act
        AreaBreak result = utility.createPageLandscape();
        // assert
        assertEquals("invalid page size",
                PageSize.A4.rotate().toString(),
                result.getPageSize().toString());
    }

    @Test
    public void createPagePortrait() {
        // act
        AreaBreak result = utility.createPagePortrait();
        // assert
        assertEquals("invalid page size",
                PageSize.A4.toString(),
                result.getPageSize().toString());
    }

    @Test
    public void createStyleWithoutArgument() throws IOException {
        // act
        Style style = utility.createStyle();
        // assert
        assertWholeStyle(style,
                DEFAULT_FONT,
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
                DEFAULT_FONT_SIZE,
                DEFAULT_FONT_COLOR,
                DEFAULT_BACKGROUND_COLOR);
    }

    @Test
    public void createStyleWithArgumentsFontSize() throws IOException {
        // act
        Style style = utility.createStyle(TEST_FONT_02,
                TEST_FONT_SIZE_01);
        // assert
        assertWholeStyle(style,
                TEST_FONT_02,
                TEST_FONT_SIZE_01,
                DEFAULT_FONT_COLOR,
                DEFAULT_BACKGROUND_COLOR);
    }

    @Test
    public void createStyleWithArgumentsFontSizeForeground() throws IOException {
        // act
        Style style = utility.createStyle(TEST_FONT_03,
                TEST_FONT_SIZE_02,
                TEST_FONT_COLOR_01);
        // assert
        assertWholeStyle(style,
                TEST_FONT_03,
                TEST_FONT_SIZE_02,
                TEST_FONT_COLOR_01,
                DEFAULT_BACKGROUND_COLOR);
    }

    @Test
    public void createStyleWithArgumentsFontSizeForegroundBackground() throws IOException {
        // act
        Style style = utility.createStyle(TEST_FONT_04,
                TEST_FONT_SIZE_03,
                TEST_FONT_COLOR_02,
                TEST_BACKGROUND_COLOR_01);
        // assert
        assertWholeStyle(style,
                TEST_FONT_04,
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
                                  Integer expectedFontSize,
                                  Color expectedFontColor,
                                  Color expectedBackgroundColor) {
        PdfType1Font font = style.getProperty(FONT_PROPERTY);
        assertEquals("invalid font", expectedFont, font.getFontProgram().getFontNames().getFontName());
        UnitValue size = style.getProperty(FONT_SIZE_PROPERTY);
        assertEquals("invalid size", expectedFontSize, Integer.valueOf((int) size.getValue()));
        TransparentColor fontColor = style.getProperty(FONT_COLOR_PROPERTY);
        assertEquals("invalid font color", expectedFontColor, fontColor.getColor());
        Background background = style.getProperty(BACKGROUND_PROPERTY);
        assertEquals("invalid background color", expectedBackgroundColor, background.getColor());
    }

}