package message.compressionStrat.EvenMoreHuffman;

import message.compressionStrat.Compression;
import message.compressionStrat.Huffman.Dico;
import message.compressionStrat.Huffman.Sort;
import message.compressionStrat.HuffmanCompression.Dictionary;

/**
 * Created by aelar on 22/01/16.
 */
public class EvenMoreHuffman extends Dico implements Compression {
    private String compressedString;
    private Dictionary dico;

    @Override
    public String getCompressed(byte[] msg) {
        this.compressedString = "";


        EvenMoreSort sort = new EvenMoreSort(msg);
        Bijection bij = new Bijection(sort.getMap().peek().getValue().getKey());


        String message = "";
        this.compressedString = normalize(sort.getMap().peek().getShape());

        byte[] b = bij.getCode().toByteArray();

        for (int i = 0; i < b.length ; i++) {
            this.compressedString += toBinary(b[i]);
        }

        for(int i = 0; i < msg.length;++i){

            message += sort.getMap().peek().getSymbolCode(msg[i]);
        }

        this.compressedString += toBinary((byte) (message.length() % 8));
        this. compressedString += message;
        while((compressedString.length() % 8) != 0) this.compressedString += "0";

        return compressedString;

    }

    @Override
    public Dico getDictionnary() {
        return null;
    }

    @Override
    public String getDico() {
        return null;
    }

}
