package imgAnalyse;

import LSB.*;
import action.Ways;
import exceptions.ChannelException;
import exceptions.ImageTooShort;
import message.Message;
import message.compressionStrat.Compression;
import message.compressionStrat.Huffman.Dico;
import message.compressionStrat.HuffmanCompression.Dictionary;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aelar on 10/11/15.
 */

/**
 * \file          ImageMatrix.java
 * \author    Benjamin PIAT
 * \version   1.0
 * \date       10 November 2015
 * \brief       Represent the image as a matrix with the different action that you can perform on it
 *
 */
public class ImageMatrix {

    private List<List<Color>> image;
    private int height;
    private int width;
    private boolean asAlpha;
    private boolean greyscale;
    private WayStrategy way;
    private int nbPixelsImpacted;
    private ArrayList<Integer> nbChannelsImpacted = new ArrayList<>();
    private Dico dictionnary = null;
    private Message message;
    /**
     * @param imagePath
     */

    /**
     * \brief       Constructor of the matrix
     * \details    Build the matrix, and take some information about the image
     * \param    imagePath         The path of the given image
     */
    public ImageMatrix(String imagePath) throws IOException {

        //Try to open the image at the given path
        File imageFile = new File(imagePath);
        String fileType = Files.probeContentType(imageFile.toPath());

        BufferedImage bImage = ImageIO.read(imageFile);


        //check if the image as a transparency channel
        asAlpha = true;
        height = bImage.getHeight();
        width = bImage.getWidth();
        //keep the height and the width of the image

        image = new ArrayList(height);


        if (bImage.getRaster().getNumDataElements() < 4) asAlpha = false;
        if (bImage.getRaster().getNumDataElements() == 1 || bImage.getRaster().getNumDataElements() == 2)
            greyscale = true;
        //fill the our matrix with the Color from the opened image
        //int[] bitList = bImage.getRGB(0,0,width,height,null,0,width);

        /*for(int i = 0; i < bitList.length;++i){
            image.add(new Color(bitList[i]));
        }*/

        for (int i = 0; i < height; ++i) {
            image.add(new ArrayList<>(width));
            for (int j = 0; j < width; ++j) {
                image.get(i).add(new Color(bImage.getRGB(j, i)));
                // System.out.println();
            }
        }


    }

    /**
     * \brief       Check if the image is large enough to hide the message
     * \param    msgSize         Size of the message
     * \param    nbChannel         Number of channel used to hide the information
     * \param    nbBits         Number of bit used per pixel
     * \param    nbPixel         Number of pixel in the image
     */
    public boolean checkSize(int msgSize, int nbChannel, int nbBits, int nbPixel) {


        return (((msgSize)) < (nbPixel * nbChannel * nbBits)) ? true : false;

    }

    /**
     * \brief       Get the number of impacted pixels
     * \return      An \e int representing the number of impacted pixels
     */
    public int getNbPixelsImpacted() {
        return nbPixelsImpacted;
    }

    /**
     * \brief       Get the number of channels impacted
     * \return      A \e ArrayList<Integer> representing the number of channels impacted
     */
    public ArrayList<Integer> getNbChannelsImpacted() {
        return nbChannelsImpacted;
    }

    /**
     * \brief       Get the image is in greyscale
     * \return      A \e boolean representing if the image is in greyscale
     */
    public boolean isGreyscale() {
        return greyscale;
    }

    /**
     * \brief       Get the pattern algorithm used to hide the image
     * \return      A \e WayStrategy representing the pattern used to hide the image
     */
    public WayStrategy getWay() {

        return way;
    }
    /**
     * \brief       Set the pattern algorithm used to hide the image
     * \return      ways      Represent the pattern to set
     */
    public void setWay(WayStrategy way) {
        this.way = way;
    }

    /**
     * @param i
     * @return Color at the index asked
     */
    /**
     * \brief       Get a pixel
     * \return      A \e Color representing a pixel
     */
    public Color getColor(int i, int j) {
        return image.get(i).get(j);
    }

    /**
     * @return
     */
    /**
     * \brief       Get the height of the image
     * \return      An \e int representing the height of the image
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return
     */
    /**
     * \brief       Get the width of the image
     * \return      An \e int representing the width of the image
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return
     */
    /**
     * \brief       Get if the image as an alpha channel
     * \return      A \e boolean representing the presence of an alpha channel
     */
    public boolean getAsAlpha() {
        return asAlpha;
    }

    /**
     * @return Matrix of color, representing the image
     */
    /**
     * \brief       Get the matrix of the image
     * \return      A \e List<List<Color>> representing the matrix of the image
     */
    public List<List<Color>> getImage() {
        return image;
    }

    /**
     * \brief       Get the number of pixel of the image
     * \return      A \e int representing the number of pixel of the image
     */
    public int getSize() {
        return height * width;
    }

    public Message getMessage() {
        return message;
    }

    public Dico getDictionnary() {
        return dictionnary;
    }

    /**
     * \brief       Apply the lsb to the matrix
     * \param       msg     The message to hide
     * \param       magicWord       The magic word to use
     * \param       bitPerPixel     Number of bit used in each pixel
     * \param       chan        \e Channels used to hide
     * \param       way     The way through the image
     * \return      A \e boolean representing the success of the operation
     */
    public boolean LSB(byte[] msg, String magicWord, int bitPerPixel, Channels[] chan, Ways w, Compression compression) throws ChannelException, ImageTooShort {

        message = new Message(msg, magicWord, bitPerPixel, compression);
        dictionnary = message.getDictionnary();
        int messageLength = message.getBinString().length();
        if (!checkSize(messageLength, chan.length, bitPerPixel, getSize())) throw new ImageTooShort();

        nbPixelsImpacted = messageLength / bitPerPixel;
        int nbPixelsImpactedtmp = nbPixelsImpacted;
        for (int i = 0; i < chan.length; ++i) {
            if (nbPixelsImpactedtmp <= 0)
                nbChannelsImpacted.add(0);
            if (nbPixelsImpactedtmp > getSize()) {
                nbChannelsImpacted.add(getSize());
                nbPixelsImpactedtmp -= getSize();
            } else {
                nbChannelsImpacted.add(nbPixelsImpactedtmp);
                nbPixelsImpactedtmp -= nbChannelsImpacted.get(i);
            }

        }

        if (w == Ways.DIRECT) {
            way = new DirectWay();
        } else if (w == Ways.REVERSE) {
            way = new RevertWay();
        } else if (w == Ways.EXTSPI) {
            way = new ExternSpiralWay();
        } else if (w == Ways.INTSPI) {
            way = new InternSpiralWay();
        }

        way.LSB(this, bitPerPixel, chan, message.getBinString());

        return true;

    }
    /**
     * \brief       Save the image
     * \param       name     Name of the saved file
     * \param       type       Format of the saved file
     */
    public void saveImage(String name, String type) {

        BufferedImage bImg = new BufferedImage(width, height,
                greyscale ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_INT_ARGB);

        bImg.setRGB(0, 0, width, height, toArray(), 0, width);

        try {
            ImageIO.write(bImg, type, new File("./" + name + "." + type));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * \brief       Give the matrix as an \e int []
     * \return      A \e int[] of the matrix
     */
    private int[] toArray() {
        ArrayList<Integer> img = new ArrayList<>();

        image.forEach(x -> x.forEach(y -> img.add(y.getRGB())));
        return img.stream().mapToInt(i -> i).toArray();

    }


}
