import java.io.IOException;
import student.TestCase;

/**
 * @author Hulya Dogan and Chris Luersen
 * @version 11/10/2020
 * 
 *          Test class for the External Sort class.
 *
 */
public class ExternalsortTest extends TestCase {

    /**
     * @throws IOException
     *             testing for the main method in the external sort class.
     */
    public void test() throws IOException {
        String[] args = { "sampleInput16.bin" }; // bin file
        Externalsort.main(args);
        CommandProcessor cp = Externalsort.getSortContainer();
        assertTrue(cp.inputBuffer().empty());
        assertTrue(cp.outputBuffer().empty());
    }

}
