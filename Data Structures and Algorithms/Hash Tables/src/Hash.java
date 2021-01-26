/**
 * Stub for hash table class
 *
 * @author Chris Luersen + Hulya Dogan
 * @version 09/15/2020
 */

public class Hash {
// Initializer
    /**
     * Create a new Hash object.
     */
    public Hash() {
        // Nothing here yet
    }


    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on
     * hash functions
     *
     * @param s
     *            The string that we are hashing
     * @param m
     *            The size of the hash table
     * @return The home slot for that string
     */
    // You can make this private in your project.
    // This is public for distributing the hash function in a way
    // that will pass milestone 1 without change.
    public int hash(String s, int m) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % m);
    }

// Fields
    private int hashSize;
    private int totalEntries;
    private Handle[] handles;
    private MemoryManager memoryManager;

// Constructor

    /**
     * Create a new Hash object.
     * 
     * @param size
     *            The size of the hash to initialize
     * @param memoryManagerSize
     *            Size of the memory manager to initialize
     * 
     */
    public Hash(int size, int memoryManagerSize) {
        handles = new Handle[size];
        totalEntries = 0;
        hashSize = size;
        memoryManager = new MemoryManager(memoryManagerSize);
    }


// Methods
    /**
     * Add string to hash table.
     * 
     * @param name
     *            The string to be added
     */
    public void add(String name) {
        int originalPosition = hash(name, hashSize);
        int newPosition = originalPosition;
        int tombstone = -1;
        for (int i = 0; handles[newPosition] != null && i < hashSize * 5; i++) {
            newPosition = (int)((originalPosition + Math.pow(i, 2)) % hashSize);
            if (handles[newPosition] == null) {
                continue;
            }
            if (name.compareTo(handles[newPosition].getHandleName()) == 0) {
                System.out.println("|" + name + "|"
                    + " duplicates a record already in the Name database.");
                return;
            }
            else if (tombstone == -1 && handles[newPosition].getHandleName()
                .compareTo("[del]") == 0) {
                tombstone = newPosition;
            }
        }

        if (tombstone == -1 && handles[newPosition] != null) {
            return;
        }
        else if (tombstone != -1) {
            newPosition = tombstone;
        }
        handles[newPosition] = new Handle(size(name.length()), memoryManager
            .insert(name.getBytes(), name.length()), name, name.length());
        totalEntries++;

        // Check Hash Table size, and update if necessary
        if (totalEntries > hashSize / 2) {
            hashSize *= 2;
            newSize(hashSize);
        }
        System.out.println("|" + name + "|"
            + " has been added to the Name database.");

    }


    /**
     * Set space in memory manager.
     * 
     * @param length
     *            Length of the handle
     * @return The space needed for the length
     */
    public int size(int length) {
        int powTwo = 1;
        while (powTwo < length) {
            powTwo *= 2;
        }
        return powTwo;
    }


    /**
     * Update size of hash.
     * 
     * @param newSize
     *            Update hash size
     */
    private void newSize(int newSize) {

        Handle[] newHandle = new Handle[newSize];
        for (int i = 0; i < newSize / 2; i++) {
            if (handles[i] != null) {
                int position = hash(handles[i].getHandleName(), hashSize);
                for (int j = 0; newHandle[position] != null; j++) {
                    position = (int)((position + Math.pow(j, 2)) % hashSize);
                }
                newHandle[position] = new Handle(handles[i].getHandleSize(),
                    handles[i].getHandlelocation(), handles[i].getHandleName(),
                    handles[i].getHandlelength());
            }
        }
        handles = newHandle;
        System.out.println("Name hash table size doubled to " + newSize
            + " slots.");
    }


    /**
     * Print the commands of what happened.
     * 
     * @param command
     *            Print commands as strings
     */
    public void print(String command) {
        if (command.compareTo("blocks") == 0) {
            memoryManager.print();
        }
        else if (command.compareTo("hashtable") == 0) {
            for (int i = 0; i < hashSize; i++) {
                if (handles[i] != null && handles[i].getHandleName().compareTo(
                    "[del]") != 0) {
                    System.out.println("|" + handles[i].getHandleName() + "| "
                        + i);
                }
            }
            System.out.println("Total records: " + totalEntries);
        }
    }


    /**
     * Delete a string from the hash.
     * 
     * @param name
     *            Name to delete
     */
    public void delete(String name) {
        int position = search(name);
        if (position != -1) {
            memoryManager.remove(handles[position]);
            handles[position] = new Handle(-1, -1, "[del]", -1);
            totalEntries--;
            System.out.println("|" + name + "|"
                + " has been deleted from the Name database.");
        }
        else {
            System.out.println("|" + name + "|"
                + " not deleted because it does not "
                + "exist in the Name database.");
        }
    }


    /**
     * Search for a string
     * 
     * @param name
     *            The string to search for
     * @return The position of the string
     */
    private int search(String name) {
        int originalPosition = hash(name, hashSize);
        int position = originalPosition;

        for (int i = 0; handles[position] != null && i < hashSize * 5; i++) {
            position = (int)((originalPosition + Math.pow(i, 2)) % hashSize);
            if (handles[position] == null) {
                continue;
            }
            if (name.compareTo(handles[position].getHandleName()) == 0) {
                return position;
            }
        }
        return -1;
    }


    /**
     * Update a record
     * 
     * @param command
     *            Command to do(add/remove)
     * @param name
     *            Name of the string
     * @param type
     *            Category type
     * @param data
     *            Data that's updated
     */
    public void update(String command, String name, String type, String data) {
        int location = search(name);
        if (location == -1) {
            System.out.println("|" + name + "|"
                + " not updated because it does "
                + "not exist in the Name database.");
            return;
        }
        if (!findData(location, type) && command.equals("delete")) {
            System.out.println("|" + name + "|"
                + " not updated because the field |" + type
                + "| does not exist");
        }
        else if (command.equals("delete")) {
            System.out.println("Updated Record: |" + memoryManager.get(
                handles[location]) + "|");
        }
        if (command.equals("add")) {
            String str = memoryManager.get(handles[location]) + "<SEP>" + type
                + "<SEP>" + data;
            memoryManager.remove(handles[location]);
            handles[location].setHandleLength(str.length());
            handles[location].setHandleSize(size(str.length()));
            handles[location].setHandleLoc(memoryManager.insert(str.getBytes(),
                handles[location].getHandleSize()));
            System.out.println("Updated Record: |" + memoryManager.get(
                handles[location]) + "|");
        }

    }


    /**
     * Search for data in hash
     * 
     * @param position
     *            the record entry number
     * @return whether it was found
     */
    private boolean findData(int position, String type) {
        String string = memoryManager.get(handles[position]);
        int location = string.indexOf("<SEP>" + type + "<SEP>");
        if (location == -1) {
            return false;
        }
        int dataLength = 10 + type.length();
        String[] seperate = string.split("<SEP>");
        for (int i = 0; i < seperate.length; i++) {
            if (seperate[i].equals(type)) {
                dataLength += seperate[i + 1].length();
            }
        }
        string = string.substring(0, location) + string.substring(location
            + dataLength);
        memoryManager.remove(handles[position]);
        handles[position].setHandleLength(string.length());
        handles[position].setHandleSize(size(string.length()));
        handles[position].setHandleLoc(memoryManager.insert(string.getBytes(),
            size(string.length())));
        return true;
    }
}
