import java.util.Stack;

/**
 * @author Hulya Dogan, Chris Luersen
 * @version 10/11/2020
 * @param <T>
 *            the generic type; extends Comparable
 */

public class BST<T extends Comparable<T>> {
    /** The root. */
    protected Node<T> rt;
    /** The size */
    protected int size;

    /**
     * BST class constructor.
     */

    public BST() {
        // Intentionally left blank.
    }


    /**
     * Insert key-value pair into BST.
     * 
     * @param newrname
     *            to insert
     * @param r
     *            to insert
     */
    public void insert(T newrname, Rectangle r) {
        if (eligible(newrname, r)) {
            rt = insert(this.rt, newrname, r);
        }
        else {
            System.out.println("Rectangle rejected: (" + newrname + ", " + r
                .getx() + ", " + r.gety() + ", " + r.getWidth() + ", " + r
                    .getHeight() + ")");
        }
    }


    /**
     * Private helper method for the public insert method.
     * 
     * @param root
     * @param newrname
     * @param r
     * @return root
     */
    private Node<T> insert(Node<T> root, T newrname, Rectangle r) {
        if (root == null) {
            root = new Node<T>(newrname, r);
            size = size + 1;
            System.out.println("Rectangle accepted: (" + newrname + ", " + r
                .getx() + ", " + r.gety() + ", " + r.getWidth() + ", " + r
                    .getHeight() + ")");
            return root;
        }
        int cmp = newrname.compareTo(root.name);
        if (cmp <= 0) {
            root.left = this.insert(root.left, newrname, r);
            return root;
        }
        else {
            root.right = insert(root.right, newrname, r);
            return root;
        }
    }


    /**
     * Eligible method for insertion
     * 
     * @return boolean
     * @param cname
     *            and R
     */
    private boolean eligible(T cname, Rectangle r) {
        if (r.getx() < 0 || r.gety() < 0 || r.getHeight() < 1 || r
            .getWidth() < 1) {
            return false;
        }
        else if (r.getx() + r.getWidth() > 1024 || r.gety() + r
            .getHeight() > 1024) {
            return false;
        }
        String name = cname.toString();

        if (Character.isLetter(name.charAt(0))) {

            return (!(bTcheckval(rt, cname, r)));
        }
        return false;
    }


    /**
     * compares the rectangle inputs if there exists an identical rectangle
     * 
     * @param myroot
     * @param cname
     * @param R
     * @return
     */
    private boolean bTcheckval(Node<T> myroot, T cname, Rectangle r) {
        if (myroot == null) {
            return false;
        }
        if (myroot.name.equals(cname) && myroot.myRectangle.getx() == r.getx()
            && myroot.myRectangle.gety() == r.gety() && myroot.myRectangle
                .getWidth() == r.getWidth() && myroot.myRectangle
                    .getHeight() == r.getHeight()) {
            return true;
        }
        else {
            return (bTcheckval(myroot.left, cname, r) || bTcheckval(
                myroot.right, cname, r));
        }
    }


    /**
     * is Empty method to check if the tree is empty.
     * 
     * @return size
     */
    public boolean isEmpty() {
        return size() == 0;
    }


    /**
     * Check the size of the tree
     * 
     * @return number of key-value pairs in BST
     */
    public int size() {
        return this.size;
    }


    /**
     * Helper method to calculate the depth of the tree
     * 
     * @return
     */
    private int depth(Node<T> node, T key, int level) {
        if (node == null) {
            return 0;
        }
        if (node.name == key) {
            return level;
        }
        int nlevel = depth(node.left, key, level + 1);
        if (nlevel != 0) {
            return nlevel;
        }
        nlevel = depth(node.right, key, level + 1);
        return nlevel;
    }


    /**
     * helper method to find the minimum element on the left
     * 
     * @param root
     */
    private Node<T> minimumElement(Node<T> root) {
        if (root.left == null) {
            return root;
        }
        else {
            return minimumElement(root.left);
        }
    }


    /**
     * Contains method checks if tree contains a rectangle with name key.
     * 
     * @param root
     *            to check contains
     * @param key
     *            for contains
     * @return boolean
     */

    private boolean contains(Node<T> root, T key) {
        if (root == null) {
            return false;
        }
        if (root.name.compareTo(key) == 0) {
            return true;
        }
        if (root.name.compareTo(key) > 0) {
            return contains(root.left, key);
        }
        else {
            return contains(root.right, key);
        }
    }


    /**
     * remove by name method.
     * 
     * @param key
     *            to remove
     */
    public void remove(T key) {
        if (!contains(rt, key)) {
            System.out.println("Rectangle rejected: " + key);
        }
        else {
            size--;
            rt = remove(rt, key);
        }
    }


    /**
     * remove by name private helper method.
     * 
     * @param key
     *            the key to delete
     */
    private Node<T> remove(Node<T> root, T key) {
        if (root.name.compareTo(key) > 0) {
            root.left = remove(root.left, key);
        }
        else if (root.name.compareTo(key) < 0) {
            root.right = remove(root.right, key);
        }
        else {
            if (root.left != null && root.right != null) {
                Node<T> temp = root;
                Node<T> minNodeForRight = minimumElement(temp.right);
                root.name = minNodeForRight.name;
                root.myRectangle = minNodeForRight.myRectangle;
                root.right = remove(root.right, minNodeForRight.name);
            }
            else if (root.left != null) {
                root = root.left;
            }
            else if (root.right != null) {
                root = root.right;
            }
            else {
                root = null;
            }
        }
        return root;
    }


    /**
     * Private node for remove method.
     * 
     * @param root
     * @param x
     * @param y
     * @param w
     * @param h
     * @param key
     * @return node
     */
    private Node<T> remove(Node<T> root, int x, int y, int w, int h, T key) {
        if (root == null) {
            return null;
        }
        if ((root.myRectangle.getx() == x && root.myRectangle.gety() == y
            && root.myRectangle.getWidth() == w && root.myRectangle
                .getHeight() == h) && (root.name == key)) {
            if (root.left != null && root.right != null) {
                Node<T> temp = root;
                Node<T> minNodeForRight = minimumElement(temp.right);
                root.name = minNodeForRight.name;
                root.myRectangle = minNodeForRight.myRectangle;
                root.right = remove(root.right, root.myRectangle.getx(),
                    root.myRectangle.gety(), root.myRectangle.getWidth(),
                    root.myRectangle.getHeight(), minNodeForRight.name);
            }
            else if (root.left != null) {
                root = root.left;
            }
            else if (root.right != null) {
                root = root.right;
            }
            else {
                root = null;
            }
            return root;
        }
        root.left = remove(root.left, x, y, w, h, key);
        root.right = remove(root.right, x, y, w, h, key);
        return root;
    }


    /**
     * helper method to find the name of the rectangle to be removed
     * by looking at the coordinates
     * 
     * @param root
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     */
    private T removegetname(Node<T> root, int x, int y, int w, int h) {

        if (root == null) {
            return null;
        }
        if (root.myRectangle.getx() == x && root.myRectangle.gety() == y
            && root.myRectangle.getWidth() == w && root.myRectangle
                .getHeight() == h) {
            T temp = root.name;
            return temp;
        }
        T temp1 = removegetname(root.left, x, y, w, h);
        if (temp1 != null) {
            return temp1;
        }
        T temp2 = removegetname(root.right, x, y, w, h);
        {
            return temp2;
        }
    }


    /**
     * Remove method based on the coordinates of the rectangle.
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

    public void remove(int x, int y, int w, int h) {
        if (x < 0 || y < 0 || w < 1 || h < 1) {
            System.out.println("Rectangle rejected: (" + x + ", " + y + ", " + w
                + ", " + h + ")");
        }
        else {
            T name = removegetname(rt, x, y, w, h);
            remove(rt, x, y, w, h, name);

            if (name != null) {
                size--;
            }
            else {
                System.out.println("Rectangle rejected: (" + x + ", " + y + ", "
                    + w + ", " + h + ")");
            }
        }
    }


    /**
     * intersections method.
     */

    public void intersections() {
        System.out.println("Intersection pairs:");
        iterator();
    }


    /**
     * Private helper method for regionsearch method.
     * 
     * @param root
     * @param rx
     * @param ry
     * @param rw
     * @param rh
     */
    private void regionsearch(Node<T> root, int rx, int ry, int rw, int rh) {
        if (root == null) {
            return;
        }
        if (!(rx + 1 > (root.myRectangle.getx() + root.myRectangle.getWidth())
            || (root.myRectangle.getx() + 1 > (rx + rw)) || ry
                + 1 > (root.myRectangle.gety() + root.myRectangle.getHeight())
            || (root.myRectangle.gety() + 1 > (ry + rh)))) {
            System.out.println("(" + root.name + ", " + root.myRectangle.getx()
                + ", " + root.myRectangle.gety() + ", " + root.myRectangle
                    .getWidth() + ", " + root.myRectangle.getHeight() + ")");
        }
        regionsearch(root.left, rx, ry, rw, rh);
        regionsearch(root.right, rx, ry, rw, rh);
    }


    /**
     * Private helper method for region search method.
     * 
     * @param root
     *            to search region
     * @param rx
     *            to search region
     * @param ry
     *            to search region
     * @param rw
     *            to search region
     * @param rh
     *            to search region
     * @return found
     */
    private int region(Node<T> root, int rx, int ry, int rw, int rh) {
        int found;
        if (root == null) {
            return 0;
        }
        if (!(rx + 1 > (root.myRectangle.getx() + root.myRectangle.getWidth())
            || (root.myRectangle.getx() + 1 > (rx + rw)) || ry
                + 1 > (root.myRectangle.gety() + root.myRectangle.getHeight())
            || (root.myRectangle.gety() + 1 > (ry + rh)))) {
            found = 1;
            return found;
        }
        found = region(root.left, rx, ry, rw, rh);
        if (found > 0) {
            return found;
        }
        else {
            return region(root.right, rx, ry, rw, rh);
        }
    }


    /**
     * RegionSearch method
     * 
     * @param rx
     *            to region search
     * @param ry
     *            to region search
     * @param rw
     *            to region search
     * @param rh
     *            to region search
     */

    public void regionsearch(int rx, int ry, int rw, int rh) {
        if (rt != null && rw > 0 && rh > 0) {
            int found = region(rt, rx, ry, rw, rh);
            if (found > 0) {
                System.out.println("Rectangles intersecting region (" + rx
                    + ", " + ry + ", " + rw + ", " + rh + "):");
                regionsearch(rt, rx, ry, rw, rh);
            }
        }
    }


    /**
     * helper method returns true if the rectangle is found
     * 
     * @param root
     * @param key
     * @return
     */
    private boolean searchresult(Node<T> root, T key) {
        if (root == null) {
            return false;
        }
        if (root.name.compareTo(key) == 0) {
            return true;
        }
        boolean temp1 = searchresult(root.left, key);
        if (temp1) {
            return true;
        }
        boolean temp2 = searchresult(root.right, key);
        {
            return temp2;
        }
    }


    /**
     * private node search helper method for the search method.
     * 
     * @param root
     *            as node
     * @param key
     *            s name
     */
    private void search(Node<T> root, T key) {
        if (root.name.compareTo(key) == 0) {
            System.out.println("Rectangle found: (" + key + ", "
                + root.myRectangle.getx() + ", " + root.myRectangle.gety()
                + ", " + root.myRectangle.getWidth() + ", " + root.myRectangle
                    .getHeight() + ")");
        }
        if (root.left != null) {
            search(root.left, key);
        }
        if (root.right != null) {
            search(root.right, key);
        }
    }


    /**
     * search method.
     * 
     * @param key
     *            as name.
     */
    public void search(T key) {
        if (!searchresult(rt, key)) {
            System.out.println("Rectangle not found: " + key);
        }
        else {
            search(rt, key);
        }
    }


    /**
     * Private helper method for dump
     * 
     * @param root
     */
    private void dumpRec(Node<T> root) {
        if (rt == null) {
            System.out.println("Node has depth 0, Value (null)");
        }
        if (root == null) {
            return;
        }
        dumpRec(root.left);
        int depth = depth(rt, root.name, 0);
        System.out.println("Node has depth " + depth + ", Value (" + root.name
            + ", " + root.myRectangle.getx() + ", " + root.myRectangle.gety()
            + ", " + root.myRectangle.getWidth() + ", " + root.myRectangle
                .getHeight() + ")");
        dumpRec(root.right);
    }


    /**
     * dump method, print each BST node with in-order traversal
     */
    public void dump() {
        System.out.println("BST dump:");
        dumpRec(rt);
        System.out.println("BST size is: " + size);
    }


    /**
     * Iterator method for Intersection method.
     */
    public void iterator() {
        TreeIterator<T> it1 = new TreeIterator<T>(rt);
        TreeIterator<T> it2 = new TreeIterator<T>(rt);
        Stack<Node<T>> stack1 = new Stack<>();
        Stack<Node<T>> stack2 = new Stack<>();
        while (it2.hasNext()) {
            Node<T> temp1 = it2.next();
            stack1.push(temp1);
        }
        while (it1.hasNext()) {
            Node<T> root1 = it1.next();
            while (!stack1.isEmpty()) {
                Node<T> rootref1 = stack1.pop();
                if (!(root1.name == rootref1.name && root1.myRectangle
                    .getx() == rootref1.myRectangle.getx() && root1.myRectangle
                        .gety() == rootref1.myRectangle.gety()
                    && root1.myRectangle.getWidth() == rootref1.myRectangle
                        .getWidth() && root1.myRectangle
                            .getHeight() == rootref1.myRectangle.getHeight())) {
                    stack2.push(rootref1);
                    if (!(rootref1.myRectangle.getx() + 1 > (root1.myRectangle
                        .getx() + root1.myRectangle.getWidth())
                        || (root1.myRectangle.getx() + 1 > (rootref1.myRectangle
                            .getx() + rootref1.myRectangle.getWidth()))
                        || rootref1.myRectangle.gety() + 1 > (root1.myRectangle
                            .gety() + root1.myRectangle.getHeight())
                        || (root1.myRectangle.gety() + 1 > (rootref1.myRectangle
                            .gety() + rootref1.myRectangle.getHeight())))) {
                        System.out.println("(" + rootref1.name + ", "
                            + rootref1.myRectangle.getx() + ", "
                            + rootref1.myRectangle.gety() + ", "
                            + rootref1.myRectangle.getWidth() + ", "
                            + rootref1.myRectangle.getHeight() + ") : " + "("
                            + root1.name + ", " + root1.myRectangle.getx()
                            + ", " + root1.myRectangle.gety() + ", "
                            + root1.myRectangle.getWidth() + ", "
                            + root1.myRectangle.getHeight() + ")");
                    }
                }
            }
            while (!stack2.isEmpty()) {
                Node<T> temp2 = stack2.pop();
                stack1.push(temp2);
            }
        }
    }

}
