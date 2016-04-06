package message.compressionStrat.Huffman;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 24/01/2016.
 */
public class CountOccurTest {

    @Test
    public void testGetOccur() throws Exception {
        String msgToCompress = "aabdecdaebade";
        byte[] byteOfMessage = msgToCompress.getBytes();
        CountOccur countOccur = new CountOccur(byteOfMessage);
        assertEquals(4, countOccur.getOccur()[97]);
        assertEquals(2, countOccur.getOccur()[98]);
        assertEquals(1, countOccur.getOccur()[99]);
        assertEquals(3, countOccur.getOccur()[100]);
        assertEquals(3, countOccur.getOccur()[101]);
    }
}