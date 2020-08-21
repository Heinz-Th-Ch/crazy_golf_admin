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

}
