package communications.datastructures;

import dataStructures.BallCharacteristicsImpl;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * This class is used to transport several data of the state of ball characteristics data.<br>
 * It is used inside the class {@link DataStatesData}.
 * <p>
 * In a next step, this class wil be replaced by an xml object with sam name.
 * </p>
 */
public class BallCharacteristicsData implements Serializable {

    private final String NEW_LINE = "\n";

    private final Integer numberOfEntries;

    public BallCharacteristicsData(List<BallCharacteristicsImpl> ballCharacteristics) {
        this.numberOfEntries = ballCharacteristics.size();
    }

    public Integer getNumberOfEntries() {
        return numberOfEntries;
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

    /**
     * Returns a string representation of the service session runner data as a xml representation.
     *
     * @return
     */
    public String toXmlString() {
        StringBuffer result = new StringBuffer(String.format("<ballCharacteristicsData>%s", NEW_LINE));

        result.append(String.format("<numberOfEntries>%d</numberOfEntries>%s", getNumberOfEntries(), NEW_LINE));

        result.append(String.format("</ballCharacteristicsData>%s", NEW_LINE));

        return result.toString();
    }

}
