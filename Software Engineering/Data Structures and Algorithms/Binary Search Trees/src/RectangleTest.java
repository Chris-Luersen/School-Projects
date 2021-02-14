
/**
 * @author Hulya Dogan and Chris Luersen
 * @version October 10,2020
 * 
 */

/**
 * Test class for the rectangle class.
 * Declaration of the variable
 * 
 * @author Hulya Dogan, Chris Luersen
 * @version 10/11/2020
 */
public class RectangleTest extends student.TestCase {
    private Rectangle myrec;

    /**
     * setUp constructor
     * Initiated the rectangle.
     */
    public void setUp() {
        myrec = new Rectangle(10, 20, 6, 7);
    }


    /**
     * testing getx method.
     */

    public void testGetx() {
        assertEquals(myrec.getx(), 10);
    }


    /**
     * testing gety method.
     */
    public void testGety() {
        assertEquals(myrec.gety(), 20);
    }


    /**
     * testing getwidth method.
     */
    public void testGetwith() {
        assertEquals(myrec.getWidth(), 6);
    }


    /**
     * testing getheight method.
     */
    public void testGetheight() {
        assertEquals(myrec.getHeight(), 7);

    }

}
