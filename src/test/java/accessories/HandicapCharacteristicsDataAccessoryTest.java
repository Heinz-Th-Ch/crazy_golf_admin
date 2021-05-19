package accessories;

import abstracts.AbstractPlainJava;
import dataStructures.HandicapCharacteristicsImpl;
import enumerations.DataAccessoryComparisonValues;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * JU/nit test for {@link HandicapCharacteristicsDataAccessory}.
 */
public class HandicapCharacteristicsDataAccessoryTest extends AbstractPlainJava {

    private final static String[] FIELD_NAMES = {"primaryKey",
            "foreignKeyBall",
            "positioning",
            "cushioning",
            "marking",
            "remark"
    };
    private final static int INITIAL_NUMBER_OF_ENTRIES = 7;

    private final List<HandicapCharacteristicsImpl> characteristicsList = new ArrayList<>();

    private HandicapCharacteristicsImpl expectedCharacteristics;

    private HandicapCharacteristicsImpl characteristics;

    private HandicapCharacteristicsDataAccessory accessory;

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < INITIAL_NUMBER_OF_ENTRIES; i++) {
            HandicapCharacteristicsImpl localCharacteristics = new HandicapCharacteristicsImpl(i + 1,
                    i + 101,
                    "POSITIONING " + i,
                    "CUSHIONING " + i,
                    "MARKING " + i,
                    "REMARK " + i);
            if (i == 3) {
                expectedCharacteristics = localCharacteristics;
            }
            characteristicsList.add(localCharacteristics);
        }
        accessory = new HandicapCharacteristicsDataAccessory();
    }

    @Test
    public void cloneElement() {
        // act
        HandicapCharacteristicsImpl result = accessory.cloneElement(expectedCharacteristics);
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
        List<HandicapCharacteristicsImpl> result = accessory.delete(characteristicsList,
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
        List<HandicapCharacteristicsImpl> result = accessory.delete(characteristicsList,
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
        List<HandicapCharacteristicsImpl> toDelete = new ArrayList<>();
        toDelete.add(expectedCharacteristics);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.delete(characteristicsList,
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
        HandicapCharacteristicsImpl result = accessory.delete(characteristicsList, 99);
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
        HandicapCharacteristicsImpl result = accessory.delete(characteristicsList,
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
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                1,
                DataAccessoryComparisonValues.EQ,
                INITIAL_NUMBER_OF_ENTRIES + 1);
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField01OkayWithEqual() throws IOException {
        // arrange
        HandicapCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        HandicapCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        HandicapCharacteristicsImpl expected = characteristicsList.get(1);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        HandicapCharacteristicsImpl expected = characteristicsList.get(4);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
    public void fetchByField03NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                3,
                DataAccessoryComparisonValues.LK,
                "%XPOS%");
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField03OkayWithNotLike() throws IOException {
        // arrange
        HandicapCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        HandicapCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                3,
                DataAccessoryComparisonValues.LK,
                "%NIN%");
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
        // arrange
        characteristicsList.remove(4);
        HandicapCharacteristicsImpl expected = characteristicsList.get(5);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                4,
                DataAccessoryComparisonValues.EQ,
                "CUSHIONING");
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField04OkayWithEqual() throws IOException {
        // arrange
        HandicapCharacteristicsImpl expected = characteristicsList.get(6);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                4,
                DataAccessoryComparisonValues.EQ,
                "CUSHIONING 6");
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
        HandicapCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                4,
                DataAccessoryComparisonValues.NE,
                "CUSHIONING 6");
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 1,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(3));
    }

    @Test
    public void fetchByField05NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                5,
                DataAccessoryComparisonValues.BW,
                "MARKING 8",
                "MARKING 9");
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField05OkayWithBetween() throws IOException {
        // arrange
        HandicapCharacteristicsImpl expected = characteristicsList.get(5);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                5,
                DataAccessoryComparisonValues.BW,
                "MARKING 5",
                "MARKING 6");
        // assert
        assertEquals("invalid number of results",
                2,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(0));
    }

    @Test
    public void fetchByField05OkayWithNotBetween() throws IOException {
        // arrange
        HandicapCharacteristicsImpl expected_1 = characteristicsList.get(0);
        HandicapCharacteristicsImpl expected_2 = characteristicsList.get(2);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                5,
                DataAccessoryComparisonValues.NB,
                "MARKING 3",
                "MARKING 6");
        // assert
        assertEquals("invalid number of results",
                3,
                result.size());
        assertEquals("wrong element found",
                expected_1,
                result.get(0));
        assertEquals("wrong element found",
                expected_2,
                result.get(2));
    }

    @Test
    public void fetchByField06NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                6,
                DataAccessoryComparisonValues.EQ,
                "REMARK");
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField06OkayWithEqual() throws IOException {
        // arrange
        HandicapCharacteristicsImpl expected = characteristicsList.get(4);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                6,
                DataAccessoryComparisonValues.EQ,
                "REMARK 4");
        // assert
        assertEquals("invalid number of results",
                1,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(0));
    }

    @Test
    public void fetchByField06OkayWithNotEqual() throws IOException {
        // arrange
        HandicapCharacteristicsImpl expected = characteristicsList.get(5);
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                6,
                DataAccessoryComparisonValues.NE,
                "REMARK 4");
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 1,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(4));
    }

    @Test
    public void fetchByFieldNotOkayBecauseWrongFieldNumber() throws IOException {
        // act
        List<HandicapCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        for (HandicapCharacteristicsImpl element : characteristicsList) {
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
        for (HandicapCharacteristicsImpl element : characteristicsList) {
            if (element.equals(expectedCharacteristics)) {
                result++;
            }
        }
        assertEquals("no duplicate element found", 2, result);
    }

    @Test
    public void insertElementOkayForUniqueElement() {
        // arrange
        HandicapCharacteristicsImpl secondCharacteristics = new HandicapCharacteristicsImpl(new ArrayList<>(),
                expectedCharacteristics);
        secondCharacteristics.setPositioning(secondCharacteristics.getPositioning() + "X");
        // act and assert
        assertTrue("element unexpected inserted",
                accessory.insert(characteristicsList,
                        secondCharacteristics,
                        true));
        // assert
        int result = 0;
        for (HandicapCharacteristicsImpl element : characteristicsList) {
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
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
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
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
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
        HandicapCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
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
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
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
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
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
        HandicapCharacteristicsImpl expected = characteristicsList.get(0);
        // act
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
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
    public void selectByField03NotOkayBecauseValueNotFound() throws IOException {
        // act
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
                3,
                DataAccessoryComparisonValues.LK,
                "%XPOS%");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField03NotOkayWithNotLikeTooManyResults() throws IOException {
        // act
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
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
        HandicapCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
                3,
                DataAccessoryComparisonValues.LK,
                "%ING 3");
        // assert
        assertNotNull("expected result not received",
                result);
        assertEquals("wrong element found",
                expected,
                result);
    }

    @Test
    public void selectByField04NotOkayBecauseValueNotFound() throws IOException {
        // arrange
        characteristicsList.remove(4);
        HandicapCharacteristicsImpl expected = characteristicsList.get(5);
        // act
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
                4,
                DataAccessoryComparisonValues.EQ,
                "CUSHIONING");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField04NotOkayWithNotEqualTooManyResults() throws IOException {
        // act
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
                4,
                DataAccessoryComparisonValues.NE,
                "CUSHIONING 4");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField04OkayWithEqual() throws IOException {
        // arrange
        HandicapCharacteristicsImpl expected = characteristicsList.get(5);
        // act
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
                4,
                DataAccessoryComparisonValues.EQ,
                "CUSHIONING 5");
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
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
                5,
                DataAccessoryComparisonValues.BW,
                "MARKING 8",
                "MARKING 9");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField05NotOkayWithNotBetweenTooManResults() throws IOException {
        // act
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
                5,
                DataAccessoryComparisonValues.NB,
                "MARKING 3",
                "MARKING 5");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField05OkayWithBetween() throws IOException {
        // arrange
        HandicapCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
                5,
                DataAccessoryComparisonValues.BW,
                "MARKING 2.5",
                "MARKING 3.5");
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
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
                6,
                DataAccessoryComparisonValues.EQ,
                "REMARK 2.5");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField06NotOkayWithNotEqualTooManyResults() throws IOException {
        // act
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
                6,
                DataAccessoryComparisonValues.NE,
                "REMARK 5");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField06OkayWithEqual() throws IOException {
        // arrange
        HandicapCharacteristicsImpl expected = characteristicsList.get(4);
        // act
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
                6,
                DataAccessoryComparisonValues.EQ,
                "REMARK 4");
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
        HandicapCharacteristicsImpl result = accessory.select(characteristicsList,
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
        HandicapCharacteristicsImpl expected_1 = characteristicsList.get(3);
        HandicapCharacteristicsImpl expected_2 = accessory.cloneElement(characteristicsList.get(3));
        expected_2.setPrimaryKey(0);
        // act
        Pair<HandicapCharacteristicsImpl, HandicapCharacteristicsImpl> result = accessory.update(characteristicsList,
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
        HandicapCharacteristicsImpl expected_1 = characteristicsList.get(3);
        HandicapCharacteristicsImpl expected_2 = accessory.cloneElement(characteristicsList.get(3));
        expected_2.setMarking("newMarking");
        // act
        Pair<HandicapCharacteristicsImpl, HandicapCharacteristicsImpl> result = accessory.update(characteristicsList,
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