
import java.nio.ByteBuffer;

// import java.nio.ByteBuffer;

/**
 * Holds a single record
 * 
 * @author CS Staff
 * @version 2020-10-15
 */
public class Record implements Comparable<Record> {

    private byte[] completeRecord;
    private long data;

    /**
     * The constructor for the Record class
     * 
     * @param record
     *            The byte for this object
     */
    public Record(byte[] record) {

        completeRecord = record;
    }


    /**
     * Another constructor with parameters.
     * 
     * @param arr
     *            for the byte array
     * @param num
     *            is for number
     * 
     */

    public Record(byte[] arr, int num) {
        ByteBuffer buf = ByteBuffer.allocate(16);
        buf.put(arr);
        buf.flip();
        data = buf.getLong();

    }


    /**
     * get complete record as getter
     * 
     * @return complete record
     */
    public byte[] getCompleteRecord() {
        return completeRecord;
    }


    /**
     * getdata method as getter for the data
     * 
     * @return complete record
     */

    public long getData() {
        return data;
    }


    /**
     * Returns the object's key
     * 
     * @return the key
     */
    public double getKey() {
        ByteBuffer buff = ByteBuffer.wrap(completeRecord);
        return buff.getDouble(8);
    }


    /**
     * Compare Two Records based on their keys
     * 
     * @param toBeCompared
     *            - The Record to be compared.
     * @return A negative integer, zero, or a positive integer as this employee
     *         is less than, equal to, or greater than the supplied record
     *         object.
     */
    @Override
    public int compareTo(Record toBeCompared) {
        return Double.compare(this.getKey(), toBeCompared.getKey());
    }


    /**
     * Outputs the record as a String
     * 
     * @return a string of what the record contains
     */
    public String toString() {
        return "" + this.getKey();
    }

}
