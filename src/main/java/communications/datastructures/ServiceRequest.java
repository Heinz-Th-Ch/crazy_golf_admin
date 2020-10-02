package communications.datastructures;

import communications.enumerations.ServiceFunction;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * This class is used to send a request which is service specific.
 * After handle of this request a {@link ServiceResponse} has to send.
 */
public class ServiceRequest implements Serializable {

    private final ServiceFunction function;

    public ServiceRequest(ServiceFunction function) {
        this.function = function;
    }

    public ServiceFunction getFunction() {
        return function;
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
