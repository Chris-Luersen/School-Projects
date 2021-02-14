/**
 * The class containing handles.
 *
 * @author Chris Luersen + Hulya Dogan
 * @version 09/15/2020
 */

public class Handle {
// Fields
    private int blockSize;
    private int handleLocation;
    private String handleName;
    private int handleLength;

// Constructor
    /**
     * @param handleSize
     *            Handle size allocation
     * @param location
     *            Handle location allocation
     * @param name
     *            Handle name allocation
     * @param length
     *            Handle length allocation
     */
    public Handle(int handleSize, int location, String name, int length) {
        blockSize = handleSize;
        handleLocation = location;
        handleName = name;
        handleLength = length;
    }


// Methods
    /**
     * @return The handle size.
     */
    public int getHandleSize() {
        return blockSize;
    }


    /**
     * @return The handle location.
     */
    public int getHandlelocation() {
        return handleLocation;
    }


    /**
     * @return The handle name.
     */
    public String getHandleName() {
        return handleName;
    }


    /**
     * @return The handle length.
     */
    public int getHandlelength() {
        return handleLength;
    }


    /**
     * @param size
     *            Setter for handle size.
     */
    public void setHandleSize(int size) {
        blockSize = size;
    }


    /**
     * @param location
     *            Setter for handle location.
     */
    public void setHandleLoc(int location) {
        handleLocation = location;
    }


    /**
     * @param length
     *            Setter for handle length.
     */
    public void setHandleLength(int length) {
        handleLength = length;
    }
}
