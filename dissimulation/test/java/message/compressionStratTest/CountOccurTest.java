package java.message.compressionStratTest;

import message.compressionStrat.Huffman.CountOccur;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Loïc on 21/01/2016.
 */
public class CountOccurTest {

    CountOccur theCountOccurWeAreTesting;

    @Before
    public void context(){
        byte[] theByteArray = {1,2,3,4,5,6,7,8,9};
        theCountOccurWeAreTesting = new CountOccur(theByteArray);
    }

    @Test
    public void testGetOccur(){
        //assertEquals();
    }
}
