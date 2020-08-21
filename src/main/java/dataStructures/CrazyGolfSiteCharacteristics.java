package dataStructures;

import java.util.List;

/**
 * The interface which allow the read view to a full crazy golf site.
 */

public interface CrazyGolfSiteCharacteristics {

    /**
     * Return the primary key, which is the number of the site.
     *
     * @return
     */
    Integer getPrimaryKey();

    /**
     * Returns the name of the site.
     *
     * @return
     */
    String getSiteName();

    /**
     * Returns the address of the site.
     *
     * @return
     */
    String getAddress();

    /**
     * Return the post code of the town in which the site is registered.
     *
     * @return
     */
    String getPostCode();

    /**
     * Return the town in which the site is registered.
     *
     * @return
     */
    String getTown();

    /**
     * Returns the characteristics of the 18 handicaps.
     *
     * @return
     */
    List<HandicapCharacteristicsImpl> getContents();

}
