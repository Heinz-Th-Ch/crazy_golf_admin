package enrichers;

/**
 * The interface is the model for all relevant enrichment in this application.
 * @param <T>
 */
public interface Enricher <T> {

    /**
     * Enriches the containig fields inside a class.
     *
     * @param updatableData
     * @return true if all enrichment operations ended correctly, otherwise false.
     */
    boolean enrich(T updatableData);

}
