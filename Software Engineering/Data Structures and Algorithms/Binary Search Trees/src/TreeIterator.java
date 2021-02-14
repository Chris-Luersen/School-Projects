import java.util.Stack;

/**
 * @author Hulya Dogan (hulyad), Chris Luersen (cluersen)
 *
 * @version 2020-14-10
 * 
 *          Iterator class for the intersection method in BST.
 * 
 * @param <T>
 *            for
 *            the BST
 */
public class TreeIterator<T extends Comparable<T>> {
    /**
     * Instantiation of the stack which was used as iterator.
     */
    private Stack<Node<T>> stack = new Stack<>(); // stack for the iterator
    /**
     * Current node
     */
    private Node<T> current; // node to connect BST

    /**
     * Constructor for the iterator class.
     * 
     * @param argRoot
     *            root of the tree passed
     */
    public TreeIterator(Node<T> argRoot) {
        current = argRoot;
    }


    /**
     * Next method for the iterator
     * 
     * @return Node
     */

    public Node<T> next() {
        while (current != null) {
            stack.push(current);
            current = current.left;
        }

        current = stack.pop();
        Node<T> node = current;
        current = current.right;

        return node;
    }


    /**
     * has next method for the iterator
     * 
     * @return boolean
     */
    public boolean hasNext() {
        return (!stack.isEmpty() || current != null);
    }
}
