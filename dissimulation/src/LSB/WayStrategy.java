package LSB;

import exceptions.ChannelException;
import imgAnalyse.Channels;
import imgAnalyse.ImageMatrix;

import java.awt.*;

/**
 * Created by aelar on 02/12/15.
 */
public abstract class WayStrategy {


    /**
     * \brief       Apply the LSB
     * \param    imageMatrix         The matrix to use
     * \param    nbBytes         number of bits in each pixel
     * \param    channels      The channels used to hide
     * \param    message        The message to hide
     * \return    A \e boolean representing the remaining part of the message to hide
     */
    public boolean LSB(ImageMatrix imageMatrix, int nbBytes, Channels[] channels, String message) throws ChannelException {
        int count = 0;
        int mask = (255 << nbBytes) & 255;


        for (Channels s : channels) {
            switch (s) {
                case RED:
                    count = Browse(imageMatrix, nbBytes, count, message, mask, new RHider());
                    break;
                case GREEN:
                    count = Browse(imageMatrix, nbBytes, count, message, mask, new GHider());
                    break;
                case BLUE:
                    count = Browse(imageMatrix, nbBytes, count, message, mask, new BHider());
                    break;
                /*case GREY:
                    count = LsbGrey(imageMatrix,nbBytes,count,message,mask);
                    break;
                case ALPHA:
                    if (!imageMatrix.getAsAlpha()) throw new ChannelException(); //TODO throw exception

                    break;*/
            }
            if (count == message.length()) break;

        }
        return true;
    }

    /**
     * \brief       Run the pattern algorithm and apply the LSB
     * \param    imageMatrix         The matrix to use
     * \param    nbBytes         number of bits in each pixel
     * \param    begin      Where to begin to hide in the message
     * \param    msg        The message to hide
     * \param    mask       Represent the mask to applicate before each hide
     * \param    hider      represent in which channel we are going to hide
     * \return    An \e int representing the remaining part of the message to hide
     */
    protected abstract int Browse(ImageMatrix imageMatrix, int nbBytes, int begin, String msg, int mask, Hider hider);

    protected interface Hider {

        /**
         * \brief       Hide information in a pixel and a channel
         * \param    imageMatrix         The matrix to use
         * \param    i         x position of the pixel
         * \param    j      y position of the pixel
         * \param    mask      Represent the mask to applicate before each hide
         * \param    msg       The message to hide
         * \param    hider      Represent in which channel we are going to hide
         */
        void hide(ImageMatrix imageMatrix, int i, int j, int mask, String msg);

    }

    protected class RHider implements Hider {

        /**
         * \brief       Hide information in a pixel and red channel
         * \param    imageMatrix         The matrix to use
         * \param    i         x position of the pixel
         * \param    j      y position of the pixel
         * \param    mask      Represent the mask to applicate before each hide
         * \param    msg       The message to hide
         * \param    hider      Represent in which channel we are going to hide
         */
        @Override
        public void hide(ImageMatrix imageMatrix, int i, int j, int mask, String msg) {

            int r, g, b, a;
            r = imageMatrix.getColor(i, j).getRed();
            g = imageMatrix.getColor(i, j).getGreen();
            b = imageMatrix.getColor(i, j).getBlue();
            if (imageMatrix.getAsAlpha()) a = imageMatrix.getColor(i, j).getAlpha();
            else a = 0;

            //System.out.println(i + " "+j);
            r &= mask;
            r |= Integer.parseInt(msg, 2);
            //System.out.println(fullMessage.substring(count,count+nbBytes));


            //System.out.println(begin + " " + r);
            if (imageMatrix.getAsAlpha()) imageMatrix.getImage().get(i).set(j, new Color(r, g, b, a));
            else imageMatrix.getImage().get(i).set(j, new Color(r, g, b));
        }
    }

    protected class GHider implements Hider {

        /**
         * \brief       Hide information in a pixel and green channel
         * \param    imageMatrix         The matrix to use
         * \param    i         x position of the pixel
         * \param    j      y position of the pixel
         * \param    mask      Represent the mask to applicate before each hide
         * \param    msg       The message to hide
         * \param    hider      Represent in which channel we are going to hide
         */
        @Override
        public void hide(ImageMatrix imageMatrix, int i, int j, int mask, String msg) {
            int r, g, b, a;

            r = imageMatrix.getColor(i, j).getRed();
            g = imageMatrix.getColor(i, j).getGreen();
            b = imageMatrix.getColor(i, j).getBlue();
            if (imageMatrix.getAsAlpha()) a = imageMatrix.getColor(i, j).getAlpha();
            else a = 0;


            g &= mask;
            g |= Integer.parseInt(msg, 2);
            //System.out.println(fullMessage.substring(count,count+nbBytes));

            //System.out.println(begin + " " + r);
            if (imageMatrix.getAsAlpha()) imageMatrix.getImage().get(i).set(j, new Color(r, g, b, a));
            else imageMatrix.getImage().get(i).set(j, new Color(r, g, b));
        }
    }

    protected class BHider implements Hider {

        /**
         * \brief       Hide information in a pixel and blue channel
         * \param    imageMatrix         The matrix to use
         * \param    i         x position of the pixel
         * \param    j      y position of the pixel
         * \param    mask      Represent the mask to applicate before each hide
         * \param    msg       The message to hide
         * \param    hider      Represent in which channel we are going to hide
         */
        @Override
        public void hide(ImageMatrix imageMatrix, int i, int j, int mask, String msg) {
            int r, g, b, a;

            r = imageMatrix.getColor(i, j).getRed();
            g = imageMatrix.getColor(i, j).getGreen();
            b = imageMatrix.getColor(i, j).getBlue();
            if (imageMatrix.getAsAlpha()) a = imageMatrix.getColor(i, j).getAlpha();
            else a = 0;


            b &= mask;
            b |= Integer.parseInt(msg, 2);
            //System.out.println(fullMessage.substring(count,count+nbBytes));

            //System.out.println(begin + " " + r);
            if (imageMatrix.getAsAlpha()) imageMatrix.getImage().get(i).set(j, new Color(r, g, b, a));
            else imageMatrix.getImage().get(i).set(j, new Color(r, g, b));
        }
    }


}
