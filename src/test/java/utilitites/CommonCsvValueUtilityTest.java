package utilitites;

import abstracts.AbstractPlainJava;
import org.junit.Before;
import org.junit.Test;
import utilities.ApplicationLoggerUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * JUnit test for {@link CommonCsvValueUtility}.
 */
public class CommonCsvValueUtilityTest extends AbstractPlainJava {

    private final int NUMBER_OF_FIELDS = 3;

    private final Integer EXISTING_VALUE_ROW = 7;
    private final String EXISTING_VALUE = "an existing value";
    private final String EXISTING_VALUE_TO_TRIM = " an existing value ";
    private final String NOT_EXISTING_VALUE = "an NOT existing value";
    private final String WRONG_EXISTING_VALUE = "an exiSting value";

    private final ApplicationLoggerUtil loggerMock = mock(ApplicationLoggerUtil.class);
    private final List<String> headLines = new ArrayList<>();

    private final CommonCsvValueUtility utility = new CommonCsvValueUtility();

    @Before
    public void setup() {
        headLines.clear();
        for (int i = 0; i < 12; i++) {
            if (i == EXISTING_VALUE_ROW) {
                headLines.add(EXISTING_VALUE);
            }
            headLines.add("another value " + i);
        }
        reset(loggerMock);
    }

    @Test
    public void checkAndDebugFailingValueDifferentContent() throws IOException {
        // act
        utility.checkAndDebugFailingValue(headLines,
                EXISTING_VALUE_ROW,
                WRONG_EXISTING_VALUE,
                loggerMock);
        // assert
        verify(loggerMock).debug("different contents between two values. Requested:'{}', actual: '{}'",
                WRONG_EXISTING_VALUE,
                EXISTING_VALUE);
        verify(loggerMock).debug("different sign between two trimmed values on position {}. Requested:'{}', actual: '{}'",
                6,
                WRONG_EXISTING_VALUE,
                EXISTING_VALUE);
    }

    @Test
    public void checkAndDebugFailingValueDifferentLength() throws IOException {
        // act
        utility.checkAndDebugFailingValue(headLines,
                0,
                EXISTING_VALUE,
                loggerMock);
        // assert
        verify(loggerMock).debug("different lengths between two values. Requested:'{}', actual: '{}'",
                EXISTING_VALUE,
                "another value 0");
    }

    @Test
    public void checkAndDebugFailingValueNoDifferencesFound() throws IOException {
        // act
        utility.checkAndDebugFailingValue(headLines,
                EXISTING_VALUE_ROW,
                EXISTING_VALUE,
                loggerMock);
        // assert
        verify(loggerMock).debug("no differences found !!!");
    }

    @Test
    public void headLinesContainsValueNo() throws IOException {
        // act
        utility.headLinesContainsValue(headLines,
                NOT_EXISTING_VALUE,
                loggerMock);
        // assert
        verify(loggerMock).debug(anyString(), anyString());
    }

    @Test
    public void headLinesContainsValueYes() throws IOException {
        // act
        utility.headLinesContainsValue(headLines,
                EXISTING_VALUE,
                loggerMock);
        // assert
        verify(loggerMock, never()).debug(anyString(), anyString());
    }

    @Test
    public void testNumberOfFields() {
        // act and assert
        assertEquals("invalid number of fields",
                NUMBER_OF_FIELDS,
                CommonCsvValueUtility.class.getDeclaredFields().length);
    }

    @Test
    public void trimHeadLineEntries() throws IOException {
        // arrange 1
        headLines.remove(EXISTING_VALUE_ROW.intValue());
        headLines.add(EXISTING_VALUE_ROW, EXISTING_VALUE_TO_TRIM);
        // act 1
        utility.headLinesContainsValue(headLines,
                EXISTING_VALUE,
                loggerMock);
        // assert 2
        verify(loggerMock).debug(anyString(), anyString());
        reset(loggerMock);
        // arrange 2
        utility.trimHeadLineEntries(headLines);
        // act 2
        utility.headLinesContainsValue(headLines,
                EXISTING_VALUE,
                loggerMock);
        // assert 2
        verify(loggerMock, never()).debug(anyString(), anyString());
    }
}