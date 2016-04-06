package exceptions;

/**
 * Created by Loïc on 18/01/2016.
 */
public class NotAllowMultipleValuesException extends Exception {
    public NotAllowMultipleValuesException(){super("There is an option who do not need parameters (-compress or -show)");}
}
