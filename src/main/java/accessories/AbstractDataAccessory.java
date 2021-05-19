package accessories;

import enumerations.DataAccessoryComparisonValues;
import exceptions.IncompatibleClassException;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Abstract definitions of all needed data accessories of crazy golf application.<br>
 * These are ...
 * <ul>
 *     <li>accessory for the list of {@link dataStructures.BallCharacteristicsImpl}</li>
 *     <li>accessory for the list of {@link dataStructures.CrazyGolfSiteCharacteristicsImpl}</li>
 *     <li>accessory for the list of {@link dataStructures.SuitCaseCharacteristicsImpl}</li>
 * </ul>
 */
public abstract class AbstractDataAccessory<T> {

    private final static String LIKE_WILD_CARD = "%";

    private final Field[] fields;

    protected AbstractDataAccessory(Field[] fields) {
        this.fields = fields;
    }

    /**
     * Creates a new copy o an element.
     *
     * @param element the element, which has to be copied
     * @return the new copy of the element
     */
    protected abstract T cloneElement(T element);

    /**
     * Deletes an element inside the list, defined by primaryKey.
     *
     * @param list       the list with all the characteristic
     * @param primaryKey the key of the element which has to be deleted
     * @return the element which has be deleted when the primary key was found, otherwise returns null
     */
    protected abstract T delete(List<T> list, Integer primaryKey);

    /**
     * Deletes the element inside the list, defined by an element.<br>
     * The equals-Method of the object is used for the correct decision to delete.
     *
     * @param list    the list with all the characteristic
     * @param element the element which has to be deleted
     * @return a list of elements which are deleted when the element values were found, otherwise an empty list
     */
    protected abstract List<T> delete(List<T> list, T element);

    /**
     * Deletes the element inside the list, defined by a list of some elements.
     *
     * @param list         the list with all the characteristic
     * @param listToDelete the list with all the characteristic which are to be delete
     * @return
     */
    protected abstract List<T> delete(List<T> list,
                                      List<T> listToDelete) throws IOException;

    /**
     * Inserts the new element into the list.<br>
     * The primary key will be set at the insertion time of the element creation.
     *
     * @param list    the list with all the characteristic
     * @param element the element which has to be inserted
     * @return true when insert was successfully, otherwise false
     */
    protected abstract boolean insert(List<T> list, T element, boolean checkForClearness);

    /**
     * Updates the element inside the list, defined by primaryKey of the element.
     *
     * @param list    the list with all the characteristic
     * @param element the element which has to be updated
     * @return a Pair with the old element on the left side and the new element on the riht side
     */
    protected abstract Pair<T, T> update(List<T> list, T element);

    /**
     * Returns a subset of elements inside the list, depending on several search criteria
     *
     * @param list            the list with all the characteristic
     * @param field           the number of the field inside en element
     * @param comparisonValue a logical criteria to search
     * @param value           search values
     * @return a list with a size depending on the found results
     */
    protected abstract List<T> fetch(List<T> list,
                                     Integer field,
                                     DataAccessoryComparisonValues comparisonValue,
                                     Object... value) throws IOException;

    /**
     * Returns one element from the list, depending on several search criteria
     *
     * @param list            the list with all the characteristic
     * @param field           the number of the field inside en element
     * @param comparisonValue a logical criteria to search
     * @param value           search values
     * @return one element when only one element is found, otherwise null
     */
    protected abstract T select(List<T> list,
                                Integer field,
                                DataAccessoryComparisonValues comparisonValue,
                                Object... value) throws IOException;

    public Field[] getFields() {
        return fields;
    }

    /**
     * Returns, if the value is equal of one or between both search values.<br>
     * <b>NOTE</b>: When the types of the objects are not compatible a {@link exceptions.IncompatibleClassException}
     * is thrown.<br>
     * Actually are following classes implemented:
     * <ul>
     *     <li>{@link String}</li>
     *     <li>{@link Double}</li>
     *     <li>{@link Float}</li>
     *     <li>{@link Integer}</li>
     * </ul>
     * <b>NOTE</b>: When a not implemented class is used a {@link NotImplementedException} is thrown.<br>
     *
     * @param dataValue          the value from a checked field
     * @param lowerSearchedValue the lower value which has to be search
     * @param upperSearchedValue the upper value which has to be search
     * @return true if value is equal of one or between both search values, otherwise false
     */
    public boolean between(Object dataValue,
                           Object lowerSearchedValue,
                           Object upperSearchedValue) {
        classCompatibilityCheck(dataValue, lowerSearchedValue);
        classCompatibilityCheck(dataValue, upperSearchedValue);
        if (dataValue instanceof String) {
            int resultLower = StringUtils.compare((String) dataValue, (String) lowerSearchedValue);
            int resultUpper = StringUtils.compare((String) dataValue, (String) upperSearchedValue);
            return (resultLower == 0 || resultUpper == 0
                    || (resultLower > 0 && resultUpper < 0));
        }
        if (dataValue instanceof Double) {
            int resultLower = Double.compare((Double) dataValue, (Double) lowerSearchedValue);
            int resultUpper = Double.compare((Double) dataValue, (Double) upperSearchedValue);
            return (resultLower == 0 || resultUpper == 0
                    || (resultLower > 0 && resultUpper < 0));
        }
        if (dataValue instanceof Float) {
            int resultLower = Float.compare((Float) dataValue, (Float) lowerSearchedValue);
            int resultUpper = Float.compare((Float) dataValue, (Float) upperSearchedValue);
            return (resultLower == 0 || resultUpper == 0
                    || (resultLower > 0 && resultUpper < 0));
        }
        if (dataValue instanceof Integer) {
            int resultLower = Integer.compare((Integer) dataValue, (Integer) lowerSearchedValue);
            int resultUpper = Integer.compare((Integer) dataValue, (Integer) upperSearchedValue);
            return (resultLower == 0 || resultUpper == 0
                    || (resultLower > 0 && resultUpper < 0));
        }
        // function for class not implemented !!!
        throw new NotImplementedException(String.format("between check for class %s not implemented",
                dataValue.getClass().getName()));
    }

    /**
     * Returns, if the values are equal or not.<br>
     * <b>NOTE</b>: When the types of the objects are not compatible a {@link exceptions.IncompatibleClassException}
     * is thrown.
     *
     * @param dataValue     the value from a checked field
     * @param searchedValue the value which has to be search
     * @return true if the values are equal, otherwise false
     */
    public boolean equal(Object dataValue, Object searchedValue) {
        classCompatibilityCheck(dataValue, searchedValue);
        return dataValue.equals(searchedValue);
    }

    /**
     * Returns a text string of all fields with numbered positions.<br>
     * The syntax of one line is like ...<br>
     * <code>1 - primaryKey</code>
     *
     * @return the string of the created buffer
     */
    public String getFieldsToNumberedString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < fields.length; i++) {
            if (buffer.length() > 0) {
                buffer.append("\n");
            }
            buffer.append(i + 1).append(" - ").append(fields[i].getName());
        }
        return buffer.toString();
    }

    /**
     * This is the central access point for all comparison functions, which are ...
     * <ul>
     *     <li>{@link #between(Object, Object, Object)}</li>
     *     <li>{@link #equal(Object, Object)}</li>
     *     <li>{@link #like(Object, Object)}</li>
     *     <li>{@link #notBetween(Object, Object, Object)}</li>
     *     <li>{@link #notEqual(Object, Object)}</li>
     *     <li>{@link #notLike(Object, Object)}</li>
     * </ul>
     * <b>NOTE</b>: When a not implemented {@link DataAccessoryComparisonValues} is used a
     * {@link NotImplementedException} is thrown.<br>
     *
     * @param dataValue
     * @param comparison
     * @param searchedValues
     * @return
     */
    public boolean isConditionComplied(Object dataValue,
                                       DataAccessoryComparisonValues comparison,
                                       Object... searchedValues) {
        switch (comparison) {
            case BW:
                return between(dataValue, searchedValues[0], searchedValues[1]);
            case EQ:
                return equal(dataValue, searchedValues[0]);
            case LK:
                return like(dataValue, searchedValues[0]);
            case NB:
                return notBetween(dataValue, searchedValues[0], searchedValues[1]);
            case NE:
                return notEqual(dataValue, searchedValues[0]);
            case NL:
                return notLike(dataValue, searchedValues[0]);
            default:
                // function for class not implemented !!!
                throw new NotImplementedException(String.format("comparisonValue %s not implemented",
                        comparison));
        }
    }

    /**
     * Returns, if the dataValue contains the searchedValue or not.<br>
     * <b>NOTE</b>: When the types of the objects are not compatible a {@link exceptions.IncompatibleClassException}
     * is thrown.
     * Actually are following classes implemented:
     * <ul>
     *     <li>{@link String}</li>
     *     <li>{@link StringBuilder}</li>
     *     <li>{@link StringBuilder}</li>
     * </ul>
     * <b>NOTE</b>: When a not implemented class is used a {@link NotImplementedException} is thrown.<br>
     *
     * @param dataValue     the value from a checked field
     * @param searchedValue the value which has to be search
     * @return true if the dataValue contains the searchedValue, otherwise false
     */
    public boolean like(Object dataValue, Object searchedValue) {
        classCompatibilityCheck(dataValue, searchedValue);
        String localDataValue;
        String localSearchedValue;
        if (dataValue instanceof StringBuilder) {
            localDataValue = ((StringBuilder) dataValue).toString();
            localSearchedValue = ((StringBuilder) searchedValue).toString();
        } else if (dataValue instanceof StringBuffer) {
            localDataValue = ((StringBuffer) dataValue).toString();
            localSearchedValue = ((StringBuffer) searchedValue).toString();
        } else if (dataValue instanceof String) {
            localDataValue = ((String) dataValue);
            localSearchedValue = ((String) searchedValue);
        } else {
            // function for class not implemented !!!
            throw new NotImplementedException(String.format("between check for class %s not implemented",
                    dataValue.getClass().getName()));
        }
        if ((localSearchedValue.startsWith(LIKE_WILD_CARD)
                && localSearchedValue.endsWith(LIKE_WILD_CARD))
                || (!localSearchedValue.startsWith(LIKE_WILD_CARD)
                && !localSearchedValue.endsWith(LIKE_WILD_CARD))) {
            localSearchedValue = localSearchedValue.replaceAll(LIKE_WILD_CARD, "");
            return localDataValue.contains(localSearchedValue);
        } else if (localSearchedValue.endsWith(LIKE_WILD_CARD)) {
            localSearchedValue = localSearchedValue.replaceAll(LIKE_WILD_CARD, "");
            return (localDataValue.startsWith(localSearchedValue));
        } else {
            localSearchedValue = localSearchedValue.replaceAll(LIKE_WILD_CARD, "");
            return (localDataValue.endsWith(localSearchedValue));
        }
    }

    /**
     * Returns, if the value is not equal of one and not between both search values.<br>
     * <b>NOTE</b>: When the types of the objects are not compatible a {@link exceptions.IncompatibleClassException}
     * are thrown.
     * Actually are following classes implemented:
     * <ul>
     *     <li>{@link String}</li>
     *     <li>{@link Double}</li>
     *     <li>{@link Float}</li>
     *     <li>{@link Integer}</li>
     * </ul>
     * <b>NOTE</b>: When a not implemented class is used a {@link NotImplementedException} is thrown.<br>
     *
     * @param dataValue          the value from a checked field
     * @param lowerSearchedValue the lower value which has to be search
     * @param upperSearchedValue the upper value which has to be search
     * @return true if value is not equal of one and not between both search values, otherwise false
     */
    public boolean notBetween(Object dataValue,
                              Object lowerSearchedValue,
                              Object upperSearchedValue) {
        return !between(dataValue, lowerSearchedValue, upperSearchedValue);
    }

    /**
     * Returns, if the values are not equal or still it does.<br>
     * <b>NOTE</b>: When the types of the objects are not compatible a {@link exceptions.IncompatibleClassException}
     * is thrown.
     *
     * @param dataValue     the value from a checked field
     * @param searchedValue the value which has to be search
     * @return true if the values are not equal, otherwise false
     */
    public boolean notEqual(Object dataValue, Object searchedValue) {
        return !equal(dataValue, searchedValue);
    }

    /**
     * Returns, if the dataValue does not contain the searchedValue or still it does.<br>
     * <b>NOTE</b>: When the types of the objects are not compatible a {@link exceptions.IncompatibleClassException}
     * is thrown.
     * Actually are following classes implemented:
     * <ul>
     *     <li>{@link String}</li>
     *     <li>{@link StringBuilder}</li>
     *     <li>{@link StringBuilder}</li>
     * </ul>
     * <b>NOTE</b>: When a not implemented class is used a {@link NotImplementedException} is thrown.<br>
     *
     * @param dataValue     the value from a checked field
     * @param searchedValue the value which has to be search
     * @return true if the dataValue does not contain the searchedValue, otherwise false
     */
    public boolean notLike(Object dataValue, Object searchedValue) {
        return !like(dataValue, searchedValue);
    }

    /**
     * Checks each element against each other.
     *
     * @param elements
     */
    private void classCompatibilityCheck(Object... elements) {
        if (elements.length == 1) {
            return;
        }
        for (int i = 0; i < elements.length - 1; i++) {
            for (int j = i + 1; j < elements.length; j++) {
                if (!elements[i].getClass().equals(elements[j].getClass())) {
                    throw new IncompatibleClassException(String.format("Incompatible classes found. Class 1: %s, class 2: %s",
                            elements[i].getClass(),
                            elements[j].getClass()));
                }
            }
        }
    }

}
