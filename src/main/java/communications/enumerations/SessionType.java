package communications.enumerations;

/**
 * This enum contains the types which sessions can represent.
 */
public enum SessionType {

    DATA_SESSION("Data Session"),
    SERVICE_SESSION("Service Session"),
    WEB_SESSION("Web Session"),
    ;

    private final String sessionName;

    SessionType(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionName() {
        return sessionName;
    }

}
