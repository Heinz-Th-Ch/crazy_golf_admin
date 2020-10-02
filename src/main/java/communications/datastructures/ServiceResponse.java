package communications.datastructures;

import communications.enumerations.ServiceFunction;
import communications.enumerations.ServiceReturnCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * This class is used to send a response which is service specific.
 * It is used after handle of {@link ServiceRequest}.
 */
public class ServiceResponse implements Serializable {

    private final ServiceFunction function;
    private final ServiceReturnCode returnCode;

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
