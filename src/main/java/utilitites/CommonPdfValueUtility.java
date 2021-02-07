package utilitites;

/**
 * This class is used to present several values for pdf export functions.<br>
 * The content inside this class is mostly text templates.
 */
public class CommonPdfValueUtility extends CommonTableTitleValueUtility {

    /**
     * Text templates of title page.
     */
    public final String documentTitle = "Minigolfverwaltung";
    public final String documentDate = "erstellt am";

    /**
     * Text templates of data pages.
     */
    public final String pageTitleBallCharacteristics = "Übersicht der Minigolfbälle";
    public final String pageTitleSuitCaseCharacteristics = "Übersicht der Ballkoffer";
    public final String pageTitleCrazyGolfSitesCharacteristics = "Übersicht der Minigolfbahnen";

    /**
     * Text templates of sub titles.
     */
    public final String subTitleSuitCaseCharacteristics = "Ballkoffer %d - %s";
    public final String subTitleCrazyGolfSitesCharacteristics = "Minigolfbahn %d - %s";

    /**
     * Text templates of special table titles.
     */
    public final String tableTitleCoscPrimaryKey = "Position";

    /**
     * Text templates of data field.
     */
    public final String dataTitleSccContents = "Inhalt des Koffers:";
    public final String tableTitleCgscContents = "Bahnen der Anlage:";

    /**
     * Several text templates.
     */
    public final String newLine = "\n";
    public final String noDataAvailable = "Es sind keine passenden Daten verfügbar !";
    public final String notApplicable = "n/a";
    public final String space = " ";
    public final String tabulator = "\t";

}
