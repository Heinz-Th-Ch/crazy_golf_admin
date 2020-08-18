package enumerations;

public enum Hardness {

    SW("very soft"),
    W("soft"),
    M("medium"),
    H("hard"),
    SH("very hard"),
    UNDEF("undefined");

    private String text;

    Hardness(String hardness) {
        text = hardness;
    }

    String getText() {
        return text;
    }
}
