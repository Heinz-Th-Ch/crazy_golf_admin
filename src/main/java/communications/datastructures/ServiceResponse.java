package communications.datastructures;

import communications.enumerations.ServiceFunction;
import communications.enumerations.ServiceReturnCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to send a response which is service specific.
 * It is used after handle of {@link ServiceRequest}.
 */
public class ServiceResponse implements Serializable {

    private final ServiceFunction function;
    private final ServiceReturnCode returnCode;
    private final List<ApplicationStatesData> applicationStates = new ArrayList<>(List.of());
    private final List<DataStatesData> dataStates = new ArrayList<>(List.of());
    private final List<SessionStatesData> sessionStates = new ArrayList<>(List.of());
    private String applicationName;

    public ServiceResponse(ServiceFunction function,
                           ServiceReturnCode returnCode) {
        this.function = function;
        this.returnCode = returnCode;
    }

    public ServiceFunction getFunction() {
        return function;
    }

    public ServiceReturnCode getReturnCode() {
        return returnCode;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<ApplicationStatesData> getApplicationStates() {
        return applicationStates;
    }

    public List<DataStatesData> getDataStates() {
        return dataStates;
    }

    public List<SessionStatesData> getSessionStates() {
        return sessionStates;
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
