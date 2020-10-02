package communications.datastructures;

import communications.enumerations.SessionFunction;
import communications.enumerations.SessionType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * This class is used to send a request which is session specific.
 * After handle of this request a {@link SessionResponse} has to send.
 */
public class SessionRequest implements Serializable {

    private final SessionFunction function;
    private final SessionType sessionType;

    public SessionRequest(SessionFunction function, SessionType sessionType) {
        this.function = function;
        this.sessionType = sessionType;
    }

    public SessionFunction getFunction() {
        return function;
    }

    public SessionType getSessionType() {
        return sessionType;
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
