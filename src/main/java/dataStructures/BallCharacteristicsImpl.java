package dataStructures;

import enumerations.Hardness;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

import static utilities.AssertionUtil.notNull;

/**
 * The implementation of {@link BallCharacteristics}.
 * The primary key is {@link BallCharacteristicsImpl#primaryKey}.
 * An additional combined unique key is consisting of {@link BallCharacteristicsImpl#identifier} and
 * {@link BallCharacteristicsImpl#description}.
 */
public class BallCharacteristicsImpl implements BallCharacteristics, Serializable {

    /**
     * Primary key inside a group of {@link BallCharacteristicsImpl}.
     */
    private Integer primaryKey;

    private String identifier;
    private String description;
    private Hardness hardness;
    private Integer upThrow;
    private Integer weight;
    private Double angleFactor;
    private String comment;

    /**
     * Constructs a new instance.
     *
     * @param primaryKey
     * @param identifier
     * @param description
     * @param hardness
     * @param upThrow
     * @param weight
     * @param angleFactor
     * @param comment
     */
    public BallCharacteristicsImpl(Integer primaryKey,
                                   String identifier,
                                   String description,
                                   Hardness hardness,
                                   Integer upThrow,
                                   Integer weight,
                                   Double angleFactor,
                                   String comment) {
        initializeValues(primaryKey,
                identifier,
                description,
                hardness,
                upThrow,
                weight,
                angleFactor,
                comment);
    }

    /**
     * Constructs a new instance.
     *
     * @param list
     * @param ballCharacteristics
     */
    public BallCharacteristicsImpl(List<BallCharacteristicsImpl> list,
                                   BallCharacteristicsImpl ballCharacteristics) {
        notNull("'list' must not be null", list);
        notNull("'ballCharacteristics' must not be null", ballCharacteristics);
        initializeValues(getNextPrimaryKey(list),
                ballCharacteristics.getIdentifier(),
                ballCharacteristics.description,
                ballCharacteristics.getHardness(),
                ballCharacteristics.getUpThrow(),
                ballCharacteristics.getWeight(),
                ballCharacteristics.getAngleFactor(),
                ballCharacteristics.getComment());
    }

    /**
     * Constructs a new instance.
     *
     * @param list
     * @param identifier
     * @param description
     * @param hardness
     * @param upThrow
     * @param weight
     * @param angleFactor
     * @param comment
     */
    public BallCharacteristicsImpl(List<BallCharacteristicsImpl> list,
                                   String identifier,
                                   String description,
                                   Hardness hardness,
                                   Integer upThrow,
                                   Integer weight,
                                   Double angleFactor,
                                   String comment) {
        notNull("'list' must not be null", list);
        initializeValues(getNextPrimaryKey(list),
                identifier,
                description,
                hardness,
                upThrow,
                weight,
                angleFactor,
                comment);
    }

    /**
     * Evaluates the highest primary key in list and returns the next possible primary key.
     *
     * @param list a group of {@link BallCharacteristicsImpl}.
     * @return the new possible primary key.
     */
    public static Integer getNextPrimaryKey(List<BallCharacteristicsImpl> list) {
        int oldPrimaryKey = 0;
        for (BallCharacteristicsImpl entry : list) {
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
     * @param hardness
     * @param upThrow
     * @param weight
     * @param angleFactor
     * @param comment
     */
    private void initializeValues(Integer primaryKey,
                                  String identifier,
                                  String description,
                                  Hardness hardness,
                                  Integer upThrow,
                                  Integer weight,
                                  Double angleFactor,
                                  String comment) {
        notNull("'primaryKey' must not be null", primaryKey);
        notNull("'identifier' must not be null", identifier);
        notNull("'description' must not be null", description);
        notNull("'hardness' must not be null", hardness);
        notNull("'upThrow' must not be null", upThrow);
        notNull("'weight' must not be null", weight);
        notNull("'angleFactor' must not be null", angleFactor);
        notNull("'comment' must not be null", comment);
        this.primaryKey = primaryKey;
        this.identifier = identifier;
        this.description = description;
        this.hardness = hardness;
        this.upThrow = upThrow;
        this.weight = weight;
        this.angleFactor = angleFactor;
        this.comment = comment;
    }

    /**
     * Compares this string representation to the string representation of the specified object.
     * This comparison is without the primary key.
     *
     * @param testObject
     * @return true if equal, otherwise false
     */
    public boolean equals(BallCharacteristicsImpl testObject) {
        if (this == testObject) {
            return true;
        }
        return identifier.equals(testObject.getIdentifier())
                && description.equals(testObject.getDescription())
                && hardness.equals(testObject.getHardness())
                && upThrow.equals(testObject.getUpThrow())
                && weight.equals(testObject.getWeight())
                && angleFactor.equals(testObject.getAngleFactor())
                && comment.equals(testObject.getComment());
    }

    /**
     * Returns a hash code value for the ball.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return identifier.hashCode()
                + description.hashCode()
                + hardness.hashCode()
                + upThrow.hashCode()
                + weight.hashCode()
                + angleFactor.hashCode()
                + comment.hashCode();
    }

    /**
     * Checks on the one hand the primary key and otherwise the combination of identifier and description against the
     * content of a list of {@link BallCharacteristicsImpl} for uniqueness.
     *
     * @return true if is unique, otherwise false
     */
    public boolean isUnique(List<BallCharacteristicsImpl> list) {
        for (BallCharacteristicsImpl entry : list) {
            if (this != entry) {
                if (primaryKey.equals(entry.getPrimaryKey())
                        || (identifier.equals(entry.getIdentifier())
                        && description.equals(entry.getDescription()))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the index of the ball in the data collection.
     *
     * @return the index
     */
    @Override
    public Integer getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Sets the index of the ball in the data collection.
     *
     * @param primaryKey
     */
    public void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Returns the identifier respectively the name of the ball.
     *
     * @return the name
     */
    @Override
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier respectively the name of the ball.
     *
     * @param identifier the name
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Returns the description of a crazy golf ball.
     *
     * @return the description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of a crazy golf ball.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the hardness of a crazy golf ball.
     *
     * @return the hardness
     */
    @Override
    public Hardness getHardness() {
        return hardness;
    }

    /**
     * Sets the hardness of a crazy golf ball.
     *
     * @param hardness the hardness
     */
    public void setHardness(Hardness hardness) {
        this.hardness = hardness;
    }

    /**
     * Returns the up throw of a crazy golf ball.
     *
     * @return the up throw
     */
    @Override
    public Integer getUpThrow() {
        return upThrow;
    }

    /**
     * Sets the up throw of a crazy golf ball.
     *
     * @param upThrow the up throw
     */
    public void setUpThrow(Integer upThrow) {
        this.upThrow = upThrow;
    }

    /**
     * Returns the weight of a crazy golf ball.
     *
     * @return the weight
     */
    @Override
    public Integer getWeight() {
        return weight;
    }

    /**
     * Sets the weight of a crazy golf ball.
     *
     * @param weight the weight
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * Returns the angle factor of a crazy golf ball.
     *
     * @return the angle factor
     */
    @Override
    public Double getAngleFactor() {
        return angleFactor;
    }

    /**
     * Sets the angle factor of a crazy golf ball.
     *
     * @param angleFactor the angle factor
     */
    public void setAngleFactor(Double angleFactor) {
        this.angleFactor = angleFactor;
    }

    /**
     * Returns the comment of a crazy golf ball.
     *
     * @return the comment
     */
    @Override
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment of a crazy golf ball.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Returns a string representation of the ball. It is created by {@link ReflectionToStringBuilder}.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
