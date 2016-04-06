package message.compressionStrat.Huffman;

/**
 * Created by aelar on 18/01/16.
 */
public class PairTree {
    private Pair value;
    private PairTree leftNode;
    private PairTree rightNode;

    public PairTree(Pair val){
        value = val;
    }

    @Override
    public String toString() {
        String str =  "PairTree{" +
                "value=" + value.toString();

        if(leftNode != null) str += ", leftNode=" + leftNode.toString();

        if(leftNode != null) str += ", rightNode=" + rightNode.toString();
        str +=  '}';
        return str;
    }

    public PairTree(PairTree tree){
        this.value = tree.getValue();
        this.leftNode = tree.getLeftNode();
        this.rightNode = tree.getRightNode();
    }
    public Pair getValue() {
        return value;
    }


    public PairTree getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(PairTree leftNode) {
        this.leftNode = leftNode;
    }

    public PairTree getRightNode() {
        return rightNode;
    }

    public void setRightNode(PairTree rightNode) {
        this.rightNode = rightNode;
    }


    public String getSymbolCode(byte c){

        if(leftNode  == null && rightNode == null && value.getKey().length == 1){
            return "0";
        }

        if(leftNode.value.getKey().length == 1 && leftNode.value.getKey()[0] == c){
            return "0";
        }

        if(rightNode.value.getKey().length == 1 && rightNode.value.getKey()[0] == c){
            return "1";
        }

        if(foundSymbol(leftNode.value.getKey(),c)){
            return "0" + leftNode.getSymbolCode(c);
        }

        if(foundSymbol(rightNode.value.getKey(),c)){
            return "1" + rightNode.getSymbolCode(c);
        }

        return null;
    }

    public String getShape(){
        String str = "";
        if(leftNode != null) str += "1" + leftNode.getShape();
        if(leftNode == null && rightNode == null) str += "0";
        if(rightNode != null) str += rightNode.getShape();

        return str;
    }

    private boolean foundSymbol(byte[] list, byte symbol){

        for (int i = 0; i < list.length; i++) {
            if(list[i] == symbol) return true;
        }
        return false;
    }
}
