/**
 * This class is copied from my DoublyLinkedList implementation from CS2114
 *
 * @author Chris Luersen + Hulya Dogan
 * @version 09/15/2020
 *
 * @param <E>
 *            The type of object the class will store
 */
public class DoublyLinkedList<E> {
// Start private class
    /**
     * Node implementation
     *
     * @author Chris Luersen + Hulya Dogan
     * @version 09/14/2020
     * 
     * @param <E>
     *            Type of data stored in node
     */
    private static class Node<E> {
// Fields
        private Node<E> next;
        private Node<E> previous;
        private E data;

// Constructor
        /**
         * Creates a new node with data.
         *
         * @param d
         *            data to be stored in node
         */
        public Node(E d) {
            data = d;
        }
// Methods
// Setters


        /**
         * Sets new node to the right.
         *
         * @param rightNode
         *            identifier for the new node
         */
        public void setNext(Node<E> rightNode) {
            next = rightNode;
        }


        /**
         * Sets new node to the right.
         *
         * @param leftNode
         *            identifier for the now to the left
         */
        public void setPrevious(Node<E> leftNode) {
            previous = leftNode;
        }


// Node allocation
        /**
         * Gets the next node.
         *
         * @return the next node
         */
        public Node<E> next() {
            return next;
        }


        /**
         * Gets the node before this one.
         *
         * @return the node before this one
         */
        public Node<E> previous() {
            return previous;
        }


        /**
         * Gets current node data.
         *
         * @return current node data
         */
        public E getData() {
            return data;
        }
    }

    /**
     * Number of nodes.
     */
    private int size;

    /**
     * First node.
     */
    private Node<E> head;

    /**
     * Last node.
     */
    private Node<E> tail;

    /**
     * Create a new DoublyLinkedList object.
     */
    public DoublyLinkedList() {
        init();
    }


    /**
     * Initializes list.
     */
    private void init() {
        head = new DoublyLinkedList.Node<E>(null);
        tail = new DoublyLinkedList.Node<E>(null);
        head.setNext(tail);
        tail.setPrevious(head);
        size = 0;
    }


// Checkers
    /**
     * Size check.
     *
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * New size.
     *
     * @return number of nodes
     */
    public int size() {
        return size;
    }


    /**
     * Removes all nodes.
     */
    public void clear() {
        init();
    }


    /**
     * Search list.
     *
     * @param obj
     *            data searched for
     * @return true if list contains data
     */
    public boolean contains(E obj) {
        return lastIndexOf(obj) != -1;
    }


    /**
     * Gets the data's position.
     *
     * @param index
     *            Position of data
     * @return Data at that position
     * @throws IndexOutOfBoundsException
     *             If there is no data returned
     */
    public E get(int index) {
        return getNodeAtIndex(index).getData();
    }


    /**
     * Adds a node to the end of the list.
     *
     * @param newEntry
     *            Node to add
     */
    public void add(E newEntry) {
        add(size(), newEntry);
    }


    /**
     * Adds a node to a specific position.
     *
     * @param index
     *            position where to add it to
     * @param obj
     *            node to add
     * @throws IndexOutOfBoundsException
     *             if the position is <0 or >size
     * @throws IllegalArgumentException
     *             if there is no data entered
     */
    public void add(int index, E obj) {
        if (index < 0 || size < index) {
            throw new IndexOutOfBoundsException();
        }
        if (obj == null) {
            throw new IllegalArgumentException("Cannot add null "
                + "objects to a list");
        }

        Node<E> nodeAfterAddedNode;
        if (index == size) {
            nodeAfterAddedNode = tail;
        }
        else {
            nodeAfterAddedNode = getNodeAtIndex(index);
        }

        Node<E> addedNode = new Node<E>(obj);
        addedNode.setPrevious(nodeAfterAddedNode.previous());
        addedNode.setNext(nodeAfterAddedNode);
        nodeAfterAddedNode.previous().setNext(addedNode);
        nodeAfterAddedNode.setPrevious(addedNode);
        size++;

    }


    /**
     * Gets the node at the position.
     * 
     * @param index
     *            position of the node
     * @return node at that position
     */
    private Node<E> getNodeAtIndex(int index) {
        if (index < 0 || size() <= index) {
            throw new IndexOutOfBoundsException("No element exists at "
                + index);
        }
        Node<E> currentNode = head.next(); // as we have a sentinel node
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next();
        }
        return currentNode;
    }


    /**
     * Gets last position of data.
     *
     * @param obj
     *            data to look for
     * @return the last position of the data
     */
    public int lastIndexOf(E obj) {
        /*
         * We should go from the end of the list as then we an stop once we find
         * the
         * first one
         */
        Node<E> currentNode = tail.previous();
        for (int i = size() - 1; i >= 0; i--) {
            if (currentNode.getData().equals(obj)) {
                return i;
            }
            currentNode = currentNode.previous();
        }
        return -1; // if we do not find it
    }


    /**
     * Removes the data at the specified position.
     *
     * @param index
     *            Position of data
     * @throws IndexOutOfBoundsException
     *             Error if data isn't there
     * @return True if the data is found
     */
    public boolean remove(int index) {
        Node<E> removedNode = getNodeAtIndex(index);
        removedNode.previous().setNext(removedNode.next());
        removedNode.next().setPrevious(removedNode.previous());
        size--;
        return true;
    }


    /**
     * Removes the first instance found of the data.
     *
     * @param obj
     *            Data to remove
     * @return true If the data was found and removed it
     */

    public boolean remove(E obj) {
        Node<E> currentNode = head.next();
        while (!currentNode.equals(tail)) {
            if (currentNode.getData().equals(obj)) {
                currentNode.previous().setNext(currentNode.next());
                currentNode.next().setPrevious(currentNode.previous());
                size--;
                return true;
            }
            currentNode = currentNode.next();
        }
        return false;
    }


    /**
     * Returns DoublyLinkedList as a string.
     *
     * @return A string of the DoublyLinkedList
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        if (!isEmpty()) {
            Node<E> currentNode = head.next();
            while (currentNode != tail) {
                E element = currentNode.getData();
                stringBuilder.append(element.toString());
                if (currentNode.next != tail) {
                    stringBuilder.append(", ");
                }
                currentNode = currentNode.next();
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
