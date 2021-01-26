/**
 * The class containing the memory manager tests.
 *
 * @author Chris Luersen + Hulya Dogan
 * @version 09/15/2020
 */

public class MemoryManagerTest extends student.TestCase {

    /**
     * Set up for tests Not used
     */
    public void setUp() {
        // Nothing Here

    }


    /**
     * Testing args to parse
     * the command lines.
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


    /**
     * tests breaking up free blocks
     */
    public void testBreak() {
        MemoryManager memoryManager = new MemoryManager(128);
        String string = "asdfghjklzxcvbnm";
        memoryManager.insert(string.getBytes(), 16);
        string = "asdfghjklzxcvbnmasdf";
        memoryManager.insert(string.getBytes(), 32);
        memoryManager.print();
        assertTrue(systemOut().getHistory().contains("16: 16\n" + "64: 64"));
    }


    /**
     * tests merging free blocks
     */
    public void testMerge() {
        MemoryManager memoryManager = new MemoryManager(128);
        String string = "test";
        memoryManager.insert(string.getBytes(), 16);
        memoryManager.print();
        Handle hand = new Handle(16, 0, string, 16);
        memoryManager.remove(hand);
        memoryManager.print();
        assertTrue(systemOut().getHistory().contains("16: 16\n" + "32: 32\n"
            + "64: 64\n" + "128: 0"));
    }

}
