package exceptions;

/**
 * Created by Lo�c on 11/15/2015.
 */

/**
 * \file          InvalidValueException.java
 * \author    Loïc ROSE
 * \version   1.0
 * \date       15 November 2015
 * \brief       Exception which is raised when a value passed is wrong
 */
public class InvalidValueException extends Exception {

    /**
     * \brief       Default constructor
     * \details     Used to raise the exception
     */
    public InvalidValueException() {
        super("Invalid value");
    }

    /**
     * \brief       Same as the default constructor but specify the invalid value
     */
    public InvalidValueException(String value) {
        super("Invalid value: " + value);
    }

}
