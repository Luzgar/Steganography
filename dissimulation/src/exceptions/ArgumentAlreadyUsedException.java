package exceptions;

/**
 * Created by Lo�c on 11/15/2015.
 */

/**
 * \file          ArgumentAlreadyUsedException.java
 * \author    Loïc ROSE
 * \version   1.0
 * \date       15 November 2015
 * \brief       Exception which is raised when an argument is specified more than one time
 */
public class ArgumentAlreadyUsedException extends Exception {

    /**
     * \brief       Default constructor
     * \details     Used to raise the exception
     */
    public ArgumentAlreadyUsedException() {
        super("Argument already specified");
    }

    /**
     * \brief       Same as the default constructor but specify the argument already given
     */
    public ArgumentAlreadyUsedException(String argument) {
        super("Argument already specified: " + argument);
    }

}
