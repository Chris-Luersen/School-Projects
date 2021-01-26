/**
 * 
 * @author Hulya Dogan and Chris Luersen
 * @version 10/14/2020
 * @param <T>
 *            generic type
 *            Node class
 */

public class Node<T extends Comparable<T>> {

    /**
     * Key for the node
     */
    public T name;
    /**
     * Associated data
     */
    public Rectangle myRectangle;
    /**
     * left subtree
     */
    public Node<T> left;
    /**
     * right subtree
     */
    public Node<T> right;

    /**
     * Node class constructor with parameters.
     * 
     * @param key
     *            for the name
     * @param r
     *            for the rectangle
     */
    public Node(T key, Rectangle r) {
        this.name = key;
        this.myRectangle = r;
    }
    
}
