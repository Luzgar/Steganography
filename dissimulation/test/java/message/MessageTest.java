package message;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aelar on 09/12/15.
 */
public class MessageTest {

    Message theMessageWeAreTesting;
    String aMessage;
    String aBinaryMessage;

    @Before
    public void context(){
        theMessageWeAreTesting = new Message("MessageTest".getBytes(), "MagicWord", 4, null);
        aMessage = "MessageTestMagicWord";
        aBinaryMessage = "010011010110010101110011011100110110000101100111011001010101010001100101011100110111010001001" + "1010110000101100111011010010110001101010111011011110111001001100100";
    }

    @Test
    public void testGetFullMessage() throws Exception {
        assertEquals(theMessageWeAreTesting.getFullMessage(),aMessage);
        assertNotEquals(theMessageWeAreTesting.getFullMessage(), "test");
    }

    @Test
    public void testGetBinString() throws Exception {

        assertEquals(theMessageWeAreTesting.getBinString(),aBinaryMessage);
        assertNotEquals(theMessageWeAreTesting.getBinString(),aBinaryMessage+"01");
    }

    @Test
    public void testGetBinLength(){
        assertEquals(160, theMessageWeAreTesting.getBinLength());
    }
}