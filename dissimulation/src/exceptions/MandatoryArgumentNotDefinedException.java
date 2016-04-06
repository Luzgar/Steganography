package exceptions;

/**
 * Created by Lo�c on 11/15/2015.
 */

/**
 * \file          MandatoryArgumentNotDefineException.java
 * \author    Loïc ROSE
 * \version   1.0
 * \date       15 November 2015
 * \brief       Exception which is raised when a mandatory argument is not specified
 */
public class MandatoryArgumentNotDefinedException extends Exception {

    /**
     * \brief       Default constructor
     * \details     Used to raise the exception
     */
    public MandatoryArgumentNotDefinedException() {
        super("Mandatory argument not specified");
    }

    /**
     * \brief       Same as the default constructor but specify the missing argument
     */
    public MandatoryArgumentNotDefinedException(String argument) {
        super("Mandatory argument not specified: " + argument);
    }

}
