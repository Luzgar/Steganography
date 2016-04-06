package exceptions;

/**
 * Created by aelar on 12/12/15.
 */

/**
 * \file          ImageTooShort.java
 * \author    Benjamin PIAT
 * \version   1.0
 * \date       12 December 2015
 * \brief       Exception which is raised when the given image is too short to hide the message
 */
public class ImageTooShort extends Exception {

    /**
     * \brief       Default constructor
     * \details     Used to raise the exception
     */
    public ImageTooShort() {
        super("Your image isn't big enough to hide your message");
    }
}
