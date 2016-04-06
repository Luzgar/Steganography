package exceptions;

/**
 * Created by aelar on 16/11/15.
 */

/**
 * \file          ChannelException.java
 * \author    Benjamin PIAT
 * \version   1.0
 * \date       16 November 2015
 * \brief       Exception which is raised when a specified channel is wrong
 */

public class ChannelException extends Exception {

    /**
     * \brief       Default constructor
     * \details     Used to raise the exception
     */
    public ChannelException() {
        super("Error in channel specification");
    }
}
