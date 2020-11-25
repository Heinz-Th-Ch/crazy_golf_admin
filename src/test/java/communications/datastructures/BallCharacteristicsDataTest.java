package communications.datastructures;

import abstracts.AbstractPlainJava;
import dataStructures.BallCharacteristicsImpl;
import enumerations.Hardness;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link BallCharacteristicsData}.
 */
public class BallCharacteristicsDataTest extends AbstractPlainJava {

    private final String EXPECTED_WITH_EMPTY_LIST = "<ballCharacteristicsData>\n" +
            "<numberOfEntries>0</numberOfEntries>\n" +
            "</ballCharacteristicsData>\n";
    private final String EXPECTED_WITH_FILLED_LIST = "<ballCharacteristicsData>\n" +
            "<numberOfEntries>27</numberOfEntries>\n" +
            "</ballCharacteristicsData>\n";

    private BallCharacteristicsData testableData;

    @Test
    public void toXmlStringWithEmptyList() {
        // arrange
        testableData = new BallCharacteristicsData(List.of());
        // act and assert
        assertEquals("wrong result received", EXPECTED_WITH_EMPTY_LIST, testableData.toXmlString());
    }

    @Test
    public void toXmlStringWithFilledList() {
        // arrange
        List<BallCharacteristicsImpl> characteristics = new ArrayList<>(List.of());
        for (int i = 0; i < 27; i++) {
            characteristics.add(new BallCharacteristicsImpl(characteristics,
                    "ID",
                    "DESCRIPTION",
                    Hardness.H,
                    5,
                    6,
                    0.5,
                    "COMMENT"));
        }
        testableData = new BallCharacteristicsData(characteristics);
        // act and assert
        assertEquals("wrong result received", EXPECTED_WITH_FILLED_LIST, testableData.toXmlString());
    }
}