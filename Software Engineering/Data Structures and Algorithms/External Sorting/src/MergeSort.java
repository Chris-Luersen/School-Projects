
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * @author Hulya Dogan and Chris Luersen
 * @version 11/10/2020
 *
 *          This is merge sort class. It takes a random access file
 *          holding sorted runs and uses the working memory to sort all of
 *          the runs into one sorted run.
 */
public class MergeSort {
    /**
     * priority queue used to sort up to R records at a time
     */
    protected PriorityQueue<Node> pnode;
    /**
     * number of runs left to merge
     */
    protected int numberOfRuns;

    /**
     * Linked list storing run information
     */
    protected LinkedList<Runs> linkedRuns;
    /**
     * Linked list storing current run information
     */

    protected LinkedList<Integer> curRuns;
    /**
     * The heap which contains the R block array
     */
    protected MinHeap heap;
    /**
     * file runs are read from
     */
    protected RandomAccessFile readFile;
    /**
     * reference to original input file
     */
    protected RandomAccessFile givenInputFile;
    /**
     * The file results are printed to
     */
    protected RandomAccessFile writeFile;
    /**
     * The output 1 block byte array
     */
    protected Buffer outputBuffer;

    /**
     * This class performs an R-way merge
     */
    public static final int BLOCKS_RUN = 8;

    /**
     * Constant number of bytes in a block
     */
    public static final int BLOCK_LENGTH = 8192;

    /**
     * Constant number of bytes in a record
     */
    public static final int RECORD_LENGTH = 16;

    /**
     * Constructor for the merge class
     * 
     * @param c
     *            the container for all shared objects
     *            between merge and replacement selection
     * @throws IOException
     */
    MergeSort(CommandProcessor c) throws IOException {
        this.linkedRuns = c.runsList();
        this.heap = c.heap();
        this.readFile = c.runsFile();
        this.writeFile = c.inFile();
        this.givenInputFile = c.inFile();
        c.inputBuffer().clear();
        this.numberOfRuns = linkedRuns.size();
        this.outputBuffer = c.outputBuffer();
        this.curRuns = new LinkedList<Integer>();
        this.readFile.seek(0);
        this.writeFile.seek(0);
    }


    /**
     * merge operation
     * 
     * @throws IOException
     */
    public void merge() throws IOException {
        // if there is already one run, then just
        // go straight to printing the output
        if (numberOfRuns == 1) {
            this.writetoFile(readFile);
        }
        else {
            int numOfRuns = BLOCKS_RUN;
            if (numberOfRuns < BLOCKS_RUN) {
                numOfRuns = numberOfRuns;
            }
            this.workBlock(numOfRuns);
        }
    }


    /**
     * Loads the first R blocks from output file into working memory
     * 
     * @param numOfRuns
     *            the number of runs for which to load blocks
     * @throws IOException
     */
    private void workBlock(int numOfRuns) throws IOException {
        // create a priority queue that will hold the first record
        // from each of the blocks in working memory
        pnode = new PriorityQueue<Node>(numOfRuns, new NodeComparator());
        for (int i = 0; i < numOfRuns; i++) {
            // curRuns holds the integer value of the runs
            // so that it is known when runs are exhausted
            // when the elements have all been removed
            curRuns.add(i);
            Runs fileNode = linkedRuns.get(i);
            workNBlock(fileNode);
        }
        merge(numOfRuns);
    }


    /**
     * Merges multiple blocks together in the working memory
     * 
     * @param nRuns
     *            the number of runs for which to merge
     * @throws IOException
     */
    private void merge(int nRuns) throws IOException {
        // save the file pointer for the newly created
        // run as a result of the merge
        long nextStartPos = writeFile.getFilePointer();
        outputBuffer.clear();

        while (curRuns.size() > 0) { // all runs are exhausted
            // get the lowest value from the priority queue
            Node minNode = pnode.poll();
            outputBuffer.write(minNode.getRecord());
            // increment the place to read from in the block
            minNode.incrementCurPos(RECORD_LENGTH);
            if (outputBuffer.full()) {
                writeFile.write(Arrays.copyOfRange(outputBuffer.array(), 0,
                    outputBuffer.position()));
                outputBuffer.clear();
            }

            // change minNode record and increment its current Position
            int readBlocks = minNode.getBlockNumber();
            int blockSpace = minNode.getEndPos() - minNode.getCurPos();
            boolean reloadBlock = false;
            boolean runRead = true;
            Runs fileNode = linkedRuns.get(readBlocks); // getting data from
                                                        // disk
            long runLength = fileNode.getEndPos() - fileNode.getCurPos();
            // if we ran out of block space and we can still read from
            // the block
            if (blockSpace == 0 && curRuns.contains(readBlocks)) {
                reloadBlock = true;
                // need to get a new block!
                if (runLength < BLOCK_LENGTH) {
                    long end = (readBlocks * BLOCK_LENGTH) + runLength;
                    minNode.setEndPos((int)end);
                }
                if (runLength == 0) {
                    // need to remove that run from linked list
                    // if the run is exhausted on disk
                    curRuns.remove((Integer)readBlocks);
                    runRead = false;
                }
                else {
                    workNBlock(fileNode);
                }
            }
            // load the next record from the block if the run
            // is not exhausted and a block was not just
            // reloaded
            if (runRead && !reloadBlock) {
                loadDataFromHeap(readBlocks, minNode.getCurPos(), minNode
                    .getEndPos());
            }
        }
        // remove the merged runs from the runs linked list
        for (int i = nRuns - 1; i >= 0; i--) {
            linkedRuns.remove(i);
        }
        finish(nextStartPos);
    }


    /**
     * Last steps in the merge
     * 
     * @param nStart
     *            the starting position of the newly
     *            created merged run
     * @throws IOException
     */
    private void finish(long nStart) throws IOException {

        if (!outputBuffer.empty()) {
            writeFile.write(Arrays.copyOfRange(outputBuffer.array(), 0,
                outputBuffer.position()));
            outputBuffer.clear();
        }

        Iterator<Node> i = pnode.iterator();
        while (i.hasNext()) {
            Node minNode = pnode.poll();
            outputBuffer.write(minNode.getRecord());
        }
        if (!outputBuffer.empty()) {
            writeFile.write(Arrays.copyOfRange(outputBuffer.array(), 0,
                outputBuffer.position()));
            outputBuffer.clear();
        }

        if (linkedRuns.size() == 0) {
            writetoFile(writeFile);
        }
        else {

            long end = writeFile.getFilePointer();
            boolean merged = true;
            Runs n = new Runs(linkedRuns.size(), nStart, end, merged);
            leftRuns(n);

        }
    }


    /**
     * @param n
     *            the newly created merged node
     * @throws IOException
     */
    private void leftRuns(Runs n) throws IOException {
        int numberOfRunsLeft = 0;
        Iterator<Runs> j = linkedRuns.iterator();
        int i = 0;
        while (j.hasNext()) {
            Runs r = j.next();

            if (!r.gotMerged()) {
                r.setRunNumber(i);
                numberOfRunsLeft++;
                i++;
            }
        }
        linkedRuns.add(n);
        if (numberOfRunsLeft == 0) {

            Iterator<Runs> k = linkedRuns.iterator();
            int count = 0;

            while (k.hasNext()) {
                Runs nNode = k.next();

                nNode.setRunNumber(count);
                nNode.setMerged(false);
                count++;
            }
            numberOfRunsLeft = linkedRuns.size();

            RandomAccessFile temp = readFile;
            readFile = writeFile;
            writeFile = temp;
            readFile.seek(0);
            writeFile.seek(0);
            this.numberOfRuns = linkedRuns.size();
            numberOfRunsLeft = this.numberOfRuns;
        }
        int numOfRuns = BLOCKS_RUN;
        if (numberOfRunsLeft < BLOCKS_RUN) {
            numOfRuns = numberOfRunsLeft;
        }

        workBlock(numOfRuns);
    }


    /**
     * Load a record from the corresponding place in working memory
     * 
     * @param runNum
     *            the run number to load from
     * @param cur
     *            the current position within the block
     * @param end
     *            the end position of the data in the block
     * @throws IOException
     */
    private void loadDataFromHeap(int runNum, int cur, int end)
        throws IOException {

        byte[] myRecord = new byte[16];
        try {
            System.arraycopy(heap.array(), cur, myRecord, 0, 16);
        }
        catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println(exception);
        }

        Node mergeNode = new Node(runNum, myRecord, cur, end);
        pnode.add(mergeNode);
    }


    /**
     * Load the next block from disk into the working memory
     * 
     * @param fileNode
     *            the node containing the run data
     * @throws IOException
     */
    private void workNBlock(Runs fileNode) throws IOException {

        long runLength = fileNode.getEndPos() - fileNode.getCurPos();
        long runNum = fileNode.getRunNumber();

        if (runLength < BLOCK_LENGTH) {

            readFile.seek(fileNode.getCurPos());
            readFile.read(heap.array(), (int)(BLOCK_LENGTH * runNum),
                (int)runLength);
            fileNode.setCurPos(readFile.getFilePointer());
            int start = (int)(BLOCK_LENGTH * runNum);
            int end = (int)(BLOCK_LENGTH * runNum + runLength);

            loadDataFromHeap((int)runNum, start, end);
        }
        else {
            readFile.seek(fileNode.getCurPos());
            readFile.read(heap.array(), (int)(BLOCK_LENGTH * runNum),
                BLOCK_LENGTH);
            fileNode.setCurPos(readFile.getFilePointer());
            int start = (int)(BLOCK_LENGTH * runNum);
            int end = (int)(BLOCK_LENGTH * runNum + BLOCK_LENGTH);
            loadDataFromHeap((int)runNum, start, end);
        }
    }


    /**
     * @param endFile
     *            the sorted data will be printed
     * @throws IOException
     */
    private void writetoFile(RandomAccessFile endFile) throws IOException {
        endFile.seek(0);
        int i = 0;
        while (endFile.getFilePointer() != endFile.length()) {
            byte[] b = new byte[16];
            endFile.read(b);
            i++;

            endFile.seek(BLOCK_LENGTH * i);
            convert(b);

            if (i % 5 == 0) {
                System.out.println();
            }
            else {
                System.out.print(' ');
            }
        }
    }


    /**
     * Helper function to convert bytes into ID and key
     * 
     * @param bytes
     *            the byte array to convert
     * @return the key value
     */
    private double convert(byte[] bytes) {
        byte[] idBytes = Arrays.copyOfRange(bytes, 0, RECORD_LENGTH / 2);
        byte[] keyBytes = Arrays.copyOfRange(bytes, RECORD_LENGTH / 2,
            RECORD_LENGTH);
        long id = ByteBuffer.wrap(idBytes).getLong();
        double key = ByteBuffer.wrap(keyBytes).getDouble();
        System.out.print(id + " " + key);
        return key;
    }

}
