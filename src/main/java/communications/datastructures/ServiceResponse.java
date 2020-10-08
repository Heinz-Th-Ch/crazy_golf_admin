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

    private List<String> applicationStates = new ArrayList<>(List.of());
    private List<String> dataStates = new ArrayList<>(List.of());
    private List<SessionStatesData> sessionStates = new ArrayList<>(List.of());

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

    public List<String> getApplicationStates() {
        return applicationStates;
    }

    public void setApplicationStates(List<String> applicationStates) {
        this.applicationStates = applicationStates;
    }

    public List<String> getDataStates() {
        return dataStates;
    }

    public void setDataStates(List<String> dataStates) {
        this.dataStates = dataStates;
    }

    public List<SessionStatesData> getSessionStates() {
        return sessionStates;
    }

    public void setSessionStates(List<SessionStatesData> sessionStates) {
        this.sessionStates = sessionStates;
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
