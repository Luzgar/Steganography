package message.compressionStrat.EvenMoreHuffman;

import message.compressionStrat.Huffman.CountOccur;
import message.compressionStrat.Huffman.Pair;
import message.compressionStrat.Huffman.PairTree;
import message.compressionStrat.Huffman.Sort;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by aelar on 22/01/16.
 */
public class EvenMoreSort  {

    private PriorityQueue<PairTree> map;

    public EvenMoreSort(byte[] str){
        map = new PriorityQueue<PairTree>(new compPair());

        CountOccur count = new CountOccur(str);

        sort(count.getOccur());


    }

    public PriorityQueue<PairTree> getMap() {

        return map;
    }

    private void sort(int[] occur){
        Pair p;
        for(int i = 0; i < occur.length; ++i){

                p = new Pair((byte) i, occur[i]);

                map.offer(new PairTree(p));

        }

        iterate();

    }

    private class compPair implements Comparator<PairTree> {


        public int compare(PairTree pair, PairTree t1) {
            return pair.getValue().compareTo(t1.getValue());
        }
    }

    private void iterate(){
        PairTree pair;
        PairTree pair2;
        byte[] ch;
        PairTree p;
        while(map.size() > 1){

            pair = map.poll();
            pair2 = map.poll();

            ch = new byte[pair.getValue().getKey().length + pair2.getValue().getKey().length ];

            for (int i = 0; i < pair.getValue().getKey().length ; i++) {
                ch[i] = pair.getValue().getKey()[i];
            }

            for (int i =  0 ; i < pair2.getValue().getKey().length; i++) {

                ch[pair.getValue().getKey().length + i ] =  pair2.getValue().getKey()[i];
            }


            p = new PairTree(new Pair(ch,pair.getValue().getVal() + pair2.getValue().getVal()));

            p.setLeftNode(new PairTree(pair));
            p.setRightNode(new PairTree(pair2));
            map.offer(p);

        }
    }




}
