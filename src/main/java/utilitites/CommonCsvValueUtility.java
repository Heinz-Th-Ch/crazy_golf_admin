package utilitites;

import utilities.ApplicationLoggerUtil;

import java.io.IOException;
import java.util.List;

/**
 * This class is used to present several values for csv import and export functions.
 */
public class CommonCsvValueUtility extends CommonTableTitleValueUtility {

    public final String csvSplitter = ";";
    public final String newLine = "\n";
    public final String notApplicable = "n/a";

    /**
     * Compares two values and, in case of a difference, write the needed debug message.<br>
     * <b>NOTE:</b>
     * In a normal case this method is only used in the test class of {@link CommonCsvValueUtility}. It is only used
     * temporarily in the production code to analyse a special error constellation.
     *
     * @param lines          a list with all available text lines
     * @param indexInList    the position of the checked value inside the list
     * @param requestedValue a value which has to check against the defined line
     * @param logger         the logger class to process a debug message
     */
    protected void checkAndDebugFailingValue(List<String> lines,
                                             int indexInList,
                                             String requestedValue,
                                             ApplicationLoggerUtil logger) throws IOException {
        String actualValue = lines.get(indexInList);
        if (requestedValue.length() != actualValue.length()) {
            logger.debug("different lengths between two values. Requested:'{}', actual: '{}'",
                    requestedValue,
                    actualValue);
            return;
        }
        if (!requestedValue.equals(actualValue)) {
            logger.debug("different contents between two values. Requested:'{}', actual: '{}'",
                    requestedValue,
                    actualValue);
            for (int i = 0; i < requestedValue.length(); i++) {
                if (requestedValue.toCharArray()[i] != actualValue.toCharArray()[i]) {
                    logger.debug("different sign between two trimmed values on position {}. Requested:'{}', actual: '{}'",
                            i,
                            requestedValue,
                            actualValue);
                }
            }
            return;
        }
        logger.debug("no differences found !!!");
    }

    /**
     * Checks if the value is contained into the list.<br>
     * When the answer is {@code false}, a debug log message will be written.
     *
     * @param lines  a list with all available text lines
     * @param value  a value which has to check against lines
     * @param logger the logger class to process a debug message
     * @return true if the value is found, otherwise false
     */
    protected boolean headLinesContainsValue(List<String> lines,
                                             String value,
                                             ApplicationLoggerUtil logger) throws IOException {
        if (lines.contains(value)) {
            return true;
        }
        logger.debug("the headline don't contain the requested value '{}'", value);
        return false;
    }

    /**
     * Trims all entries in receive headline list.
     *
     * @param lines a list with all available text lines
     */
    protected List<String> trimHeadLineEntries(List<String> lines) {
        for (int i = 0, linesSize = lines.size(); i < linesSize; i++) {
            String line = lines.get(i).trim();
            lines.remove(i);
            lines.add(i, line);
        }
        return lines;
    }
}
