import java.io.File;
import java.io.IOException;
import java.util.Scanner;
// import BST.Node;

/**
 * 
 */

/**
 * @author Hulya Dogan and Chris Luersen
 * @version 10/2/2020
 *          Parser class to parse the command line.
 */
public class Parser {

    /**
     * constructor
     * 
     * @param <T>
     *            for the parser class.
     */
    public <T> Parser() {
        // intentionally left blank.
    }


    /**
     * begin parsing method to parse the command lines from the text file.
     * 
     * @param file
     *            to parse
     * @param tree
     *            to store
     * @return boolean
     */
    public static boolean beginParsing(String file, BST<String> tree)

    {
        try {
            Scanner fileScanner = new Scanner(new File(file));
            Scanner textScanner;

            while (fileScanner.hasNext()) {
                String currentLine = fileScanner.nextLine();

                if (currentLine.length() == 0) {
                    continue;
                }
                textScanner = new Scanner(currentLine);
                if (!textScanner.hasNext()) {
                    continue;
                }

                // textScanner = new Scanner(currentLine.trim());
                String command = textScanner.next();
                if (command.equals("insert")) {

                    String name = textScanner.next();
                    String strx = textScanner.next();
                    String stry = textScanner.next();
                    String strw = textScanner.next();
                    String strh = textScanner.next();

                    int x = Integer.parseInt(strx);
                    int y = Integer.parseInt(stry);
                    int w = Integer.parseInt(strw);
                    int h = Integer.parseInt(strh);

                    Rectangle tempRectangle = new Rectangle(x, y, w, h);
                    tree.insert(name, tempRectangle);
                    // System.out.println(tree);
                }
                if (command.equals("dump")) {
                    tree.dump();
                }
                if (command.equals("remove")) {
                    String subcurrentLine = currentLine.substring(7);
                    Scanner newscanner = new Scanner(subcurrentLine.trim());
                    String temp = textScanner.next();
                    if (!textScanner.hasNext()) {
                        tree.remove(temp);
                    }
                    else {
                        String strx = newscanner.next();
                        String stry = newscanner.next();
                        String strw = newscanner.next();
                        String strh = newscanner.next();
                        int x = Integer.parseInt(strx);
                        int y = Integer.parseInt(stry);
                        int w = Integer.parseInt(strw);
                        int h = Integer.parseInt(strh);
                        tree.remove(x, y, w, h);
                    }

                }
                if (command.equals("intersections")) {
                    tree.intersections();
                }

                if (command.equals("search")) {
                    String sname = textScanner.next();
                    tree.search(sname);
                }

                if (command.equals("regionsearch")) {

                    String strx = textScanner.next();
                    String stry = textScanner.next();
                    String strw = textScanner.next();
                    String strh = textScanner.next();

                    int x = Integer.parseInt(strx);
                    int y = Integer.parseInt(stry);
                    int w = Integer.parseInt(strw);
                    int h = Integer.parseInt(strh);
                    tree.regionsearch(x, y, w, h);

                }

            }
            fileScanner.close();
        }

        catch (IOException e) {
            // Intentionally left blank.

        }
        return true;

    }
}
