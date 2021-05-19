package accessories;

import abstracts.AbstractPlainJava;
import dataStructures.SuitCaseCharacteristicsImpl;
import enumerations.DataAccessoryComparisonValues;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link SuitCaseCharacteristicsDataAccessory}.
 */
public class SuitCaseCharacteristicsDataAccessoryTest extends AbstractPlainJava {

    private final static String[] FIELD_NAMES = {"primaryKey",
            "identifier",
            "description",
            "owner",
            "contents"
    };

    private final static int INITIAL_NUMBER_OF_ENTRIES = 7;

    private final List<SuitCaseCharacteristicsImpl> characteristicsList = new ArrayList<>();

    private SuitCaseCharacteristicsImpl expectedCharacteristics;

    private SuitCaseCharacteristicsImpl characteristics;

    private SuitCaseCharacteristicsDataAccessory accessory;

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < INITIAL_NUMBER_OF_ENTRIES; i++) {
            SuitCaseCharacteristicsImpl localCharacteristics = new SuitCaseCharacteristicsImpl(i + 1,
                    "ID " + i,
                    "DESC" + i,
                    "OWNER" + i,
                    new ArrayList<>());
            if (i == 3) {
                expectedCharacteristics = localCharacteristics;
            }
            characteristicsList.add(localCharacteristics);
        }
        accessory = new SuitCaseCharacteristicsDataAccessory();
    }

    @Test
    public void fetchByField01NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                1,
                DataAccessoryComparisonValues.EQ,
                INITIAL_NUMBER_OF_ENTRIES + 1);
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField01OkayWithEqual() throws IOException {
        // arrange
        SuitCaseCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        SuitCaseCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                2,
                DataAccessoryComparisonValues.BW,
                "ID " + INITIAL_NUMBER_OF_ENTRIES + 1,
                "ID " + INITIAL_NUMBER_OF_ENTRIES + 3);
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField02OkayWithBetween() throws IOException {
        // arrange
        SuitCaseCharacteristicsImpl expected = characteristicsList.get(2);
        // act
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                2,
                DataAccessoryComparisonValues.BW,
                "ID 1",
                "ID 3");
        // assert
        assertEquals("invalid number of results",
                3,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(1));
    }

    @Test
    public void fetchByField02OkayWithNotBetween() throws IOException {
        // arrange
        SuitCaseCharacteristicsImpl expected = characteristicsList.get(4);
        // act
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                2,
                DataAccessoryComparisonValues.NB,
                "ID 1",
                "ID 3");
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 3,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(1));
    }

    @Test
    public void fetchByField03NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                3,
                DataAccessoryComparisonValues.LK,
                "%XESC%");
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField03OkayWithNotLike() throws IOException {
        // arrange
        SuitCaseCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                3,
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
    public void fetchByField03OkayWithLike() throws IOException {
        // arrange
        SuitCaseCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                3,
                DataAccessoryComparisonValues.LK,
                "%ESC%");
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(3));
    }

    @Test
    public void fetchByField04NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                4,
                DataAccessoryComparisonValues.EQ,
                "OWNER11");
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField04OkayWithEqual() throws IOException {
        // arrange
        SuitCaseCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                4,
                DataAccessoryComparisonValues.EQ,
                "OWNER3");
        // assert
        assertEquals("invalid number of results",
                1,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(0));
    }

    @Test
    public void fetchByField04OkayWithNotEqual() throws IOException {
        // arrange
        SuitCaseCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                4,
                DataAccessoryComparisonValues.NE,
                "OWNER3");
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 1,
                result.size());
        assertFalse("unexpected object found",
                result.contains(expected));
    }

    @Test
    public void fetchByFieldNotOkayBecauseWrongFieldNumber() throws IOException {
        // act
        List<SuitCaseCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                5,
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
        for (SuitCaseCharacteristicsImpl element : characteristicsList) {
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
        for (SuitCaseCharacteristicsImpl element : characteristicsList) {
            if (element.equals(expectedCharacteristics)) {
                result++;
            }
        }
        assertEquals("no duplicate element found", 2, result);
    }

    @Test
    public void insertElementOkayForUniqueElement() {
        // arrange
        SuitCaseCharacteristicsImpl secondCharacteristics = new SuitCaseCharacteristicsImpl(new ArrayList<>(),
                expectedCharacteristics);
        secondCharacteristics.setIdentifier(secondCharacteristics.getIdentifier() + "X");
        // act and assert
        assertTrue("element unexpected inserted",
                accessory.insert(characteristicsList,
                        secondCharacteristics,
                        true));
        // assert
        int result = 0;
        for (SuitCaseCharacteristicsImpl element : characteristicsList) {
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
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
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
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
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
        SuitCaseCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
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
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
                2,
                DataAccessoryComparisonValues.BW,
                "ID " + INITIAL_NUMBER_OF_ENTRIES + 1,
                "ID " + INITIAL_NUMBER_OF_ENTRIES + 3);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField02NotOkayWithBetweenTooManyResults() throws IOException {
        // act
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
                2,
                DataAccessoryComparisonValues.BW,
                "ID 1",
                "ID 3");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField02OkayWithNotBetween() throws IOException {
        // arrange
        SuitCaseCharacteristicsImpl expected = characteristicsList.get(0);
        // act
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
                2,
                DataAccessoryComparisonValues.NB,
                "ID 1",
                "ID 7");
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
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
                3,
                DataAccessoryComparisonValues.LK,
                "%XESC%");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField03NotOkayWithNotLikeTooManyResults() throws IOException {
        // act
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
                3,
                DataAccessoryComparisonValues.NL,
                "%3");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField03OkayWithLike() throws IOException {
        // arrange
        SuitCaseCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
                3,
                DataAccessoryComparisonValues.LK,
                "%ESC3");
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
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
                4,
                DataAccessoryComparisonValues.EQ,
                "OWNER11");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField04NotOkayWithNotEqualTooManyResults() throws IOException {
        // act
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
                4,
                DataAccessoryComparisonValues.NE,
                "OWNER3");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField04OkayWithEqual() throws IOException {
        // arrange
        SuitCaseCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
                4,
                DataAccessoryComparisonValues.EQ,
                "OWNER3");
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
        SuitCaseCharacteristicsImpl result = accessory.select(characteristicsList,
                5,
                DataAccessoryComparisonValues.NE,
                "COMMENT 3");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void updateNotOkayBecauseUnknownPrimaryKey() {
        // arrange
        SuitCaseCharacteristicsImpl expected_1 = characteristicsList.get(3);
        SuitCaseCharacteristicsImpl expected_2 = accessory.cloneElement(characteristicsList.get(3));
        expected_2.setPrimaryKey(0);
        // act
        Pair<SuitCaseCharacteristicsImpl, SuitCaseCharacteristicsImpl> result = accessory.update(characteristicsList,
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
        SuitCaseCharacteristicsImpl expected_1 = characteristicsList.get(3);
        SuitCaseCharacteristicsImpl expected_2 = accessory.cloneElement(characteristicsList.get(3));
        expected_2.setDescription("newDescription");
        // act
        Pair<SuitCaseCharacteristicsImpl, SuitCaseCharacteristicsImpl> result = accessory.update(characteristicsList,
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