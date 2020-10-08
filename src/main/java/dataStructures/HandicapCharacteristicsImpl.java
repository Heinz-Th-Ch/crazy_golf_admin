package dataStructures;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.jetbrains.annotations.VisibleForTesting;

import java.io.Serializable;
import java.util.List;

import static utilities.AssertionUtil.notNull;

/**
 * The implementation of {@link HandicapCharacteristics}.
 * The primary key is {@link HandicapCharacteristicsImpl#primaryKey}.
 * The two foreign keys are {@link HandicapCharacteristicsImpl#foreignKeySuitCase} which shows to the primary key
 * in {@link SuitCaseCharacteristicsImpl} and {@link HandicapCharacteristicsImpl#foreignKeyBall} which shows to the
 * primary key in {@link ContentOfSuitCaseImpl}.
 */
public class HandicapCharacteristicsImpl implements HandicapCharacteristics, Serializable {

    private static final Integer VALUE_FOR_UNDEFINED_NUMBER = -1;
    private static final String VALUE_FOR_UNDEFINED_STRING = "";

    /**
     * Primary key inside a group of {@link HandicapCharacteristicsImpl}.
     */
    private Integer primaryKey;

    /**
     * Foreign key linked to {@link SuitCaseCharacteristicsImpl}.
     */
    private Integer foreignKeySuitCase;

    /**
     * Foreign key inside a group {@link ContentOfSuitCaseImpl} inside {@link SuitCaseCharacteristicsImpl}.
     */
    private Integer foreignKeyBall;

    private String positioning;
    private String cushioning;
    private String marking;
    private String remark;

    /**
     * Constructs a new instance.
     *
     * @param primaryKey
     * @param foreignKeySuitCase
     * @param foreignKeyBall
     * @param positioning
     * @param cushioning
     * @param marking
     * @param remark
     */
    public HandicapCharacteristicsImpl(Integer primaryKey,
                                       Integer foreignKeySuitCase,
                                       Integer foreignKeyBall,
                                       String positioning,
                                       String cushioning,
                                       String marking,
                                       String remark) {
        initializeValues(primaryKey,
                foreignKeySuitCase,
                foreignKeyBall,
                positioning,
                cushioning,
                marking,
                remark);
    }

    /**
     * Constructs a new instance.
     *
     * @param list
     * @param handicapCharacteristics
     */
    public HandicapCharacteristicsImpl(List<HandicapCharacteristicsImpl> list,
                                       HandicapCharacteristicsImpl handicapCharacteristics) {
        notNull("'list' must not be null", list);
        notNull("'handicapCharacteristics' must not be null", handicapCharacteristics);
        initializeValues(getNextPrimaryKey(list),
                handicapCharacteristics.getForeignKeySuitCase(),
                handicapCharacteristics.getForeignKeyBall(),
                handicapCharacteristics.getPositioning(),
                handicapCharacteristics.getCushioning(),
                handicapCharacteristics.getMarking(),
                handicapCharacteristics.getRemark());
    }

    /**
     * Constructs a new instance.
     *
     * @param list
     */
    public HandicapCharacteristicsImpl(List<HandicapCharacteristicsImpl> list) {
        notNull("'list' must not be null", list);
        initializeValues(getNextPrimaryKey(list),
                VALUE_FOR_UNDEFINED_NUMBER,
                VALUE_FOR_UNDEFINED_NUMBER,
                VALUE_FOR_UNDEFINED_STRING,
                VALUE_FOR_UNDEFINED_STRING,
                VALUE_FOR_UNDEFINED_STRING,
                VALUE_FOR_UNDEFINED_STRING);
    }

    /**
     * Evaluates the highest primary key in list and returns the next possible primary key.
     *
     * @param list a group of {@link HandicapCharacteristicsImpl}.
     * @return the new possible primary key.
     */
    public static int getNextPrimaryKey(List<HandicapCharacteristicsImpl> list) {
        int oldPrimaryKey = 0;
        for (HandicapCharacteristicsImpl entry : list) {
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
     * @param foreignKeySuitCase
     * @param foreignKeyBall
     * @param positioning
     * @param cushioning
     * @param marking
     * @param remark
     */
    private void initializeValues(Integer primaryKey,
                                  Integer foreignKeySuitCase,
                                  Integer foreignKeyBall,
                                  String positioning,
                                  String cushioning,
                                  String marking,
                                  String remark) {
        notNull("'primaryKey' must not be null", primaryKey);
        notNull("'foreignKeySuitCase' must not be null", foreignKeySuitCase);
        notNull("'foreignKeyBall' must not be null", foreignKeyBall);
        notNull("'positioning' must not be null", positioning);
        notNull("'cushioning' must not be null", cushioning);
        notNull("'marking' must not be null", marking);
        notNull("'remark' must not be null", remark);
        this.primaryKey = primaryKey;
        this.foreignKeySuitCase = foreignKeySuitCase;
        this.foreignKeyBall = foreignKeyBall;
        this.positioning = positioning;
        this.cushioning = cushioning;
        this.marking = marking;
        this.remark = remark;
    }

    /**
     * Compares this string representation to the string representation of the specified object.
     * This comparison is without the primary key.
     *
     * @param testObject
     * @return true if equal, otherwise false
     */
    public boolean equals(HandicapCharacteristicsImpl testObject) {
        if (this == testObject) {
            return true;
        }
        return foreignKeySuitCase.equals(testObject.getForeignKeySuitCase())
                && foreignKeyBall.equals(testObject.getForeignKeyBall())
                && positioning.equals(testObject.getPositioning())
                && cushioning.equals(testObject.getCushioning())
                && marking.equals(testObject.getMarking())
                && remark.equals(testObject.getRemark());
    }

    /**
     * Returns a hash code value for the handicap.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return foreignKeySuitCase.hashCode()
                + foreignKeyBall.hashCode()
                + positioning.hashCode()
                + cushioning.hashCode()
                + marking.hashCode()
                + remark.hashCode();
    }

    /**
     * Checks the primary key against the content of a list of {@link HandicapCharacteristicsImpl}  for uniqueness.
     *
     * @return true if is unique, otherwise false
     */
    public boolean isUnique(List<HandicapCharacteristicsImpl> list) {
        for (HandicapCharacteristicsImpl entry : list) {
            if (this != entry) {
                if (primaryKey.equals(entry.getPrimaryKey())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return the primary key, which is the number of the handicap on a crazy golf course.
     *
     * @return
     */
    @Override
    public Integer getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Sets the number of the handicap on a crazy golf course.
     *
     * @param primaryKey
     */
    @VisibleForTesting
    protected void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Returns the index of the suitcase in the data collection of suitcases, which is used on this handicap.
     *
     * @return the index
     */
    @Override
    public Integer getForeignKeySuitCase() {
        return foreignKeySuitCase;
    }

    /**
     * Sets the index of the suitcase in the data collection.
     *
     * @param foreignKeySuitCase
     */
    public void setForeignKeySuitCase(Integer foreignKeySuitCase) {
        this.foreignKeySuitCase = foreignKeySuitCase;
    }

    /**
     * Returns the index - position - of the ball inside the suitcase, which is used on this handicap.
     *
     * @return the index
     */
    @Override
    public Integer getForeignKeyBall() {
        return foreignKeyBall;
    }

    /**
     * Sets the index - position - of the ball inside the suitcase.
     *
     * @param foreignKeyBall
     */
    public void setForeignKeyBall(Integer foreignKeyBall) {
        this.foreignKeyBall = foreignKeyBall;
    }

    /**
     * Returns the positioning of the ball.
     *
     * @return the position to set the ball
     */
    @Override
    public String getPositioning() {
        return positioning;
    }

    /**
     * Sets the position of the ball.
     *
     * @param positioning
     */
    public void setPositioning(String positioning) {
        this.positioning = positioning;
    }

    /**
     * Returns the kind of cushioning, if needed.
     *
     * @return the kind of cushioning
     */
    @Override
    public String getCushioning() {
        return cushioning;
    }

    /**
     * Sets the kind of cushioning.
     *
     * @param cushioning
     */
    public void setCushioning(String cushioning) {
        this.cushioning = cushioning;
    }

    /**
     * Returns the kind and position of marking, if existing.
     *
     * @return the marking
     */
    @Override
    public String getMarking() {
        return marking;
    }

    /**
     * Sets the kind and position of marking.
     *
     * @param marking
     */
    public void setMarking(String marking) {
        this.marking = marking;
    }

    /**
     * Returns the remark, if existing.
     *
     * @return the remark
     */
    @Override
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the remark.
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * Returns a string representation of the handicap. It is created by {@link ReflectionToStringBuilder}.
     *
     * @return the string representation
     */
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
