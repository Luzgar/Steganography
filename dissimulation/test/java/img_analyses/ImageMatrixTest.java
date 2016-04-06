package imgAnalyse;

import LSB.RevertWay;
import LSB.WayStrategy;
import action.Ways;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by aelar on 14/11/15.
 */
public class ImageMatrixTest {
    ImageMatrix image;
    ImageMatrix image2;

    @Before
    public void context() throws IOException {
        image = new ImageMatrix("test/test.png");
        image2 = new ImageMatrix("test/test.png");
    }

    @Test(expected = IOException.class)
    public void testImageMatrix() throws IOException {
        ImageMatrix imageMatrix = new ImageMatrix(
                "test/rd.png");

        assertTrue(imageMatrix.getImage().equals(image2.getImage()));
    }


    

    @Test()
    public void testSaveImage() throws Exception {
        ImageMatrix imageMatrix = new ImageMatrix(
                "test/test.png");
        imageMatrix.saveImage("test","png");
        File file = new File("test/test.png");


        assertTrue(file.exists());
        assertTrue(file.canRead());

    }




    
}