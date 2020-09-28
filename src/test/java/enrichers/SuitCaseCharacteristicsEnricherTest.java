package enrichers;

import abstracts.AbstractPlainJava;
import dataStructures.DataListContainerImpl;
import dataStructures.SuitCaseCharacteristicsImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for {@link SuitCaseCharacteristicsEnricher}.
 */
public class SuitCaseCharacteristicsEnricherTest extends AbstractPlainJava {

    private final DataListContainerImpl dataListContainer = new DataListContainerImpl();
    private final SuitCaseCharacteristicsEnricher enricher = new SuitCaseCharacteristicsEnricher();

    private Integer HIGHEST_PRIMARY_KEY = 0;

    @Before
    public void setUp() {
        for (int i = 0; i < 82; i++) {
            HIGHEST_PRIMARY_KEY = i * 71;
            dataListContainer.getSuitCaseCharacteristics().add(new SuitCaseCharacteristicsImpl(HIGHEST_PRIMARY_KEY,
                    "ID",
                    "DESC",
                    "OWNER",
                    i * 3));
        }
    }

    @Test
    public void enrichOkay() {
        SuitCaseCharacteristicsImpl characteristics = new SuitCaseCharacteristicsImpl(0,
                "ID",
                "DESC",
                "OWNER",
                15);
        assertTrue("enrich not executed", enricher.enrich(characteristics));
        assertEquals("wrong primary key enriched",
                HIGHEST_PRIMARY_KEY + 1,
                characteristics.getPrimaryKey().intValue());
    }

    @Test
    public void enrichNotOkayBecauseAlreadyEnriched() {
        SuitCaseCharacteristicsImpl characteristics = new SuitCaseCharacteristicsImpl(1,
                "ID",
                "DESC",
                "OWNER",
                25);
        assertFalse("enrich executed", enricher.enrich(characteristics));
        assertEquals("wrong primary key enriched",
                1,
                characteristics.getPrimaryKey().intValue());
    }

}