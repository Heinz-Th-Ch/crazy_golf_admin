package communications.datastructures;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * This class is used to transport several data of the client sessions.<br>
 * It is used inside the class {@link ApplicationStatesData}.
 * <p>
 * In a next step, this class wil be replaced by an xml object with sam name.
 * </p>
 */
public class ClientSessionStatesData implements Serializable {

    private final String NEW_LINE = "\n";

    private final Integer listSize;

    public ClientSessionStatesData(Integer listSize) {
        this.listSize = listSize;
    }

    public Integer getListSize() {
        return listSize;
    }

    /**
     * Returns a string representation of the ball. It is created by {@link ReflectionToStringBuilder}.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    /**
     * Returns a string representation of the socket data as a xml representation.
     *
     * @return
     */
    public String toXmlString() {
        StringBuffer result = new StringBuffer(String.format("<clientSessionStatesData>%s", NEW_LINE));

        result.append(String.format("<listSize>%s</listSize>%s", getListSize(), NEW_LINE));

        result.append(String.format("</clientSessionStatesData>%s", NEW_LINE));

        return result.toString();
    }

}
