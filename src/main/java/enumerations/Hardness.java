package enumerations;

/**
 * This enum contains the codes for the hardness of the crazy golf balls.
 */
public enum Hardness {

    H("hard", "H"),
    M("medium", "N"),
    VH("very hard", "SH"),
    VS("very soft", "SW"),
    UNDEF("undefined", "?"),
    S("soft", "W");

    private final String text;
    private final String importValue;

    Hardness(String hardness, String importValue) {
        this.text = hardness;
        this.importValue = importValue;
    }

    public String getText() {
        return text;
    }

    public String getImportValue() {
        return importValue;
    }
}
