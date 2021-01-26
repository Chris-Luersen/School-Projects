/**
 * Runs class. It stores data about each run so that
 * the records can be processed.
 * 
 * @author Hulya Dogan and Chris Luersen
 * @version 11/10/2020
 *
 */
public class Runs {
    /**
     * whether this run has been merged
     */
    private boolean hasBeenMerged;
    /**
     * identifying number in the sequence of runs
     */
    private int runNumber;
    /**
     * keeps track of your current position in the run
     */
    private long curPos;
    /**
     * keeps track of where the run ends
     */
    private long endPos;

    /**
     * Constructor for a node class that holds the run data
     * 
     * @param run
     *            the run number corresponding to this run
     * @param start
     *            the starting place in the file of this run
     * @param end
     *            the ending position in the file of this run
     * @param merged
     *            whether this run was a result of a merge sort
     */
    Runs(int run, long start, long end, boolean merged) {
        runNumber = run;
        curPos = start;
        // startPos = start;
        endPos = end;
        hasBeenMerged = merged;
    }


    /**
     * Returns whether this run was a result of a merge sort
     * 
     * @return true if this run was merged, false if not
     */
    public boolean gotMerged() {
        return hasBeenMerged;
    }


    /**
     * Sets whether this run needs to be merged again
     * 
     * @param isMerged
     *            sets whether this run was merged
     */
    public void setMerged(boolean isMerged) {
        hasBeenMerged = isMerged;
    }


    /**
     * Gets the run number of this run node
     * 
     * @return the value of the run number
     */
    public long getRunNumber() {
        return runNumber;
    }


    /**
     * Sets the run value of this run node
     * 
     * @param run
     *            the number to set it to
     */
    public void setRunNumber(int run) {
        runNumber = run;
    }


    /**
     * Get the current position within the run within the file
     * 
     * @return the current position within the file
     */
    public long getCurPos() {
        return curPos;
    }


    /**
     * Sets the current position within the run within the file
     * 
     * @param l
     *            the location within the file
     */
    public void setCurPos(long l) {
        curPos = l;
    }


    /**
     * Get the ending position of the run
     * 
     * @return the ending position of the run within the file
     */
    public long getEndPos() {
        return endPos;
    }

}
