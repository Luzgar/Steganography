package exceptions;


/**
 * Created by Lo�c on 11/15/2015.
 */

/**
 * \file          InvalidArgException.java
 * \author    Loïc ROSE
 * \version   1.0
 * \date       15 November 2015
 * \brief       Exception which is raised when an argument is invalid
 */

public class InvalidArgException extends Exception {

    /**
     * \brief       Default constructor
     * \details     Used to raise the exception
     */
    public InvalidArgException() {
        super("Invalid Argument");
    }

    /**
     * \brief       Same as the default constructor, but specify which argument is invalid
     */
    public InvalidArgException(String argument) {
        super("Invalid Argument: " + argument);
    }

}

