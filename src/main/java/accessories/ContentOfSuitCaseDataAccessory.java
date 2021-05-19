package accessories;

import dataStructures.ContentOfSuitCaseImpl;
import enumerations.DataAccessoryComparisonValues;
import org.apache.commons.lang3.tuple.Pair;
import utilities.ApplicationLoggerUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link AbstractDataAccessory} for data of {@link ContentOfSuitCaseImpl}.
 */
public class ContentOfSuitCaseDataAccessory extends AbstractDataAccessory<ContentOfSuitCaseImpl> {

    private static final ApplicationLoggerUtil logger =
            new ApplicationLoggerUtil(ContentOfSuitCaseDataAccessory.class);

    protected ContentOfSuitCaseDataAccessory() {
        super(ContentOfSuitCaseImpl.class.getDeclaredFields());
    }

    /**
     * Creates a new copy o an element.
     *
     * @param element the element, which has to be copied
     * @return the new copy of the element
     */
    @Override
    protected ContentOfSuitCaseImpl cloneElement(ContentOfSuitCaseImpl element) {
        return new ContentOfSuitCaseImpl(element.getPrimaryKey(),
                element.getForeignKeyBall());
    }

    /**
     * Deletes an element inside the list, defined by primaryKey.
     *
     * @param list       the list with all the characteristic
     * @param primaryKey the key of the element which has to be deleted
     * @return the element which has be deleted when the primary key was found, otherwise returns null
     */
    @Override
    protected ContentOfSuitCaseImpl delete(List<ContentOfSuitCaseImpl> list,
                                           Integer primaryKey) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPrimaryKey().equals(primaryKey)) {
                return list.remove(i);
            }
        }
        return null;
    }

    /**
     * Deletes the element inside the list, defined by an element.<br>
     * The equals-Method of the object is used for the correct decision to delete.
     *
     * @param list    the list with all the characteristic
     * @param element the element which has to be deleted
     * @return a list of elements which are deleted when the element values were found, otherwise an empty list
     */
    @Override
    protected List<ContentOfSuitCaseImpl> delete(List<ContentOfSuitCaseImpl> list,
                                                 ContentOfSuitCaseImpl element) {
        List<ContentOfSuitCaseImpl> result = new ArrayList<>();
        for (ContentOfSuitCaseImpl ballCharacteristics : list) {
            if (ballCharacteristics.equals(element)) {
                result.add(ballCharacteristics);
            }
        }
        if (list.removeAll(result)) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Deletes the element inside the list, defined by a list of some elements.
     *
     * @param list         the list with all the characteristic
     * @param listToDelete the list with all the characteristic which are to be delete
     * @return
     */
    @Override
    protected List<ContentOfSuitCaseImpl> delete(List<ContentOfSuitCaseImpl> list,
                                                 List<ContentOfSuitCaseImpl> listToDelete)
            throws IOException {
        if (list.removeAll(listToDelete)) {
            return listToDelete;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Inserts the new element into the list.<br>
     * The primary key will be set at the insertion time of the element creation.
     *
     * @param list              the list with all the characteristic
     * @param element           the element which has to be inserted
     * @param checkForClearness
     * @return true when insert was successfully, otherwise false
     */
    @Override
    protected boolean insert(List<ContentOfSuitCaseImpl> list,
                             ContentOfSuitCaseImpl element,
                             boolean checkForClearness) {
        if (checkForClearness) {
            for (ContentOfSuitCaseImpl entry : list) {
                if (entry.equals(element)) {
                    return false;
                }
            }
        }
        element.setPrimaryKey(ContentOfSuitCaseImpl.getNextPrimaryKey(list));
        return list.add(element);
    }

    /**
     * Updates the element inside the list, defined by primaryKey of the element.
     *
     * @param list       the list with all the characteristic
     * @param newElement the element which has to be updated
     * @return a Pair with the old element on the left side and the new element on the riht side
     */
    @Override
    protected Pair<ContentOfSuitCaseImpl, ContentOfSuitCaseImpl> update(List<ContentOfSuitCaseImpl> list,
                                                                        ContentOfSuitCaseImpl newElement) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPrimaryKey().equals(newElement.getPrimaryKey())) {
                ContentOfSuitCaseImpl oldElement = list.remove(i);
                list.add(i, newElement);
                return Pair.of(oldElement, newElement);
            }
        }
        return Pair.of(null, newElement);
    }

    /**
     * Returns a subset of elements inside the list, depending on several search criteria
     *
     * @param list       the list with all the characteristic
     * @param field      the number of the field inside en element
     * @param comparison a logical criteria to search
     * @param value      search values
     * @return a list with a size depending on the found results
     */
    @Override
    protected List<ContentOfSuitCaseImpl> fetch(List<ContentOfSuitCaseImpl> list,
                                                Integer field,
                                                DataAccessoryComparisonValues comparison,
                                                Object... value)
            throws IOException {
        List<ContentOfSuitCaseImpl> result = new ArrayList<>();
        for (ContentOfSuitCaseImpl entry : list) {
            switch (field) {
                case 1:
                    if (isConditionComplied(entry.getPrimaryKey(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 2:
                    if (isConditionComplied(entry.getForeignKeyBall(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                default:
                    logger.warn("invalid field index received. Valid are 1 to 2");
                    break;
            }
        }
        return result;
    }

    /**
     * Returns one element from the list, depending on several search criteria
     *
     * @param list       the list with all the characteristic
     * @param field      the number of the field inside en element
     * @param comparison a logical criteria to search
     * @param value      search values
     * @return one element when only one element is found, otherwise null
     */
    @Override
    protected ContentOfSuitCaseImpl select(List<ContentOfSuitCaseImpl> list,
                                           Integer field,
                                           DataAccessoryComparisonValues comparison,
                                           Object... value)
            throws IOException {
        List<ContentOfSuitCaseImpl> result = new ArrayList<>();
        for (ContentOfSuitCaseImpl entry : list) {
            switch (field) {
                case 1:
                    if (isConditionComplied(entry.getPrimaryKey(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 2:
                    if (isConditionComplied(entry.getForeignKeyBall(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                default:
                    logger.warn("invalid field index received. Valid are 1 to 2");
                    break;
            }
        }
        if (result.size() == 1) {
            return result.get(0);
        } else {
            return null;
        }
    }

}
