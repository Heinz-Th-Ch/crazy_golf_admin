package communications.datastructures;

import dataStructures.DataListContainerImpl;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * This class is used to transport several data over the states of data.<br>
 * It is used to create all needed data which reside in {@link states.DataStates}.
 * <p>
 * In a next step, this class wil be replaced by an xml object with sam name.
 * </p>
 */
public class DataStatesData implements Serializable {

    private final String NEW_LINE = "\n";

    private final String applicationName;
    private final Boolean dataAvailable;
    private final BallCharacteristicsData ballCharacteristicsData;
    private final CrazyGolfSiteCharacteristicsData crazyGolfSiteCharacteristicsData;
    private final SuitCaseCharacteristicsData suitCaseCharacteristicsData;

    public DataStatesData(String applicationName,
                          @Nullable DataListContainerImpl dataListContainer) {
        this.applicationName = applicationName;
        this.dataAvailable = (dataListContainer != null);
        if (this.dataAvailable) {
            ballCharacteristicsData = new BallCharacteristicsData(dataListContainer.getBallCharacteristics());
            crazyGolfSiteCharacteristicsData = new CrazyGolfSiteCharacteristicsData(dataListContainer.getCrazyGolfSiteCharacteristics());
            suitCaseCharacteristicsData = new SuitCaseCharacteristicsData(dataListContainer.getSuitCaseCharacteristics());

        } else {
            ballCharacteristicsData = new BallCharacteristicsData(List.of());
            crazyGolfSiteCharacteristicsData = new CrazyGolfSiteCharacteristicsData(List.of());
            suitCaseCharacteristicsData = new SuitCaseCharacteristicsData(List.of());
        }
    }

    public String getApplicationName() {
        return applicationName;
    }

    public Boolean getDataAvailable() {
        return dataAvailable;
    }

    public BallCharacteristicsData getBallCharacteristicsData() {
        return ballCharacteristicsData;
    }

    public CrazyGolfSiteCharacteristicsData getCrazyGolfSiteCharacteristicsData() {
        return crazyGolfSiteCharacteristicsData;
    }

    public SuitCaseCharacteristicsData getSuitCaseCharacteristicsData() {
        return suitCaseCharacteristicsData;
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
        StringBuffer result = new StringBuffer(String.format("<?xml version=\"1.0\" encoding=\"utf-8\"?>%s", NEW_LINE));
        result.append(String.format("<%s>%s", getClass().getSimpleName(), NEW_LINE));

        result.append(String.format("<applicationName>%s</applicationName>%s", getApplicationName(), NEW_LINE));
        result.append(String.format("<dataAvailable>%s</dataAvailable>%s", getDataAvailable(), NEW_LINE));

        result.append(getBallCharacteristicsData().toXmlString());
        result.append(getSuitCaseCharacteristicsData().toXmlString());
        result.append(getCrazyGolfSiteCharacteristicsData().toXmlString());

        result.append(String.format("</%s>%s", getClass().getSimpleName(), NEW_LINE));

        return result.toString();
    }

}
