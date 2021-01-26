
/**
 * @author Hulya Dogan, Chris Luersen
 * @version October 11,2020
 *          Test class to test the parser class.
 *
 */
public class ParserTest extends student.TestCase {
    /**
     * setUp constructor.
     */

    public void setUp() {
        // intentionally left blank.
    }


    /**
     * testing insert method.
     */
    public void testInsert() {
        BST<String> tree = new BST<String>();
        Parser myParser = new Parser();
        myParser.getClass();
        Parser.beginParsing("SimpleInsertionTest", tree);
        // assertTrue(systemOut().getHistory().contains("a"));
        assertTrue(systemOut().getHistory().contains(
            "Rectangle accepted: (b, 4, 3, 6, 1)"));
        assertTrue(systemOut().getHistory().contains("a"));
        assertTrue(systemOut().getHistory().contains(
            "Rectangle accepted: (a, 1, 0, 2, 4)"));
    }


    /**
     * testing dump method
     */

    public void testDump() {
        Parser myParser = new Parser();
        myParser.getClass();
        BST<String> tree = new BST<String>();
        Parser.beginParsing("SimpleInsertionTest", tree);
        assertTrue(systemOut().getHistory().contains("b"));
    }


    /**
     * testing remove method
     */

    public void testRemove() {
        Parser myParser = new Parser();
        myParser.getClass();
        BST<String> tree = new BST<String>();
        Parser.beginParsing("SimpleInsertionTest", tree);
        assertTrue(systemOut().getHistory().contains(
            "Rectangle rejected: (1, 1, 0, 0)"));
        assertTrue(systemOut().getHistory().contains(
            "Rectangle rejected: r_r"));
    }


    /**
     * testing search method
     */

    public void testSearch() {
        Parser myParser = new Parser();
        myParser.getClass();
        BST<String> tree = new BST<String>();
        Parser.beginParsing("SimpleInsertionTest", tree);
        assertTrue(systemOut().getHistory().contains(
            "Rectangle not found: r_r"));
    }


    /**
     * testing region search method
     */

    public void testRegionSearch() {
        Parser myParser = new Parser();
        myParser.getClass();
        BST<String> tree = new BST<String>();
        Parser.beginParsing("Syntax Test", tree);
        assertTrue(systemOut().getHistory().contains(""));
    }


    /**
     * testing region intersection method
     */

    public void testIntersection() {
        Parser myParser = new Parser();
        myParser.getClass();
        BST<String> tree = new BST<String>();
        Parser.beginParsing("Syntax Test", tree);
        assertTrue(systemOut().getHistory().contains("Intersection pairs:"));
    }

}
