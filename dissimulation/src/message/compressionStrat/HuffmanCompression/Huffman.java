package message.compressionStrat.HuffmanCompression;

import message.Binarable;
import message.compressionStrat.Compression;
import message.compressionStrat.Huffman.Sort;

import java.util.Arrays;


/**
 * Created by aelar on 19/01/16.
 */
public class Huffman extends Binarable implements Compression  {
    private String compressedString;
    private Dictionary dictionnary;


    public void compress(byte[] msg){

        this.compressedString = "";


        Sort sort = new Sort(msg);

        dictionnary = new Dictionary(sort.getMap().peek());

        String message = "";
        this. compressedString += dictionnary.getDico();


        for(int i = 0; i < msg.length;++i){

            message += sort.getMap().peek().getSymbolCode(msg[i]);
        }

        this.compressedString += toBinary((byte) (message.length() % 8));
        this. compressedString += message;
        while((compressedString.length() % 8) != 0) this.compressedString += "0";
    }
    @Override
    public String getCompressed(byte[] msg) {
        compress(msg);

        return compressedString;
    }

    @Override
    public Dictionary getDictionnary() {
        return dictionnary;
    }


}



