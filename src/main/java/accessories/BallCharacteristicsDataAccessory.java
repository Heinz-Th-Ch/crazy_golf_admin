package accessories;

import dataStructures.BallCharacteristicsImpl;
import enumerations.DataAccessoryComparisonValues;
import org.apache.commons.lang3.tuple.Pair;
import utilities.ApplicationLoggerUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link AbstractDataAccessory} for data of {@link BallCharacteristicsImpl}.
 */
public class BallCharacteristicsDataAccessory extends AbstractDataAccessory<BallCharacteristicsImpl> {

    private static final ApplicationLoggerUtil logger =
            new ApplicationLoggerUtil(BallCharacteristicsDataAccessory.class);

    public BallCharacteristicsDataAccessory() {
        super(BallCharacteristicsImpl.class.getDeclaredFields());
    }

    /**
     * Creates a new copy o an element.
     *
     * @param element the element, which has to be copied
     * @return the new copy of the element
     */
    @Override
    protected BallCharacteristicsImpl cloneElement(BallCharacteristicsImpl element) {
        return new BallCharacteristicsImpl(element.getPrimaryKey(),
                element.getIdentifier(),
                element.getDescription(),
                element.getHardness(),
                element.getUpThrow(),
                element.getWeight(),
                element.getAngleFactor(),
                element.getComment());
    }

    /**
     * Deletes an element inside the list, defined by primaryKey.
     *
     * @param list       the list with all the characteristic
     * @param primaryKey the key of the element which has to be deleted
     * @return the element which has be deleted when the primary key was found, otherwise returns null
     */
    @Override
    protected BallCharacteristicsImpl delete(List<BallCharacteristicsImpl> list,
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
    protected List<BallCharacteristicsImpl> delete(List<BallCharacteristicsImpl> list,
                                                   BallCharacteristicsImpl element) {
        List<BallCharacteristicsImpl> result = new ArrayList<>();
        for (BallCharacteristicsImpl ballCharacteristics : list) {
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
     * @return a list of elements which are deleted when the element values were found, otherwise an empty list
     */
    @Override
    protected List<BallCharacteristicsImpl> delete(List<BallCharacteristicsImpl> list,
                                                   List<BallCharacteristicsImpl> listToDelete)
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
     * @param list    the list with all the characteristic
     * @param element the element which has to be inserted
     * @return true when insert was successfully, otherwise false
     */
    @Override
    protected boolean insert(List<BallCharacteristicsImpl> list,
                             BallCharacteristicsImpl element,
                             boolean checkForClearness) {
        if (checkForClearness) {
            for (BallCharacteristicsImpl entry : list) {
                if (entry.equals(element)) {
                    return false;
                }
            }
        }
        element.setPrimaryKey(BallCharacteristicsImpl.getNextPrimaryKey(list));
        return list.add(element);
    }

    /**
     * Updates the element inside the list, defined by primaryKey of the element.
     *
     * @param list    the list with all the characteristic
     * @param newElement the element which has to be updated
     * @return a Pair with the old element on the left side and the new element on the riht side
     */
    @Override
    protected Pair<BallCharacteristicsImpl, BallCharacteristicsImpl> update(List<BallCharacteristicsImpl> list,
                                                                            BallCharacteristicsImpl newElement) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPrimaryKey().equals(newElement.getPrimaryKey())) {
                BallCharacteristicsImpl oldElement = list.remove(i);
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
    protected List<BallCharacteristicsImpl> fetch(List<BallCharacteristicsImpl> list,
                                                  Integer field,
                                                  DataAccessoryComparisonValues comparison,
                                                  Object... value)
            throws IOException {
        List<BallCharacteristicsImpl> result = new ArrayList<>();
        for (BallCharacteristicsImpl entry : list) {
            switch (field) {
                case 1:
                    if (isConditionComplied(entry.getPrimaryKey(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 2:
                    if (isConditionComplied(entry.getIdentifier(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 3:
                    if (isConditionComplied(entry.getDescription(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 4:
                    if (isConditionComplied(entry.getHardness(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 5:
                    if (isConditionComplied(entry.getUpThrow(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 6:
                    if (isConditionComplied(entry.getWeight(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 7:
                    if (isConditionComplied(entry.getAngleFactor(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 8:
                    if (isConditionComplied(entry.getComment(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                default:
                    logger.warn("invalid field index received. Valid are 1 to 8");
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
    protected BallCharacteristicsImpl select(List<BallCharacteristicsImpl> list,
                                             Integer field,
                                             DataAccessoryComparisonValues comparison,
                                             Object... value)
            throws IOException {
        List<BallCharacteristicsImpl> result = new ArrayList<>();
        for (BallCharacteristicsImpl entry : list) {
            switch (field) {
                case 1:
                    if (isConditionComplied(entry.getPrimaryKey(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 2:
                    if (isConditionComplied(entry.getIdentifier(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 3:
                    if (isConditionComplied(entry.getDescription(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 4:
                    if (isConditionComplied(entry.getHardness(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 5:
                    if (isConditionComplied(entry.getUpThrow(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 6:
                    if (isConditionComplied(entry.getWeight(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 7:
                    if (isConditionComplied(entry.getAngleFactor(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                case 8:
                    if (isConditionComplied(entry.getComment(), comparison, value)) {
                        result.add(entry);
                    }
                    break;
                default:
                    logger.warn("invalid field index received. Valid are 1 to 8");
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
