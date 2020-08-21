package dataStructures;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of {@link CrazyGolfSiteCharacteristics}.
 * The primary key is {@link CrazyGolfSiteCharacteristicsImpl#primaryKey}.
 * An additional combined unique key is consisting of {@link CrazyGolfSiteCharacteristicsImpl#siteName},
 * {@link CrazyGolfSiteCharacteristicsImpl#postCode} and {@link CrazyGolfSiteCharacteristicsImpl#town}.
 */

public class CrazyGolfSiteCharacteristicsImpl implements CrazyGolfSiteCharacteristics {

    private final static Integer NUMBER_OF_HANDICAPS = 18;

    /**
     * Primary key inside a group of {@link CrazyGolfSiteCharacteristicsImpl}.
     */
    private final List<HandicapCharacteristicsImpl> contents;

    private Integer primaryKey;
    private String siteName;
    private String address;
    private String postCode;
    private String town;

    /**
     * Constructs a new instance.
     *
     * @param primaryKey
     * @param siteName
     * @param address
     * @param postCode
     * @param town
     */
    public CrazyGolfSiteCharacteristicsImpl(Integer primaryKey,
                                            String siteName,
                                            String address,
                                            String postCode,
                                            String town) {
        this.primaryKey = primaryKey;
        this.siteName = siteName;
        this.address = address;
        this.postCode = postCode;
        this.town = town;
        List<HandicapCharacteristicsImpl> tempList = new ArrayList<>(List.of());
        for (int i = 0; i < NUMBER_OF_HANDICAPS; i++) {
            tempList.add(new HandicapCharacteristicsImpl(tempList));
        }
        this.contents = tempList;
    }

    /**
     * Constructs a new instance.
     *
     * @param list
     * @param crazyGolfSiteCharacteristics
     */
    public CrazyGolfSiteCharacteristicsImpl(List<CrazyGolfSiteCharacteristicsImpl> list, CrazyGolfSiteCharacteristicsImpl crazyGolfSiteCharacteristics) {
        this.primaryKey = getNextPrimaryKey(list);
        this.siteName = crazyGolfSiteCharacteristics.getSiteName();
        this.address = crazyGolfSiteCharacteristics.getAddress();
        this.postCode = crazyGolfSiteCharacteristics.getPostCode();
        this.town = crazyGolfSiteCharacteristics.getTown();
        this.contents = crazyGolfSiteCharacteristics.getContents();

    }

    /**
     * Constructs a new instance.
     *
     * @param list
     * @param siteName
     * @param address
     * @param postCode
     * @param town
     * @param contents
     */
    public CrazyGolfSiteCharacteristicsImpl(List<CrazyGolfSiteCharacteristicsImpl> list,
                                            String siteName,
                                            String address,
                                            String postCode,
                                            String town,
                                            List<HandicapCharacteristicsImpl> contents) {
        this.primaryKey = getNextPrimaryKey(list);
        this.siteName = siteName;
        this.address = address;
        this.postCode = postCode;
        this.town = town;
        this.contents = contents;
    }

    /**
     * Evaluates the highest primary key in list and returns the next possible primary key.
     *
     * @param list a group of {@link CrazyGolfSiteCharacteristicsImpl}.
     * @return the new possible primary key.
     */
    public static int getNextPrimaryKey(List<CrazyGolfSiteCharacteristicsImpl> list) {
        int oldPrimaryKey = 0;
        for (CrazyGolfSiteCharacteristicsImpl entry : list) {
            if (entry.getPrimaryKey() > oldPrimaryKey) {
                oldPrimaryKey = entry.getPrimaryKey();
            }
        }
        return oldPrimaryKey + 1;
    }

    /**
     * Compares this string representation to the string representation of the specified object.
     * This comparison is without the primary key.
     *
     * @param testObject
     * @return true if equal, otherwise false
     */
    public boolean equals(CrazyGolfSiteCharacteristicsImpl testObject) {
        if (this == testObject) {
            return true;
        }
        return siteName.equals(testObject.getSiteName())
                && address.equals(testObject.getAddress())
                && postCode.equals(testObject.getPostCode())
                && town.equals(testObject.getTown())
                && contents.equals(testObject.getContents());
    }

    /**
     * Checks on the one hand the primary key and otherwise the combination of identifier, description and owner
     * against the content of a list of {@link CrazyGolfSiteCharacteristicsImpl} for uniqueness.
     *
     * @return true if is unique, otherwise false
     */
    public boolean isUnique(List<CrazyGolfSiteCharacteristicsImpl> list) {
        for (CrazyGolfSiteCharacteristicsImpl entry : list) {
            if (this != entry) {
                if (primaryKey.equals(entry.getPrimaryKey())
                        || (siteName.equals(entry.getSiteName())
                        && postCode.equals(entry.getPostCode())
                        && town.equals(entry.getTown()))) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Return the primary key, which is the number of the site.
     *
     * @return
     */
    @Override
    public Integer getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Sets the primary key, which is the number of the site.
     *
     * @param primaryKey
     */
    public void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Returns the name of the site.
     *
     * @return
     */
    @Override
    public String getSiteName() {
        return siteName;
    }

    /**
     * Sets the name of the site.
     *
     * @param siteName
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * Returns the address of the site.
     *
     * @return
     */
    @Override
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the site.
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Return the post code of the town in which the site is registered.
     *
     * @return
     */
    @Override
    public String getPostCode() {
        return postCode;
    }

    /**
     * Sets the post code of the town in which the site is registered.
     *
     * @param postCode
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * Return the town in which the site is registered.
     *
     * @return
     */
    @Override
    public String getTown() {
        return town;
    }

    /**
     * Sets the town in which the site is registered.
     *
     * @param town
     */
    public void setTown(String town) {
        this.town = town;
    }

    /**
     * Returns the characteristics of the 18 handicaps.
     *
     * @return
     */
    @Override
    public List<HandicapCharacteristicsImpl> getContents() {
        return contents;
    }

    /**
     * Returns a string representation of the crazy golf site. It is created by {@link ReflectionToStringBuilder}.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}