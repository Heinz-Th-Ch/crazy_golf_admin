package dataStructures;

import java.util.List;

/**
 * The interface which allow to read a view to all relevant lists of ...
 * <li>{@link BallCharacteristicsImpl}</li>
 * <li>{@link SuitCaseCharacteristicsImpl}</li>
 * <li>... inside {@link ContentOfSuitCaseImpl}</li>
 * <li>{@link CrazyGolfSiteCharacteristicsImpl}</li>
 * <li>... inside {@link HandicapCharacteristicsImpl}</li>
 */
public interface DataListContainer {

    /**
     * Returns the characteristics of all available crazy golf balls.
     *
     * @return
     */
    List<BallCharacteristicsImpl> getBallCharacteristics();

    /**
     * Returns the characteristics of all available suit cases, these include a pre-defined number of slot for crazy
     * golf balls.
     *
     * @return
     */
    List<SuitCaseCharacteristicsImpl> getSuitCaseCharacteristics();

    /**
     * Returns the characteristics of all familiar crazy golf sites, these include characteristics of eighteen handicaps.
     * golf balls.
     *
     * @return
     */
    List<CrazyGolfSiteCharacteristicsImpl> getCrazyGolfSiteCharacteristics();

    /**
     * Returns the change information of crazy golf balls.
     *
     * @return true, if changed, otherwise false
     */
    boolean isBallCharacteristicsChanged();

    /**
     * Resets the change information of crazy golf balls.
     */
    void resetBallCharacteristicsChanged();

    /**
     * Sets the change information of crazy golf balls.
     */
    void setBallCharacteristicsChanged();

    /**
     * Returns the change information of suit cases.
     *
     * @return true, if changed, otherwise false
     */
    boolean isSuitCaseCharacteristicsChanged();

    /**
     * Resets the change information of suit cases.
     */
    void resetSuitCaseCharacteristicsChanged();

    /**
     * Sets the change information of suit cases.
     */
    void setSuitCaseCharacteristicsChanged();

    /**
     * Returns the change information of crazy golf sites.
     *
     * @return true, if changed, otherwise false
     */
    boolean isCrazyGolfSiteCharacteristicsChanged();

    /**
     * Resets the change information of crazy golf sites
     */
    void resetCrazyGolfSiteCharacteristicsChanged();

    /**
     * Sets the change information of crazy golf sites
     */
    void setCrazyGolfSiteCharacteristicsChanged();

}
