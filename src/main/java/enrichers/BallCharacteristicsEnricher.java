package enrichers;

import dataStructures.BallCharacteristicsImpl;
import dataStructures.DataListContainerImpl;

import static dataStructures.BallCharacteristicsImpl.getNextPrimaryKey;

/**
 * This implementation of {@link Enricher} is used to enrich some elements of {@link BallCharacteristicsImpl}.
 * Actually are following fields in the enrich process involved:
 * <ol>
 * <li>{@code primaryKey}</li>
 * </ol>
 */
public class BallCharacteristicsEnricher implements Enricher<BallCharacteristicsImpl> {

    private final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    /**
     * Enriches the containig fields inside a class.
     *
     * @param updatableData
     * @return true if all enrichment operations ended correctly, otherwise false.
     */
    @Override
    public boolean enrich(BallCharacteristicsImpl updatableData) {
        if (updatableData.getPrimaryKey() > 0) {
            return false;
        }
        updatableData.setPrimaryKey(getNextPrimaryKey(dataListContainer.getBallCharacteristics()));
        return true;
    }

}
