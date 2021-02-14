// To-Do
/**
 * The class containing the memory manager.
 *
 * @author Chris Luersen + Hulya Dogan
 * @version 09/13/20
 */

public class MemoryManager {

    /**
     * Byte array for storing names
     */
    private byte[] info;

    /**
     * List that keeps track of free blocks left
     */
    private DoublyLinkedList<Block> blocks;

    /**
     * Sets up memory manager
     * 
     * @param size
     *            How big the memory is
     */
    public MemoryManager(int size) {
        info = new byte[size];
        blocks = new DoublyLinkedList<Block>();
        blocks.add(0, new Block(size, 0));
    }


    /**
     * Insert name into memory manager
     * 
     * @param byteLength
     *            How many bytes are inserted
     * @param byteSize
     *            Size needed for bytes
     * @return Returns position in byte list
     */
    public int insert(byte[] byteLength, int byteSize) {

        // location in byte list
        int location = -1;
        int currentByteSize = info.length + 1;
        int postion = blocks.size();
        // Goes through list
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getSize() == byteSize) {
                postion = blocks.get(i).getLoc();
                for (int j = 0; j < byteLength.length; j++) {
                    info[j + postion] = byteLength[j];
                }
                blocks.remove(i);
                return postion;
            }
            if (blocks.get(i).getSize() > byteSize && currentByteSize > blocks
                .get(i).getSize()) {
                location = blocks.get(i).getLoc();
                currentByteSize = blocks.get(i).getSize();
                postion = i;
            }
        }

        if (location == -1) {
            byte[] newData = new byte[info.length * 2];
            for (int i = 0; i < info.length; i++) {
                newData[i] = info[i];
            }
            if (!blocks.isEmpty() && blocks.get(0).getSize() == info.length) {
                mergeBlocks(blocks.size(), info.length, info.length);
            }
            else {
                blocks.add(new Block(info.length, info.length));
            }
            info = newData;
            System.out.println("Memory pool expanded to be " + info.length
                + " bytes.");
            return insert(byteLength, byteSize);
        }

        for (int k = 0; k < byteLength.length; k++) {
            info[k + location] = byteLength[k];
        }
        // new blocks created
        Block newBlock;

        blocks.remove(postion);
        currentByteSize /= 2;
        while (currentByteSize >= byteSize) {
            newBlock = new Block(currentByteSize, location + currentByteSize);
            int newSpot = findLocation(currentByteSize, location);
            blocks.add(newSpot, newBlock);
            currentByteSize /= 2;
        }

        return location;
    }


    /**
     * Find Location for new block in free list
     * 
     * @param newSize
     *            size of block being inserted
     * @param newLocation
     *            location in memory of block being inserted
     * @return location in the list where inserted
     */
    private int findLocation(int newSize, int newLocation) {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getSize() > newSize || (blocks.get(i)
                .getSize() == newSize && newLocation < blocks.get(i)
                    .getLoc())) {

                return mergeBlocks(i, newSize, newLocation);
            }
            if (i == blocks.size() - 1 && blocks.get(i).getSize() == newSize) {
                i++;
                return mergeBlocks(i, newSize, newLocation);
            }
        }
        return blocks.size();
    }


    /**
     * Merge the two list of blocks together
     * 
     * @param location
     *            location to be inserted in list
     * @param blockSize
     *            size of the block
     * @param memoryLocation
     *            location in memory
     * @return Location of the blocks in the list and -1 if they merge
     */
    private int mergeBlocks(int location, int blockSize, int memoryLocation) {
        if (memoryLocation % (blockSize * 2) != 0 && location > 0
            && blockSize == blocks.get(location - 1).getSize()
            && memoryLocation == blocks.get(location - 1).getLoc()
                + blockSize) {
            blocks.remove(location - 1);
            int newSpot = findLocation(blockSize * 2, memoryLocation
                - blockSize);
            if (newSpot != -1) {
                blocks.add(newSpot, new Block(blockSize * 2, memoryLocation
                    - blockSize));
            }
            return -1;
        }
        else if (memoryLocation % (blockSize * 2) == 0 && location < blocks
            .size() && blockSize == blocks.get(location).getSize()
            && memoryLocation + blockSize == blocks.get(location).getLoc()) {
            blocks.remove(location);
            int newSpot = findLocation(blockSize * 2, memoryLocation);
            if (newSpot != -1) {
                blocks.add(newSpot, new Block(blockSize * 2, memoryLocation));
            }
            return -1;
        }

        return location;
    }


    /**
     * Remove the block out of memory and put it back into the list of free
     * blocks
     * 
     * @param handle
     *            Location of blocks to be removed
     */
    public void remove(Handle handle) {

        int newLocation = findLocation(handle.getHandleSize(), handle
            .getHandlelocation());
        if (newLocation != -1) {
            blocks.add(newLocation, new Block(handle.getHandleSize(), handle
                .getHandlelocation()));
        }
    }


    /**
     * Retrieves the block of memory
     * 
     * @param handle
     *            Block of memory
     * @return Put what was in that memory into a string
     */
    public String get(Handle handle) {
        String ret = new String(info); // string to return
        return ret.substring(handle.getHandlelocation(), handle
            .getHandlelocation() + handle.getHandlelength());
    }


    /**
     * Print the list of free blocks
     */
    public void print() {

        if (blocks.isEmpty()) {
            System.out.println("No free blocks are available.");
            return;
        }

        int currentSizeOfBlocksList = blocks.get(0).getSize();
        System.out.print(blocks.get(0).getSize() + ":");
        for (int i = 0; i < blocks.size(); i++) {
            if (currentSizeOfBlocksList != blocks.get(i).getSize()) {
                currentSizeOfBlocksList = blocks.get(i).getSize();
                System.out.print("\n" + blocks.get(i).getSize() + ":");
            }
            System.out.print(" " + blocks.get(i).getLoc());
        }
        System.out.print("\n");
    }

    /**
     * @author Chris Luersen + Hulya Dogan
     * 
     *         Class describing how blocks work
     */
    private class Block {

        /**
         * Set the size of the block
         */
        private int blockSize;

        /**
         * Set the location of the block
         */
        private int blockLocation;

        /**
         * 
         * @param size
         *            Current size of block
         * @param location
         *            Current location of block
         */
        public Block(int size, int location) {
            blockSize = size;
            blockLocation = location;
        }


        /**
         * @return The size of the block
         */
        public int getSize() {
            return blockSize;
        }


        /**
         * @return The location of the block
         */
        public int getLoc() {
            return blockLocation;
        }
    }
}
