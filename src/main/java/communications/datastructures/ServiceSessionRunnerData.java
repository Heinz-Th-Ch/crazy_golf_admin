package communications.datastructures;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import runnables.ServiceSessionRunner;

import java.io.Serializable;

/**
 * This class is used to transport several data over communication sessions.<br>
 * It is used inside the class {@link SessionStatesData}.
 * <p>
 * In a next step, this class wil be replaced by an xml object with sam name.
 * </p>
 */
public class ServiceSessionRunnerData implements Serializable {

    private final String NEW_LINE = "\n";
    private final String NOT_APPLICABLE = "n/a";

    private final Long runnerId;
    private final String runnerName;
    private final String runnerState;

    public ServiceSessionRunnerData(ServiceSessionRunner serviceSessionRunner) {
        if (serviceSessionRunner != null) {
            this.runnerId = serviceSessionRunner.getId();
            this.runnerName = serviceSessionRunner.getName();
            this.runnerState = serviceSessionRunner.getState().name();
        } else {
            this.runnerId = 0L;
            this.runnerName = NOT_APPLICABLE;
            this.runnerState = NOT_APPLICABLE;
        }
    }

    public Long getRunnerId() {
        return runnerId;
    }

    public String getRunnerName() {
        return runnerName;
    }

    public String getRunnerState() {
        return runnerState;
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
        StringBuffer result = new StringBuffer(String.format("<serviceSessionRunnerData>%s", NEW_LINE));

        result.append(String.format("<runnerId>%d</runnerId>%s", getRunnerId(), NEW_LINE));
        result.append(String.format("<runnerName>%s</runnerName>%s", getRunnerName(), NEW_LINE));
        result.append(String.format("<runnerState>%s</runnerState>%s", getRunnerState(), NEW_LINE));

        result.append(String.format("</serviceSessionRunnerData>%s", NEW_LINE));

        return result.toString();
    }
}
