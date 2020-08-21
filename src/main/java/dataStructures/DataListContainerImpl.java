package dataStructures;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

/**
 * The implementation of {@link DataListContainer}.
 * All included lists are defined as <b>{@code final}</b>. Nevertheless the contents can be manipulated.
 */
public class DataListContainerImpl implements DataListContainer {

    private final List<BallCharacteristicsImpl> ballCharacteristics;
    private final List<SuitCaseCharacteristicsImpl> suitCaseCharacteristics;
    private final List<CrazyGolfSiteCharacteristicsImpl> crazyGolfSiteCharacteristics;

    /**
     * Constructs a new instance.
     *
     * @param ballCharacteristics
     * @param suitCaseCharacteristics
     * @param crazyGolfSiteCharacteristics
     */
    public DataListContainerImpl(List<BallCharacteristicsImpl> ballCharacteristics,
                                 List<SuitCaseCharacteristicsImpl> suitCaseCharacteristics,
                                 List<CrazyGolfSiteCharacteristicsImpl> crazyGolfSiteCharacteristics) {
        this.ballCharacteristics = ballCharacteristics;
        this.suitCaseCharacteristics = suitCaseCharacteristics;
        this.crazyGolfSiteCharacteristics = crazyGolfSiteCharacteristics;
    }

    /**
     * Returns the characteristics of all available crazy golf balls.
     *
     * @return
     */
    @Override
    public List<BallCharacteristicsImpl> getBallCharacteristics() {
        return ballCharacteristics;
    }

    public boolean loadBallCharacteristics(){
        return true;
    }

    public boolean storeBallCharacteristics(){
        return true;
    }

    /**
     * Returns the characteristics of all available suit cases, these include a pre-defined number of slot for crazy
     * golf balls.
     *
     * @return
     */
    @Override
    public List<SuitCaseCharacteristicsImpl> getSuitCaseCharacteristics() {
        return suitCaseCharacteristics;
    }

    public boolean loadSuitCaseCharacteristics(){
        return true;
    }

    public boolean storeSuitCaseCharacteristics(){
        return true;
    }

    /**
     * Returns the characteristics of all familiar crazy golf sites, these include characteristics of eighteen handicaps.
     * golf balls.
     *
     * @return
     */
    @Override
    public List<CrazyGolfSiteCharacteristicsImpl> getCrazyGolfSiteCharacteristics() {
        return crazyGolfSiteCharacteristics;
    }

    public boolean loadCrazyGolfSiteCharacteristics(){
        return true;
    }

    public boolean storeCrazyGolfSiteCharacteristics(){
        return true;
    }

    /**
     * Returns a string representation of the whole data container. It is created by {@link ReflectionToStringBuilder}.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
