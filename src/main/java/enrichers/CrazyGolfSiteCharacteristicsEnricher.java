package enrichers;

import dataStructures.CrazyGolfSiteCharacteristicsImpl;
import dataStructures.DataListContainerImpl;

import static dataStructures.CrazyGolfSiteCharacteristicsImpl.getNextPrimaryKey;

/**
 * This implementation of {@link Enricher} is used to enrich some elements of {@link CrazyGolfSiteCharacteristicsImpl}.
 * Actually are following fields in the enrich process involved:
 * <ol>
 * <li>{@code primaryKey}</li>
 * </ol>
 */
public class CrazyGolfSiteCharacteristicsEnricher implements Enricher<CrazyGolfSiteCharacteristicsImpl> {

    private final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    /**
     * Enriches the containig fields inside a class.
     *
     * @param updatableData
     * @return true if all enrichment operations ended correctly, otherwise false.
     */
    @Override
    public boolean enrich(CrazyGolfSiteCharacteristicsImpl updatableData) {
        if (updatableData.getPrimaryKey() > 0) {
            return false;
        }
        updatableData.setPrimaryKey(getNextPrimaryKey(dataListContainer.getCrazyGolfSiteCharacteristics()));
        return true;
    }
}
