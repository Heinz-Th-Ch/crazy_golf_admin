package accessories;

import abstracts.AbstractPlainJava;
import dataStructures.CrazyGolfSiteCharacteristicsImpl;
import enumerations.DataAccessoryComparisonValues;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CrazyGolfSiteCharacteristicsDataAccessoryTest extends AbstractPlainJava {

    private final static String[] FIELD_NAMES = {"primaryKey",
            "foreignKeySuitCase",
            "siteName",
            "address",
            "postCode",
            "town",
            "contents"
    };

    private final static int INITIAL_NUMBER_OF_ENTRIES = 7;

    private final List<CrazyGolfSiteCharacteristicsImpl> characteristicsList = new ArrayList<>();

    private CrazyGolfSiteCharacteristicsImpl expectedCharacteristics;

    private CrazyGolfSiteCharacteristicsImpl characteristics;

    private CrazyGolfSiteCharacteristicsDataAccessory accessory;

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < INITIAL_NUMBER_OF_ENTRIES; i++) {
            CrazyGolfSiteCharacteristicsImpl localCharacteristics = new CrazyGolfSiteCharacteristicsImpl(i + 1,
                    i + 111,
                    "SITE " + i,
                    "ADDRESS" + i,
                    "POSTCODE" + i,
                    "TOWN" + i,
                    new ArrayList<>());
            if (i == 3) {
                expectedCharacteristics = localCharacteristics;
            }
            characteristicsList.add(localCharacteristics);
        }
        accessory = new CrazyGolfSiteCharacteristicsDataAccessory();
    }

    @Test
    public void fetchByField01NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                1,
                DataAccessoryComparisonValues.EQ,
                INITIAL_NUMBER_OF_ENTRIES + 1);
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField01OkayWithEqual() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                1,
                DataAccessoryComparisonValues.EQ,
                4);
        // assert
        assertEquals("invalid number of results",
                1,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(0));
    }

    @Test
    public void fetchByField01OkayWithNotEqual() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                1,
                DataAccessoryComparisonValues.NE,
                4);
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 1,
                result.size());
        assertFalse("unexpected object found",
                result.contains(expected));
    }

    @Test
    public void fetchByField02NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                2,
                DataAccessoryComparisonValues.EQ,
                INITIAL_NUMBER_OF_ENTRIES + 1);
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField02OkayWithEqual() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                2,
                DataAccessoryComparisonValues.EQ,
                114);
        // assert
        assertEquals("invalid number of results",
                1,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(0));
    }

    @Test
    public void fetchByField02OkayWithNotEqual() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                2,
                DataAccessoryComparisonValues.NE,
                114);
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 1,
                result.size());
        assertFalse("unexpected object found",
                result.contains(expected));
    }

    @Test
    public void fetchByField03NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                3,
                DataAccessoryComparisonValues.BW,
                "SITE " + INITIAL_NUMBER_OF_ENTRIES + 1,
                "SITE " + INITIAL_NUMBER_OF_ENTRIES + 3);
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField03OkayWithBetween() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(2);
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                3,
                DataAccessoryComparisonValues.BW,
                "SITE 1",
                "SITE 3");
        // assert
        assertEquals("invalid number of results",
                3,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(1));
    }

    @Test
    public void fetchByField03OkayWithNotBetween() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(4);
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                3,
                DataAccessoryComparisonValues.NB,
                "SITE 1",
                "SITE 3");
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 3,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(1));
    }

    @Test
    public void fetchByField04NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                4,
                DataAccessoryComparisonValues.LK,
                "%XADD%");
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField04OkayWithNotLike() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                4,
                DataAccessoryComparisonValues.NL,
                "%3");
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 1,
                result.size());
        assertFalse("unexpected object found",
                result.contains(expected));
    }

    @Test
    public void fetchByField04OkayWithLike() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                4,
                DataAccessoryComparisonValues.LK,
                "%DDR%");
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(3));
    }

    @Test
    public void fetchByField05NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                5,
                DataAccessoryComparisonValues.EQ,
                "POSTCODE11");
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField05OkayWithEqual() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                5,
                DataAccessoryComparisonValues.EQ,
                "POSTCODE3");
        // assert
        assertEquals("invalid number of results",
                1,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(0));
    }

    @Test
    public void fetchByField05OkayWithNotEqual() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                5,
                DataAccessoryComparisonValues.NE,
                "POSTCODE3");
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 1,
                result.size());
        assertFalse("unexpected object found",
                result.contains(expected));
    }

    @Test
    public void fetchByField06NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                6,
                DataAccessoryComparisonValues.BW,
                "TOWN" + INITIAL_NUMBER_OF_ENTRIES + 1,
                "TOWN" + INITIAL_NUMBER_OF_ENTRIES + 3);
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField06OkayWithBetween() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(2);
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                6,
                DataAccessoryComparisonValues.BW,
                "TOWN1",
                "TOWN3");
        // assert
        assertEquals("invalid number of results",
                3,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(1));
    }

    @Test
    public void fetchByField06OkayWithNotBetween() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(4);
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                6,
                DataAccessoryComparisonValues.NB,
                "TOWN1",
                "TOWN3");
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 3,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(1));
    }

    @Test
    public void fetchByFieldNotOkayBecauseWrongFieldNumber() throws IOException {
        // act
        List<CrazyGolfSiteCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                7,
                DataAccessoryComparisonValues.NE,
                "COMMENT 3");
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void getFieldsToNumberedString() {
        String result = accessory.getFieldsToNumberedString();
        for (String fieldName : FIELD_NAMES) {
            assertTrue("expected field name not found",
                    result.contains(fieldName));
        }
        String[] splitResult = result.split("\n");
        for (String splitEntry : splitResult) {
            boolean found = false;
            String splitValue = splitEntry.substring(splitEntry.lastIndexOf("-") + 1).trim();
            for (String fieldName : FIELD_NAMES) {
                if (splitValue.equals(fieldName)) {
                    found = true;
                    break;
                }
            }
            assertTrue("expected split value not found",
                    found);
        }
    }

    @Test
    public void insertElementNotOkayBecauseDuplicate() {
        // act and assert
        assertFalse("element unexpected inserted",
                accessory.insert(characteristicsList,
                        expectedCharacteristics,
                        true));
        // assert
        int result = 0;
        for (CrazyGolfSiteCharacteristicsImpl element : characteristicsList) {
            if (element.equals(expectedCharacteristics)) {
                result++;
            }
        }
        assertEquals("expected element not unique or not found", 1, result);
    }

    @Test
    public void insertElementOkayForDuplicateElement() {
        // act and assert
        assertTrue("element not inserted",
                accessory.insert(characteristicsList,
                        expectedCharacteristics,
                        false));
        // assert
        int result = 0;
        for (CrazyGolfSiteCharacteristicsImpl element : characteristicsList) {
            if (element.equals(expectedCharacteristics)) {
                result++;
            }
        }
        assertEquals("no duplicate element found", 2, result);
    }

    @Test
    public void insertElementOkayForUniqueElement() {
        // arrange
        CrazyGolfSiteCharacteristicsImpl secondCharacteristics = new CrazyGolfSiteCharacteristicsImpl(new ArrayList<>(),
                expectedCharacteristics);
        secondCharacteristics.setSiteName(secondCharacteristics.getSiteName() + "X");
        // act and assert
        assertTrue("element unexpected inserted",
                accessory.insert(characteristicsList,
                        secondCharacteristics,
                        true));
        // assert
        int result = 0;
        for (CrazyGolfSiteCharacteristicsImpl element : characteristicsList) {
            if (element.equals(expectedCharacteristics)) {
                result++;
            }
        }
        assertEquals("expected element not unique or not found", 1, result);
    }

    @Test
    public void numberAndOrderOfField() {
        // act
        Field[] fields = accessory.getFields();
        // assert
        assertEquals("unexpected number of fields",
                FIELD_NAMES.length,
                fields.length);
        for (int i = 0; i < fields.length; i++) {
            assertEquals("field not in correct sequence",
                    FIELD_NAMES[i],
                    fields[i].getName());
        }
    }

    @Test
    public void selectByField01NotOkayBecauseValueNotFound() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                1,
                DataAccessoryComparisonValues.EQ,
                INITIAL_NUMBER_OF_ENTRIES + 1);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField01NotOkayWithNotEqualTooManyResults() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                1,
                DataAccessoryComparisonValues.NE,
                4);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField01OkayWithEqual() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                1,
                DataAccessoryComparisonValues.EQ,
                4);
        // assert
        assertNotNull("expected result not received",
                result);
        assertEquals("wrong element found",
                expected,
                result);
    }

    @Test
    public void selectByField02NotOkayBecauseValueNotFound() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                2,
                DataAccessoryComparisonValues.EQ,
                INITIAL_NUMBER_OF_ENTRIES + 1);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField02NotOkayWithNotEqualTooManyResults() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                2,
                DataAccessoryComparisonValues.NE,
                114);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField02OkayWithEqual() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                2,
                DataAccessoryComparisonValues.EQ,
                114);
        // assert
        assertNotNull("expected result not received",
                result);
        assertEquals("wrong element found",
                expected,
                result);
    }

    @Test
    public void selectByField03NotOkayBecauseValueNotFound() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                3,
                DataAccessoryComparisonValues.BW,
                "SITE " + INITIAL_NUMBER_OF_ENTRIES + 1,
                "SITE " + INITIAL_NUMBER_OF_ENTRIES + 3);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField03NotOkayWithBetweenTooManyResults() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                3,
                DataAccessoryComparisonValues.BW,
                "SITE 1",
                "SITE 3");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField03OkayWithNotBetween() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(0);
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                3,
                DataAccessoryComparisonValues.NB,
                "SITE 1",
                "SITE 7");
        // assert
        assertNotNull("expected result not received",
                result);
        assertEquals("wrong element found",
                expected,
                result);
    }

    @Test
    public void selectByField04NotOkayBecauseValueNotFound() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                4,
                DataAccessoryComparisonValues.LK,
                "%XADD%");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField04NotOkayWithNotLikeTooManyResults() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                4,
                DataAccessoryComparisonValues.NL,
                "%3");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField04OkayWithLike() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                4,
                DataAccessoryComparisonValues.LK,
                "%DDRESS3");
        // assert
        assertNotNull("expected result not received",
                result);
        assertEquals("wrong element found",
                expected,
                result);
    }

    @Test
    public void selectByField05NotOkayBecauseValueNotFound() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                5,
                DataAccessoryComparisonValues.EQ,
                "POSTCODE");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField05NotOkayWithNotEqualTooManyResults() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                5,
                DataAccessoryComparisonValues.NE,
                "POSTCODE3");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField05OkayWithEqual() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                5,
                DataAccessoryComparisonValues.EQ,
                "POSTCODE3");
        // assert
        assertNotNull("expected result not received",
                result);
        assertEquals("wrong element found",
                expected,
                result);
    }

    @Test
    public void selectByField06NotOkayBecauseValueNotFound() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                6,
                DataAccessoryComparisonValues.LK,
                "%XTOW%");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField06NotOkayWithNotLikeTooManyResults() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                6,
                DataAccessoryComparisonValues.NL,
                "%3");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField06OkayWithLike() throws IOException {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                6,
                DataAccessoryComparisonValues.LK,
                "%TOWN3");
        // assert
        assertNotNull("expected result not received",
                result);
        assertEquals("wrong element found",
                expected,
                result);
    }

    @Test
    public void selectByFieldNotOkayBecauseWrongFieldNumber() throws IOException {
        // act
        CrazyGolfSiteCharacteristicsImpl result = accessory.select(characteristicsList,
                7,
                DataAccessoryComparisonValues.NE,
                "COMMENT 3");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void updateNotOkayBecauseUnknownPrimaryKey() {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected_1 = characteristicsList.get(3);
        CrazyGolfSiteCharacteristicsImpl expected_2 = accessory.cloneElement(characteristicsList.get(3));
        expected_2.setPrimaryKey(0);
        // act
        Pair<CrazyGolfSiteCharacteristicsImpl, CrazyGolfSiteCharacteristicsImpl> result = accessory.update(characteristicsList,
                expected_2);
        // assert
        assertNull("unexpected old result received",
                result.getLeft());
        assertEquals("unexpected old result received",
                expected_2,
                result.getRight());
        assertTrue("expected element not found in list",
                characteristicsList.contains(expected_1));
        assertFalse("unexpected element found in list",
                characteristicsList.contains(expected_2));
    }

    @Test
    public void updateOkay() {
        // arrange
        CrazyGolfSiteCharacteristicsImpl expected_1 = characteristicsList.get(3);
        CrazyGolfSiteCharacteristicsImpl expected_2 = accessory.cloneElement(characteristicsList.get(3));
        expected_2.setSiteName("newSite");
        // act
        Pair<CrazyGolfSiteCharacteristicsImpl, CrazyGolfSiteCharacteristicsImpl> result = accessory.update(characteristicsList,
                expected_2);
        // assert
        assertEquals("unexpected old result received",
                expected_1,
                result.getLeft());
        assertEquals("unexpected new result received",
                expected_2,
                result.getRight());
        assertTrue("expected element not found in list",
                characteristicsList.contains(expected_2));
        assertFalse("unexpected element found in list",
                characteristicsList.contains(expected_1));
    }

}