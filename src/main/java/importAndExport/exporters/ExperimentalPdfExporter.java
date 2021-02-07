package importAndExport.exporters;

import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.OutsetBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

/**
 * Is an experimental class, in which i will do some tries for pdf creation.
 * This class needs no tests, because it is elaboration class.
 */
public class ExperimentalPdfExporter {

    private final static String targetPath = "testData/ExperimentalPdfExporter/";

    public static void main(String... args) throws IOException {
        if (!(new File(targetPath)).exists()) {
            (new File(targetPath)).mkdirs();
        }
        createDocumentWhichIsEmpty();
        createDocumentWithAddedAreaBreak();
        createDocumentWithAddedParagraph();
        createDocumentWithAddedParagraphsInsideACell();
        createDocumentWithAddedList();
        createDocumentWithAddedTable();
        createDocumentWithAddedTableWithFormattedCells();
        createDocumentWithAddedTableWithAnImageInACell();
        createDocumentWithAddedTableWithAnAdditionalTableInACell();
        createDocumentWithAddedTableWithAListInACell();
        createDocumentWithAnImageOnFourPages();
        createDocumentWithTextAnnotations();
        createDocumentWithCanvas();
        createDocumentWithMiscellaneous();
        createDocumentWithSeveralOrientations();
    }

    private static void createDocumentWithAddedAreaBreak() throws FileNotFoundException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithAddedAreaBreak.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);
        // Creating an area break
        AreaBreak areaBreak = new AreaBreak();
        // Adding the area break to the pdf
        document.add(areaBreak);
        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with area break created");
    }

    private static void createDocumentWithAddedList() throws FileNotFoundException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithAddedList.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);
        // Creating a list
        List list = new List();
        // Add entries to the list
        for (int i = 0; i < 10; i++) {
            list.add("entry number " + (i + 1));
        }
        // Adding the area break to the pdf
        document.add(list);
        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with list created");
    }

    private static void createDocumentWithAddedParagraph() throws FileNotFoundException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithAddedParagraph.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);
        // Creating a paragraph
        Paragraph paragraph1 = new Paragraph("Welcome to the experimental class");
        Paragraph paragraph2 = new Paragraph("... and the next one");
        // Adding the area break to the pdf
        document.add(paragraph1);
        document.add(paragraph2);
        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with paragraph created");
    }

    private static void createDocumentWithAddedParagraphsInsideACell() throws FileNotFoundException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithAddedParagraphsInsideACell.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);
        // Creating a paragraph inside a cell
        Cell cell1 = new Cell();
        cell1.add(new Paragraph("Welcome to the experimental class 1"));
        cell1.setWidth(100);
        Cell cell2 = new Cell();
        cell2.add(new Paragraph("Welcome to the experimental class 2"));
        cell2.setWidth(100);
        // Adding the area break to the pdf
        document.add(cell1);
        document.add(cell2);
        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with paragraphs inside a cell created");
    }

    private static void createDocumentWithAddedTable() throws FileNotFoundException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithAddedTable.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);
        // Creating a table with three columns
        float[] columnWidths = {150f, 150f, 150f};
        Table table = new Table(columnWidths);
        // Add cells to the table
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                table.addCell("column " + (j + 1) + ", line " + (i + 1));
            }
        }
        // Adding the table to the pdf
        document.add(table);
        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with table created");
    }

    private static void createDocumentWithAddedTableWithAListInACell() throws FileNotFoundException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithAddedTableAListInACell.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);
        // Creating a table with three columns
        float[] columnWidths = {150f, 150f, 150f};
        Table table = new Table(columnWidths);
        // Add cells to the table
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                Cell cell = new Cell();
                if (i == 4 && j == 1) {
                    cell.add(makeAList());
                } else {
                    cell.add(new Paragraph("column " + (j + 1) + ", line " + (i + 1)));
                }
                table.addCell(cell);
            }
        }
        // Adding the table to the pdf
        document.add(table);
        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with table with a list in a cell created");
    }

    private static void createDocumentWithAddedTableWithAnAdditionalTableInACell() throws FileNotFoundException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithAddedTableAnAdditionalTableInACell.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);
        // Creating a table with three columns
        float[] columnWidths = {150f, 150f, 150f};
        Table table = new Table(columnWidths);
        // Add cells to the table
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                Cell cell = new Cell();
                if (i == 4 && j == 1) {
                    cell.add(makeAnAdditionalTable());
                } else {
                    cell.add(new Paragraph("column " + (j + 1) + ", line " + (i + 1)));
                }
                table.addCell(cell);
            }
        }
        // Adding the table to the pdf
        document.add(table);
        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with table with an additional table in a cell created");
    }

    private static void createDocumentWithAddedTableWithAnImageInACell() throws FileNotFoundException,
            MalformedURLException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithAddedTableAnImageInACell.pdf";
        String imageFile = targetPath + "Hilfsobjekte/OneImage.jpg";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);
        // Creating a table with three columns
        float[] columnWidths = {150f, 150f, 150f};
        Table table = new Table(columnWidths);
        // Add cells to the table
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                Cell cell = new Cell();
                if (i == 4 && j == 1) {
                    // Creating a ImageData object
                    ImageData imageData = ImageDataFactory.create(imageFile);
                    // Creating the image
                    Image image = new Image(imageData);
                    cell.add(image.setAutoScale(true));
                } else {
                    cell.add(new Paragraph("column " + (j + 1) + ", line " + (i + 1)));
                }
                table.addCell(cell);
            }
        }
        // Adding the table to the pdf
        document.add(table);
        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with table with an image in a cell created");
    }

    private static void createDocumentWithAddedTableWithFormattedCells() throws FileNotFoundException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithAddedTableWithFormattedCells.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);
        // Creating a table with three columns
        float[] columnWidths = {150f, 150f, 150f};
        Table table = new Table(columnWidths);
        // Add cells to the table
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                Cell cell = new Cell().add(new Paragraph("column " + (j + 1) + ", line " + (i + 1)));
                switch (j) {
                    case 0:
                        cell.setBackgroundColor(ColorConstants.CYAN);
                        cell.setBorder(new DashedBorder(ColorConstants.GREEN, 2));
                        cell.setTextAlignment(TextAlignment.LEFT);
                        break;
                    case 2:
                        cell.setFontColor(ColorConstants.RED);
                        cell.setTextAlignment(TextAlignment.CENTER);
                        cell.setBorder(new SolidBorder(ColorConstants.MAGENTA, 1));
                        break;
                    default:
                        cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                        cell.setFontColor(ColorConstants.DARK_GRAY);
                        cell.setTextAlignment(TextAlignment.RIGHT);
                        cell.setBorder(new OutsetBorder(DeviceGray.GRAY, 3));
                        break;
                }
                table.addCell(cell);
            }
        }
        // Adding the table to the pdf
        document.add(table);
        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with table with formatted cells created");
    }

    private static void createDocumentWithAnImageOnFourPages() throws FileNotFoundException,
            MalformedURLException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithAnImageOnFourPages.pdf";
        String imageFile = targetPath + "Hilfsobjekte/OneImage.jpg";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);
        // Creating a ImageData object
        ImageData imageData = ImageDataFactory.create(imageFile);
        // Creating the image
        Image image = new Image(imageData);
        // Creating an area break
        AreaBreak areaBreak = new AreaBreak();
        // Adding the image to the pdf (1/4)
        document.add(image);
        // Adding the area break to the pdf (1/3)
        document.add(areaBreak);
        // Setting the position of the image to the center of the page
        image.setFixedPosition(100, 250);
        // Adding the image to the pdf (2/4)
        document.add(image);
        // Adding the area break to the pdf (2/3)
        document.add(areaBreak);
        // Setting the scaling of the image
        image.setAutoScale(true);
        // Adding the image to the pdf (3/4)
        document.add(image);
        // Adding the area break to the pdf (3/3)
        document.add(areaBreak);
        // Rotate the image
        image.setRotationAngle(30);
        // Adding the image to the pdf (4/4)
        document.add(image);
        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with an image on four pages created");
    }

    private static void createDocumentWithCanvas() throws FileNotFoundException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithCanvas.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);

        // Creating a new page
        PdfPage pdfPage = pdfDocument.addNewPage();

        // Creating a PdfCanvas object - ellipsis
        PdfCanvas canvas1 = new PdfCanvas(pdfPage);
        // Drawing an arc
        canvas1.arc(50, 50, 300, 545, 0, 270);
        // Filling the arc
        canvas1.fill();

        // Creating a PdfCanvas object  - line
        PdfCanvas canvas2 = new PdfCanvas(pdfPage);
        // Initial point of the line
        canvas2.moveTo(100, 700);
        // Drawing the line
        canvas2.lineTo(500, 300);
        // Closing the path stroke
        canvas2.closePathStroke();

        // Creating a PdfCanvas object - circle
        PdfCanvas canvas = new PdfCanvas(pdfPage);

        // Setting color to the circle
        canvas.setColor(ColorConstants.GREEN, true);
        // creating a circle
        canvas.circle(500, 500, 50);
        // Filling the circle
        canvas.fill();

        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with canvas created");
    }

    private static void createDocumentWithMiscellaneous() throws IOException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithMiscellaneous.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);

        // Evaluate all registered fonts
        Set<String> registeredFonts = FontProgramFactory.getRegisteredFonts();
        List fontList = new List();
        for (String font : registeredFonts) {
            fontList.add(font);
        }
        // Adding the area break to the pdf
        document.add(fontList);

        // Adding the area break to the pdf
        document.add(new AreaBreak());

        // Define font of the text
        PdfFont font0 = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        // Creating text object 1
        Text text0 = new Text(font0.getFontProgram() + "\n");
        // Setting font of the text
        text0.setFont(font0);
        // Setting font color
        text0.setFontColor(ColorConstants.BLACK);
        // Define font of the text
        PdfFont font1 = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);
        // Creating text object 2
        Text text1 = new Text(font1.getFontProgram() + "\n");
        // Setting font of the text
        text1.setFont(font1);
        // Setting font color
        text1.setFontColor(ColorConstants.BLACK);
        // Define font of the text
        PdfFont font2 = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        // Creating text object 3
        Text text2 = new Text(font2.getFontProgram() + "\n");
        // Setting font of the text
        text2.setFont(font2);
        // Setting font color
        text2.setFontColor(ColorConstants.BLACK);
        // Define font of the text
        PdfFont font3 = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLDOBLIQUE);
        // Creating text object 4
        Text text3 = new Text(font3.getFontProgram() + " Das ist ein relativ langer Text, der einen automatischen Zeilenumbruch forcieren sollte. Mal sehen wie das dann in Natura aussieht.");
        // Setting font of the text
        text3.setFont(font3);
        // Setting font color
        text3.setFontColor(ColorConstants.BLACK);
        // Creating Paragraph
        Paragraph paragraph1 = new Paragraph();
        // Adding text1 to the paragraph
        paragraph1.add(text0);
        paragraph1.add(text1);
        paragraph1.add(text2);
        paragraph1.add(text3);
        // Adding paragraphs to the document
        document.add(paragraph1);

        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with miscellaneous created");
    }

    private static void createDocumentWhichIsEmpty() throws FileNotFoundException {
        // Define the name of the empty pdf file
        String targetFile = targetPath + "DocumentIsEmpty.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Adding a empty page
        pdfDocument.addNewPage();
        // Creating a document
        Document document = new Document(pdfDocument);
        // Closing the document
        document.close();
        // End message to log
        System.out.println("empty pdf document created");
    }

    private static void createDocumentWithSeveralOrientations() throws FileNotFoundException {
        final PdfNumber PORTRAIT = new PdfNumber(0);
        final PdfNumber LANDSCAPE = new PdfNumber(90);
        final PdfNumber INVERTED_PORTRAIT = new PdfNumber(180);
        final PdfNumber SEASCAPE = new PdfNumber(270);

        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithSeveralOrientations.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // The default page orientation is set to portrait in the custom event handler.
        PageOrientationsEventHandler eventHandler = new PageOrientationsEventHandler();
        pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, eventHandler);
        // Creating a document
        Document document = new Document(pdfDocument);
        // Set text on first page - portrait is default
        document.add(new Paragraph("A simple page in portrait orientation"));
        // Set text next page - landscape
        eventHandler.setOrientation(LANDSCAPE);
        document.add(new AreaBreak());
        document.add(new Paragraph("A simple page in landscape orientation"));
        // Set text next page - inverted portrait
        eventHandler.setOrientation(INVERTED_PORTRAIT);
        document.add(new AreaBreak());
        document.add(new Paragraph("A simple page in inverted portrait orientation"));
        // Set text next page - seascape
        eventHandler.setOrientation(SEASCAPE);
        document.add(new AreaBreak());
        document.add(new Paragraph("A simple page in seascape orientation"));
        // Set text next page - back to portrait
        eventHandler.setOrientation(PORTRAIT);
        document.add(new AreaBreak());
        document.add(new Paragraph("A simple page in portrait orientation again"));
        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with several orientations created");
    }

    private static void createDocumentWithTextAnnotations() throws FileNotFoundException {
        // Define the name of the pdf file
        String targetFile = targetPath + "DocumentWithTextAnnotations.pdf";
        // Creating a pdf writer
        PdfWriter writer = new PdfWriter(targetFile);
        // Creating a pdf document
        PdfDocument pdfDocument = new PdfDocument(writer);
        // Creating a document
        Document document = new Document(pdfDocument);

        // Creating PdfTextAnnotation object
        Rectangle rect1 = new Rectangle(20, 800, 0, 0);
        PdfAnnotation pdfTextAnnotation = new PdfTextAnnotation(rect1);
        // Setting color to the annotation - has in this case no effect
        pdfTextAnnotation.setColor(ColorConstants.CYAN);
        // Setting title to the annotation
        pdfTextAnnotation.setTitle(new PdfString("Hallole"));
        // Setting contents of the annotation
        pdfTextAnnotation.setContents("Grueezi bim Versuech vo Annotatione.");
        // Creating a new page
        PdfPage page1 = pdfDocument.addNewPage();
        // Adding annotation to a page in a PDF
        page1.addAnnotation(pdfTextAnnotation);

        // Adding the area break to the pdf
        document.add(new AreaBreak());
        // Creating a PdfLinkAnnotation object
        Rectangle rect2 = new Rectangle(0, 0);
        PdfLinkAnnotation pdfLinkAnnotation = new PdfLinkAnnotation(rect2);
        // Setting action of the annotation
        PdfAction action = PdfAction.createURI("https://thayngen.ch/");
        pdfLinkAnnotation.setAction(action);
        // Creating a link
        Link link = new Link("Click here", pdfLinkAnnotation);
        // Creating a paragraph
        Paragraph paragraph = new Paragraph("Willkomme bim Link uf Thaynge -> ");
        // Define colors
        paragraph.setFontColor(ColorConstants.ORANGE);
        paragraph.setBackgroundColor(ColorConstants.BLUE);
        // Adding link to paragraph
        paragraph.add(link.setUnderline());
        // Adding paragraph to document
        document.add(paragraph);

        // Creating a PdfTextMarkupAnnotation object
        Rectangle rect3 = new Rectangle(105, 790, 64, 10);
        float[] floatArray = new float[]{169, 790, 105, 790, 169, 800, 105, 800};
        PdfAnnotation textMarkupAnnotation = PdfTextMarkupAnnotation.createHighLight(rect3, floatArray);
        // Setting color to the annotation
        textMarkupAnnotation.setColor(ColorConstants.RED);
        // Setting title to the annotation
        textMarkupAnnotation.setTitle(new PdfString("Hello!"));
        // Setting contents to the annotation
        textMarkupAnnotation.setContents(new PdfString("Hi welcome to Tutorialspoint"));
        // Creating a new Pdfpage
        PdfPage pdfPage2 = pdfDocument.addNewPage();
        // Adding annotation to a page in a PDF
        pdfPage2.addAnnotation(textMarkupAnnotation);

        // Creating a PdfCircleAnnotation objects
        Rectangle rect4 = new Rectangle(150, 770, 50, 50);
        PdfAnnotation pdfCircleAnnotation1 = new PdfCircleAnnotation(rect4);
        Rectangle rect5 = new Rectangle(400, 500, 101, 75);
        PdfAnnotation pdfCircleAnnotation2 = new PdfCircleAnnotation(rect5);
        // Setting color to the annotations
        pdfCircleAnnotation1.setColor(ColorConstants.GREEN);
        pdfCircleAnnotation2.setColor(ColorConstants.ORANGE);
        // Setting title to the annotation
        pdfCircleAnnotation1.setTitle(new PdfString("circle annotation"));
        pdfCircleAnnotation2.setTitle(new PdfString("circle annotation2"));
        // Setting contents of the annotation
        pdfCircleAnnotation1.setContents(new PdfString("Hi welcome to Tutorialspoint"));
        pdfCircleAnnotation2.setContents(new PdfString("Hi welcome to Tutorialspoint2"));
        // Creating a new page
        PdfPage page3 = pdfDocument.addNewPage();
        // Adding annotation to a page in a PDF
        page3.addAnnotation(pdfCircleAnnotation1);
        page3.addAnnotation(pdfCircleAnnotation2);

        // Closing the document
        document.close();
        // End message to log
        System.out.println("pdf document with text annotations created");

    }

    private static List makeAList() {
        List list = new List();
        list.add(new ListItem("entry 1"));
        list.add(new ListItem("entry 2"));
        list.add(new ListItem("entry 3"));
        list.add(new ListItem("entry 4"));
        list.add(new ListItem("entry 5"));
        list.add(new ListItem("entry 6"));
        return list;
    }

    private static Table makeAnAdditionalTable() {
        // Creating a table with two columns
        float[] columnWidths = {150f, 150f};
        Table table = new Table(columnWidths);
        // Creating a line one
        table.addCell("Test 1.1");
        table.addCell("Test 1.2");
        // Creating a line two
        table.addCell("Test 2.1");
        table.startNewRow();
        // Creating a line one
        table.addCell("Test 3.1");
        table.addCell("Test 3.2");
        // give the new table back
        return table;
    }

    private static class PageOrientationsEventHandler implements IEventHandler {
        private PdfNumber orientation = new PdfNumber(0);

        public void setOrientation(PdfNumber orientation) {
            this.orientation = orientation;
        }

        @Override
        public void handleEvent(Event currentEvent) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) currentEvent;
            docEvent.getPage().put(PdfName.Rotate, orientation);
        }

    }

}
