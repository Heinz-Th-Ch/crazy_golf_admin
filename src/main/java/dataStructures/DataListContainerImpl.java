package dataStructures;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of {@link DataListContainer}.
 * All included lists are defined as <b>{@code final}</b>. Nevertheless the contents can be manipulated.
 */
public class DataListContainerImpl implements DataListContainer {

    private static List<SuitCaseCharacteristicsImpl> suitCaseCharacteristics = new ArrayList<>(List.of());
    private static List<CrazyGolfSiteCharacteristicsImpl> crazyGolfSiteCharacteristics = new ArrayList<>(List.of());
    private static List<BallCharacteristicsImpl> ballCharacteristics = new ArrayList<>(List.of());
    private static Boolean ballCharacteristicsChanged = false;
    private static Boolean crazyGolfSiteCharacteristicsChanged = false;
    private static Boolean suitCaseCharacteristicsChanged = false;

    /**
     * Returns the characteristics of all available crazy golf balls.
     *
     * @return
     */
    @Override
    public synchronized List<BallCharacteristicsImpl> getBallCharacteristics() {
        return ballCharacteristics;
    }

    /**
     * Loads the relevant data from a predefined file.
     *
     * @param fis
     * @return true if the action ended correctly, else false
     */
    public synchronized boolean loadBallCharacteristics(FileInputStream fis) throws IOException, ClassNotFoundException {
        ballCharacteristics.clear();
        ObjectInputStream stream = new ObjectInputStream(fis);
        ballCharacteristics = (List<BallCharacteristicsImpl>) stream.readObject();
        stream.close();
        return true;
    }

    /**
     * Stores the relevant data into a predefined file.
     *
     * @param fos
     * @return true if the action ended correctly, else false
     */
    public synchronized boolean storeBallCharacteristics(FileOutputStream fos) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(fos);
        stream.writeObject(ballCharacteristics);
        stream.close();
        return true;
    }

    /**
     * Returns the characteristics of all available suit cases, these include a pre-defined number of slot for crazy
     * golf balls.
     *
     * @return
     */
    @Override
    public synchronized List<SuitCaseCharacteristicsImpl> getSuitCaseCharacteristics() {
        return suitCaseCharacteristics;
    }

    /**
     * Loads the relevant data from a predefined file.
     *
     * @param fis
     * @return true if the action ended correctly, else false
     */
    public synchronized boolean loadSuitCaseCharacteristics(FileInputStream fis) throws IOException, ClassNotFoundException {
        suitCaseCharacteristics.clear();
        ObjectInputStream stream = new ObjectInputStream(fis);
        suitCaseCharacteristics = (List<SuitCaseCharacteristicsImpl>) stream.readObject();
        stream.close();
        return true;
    }

    /**
     * Stores the relevant data into a predefined file.
     *
     * @param fos
     * @return true if the action ended correctly, else false
     */
    public synchronized boolean storeSuitCaseCharacteristics(FileOutputStream fos) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(fos);
        stream.writeObject(suitCaseCharacteristics);
        stream.close();
        return true;
    }

    /**
     * Returns the characteristics of all familiar crazy golf sites, these include characteristics of eighteen handicaps.
     * golf balls.
     *
     * @return
     */
    @Override
    public synchronized List<CrazyGolfSiteCharacteristicsImpl> getCrazyGolfSiteCharacteristics() {
        return crazyGolfSiteCharacteristics;
    }

    /**
     * Returns the change information of crazy golf balls.
     *
     * @return true, if changed, otherwise false
     */
    @Override
    public synchronized boolean isBallCharacteristicsChanged() {
        return ballCharacteristicsChanged;
    }

    /**
     * Resets the change information of crazy golf balls.
     */
    @Override
    public synchronized void resetBallCharacteristicsChanged() {
        ballCharacteristicsChanged = false;
    }

    /**
     * Sets the change information of crazy golf balls.
     */
    @Override
    public synchronized void setBallCharacteristicsChanged() {
        ballCharacteristicsChanged = true;
    }

    /**
     * Returns the change information of suit cases.
     *
     * @return true, if changed, otherwise false
     */
    @Override
    public synchronized boolean isSuitCaseCharacteristicsChanged() {
        return suitCaseCharacteristicsChanged;
    }

    /**
     * Resets the change information of suit cases.
     */
    @Override
    public synchronized void resetSuitCaseCharacteristicsChanged() {
        suitCaseCharacteristicsChanged = false;
    }

    /**
     * Sets the change information of suit cases.
     */
    @Override
    public synchronized void setSuitCaseCharacteristicsChanged() {
        suitCaseCharacteristicsChanged = true;
    }

    /**
     * Returns the change information of crazy golf sites.
     *
     * @return true, if changed, otherwise false
     */
    @Override
    public synchronized boolean isCrazyGolfSiteCharacteristicsChanged() {
        return crazyGolfSiteCharacteristicsChanged;
    }

    /**
     * Resets the change information of crazy golf sites
     */
    @Override
    public synchronized void resetCrazyGolfSiteCharacteristicsChanged() {
        crazyGolfSiteCharacteristicsChanged = false;
    }

    /**
     * Sets the change information of crazy golf sites
     */
    @Override
    public synchronized void setCrazyGolfSiteCharacteristicsChanged() {
        crazyGolfSiteCharacteristicsChanged = true;
    }

    /**
     * Loads the relevant data from a predefined file.
     *
     * @param fis
     * @return true if the action ended correctly, else false
     */
    public synchronized boolean loadCrazyGolfSiteCharacteristics(FileInputStream fis) throws IOException, ClassNotFoundException {
        crazyGolfSiteCharacteristics.clear();
        ObjectInputStream stream = new ObjectInputStream(fis);
        crazyGolfSiteCharacteristics = (List<CrazyGolfSiteCharacteristicsImpl>) stream.readObject();
        stream.close();
        return true;
    }

    /**
     * Stores the relevant data into a predefined file.
     *
     * @param fos
     * @return true if the action ended correctly, else false
     */
    public synchronized boolean storeCrazyGolfSiteCharacteristics(FileOutputStream fos) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(fos);
        stream.writeObject(crazyGolfSiteCharacteristics);
        stream.close();
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
