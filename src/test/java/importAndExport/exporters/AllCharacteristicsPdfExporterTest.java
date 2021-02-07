package importAndExport.exporters;

import abstracts.AbstractPlainJava;
import dataStructures.*;
import enumerations.Hardness;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for {@link AllCharacteristicsPdfExporter}.
 * The only one test is the test for method {@link AllCharacteristicsPdfExporter#executeExport()} because the mocking of
 * final classes is not so easy and so the test tests the whole class.
 */
public class AllCharacteristicsPdfExporterTest extends AbstractPlainJava {

    private final static Integer BALL_PRIMARY_KEY_1 = 45;
    private final static Integer BALL_PRIMARY_KEY_2 = 718;
    private final static String BALL_IDENTIFIER_1 = "02";
    private final static String BALL_IDENTIFIER_2 = "B+M K9";
    private final static String BALL_DESCRIPTION_1 = "Stein";
    private final static String BALL_DESCRIPTION_2 = "Rohling";
    private final static Hardness BALL_HARDNESS_1 = Hardness.H;
    private final static Hardness BALL_HARDNESS_2 = Hardness.S;
    private final static Integer BALL_UP_THROW_1 = 1;
    private final static Integer BALL_UP_THROW_2 = 43;
    private final static Integer BALL_WEIGHT_1 = 48;
    private final static Integer BALL_WEIGHT_2 = 30;
    private final static Double BALL_ANGLE_FACTOR_1 = 0d;
    private final static Double BALL_ANGLE_FACTOR_2 = 0.9;
    private final static String BALL_COMMENT_2 = "evtl. B+M K9";

    private final static Integer SUIT_CASE_PRIMARY_KEY_1 = 7;
    private final static Integer SUIT_CASE_PRIMARY_KEY_2 = 12;
    private final static String SUIT_CASE_IDENTIFIER_1 = "Koffer 1";
    private final static String SUIT_CASE_IDENTIFIER_2 = "Koffer 2";
    private final static String SUIT_CASE_DESCRIPTION_1 = "Test fuer die eine Bahn";
    private final static String SUIT_CASE_DESCRIPTION_2 = "Test fuer die andere Bahn";
    private final static String SUIT_CASE_OWNER_1 = "Ich";
    private final static String SUIT_CASE_OWNER_2 = "Du";

    private final static Integer CONTAIN_PRIMARY_KEY_1 = 2;
    private final static Integer CONTAIN_PRIMARY_KEY_2 = 4;
    private final static Integer CONTAIN_PRIMARY_KEY_3 = 28;
    private final static Integer CONTAIN_PRIMARY_KEY_4 = 48;
    private final static Integer CONTAIN_FOREIGN_KEY_1 = 45;
    private final static Integer CONTAIN_FOREIGN_KEY_2 = 718;
    private final static Integer CONTAIN_FOREIGN_KEY_3 = 51;
    private final static Integer CONTAIN_FOREIGN_KEY_4 = 71;

    private final static Integer CRAZY_GOLF_SITE_PRIMARY_KEY_1 = 8;
    private final static Integer CRAZY_GOLF_SITE_PRIMARY_KEY_2 = 21;
    private final static Integer CRAZY_GOLF_SITE_FOREIGN_KEY_1 = 7;
    private final static Integer CRAZY_GOLF_SITE_FOREIGN_KEY_2 = 12;
    private final static String CRAZY_GOLF_SITE_SITE_NAME_1 = "Neuhausen am Rheinfall";
    private final static String CRAZY_GOLF_SITE_SITE_NAME_2 = "Dietikon ";
    private final static String CRAZY_GOLF_SITE_ADDRESS_1 = "Langriet";
    private final static String CRAZY_GOLF_SITE_ADDRESS_2 = "Muehlimatt";
    private final static String CRAZY_GOLF_SITE_POST_CODE_1 = "CH-8212";
    private final static String CRAZY_GOLF_SITE_POST_CODE_2 = "8047";
    private final static String CRAZY_GOLF_SITE_TOWN_1 = "Neuhausen am Rheinfall";
    private final static String CRAZY_GOLF_SITE_TOWN_2 = "Dietikon";

    private final static Integer HANDICAP_PRIMARY_KEY_1 = 1;
    private final static Integer HANDICAP_PRIMARY_KEY_2 = 18;
    private final static Integer HANDICAP_PRIMARY_KEY_3 = 1;
    private final static Integer HANDICAP_PRIMARY_KEY_4 = 18;
    private final static Integer HANDICAP_FOREIGN_KEY_1 = 45;
    private final static Integer HANDICAP_FOREIGN_KEY_2 = 718;
    private final static Integer HANDICAP_FOREIGN_KEY_3 = 451;
    private final static Integer HANDICAP_FOREIGN_KEY_4 = 71;
    private final static String HANDICAP_POSITIONING_1 = "12/00";
    private final static String HANDICAP_POSITIONING_2 = "15/30";
    private final static String HANDICAP_POSITIONING_3 = "12/00";
    private final static String HANDICAP_POSITIONING_4 = "15/30";
    private final static String HANDICAP_CUSHIONING_1 = "links, 60 cm nach Verbindung";
    private final static String HANDICAP_CUSHIONING_2 = "";
    private final static String HANDICAP_CUSHIONING_3 = "rechts, nach 9. Platte";
    private final static String HANDICAP_CUSHIONING_4 = "rechts, auf Steinplatte";
    private final static String HANDICAP_MARKING_1 = "Fleck in Bande";
    private final static String HANDICAP_MARKING_2 = "Fleck nach 80cm auf der Bahn";
    private final static String HANDICAP_MARKING_3 = "";
    private final static String HANDICAP_MARKING_4 = "";
    private final static String HANDICAP_REMARK_1 = "mittelstark";
    private final static String HANDICAP_REMARK_2 = "";
    private final static String HANDICAP_REMARK_3 = "leicht";
    private final static String HANDICAP_REMARK_4 = "mit Drall nach rechts";

    private final String testFileName = "AllCharacteristics.pdf";
    private final List<BallCharacteristicsImpl> testListBall = new ArrayList<>(List.of());
    private final List<CrazyGolfSiteCharacteristicsImpl> testListCrazyGolfSite = new ArrayList<>(List.of());
    private final List<SuitCaseCharacteristicsImpl> testListSuitCase = new ArrayList<>(List.of());

    private File testFile;

    private AllCharacteristicsPdfExporter exporter;

    @Before
    public void setUp() throws Exception {
        testFile = new File(createTestPath(getClass().getSimpleName()) + "/" + testFileName);
        if (testFile.exists()) {
            if (!testFile.delete()) {
                fail("initialization of test failed");
            }
        }
        // prepare data - ball characteristics
        testListBall.add(new BallCharacteristicsImpl(BALL_PRIMARY_KEY_1,
                BALL_IDENTIFIER_1,
                BALL_DESCRIPTION_1,
                BALL_HARDNESS_1,
                BALL_UP_THROW_1,
                BALL_WEIGHT_1,
                BALL_ANGLE_FACTOR_1,
                ""));
        testListBall.add(new BallCharacteristicsImpl(BALL_PRIMARY_KEY_2,
                BALL_IDENTIFIER_2,
                BALL_DESCRIPTION_2,
                BALL_HARDNESS_2,
                BALL_UP_THROW_2,
                BALL_WEIGHT_2,
                BALL_ANGLE_FACTOR_2,
                BALL_COMMENT_2));
        // prepare data - suitcase characteristics
        SuitCaseCharacteristicsImpl SCCentry1 = new SuitCaseCharacteristicsImpl(SUIT_CASE_PRIMARY_KEY_1,
                SUIT_CASE_IDENTIFIER_1,
                SUIT_CASE_DESCRIPTION_1,
                SUIT_CASE_OWNER_1,
                1);
        SCCentry1.getContents().clear();
        SCCentry1.getContents().add(new ContentOfSuitCaseImpl(CONTAIN_PRIMARY_KEY_1,
                CONTAIN_FOREIGN_KEY_1));
        SCCentry1.getContents().add(new ContentOfSuitCaseImpl(CONTAIN_PRIMARY_KEY_2,
                CONTAIN_FOREIGN_KEY_2));
        testListSuitCase.add(SCCentry1);
        SuitCaseCharacteristicsImpl SCCentry2 = new SuitCaseCharacteristicsImpl(SUIT_CASE_PRIMARY_KEY_2,
                SUIT_CASE_IDENTIFIER_2,
                SUIT_CASE_DESCRIPTION_2,
                SUIT_CASE_OWNER_2,
                1);
        SCCentry2.getContents().clear();
        SCCentry2.getContents().add(new ContentOfSuitCaseImpl(CONTAIN_PRIMARY_KEY_3,
                CONTAIN_FOREIGN_KEY_3));
        SCCentry2.getContents().add(new ContentOfSuitCaseImpl(CONTAIN_PRIMARY_KEY_4,
                CONTAIN_FOREIGN_KEY_4));
        testListSuitCase.add(SCCentry2);
        // prepare data
        CrazyGolfSiteCharacteristicsImpl CGSCentry1 = new CrazyGolfSiteCharacteristicsImpl(CRAZY_GOLF_SITE_PRIMARY_KEY_1,
                CRAZY_GOLF_SITE_FOREIGN_KEY_1,
                CRAZY_GOLF_SITE_SITE_NAME_1,
                CRAZY_GOLF_SITE_ADDRESS_1,
                CRAZY_GOLF_SITE_POST_CODE_1,
                CRAZY_GOLF_SITE_TOWN_1);
        CGSCentry1.getContents().clear();
        CGSCentry1.getContents().add(new HandicapCharacteristicsImpl(HANDICAP_PRIMARY_KEY_1,
                HANDICAP_FOREIGN_KEY_1,
                HANDICAP_POSITIONING_1,
                HANDICAP_CUSHIONING_1,
                HANDICAP_MARKING_1,
                HANDICAP_REMARK_1));
        CGSCentry1.getContents().add(new HandicapCharacteristicsImpl(HANDICAP_PRIMARY_KEY_2,
                HANDICAP_FOREIGN_KEY_2,
                HANDICAP_POSITIONING_2,
                HANDICAP_CUSHIONING_2,
                HANDICAP_MARKING_2,
                HANDICAP_REMARK_2));
        testListCrazyGolfSite.add(CGSCentry1);
        CrazyGolfSiteCharacteristicsImpl CGSCentry2 = new CrazyGolfSiteCharacteristicsImpl(CRAZY_GOLF_SITE_PRIMARY_KEY_2,
                CRAZY_GOLF_SITE_FOREIGN_KEY_2,
                CRAZY_GOLF_SITE_SITE_NAME_2,
                CRAZY_GOLF_SITE_ADDRESS_2,
                CRAZY_GOLF_SITE_POST_CODE_2,
                CRAZY_GOLF_SITE_TOWN_2);
        CGSCentry2.getContents().clear();
        CGSCentry2.getContents().add(new HandicapCharacteristicsImpl(HANDICAP_PRIMARY_KEY_3,
                HANDICAP_FOREIGN_KEY_3,
                HANDICAP_POSITIONING_3,
                HANDICAP_CUSHIONING_3,
                HANDICAP_MARKING_3,
                HANDICAP_REMARK_3));
        CGSCentry2.getContents().add(new HandicapCharacteristicsImpl(HANDICAP_PRIMARY_KEY_4,
                HANDICAP_FOREIGN_KEY_4,
                HANDICAP_POSITIONING_4,
                HANDICAP_CUSHIONING_4,
                HANDICAP_MARKING_4,
                HANDICAP_REMARK_4));
        testListCrazyGolfSite.add(CGSCentry2);
        exporter = new AllCharacteristicsPdfExporter(testListBall,
                testListCrazyGolfSite,
                testListSuitCase,
                testFile);
    }

    @Test
    public void executeExport() throws IOException {
        exporter.executeExport();
    }

}