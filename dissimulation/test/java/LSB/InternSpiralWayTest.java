package LSB;

import action.Ways;
import exceptions.ChannelException;
import exceptions.ImageTooShort;
import imgAnalyse.Channels;
import imgAnalyse.ImageMatrix;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by aelar on 14/12/15.
 */
public class InternSpiralWayTest {

    ImageMatrix image;
    String msg;
    String magic;
    Channels[] chan;

    @Before
    public void context() throws IOException {
        image = new ImageMatrix("test/test.png");
        msg = "test";
        magic = "HELP";
        chan = new Channels[]{Channels.RED,Channels.GREEN,Channels.BLUE};
    }

    @Ignore
    public void testBrowse() throws Exception {
        image.LSB(msg.getBytes(),magic,8,chan, Ways.INTSPI,null);
        assertTrue(image.getImage().equals(new ImageMatrix("test/intern.png").getImage()));
        assertFalse(image.getImage().equals(new ImageMatrix("test/test.png").getImage()));

    }
    @Test(expected = ImageTooShort.class)
    public void testSizeException() throws ImageTooShort, ChannelException {
        image.LSB(new File("secret.txt").toString().getBytes(),magic,1,chan,Ways.INTSPI,null);
    }

    @Ignore//(expected = ChannelException.class)
    public void testchannel() throws ImageTooShort, ChannelException, IOException {
        ImageMatrix imageMatrix = new ImageMatrix("test/exception.jpg");
        imageMatrix.LSB(msg.getBytes(),magic,8,new Channels[]{Channels.ALPHA},Ways.INTSPI,null);
    }

}