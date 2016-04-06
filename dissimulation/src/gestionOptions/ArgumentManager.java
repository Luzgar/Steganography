package gestionOptions;

import exceptions.*;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 * @file      ArgumentManager.java
 * @author    Loïc ROSE
 * @version   1.0
 * @date      17 January 2016
 * @brief     Manage all possible program options, and validate arguments passed on the command line
 * @details   Store all options allowed by the program, with the list of valid and default values. It also allow to
 *            validate the options passed through the command line.
 */
public class ArgumentManager {

    // Collection of arguments allowed by the program. The argument name will be used as a key.
    private Hashtable<String, Argument> arguments = new Hashtable<String, Argument>();

    /**
     * @brief  Default constructor
     */
    public ArgumentManager() {
    }

    /**
     * Add a new argument
     * @param argument : Argument to add
     */
    public void addArgument(Argument argument){
        arguments.put(argument.getName(),argument);
    }

    /**
     * Returns a previously stored argument using its name
     * @param argName : Argument name
     */
    public Argument getArgument(String argName){
        return arguments.get(argName);
    }


    /**
     * Validate an array of argument against the list of authorized arguments previously added to the ArgumentManager.
     * In case of a validation error, an exception is raised
     * @param args : array or string arguments and values to validate (from the command line)
     * @throws InvalidArgException
     * @throws MandatoryArgumentNotDefinedException
     * @throws ArgumentAlreadyUsedException
     * @throws InvalidValueException
     * @throws TooManyValuesException
     */
    public void Validate(String[] args) throws InvalidArgException, MandatoryArgumentNotDefinedException, ArgumentAlreadyUsedException, InvalidValueException, TooManyValuesException {
        Argument currentArgument = null;
        ArrayList<String> argValues = new ArrayList<String>();
        String argName;

        // Loop on all command line arguments
        for (int i = 0; i < args.length; i++) {
            // If current item starts with a "-", it's an argument
            if (args[i].startsWith("-")) {
                // Check if we were already working with a previous argument
                if (currentArgument != null) {
                    // If yes, then we are ready to check if options of previous argument are valid
                    currentArgument.checkUserValues(argValues);
                    // Do some cleanup
                    currentArgument = null;
                    argValues.clear();
                }

                // Get argument name, without the leading "-" (ex: Fin)
                argName = args[i].substring(1);

                // Check if argument name corresponds to a valid one
                if (!arguments.containsKey(argName)) {
                    throw new InvalidArgException(argName);
                }

                // Retrieve Argument using its name as a key
                currentArgument = arguments.get(argName);

                // Is this argument already used ?
                if (currentArgument.getIsUsed()) {
                    throw new ArgumentAlreadyUsedException(currentArgument.getName());
                }
                // Update the Argument to indicate its in use
                currentArgument.setIsUsed(true);

            } else {
                // Current item doesn't start with a "-", so its an option
                // If there was to current argument, it's an error
                if (currentArgument == null) {
                    throw new InvalidArgException(args[i]);

                }
                // Otherwise, we add the item to the list of argument values
                argValues.add(args[i]);
            }
        }
        // Do not forget , once finished, to validate the values of the last argument !
        if (currentArgument != null) {
            currentArgument.checkUserValues(argValues);
        }

        // Check if all mandatory argument are specified
        for (Argument arg : arguments.values()) {
            // If an argument is mandatory but not specified, we throw an exception !
            if (arg.isMandatory() && !(arg.getIsUsed())) {
                throw new MandatoryArgumentNotDefinedException(arg.getName());
            }

        }

    }

}
