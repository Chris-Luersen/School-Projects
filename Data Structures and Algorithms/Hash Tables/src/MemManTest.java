import student.TestCase;

/**
 * @author Chris Luersen + Huyla Dogan
 * @version 09/15/2020
 */
public class MemManTest extends TestCase {
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
        MemMan manager = new MemMan();
        assertNotNull(manager);
        MemMan.main(null);
    }


    /**
     * Tests too little args.
     */
    public void testsTooLittleArgs() {
        String[] args = new String[2];
        args[0] = "0";
        args[1] = "1";
        MemMan.main(args);
        assertFalse(systemOut().getHistory().contains("add"));
        assertFalse(systemOut().getHistory().contains("print"));
    }


    /**
     * Tests MemMan when it should work.
     */
    public void testsGoodArgs() {
        String[] args = new String[3];
        args[0] = "10";
        args[1] = "10";
        args[2] = "testMemMan";
        MemMan.main(args);
        assertTrue(systemOut().getHistory().contains(
            "Invalid arguments for add"));
        assertTrue(systemOut().getHistory().contains(
            "Invalid arguments for delete"));
        assertTrue(systemOut().getHistory().contains(
            "Invalid arguments for update"));
        assertTrue(systemOut().getHistory().contains(
            "Invalid arguments for print"));
    }


    /**
     * Tests for a wrong print command in MemMan.
     */
    public void testsIncorrectPrintCommand() {
        String[] args = new String[3];
        args[0] = "10";
        args[1] = "10";
        args[2] = "testsIncorrectPrintCommand";
        MemMan.main(args);
        assertTrue(systemOut().getHistory().contains(
            "Invalid arguments for print"));
    }
}
