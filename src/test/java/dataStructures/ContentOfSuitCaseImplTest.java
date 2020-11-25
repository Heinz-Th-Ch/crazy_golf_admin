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
        // arrange
        List<ContentOfSuitCaseImpl> testList = new ArrayList<>(List.of());
        testList.add(originalContentOfSuitcase);
        resetOtherContentOfSuitcase(testList);
        testList.add(otherContentOfSuitcase);
        ContentOfSuitCaseImpl thirdContentOfSuitcase = new ContentOfSuitCaseImpl(testList,
                originalContentOfSuitcase);
        testList.add(thirdContentOfSuitcase);
        // act and assert
        assertEquals("invalid new primary key",
                3,
                ContentOfSuitCaseImpl.getNextPrimaryKey(testList));
    }

    @Test
    public void equals() {
        // act and assert 1
        assertTrue("objects are not equal",
                originalContentOfSuitcase.equals(originalContentOfSuitcase));
        assertTrue("data is not equal",
                originalContentOfSuitcase.equals(otherContentOfSuitcase));
        // arrange 2
        otherContentOfSuitcase.setForeignKeyBall(OTHER_FOREIGN_KEY);
        // act and assert 2
        assertFalse("foreign key is equal",
                originalContentOfSuitcase.equals(otherContentOfSuitcase));
    }

    @Test
    public void isUnique() {
        // arrange 1
        List<ContentOfSuitCaseImpl> testList = new ArrayList<>(List.of());
        testList.add(originalContentOfSuitcase);
        // act and assert 1
        assertTrue("primary key not unique",
                originalContentOfSuitcase.isUnique(testList));
        // arrange 2
        testList.add(otherContentOfSuitcase);
        otherContentOfSuitcase.setPrimaryKey(ORIG_INDEX);
        // act and assert 2
        assertFalse("primary key unexpected unique",
                originalContentOfSuitcase.isUnique(testList));
        // arrange 3
        otherContentOfSuitcase.setPrimaryKey(otherContentOfSuitcase.getPrimaryKey() + 1);
        // act and assert 3
        assertFalse("data combination unexpected unique",
                originalContentOfSuitcase.isUnique(testList));
        // arrange 4
        otherContentOfSuitcase.setForeignKeyBall(OTHER_FOREIGN_KEY);
        // act and assert 4
        assertTrue("data combination not unique",
                originalContentOfSuitcase.isUnique(testList));
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForIndex() {
        // act and assert
        new ContentOfSuitCaseImpl(NULL_INDEX,
                ORIG_FOREIGN_KEY);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForForeignKey() {
        // act and assert
        new ContentOfSuitCaseImpl(ORIG_INDEX,
                null);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForListOnly() {
        // act and assert
        new ContentOfSuitCaseImpl(NULL_LIST);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullForListAndFullCharacteristic() {
        // act and assert
        new ContentOfSuitCaseImpl(NULL_LIST,
                originalContentOfSuitcase);
    }

    private void resetOtherContentOfSuitcase(List<ContentOfSuitCaseImpl> list) {
        otherContentOfSuitcase = new ContentOfSuitCaseImpl(list, originalContentOfSuitcase);
    }

}