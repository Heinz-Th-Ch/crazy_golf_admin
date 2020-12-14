package importAndExport.exporters;

import abstracts.AbstractPlainJava;
import org.junit.Test;

/**
 * JUnit test for {@link AbstractCsvDataExporter}.
 */
public class AbstractCsvDataExporterTest extends AbstractPlainJava {

    private final int NUMBER_OF_METHODS = 1;

    @Test
    public void testNumberOfMethods() {
        // act and assert
        assertEquals("invalid number of methods",
                NUMBER_OF_METHODS,
                AbstractCsvDataExporter.class.getDeclaredMethods().length);
    }

}