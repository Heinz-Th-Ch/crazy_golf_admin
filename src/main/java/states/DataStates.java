package states;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * This class represents only the state of several data groups but not the data contents.
 */
public class DataStates {

    private String dataName;
    private Integer numberOfRecords;
    private Integer numberOfInternalRecords;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public Integer getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(Integer numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public Integer getNumberOfInternalRecords() {
        return numberOfInternalRecords;
    }

    public void setNumberOfInternalRecords(Integer numberOfInternalRecords) {
        this.numberOfInternalRecords = numberOfInternalRecords;
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
