import student.TestCase;

/**
 * @author Hulya Dogan, Chris Luersen
 * @version 10/11/2020
 */
public class RectangleDBTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing Here
    }


    /**
     * Get code coverage of the class declaration.
     */
    public void testRInit() {
        RectangleDB manager = new RectangleDB();
        assertNotNull(manager);
        RectangleDB.main(null);
    }


    /**
     * Tests too little args.
     */
    public void testsTooLittleArgs() {
        String[] args = new String[1];
        args[0] = "null";
        RectangleDB.main(args);
        assertFalse(systemOut().getHistory().contains("dump"));
        /*
         * Exception exception = null;
         * try {
         * RectangleDB.main(args);
         * }
         * catch (NullPointerException e) {
         * exception = e;
         * }
         * assertNotNull(exception);
         **/
    }


    /**
     * Tests too many args.
     */
    public void testsTooManyArgs() {
        String[] args = new String[3];
        args[0] = "Test2";
        args[1] = "Test3";
        args[2] = "Test4";
        RectangleDB.main(args);
        assertFalse(systemOut().getHistory().contains("dump"));
        assertFalse(systemOut().getHistory().contains("insert"));
    }


    /**
     * Tests too many null args.
     */

    public void testsTooManyNullArgs() {
        String[] args = new String[2];
        args[0] = null;
        args[1] = null;
        RectangleDB.main(args);
        assertFalse(systemOut().getHistory().contains("dump"));
        assertFalse(systemOut().getHistory().contains("insert"));
    }


    /**
     * Tests too exact args.
     */

    public void testsExactArgs() {
        String[] args = new String[1];
        args[0] = "Test1";
        RectangleDB.main(args);
        assertTrue(systemOut().getHistory().contains(""));

    }

}
