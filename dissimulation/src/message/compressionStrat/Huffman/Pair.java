package message.compressionStrat.Huffman;

/**
 * Created by aelar on 18/01/16.
 */
public class Pair implements Comparable {
    private byte[] key;
    private int val;

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + new String(key) +
                ", val=" + val +
                '}';
    }

    public Pair(byte key, int i){
        this.key = new byte[1];
        this.key[0] = key;

        val = i;
    }

    public Pair(byte[] key, int i){

        this.key = key;
        val = i;
    }

    public void inc(){++val;}

    public byte[] getKey() {
        return key;
    }

    public int getVal() {
        return val;
    }

    public int compareTo(Object o) {

        if(val < ((Pair) o).getVal()) return -1;
        else if(val > ((Pair) o).getVal())return 1;
        else if(val == ((Pair) o).getVal()) {
            if(key.length >((Pair) o).getKey().length) return 1;
            else if(key.length <((Pair) o).getKey().length) return -1;
            return 0;
        }
        return 0;
    }


}
