package message.compressionStrat.Huffman;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 24/01/2016.
 */
public class SortTest {

    @Test
    public void testSort(){
        String msg = "aabdecdaebade";
        byte[] byteOfMessage = msg.getBytes();
        Sort sort = new Sort(byteOfMessage);
        String compressedMessage ="";
        for(int i = 0; i < msg.length();++i){
            compressedMessage += sort.getMap().peek().getSymbolCode(byteOfMessage[i]);
        }
        String expectedResult = "11111010001100001101101110001";
        assertEquals(expectedResult, compressedMessage);
    }

}