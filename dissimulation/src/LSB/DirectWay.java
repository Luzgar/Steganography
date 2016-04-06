package LSB;

import imgAnalyse.ImageMatrix;

/**
 * Created by aelar on 02/12/15.
 */
/**
 * \file          DirectWay.java
 * \author    Benjamin PIAT
 * \version   1.0
 * \date       02 December 2015
 * \brief       The direct pattern
 *
 * \details    Go through the image from top left to bottom right
 */
public class DirectWay extends WayStrategy {


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
    @Override
    protected int Browse(ImageMatrix imageMatrix, int nbBytes, int begin, String msg, int mask, Hider hider) {
        String hide;
        for (int i = 0; i < imageMatrix.getHeight() && begin < msg.length(); ++i) {
            for (int j = 0; j < imageMatrix.getWidth() && begin < msg.length(); ++j) {
                hide = msg.substring(begin, begin + nbBytes);
                hider.hide(imageMatrix, i, j, mask, hide);
                begin += nbBytes;
            }
        }

        return begin;
    }
}
