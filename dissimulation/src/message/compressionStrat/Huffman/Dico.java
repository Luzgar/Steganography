package message.compressionStrat.Huffman;

import message.Binarable;

/**
 * Created by aelar on 20/01/16.
 */
public abstract class Dico extends Binarable{

    protected String code(String code){
        String str;

        str = toBinary((byte) code.length());

        str += code;

        while((str.length() % 8) != 0 ) str += "0";

        return str;
    }

    protected String normalize(String bin){
        String str = bin;
        if(str.length() > 3)
            str = str.substring(1,str.length() - 2);
        else str = "0";
        //System.out.println("shape " + bin + " " + str);
        while((str.length() % 8) != 0)
            str += "0";
        return str;
    }


     public abstract String getDico();
}
