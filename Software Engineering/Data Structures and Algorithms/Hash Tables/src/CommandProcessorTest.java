import student.TestCase;

/**
 * Tests CommandProcessor
 *
 * @author Chris Luersen + Hulya Dogan
 * @version 09/15/2020
 */

public class CommandProcessorTest extends TestCase {
// Fields
    private Hash hash;

// Constructor
    /**
     * Set up test conditions.
     */
    public void setUp() {
        hash = new Hash(3, 64);
    }


// Methods
    /**
     * Tests adding a record to the hash,
     * and adding a duplicate record to the hash.
     */
    public void testHashAdd() {
        CommandProcessor commandProcessor = new CommandProcessor();
        commandProcessor.getClass();
        CommandProcessor.parse("testHashAdd", hash);
        assertTrue(systemOut().getHistory().contains(
            "|test2| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains("Total records: 2"));
        assertTrue(systemOut().getHistory().contains("|test2| 0"));
        assertTrue(systemOut().getHistory().contains("|test2| 6"));
        assertTrue(systemOut().getHistory().contains(
            " duplicates a record already in the Name database."));
    }


    /**
     * Tests adding two to hash.
     */
    public void testHashAddTwo() {
        CommandProcessor.parse("P1sampleInput", hash);
        assertTrue(systemOut().getHistory().contains(
            "|Death Note| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains("|Death Note| 0\n"
            + "|Spirited Away| 1\n" + "|Can You Handle?| 4\n"
            + "|death note| 5\n" + "|Fullmetal Alchemist| 7\n"
            + "|Castle in the Sky| 11\n" + "Total records: 6"));
    }


    /**
     * Test adding large text to the hash.
     */
    public void testAddLargeString() {
        CommandProcessor.parse("testAddLargeString", hash);
        assertTrue(systemOut().getHistory().contains(
            "|a really long string where when we call print " + "blocks first "
                + "it will say 64:0 but now will say no free "
                + "blocks available.| "
                + "has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains(
            "No free blocks are available."));
    }


    /**
     * Tests the while loop in add.
     */
    public void testAddLoop() {
        Hash hashLoop = new Hash(4, 64);
        CommandProcessor.parse("testAddLoop", hashLoop);
        assertTrue(systemOut().getHistory().contains(
            "|test1| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains(
            "|test2| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains("|test1| 1\n"
            + "|test2| 2\n" + "Total records: 2"));
    }


    /**
     * Tests deleting from hash.
     */
    public void testHashDelete() {
        CommandProcessor.parse("testHashDelete", hash);
        assertTrue(systemOut().getHistory().contains(
            "|test1| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains(
            "|test1| has been deleted from the Name database."));
        assertTrue(systemOut().getHistory().contains(
            "|does not exist| not deleted because it does not "
                + "exist in the Name database."));
    }


    /**
     * Tests the while loop in delete.
     */
    public void testDeleteLoop() {
        Hash hashLoop = new Hash(4, 64);
        CommandProcessor.parse("testDeleteLoop", hashLoop);
        assertTrue(systemOut().getHistory().contains(
            "|test1| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains(
            "|test2| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains(
            "|does not exist| not deleted because it does not "
                + "exist in the Name database."));
    }


    /**
     * Tests that the hash is updated.
     */
    public void testUpdate() {
        CommandProcessor.parse("testUpdate", hash);
        assertTrue(systemOut().getHistory().contains(
            "|test1| not updated because the field "
                + "|Type doesn't Exist| does not exist"));
        assertTrue(systemOut().getHistory().contains(
            "Updated Record: |test 1<SEP>type<SEP>test|"));
        assertTrue(systemOut().getHistory().contains(
            "Updated Record: |test 1|"));
    }


    /**
     * Tests the free blocks limit.
     */
    public void testFreeBlocksTest() {
        CommandProcessor.parse("testFreeBlocksTest", hash);
        assertTrue(systemOut().getHistory().contains("16: 16\n" + "32: 32"));
        assertTrue(systemOut().getHistory().contains("Updated Record: |test|"));
    }


    /**
     * Test memory adding freeing and merging.
     */
    public void testCombineBlocks() {
        CommandProcessor.parse("testCombineBlocks", hash);
        assertTrue(systemOut().getHistory().contains(
            "Updated Record: |test2<SEP>type<SEP>test|"));
        assertTrue(systemOut().getHistory().contains("32: 32"));
    }

}
