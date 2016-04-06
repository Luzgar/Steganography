package message.compressionStrat.MoreHuffman;

import message.Binarable;
import message.compressionStrat.Compression;
import message.compressionStrat.Huffman.Dico;
import message.compressionStrat.Huffman.Sort;
import message.compressionStrat.HuffmanCompression.Dictionary;

/**
 * Created by aelar on 20/01/16.
 */
public class MoreHuffman extends Binarable implements Compression {
    private String compressedString;
    private Treectionnary dictionnary;


    public void compress(byte[] msg){

        this.compressedString = "";


        Sort sort = new Sort(msg);

        dictionnary = new Treectionnary(sort.getMap().peek());

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
        //System.out.println("More");
        //System.out.println(compressedString);
        return compressedString;
    }
    @Override
    public Dico getDictionnary() {
        return dictionnary;
    }
}
