import java.nio.ByteBuffer;
import java.util.*;

/**
 * This class implements the heap data structure,
 * as an array-based min heap meaning the value
 * at the root is smaller than all of its children.
 * 
 * @author Hulya Dogan and Chris Luersen
 * @version 11/10/2020
 *          Min Heap class to heapify the datas.
 *
 */
public class MinHeap {

    /**
     * Constant number of max datas in the heap
     */
    public static final int HEAP_SIZE = 4096;

    /**
     * Constant number of bytes in a data
     */
    public static final int DATA_SIZE = 16;

    /**
     * Pointer to the arr array
     */
    private byte[] arr;
    /**
     * Maximum number of datas in arr
     */
    private int capacity;
    /**
     * Number of datas now in arr
     */
    private int dataCount;

    /**
     * Constructor supporting pre-loading of arr contents
     * 
     * @param memory
     *            the base array
     * @param num
     *            the cur number of datas
     * @param max
     *            the max number of datas
     */
    public MinHeap(byte[] memory, int num, int max) {
        arr = memory;
        dataCount = num;
        capacity = max;
        buildHeap();
    }


    /**
     * Default Constructor allocates the default size
     */
    public MinHeap() {
        arr = new byte[HEAP_SIZE];
        dataCount = HEAP_SIZE;
        capacity = HEAP_SIZE;
    }


    /**
     * @param position
     *            the position at which to get the data
     * @return the data at the given array position
     */
    public byte[] getData(int position) {
        return Arrays.copyOfRange(arr, position, position + DATA_SIZE);
    }


    /**
     * @return current number of datas in the array
     */
    public int heapSize() {
        return dataCount;
    }


    /**
     * Compare datas by key value
     * must convert to doubles
     * 
     * @param dat1
     *            the first data to compare
     * @param dat2
     *            the second data to compare
     * @return 0, -1, or 1 depending on the input
     */
    public int compareData(byte[] dat1, byte[] dat2) {
        ByteBuffer buffer1 = ByteBuffer.wrap(Arrays.copyOfRange(dat1, DATA_SIZE
            / 2, DATA_SIZE));
        Double rec1Double = buffer1.getDouble();
        ByteBuffer buffer2 = ByteBuffer.wrap(Arrays.copyOfRange(dat2, DATA_SIZE
            / 2, DATA_SIZE));
        Double rec2Double = buffer2.getDouble();
        return rec1Double.compareTo(rec2Double);
    }


    /**
     * @return the byte array
     */
    public byte[] array() {
        return arr;
    }


    /**
     * Return true if pos a leaf position, false otherwise
     * 
     * @param pos
     *            the position to check if a leaf node
     * @return whether the node at that position is a leaf
     */
    public boolean isLeaf(int pos) {
        return ((pos / DATA_SIZE) >= dataCount / 2) && ((pos
            / DATA_SIZE) < dataCount);
    }


    /**
     * @return if the number of datas in heap is zero
     */
    public boolean empty() {
        return dataCount == 0;
    }


    /**
     * Return position for left child of pos
     * 
     * @param pos
     *            the position to return the left child of
     * @return the position of the left child
     */
    public int leftchild(int pos) {
        if ((pos / DATA_SIZE) >= dataCount / 2) {
            return -1;
        }
        return (2 * pos) + DATA_SIZE;
    }


    /**
     * Return position for right child of pos
     * 
     * @param pos
     *            the position to return the right child of
     * @return the position of the right child
     */
    public int rightchild(int pos) {
        if ((pos / DATA_SIZE) >= (dataCount - 1) / 2) {
            return -1;
        }
        return (2 * pos) + 2 * DATA_SIZE;
    }


    /**
     * Return position for parent
     * 
     * @param pos
     *            the position to return the parent of
     * @return the index of the parent
     */
    public int parent(int pos) {
        if ((pos / DATA_SIZE) <= 0) {
            return -1;
        }
        return (pos / DATA_SIZE - 1) / 2;
    }


    /**
     * Heapify contents of heap
     */
    public void buildHeap() {
        int i = (dataCount / 2 - 1) * DATA_SIZE;
        for (; i >= 0; i -= DATA_SIZE) {
            siftdown(i);
        }
    }


    /**
     * Heapify contents of heap of a specified size
     * 
     * @param datasLeft
     *            the number of datas in heap
     */
    public void buildHeap(int datasLeft) {

        if (datasLeft < capacity) {
            int start = (capacity - datasLeft) * DATA_SIZE;
            System.arraycopy(arr, start, arr, 0, datasLeft * DATA_SIZE);
        }
        dataCount = datasLeft;
        buildHeap();
    }


    /**
     * Put element in its correct place in heap
     * 
     * @param pos
     *            the current position to check for sifting down
     */
    private void siftdown(int pos) {
        if ((pos < 0) || ((pos / DATA_SIZE) >= dataCount)) {
            return;
        }
        while (!isLeaf(pos)) {
            int j = leftchild(pos);
            byte[] a = Arrays.copyOfRange(arr, j, j + DATA_SIZE);
            byte[] b = Arrays.copyOfRange(arr, j + DATA_SIZE, j + 2
                * DATA_SIZE);
            if (((j / DATA_SIZE) < (dataCount - 1)) && (compareData(a,
                b) >= 0)) {
                j += DATA_SIZE;
            }
            byte[] c = Arrays.copyOfRange(arr, pos, pos + DATA_SIZE);
            byte[] d = Arrays.copyOfRange(arr, j, j + DATA_SIZE);
            if (compareData(c, d) <= 0) {
                return;
            }
            swap(j, pos);
            pos = j;

        }
    }


    /**
     * Swaps the values in two positions
     * 
     * @param pos1
     *            the first position to swap
     * @param pos2
     *            the second position to swap
     */
    private void swap(int pos1, int pos2) {
        byte[] temp = Arrays.copyOfRange(arr, pos1, pos1 + DATA_SIZE);
        int i = pos1;
        int j = pos2;
        for (; i < pos1 + 16; i++, j++) {
            arr[i] = arr[j];
        }
        i = 0;
        j = pos2;
        for (; j < pos2 + DATA_SIZE; j++, i++) {
            arr[j] = temp[i];
        }
    }


    /**
     * Remove and return minimum value
     * 
     * @return the minimum value of the heap
     */
    public byte[] removemin() {
        if (dataCount == 0) {
            return null;
        }
        swap(0, (--dataCount) * DATA_SIZE);
        if (dataCount != 0) {
            siftdown(0);
        }

        return Arrays.copyOfRange(arr, dataCount * DATA_SIZE, dataCount
            * DATA_SIZE + DATA_SIZE);
    }


    /**
     * Remove the minimum value and replace with array b
     * 
     * @param b
     *            the value to replace the minimum with
     * @return the minimum value in byte array form
     */
    public byte[] removemin(byte[] b) {
        if (dataCount == 0) {
            return null;
        }
        swap(0, (--dataCount) * DATA_SIZE);
        if (dataCount != 0) {
            siftdown(0);
        }
        System.arraycopy(b, 0, arr, dataCount * DATA_SIZE, DATA_SIZE);
        return Arrays.copyOfRange(arr, dataCount * DATA_SIZE, dataCount
            * DATA_SIZE + DATA_SIZE);
    }


    /**
     * Modify the value at the given position
     * 
     * @param pos
     *            the position to place the newVal at
     * @param newVal
     *            the new value to insert
     */
    public void change(int pos, byte[] newVal) {
        if ((pos < 0) || (pos >= dataCount * DATA_SIZE)) {
            return;
        }
        System.arraycopy(newVal, 0, arr, pos, DATA_SIZE);
        renew(pos);
    }


    /**
     * The value at pos has been changed, so restore the heap
     * 
     * @param pos
     *            the position that has the changed value
     */
    private void renew(int pos) {

        while ((pos > 0) && (compareData(Arrays.copyOfRange(arr, pos, pos
            + DATA_SIZE), Arrays.copyOfRange(arr, parent(pos), parent(pos)
                + DATA_SIZE)) < 0)) {
            swap(pos, parent(pos));
            pos = parent(pos);
        }
        if (dataCount != 0) {
            siftdown(pos);
        }
    }


    /**
     * Helper function that takes a long and a double and returns a
     * byte array for testing
     * 
     * @param id
     *            the id to turn into a byte array
     * @param key
     *            the key to turn into a byte array
     * @return the data as a byte array
     */
    public byte[] toByteArray(long id, double key) {
        byte[] bytes1 = new byte[DATA_SIZE / 2];
        byte[] bytes2 = new byte[DATA_SIZE / 2];
        ByteBuffer.wrap(bytes1).putLong(id);
        ByteBuffer.wrap(bytes2).putDouble(key);
        byte[] data = new byte[DATA_SIZE];
        System.arraycopy(bytes1, 0, data, 0, DATA_SIZE / 2);
        System.arraycopy(bytes2, 0, data, DATA_SIZE / 2, DATA_SIZE / 2);
        return data;
    }


    /**
     * Testing function
     * 
     * @param bytes
     *            the bytes to convert into a key value
     * @return the key as a double
     */
    public double convert(byte[] bytes) {
        byte[] keyBytes = Arrays.copyOfRange(bytes, DATA_SIZE / 2, DATA_SIZE);
        double key = ByteBuffer.wrap(keyBytes).getDouble();
        return key;
    }

}
