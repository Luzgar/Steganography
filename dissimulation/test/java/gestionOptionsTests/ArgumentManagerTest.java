package gestionOptionsTests;

import exceptions.*;
import gestionOptions.Argument;
import gestionOptions.ArgumentManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Created by Loïc on 20/01/2016.
 */
public class ArgumentManagerTest {

    ArgumentManager theArgumentManager;

    @Before
    public void context(){
        theArgumentManager = new ArgumentManager();
        theArgumentManager.addArgument(new Argument("Fin", false, false, "jpeg,png,bmp,ppm,pgm"));
        theArgumentManager.addArgument(new Argument("Fout", false, false, "png,bmp,ppm,pgm", "png"));
        theArgumentManager.addArgument(new Argument("in", false, true, ""));
        theArgumentManager.addArgument(new Argument("msg", false, true, ""));
        theArgumentManager.addArgument(new Argument("out", false, false, "", "result"));
        theArgumentManager.addArgument(new Argument("b", false, false, "1,2,3,4,5,6,7,8", "1"));
        theArgumentManager.addArgument(new Argument("c", true, false, "Red,Green,Blue,Alpha,Grey", "Red,Green,Blue"));
        theArgumentManager.addArgument(new Argument("p", false, false, "direct,reverse,external_spiral,internal_spiral", "direct"));
        theArgumentManager.addArgument(new Argument("magic", false, false, "", "48 45 4C 50"));
        theArgumentManager.addArgument(new Argument("metrics", true, false, "histogram,template,time,impact,compression_saving,compression_time", "time"));
        theArgumentManager.addArgument(new Argument("compress", true, false, ",more,even_more,recursive,bg"));
        theArgumentManager.addArgument(new Argument("show", false, false, ""));
    }

    @Test
    public void testGetArgument(){
        Argument arg1 = new Argument("Fin", false, false, "jpeg,png,bmp,ppm,pgm");
        Argument arg2 = new Argument("Fout", false, false, "png,bmp,ppm,pgm", "png");
        // Temporary test... to be change
        assertEquals("Fin", theArgumentManager.getArgument("Fin").getName());
        assertEquals("Fout", theArgumentManager.getArgument("Fout").getName());
        assertNotEquals("Fin", theArgumentManager.getArgument("in").getName());
        assertNotEquals("Fout", theArgumentManager.getArgument("out").getName());
    }

    @Test
    public void testValidateThrowingInvalidArgException() throws MandatoryArgumentNotDefinedException, ArgumentAlreadyUsedException, InvalidValueException, TooManyValuesException{
        String[] theWrongArray = {"-inn", "inputFile"};
        try {
            theArgumentManager.Validate(theWrongArray);
            fail("The option -inn is not correct!");
        } catch (InvalidArgException e){
            //e.printStackTrace();
        }
    }

    @Test
    public void testValidateThrowingArgumentAlreadyUsedException() throws MandatoryArgumentNotDefinedException, InvalidValueException, TooManyValuesException, InvalidArgException{
        String[] theWrongArray = {"-in", "input", "-in", "inputInput"};
        try {
            theArgumentManager.Validate(theWrongArray);
            fail("The option -in is used two times!");
        } catch (ArgumentAlreadyUsedException e){
            //e.printStackTrace();
        }
    }

    @Test
    public void testValidateThrowingMandatoryArgumentNotDefined() throws InvalidValueException,TooManyValuesException, InvalidArgException, ArgumentAlreadyUsedException{
        String[] theWrongArray = {"-Fout", "png", "-b", "3"};
        try {
            theArgumentManager.Validate(theWrongArray);
            fail("Mandatory argument not defined!");
        } catch (MandatoryArgumentNotDefinedException e){
            //e.printStackTrace();
        }
    }

    @Test
    public void testValidateThrowingInvalidValueException() throws InvalidArgException, TooManyValuesException, ArgumentAlreadyUsedException, MandatoryArgumentNotDefinedException{
        String[] theWrongArray = {"-Fout", "png", "-b", "3", "-c", "Red,Blue,Green"};
        try {
            theArgumentManager.Validate(theWrongArray);
        } catch (InvalidValueException e){
            //e.printStackTrace();
        }
    }

    @Test
    public void testValidateThrowingTooManyValuesException() throws InvalidArgException, InvalidValueException, ArgumentAlreadyUsedException, MandatoryArgumentNotDefinedException{
        String[] theWrongArray = {"-Fout", "png", "ppm"};
        try {
            theArgumentManager.Validate(theWrongArray);
        } catch (TooManyValuesException e){
            //e.printStackTrace();
        }
    }


}
