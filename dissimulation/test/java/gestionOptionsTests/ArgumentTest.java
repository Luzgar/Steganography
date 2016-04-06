package gestionOptionsTests;

import exceptions.InvalidArgException;
import exceptions.InvalidValueException;
import exceptions.TooManyValuesException;
import gestionOptions.Argument;
import gestionOptions.ArgumentManager;
import gestionOptions.ProgramOptions;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Created by Loïc on 21/01/2016.
 */
public class ArgumentTest {

    Argument theArgumentWeAreTesting;

    @Before
    public void context(){
        theArgumentWeAreTesting = new Argument("b", false, false, "1,2,3,4,5,6,7,8", "1");
    }

    @Test
    public void testGetName(){
        assertEquals("b", theArgumentWeAreTesting.getName());
    }

    @Test
    public void testIsMandatory(){
        assertEquals(false, theArgumentWeAreTesting.isMandatory());
    }

    @Test
    public void testGetAllowMultipleValues(){assertEquals(false, theArgumentWeAreTesting.getAllowsMultipleValues());}

    @Test
    public void testIsUsed(){
        // Default value of isUsed boolean is false (cf. Argument.java - attributes)
        assertEquals(false, theArgumentWeAreTesting.getIsUsed());
        // Then we set the value of isUsed to 'true'
        theArgumentWeAreTesting.setIsUsed(true);
        assertEquals(true, theArgumentWeAreTesting.getIsUsed());
    }

    @Test
    public void testGetDefaultValues(){
        HashSet<String> theGoodHashSet = new HashSet<>();
        // We add the default value of our argument (that is "1")
        theGoodHashSet.add("1");
        assertEquals(theGoodHashSet, theArgumentWeAreTesting.getDefaultValues());
    }

    @Test
    public void testGetValues(){
        HashSet<String> theGoodHashSet = new HashSet<>();
        // Because we did not specified any specific values for this argument, we just add the default value ("1") in our HashSet
        theGoodHashSet.add("1");
        assertEquals(theGoodHashSet, theArgumentWeAreTesting.getValues());
    }

    @Test
    public void testGetToString(){
        // Because we did not specified any specific values for this argument, we gonna have the name ("b") and the default value ("1")
        assertEquals("b: 1", theArgumentWeAreTesting.toString());
    }

    @Test
    public void testGetValuesString(){
        Argument anotherArgumentForThisTest = new Argument("c", true, false, "Red,Green,Blue,Alpha,Grey", "Red,Blue,Green");
        // Again, because there is no specified values, the method wil just print all the default values so : "Red,Blue,Green"!
        assertEquals("Red,Blue,Green", anotherArgumentForThisTest.getValuesString());
    }

    @Test
    public void testCheckValues() throws InvalidValueException, TooManyValuesException{
        ArrayList<String> theGoodList = new ArrayList<>();
        // Here, the user type good value for the option -b (number of bits) so it should return true
        theGoodList.add("3");
        assertEquals(true, theArgumentWeAreTesting.checkUserValues(theGoodList));
    }

    @Test
    public void testCheckUserValueThrowingInvalidValueException() throws TooManyValuesException{
        ArrayList<String> theWrongList = new ArrayList<>();
        // For the option -b (number of bits) we can only have at most 8 bits, and here, it's as if the user entered 12 bits, so it should throw the InvalidValueException
        theWrongList.add("12");
        try {
            theArgumentWeAreTesting.checkUserValues(theWrongList);
        } catch (InvalidValueException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckUsersValuesThrowingTooManyValuesException() throws InvalidValueException{
        ArrayList<String> theWrongList = new ArrayList<>();
        // The option -b (number of bits) only accept one argument, and here, it's as if the user add 2 arguments (even if they are good values), so it should throw the TooManyValuesException
        theWrongList.add("2");
        theWrongList.add("5");
        try {
            theArgumentWeAreTesting.checkUserValues(theWrongList);
        } catch (TooManyValuesException e){
            e.printStackTrace();
        }
    }

}
