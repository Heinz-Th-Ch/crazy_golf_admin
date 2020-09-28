package communications.datastructures;

import communications.enumerations.SessionReturnCode;

import java.io.Serializable;

/**
 * This class is used to send response which are session specific.
 * Is used after handle of {@link SessionRequest}.
 */
public class SessionResponse implements Serializable {

    private final SessionReturnCode returnCode;

    public SessionResponse(SessionReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    public SessionReturnCode getReturnCode() {
        return returnCode;
    }

}
