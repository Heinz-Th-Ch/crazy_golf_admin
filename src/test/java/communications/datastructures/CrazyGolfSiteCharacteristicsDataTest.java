package communications.datastructures;

import abstracts.AbstractPlainJava;
import dataStructures.CrazyGolfSiteCharacteristicsImpl;
import dataStructures.SuitCaseCharacteristicsImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * JUnit test for {@link CrazyGolfSiteCharacteristicsData}.
 */
public class CrazyGolfSiteCharacteristicsDataTest extends AbstractPlainJava {

    private final String EXPECTED_WITH_EMPTY_LIST = "<crazyGolfSiteCharacteristicsData>\n" +
            "<numberOfEntries>0</numberOfEntries>\n" +
            "</crazyGolfSiteCharacteristicsData>\n";
    private final String EXPECTED_WITH_FILLED_LIST = "<crazyGolfSiteCharacteristicsData>\n" +
            "<numberOfEntries>4</numberOfEntries>\n" +
            "<numberOfSubEntries>18</numberOfSubEntries>\n" +
            "<numberOfSubEntries>18</numberOfSubEntries>\n" +
            "<numberOfSubEntries>18</numberOfSubEntries>\n" +
            "<numberOfSubEntries>18</numberOfSubEntries>\n" +
            "</crazyGolfSiteCharacteristicsData>\n";

    private CrazyGolfSiteCharacteristicsData testableData;

    @Test
    public void toXmlStringWithEmptyList() {
        testableData = new CrazyGolfSiteCharacteristicsData(List.of());
        assertEquals("wrong result received", EXPECTED_WITH_EMPTY_LIST, testableData.toXmlString());
    }

    @Test
    public void toXmlStringWithFilledList() {
        List<CrazyGolfSiteCharacteristicsImpl> characteristics = new ArrayList<>(List.of());
        for (int i = 0; i < 4; i++) {
            characteristics.add(new CrazyGolfSiteCharacteristicsImpl(1,
                    "SITE_NAME",
                    "ADDRESS",
                    "POSTCODE",
                    "TOWN"));
        }
        testableData = new CrazyGolfSiteCharacteristicsData(characteristics);
        assertEquals("wrong result received", EXPECTED_WITH_FILLED_LIST, testableData.toXmlString());
    }

}