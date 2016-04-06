package exceptions;

/**
 * Created by Loïc on 11/15/2015.
 */

/**
 * \file          TooManyValuesException.java
 * \author    Loïc ROSE
 * \version   1.0
 * \date       15 November 2015
 * \brief       Exception which is raised when an argument has too many passed values
 */
public class TooManyValuesException extends Exception {

    /**
     * \brief       Default constructor
     * \details     Used to raise the exception
     */
    public TooManyValuesException() {
        super("Too many values");
    }

    /**
     * \brief       Same as the default constructor but specify which arguement has too many values
     */
    public TooManyValuesException(String arg) {
        super("Too many values: " + arg);
    }


}
