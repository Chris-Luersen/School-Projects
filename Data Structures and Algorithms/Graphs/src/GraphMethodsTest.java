
import student.TestCase;

/**
 * @author CS Staff
 * @version 2020-11-17
 */
public class GraphMethodsTest extends TestCase {

    private GraphMethods theMethods;

    private Graph theGraph;

    /**
     * The Test setup
     */
    public void setUp() throws Exception {
        theGraph = new Graph(8);
        theGraph.addEdge(1, 2);
        theGraph.addEdge(6, 0);
        theGraph.addEdge(7, 0);
        theGraph.addEdge(7, 2);
        theGraph.addEdge(6, 1);
        theGraph.addEdge(7, 1);
        theGraph.addEdge(4, 3);
        theGraph.addEdge(5, 4);
        theGraph.addEdge(6, 5);
        theGraph.addEdge(7, 6);
        theGraph.addEdge(0, 6);
        theGraph.addEdge(0, 7);
        theGraph.addEdge(1, 6);
        theGraph.addEdge(1, 7);
        theGraph.addEdge(2, 1);
        theGraph.addEdge(2, 7);
        theGraph.addEdge(4, 5);
        theGraph.addEdge(3, 4);
        theGraph.addEdge(5, 6);
        theGraph.addEdge(6, 7);
        theMethods = new GraphMethods();
    }


    /**
     * testBreadthFirstTraversal
     */
    public void testBreadthFirstTraversal() {
        assertEquals(8, theMethods.breadthFirstTraversal(theGraph, 0).size());
    }


    /**
     * testDepthFirstTraversal
     */
    public void testDepthFirstTraversal() {
        assertEquals(8, theMethods.depthFirstTraversal(theGraph, 0).size());

    }


    /**
     * testShortestDistance
     */
    public void testShortestDistance() {
        assertEquals(0, theMethods.shortestDistance(theGraph, 0, 0));
        assertEquals(2, theMethods.shortestDistance(theGraph, 0, 1));
        assertEquals(2, theMethods.shortestDistance(theGraph, 0, 2));
        assertEquals(2, theMethods.shortestDistance(theGraph, 1, 0));
        assertEquals(1, theMethods.shortestDistance(theGraph, 1, 2));
        assertEquals(1, theMethods.shortestDistance(theGraph, 1, 2));

    }


    /**
     * testLongestShortestPath
     */
    public void testLongestShortestPath() {
        assertEquals(4, theMethods.longestShortestPath(theGraph, 0));

    }


    /**
     * testShortestPath
     */
    public void testShortestPath() {

        theMethods.shortestPath(theGraph, 0, 0);
        theMethods.shortestPath(theGraph, 1, 5);
        assertEquals(5, theMethods.shortestPath(theGraph, 0, 3).size());
        assertEquals(3, theMethods.shortestPath(theGraph, 1, 5).size());
    }


    /**
     * testPathExists
     */
    public void testPathExists() {
        int[] nodeList1 = new int[theGraph.getNumNodes()];
        assertEquals(3, theMethods.pathExists(theGraph, nodeList1, 1).size());

    }

}
