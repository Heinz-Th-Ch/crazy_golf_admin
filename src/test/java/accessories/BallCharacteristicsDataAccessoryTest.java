package accessories;

import abstracts.AbstractPlainJava;
import dataStructures.BallCharacteristicsImpl;
import enumerations.DataAccessoryComparisonValues;
import enumerations.Hardness;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link BallCharacteristicsDataAccessory}.
 */
public class BallCharacteristicsDataAccessoryTest extends AbstractPlainJava {

    private final static String[] FIELD_NAMES = {"primaryKey",
            "identifier",
            "description",
            "hardness",
            "upThrow",
            "weight",
            "angleFactor",
            "comment"
    };
    private final static int INITIAL_NUMBER_OF_ENTRIES = 7;

    private final List<BallCharacteristicsImpl> characteristicsList = new ArrayList<>();

    private BallCharacteristicsImpl expectedCharacteristics;

    private BallCharacteristicsImpl characteristics;

    private BallCharacteristicsDataAccessory accessory;

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < INITIAL_NUMBER_OF_ENTRIES; i++) {
            Hardness hardness;
            switch (i) {
                case 0:
                    hardness = Hardness.H;
                    break;
                case 1:
                    hardness = Hardness.M;
                    break;
                case 2:
                    hardness = Hardness.S;
                    break;
                case 3:
                    hardness = Hardness.VH;
                    break;
                case 4:
                    hardness = Hardness.VS;
                    break;
                default:
                    hardness = Hardness.UNDEF;
            }
            BallCharacteristicsImpl localCharacteristics = new BallCharacteristicsImpl(i + 1,
                    "ID " + i,
                    "DESC" + i,
                    hardness,
                    i * 5,
                    i * 11,
                    0.05 * i,
                    "COMMENT " + i);
            if (i == 3) {
                expectedCharacteristics = localCharacteristics;
            }
            characteristicsList.add(localCharacteristics);
        }
        accessory = new BallCharacteristicsDataAccessory();
    }

    @Test
    public void cloneElement() {
        // act
        BallCharacteristicsImpl result = accessory.cloneElement(expectedCharacteristics);
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
        List<BallCharacteristicsImpl> result = accessory.delete(characteristicsList,
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
        List<BallCharacteristicsImpl> result = accessory.delete(characteristicsList,
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
        List<BallCharacteristicsImpl> toDelete = new ArrayList<>();
        toDelete.add(expectedCharacteristics);
        // act
        List<BallCharacteristicsImpl> result = accessory.delete(characteristicsList,
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
        BallCharacteristicsImpl result = accessory.delete(characteristicsList, 99);
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
        BallCharacteristicsImpl result = accessory.delete(characteristicsList,
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
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                1,
                DataAccessoryComparisonValues.EQ,
                INITIAL_NUMBER_OF_ENTRIES + 1);
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField01OkayWithEqual() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        BallCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                1,
                DataAccessoryComparisonValues.NE,
                expected.getPrimaryKey());
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
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        BallCharacteristicsImpl expected = characteristicsList.get(2);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        BallCharacteristicsImpl expected = characteristicsList.get(4);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        BallCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        BallCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
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
        // arrange
        characteristicsList.remove(4);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                4,
                DataAccessoryComparisonValues.EQ,
                Hardness.VS);
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField04OkayWithEqual() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(5);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                4,
                DataAccessoryComparisonValues.EQ,
                Hardness.UNDEF);
        // assert
        assertEquals("invalid number of results",
                2,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(0));
    }

    @Test
    public void fetchByField04OkayWithNotEqual() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                4,
                DataAccessoryComparisonValues.NE,
                Hardness.UNDEF);
        // assert
        assertEquals("invalid number of results",
                5,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(3));
    }

    @Test
    public void fetchByField05NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                5,
                DataAccessoryComparisonValues.BW,
                113,
                122);
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField05OkayWithBetween() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                5,
                DataAccessoryComparisonValues.BW,
                13,
                22);
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
        BallCharacteristicsImpl expected_1 = characteristicsList.get(0);
        BallCharacteristicsImpl expected_2 = characteristicsList.get(6);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                5,
                DataAccessoryComparisonValues.NB,
                6,
                28);
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
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                6,
                DataAccessoryComparisonValues.EQ,
                45);
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField06OkayWithEqual() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(4);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                6,
                DataAccessoryComparisonValues.EQ,
                44);
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
        BallCharacteristicsImpl expected = characteristicsList.get(5);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                6,
                DataAccessoryComparisonValues.NE,
                44);
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 1,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(4));
    }

    @Test
    public void fetchByField07NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                7,
                DataAccessoryComparisonValues.BW,
                0.01,
                0.04);
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField07OkayWithBetween() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(0);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                7,
                DataAccessoryComparisonValues.BW,
                0.0,
                0.2);
        // assert
        assertEquals("invalid number of results",
                INITIAL_NUMBER_OF_ENTRIES - 2,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(0));
    }

    @Test
    public void fetchByField07OkayWithNotBetween() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(5);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                7,
                DataAccessoryComparisonValues.NB,
                0.0,
                0.2);
        // assert
        assertEquals("invalid number of results",
                2,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(0));
    }

    @Test
    public void fetchByField08NotOkayBecauseValueNotFound() throws IOException {
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                8,
                DataAccessoryComparisonValues.EQ,
                "COMMENT 11");
        // assert
        assertTrue("result is not empty",
                result.isEmpty());
    }

    @Test
    public void fetchByField08OkayWithEqual() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                8,
                DataAccessoryComparisonValues.EQ,
                "COMMENT 3");
        // assert
        assertEquals("invalid number of results",
                1,
                result.size());
        assertEquals("wrong element found",
                expected,
                result.get(0));
    }

    @Test
    public void fetchByField08OkayWithNotEqual() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                8,
                DataAccessoryComparisonValues.NE,
                "COMMENT 3");
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
        List<BallCharacteristicsImpl> result = accessory.fetch(characteristicsList,
                9,
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
        for (BallCharacteristicsImpl element : characteristicsList) {
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
        for (BallCharacteristicsImpl element : characteristicsList) {
            if (element.equals(expectedCharacteristics)) {
                result++;
            }
        }
        assertEquals("no duplicate element found", 2, result);
    }

    @Test
    public void insertElementOkayForUniqueElement() {
        // arrange
        BallCharacteristicsImpl secondCharacteristics = new BallCharacteristicsImpl(new ArrayList<>(),
                expectedCharacteristics);
        secondCharacteristics.setIdentifier(secondCharacteristics.getIdentifier() + "X");
        // act and assert
        assertTrue("element unexpected inserted",
                accessory.insert(characteristicsList,
                        secondCharacteristics,
                        true));
        // assert
        int result = 0;
        for (BallCharacteristicsImpl element : characteristicsList) {
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
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
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
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
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
        BallCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
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
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
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
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
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
        BallCharacteristicsImpl expected = characteristicsList.get(0);
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
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
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
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
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
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
        BallCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
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
        // arrange
        characteristicsList.remove(4);
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                4,
                DataAccessoryComparisonValues.EQ,
                Hardness.VS);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField04NotOkayWithNotEqualTooManyResults() throws IOException {
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                4,
                DataAccessoryComparisonValues.NE,
                Hardness.UNDEF);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField04OkayWithEqual() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(0);
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                4,
                DataAccessoryComparisonValues.EQ,
                Hardness.H);
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
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                5,
                DataAccessoryComparisonValues.BW,
                113,
                122);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField05NotOkayWithNotBetweenTooManResults() throws IOException {
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                5,
                DataAccessoryComparisonValues.NB,
                6,
                28);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField05OkayWithBetween() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                5,
                DataAccessoryComparisonValues.BW,
                13,
                17);
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
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                6,
                DataAccessoryComparisonValues.EQ,
                45);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField06NotOkayWithNotEqualTooManyResults() throws IOException {
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                6,
                DataAccessoryComparisonValues.NE,
                44);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField06OkayWithEqual() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(4);
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                6,
                DataAccessoryComparisonValues.EQ,
                44);
        // assert
        assertNotNull("expected result not received",
                result);
        assertEquals("wrong element found",
                expected,
                result);
    }

    @Test
    public void selectByField07NotOkayBecauseValueNotFound() throws IOException {
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                7,
                DataAccessoryComparisonValues.BW,
                0.01,
                0.04);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField07NotOkayWithNotBetweenTooManyResults() throws IOException {
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                7,
                DataAccessoryComparisonValues.NB,
                0.0,
                0.2);
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField07OkayWithBetween() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(1);
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                7,
                DataAccessoryComparisonValues.BW,
                0.03,
                0.08);
        // assert
        assertNotNull("expected result not received",
                result);
        assertEquals("wrong element found",
                expected,
                result);
    }

    @Test
    public void selectByField08NotOkayBecauseValueNotFound() throws IOException {
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                8,
                DataAccessoryComparisonValues.EQ,
                "COMMENT 11");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField08NotOkayWithNotEqualTooManyResults() throws IOException {
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                8,
                DataAccessoryComparisonValues.NE,
                "COMMENT 3");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void selectByField08OkayWithEqual() throws IOException {
        // arrange
        BallCharacteristicsImpl expected = characteristicsList.get(3);
        // act
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                8,
                DataAccessoryComparisonValues.EQ,
                "COMMENT 3");
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
        BallCharacteristicsImpl result = accessory.select(characteristicsList,
                9,
                DataAccessoryComparisonValues.NE,
                "COMMENT 3");
        // assert
        assertNull("unexpected result received",
                result);
    }

    @Test
    public void updateNotOkayBecauseUnknownPrimaryKey() {
        // arrange
        BallCharacteristicsImpl expected_1 = characteristicsList.get(3);
        BallCharacteristicsImpl expected_2 = accessory.cloneElement(characteristicsList.get(3));
        expected_2.setPrimaryKey(0);
        expected_2.setDescription("newDescription");
        // act
        Pair<BallCharacteristicsImpl, BallCharacteristicsImpl> result = accessory.update(characteristicsList,
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
        BallCharacteristicsImpl expected_1 = characteristicsList.get(3);
        BallCharacteristicsImpl expected_2 = accessory.cloneElement(characteristicsList.get(3));
        expected_2.setDescription("newDescription");
        // act
        Pair<BallCharacteristicsImpl, BallCharacteristicsImpl> result = accessory.update(characteristicsList,
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