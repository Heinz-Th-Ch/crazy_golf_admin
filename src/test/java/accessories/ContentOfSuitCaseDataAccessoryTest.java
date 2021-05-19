package accessories;

import abstracts.AbstractPlainJava;
import dataStructures.ContentOfSuitCaseImpl;
import enumerations.DataAccessoryComparisonValues;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link ContentOfSuitCaseDataAccessory}.
 */
public class ContentOfSuitCaseDataAccessoryTest extends AbstractPlainJava {

    private final static String[] FIELD_NAMES = {"primaryKey",
            "foreignKeyBall"
    };
    private final static int INITIAL_NUMBER_OF_ENTRIES = 7;

    private final List<ContentOfSuitCaseImpl> characteristicsList = new ArrayList<>();

    private ContentOfSuitCaseImpl expectedCharacteristics;

    private ContentOfSuitCaseImpl characteristics;

    private ContentOfSuitCaseDataAccessory accessory;

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < INITIAL_NUMBER_OF_ENTRIES; i++) {
            ContentOfSuitCaseImpl localCharacteristics = new ContentOfSuitCaseImpl(i + 1,
                    i + 101);
            if (i == 3) {
                expectedCharacteristics = localCharacteristics;
            }
            characteristicsList.add(localCharacteristics);
        }
        accessory = new ContentOfSuitCaseDataAccessory();
    }

    @Test
    public void cloneElement() {
        // act
        ContentOfSuitCaseImpl result = accessory.cloneElement(expectedCharacteristics);
        // assert
        assertNotEquals("objects are unexpected same", expectedCharacteristics, result);
        assertTrue("content of objects are not equal", result.equals(expectedCharacteristics));
    }

    @Test
    public void deleteByElementNotOkayBecauseElementNotFound() {
        // arrange
        accessory.delete(characteristicsList,
                expectedCharacteristics.getPrimaryKey());
        // act
        List<ContentOfSuitCaseImpl> result = accessory.delete(characteristicsList,
                expectedCharacteristics);
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
        assertFalse("unexpected entry found",
                characteristicsList.contains(expectedCharacteristics));
    }

    @Test
    public void deleteByElementOkay() {
        // act
        List<ContentOfSuitCaseImpl> result = accessory.delete(characteristicsList,
                expectedCharacteristics);
        // assert
        assertEquals("invalid list size received",
                INITIAL_NUMBER_OF_ENTRIES - 1,
                characteristicsList.size());
        assertEquals("invalid deletion result size received",
                1,
                result.size());
        assertEquals("invalid deletion result received",
                expectedCharacteristics,
                result.get(0));
        assertFalse("unexpected entry found",
                characteristicsList.contains(expectedCharacteristics));
    }

    @Test
    public void deleteByListOfElementsOkay() throws IOException {
        // arrange
        List<ContentOfSuitCaseImpl> toDelete = new ArrayList<>();
        toDelete.add(expectedCharacteristics);
        // act
        List<ContentOfSuitCaseImpl> result = accessory.delete(characteristicsList,
                toDelete);
        // assert
        assertEquals("invalid list size received",
                INITIAL_NUMBER_OF_ENTRIES - 1,
                characteristicsList.size());
        assertEquals("invalid deletion result size received",
                1,
                result.size());
        assertEquals("invalid deletion result received",
                expectedCharacteristics,
                result.get(0));
        assertFalse("unexpected entry found",
                characteristicsList.contains(expectedCharacteristics));
    }

    @Test
    public void deleteByPrimaryKeyNotOkayBecausePrimaryKeyNotFound() {
        // act
        ContentOfSuitCaseImpl result = accessory.delete(characteristicsList, 99);
        // assert
        assertEquals("invalid list size received",
                INITIAL_NUMBER_OF_ENTRIES,
                characteristicsList.size());
        assertNull("invalid deletion result received", result);
        assertTrue("expected entry not found",
                characteristicsList.contains(expectedCharacteristics));
    }

    @Test
    public void deleteByPrimaryKeyOkay() {
        // act
        ContentOfSuitCaseImpl result = accessory.delete(characteristicsList,
                expectedCharacteristics.getPrimaryKey());
        // assert
        assertEquals("invalid list size received",
                INITIAL_NUMBER_OF_ENTRIES - 1,
                characteristicsList.size());
        assertEquals("invalid deletion result received",
                expectedCharacteristics,
                result);
        assertFalse("unexpected entry found",
                characteristicsList.contains(expectedCharacteristics));
    }

    @Test
    public void fetchByField01NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<ContentOfSuitCaseImpl> result = accessory.fetch(characteristicsList,
                1,
                DataAccessoryComparisonValues.EQ,
                INITIAL_NUMBER_OF_ENTRIES + 1);
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField01OkayWithEqual() throws IOException {
        // arrange
        ContentOfSuitCaseImpl expected = characteristicsList.get(3);
        // act
        List<ContentOfSuitCaseImpl> result = accessory.fetch(characteristicsList,
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
        ContentOfSuitCaseImpl expected = characteristicsList.get(3);
        // act
        List<ContentOfSuitCaseImpl> result = accessory.fetch(characteristicsList,
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
        List<ContentOfSuitCaseImpl> result = accessory.fetch(characteristicsList,
                2,
                DataAccessoryComparisonValues.BW,
                1,
                3);
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField02OkayWithBetween() throws IOException {
        // arrange
        ContentOfSuitCaseImpl expected = characteristicsList.get(1);
        // act
        List<ContentOfSuitCaseImpl> result = accessory.fetch(characteristicsList,
                2,
                DataAccessoryComparisonValues.BW,
                101,
                103);
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
        ContentOfSuitCaseImpl expected = characteristicsList.get(4);
        // act
        List<ContentOfSuitCaseImpl> result = accessory.fetch(characteristicsList,
                2,
                DataAccessoryComparisonValues.NB,
                101,
                103);
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 3,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(1));
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
        for (ContentOfSuitCaseImpl element : characteristicsList) {
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
        for (ContentOfSuitCaseImpl element : characteristicsList) {
            if (element.equals(expectedCharacteristics)) {
                result++;
            }
        }
        assertEquals("no duplicate element found", 2, result);
    }

    @Test
    public void insertElementOkayForUniqueElement() {
        // arrange
        ContentOfSuitCaseImpl secondCharacteristics = new ContentOfSuitCaseImpl(new ArrayList<>(),
                expectedCharacteristics);
        secondCharacteristics.setForeignKeyBall(secondCharacteristics.getForeignKeyBall() + 1000);
        // act and assert
        assertTrue("element unexpected inserted",
                accessory.insert(characteristicsList,
                        secondCharacteristics,
                        true));
        // assert
        int result = 0;
        for (ContentOfSuitCaseImpl element : characteristicsList) {
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
        ContentOfSuitCaseImpl result = accessory.select(characteristicsList,
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
        ContentOfSuitCaseImpl result = accessory.select(characteristicsList,
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
        ContentOfSuitCaseImpl expected = characteristicsList.get(3);
        // act
        ContentOfSuitCaseImpl result = accessory.select(characteristicsList,
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
        ContentOfSuitCaseImpl result = accessory.select(characteristicsList,
                2,
                DataAccessoryComparisonValues.BW,
                1,
                3);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField02NotOkayWithBetweenTooManyResults() throws IOException {
        // act
        ContentOfSuitCaseImpl result = accessory.select(characteristicsList,
                2,
                DataAccessoryComparisonValues.BW,
                101,
                103);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField02OkayWithNotBetween() throws IOException {
        // arrange
        ContentOfSuitCaseImpl expected = characteristicsList.get(0);
        // act
        ContentOfSuitCaseImpl result = accessory.select(characteristicsList,
                2,
                DataAccessoryComparisonValues.NB,
                102,
                107);
        // assert
        assertNotNull("expected result not received",
                result);
        assertEquals("wrong element found",
                expected,
                result);
    }

    @Test
    public void updateNotOkayBecauseUnknownPrimaryKey() {
        // arrange
        ContentOfSuitCaseImpl expected_1 = characteristicsList.get(3);
        ContentOfSuitCaseImpl expected_2 = accessory.cloneElement(characteristicsList.get(3));
        expected_2.setPrimaryKey(0);
        // act
        Pair<ContentOfSuitCaseImpl, ContentOfSuitCaseImpl> result = accessory.update(characteristicsList,
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
        ContentOfSuitCaseImpl expected_1 = characteristicsList.get(3);
        ContentOfSuitCaseImpl expected_2 = accessory.cloneElement(characteristicsList.get(3));
        expected_2.setForeignKeyBall(9999);
        // act
        Pair<ContentOfSuitCaseImpl, ContentOfSuitCaseImpl> result = accessory.update(characteristicsList,
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