package dataStructures;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

/**
 * The implementation of {@link ContentOfSuitcase}.
 * The primary key is {@link ContentOfSuitcaseImpl#primaryKey}, which is corresponds to the position of a ball inside
 * the suitcase. The limit of primary keys contingent on the size of the suitcase(s), defined in the ???.
 * The foreign key is {@link ContentOfSuitcaseImpl#foreignKey} which shows to the primary key in
 * {@link BallCharacteristicsImpl}.
 */
public class ContentOfSuitcaseImpl implements ContentOfSuitcase {

    /**
     * Primary key inside a group of {@link ContentOfSuitcaseImpl}.
     */
    private Integer primaryKey;

    /**
     * Foreign key inside a group of {@link ContentOfSuitcaseImpl} linked to {@link BallCharacteristicsImpl}.
     */
    private Integer foreignKey;

    /**
     * Constructs a new instance.
     *
     * @param primaryKey
     * @param foreignKey
     */
    public ContentOfSuitcaseImpl(Integer primaryKey, Integer foreignKey) {
        this.primaryKey = primaryKey;
        this.foreignKey = foreignKey;
    }

    /**
     * Constructs a new instance.
     *
     * @param list
     * @param contentOfSuitcase
     */
    public ContentOfSuitcaseImpl(List<ContentOfSuitcaseImpl> list,
                                 ContentOfSuitcaseImpl contentOfSuitcase) {
        this.primaryKey = getNextPrimaryKey(list);
        this.foreignKey = contentOfSuitcase.getForeignKey();
    }

    /**
     * Construct a new instance with primary key set only.
     *
     * @param list
     */
    public ContentOfSuitcaseImpl(List<ContentOfSuitcaseImpl> list) {
        this.primaryKey = getNextPrimaryKey(list);
        this.foreignKey = -1;
    }

    /**
     * Evaluates the highest primary key in list and returns the next possible primary key.
     *
     * @param list a group of {@link ContentOfSuitcaseImpl}.
     * @return the new possible primary key.
     */
    public static int getNextPrimaryKey(List<ContentOfSuitcaseImpl> list) {
        int oldPrimaryKey = 0;
        for (ContentOfSuitcaseImpl entry : list) {
            if (entry.getPrimaryKey() > oldPrimaryKey) {
                oldPrimaryKey = entry.getPrimaryKey();
            }
        }
        return oldPrimaryKey + 1;
    }

    /**
     * Compares this string representation to the string representation of the specified object.
     * This comparison is without the primary key.
     *
     * @param contentOfSuitcase the specified object
     * @return true if equal, otherwise false
     */
    public boolean equals(ContentOfSuitcaseImpl contentOfSuitcase) {
        if (this == contentOfSuitcase) {
            return true;
        }
        return foreignKey.equals(contentOfSuitcase.getForeignKey());
    }

    /**
     * Returns a hash code value for the ball in the suite case.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return foreignKey.toString().hashCode();
    }

    /**
     * Checks on the one hand the primary key and otherwise the foreign key against the content of a list
     * of {@link ContentOfSuitcaseImpl} for uniqueness.
     *
     * @return true if is unique, otherwise false
     */
    public boolean isUnique(List<ContentOfSuitcaseImpl> list) {
        for (ContentOfSuitcaseImpl entry : list) {
            if (this != entry) {
                if (primaryKey.equals(entry.getPrimaryKey())
                        || foreignKey.equals(entry.getForeignKey())) {
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
    public Integer getForeignKey() {
        return foreignKey;
    }

    /**
     * Sets the index of the ball in the data collection of balls.
     *
     * @param foreignKey the index
     */
    public void setForeignKey(Integer foreignKey) {
        this.foreignKey = foreignKey;
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
