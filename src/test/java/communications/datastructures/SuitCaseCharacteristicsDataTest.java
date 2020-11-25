package communications.datastructures;

import abstracts.AbstractPlainJava;
import dataStructures.SuitCaseCharacteristicsImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link SuitCaseCharacteristicsData}.
 */
public class SuitCaseCharacteristicsDataTest extends AbstractPlainJava {

    private final String EXPECTED_WITH_EMPTY_LIST = "<suitCaseCharacteristicsData>\n" +
            "<numberOfEntries>0</numberOfEntries>\n" +
            "</suitCaseCharacteristicsData>\n";
    private final String EXPECTED_WITH_FILLED_LIST = "<suitCaseCharacteristicsData>\n" +
            "<numberOfEntries>7</numberOfEntries>\n" +
            "<numberOfSubEntries>1</numberOfSubEntries>\n" +
            "<numberOfSubEntries>3</numberOfSubEntries>\n" +
            "<numberOfSubEntries>5</numberOfSubEntries>\n" +
            "<numberOfSubEntries>7</numberOfSubEntries>\n" +
            "<numberOfSubEntries>9</numberOfSubEntries>\n" +
            "<numberOfSubEntries>11</numberOfSubEntries>\n" +
            "<numberOfSubEntries>13</numberOfSubEntries>\n" +
            "</suitCaseCharacteristicsData>\n";

    private SuitCaseCharacteristicsData testableData;

    @Test
    public void toXmlStringWithEmptyList() {
        // arrange
        testableData = new SuitCaseCharacteristicsData(List.of());
        // act and assert
        assertEquals("wrong result received", EXPECTED_WITH_EMPTY_LIST, testableData.toXmlString());
    }

    @Test
    public void toXmlStringWithFilledList() {
        // arrange
        List<SuitCaseCharacteristicsImpl> characteristics = new ArrayList<>(List.of());
        for (int i = 0; i < 7; i++) {
            characteristics.add(new SuitCaseCharacteristicsImpl(1,
                    "ID",
                    "DESCRIPTION",
                    "OWNER",
                    (i * 2) + 1));
        }
        testableData = new SuitCaseCharacteristicsData(characteristics);
        // act and assert
        assertEquals("wrong result received", EXPECTED_WITH_FILLED_LIST, testableData.toXmlString());
    }

}