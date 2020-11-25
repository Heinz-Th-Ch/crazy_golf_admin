package enrichers;

import abstracts.AbstractPlainJava;
import dataStructures.BallCharacteristicsImpl;
import dataStructures.DataListContainerImpl;
import enumerations.Hardness;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for {@link BallCharacteristicsEnricher}.
 */
public class BallCharacteristicsEnricherTest extends AbstractPlainJava {

    private final DataListContainerImpl dataListContainer = new DataListContainerImpl();
    private final BallCharacteristicsEnricher enricher = new BallCharacteristicsEnricher();

    private Integer HIGHEST_PRIMARY_KEY = 0;

    @Before
    public void setup() {
        for (int i = 0; i < 8; i++) {
            HIGHEST_PRIMARY_KEY = i * 2;
            dataListContainer.getBallCharacteristics().add(new BallCharacteristicsImpl(HIGHEST_PRIMARY_KEY,
                    "ID",
                    "DESC",
                    Hardness.M,
                    50,
                    36,
                    0.5,
                    ""));
        }
    }

    @After
    public void tearDown() {
        dataListContainer.getBallCharacteristics().clear();
    }

    @Test
    public void enrichOkay() {
        // arrange
        BallCharacteristicsImpl characteristics = new BallCharacteristicsImpl(0,
                "ID",
                "DESC",
                Hardness.M,
                50,
                36,
                0.5,
                "");
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
        BallCharacteristicsImpl characteristics = new BallCharacteristicsImpl(1,
                "ID",
                "DESC",
                Hardness.M,
                50,
                36,
                0.5,
                "");
        // act and assert
        assertFalse("enrich executed", enricher.enrich(characteristics));
        // assert
        assertEquals("wrong primary key enriched",
                1,
                characteristics.getPrimaryKey().intValue());
    }

}