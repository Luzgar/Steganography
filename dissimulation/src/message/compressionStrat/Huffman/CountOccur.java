package message.compressionStrat.Huffman;

/**
 * Created by aelar on 18/01/16.
 */
public class CountOccur {
    private int[] occur;


    public CountOccur(byte[] str){
        occur = new int[256];

        for(int i = 0; i < str.length; ++i){

            occur[str[i] & 0xFF]++;
        }
    }

    public int[] getOccur() {
        return occur;
    }


}
