
/**
 * Test the BST function (you should throw this away for your project)
 *
 * @author Hulya Dogan, Chris Luersen
 * @version October 13, 2020
 * @param <T>
 */
public class BSTTest<T> extends student.TestCase {
    private BST<String> theTree;
    private Rectangle myrec;
    private Rectangle myrec2;
    private Rectangle myidenticalrec1;
    private Rectangle myidenticalrec2;

    /**
     * setUp the condition.
     */
    public void setUp() {
        theTree = new BST<String>();
        myrec = new Rectangle(10, 20, 6, 7);
        String recname = "a";
        theTree.insert(recname, myrec);
        theTree.insert(recname, myrec);
        myrec2 = new Rectangle(101, 20, 6, 7);
        String recname2 = "b";
        theTree.insert(recname2, myrec2);
        assertEquals(theTree.size(), 2);
        myidenticalrec1 = new Rectangle(1, 2, 3, 4);
        myidenticalrec2 = new Rectangle(1, 2, 3, 4);

    }


    /**
     * Test Insert method.
     */
    public void testInsert() {
        Rectangle myrec6 = new Rectangle(-10, 20, 6, -8);
        Rectangle myrec7 = new Rectangle(10, 20, -1, 7);
        Rectangle myrec8 = new Rectangle(10, 20, 6, -8);
        Rectangle myrec9 = new Rectangle(10, 20, 1100, 1100);
        Rectangle myrec13 = new Rectangle(10, 20, 1100, 70);
        Rectangle myrec14 = new Rectangle(10, 20, 70, 1100);
        // testing if the BST will accept the rectangle having height and width
        // bigger than 1024.
        BST<String> theTree4 = new BST<String>();
        theTree4.insert("f", myrec9);
        theTree4.insert("g", myrec13);
        theTree4.insert("h", myrec14);
        assertEquals(theTree4.size(), 0);
        // New Tree names theTree2
        BST<String> theTree2 = new BST<String>();
        Rectangle myrec3 = new Rectangle(80, 20, 6, 7);
        String recname3 = "c";
        // checking if the new tree, theTree2 has a null root
        assertNull(theTree2.rt);
        // inserting a new node to an empty tree
        theTree2.insert("c", myrec3);
        assertTrue(systemOut().getHistory().contains(
            "Rectangle accepted: (c, 80, 20, 6, 7)"));
        assertEquals(theTree2.size(), 1);
        // adding a new node to the tree
        Rectangle myrec4 = new Rectangle(80, 60, 6, 7);
        theTree2.insert("d", myrec4);
        assertTrue(systemOut().getHistory().contains(
            "Rectangle accepted: (d, 80, 60, 6, 7)"));
        // checking the size of the tree
        assertEquals(theTree2.size(), 2);
        // inserting the same node to the Global tree, theTree
        assertEquals(theTree.size(), 2);
        theTree.insert("a", myrec);
        assertTrue(systemOut().getHistory().contains(
            "Rectangle rejected: (a, 10, 20, 6, 7)"));
        assertNotNull(theTree);
        // checking if the size of the tree is the same
        assertEquals(theTree.size(), 2);
        // adding a new node to the tree
        theTree.insert(recname3, myrec3);
        assertEquals(theTree.size(), 3);
        // adding the same node to the tree
        theTree.insert(recname3, myrec3);
        assertEquals(theTree.size(), 3);
        // adding a new node that has a name that starts with a number
        theTree.insert("3a", myrec);
        assertEquals(theTree.size(), 3);
        assertTrue(systemOut().getHistory().contains(
            "Rectangle rejected: (3a, 10, 20, 6, 7"));
        // adding a new node that has a negative width
        theTree2.insert("dart", myrec7);
        assertTrue(systemOut().getHistory().contains(
            "Rectangle rejected: (dart, 10, 20, -1, 7)"));
        // adding a new node that has a negative width
        theTree2.insert("dert", myrec8);
        assertTrue(systemOut().getHistory().contains(
            "Rectangle rejected: (dert, 10, 20, 6, -8)"));
        // adding a new node that has a negative x
        theTree2.insert("dost", myrec6);
        assertTrue(systemOut().getHistory().contains(
            "Rectangle rejected: (dost, -10, 20, 6, -8)"));
        assertEquals(theTree2.size(), 2);
        // inserting a rectangle that falls outside the box
        Rectangle myrec19 = new Rectangle(100, 100, 1000, 1000);
        theTree.insert("f", myrec19);
        assertTrue(systemOut().getHistory().contains(
            "Rectangle rejected: (f, 100, 100, 1000, 1000)"));
        // inserting rectangles to check case sensitivity
        theTree.insert("test", myrec4);
        assertEquals(theTree.size(), 4);
        theTree.insert("Test", myrec4);
        assertEquals(theTree.size(), 5);
        theTree.insert("TeSt", myrec4);
        assertEquals(theTree.size(), 6);
        // inserting a node with a name that has underscore character
        theTree2.insert("d_23", myrec4);
        assertTrue(systemOut().getHistory().contains(
            "Rectangle accepted: (d_23, 80, 60, 6, 7)"));

    }


    /**
     * Test isEmpty method.
     */

    public void testIsEmpty() {
        BST<String> theTree3 = new BST<String>();
        assertFalse(theTree.isEmpty());
        assertTrue(theTree3.isEmpty());

    }


    /**
     * Test size method.
     */

    public void testSize() {
        BST<String> theTree4 = new BST<String>();
        Rectangle myrec11 = new Rectangle(10, 20, 6, 7);
        String recname = "a";
        theTree4.insert(recname, myrec11);
        assertEquals(theTree4.size(), 1);
        assertEquals(theTree.size(), 2);

    }


    /**
     * Test remove method.
     */

    public void testRemove() {
        assertEquals(theTree.size, 2);
        String recname3 = "a";
        theTree.remove(recname3);
        assertEquals(theTree.size, 1);
        BST<String> theTree2 = new BST<String>();
        // testing same name but different coordinates
        theTree2.insert("a", myrec);
        theTree2.insert("a", myrec2);
        theTree2.remove("a");
        assertEquals(theTree2.size, 1);
        // testing to remove a rectangle was not inserted.
        theTree2.remove("b");
        assertTrue(systemOut().getHistory().contains("Rectangle rejected: b"));
        // testing removing 2 identical rectangles
        theTree2.insert("cd", myidenticalrec1);
        theTree2.insert("cd", myidenticalrec2);
        theTree2.remove("cd");
        assertEquals(theTree.size, 1);
        BST<String> theTree3 = new BST<String>();
        Rectangle myrec10 = new Rectangle(10, 30, 4, 7);
        Rectangle myrec11 = new Rectangle(10, 60, 9, 7);
        theTree3.insert("e", myrec10);
        theTree3.insert("c", myrec11);
        theTree3.insert("g", myrec);
        theTree3.insert("b", myrec2);
        theTree3.insert("d", myrec2);
        theTree3.insert("f", myrec2);
        theTree3.insert("h", myrec2);
        assertEquals(theTree3.size, 7);
        theTree3.remove("e");
        assertEquals(theTree3.size, 6);
        theTree3.remove("h");
        assertEquals(theTree3.size, 5);
        theTree3.remove("b");
        theTree3.remove("c");
        assertEquals(theTree3.size(), 3);
        BST<String> theTree4 = new BST<String>();
        theTree4.rt = null;
        assertNull(theTree4.rt);
        Rectangle nullrectangle = null;
        theTree4.remove("b");
        assertTrue(systemOut().getHistory().contains("Rectangle rejected: b"));
        Exception exception = null;
        assertNull(exception);
        try {
            theTree4.insert(null, nullrectangle);
        }
        catch (NullPointerException e) {
            exception = e;
        }
        assertNotNull(exception);
        theTree4.remove(null);
    }


    /**
     * Test remove based on dimensions method.
     */

    public void testRemoveDimensions() {
        // testing If two or more rectangles have the same dimensions, then any
        // one such rectangle may be removed.
        Rectangle myrec5 = new Rectangle(5, 20, 6, 2);
        Rectangle myrec4 = new Rectangle(5, 20, 6, 2);
        BST<String> theTree2 = new BST<String>();
        theTree2.insert("a", myrec5);
        theTree2.insert("b", myrec4);
        theTree2.remove(5, 20, 6, 2);
        assertEquals(theTree2.size, 1);
        // testing removing 2 identical rectangles
        theTree2.insert("cd", myidenticalrec1);
        theTree2.insert("cd", myidenticalrec2);
        theTree2.remove("cd");
        assertEquals(theTree.size, 2);
        // Removing a rectangle with no coordinates.
        theTree2.remove("r_r");
        assertTrue(systemOut().getHistory().contains(
            "Rectangle rejected: r_r"));

        BST<String> theTree3 = new BST<String>();
        Rectangle myrec1 = new Rectangle(10, 30, 4, 7);
        Rectangle myrec22 = new Rectangle(10, 70, 9, 7);
        Rectangle myrec3 = new Rectangle(8, 70, 9, 7);
        Rectangle myrec6 = new Rectangle(10, 30, 7, 7);
        Rectangle myrec7 = new Rectangle(10, 70, 19, 7);
        Rectangle myrec8 = new Rectangle(88, 70, 9, 7);
        Rectangle myrec9 = new Rectangle(88, 170, 9, 7);
        Rectangle myrec10 = new Rectangle(88, 730, 9, 7);
        Rectangle myrec11 = new Rectangle(88, 170, 99, 7);
        theTree3.insert("e", myrec1);
        theTree3.insert("c", myrec22);
        theTree3.insert("g", myrec3);
        theTree3.insert("b", myrec6);
        theTree3.insert("d", myrec7);
        theTree3.insert("f", myrec8);
        theTree3.insert("h", myrec9);
        theTree3.insert("a", myrec10);
        theTree3.insert("i", myrec11);
        theTree3.search("c");
        theTree3.search("h");
        assertEquals(theTree3.size, 9);
        theTree3.remove(10, 30, 7, 7);
        assertEquals(theTree3.size, 8);
        theTree3.remove(88, 170, 9, 7);
        assertEquals(theTree3.size, 7);
        theTree3.remove(10, 30, 4, 7);
        assertEquals(theTree3.size, 6);
        // theTree3.remove("b");
        // theTree3.remove("c");

    }


    /**
     * Test regionsearch method.
     */
    public void testRegionSearch() {
        // testing a rectangle does not intersect
        theTree.regionsearch(200, 200, 50, 50);
        assertFalse(systemOut().getHistory().contains(
            "Rectangles intersecting region"));
        // testing a rectangle intersect
        theTree.regionsearch(1, 1, 1000, 1000);
        assertTrue(systemOut().getHistory().contains(
            "Rectangles intersecting region"));
        // testing if the height or width is not greater than 0.
        theTree.regionsearch(5, 6, -1, -4);
        BST<String> theTree3 = new BST<String>();
        Rectangle myrec1 = new Rectangle(10, 10, 10, 10);
        Rectangle myrec22 = new Rectangle(50, 50, 10, 10);
        Rectangle myrec3 = new Rectangle(100, 100, 10, 10);
        Rectangle myrec6 = new Rectangle(150, 150, 10, 10);
        Rectangle myrec7 = new Rectangle(200, 200, 10, 10);
        Rectangle myrec8 = new Rectangle(250, 250, 10, 10);
        Rectangle myrec9 = new Rectangle(300, 300, 10, 10);
        Rectangle myrec10 = new Rectangle(350, 350, 10, 10);
        Rectangle myrec11 = new Rectangle(400, 400, 10, 10);
        theTree3.insert("e", myrec1);
        theTree3.insert("c", myrec22);
        theTree3.insert("g", myrec3);
        theTree3.insert("b", myrec6);
        theTree3.insert("d", myrec7);
        theTree3.insert("f", myrec8);
        theTree3.insert("h", myrec9);
        theTree3.insert("a", myrec10);
        theTree3.insert("i", myrec11);
        theTree3.regionsearch(53, 53, 10, 10);
        assertTrue(systemOut().getHistory().contains(
            "Rectangles intersecting region"));
        theTree3.regionsearch(105, 105, 10, 10);
        assertTrue(systemOut().getHistory().contains(
            "Rectangles intersecting region"));

    }


    /**
     * Test intersection method.
     */
    public void testIntersection() {
        // testing intersecting rectangles
        theTree.intersections();
        assertTrue(systemOut().getHistory().contains("Intersection pairs:"));
        Rectangle myrec15 = new Rectangle(11, 20, 6, 7);
        theTree.insert("y", myrec15);
        theTree.intersections();
        assertTrue(systemOut().getHistory().contains(
            "(y, 11, 20, 6, 7) : (a, 10, 20, 6, 7)"));
        // testing not intersecting rectangles

    }


    /**
     * testing dump method.
     */
    public void testDump() {
        theTree.dump();
        assertTrue(systemOut().getHistory().contains("BST dump:"));
        BST<String> theTree3 = new BST<String>();
        Rectangle myrec10 = new Rectangle(10, 30, 4, 7);
        Rectangle myrec17 = new Rectangle(20, 60, 9, 7);
        Rectangle myrec18 = new Rectangle(10, 30, 9, 7);
        Rectangle myrec20 = new Rectangle(10, 60, 40, 7);
        theTree3.insert("v", myrec10);
        theTree3.insert("z", myrec17);
        theTree3.insert("a", myrec18);
        theTree3.insert("c", myrec20);
        assertEquals(theTree3.size(), 4);
        theTree3.dump();
        assertTrue(systemOut().getHistory().contains("Node has depth 0"));
    }


    /**
     * testing search method.
     */
    public void testSearch() {
        // testing to search for an inserted rectangle
        theTree.search("a");
        myrec = new Rectangle(10, 20, 6, 7);
        theTree.insert("a", myrec);
        assertTrue(systemOut().getHistory().contains(
            "Rectangle found: (a, 10, 20, 6, 7)"));
        // testing to search a rectangle name was not inserted.
        theTree.search("z");
        assertTrue(systemOut().getHistory().contains("Rectangle not found: z"));

    }

}
