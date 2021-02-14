import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * On my honor:
 *
 * - I have not used source code obtained from another student,
 * or any other unauthorized source, either modified or
 * unmodified.
 *
 * - All source code and documentation used in my program is
 * either my original work, or was derived by me from the
 * source code published in the textbook for this course.
 *
 * - I have not discussed coding details about this exam with
 * anyone other than my instructor, ACM/UPE tutors, or the TAs
 * assigned to this course. I have violated neither the spirit
 * nor letter of this restriction.
 *
 * @author Hulya Dogan and Chris Luersen
 * @version 12/7/2020
 *
 */
public class GraphMethods {

    /**
     *
     * Implement a breadth-first traversal.
     *
     * @param theGraph
     *            is a Graph object to traverse
     *
     * @param start
     *            is the index of the starting Node for the traversal
     *
     * @return a List<Node> of all Nodes in the order they were visited.
     *         The node with index start is visited first.
     */
    public static List<Node> breadthFirstTraversal(Graph theGraph, int start) {
        int v = theGraph.getNumNodes();
        boolean[] visited = new boolean[v];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        List<Node> output = new LinkedList<Node>();
        visited[start] = true;
        queue.add(start);
        while (queue.size() != 0) {
            start = queue.poll();
            output.add(theGraph.getNode(start));
            @SuppressWarnings("unchecked")
            Iterator<Node> iter = theGraph.getNode(start).getNeighbors();
            List<Node> neighbours = new ArrayList<Node>();
            while (iter.hasNext()) {
                neighbours.add(iter.next());
                Iterator<Node> i = neighbours.listIterator();
                while (i.hasNext()) {
                    int n = i.next().getID();
                    if (!visited[n]) {
                        visited[n] = true;
                        queue.add(n);
                    }
                }

            }
        }
        return output;
    }


    /**
     * Implement a depth first traversal
     *
     * @param theGraph
     *            is a Graph object to traverse
     *
     * @param start
     *            is the index of the starting Node for the traversal
     *
     * @return a List<Node> of all Nodes in the order they were visited.
     *         The node with index start is visited first.
     */
    public static List<Node> depthFirstTraversal(Graph theGraph, int start) {
        int v = theGraph.getNumNodes();
        boolean[] visited = new boolean[v];
        for (int i = 0; i < v; i++) {
            visited[i] = false;
        }
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Stack<Node> myStack = new Stack();
        myStack.push(theGraph.getNode(start));
        List<Node> output = new LinkedList<Node>();
        while (!myStack.empty()) {
            int topNode = myStack.peek().getID();
            myStack.pop();
            if (!visited[topNode]) {
                visited[topNode] = true;
                output.add(theGraph.getNode(topNode));
            }

            @SuppressWarnings("unchecked")
            Iterator<Node> iter = theGraph.getNode(topNode).getNeighbors();
            while (iter.hasNext()) {
                int n = iter.next().getID();
                if (!visited[n]) {
                    myStack.push(theGraph.getNode(n));
                }
            }

        }
        return output;
    }


    /**
     * Find the length of the shortest path between two nodes in the graph.
     *
     * @param theGraph
     *            is a Graph object
     *
     * @param start
     *            is the index of the starting Node
     *
     * @param end
     *            is the index of target Node
     *
     * @return an int specifying the length of the shortest path between
     *         the Nodes at start and end. If start = end, return 0.
     */

    public static int shortestDistance(Graph theGraph, int start, int end) {
        // finding distance between a node and itself
        if (start == end) {
            return 0;
        }
        HashMap<Node, Integer> indexValues = new HashMap<Node, Integer>();
        Boolean[] checker = new Boolean[theGraph.getNumNodes()];
        Arrays.fill(checker, false);
        int index = 0;

        for (int i = 0; i < theGraph.getNumNodes(); i++) {
            indexValues.put(theGraph.getNode(i), index);
            index++;
        }
        int distance = 1;
        checker[indexValues.get(theGraph.getNode(start))] = true;
        @SuppressWarnings("unchecked")
        Iterator<Node> iter = theGraph.getNode(start).getNeighbors();

        List<Node> neighbours = new ArrayList<Node>();
        while (iter.hasNext()) {
            neighbours.add(iter.next());
        }
        for (Node v : neighbours) {
            checker[indexValues.get(v)] = true;
        }

        while (!neighbours.isEmpty() && !neighbours.contains(theGraph.getNode(
            end))) {
            List<Node> nextNeighbours = new ArrayList<Node>();
            for (Node points : neighbours) {
                @SuppressWarnings("unchecked")
                Iterator<Node> iter2 = theGraph.getNode(points.getID())
                    .getNeighbors();

                List<Node> tempNeighbours = new ArrayList<Node>();
                while (iter2.hasNext()) {
                    tempNeighbours.add(iter2.next());
                }
                for (Node point : tempNeighbours) {
                    int checkerIndex = indexValues.get(point);
                    if (!checker[checkerIndex]) {
                        nextNeighbours.add(point);
                        checker[checkerIndex] = true;

                    }

                }
            }
            neighbours = nextNeighbours;
            distance++;
        }

        if (neighbours.isEmpty()) {
            return -1;
        }
        return distance;
    }


    /**
     * helper method for the shortest distance
     * 
     * @param adj
     *            neighbor list
     * @param s
     *            beginning point
     * @param dest
     *            ending point
     * @param v
     * @return list
     */
    private static LinkedList<Integer> dijksta(
        ArrayList<ArrayList<Integer>> adj,
        int s,
        int dest,
        int v) {
        int[] pred = new int[v];
        int[] dist = new int[v];

        if (!helperBreadth(adj, s, dest, v, pred, dist)) {
            System.out.println("Given source and destination"
                + "are not connected");
            return null;
        }
        LinkedList<Integer> path = new LinkedList<Integer>();
        if (s == dest) {
            path.add(dest);
        }
        else {
            int strt = dest;
            path.add(strt);
            while (pred[strt] != -1) {
                path.add(pred[strt]);
                strt = pred[strt];
            }
        }

        return path;

    }


//
    /**
     * helper method for shortest distance
     * 
     * @param adj
     * @param src
     * @param dest
     * @param v
     * @param pred
     * @param dist
     * @return boolean
     */
    private static boolean helperBreadth(
        ArrayList<ArrayList<Integer>> adj,
        int src,
        int dest,
        int v,
        int[] pred,
        int[] dist) {

        if (src == dest) {
            return true;
        }
        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean[] visited = new boolean[v];
        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }
        visited[src] = true;
        dist[src] = 0;
        queue.add(src);
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (!visited[adj.get(u).get(i)]) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));
                    if (adj.get(u).get(i) == dest) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    /**
     * Find the longest shortest path from start to any other Node in the graph
     *
     * @param theGraph
     *            is a Graph object
     *
     * @param start
     *            is an int array of starting indices
     *
     * @return an int value specifying the length of the longest shortest path
     */
    public static int longestShortestPath(Graph theGraph, int start) {
        // Initialize the output integer
        int output = 0;
        int v = theGraph.getNumNodes();
        int[] myDist = new int[v];
        for (int i = 0; i < v; i++) {
            myDist[i] = shortestDistance(theGraph, start, i);
        }
        for (int j = 0; j < v; j++) {
            if (myDist[j] > output) {
                output = myDist[j];
            }

        }

        return output;

    }


    /**
     * Find the shortest path between two nodes in the graph.
     *
     * @param theGraph
     *            is a Graph object
     *
     * @param start
     *            is the index of the starting Node
     *
     * @param end
     *            is the index of target Node
     *
     * @return a List<Node> of all Nodes visited on the shortest path between
     *         start and end, in the order that they were visited.
     */

    public static List<Node> shortestPath(Graph theGraph, int start, int end) {
        LinkedList<Node> output = new LinkedList<Node>();
        int v = theGraph.getNumNodes();
        ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(
            v);
        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<Integer>());
        }
        int y;
        for (int i = 0; i < v; i++) {
            @SuppressWarnings("unchecked")
            Iterator<Node> iter2 = theGraph.getNode(i).getNeighbors();
            while (iter2.hasNext()) {
                y = iter2.next().getID();
                addEdge(adj, i, y);
            }
        }

        LinkedList<Integer> mylist = dijksta(adj, start, end, v);
        int mysize = mylist.size();
        for (int i = mysize - 1; i >= 0; i--) {
            int number = mylist.get(i);
            Node mypath = new Node(number);
            output.add(mypath);
        }

        return output;
    }


    /**
     * helper method for the shortest path
     * 
     * @param adj
     * @param i
     * @param j
     */
    private static void addEdge(
        ArrayList<ArrayList<Integer>> adj,
        int i,
        int j) {

        adj.get(i).add(j);

    }


    /**
     * Determine whether there exists a path meeting the goal.
     *
     * @param theGraph
     *            is a Graph object
     *
     * @param start
     *            is an int array of starting indices
     *
     * @param end
     *            is the index of target Node
     *
     * @return a List<Node> specifying the shortest path from ANY start
     *         Node to the target Node
     */
    public static List<Node> pathExists(Graph theGraph, int[] start, int end) {
        // Initialize the output list

        List<Node> output = new LinkedList<Node>();
        if (start.length == 0) {
            return output;
        }

        int[] dist = new int[start.length];
        for (int i = 0; i < start.length; i++) {
            dist[i] = shortestDistance(theGraph, start[i], end);

        }
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int j = 0; j < dist.length; j++) {
            if (dist[j] < min) {
                min = dist[j];
                minIndex = j;
            }

        }
        output = shortestPath(theGraph, start[minIndex], end);
        return output;
    }

}
