package message.compressionStrat.HuffmanCompression;

import message.compressionStrat.Huffman.Dico;
import message.compressionStrat.Huffman.PairTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aelar on 19/01/16.
 */
public class Dictionary extends Dico{
    private String dictionnary;

    private int size;
    private List<Character> letterList = new ArrayList<>();
    private List<String> codeList = new ArrayList<>();

    public Dictionary(PairTree tree){
        dictionnary = create(tree);
    }



    public int getSize() {
        return size;
    }

    public List<Character> getLetterList() {
        return letterList;
    }

    public List<String> getCodeList() {
        return codeList;
    }

    private String create(PairTree tree){
        String dico;
        String str = new String(tree.getValue().getKey());

        byte[] b = tree.getValue().getKey();
        size = tree.getValue().getKey().length;
        dico = toBinary((byte)( size - 1));
        //System.out.println(Arrays.toString(b));
        String symbol;
        char c;
        for(int i = 0; i < b.length; ++i ){
            dico += toBinary(b[i]);


            letterList.add((char) b[i]);
            symbol = tree.getSymbolCode(b[i]);


            codeList.add(symbol);
            //System.out.println(b[i]);
            dico += code(symbol);


        }
        //System.out.println(dico);
        return dico;
    }

    @Override
    public String toString() {
        String result ="";
        for(int i = 0; i < letterList.size(); ++i){
            result += "\t0x"+Integer.toHexString((int) letterList.get(i))+": "+codeList.get(i) +"\n";

        }
        return result;
    }



    public String getDico() {
        return dictionnary;
    }


   

}
