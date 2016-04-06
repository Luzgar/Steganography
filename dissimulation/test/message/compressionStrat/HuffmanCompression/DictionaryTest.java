package message.compressionStrat.HuffmanCompression;

import message.compressionStrat.Huffman.Sort;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 24/01/2016.
 */
public class DictionaryTest {

    @Test
    public void dictionnaryTest(){

        String message = "aabdecdaebade";
        byte[] msg = message.getBytes();
        Sort sort = new Sort(msg);
        Dictionary dictionnary = new Dictionary(sort.getMap().peek());

        String dictionnaryString = dictionnary.getDico();
        String expectedResult = "00000100011001000000001000000000011001010000001001000000011000110000001110000000011000100000001110100000011000010000001011000000";

        assertEquals(expectedResult, dictionnaryString);
    }

}