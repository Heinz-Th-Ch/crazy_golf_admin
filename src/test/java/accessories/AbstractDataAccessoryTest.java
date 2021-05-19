package accessories;

import abstracts.AbstractPlainJava;
import enumerations.DataAccessoryComparisonValues;
import enumerations.Hardness;
import exceptions.IncompatibleClassException;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link AbstractDataAccessory}.
 */
public class AbstractDataAccessoryTest extends AbstractPlainJava {

    private final static String PART_OF_CHECKED_FIELD_NAME = "TEST_FIELD";
    private final static String TEST_FIELD_0 = "TEST_FIELD_0";

    private final static String TEST_FIELD_1 = "TEST_FIELD_1";
    private final static String TEST_FIELD_2 = "TEST_FIELD_2";

    private final static String TEST_ELEMENT = "testElement";

    private Field[] fields;
    private TestClass accessory;

    @Before
    public void setUp() throws Exception {
        List<Field> tempFields = new ArrayList<>();
        for (Field field : getClass().getDeclaredFields()) {
            if (field.getName().startsWith(PART_OF_CHECKED_FIELD_NAME)) {
                tempFields.add(field);
            }
        }
        fields = new Field[tempFields.size()];
        for (int i = 0; i < tempFields.size(); i++) {
            fields[i] = tempFields.get(i);
        }
        accessory = new TestClass(fields);
    }

    @Test
    public void betweenFound() {
        // act and assert - inside searched values
        assertTrue("Double value not between criteria", accessory.between(1.1, 1.0, 1.2));
        assertTrue("Float value not between criteria", accessory.between(154f, 153f, 155f));
        assertTrue("Integer value not between criteria", accessory.between(54, 53, 55));
        assertTrue("String value not between criteria", accessory.between(TEST_FIELD_1, "TEST_FIELD_0", "TEST_FIELD_2"));
        // act and assert - equal lower searched value
        assertTrue("Double value not between criteria", accessory.between(1.0, 1.0, 1.2));
        assertTrue("Float value not between criteria", accessory.between(153f, 153f, 155f));
        assertTrue("Integer value not between criteria", accessory.between(53, 53, 55));
        assertTrue("String value not between criteria", accessory.between(TEST_FIELD_0, "TEST_FIELD_0", "TEST_FIELD_2"));
        // act and assert - equal upper searched values
        assertTrue("Double value not between criteria", accessory.between(1.2, 1.0, 1.2));
        assertTrue("Float value not between criteria", accessory.between(155f, 153f, 155f));
        assertTrue("Integer value not between criteria", accessory.between(55, 53, 55));
        assertTrue("String value not between criteria", accessory.between(TEST_FIELD_2, "TEST_FIELD_0", "TEST_FIELD_2"));
    }

    @Test
    public void betweenNotFound() {
        // act and assert - smaller lower searched value
        assertFalse("Double value between criteria", accessory.between(0.9, 1.0, 1.2));
        assertFalse("Float value between criteria", accessory.between(152f, 153f, 155f));
        assertFalse("Integer value between criteria", accessory.between(52, 53, 55));
        assertFalse("String value between criteria", accessory.between(TEST_FIELD_0, "TEST_FIELD_1", "TEST_FIELD_2"));
        // act and assert - higher upper searched value
        assertFalse("Double value between criteria", accessory.between(1.3, 1.0, 1.2));
        assertFalse("Float value between criteria", accessory.between(156f, 153f, 155f));
        assertFalse("Integer value between criteria", accessory.between(56, 53, 55));
        assertFalse("String value between criteria", accessory.between(TEST_FIELD_2, "TEST_FIELD_0", "TEST_FIELD_1"));
    }

    @Test(expected = IncompatibleClassException.class)
    public void betweenTypeErrors() {
        // act and assert
        // --> object 1
        accessory.between(1, "2", "3");
        // --> object 2
        accessory.between("1", 2, "3");
        // --> object 3
        accessory.between("1", "2", 3);
    }

    @Test(expected = NotImplementedException.class)
    public void betweenNotImplemented() {
        accessory.between(Hardness.S, Hardness.H, Hardness.S);
    }

    @Test
    public void cloneElement() {
        // act
        String result = accessory.cloneElement(TEST_FIELD_0);
        // assert
        assertEquals("wrong cloned element received", TEST_FIELD_0, result);
    }

    @Test
    public void deleteByElement() {
        // act
        List<String> result = accessory.delete(new ArrayList<>(), "");
        // assert
        assertEquals("invalid return value", TEST_ELEMENT, result.get(0));
    }

    @Test
    public void deleteByPrimaryKey() {
        // act
        String result = accessory.delete(new ArrayList<>(), 1);
        // assert
        assertEquals("invalid return value", TEST_ELEMENT, result);
    }

    @Test
    public void deleteBySearchCriteria() throws IOException {
        // act
        List<String> result = accessory.delete(new ArrayList<>(),
                new ArrayList<>());
        // assert
        assertFalse("empty list received", result.isEmpty());
        assertEquals("invalid return value", TEST_ELEMENT, result.get(0));
    }

    @Test
    public void equalFound() {
        // act and assert
        assertTrue("Double values not equal", accessory.equal(1.1, 1.1));
        assertTrue("Float values not equal", accessory.equal(154f, 154f));
        assertTrue("Hardness values not equal", accessory.equal(Hardness.UNDEF, Hardness.UNDEF));
        assertTrue("Integer values not equal", accessory.equal(54, 54));
        assertTrue("String values not equal", accessory.equal(TEST_FIELD_1, "TEST_FIELD_1"));
    }

    @Test
    public void equalNotFound() {
        // act and assert
        assertFalse("Double values equal", accessory.equal(1.1, 1.01));
        assertFalse("Float values equal", accessory.equal(154f, 153f));
        assertFalse("Hardness values equal", accessory.equal(Hardness.UNDEF, Hardness.M));
        assertFalse("Integer values equal", accessory.equal(54, 55));
        assertFalse("String values equal", accessory.equal(TEST_FIELD_1, "TEST_FIELD_2"));
    }

    @Test(expected = IncompatibleClassException.class)
    public void equalTypeError() {
        // act and assert
        accessory.equal("", 1);
    }

    @Test
    public void fetch() {
        // act
        List<String> result = accessory.fetch(new ArrayList<>(),
                1,
                DataAccessoryComparisonValues.EQ,
                "");
        // assert
        assertEquals("invalid return value", TEST_ELEMENT, result.get(0));
    }

    @Test
    public void getFieldsToNumberedString() {
        // act
        String result = accessory.getFieldsToNumberedString();
        // assert
        assertTrue("field name not found", result.contains(TEST_FIELD_1));
        assertTrue("field name not found", result.contains(TEST_FIELD_2));
    }

    @Test
    public void insert() {
        // act
        boolean result = accessory.insert(new ArrayList<>(), "", true);
        // assert
        assertFalse("invalid return value", result);
    }

    @Test
    public void isConditionComplied() {
        for (DataAccessoryComparisonValues value : DataAccessoryComparisonValues.values()) {
            try {
                accessory.isConditionComplied("", value, "", "");
            } catch (NotImplementedException e) {
                fail(String.format("comparisonValue failure found: %s", e.getMessage()));
            }
        }
    }

    @Test
    public void likeFound() {
        // act and assert - contain data
        assertTrue("String value not found", accessory.like(TEST_FIELD_1, "IEL"));
        assertTrue("StringBuilder value not found", accessory.like(new StringBuilder(TEST_FIELD_1),
                new StringBuilder("%EST_FIELD%")));
        assertTrue("StringBuffer value not found", accessory.like(new StringBuffer(TEST_FIELD_1),
                new StringBuffer("%EST_F%")));
        // act and assert - starts with data
        assertTrue("String value not found", accessory.like(TEST_FIELD_1, "TEST_F%"));
        assertTrue("StringBuilder value not found", accessory.like(new StringBuilder(TEST_FIELD_1),
                new StringBuilder("TEST_FI%")));
        assertTrue("StringBuffer value not found", accessory.like(new StringBuffer(TEST_FIELD_1),
                new StringBuffer("TEST_FIE%")));
        // act and assert - ends with data
        assertTrue("String value not found", accessory.like(TEST_FIELD_1, "%IELD_1"));
        assertTrue("StringBuilder value not found", accessory.like(new StringBuilder(TEST_FIELD_1),
                new StringBuilder("%FIELD_1")));
        assertTrue("StringBuffer value not found", accessory.like(new StringBuffer(TEST_FIELD_1),
                new StringBuffer("%ELD_1")));
    }

    @Test
    public void likeNotFound() {
        // act and assert - doesn't contain data
        assertFalse("String value found", accessory.like(TEST_FIELD_1, "IELX"));
        assertFalse("StringBuilder value found", accessory.like(new StringBuilder(TEST_FIELD_1),
                new StringBuilder("%EST_FIELX%")));
        assertFalse("StringBuffer value found", accessory.like(new StringBuffer(TEST_FIELD_1),
                new StringBuffer("%EST_FX%")));
        // act and assert - doesn't start with data
        assertFalse("String value found", accessory.like(TEST_FIELD_1, "%TEST_FX"));
        assertFalse("StringBuilder value found", accessory.like(new StringBuilder(TEST_FIELD_1),
                new StringBuilder("%TEST_FX")));
        assertFalse("StringBuffer value found", accessory.like(new StringBuffer(TEST_FIELD_1),
                new StringBuffer("%TEST_FIX")));
        // act and assert - doesn't end with data
        assertFalse("String value found", accessory.like(TEST_FIELD_1, "IELD_2%"));
        assertFalse("StringBuilder value found", accessory.like(new StringBuilder(TEST_FIELD_1),
                new StringBuilder("FIELD_2%")));
        assertFalse("StringBuffer value found", accessory.like(new StringBuffer(TEST_FIELD_1),
                new StringBuffer("ELD_2%")));
    }

    @Test(expected = IncompatibleClassException.class)
    public void likeTypeError() {
        // act and assert
        accessory.like("", 1);
    }

    @Test
    public void notBetweenFound() {
        // act and assert - smaller lower searched value
        assertTrue("Double value between criteria", accessory.notBetween(0.9, 1.0, 1.2));
        assertTrue("Float value between criteria", accessory.notBetween(152f, 153f, 155f));
        assertTrue("Integer value between criteria", accessory.notBetween(52, 53, 55));
        assertTrue("String value between criteria", accessory.notBetween(TEST_FIELD_0, "TEST_FIELD_1", "TEST_FIELD_2"));
        // act and assert - higher upper searched value
        assertTrue("Double value between criteria", accessory.notBetween(1.3, 1.0, 1.2));
        assertTrue("Float value between criteria", accessory.notBetween(156f, 153f, 155f));
        assertTrue("Integer value between criteria", accessory.notBetween(56, 53, 55));
        assertTrue("String value between criteria", accessory.notBetween(TEST_FIELD_2, "TEST_FIELD_0", "TEST_FIELD_1"));
    }

    @Test
    public void notBetweenNotFound() {
        // act and assert - inside searched values
        assertFalse("Double value not between criteria", accessory.notBetween(1.1, 1.0, 1.2));
        assertFalse("Float value not between criteria", accessory.notBetween(154f, 153f, 155f));
        assertFalse("Integer value not between criteria", accessory.notBetween(54, 53, 55));
        assertFalse("String value not between criteria", accessory.notBetween(TEST_FIELD_1, "TEST_FIELD_0", "TEST_FIELD_2"));
        // act and assert - equal lower searched value
        assertFalse("Double value not between criteria", accessory.notBetween(1.0, 1.0, 1.2));
        assertFalse("Float value not between criteria", accessory.notBetween(153f, 153f, 155f));
        assertFalse("Integer value not between criteria", accessory.notBetween(53, 53, 55));
        assertFalse("String value not between criteria", accessory.notBetween(TEST_FIELD_0, "TEST_FIELD_0", "TEST_FIELD_2"));
        // act and assert - equal upper searched values
        assertFalse("Double value not between criteria", accessory.notBetween(1.2, 1.0, 1.2));
        assertFalse("Float value not between criteria", accessory.notBetween(155f, 153f, 155f));
        assertFalse("Integer value not between criteria", accessory.notBetween(55, 53, 55));
        assertFalse("String value not between criteria", accessory.notBetween(TEST_FIELD_2, "TEST_FIELD_0", "TEST_FIELD_2"));
    }

    @Test(expected = IncompatibleClassException.class)
    public void notBetweenTypeErrors() {
        // act and assert
        // --> object 1
        accessory.notBetween(1, "2", "3");
        // --> object 2
        accessory.notBetween("1", 2, "3");
        // --> object 3
        accessory.notBetween("1", "2", 3);
    }

    @Test(expected = NotImplementedException.class)
    public void notBetweenNotImplemented() {
        accessory.notBetween(Hardness.S, Hardness.H, Hardness.S);
    }

    @Test
    public void notEqualFound() {
        // act and assert
        assertTrue("Double values equal", accessory.notEqual(1.1, 1.01));
        assertTrue("Float values equal", accessory.notEqual(154f, 153f));
        assertTrue("Hardness values equal", accessory.notEqual(Hardness.UNDEF, Hardness.M));
        assertTrue("Integer values equal", accessory.notEqual(54, 55));
        assertTrue("String values equal", accessory.notEqual(TEST_FIELD_1, "TEST_FIELD_2"));
    }

    @Test
    public void notEqualNotFound() {
        // act and assert
        assertFalse("Double values not equal", accessory.notEqual(1.1, 1.1));
        assertFalse("Float values not equal", accessory.notEqual(154f, 154f));
        assertFalse("Hardness values not equal", accessory.notEqual(Hardness.UNDEF, Hardness.UNDEF));
        assertFalse("Integer values not equal", accessory.notEqual(54, 54));
        assertFalse("String values not equal", accessory.notEqual(TEST_FIELD_1, "TEST_FIELD_1"));
    }

    @Test(expected = IncompatibleClassException.class)
    public void notEqualTypeError() {
        // act and assert
        accessory.notEqual("", 1);
    }

    @Test
    public void notLikeFound() {
        // act and assert - doesn't contain data
        assertTrue("String value found", accessory.notLike(TEST_FIELD_1, "IELX"));
        assertTrue("StringBuilder value found", accessory.notLike(new StringBuilder(TEST_FIELD_1),
                new StringBuilder("%EST_FIELX%")));
        assertTrue("StringBuffer value found", accessory.notLike(new StringBuffer(TEST_FIELD_1),
                new StringBuffer("%EST_FX%")));
        // act and assert - doesn't start with data
        assertTrue("String value found", accessory.notLike(TEST_FIELD_1, "%TEST_FX"));
        assertTrue("StringBuilder value found", accessory.notLike(new StringBuilder(TEST_FIELD_1),
                new StringBuilder("%TEST_FX")));
        assertTrue("StringBuffer value found", accessory.notLike(new StringBuffer(TEST_FIELD_1),
                new StringBuffer("%TEST_FIX")));
        // act and assert - doesn't end with data
        assertTrue("String value found", accessory.notLike(TEST_FIELD_1, "IELD_2%"));
        assertTrue("StringBuilder value found", accessory.notLike(new StringBuilder(TEST_FIELD_1),
                new StringBuilder("FIELD_2%")));
        assertTrue("StringBuffer value found", accessory.notLike(new StringBuffer(TEST_FIELD_1),
                new StringBuffer("ELD_2%")));
    }

    @Test
    public void notLikeNotFound() {
        // act and assert - contain data
        assertFalse("String value not found", accessory.notLike(TEST_FIELD_1, "IEL"));
        assertFalse("StringBuilder value not found", accessory.notLike(new StringBuilder(TEST_FIELD_1),
                new StringBuilder("%EST_FIELD%")));
        assertFalse("StringBuffer value not found", accessory.notLike(new StringBuffer(TEST_FIELD_1),
                new StringBuffer("%EST_F%")));
        // act and assert - starts with data
        assertFalse("String value not found", accessory.notLike(TEST_FIELD_1, "TEST_F%"));
        assertFalse("StringBuilder value not found", accessory.notLike(new StringBuilder(TEST_FIELD_1),
                new StringBuilder("TEST_FI%")));
        assertFalse("StringBuffer value not found", accessory.notLike(new StringBuffer(TEST_FIELD_1),
                new StringBuffer("TEST_FIE%")));
        // act and assert - ends with data
        assertFalse("String value not found", accessory.notLike(TEST_FIELD_1, "%IELD_1"));
        assertFalse("StringBuilder value not found", accessory.notLike(new StringBuilder(TEST_FIELD_1),
                new StringBuilder("%FIELD_1")));
        assertFalse("StringBuffer value not found", accessory.notLike(new StringBuffer(TEST_FIELD_1),
                new StringBuffer("%ELD_1")));
    }

    @Test(expected = IncompatibleClassException.class)
    public void notLikeTypeError() {
        // act and assert
        accessory.notLike("", 1);
    }

    @Test
    public void select() {
        // act
        String result = accessory.select(new ArrayList<>(),
                1,
                DataAccessoryComparisonValues.EQ,
                "");
        // assert
        assertEquals("invalid return value", TEST_ELEMENT, result);
    }

    @Test
    public void update() {
        // act
        Pair<String, String> result = accessory.update(new ArrayList<>(), "");
        // assert
        assertEquals("invalid old return value", "1", result.getLeft());
        assertEquals("invalid new return value", "2", result.getRight());
    }

    private static class TestClass extends AbstractDataAccessory<String> {

        protected TestClass(Field[] fields) {
            super(fields);
        }

        /**
         * Creates a new copy o an element.
         *
         * @param element the element, which has to be copied
         * @return the new copy of the element
         */
        @Override
        protected String cloneElement(String element) {
            return new String(element);
        }

        /**
         * Deletes an element inside the list, defined by primaryKey.
         *
         * @param list       the list with all the characteristic
         * @param primaryKey the key of the element which has to be deleted
         * @return the element which has be deleted when the primary key was found, otherwise returns null
         */
        @Override
        protected String delete(List<String> list,
                                Integer primaryKey) {
            return TEST_ELEMENT;
        }

        /**
         * Deletes the element inside the list, defined by primaryKey of the element.
         *
         * @param list    the list with all the characteristic
         * @param element the element which has to be deleted
         * @return the element which has be deleted when the primary key was found, otherwise returns null
         */
        @Override
        protected List<String> delete(List<String> list,
                                      String element) {
            List<String> result = new ArrayList<>();
            result.add(TEST_ELEMENT);
            return result;
        }

        /**
         * Deletes the element inside the list, defined by a list of some elements.
         *
         * @param list         the list with all the characteristic
         * @param listToDelete the list with all the characteristic which are to be delete
         * @return
         */
        @Override
        protected List<String> delete(List<String> list, List<String> listToDelete) throws IOException {
            List<String> result = new ArrayList<>();
            result.add(TEST_ELEMENT);
            return result;
        }

        /**
         * Inserts the new element into the list.<br>
         * The primary key will be set at the insertion time of the element creation.
         *
         * @param list    the list with all the characteristic
         * @param element the element which has to be inserted
         * @return true when insert was successfully, otherwise false
         */
        @Override
        protected boolean insert(List<String> list,
                                 String element,
                                 boolean checkForClearness) {
            return false;
        }

        /**
         * Updates the element inside the list, defined by primaryKey of the element.
         *
         * @param list    the list with all the characteristic
         * @param element the element which has to be updated
         * @return a Pair with the old element on the left side and the new element on the riht side
         */
        @Override
        protected Pair<String, String> update(List<String> list, String element) {
            return Pair.of("1", "2");
        }

        /**
         * Returns a subset of elements inside the list, depending on several search criteria
         *
         * @param list            the list with all the characteristic
         * @param field           the number of the field inside en element
         * @param comparisonValue a logical criteria to search
         * @param value           search values
         * @return a list with a size depending on the found results
         */
        @Override
        protected List<String> fetch(List<String> list,
                                     Integer field,
                                     DataAccessoryComparisonValues comparisonValue,
                                     Object... value) {
            List<String> values = new ArrayList<>();
            values.add(TEST_ELEMENT);
            return values;
        }

        /**
         * Returns one element from the list, depending on several search criteria
         *
         * @param list            the list with all the characteristic
         * @param field           the number of the field inside en element
         * @param comparisonValue a logical criteria to search
         * @param value           search values
         * @return one element when only one element is found, otherwise null
         */
        @Override
        protected String select(List<String> list,
                                Integer field,
                                DataAccessoryComparisonValues comparisonValue,
                                Object... value) {
            return TEST_ELEMENT;
        }
    }
}