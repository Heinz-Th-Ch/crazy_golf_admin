package dataStructures;

import java.util.List;

/**
 * The interface which allow the read view of content of a crazy golf ball suite case.
 */
public interface SuitCaseCharacteristics {

    /**
     * Return the primary key, which is the number of the suitcase.
     *
     * @return
     */
    Integer getPrimaryKey();

    /**
     * Returns the identifier of the suitcase.
     *
     * @return the identifier
     */
    String getIdentifier();

    /**
     * Returns a description of the suitcase.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Returns the owner of the suitcase.
     *
     * @return the owner
     */
    String getOwner();

    /**
     * Returns a list of all slots inside the suitcase.
     *
     * @return a list of <b>all</b> slots, whatever they are taken or not
     */
    List<ContentOfSuitcaseImpl> getContents();

}
