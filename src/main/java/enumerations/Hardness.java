package enumerations;

/**
 * This enum contains the codes for the hardness of the crazy golf balls.
 */
public enum Hardness {

    H("hard"),
    M("medium"),
    SH("very hard"),
    SW("very soft"),
    UNDEF("undefined"),
    W("soft");

    private final String text;

    Hardness(String hardness) {
        text = hardness;
    }

    String getText() {
        return text;
    }
}
