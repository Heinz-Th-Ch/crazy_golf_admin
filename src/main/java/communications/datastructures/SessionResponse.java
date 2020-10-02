package communications.datastructures;

import communications.enumerations.SessionReturnCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * This class is used to send a response which is session specific.
 * It is used after handle of {@link SessionRequest}.
 */
public class SessionResponse implements Serializable {

    private final SessionReturnCode returnCode;

    public SessionResponse(SessionReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    public SessionReturnCode getReturnCode() {
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
