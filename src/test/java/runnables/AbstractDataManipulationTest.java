package runnables;

import abstracts.AbstractPlainJava;
import dataStructures.CommonValues;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JUnit test for {@link AbstractDataManipulation}.
 */
public class AbstractDataManipulationTest extends AbstractPlainJava {

    private final int initialNumberOfEntries = 7;
    private final List<String> testCharacteristics = new ArrayList<>();

    private final String insertValue = "insertString";
    private final int updatePosition = 3;
    private final String updateValue = "updateString";
    private final int listSize = 2;
    private final String listValue = "listString";
    private final String selectValue = "selectString";

    private DataManipulator manipulator;

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < initialNumberOfEntries; i++) {
            testCharacteristics.add("oldString" + i);
        }
        manipulator = new DataManipulator(testCharacteristics);
    }

    @Test
    public void deleteCharacteristic() {
        // act
        manipulator.deleteCharacteristic();
        // assert
        assertEquals("invalid size found",
                initialNumberOfEntries - 1,
                testCharacteristics.size());
    }

    @Test
    public void insertCharacteristic() {
        // act
        manipulator.insertCharacteristic();
        // assert
        assertEquals("invalid size found",
                initialNumberOfEntries + 1,
                testCharacteristics.size());
        assertTrue("expected data not found",
                testCharacteristics.contains(insertValue));
    }

    @Test
    public void listCharacteristics() {
        // act
        manipulator.listCharacteristics();
        // assert
        assertEquals("invalid size found",
                listSize,
                manipulator.getResultCharacteristics().size());
    }

    @Test
    public void selectCharacteristic() {
        // act
        manipulator.selectCharacteristic();
        // assert
        assertEquals("invalid data found",
                selectValue,
                manipulator.getResultCharacteristic());
    }

    @Test
    public void updateCharacteristic() {
        // act
        manipulator.updateCharacteristic();
        // assert
        assertEquals("invalid size found",
                initialNumberOfEntries,
                testCharacteristics.size());
        assertTrue("expected data not found",
                testCharacteristics.contains(updateValue));
        assertEquals("expected data not in correct position",
                updateValue,
                testCharacteristics.get(updatePosition));
    }

    class DataManipulator extends AbstractDataManipulation<String> {

        public DataManipulator(List<String> characteristics) {
            super(characteristics);
        }

        @Override
        protected void deleteCharacteristic() {
            getCharacteristics().remove(0);
        }

        @Override
        protected void insertCharacteristic() {
            getCharacteristics().add(insertValue);
        }

        @Override
        protected void listCharacteristics() {
            for (int i = 0; i < listSize; i++) {
                getCharacteristics().add(listValue);
            }
            getResultCharacteristics().addAll(getCharacteristics().stream()
                    .filter(o -> o.equals(listValue))
                    .collect(Collectors.toList()));
        }

        @Override
        protected void selectCharacteristic() {
            getCharacteristics().add(selectValue);
            setResultCharacteristic(getCharacteristics().stream()
                    .filter(o -> o.equals(selectValue))
                    .collect(Collectors.joining()));
        }

        @Override
        protected void updateCharacteristic() {
            getCharacteristics().remove(updatePosition);
            getCharacteristics().add(updatePosition, updateValue);
        }
    }
}