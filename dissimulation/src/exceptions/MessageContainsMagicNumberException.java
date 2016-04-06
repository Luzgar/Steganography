package exceptions;

/**
 * Created by Kevin on 09/01/2016.
 */

/**
 * \file          MessageContainsMagicNumberException.java
 * \author    Kevin DUGLUE
 * \version   1.0
 * \date       09 January 2016
 * \brief       Exception which is raised when the message contain the magic number
 */

public class MessageContainsMagicNumberException extends Exception {
    /**
     * \brief       Default constructor
     * \details     Used to raise the exception
     */
    public MessageContainsMagicNumberException() {
        super("The message to hide already contains the magic number");
    }
}
