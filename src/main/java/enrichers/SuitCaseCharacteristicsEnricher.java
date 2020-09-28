package enrichers;

import dataStructures.DataListContainerImpl;
import dataStructures.SuitCaseCharacteristicsImpl;

import static dataStructures.SuitCaseCharacteristicsImpl.getNextPrimaryKey;

/**
 * This implementation of {@link Enricher} is used to enrich some elements of {@link SuitCaseCharacteristicsImpl}.
 * Actually are following fields in the enrich process involved:
 * <ol>
 * <li>{@code primaryKey}</li>
 * </ol>
 */
public class SuitCaseCharacteristicsEnricher implements Enricher<SuitCaseCharacteristicsImpl> {

    private final DataListContainerImpl dataListContainer = new DataListContainerImpl();

    /**
     * Enriches the containig fields inside a class.
     *
     * @param updatableData
     * @return true if all enrichment operations ended correctly, otherwise false.
     */
    @Override
    public boolean enrich(SuitCaseCharacteristicsImpl updatableData) {
        if (updatableData.getPrimaryKey() > 0) {
            return false;
        }
        updatableData.setPrimaryKey(getNextPrimaryKey(dataListContainer.getSuitCaseCharacteristics()));
        return true;
    }
}
