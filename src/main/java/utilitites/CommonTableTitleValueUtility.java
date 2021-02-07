package utilitites;

/**
 * This class is used to present several values for csv import and export and pdf export functions.
 * The content inside this class is mostly table title text templates.
 */
public class CommonTableTitleValueUtility {

    /**
     * Used in {@link dataStructures.BallCharacteristicsImpl}, {@link dataStructures.CrazyGolfSiteCharacteristicsImpl}
     * and {@link dataStructures.SuitCaseCharacteristicsImpl}.<br>
     * The primary keys in {@link dataStructures.HandicapCharacteristicsImpl} and
     * {@link dataStructures.ContentOfSuitCaseImpl} are special uses like a handicap number on a crazy golf court or a
     * position inside a suit case.
     */
    public final String tableTitlePrimaryKey = "Primärschlüssel";

    public final String tableTitleBcIdentifier = "Bezeichnung";
    public final String tableTitleBcDescription = "Eigenschaften";
    public final String tableTitleBcHardness = "Härte";
    public final String tableTitleBcUpThrow = "Höhe";
    public final String tableTitleBcWeight = "Gewicht";
    public final String tableTitleBcAngleFactor = "Winkel";
    public final String tableTitleBcComment = "Bemerkungen";

    public final String tableTitleCoscPrimaryKey = "Position in Koffer";
    public final String tableTitleCoscForeignKeyBall = "Bezeichnung";

    public final String tableTitleCgscSiteName = "Anlage";
    public final String tableTitleCgscAddress = "Adresse";
    public final String tableTitleCgscPostCode = "Postleitzahl";
    public final String tableTitleCgscTown = "Ort";
    public final String tableTitleCgscSuitCase = "Bezeichnung Koffer";
    public final String tableTitleCgscContentsFile = "Datenfile Bahnen";

    public final String tableTitleHcPrimaryKey = "Bahn";
    public final String tableTitleHcForeignKeyBall = "Ball";
    public final String tableTitleHcPositioning = "Setzpunkt";
    public final String tableTitleHcCushioning = "Banden";
    public final String tableTitleHcMarking = "Markierung";
    public final String tableTitleHcRemark = "Bemerkungen";

    public final String tableTitleSccIdentifier = "Bezeichnung";
    public final String tableTitleSccDescription = "Eigenschaften";
    public final String tableTitleSccOwner = "Besitzer";
    public final String tableTitleSccContentsFile = "Datenfile Inhalt";

}
