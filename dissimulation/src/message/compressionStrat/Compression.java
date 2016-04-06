package message.compressionStrat;

import message.compressionStrat.Huffman.Dico;
import message.compressionStrat.HuffmanCompression.Dictionary;

/**
 * Created by aelar on 19/01/16.
 */
public interface Compression {



    String getCompressed(byte[] msg);
    Dico getDictionnary();

}
