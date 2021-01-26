import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Hulya Dogan and Chris Luersen
 * @version 11/10/2020
 * 
 *          This is replacement selection sort class.
 *          It uses an 8-block heap and two
 *          1-block buffers, one for input and one for output.
 */
public class ReplacementSelectionSort {

    /**
     * Named constant for maximum heap size
     */
    private static final int MAX_REC_HEAP = 4096;

    /**
     * Number of bytes in a record
     */
    private static final int RECORD_SIZE = 16;

    /**
     * Private variable for the 8 block heap
     */
    private MinHeap recordHeap;

    /**
     * Private variable holding the runs going into the output
     */
    private LinkedList<Runs> runs;

    /**
     * Input buffer to aid in replacement selection
     */
    private Buffer inBuffer;

    /**
     * Output buffer to aid in replacement selection
     */
    private Buffer outBuffer;

    /**
     * File from which the raw data is being read from
     */
    private RandomAccessFile inputFile;

    /**
     * File into which the original runs are put
     */
    private RandomAccessFile outputFile;

    /**
     * Starting position of current run
     */
    private long begRun;

    /**
     * Ending position of current run
     */
    private long endRun;

    /**
     * The total number of runs created
     */
    private int numberofRuns;

    /**
     * @param c
     *            the variable containing data
     *            corresponding to sorting
     * @throws IOException
     */
    ReplacementSelectionSort(CommandProcessor c) throws IOException {
        this.runs = c.runsList();
        this.recordHeap = c.heap();
        this.inputFile = c.inFile();
        this.outputFile = c.runsFile();
        this.inBuffer = c.inputBuffer();
        this.outBuffer = c.outputBuffer();
        this.begRun = 0;
        this.endRun = 0;
    }


    /**
     * @return true if the inFile can be read from, false if not
     */
    public boolean read() throws IOException {

        return this.inputFile.getFilePointer() != inputFile.length(); // if
                                                                      // these
                                                                      // are
                                                                      // equal,
                                                                      // we are
                                                                      // at the
                                                                      // end of
                                                                      // the
                                                                      // file
    }


    /**
     * Executes the reading of the input file
     * and creates runs of partially sorted data
     * which are written to the outFile and tracked
     * with the linked list
     * 
     * @throws IOException
     */
    public void work() throws IOException {

        this.begRun = outputFile.getFilePointer();
        int nextRunCount = 0;

        while (read()) {

            this.inBuffer.stock(inputFile);

            while (!inBuffer.complete()) {

                if (recordHeap.empty()) {

                    writeBufferToFile();
                    makeRun();

                    recordHeap.buildHeap(MAX_REC_HEAP);

                    nextRunCount = 0;
                }
                else if (outBuffer.full()) {
                    writeBufferToFile();
                }
                byte[] minVal = recordHeap.getData(0);
                outBuffer.write(minVal);
                byte[] buf = inBuffer.read();
                if (comparerecordHeap(buf, minVal) > 0) {
                    recordHeap.change(0, buf);
                }
                else {
                    this.recordHeap.removemin(buf);
                    nextRunCount++;
                }
            } // inBuffer is empty
        } // inFile is empty

        writeBufferToFile();

        for (int i = 0; i <= nextRunCount; i += nextRunCount) {
            while (!recordHeap.empty()) {
                if (outBuffer.full()) {
                    writeBufferToFile();
                }
                outBuffer.write(recordHeap.removemin());
            }

            writeBufferToFile();
            makeRun();

            if (nextRunCount == 0) {
                i++;
            }
            else {
                recordHeap.buildHeap(nextRunCount);
            }
        }
        inBuffer.clear();
    }


    /**
     * Compares two records based on key value
     * 
     * @param rec1
     *            the first record to compare
     * @param rec2
     *            the second record to compare
     * @return the value compared
     */
    private int comparerecordHeap(byte[] rec1, byte[] rec2) {

        ByteBuffer buffer1 = ByteBuffer.wrap(Arrays.copyOfRange(rec1,
            RECORD_SIZE / 2, RECORD_SIZE)); // convert both keys to Doubles and
                                            // compare
        Double rec1Double = buffer1.getDouble();
        ByteBuffer buffer2 = ByteBuffer.wrap(Arrays.copyOfRange(rec2,
            RECORD_SIZE / 2, RECORD_SIZE));
        Double rec2Double = buffer2.getDouble();
        return rec1Double.compareTo(rec2Double);
    }


    /**
     * Writes all the records currently in the outBuffer to the
     * outFile
     * 
     * @throws IOException
     */
    private void writeBufferToFile() throws IOException {
        if (!outBuffer.empty()) {
            outputFile.write(Arrays.copyOfRange(outBuffer.array(), 0, outBuffer
                .position()));
            outBuffer.clear();
        }
    }


    /**
     * Creates a runNode and adds it to the
     * linked list for later processing
     * 
     * @throws IOException
     */
    private void makeRun() throws IOException {

        endRun = outputFile.getFilePointer();

        if (begRun != endRun) {

            Runs n = new Runs(numberofRuns, begRun, endRun, false);

            runs.add(n);
            numberofRuns++;
            begRun = endRun; // set the start position for next run
        }
    }

}
