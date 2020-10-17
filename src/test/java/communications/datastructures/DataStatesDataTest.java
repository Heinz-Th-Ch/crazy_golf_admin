package communications.datastructures;

import abstracts.AbstractPlainJava;
import dataStructures.BallCharacteristicsImpl;
import dataStructures.CrazyGolfSiteCharacteristicsImpl;
import dataStructures.DataListContainerImpl;
import dataStructures.SuitCaseCharacteristicsImpl;
import enumerations.Hardness;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for {@link DataStatesData}.
 */
public class DataStatesDataTest extends AbstractPlainJava {

    private final String EXPECTED_WITH_UNAVAILABLE_DATA = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<DataStatesData>\n" +
            "<applicationName>TEST_APPLICATION</applicationName>\n" +
            "<dataAvailable>false</dataAvailable>\n" +
            "<ballCharacteristicsData>\n" +
            "<numberOfEntries>0</numberOfEntries>\n" +
            "</ballCharacteristicsData>\n" +
            "<suitCaseCharacteristicsData>\n" +
            "<numberOfEntries>0</numberOfEntries>\n" +
            "</suitCaseCharacteristicsData>\n" +
            "<crazyGolfSiteCharacteristicsData>\n" +
            "<numberOfEntries>0</numberOfEntries>\n" +
            "</crazyGolfSiteCharacteristicsData>\n" +
            "</DataStatesData>\n";
    private final String EXPECTED_WITH_AVAILABLE_DATA_WITH_EMPTY_LISTS = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<DataStatesData>\n" +
            "<applicationName>TEST_APPLICATION</applicationName>\n" +
            "<dataAvailable>true</dataAvailable>\n" +
            "<ballCharacteristicsData>\n" +
            "<numberOfEntries>0</numberOfEntries>\n" +
            "</ballCharacteristicsData>\n" +
            "<suitCaseCharacteristicsData>\n" +
            "<numberOfEntries>0</numberOfEntries>\n" +
            "</suitCaseCharacteristicsData>\n" +
            "<crazyGolfSiteCharacteristicsData>\n" +
            "<numberOfEntries>0</numberOfEntries>\n" +
            "</crazyGolfSiteCharacteristicsData>\n" +
            "</DataStatesData>\n";
    private final String EXPECTED_WITH_AVAILABLE_DATA_WITH_FILLED_LISTS = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<DataStatesData>\n" +
            "<applicationName>TEST_APPLICATION</applicationName>\n" +
            "<dataAvailable>true</dataAvailable>\n" +
            "<ballCharacteristicsData>\n" +
            "<numberOfEntries>7</numberOfEntries>\n" +
            "</ballCharacteristicsData>\n" +
            "<suitCaseCharacteristicsData>\n" +
            "<numberOfEntries>3</numberOfEntries>\n" +
            "<numberOfSubEntries>1</numberOfSubEntries>\n" +
            "<numberOfSubEntries>3</numberOfSubEntries>\n" +
            "<numberOfSubEntries>5</numberOfSubEntries>\n" +
            "</suitCaseCharacteristicsData>\n" +
            "<crazyGolfSiteCharacteristicsData>\n" +
            "<numberOfEntries>4</numberOfEntries>\n" +
            "<numberOfSubEntries>18</numberOfSubEntries>\n" +
            "<numberOfSubEntries>18</numberOfSubEntries>\n" +
            "<numberOfSubEntries>18</numberOfSubEntries>\n" +
            "<numberOfSubEntries>18</numberOfSubEntries>\n" +
            "</crazyGolfSiteCharacteristicsData>\n" +
            "</DataStatesData>\n";

    private DataListContainerImpl dataListContainer;

    private DataStatesData testableData;

    @Before
    public void setup() {
        dataListContainer = new DataListContainerImpl();
    }

    @Test
    public void toXmlStringForUnavailableData() {
        testableData = new DataStatesData("TEST_APPLICATION",
                null);
        assertEquals("wrong result received", EXPECTED_WITH_UNAVAILABLE_DATA, testableData.toXmlString());
    }

    @Test
    public void toXmlStringForAvailableDataWithEmptyLists() {
        dataListContainer.getBallCharacteristics().clear();
        dataListContainer.getSuitCaseCharacteristics().clear();
        dataListContainer.getCrazyGolfSiteCharacteristics().clear();
        testableData = new DataStatesData("TEST_APPLICATION",
                dataListContainer);
        assertEquals("wrong result received", EXPECTED_WITH_AVAILABLE_DATA_WITH_EMPTY_LISTS, testableData.toXmlString());
    }

    @Test
    public void toXmlStringForAvailableDataWithFilledLists() {
        for (int i = 0; i < 7; i++) {
            dataListContainer.getBallCharacteristics().add(new BallCharacteristicsImpl(dataListContainer.getBallCharacteristics(),
                    "ID",
                    "DESCRIPTION",
                    Hardness.H,
                    5,
                    6,
                    0.5,
                    "COMMENT"));
        }
        for (int i = 0; i < 3; i++) {
            dataListContainer.getSuitCaseCharacteristics().add(new SuitCaseCharacteristicsImpl(1,
                    "ID",
                    "DESCRIPTION",
                    "OWNER",
                    (i * 2) + 1));
        }
        for (int i = 0; i < 4; i++) {
            dataListContainer.getCrazyGolfSiteCharacteristics().add(new CrazyGolfSiteCharacteristicsImpl(1,
                    "SITE_NAME",
                    "ADDRESS",
                    "POSTCODE",
                    "TOWN"));
        }
        testableData = new DataStatesData("TEST_APPLICATION",
                dataListContainer);
        assertEquals("wrong result received", EXPECTED_WITH_AVAILABLE_DATA_WITH_FILLED_LISTS, testableData.toXmlString());
    }

}