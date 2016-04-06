package message.compressionStrat.EvenMoreHuffman;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Stack;

/**
 * Created by aelar on 22/01/16.
 */
public class Bijection {

    private BigInteger code;

    public BigInteger getCode() {
        return code;
    }

    private byte[] alphabet;

    public Bijection(byte[] customAlph){
        this.alphabet = customAlph;
        byte[] tmp = customAlph;
        code = BigInteger.ZERO;
        Stack<Integer> ind = new Stack<>();
        int j;
        for (int i = 0; i < tmp.length; i++) {
            //System.out.println(Arrays.toString(tmp));
            j = findChar((byte) i, tmp);
            ind.push(j - i);
            //System.out.println(j - i);
            swap(tmp,i ,j);
        }

        generateCode(ind);
        System.out.println(code.bitLength());
    }

    private int findChar(byte ind , byte[] str){
        byte c = ind;
        for (int i = ind & 0xFF; i  < str.length;++i){
            //System.out.println( str[i]+ " " + c );

            if(str[i] == c)return i;
        }
        return -1;
    }

    private byte[] swap (byte[] str, int i, int j){
        //System.out.println(i);
        byte tmp = str[i];
        str[i] = str[j];
        str[j] = tmp;
        return str;
    }

    private void generateCode(Stack<Integer> a){
        BigInteger f = BigInteger.ONE;
        //a.pop();
        for (int i = 1; i < 256;) {
            f = f.multiply(BigInteger.valueOf(++i));
            int ai = a.pop();
            code = code.add(BigInteger.valueOf(ai).multiply(f));
            //System.out.println(a.size());
        }
    }
}
