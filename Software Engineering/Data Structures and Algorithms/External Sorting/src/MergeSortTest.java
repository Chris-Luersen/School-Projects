import java.io.IOException;
import student.TestCase;

/**
 * @author Hulya Dogan and Chris Luersen
 * @version 11/10/2020
 *
 *          Testing class for the MergeSort class
 */
public class MergeSortTest extends TestCase {

    /**
     * Test method for merge sort command processor.
     * 
     * @throws IOException
     */
    public void testMergeSort() throws IOException {
        String[] args = { "newData.bin", "12" };
        GenfileProject3.main(args);
        CommandProcessor c = new CommandProcessor("newData.bin");
        ReplacementSelectionSort rSel = new ReplacementSelectionSort(c);
        rSel.work();

        MergeSort merge = new MergeSort(c);
        assertEquals(merge.numberOfRuns, 2);
        assertNull(merge.pnode);
        assertTrue(c.inputBuffer().empty());
    }


    /**
     * Test method for merge sort execution .
     * 
     * @throws IOException
     */
    public void testWork() throws IOException {
        String[] args = { "sortedRuns.bin", "12" };
        GenfileProject3.main(args);
        CommandProcessor sc = new CommandProcessor("sortedRuns.bin");
        ReplacementSelectionSort rSelection = new ReplacementSelectionSort(sc);
        rSelection.work();

        MergeSort mMerge = new MergeSort(sc);
        mMerge.merge();
        assertTrue(mMerge.pnode.isEmpty());
        assertTrue(mMerge.linkedRuns.isEmpty());
        assertTrue(sc.inputBuffer().empty());
        assertTrue(sc.outputBuffer().empty());

        String[] args2 = { "sortedRuns.bin", "156" };
        GenfileProject3.main(args2);
        CommandProcessor sc2 = new CommandProcessor("sortedRuns.bin");
        ReplacementSelectionSort rS2 = new ReplacementSelectionSort(sc2);
        rS2.work();

        MergeSort mMerge2 = new MergeSort(sc2);
        mMerge2.merge();
        assertTrue(mMerge2.pnode.isEmpty());
        assertTrue(mMerge2.linkedRuns.isEmpty());
        assertTrue(sc.inputBuffer().empty());
        assertTrue(sc.outputBuffer().empty());
    }

}
