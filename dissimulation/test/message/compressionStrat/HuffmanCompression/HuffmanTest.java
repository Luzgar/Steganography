package message.compressionStrat.HuffmanCompression;

import message.compressionStrat.Compression;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kevin on 24/01/2016.
 */
public class HuffmanTest {

    @Test
    public void testCompress() throws Exception {
        String msgToCompress = "aabdecdaebade";
        Huffman huffman = new Huffman();
        byte[] byteOfMessage = msgToCompress.getBytes();

        String compressedMessage = huffman.getCompressed(byteOfMessage);
        String expectedResult = "000001000110010000000010000000000110010100000010010000000110001100000011100000000110001000000011101000000110000100000010110000000000010111111010001100001101101110001000";
        assertEquals(expectedResult, compressedMessage);
    }
}