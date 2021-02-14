import student.TestCase;

/**
 * @author Chris Luersen + Hulya Dogan
 * @version 09/15/2020
 *          Testing DoublyLinked List class.
 */
public class DoublyLinkedListTest extends TestCase {
// Fields
    private DoublyLinkedList<String> doublyLinkedList;

// Constructor
    /**
     * Sets up tests.
     */
    @Override
    public void setUp() {
        doublyLinkedList = new DoublyLinkedList<String>();
    }


// Metods
    /**
     * Tests the add method.
     */
    public void testAdd() {
        assertEquals(0, doublyLinkedList.size());
        doublyLinkedList.add("test1");
        assertEquals(1, doublyLinkedList.size());
        doublyLinkedList.add("test2");
        assertEquals(2, doublyLinkedList.size());
        assertEquals("test2", doublyLinkedList.get(1));

    }


    /**
     * Tests adding to beginning and end
     */
    public void testAddIndex() {
        doublyLinkedList.add("test2");
        doublyLinkedList.add(0, "test1");
        assertEquals("test1", doublyLinkedList.get(0));
        assertEquals(2, doublyLinkedList.size());
        doublyLinkedList.add(2, "test4");
        assertEquals("test4", doublyLinkedList.get(2));
        doublyLinkedList.add(2, "test3");
        assertEquals("test3", doublyLinkedList.get(2));
    }


    /**
     * Tests exceptions for add method
     */
    public void testAddNullException() {
        Exception e = null;
        try {
            doublyLinkedList.add(null);
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IllegalArgumentException);
    }


    /**
     * Tests throwing nullPointerException
     */
    public void testAddIndexNullException() {
        Exception e = null;
        try {
            doublyLinkedList.add(0, null);
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IllegalArgumentException);
    }


    /**
     * Tests throwing invalidArgumentException
     */
    public void testAddException() {
        doublyLinkedList.add("test1");
        Exception e = null;
        try {
            doublyLinkedList.add(2, "test2");
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
        e = null;
        try {
            doublyLinkedList.add(-1, "test2");
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
    }


    /**
     * Tests the remove method
     */
    public void testRemoveException() {
        doublyLinkedList.add("test1");
        Exception e = null;
        try {
            doublyLinkedList.remove(2);
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
        e = null;
        try {
            doublyLinkedList.remove(-1);
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
    }


    /**
     * Tests remove first instance.
     */
    public void testRemoveIndex() {
        doublyLinkedList.add("test1");
        doublyLinkedList.add("test2");
        assertTrue(doublyLinkedList.remove(1));
        assertEquals(1, doublyLinkedList.size());
        doublyLinkedList.add("test2");
        assertTrue(doublyLinkedList.remove(0));
        assertEquals(1, doublyLinkedList.size());
    }


    /**
     * Tests removing object in list.
     */
    public void testRemoveObj() {
        assertFalse(doublyLinkedList.remove(null));
        doublyLinkedList.add("test1");
        doublyLinkedList.add("test2");
        assertTrue(doublyLinkedList.remove("test1"));
        assertEquals("test2", doublyLinkedList.get(0));
        assertEquals(1, doublyLinkedList.size());
        doublyLinkedList.add("test3");
        assertTrue(doublyLinkedList.remove("test3"));
        assertEquals("test2", doublyLinkedList.get(0));
    }


    /**
     * Tests remove method from an empty list
     */
    public void testRemoveFromEmpty() {
        doublyLinkedList.add("dance");
        doublyLinkedList.add(0, "safety");
        doublyLinkedList.clear();
        assertFalse(doublyLinkedList.remove("safety"));
        Exception exception;
        exception = null;
        try {
            doublyLinkedList.remove(0);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);

        DoublyLinkedList<String> emptyList = new DoublyLinkedList<String>();
        exception = null;
        try {
            emptyList.remove(0);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
    }


    /**
     * Tests getter throws exception.
     */
    public void testGetException() {
        Exception exception = null;
        try {
            doublyLinkedList.get(-1);
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
        exception = null;
        doublyLinkedList.add("test1");
        try {
            doublyLinkedList.get(1);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
    }


    /**
     * Test contains method
     */
    public void testContains() {
        assertFalse(doublyLinkedList.contains("test1"));
        doublyLinkedList.add("test1");
        assertTrue(doublyLinkedList.contains("test1"));
        assertFalse(doublyLinkedList.contains("test2"));
        doublyLinkedList.add("test2");
        assertTrue(doublyLinkedList.contains("test2"));
    }


    /**
     * Test if can get data for the last position
     */
    public void testLastIndexOf() {
        assertEquals(-1, doublyLinkedList.lastIndexOf("test1"));
        doublyLinkedList.add("test1");
        assertEquals(0, doublyLinkedList.lastIndexOf("test1"));
        doublyLinkedList.add("test1");
        assertEquals(1, doublyLinkedList.lastIndexOf("test1"));
        doublyLinkedList.add("test2");
        assertEquals(1, doublyLinkedList.lastIndexOf("test1"));
        assertEquals(2, doublyLinkedList.lastIndexOf("test2"));
        doublyLinkedList.add("test1");
        assertEquals(3, doublyLinkedList.lastIndexOf("test1"));
    }


    /**
     * Tests isEmpy method
     */
    public void testIsEmpty() {
        assertTrue(doublyLinkedList.isEmpty());
        doublyLinkedList.add("test1");
        assertFalse(doublyLinkedList.isEmpty());
    }


    /**
     * Tests clear exception
     */
    public void testClear() {
        doublyLinkedList.add("test1");
        doublyLinkedList.clear();
        assertEquals(0, doublyLinkedList.size());
        assertFalse(doublyLinkedList.contains("test1"));
    }


    /**
     * Tests list is translated into string correctly
     */
    public void testToString() {
        assertEquals("{}", doublyLinkedList.toString());
        doublyLinkedList.add("test1");
        assertEquals("{test1}", doublyLinkedList.toString());
        doublyLinkedList.add("test2");
        assertEquals("{test1, test2}", doublyLinkedList.toString());
    }

}
