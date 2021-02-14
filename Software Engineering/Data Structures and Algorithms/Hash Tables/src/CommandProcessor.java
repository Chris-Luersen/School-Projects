import java.io.File;
import java.util.Scanner;

/**
 * Responsible for assigning
 * methods to command strings.
 *
 * @author Chris Luersen + Hulya Dogan
 * @version 09/15/2020
 */
public class CommandProcessor {
// Constructor
    /**
     * @param file
     *            File being parsed
     * @param hashtable
     *            The hash table being parsed
     * @return true if hash was parsed successfully
     */
    public static boolean parse(String file, Hash hashtable) {
        try {
            // Variables
            Scanner fileScanner = new Scanner(new File(file));
            Scanner textScanner;
            String text = "";

            while (fileScanner.hasNextLine()) {
                text = "";
                String currentLine = fileScanner.nextLine();
                if (currentLine.length() == 0) {
                    continue;
                }
                textScanner = new Scanner(currentLine);
                if (!textScanner.hasNext()) {
                    continue;
                }
                String command = textScanner.next();
// Command
// Add
                if (command.equals("add")) {
                    if (!textScanner.hasNext()) {
                        System.out.println("Invalid arguments for add");
                        continue;
                    }
                    text += textScanner.next();
                    while (textScanner.hasNext()) {
                        text += " ";
                        text += textScanner.next();
                    }
                    hashtable.add(text);
                }
// Delete
                else if (command.equals("delete")) {
                    if (!textScanner.hasNext()) {
                        System.out.println("Invalid arguments for delete");
                        continue;
                    }
                    text += textScanner.next();
                    while (textScanner.hasNext()) {
                        text += " ";
                        text += textScanner.next();
                    }
                    hashtable.delete(text);
                }
// Update
                else if (command.equals("update")) {
                    if (!textScanner.hasNext()) {
                        System.out.println("Invalid arguments for update");
                        continue;
                    }
                    command = textScanner.next();
                    textScanner.useDelimiter("<SEP>");
                    if (command.equals("add") || command.equals("delete")) {
                        String temp;
                        temp = textScanner.next();
                        text = temp.replaceAll("\\s+", " ");
                        text = text.trim();
                        temp = textScanner.next();
                        String field = temp.replaceAll("\\s+", " ");
                        field = field.trim();
                        String val = "";
                        if (command.equals("add")) {
                            temp = textScanner.next();
                            val = temp.replaceAll("\\s+", " ");
                            val = val.trim();
                        }

                        hashtable.update(command, text, field, val);
                    }
                    else {
                        System.out.println("Invalid arguments for update");
                        continue;
                    }
                }
// Print
                else if (command.equals("print")) {

                    if (!textScanner.hasNext()) {
                        System.out.println("Invalid arguments for print");
                        continue;
                    }
                    text += textScanner.next();
                    if (text.equals("hashtable")) {
                        hashtable.print("hashtable");
                    }
                    else if (text.equals("blocks")) {
                        hashtable.print("blocks");
                    }
                }
                textScanner.close();
            }
            fileScanner.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
