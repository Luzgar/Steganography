
/**
 * \file          DicoOptimized.java
 * \author    jihane
 * \version   1.0
 * \date       22 January 2016
 * \brief       Huffman tree symbol optimization -L7
 */

 /*
package message.compressionStrat.EvenMoreHuffman;
import message.compressionStrat.Huffman.Dico;
import message.compressionStrat.Huffman.PairTree;

import javax.xml.stream.events.Characters;
import java.util.ArrayList;


public class DicoOptimized  {

    private char table[];//THE REFERENCE, a table of all the ascii characters

    private char[] setTable(){
        int i;
        for ( i = 0; i < 255; i++){
            table[i] = (char) i;
        }
        return table;
    }

    public char[] getTable() {
        return table;
    }

    private String dictionnary ;

    public DicoOptimized(PairTree tree){
        dictionnary = getDictionnary();
    }

    // Convert the string uncompressed dictionnary to char array

    private String uncompressed_dico; //TO DO

    private ArrayList<Character> convertToArrayList() {
        for (char c : uncompressed_dico.toCharArray()) {
            DicoArray.add(c);
        }
        return DicoArray;
    }

    ArrayList<Character> DicoArray = convertToArrayList();

    private int[] DicoOptimized;
    //An array containing only the difference in position of each of the symbols
    // of the dictionnary compared to the reference table

    private int[] setDicoOptimized (){
        for (int i=0; i<DicoArray.size();i++) {
            if (DicoArray.get(i) == table[i])
                DicoOptimized[i] = 0;
            else if (DicoArray.get(i) != table[i])
                DicoOptimized[i] = (DicoArray.indexOf(table[i]) -
                        DicoArray.indexOf(DicoArray.get(i)));
        }
        return DicoOptimized;
    }

    public String getDictionnary() {
        String s = DicoOptimized.toString();
        return s;
    }
}

 */
