package LSB;

import imgAnalyse.ImageMatrix;

/**
 * Created by aelar on 08/12/15.
 */

/**
 * \file          DirectWay.java
 * \author    Benjamin PIAT
 * \version   1.0
 * \date       02 December 2015
 * \brief       The intern spiral pattern
 *
 * \details    Go through the image from the center in a spiral to extern pixels
 */
public class InternSpiralWay extends WayStrategy {


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
        int top = (imageMatrix.getHeight() - 1) / 2;
        int down = ((imageMatrix.getHeight() - 1)) / 2 + 1;
        int left = (imageMatrix.getWidth() - 1) / 2;
        int right = ((imageMatrix.getWidth() - 1) / 2) + 1;
        String hide;

        while (true) {
            // Browsing top row
            for (int j = left; j > right && begin < msg.length(); --j) {
                hide = msg.substring(begin, begin + nbBytes);
                hider.hide(imageMatrix, top, j, mask, hide);
                begin += nbBytes;
            }
            top--;
            if (top <= down || begin >= msg.length()) break;
            //Browsing the rightmost column
            for (int i = top; i > down && begin < msg.length(); --i) {
                hide = msg.substring(begin, begin + nbBytes);
                hider.hide(imageMatrix, i, right, mask, hide);
                begin += nbBytes;
            }
            right++;
            if (left <= right || begin >= msg.length()) break;
            //Browsing the bottom row
            for (int j = right; j < left && begin < msg.length(); ++j) {
                hide = msg.substring(begin, begin + nbBytes);
                hider.hide(imageMatrix, down, j, mask, hide);
                begin += nbBytes;
            }
            down++;
            if (top <= down || begin >= msg.length()) break;
            //Browsing the leftmost column
            for (int i = down; i < top && begin < msg.length(); ++i) {
                hide = msg.substring(begin, begin + nbBytes);
                hider.hide(imageMatrix, i, left, mask, hide);
                begin += nbBytes;
            }
            left--;
            if (left <= right || begin >= msg.length()) break;

        }

        return begin;
    }
}
