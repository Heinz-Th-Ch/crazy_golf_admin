package dataStructures;

import com.google.common.annotations.VisibleForTesting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static utilities.AssertionUtil.notNull;

/**
 * The implementation of {@link SuitCaseCharacteristics}.
 * The primary key is {@link SuitCaseCharacteristicsImpl#primaryKey}.
 * An additional combined unique key is consisting of {@link SuitCaseCharacteristicsImpl#identifier},
 * {@link SuitCaseCharacteristicsImpl#description} and {@link SuitCaseCharacteristicsImpl#owner}.
 */
public class SuitCaseCharacteristicsImpl implements SuitCaseCharacteristics, Serializable {

    private final List<ContentOfSuitCaseImpl> contents;

    /**
     * Primary key inside a group of {@link SuitCaseCharacteristicsImpl}.
     */
    private Integer primaryKey;

    private String identifier;
    private String description;
    private String owner;

    /**
     * Constructs a new instance.
     *
     * @param identifier
     * @param description
     * @param owner
     * @param numberOfSlots
     */
    public SuitCaseCharacteristicsImpl(Integer primaryKey,
                                       String identifier,
                                       String description,
                                       String owner,
                                       Integer numberOfSlots) {
        initializeValues(primaryKey,
                identifier,
                description,
                owner);
        notNull("'numberOfSlots' must not be null", numberOfSlots);
        List<ContentOfSuitCaseImpl> tempList = new ArrayList<>(List.of());
        for (int i = 0; i < numberOfSlots; i++) {
            tempList.add(new ContentOfSuitCaseImpl(tempList));
        }
        contents = tempList;
    }

    /**
     * Constructs a new instance.
     *
     * @param list
     * @param suitCaseCharacteristics
     */
    public SuitCaseCharacteristicsImpl(List<SuitCaseCharacteristicsImpl> list,
                                       SuitCaseCharacteristicsImpl suitCaseCharacteristics) {
        notNull("'list' must not be null", list);
        notNull("'suitCaseCharacteristics' must not be null", suitCaseCharacteristics);
        initializeValues(getNextPrimaryKey(list),
                suitCaseCharacteristics.getIdentifier(),
                suitCaseCharacteristics.getDescription(),
                suitCaseCharacteristics.getOwner());
        this.contents = suitCaseCharacteristics.getContents();
    }

    /**
     * Constructs a new instance.
     *
     * @param list
     * @param identifier
     * @param description
     * @param owner
     * @param contents
     */
    public SuitCaseCharacteristicsImpl(List<SuitCaseCharacteristicsImpl> list,
                                       String identifier,
                                       String description,
                                       String owner,
                                       List<ContentOfSuitCaseImpl> contents) {
        notNull("'list' must not be null", list);
        notNull("'contents' must not be null", contents);
        initializeValues(getNextPrimaryKey(list),
                identifier,
                description,
                owner);
        this.contents = contents;
    }

    /**
     * Evaluates the highest primary key in list and returns the next possible primary key.
     *
     * @param list a group of {@link SuitCaseCharacteristicsImpl}.
     * @return the new possible primary key.
     */
    public static int getNextPrimaryKey(List<SuitCaseCharacteristicsImpl> list) {
        int oldPrimaryKey = 0;
        for (SuitCaseCharacteristicsImpl entry : list) {
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
     * @param identifier
     * @param description
     * @param owner
     */
    private void initializeValues(Integer primaryKey, String identifier, String description, String owner) {
        notNull("'primaryKey' must not be null", primaryKey);
        notNull("'identifier' must not be null", identifier);
        notNull("'description' must not be null", description);
        notNull("'owner' must not be null", owner);
        this.primaryKey = primaryKey;
        this.identifier = identifier;
        this.description = description;
        this.owner = owner;
    }

    /**
     * Compares this string representation to the string representation of the specified object.
     * This comparison is without the primary key.
     *
     * @param testObject
     * @return true if equal, otherwise false
     */
    public boolean equals(SuitCaseCharacteristicsImpl testObject) {
        if (this == testObject) {
            return true;
        }
        return identifier.equals(testObject.getIdentifier())
                && description.equals(testObject.getDescription())
                && owner.equals(testObject.getOwner())
                && contents.equals(testObject.getContents());
    }

    /**
     * Returns a hash code value for the suitcase.
     *
     * @return the hash code
     */
    public int hashCode() {
        return identifier.hashCode()
                + description.hashCode()
                + owner.hashCode()
                + contents.hashCode();
    }

    /**
     * Checks on the one hand the primary key and otherwise the combination of identifier, description and owner
     * against the content of a list of {@link SuitCaseCharacteristicsImpl} for uniqueness.
     *
     * @return true if is unique, otherwise false
     */
    public boolean isUnique(List<SuitCaseCharacteristicsImpl> list) {
        for (SuitCaseCharacteristicsImpl entry : list) {
            if (this != entry) {
                if (primaryKey.equals(entry.getPrimaryKey())
                        || (identifier.equals(entry.getIdentifier())
                        && description.equals(entry.getDescription())
                        && owner.equals(entry.getOwner()))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return the primary key, which is the number of the suitcase.
     *
     * @return
     */
    @Override
    public Integer getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Sets the primary key, which is the number of the suitcase.
     *
     * @param primaryKey
     */
    @VisibleForTesting
    protected void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Returns the identifier of the suitcase.
     *
     * @return the identifier
     */
    @Override
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier of the suitcase.
     *
     * @param identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Returns a description of the suitcase.
     *
     * @return the description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Sets a description of the suitcase.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the owner of the suitcase.
     *
     * @return the owner
     */
    @Override
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the suitcase.
     *
     * @param owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Returns a list of all slots inside the suitcase.
     *
     * @return a list of <b>all</b> slots, whatever they are taken or not
     */
    @Override
    public List<ContentOfSuitCaseImpl> getContents() {
        return contents;
    }
}
