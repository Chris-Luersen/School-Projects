import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the hash function
 *
 * @author CS3114 staff
 * @version August, 2018
 *          Chris Luersen + Hulya Dogan
 * @version 9/16/2020
 */
public class HashTest extends TestCase {
    /**
     * Sets up the tests that follow.
     */
    public void setUp() {
        // Nothing here

    }


    /**
     * Test the hash function
     */
    public void testh() {

        Hash myHash = new Hash(0, 0);
        assertEquals(myHash.hash("aaaabbbb", 101), 75);
        assertEquals(myHash.hash("aaaabbb", 101), 1640219587 % 101);
    }
}
