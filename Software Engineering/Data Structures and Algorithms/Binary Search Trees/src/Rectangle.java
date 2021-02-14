/**
 * 
 */

/**
 * @author Hulya Dogan and Chris Luersen
 * @version 10/2/2020
 *          Rectangle class to store the rectangle object
 */
public class Rectangle {

    private int x;
    private int y;
    private int w;
    private int h;

    /**
     * Constructs a new rectangle whose top-left corner
     * is at (0, 0) in the coordinate space, and
     * whose width and height are zero.
     * 
     * @param x
     *            coordinates
     * @param y
     *            coordinates
     * @param w
     *            width
     * @param h
     *            height
     */

    public Rectangle(int x, int y, int w, int h) {

        /**
         * this.setLocation(x, y);
         * this.setWidth(w);
         * this.setHeight(h);
         */
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;

    }


    /**
     * getting x coordinate
     * 
     * @return the X coordinate of
     *         the bounding Rectangle in double precision.
     */

    public int getx() {

        return this.x;

    }


    /**
     * getting the y coordinate
     * 
     * @return y coordinate of the bounding Rectangle in double precision.
     */

    public int gety() {
        return this.y;
    }


    /**
     * getting the width of the rectangle
     * 
     * @return the width of the bounding Rectangle in double precision.
     */

    public int getWidth() {
        return this.w;
    }


    /**
     * getting the height of the rectangle
     * 
     * @return the height of the bounding Rectangle in double precision.
     */
    public int getHeight() {
        return this.h;
    }
}

/*
 * private void setWidth(int newWidth) {
 * if (newWidth <= 0) {
 * throw new IllegalArgumentException();
 * }
 * this.w = newWidth;
 * }
 * 
 * 
 * private void setHeight(int newHeight) {
 * if (newHeight <= 0) {
 * throw new IllegalArgumentException();
 * }
 * this.h = newHeight;
 * }
 * 
 * 
 * private void setLocation(int newX, int newY) {
 * if (newX < 0 || newY < 0) {
 * throw new IllegalArgumentException();
 * }
 * this.x = newX;
 * this.y = newY;
 * }
 * }
 * /**
 * equals method to compare with another rectangle.
 * 
 * @param other
 * 
 * @return boolean
 */

/*
 * public boolean equals(Rectangle other) {
 * return (other != null && this.x == other.x && this.y == other.y
 * && this.w == other.w && this.h == other.h);
 * }
 */
