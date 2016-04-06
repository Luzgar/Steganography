package message.compressionStrat.MoreHuffman;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 24/01/2016.
 */
public class MoreHuffmanTest {

    @Test
    public void testCompress() throws Exception {
        MoreHuffman moreHuffman = new MoreHuffman();
        String msgToCompress = "aabdecdaebade";
        byte[] byteOfMessage = msgToCompress.getBytes();
        String compressedMessage = moreHuffman.getCompressed(byteOfMessage);
        String expectedResult = "000001000110010001100101011000110110001001100001100110000000010111111010001100001101101110001000";

        assertEquals(expectedResult, compressedMessage);
    }
}