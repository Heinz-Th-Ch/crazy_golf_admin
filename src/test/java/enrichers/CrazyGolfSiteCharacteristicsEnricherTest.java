package enrichers;

import abstracts.AbstractPlainJava;
import dataStructures.CrazyGolfSiteCharacteristicsImpl;
import dataStructures.DataListContainerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for {@link CrazyGolfSiteCharacteristicsEnricher}.
 */
public class CrazyGolfSiteCharacteristicsEnricherTest extends AbstractPlainJava {

    private final DataListContainerImpl dataListContainer = new DataListContainerImpl();
    private final CrazyGolfSiteCharacteristicsEnricher enricher = new CrazyGolfSiteCharacteristicsEnricher();

    private Integer HIGHEST_PRIMARY_KEY = 0;

    @Before
    public void setUp() {
        for (int i = 0; i < 27; i++) {
            HIGHEST_PRIMARY_KEY = i * 5;
            dataListContainer.getCrazyGolfSiteCharacteristics().add(new CrazyGolfSiteCharacteristicsImpl(HIGHEST_PRIMARY_KEY,
                    1,
                    "SITE",
                    "ADDRESS",
                    "POSTCODE",
                    "TOWN"));
        }
    }

    @After
    public void tearDown() {
        dataListContainer.getCrazyGolfSiteCharacteristics().clear();
    }

    @Test
    public void enrichOkay() {
        // arrange
        CrazyGolfSiteCharacteristicsImpl characteristics = new CrazyGolfSiteCharacteristicsImpl(0,
                1,
                "SITE",
                "ADDRESS",
                "POSTCODE",
                "TOWN");
        // act and assert
        assertTrue("enrich not executed", enricher.enrich(characteristics));
        // assert
        assertEquals("wrong primary key enriched",
                HIGHEST_PRIMARY_KEY + 1,
                characteristics.getPrimaryKey().intValue());
    }

    @Test
    public void enrichNotOkayBecauseAlreadyEnriched() {
        // arrange
        CrazyGolfSiteCharacteristicsImpl characteristics = new CrazyGolfSiteCharacteristicsImpl(1,
                1,
                "SITE",
                "ADDRESS",
                "POSTCODE",
                "TOWN");
        // act and assert
        assertFalse("enrich executed", enricher.enrich(characteristics));
        // assert
        assertEquals("wrong primary key enriched",
                1,
                characteristics.getPrimaryKey().intValue());
    }

}