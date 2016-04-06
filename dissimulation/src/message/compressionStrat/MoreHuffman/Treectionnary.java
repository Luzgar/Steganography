package message.compressionStrat.MoreHuffman;

import message.Binarable;
import message.compressionStrat.Huffman.Dico;
import message.compressionStrat.Huffman.PairTree;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by aelar on 20/01/16.
 */
public class Treectionnary extends Dico{

    private String dictionnary;
    private List<Character> symbolList = new ArrayList<>();
    private List<String> codeList = new ArrayList<>();

    private int size;

    public Treectionnary(PairTree tree){
        dictionnary = create(tree);
    }

    public int getSize() {
        return size;
    }

    private String create(PairTree tree){
        String dico;
        String str = new String(tree.getValue().getKey());
        byte[] b = str.getBytes();
        size = tree.getValue().getKey().length;


        dico = toBinary((byte)( size - 1));


        for(int i = 0; i < str.length(); ++i ){
            dico += toBinary(b[i]);
            symbolList.add((char) b[i]);
            codeList.add(tree.getSymbolCode(b[i]));
        }

        dico += normalize(tree.getShape());
        return dico;
    }

    @Override
    public String getDico() {
        return dictionnary;
    }

    @Override
    public String toString() {
        String result ="";
        for(int i = 0; i < symbolList.size(); ++i){
            result += "\t0x"+Integer.toHexString((int) symbolList.get(i))+": "+codeList.get(i) +"\n";

        }
        return result;
    }
}
