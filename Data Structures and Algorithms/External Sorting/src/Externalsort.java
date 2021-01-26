import java.io.IOException;

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * This project implements an external sorting algorithm
 * for binary data. The input data file
 * consist of at least 8N blocks of data,
 * where a block is 8,192 bytes.
 * Each block contains a series of records,
 * where each record is 16 bytes long and contains two
 * fields. The first 8-byte field is a non-negative
 * integer value of type long for the record
 * ID and the second 8-byte field is the record key of type double,
 * which used for sorting.
 *
 * @author Hulya Dogan and Chris Luersen
 * @version 11/10/2020
 * 
 */

public class Externalsort {
    /**
     * This object stores all data and structures necessary
     * for both replacement selection and merge sort
     * 
     */
    private static CommandProcessor parser;

    /**
     * Main method for the external sort class. It has main method.
     * 
     * @param args
     *            input string array
     * @throws IOException
     * 
     */
    public static void main(String[] args) throws IOException {

        parser = new CommandProcessor(args[0]);

        ReplacementSelectionSort rs = new ReplacementSelectionSort(parser);
        rs.work();

        MergeSort ms = new MergeSort(parser);
        ms.merge();

        parser.inFile().close();
        parser.runsFile().close();

    }


    /**
     * @return the sort container to provide access
     *         to the heap array, input and output files, etc
     *         for testing purposes
     */
    public static CommandProcessor getSortContainer() {
        return parser;
    }

}
