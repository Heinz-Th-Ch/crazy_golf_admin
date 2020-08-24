package enumerations;

/**
 * This enum contains the codes for the hardness of the crazy golf balls.
 */
public enum Hardness {

    SW("very soft"),
    W("soft"),
    M("medium"),
    H("hard"),
    SH("very hard"),
    UNDEF("undefined");

    private final String text;

    Hardness(String hardness) {
        text = hardness;
    }

    String getText() {
        return text;
    }
}
