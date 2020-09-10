package dataStructures;

import abstracts.AbstractPlainJava;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit tests of {@link ContentOfSuitCaseImpl}.
 */
public class ContentOfSuitCaseImplTest extends AbstractPlainJava {

    private final static Integer ORIG_INDEX = 0;

    private final static Integer ORIG_FOREIGN_KEY = 20;

    private final static Integer OTHER_FOREIGN_KEY = 21;

    private final static Integer NULL_INDEX = null;
    private final static List<ContentOfSuitCaseImpl> NULL_LIST = null;

    private ContentOfSuitCaseImpl originalContentOfSuitcase;
    private ContentOfSuitCaseImpl otherContentOfSuitcase;

    @Before
    public void setUp() {
        originalContentOfSuitcase = new ContentOfSuitCaseImpl(ORIG_INDEX,
                ORIG_FOREIGN_KEY);
        resetOtherContentOfSuitcase(List.of());
    }

    @Test
    public void getNextPrimaryKey() {
        List<ContentOfSuitCaseImpl> testList = new ArrayList<>(List.of());
        testList.add(originalContentOfSuitcase);
        resetOtherContentOfSuitcase(testList);
        testList.add(otherContentOfSuitcase);
        ContentOfSuitCaseImpl thirdContentOfSuitcase = new ContentOfSuitCaseImpl(testList,
                originalContentOfSuitcase);
        testList.add(thirdContentOfSuitcase);
        assertEquals("invalid new primary key",
                3,
                ContentOfSuitCaseImpl.getNextPrimaryKey(testList));
    }

    @Test
    public void equals() {
        assertTrue("objects are not equal",
                originalContentOfSuitcase.equals(originalContentOfSuitcase));
        assertTrue("data is not equal",
                originalContentOfSuitcase.equals(otherContentOfSuitcase));
        otherContentOfSuitcase.setForeignKeyBall(OTHER_FOREIGN_KEY);
        assertFalse("foreign key is equal",
                originalContentOfSuitcase.equals(otherContentOfSuitcase));
    }

    @Test
    public void isUnique() {
        List<ContentOfSuitCaseImpl> testList = new ArrayList<>(List.of());
        testList.add(originalContentOfSuitcase);
        assertTrue("primary key not unique",
                originalContentOfSuitcase.isUnique(testList));
        testList.add(otherContentOfSuitcase);
        otherContentOfSuitcase.setPrimaryKey(ORIG_INDEX);
        assertFalse("primary key unexpected unique",
                originalContentOfSuitcase.isUnique(testList));
        otherContentOfSuitcase.setPrimaryKey(otherContentOfSuitcase.getPrimaryKey() + 1);
        assertFalse("data combination unexpected unique",
                originalContentOfSuitcase.isUnique(testList));
        otherContentOfSuitcase.setForeignKeyBall(OTHER_FOREIGN_KEY);
        assertTrue("data combination not unique",
                originalContentOfSuitcase.isUnique(testList));
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForIndex() {
        new ContentOfSuitCaseImpl(NULL_INDEX,
                ORIG_FOREIGN_KEY);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForForeignKey() {
        new ContentOfSuitCaseImpl(ORIG_INDEX,
                null);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForListOnly() {
        new ContentOfSuitCaseImpl(NULL_LIST);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForListAndFullCharacteristic() {
        new ContentOfSuitCaseImpl(NULL_LIST,
                originalContentOfSuitcase);
    }

    private void resetOtherContentOfSuitcase(List<ContentOfSuitCaseImpl> list) {
        otherContentOfSuitcase = new ContentOfSuitCaseImpl(list, originalContentOfSuitcase);
    }

}