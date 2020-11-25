package communications.datastructures;

import abstracts.AbstractPlainJava;
import enumerations.SessionType;
import org.junit.Test;
import runnables.ServiceSessionRunner;
import states.ApplicationStates;
import states.SessionStates;

import java.util.Properties;

/**
 * JUnit test for {@link ServiceSessionRunnerData};
 */
public class ServiceSessionRunnerDataTest extends AbstractPlainJava {

    private final String EXPECTED_RUNNER_ID_PART1 = "<runnerId>";
    private final String EXPECTED_RUNNER_ID_PART2 = "</runnerId>\n";
    private final String EXPECTED_RUNNER_NAME_PART1 = "<runnerName>Thread-";
    private final String EXPECTED_RUNNER_NAME_PART2 = "</runnerName>\n";
    private final String EXPECTED_RUNNER_STATE = "<runnerState>NEW</runnerState>\n";
    private final String EXPECTED_WITHOUT_RUNNER = "<serviceSessionRunnerData>\n" +
            "<runnerId>0</runnerId>\n" +
            "<runnerName>n/a</runnerName>\n" +
            "<runnerState>n/a</runnerState>\n" +
            "</serviceSessionRunnerData>\n";

    private final Properties properties = new Properties();
    private final ApplicationStates applicationStates = new ApplicationStates(getClass().getSimpleName());
    private final SessionStates sessionStates = new SessionStates("localhost",
            0,
            SessionType.CLIENT_SESSION);

    private ServiceSessionRunnerData testableData;

    @Test
    public void toXmlStringWithDefinedRunner() {
        // arrange
        testableData = new ServiceSessionRunnerData(new ServiceSessionRunner("runnerName",
                properties,
                applicationStates,
                sessionStates,
                null));
        // act
        String result = testableData.toXmlString();
        // assert
        assertTrue("wrong runner id received",
                result.contains(EXPECTED_RUNNER_ID_PART1)
                        && result.contains(EXPECTED_RUNNER_ID_PART2));
        assertTrue("wrong runner name received",
                result.contains(EXPECTED_RUNNER_NAME_PART1)
                        &&result.contains(EXPECTED_RUNNER_NAME_PART2));
        assertTrue("wrong runner state received",
                result.contains(EXPECTED_RUNNER_STATE));
    }

    @Test
    public void toXmlStringWithoutDefinedRunner() {
        // arrange
        testableData = new ServiceSessionRunnerData(null);
        // act and assert
        assertEquals("wrong result received", EXPECTED_WITHOUT_RUNNER, testableData.toXmlString());
    }

}