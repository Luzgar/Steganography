package message.compressionStrat.MoreHuffman;

import message.compressionStrat.Huffman.Sort;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 24/01/2016.
 */
public class TreectionnaryTest {

    @Test
    public void treectionnaryTest(){
        String message = "aabdecdaebade";
        byte[] msg = message.getBytes();
        Sort sort = new Sort(msg);
        Treectionnary treectionnary = new Treectionnary(sort.getMap().peek());
        String dicoString = treectionnary.getDico();
        String expectedResult = "00000100011001000110010101100011011000100110000110011000";

        assertEquals(expectedResult, dicoString);

    }

}