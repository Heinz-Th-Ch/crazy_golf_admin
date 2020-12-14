package importAndExport;

/**
 * This class is used to present several values for csv import and export functions.
 */
public class CommonCsvValues {

    /**
     * Used in {@link dataStructures.BallCharacteristicsImpl}, {@link dataStructures.CrazyGolfSiteCharacteristicsImpl}
     * and {@link dataStructures.SuitCaseCharacteristicsImpl}.<br>
     * The primary keys in {@link dataStructures.HandicapCharacteristicsImpl} and
     * {@link dataStructures.ContentOfSuitCaseImpl} are special uses like a handicap number on a crazy golf court or a
     * position inside a suit case.
     */
    public final static String PRIMARY_KEY = "Primärschlüssel";

    public final static String BC_IDENTIFIER = "Bezeichnung";
    public final static String BC_DESCRIPTION = "Eigenschaften";
    public final static String BC_HARDNESS = "Härte";
    public final static String BC_UP_THROW = "Höhe";
    public final static String BC_WEIGHT = "Gewicht";
    public final static String BC_ANGLE_FACTOR = "Winkel";
    public final static String BC_COMMENT = "Bemerkungen";

    public final static String COSC_PRIMARY_KEY = "Position in Koffer";
    public final static String COSC_FOREIGN_KEY_BALL = "Bezeichnung";

    public final static String CGSC_SITE_NAME = "Anlage";
    public final static String CGSC_ADDRESS = "Adresse";
    public final static String CGSC_POST_CODE = "Postleitzahl";
    public final static String CGSC_TOWN = "Ort";
    public final static String CGSC_SUIT_CASE = "Bezeichnung Koffer";
    public final static String CGSC_CONTENTS_FILE = "Datenfile Bahnen";

    public final static String HC_PRIMARY_KEY = "Bahn";
    public final static String HC_FOREIGN_KEY_BALL = "Ball";
    public final static String HC_POSITIONING = "Setzpunkt";
    public final static String HC_CUSHIONING = "Banden";
    public final static String HC_MARKING = "Markierung";
    public final static String HC_REMARK = "Bemerkungen";

    public final static String SCC_IDENTIFIER = "Bezeichnung";
    public final static String SCC_DESCRIPTION = "Eigenschaften";
    public final static String SCC_OWNER = "Besitzer";
    public final static String SCC_CONTENTS_FILE = "Datenfile Inhalt";

    public final static String CSV_SPLITTER = ";";
    public final static String NEW_LINE = "\n";
    public final static String NOT_APPLICABLE = "n/a";

}
