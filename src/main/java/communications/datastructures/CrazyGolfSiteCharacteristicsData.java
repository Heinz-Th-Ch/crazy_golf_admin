package communications.datastructures;

import dataStructures.CrazyGolfSiteCharacteristics;
import dataStructures.CrazyGolfSiteCharacteristicsImpl;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to transport several data of the state of crazy golf site characteristics data.<br>
 * It is used inside the class {@link DataStatesData}.
 * <p>
 * In a next step, this class wil be replaced by an xml object with sam name.
 * </p>
 */
public class CrazyGolfSiteCharacteristicsData implements Serializable {

    private final String NEW_LINE = "\n";

    private final Integer numberOfEntries;
    private final List<Integer> numberOfSubEntries = new ArrayList<>(List.of());

    public CrazyGolfSiteCharacteristicsData(List<CrazyGolfSiteCharacteristicsImpl> crazyGolfSiteCharacteristics) {
        this.numberOfEntries = crazyGolfSiteCharacteristics.size();
        for (CrazyGolfSiteCharacteristics entry : crazyGolfSiteCharacteristics) {
            this.numberOfSubEntries.add(entry.getContents().size());
        }
    }

    public Integer getNumberOfEntries() {
        return numberOfEntries;
    }

    public List<Integer> getNumberOfSubEntries() {
        return numberOfSubEntries;
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
        StringBuffer result = new StringBuffer(String.format("<crazyGolfSiteCharacteristicsData>%s", NEW_LINE));

        result.append(String.format("<numberOfEntries>%d</numberOfEntries>%s", getNumberOfEntries(), NEW_LINE));
        for (Integer entry : getNumberOfSubEntries()) {
            result.append(String.format("<numberOfSubEntries>%d</numberOfSubEntries>%s", entry, NEW_LINE));
        }

        result.append(String.format("</crazyGolfSiteCharacteristicsData>%s", NEW_LINE));

        return result.toString();
    }

}
