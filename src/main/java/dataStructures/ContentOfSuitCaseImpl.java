package dataStructures;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.jetbrains.annotations.VisibleForTesting;

import java.io.Serializable;
import java.util.List;

import static utilities.AssertionUtil.notNull;

/**
 * The implementation of {@link ContentOfSuitCase}.
 * The primary key is {@link ContentOfSuitCaseImpl#primaryKey}, which is corresponds to the position of a ball inside
 * the suitcase. The limit of primary keys contingent on the size of the suitcase(s), defined in the ???.
 * The foreign key is {@link ContentOfSuitCaseImpl#foreignKeyBall} which shows to the primary key in
 * {@link BallCharacteristicsImpl}.
 */
public class ContentOfSuitCaseImpl implements ContentOfSuitCase, Serializable {

    private static final Integer VALUE_FOR_UNDEFINED_NUMBER = -1;

    /**
     * Primary key inside a group of {@link ContentOfSuitCaseImpl}.
     */
    private Integer primaryKey;

    /**
     * Foreign key inside a group of {@link ContentOfSuitCaseImpl} linked to {@link BallCharacteristicsImpl}.
     */
    private Integer foreignKeyBall;

    /**
     * Constructs a new instance.
     *
     * @param primaryKey
     * @param foreignKeyBall
     */
    public ContentOfSuitCaseImpl(Integer primaryKey, Integer foreignKeyBall) {
        initializeValues(primaryKey,
                foreignKeyBall);
    }

    /**
     * Constructs a new instance.
     *
     * @param list
     * @param contentOfSuitcase
     */
    public ContentOfSuitCaseImpl(List<ContentOfSuitCaseImpl> list,
                                 ContentOfSuitCaseImpl contentOfSuitcase) {
        notNull("'list' must not be null", list);
        notNull("'contentOfSuitcase' must not be null", contentOfSuitcase);
        initializeValues(getNextPrimaryKey(list),
                contentOfSuitcase.getForeignKeyBall());
    }

    /**
     * Construct a new instance with primary key set only.
     *
     * @param list
     */
    public ContentOfSuitCaseImpl(List<ContentOfSuitCaseImpl> list) {
        notNull("'list' must not be null", list);
        initializeValues(getNextPrimaryKey(list),
                VALUE_FOR_UNDEFINED_NUMBER);
    }

    /**
     * Evaluates the highest primary key in list and returns the next possible primary key.
     *
     * @param list a group of {@link ContentOfSuitCaseImpl}.
     * @return the new possible primary key.
     */
    public static int getNextPrimaryKey(List<ContentOfSuitCaseImpl> list) {
        int oldPrimaryKey = 0;
        for (ContentOfSuitCaseImpl entry : list) {
            if (entry.getPrimaryKey() > oldPrimaryKey) {
                oldPrimaryKey = entry.getPrimaryKey();
            }
        }
        return oldPrimaryKey + 1;
    }

    /**
     * Initializes the hole class. this additional method is essentially used to have a central point to check for the
     * correct existence of all needed objects.
     *
     * @param primaryKey
     * @param foreignKey
     */
    private void initializeValues(Integer primaryKey, Integer foreignKey) {
        notNull("'primaryKey' must not be null", primaryKey);
        notNull("'foreignKey' must not be null", foreignKey);
        this.primaryKey = primaryKey;
        this.foreignKeyBall = foreignKey;
    }

    /**
     * Compares this string representation to the string representation of the specified object.
     * This comparison is without the primary key.
     *
     * @param contentOfSuitcase the specified object
     * @return true if equal, otherwise false
     */
    public boolean equals(ContentOfSuitCaseImpl contentOfSuitcase) {
        if (this == contentOfSuitcase) {
            return true;
        }
        return foreignKeyBall.equals(contentOfSuitcase.getForeignKeyBall());
    }

    /**
     * Returns a hash code value for the ball in the suite case.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return foreignKeyBall.toString().hashCode();
    }

    /**
     * Checks on the one hand the primary key and otherwise the foreign key against the content of a list
     * of {@link ContentOfSuitCaseImpl} for uniqueness.
     *
     * @return true if is unique, otherwise false
     */
    public boolean isUnique(List<ContentOfSuitCaseImpl> list) {
        for (ContentOfSuitCaseImpl entry : list) {
            if (this != entry) {
                if (primaryKey.equals(entry.getPrimaryKey())
                        || foreignKeyBall.equals(entry.getForeignKeyBall())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the position of a ball inside the suite case.
     *
     * @return the position
     */
    @Override
    public Integer getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Sets the position of a ball inside the suite case.
     *
     * @param primaryKey the position
     */
    @VisibleForTesting
    protected void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Returns the index of the ball in the data collection of balls.
     *
     * @return the index
     */
    @Override
    public Integer getForeignKeyBall() {
        return foreignKeyBall;
    }

    /**
     * Sets the index of the ball in the data collection of balls.
     *
     * @param foreignKeyBall the index
     */
    public void setForeignKeyBall(Integer foreignKeyBall) {
        this.foreignKeyBall = foreignKeyBall;
    }

    /**
     * Returns a string representation of the ball in the suite case. It is created by {@link ReflectionToStringBuilder}.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
