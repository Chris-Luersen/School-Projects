
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

/**
 * @author Hulya Dogan and Chris Luersen
 * @version 11/10/2020
 * 
 * 
 *          This is command processor class.
 *          It parses the bin file with random access file reader and writer.
 *
 */
public class CommandProcessor {

    private RandomAccessFile given;
    private RandomAccessFile runs;
    private MinHeap heap;
    private LinkedList<Runs> linkedRuns;
    private Buffer inputBuffer;
    private Buffer outputBuffer;

    private static final int HEAP_SIZE = 8 * 8192;
    private static final int MAX_REC_HEAP = 4096;

    /**
     * @param file
     *            the name of the input bin file
     * @throws IOException
     */
    CommandProcessor(String file) throws IOException {
        inputBuffer = new Buffer();
        outputBuffer = new Buffer();
        linkedRuns = new LinkedList<Runs>();

        given = new RandomAccessFile(file, "rw");
        runs = new RandomAccessFile("runfile.bin", "rw");
        runs.setLength(HEAP_SIZE);
        given.seek(0);
        runs.seek(0);

        byte[] heapArray = new byte[HEAP_SIZE];
        given.read(heapArray);
        heap = new MinHeap(heapArray, MAX_REC_HEAP, MAX_REC_HEAP);
        heap.buildHeap();
    }


    /**
     * @return the input random access file
     */
    public RandomAccessFile inFile() {
        return given;
    }


    /**
     * @return the output random access file
     */
    public RandomAccessFile runsFile() {
        return runs;
    }


    /**
     * @return the working memory heap
     */
    public MinHeap heap() {
        return heap;
    }


    /**
     * @return the list holding the runs
     */
    public LinkedList<Runs> runsList() {
        return linkedRuns;
    }


    /**
     * @return the input buffer, used in replacement selection
     */
    public Buffer inputBuffer() {
        return inputBuffer;
    }


    /**
     * @return the output buffer used for memory storage
     */
    public Buffer outputBuffer() {
        return outputBuffer;
    }

}
