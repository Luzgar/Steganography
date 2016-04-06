package gestionOptions;

import exceptions.InvalidValueException;
import exceptions.TooManyValuesException;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;


public  class Argument {

    private String name;  // Argument name
    private boolean isUsed=false;     // true if Argument has been specified on the command line
    private boolean isMandatory=false;     // true if argument must be specified on the command line
    private boolean allowsMultipleValues; // true if argument accept multiple values ?

    private HashSet<String> authorizedValues =new HashSet<>(); // table to store all possible valid values, without duplicates
    private HashSet<String> defaultValues=new HashSet<>();     // table to store default values, without duplicates
    private LinkedHashSet<String> values =new LinkedHashSet<>();      // table to store final values (ie; values to be used by the program), without duplicates



    /**
     * Main constructor
     * @param name: Argument name
     * @param allowsMultipleValues: true if argument accept multiple values
     * @param isMandatory : true if argument is mandatory, false if optional
     * @param authorizedValues : list of valid values as a comma separated string
     * @param defaultValues : List of default values as a comma separated string
     */
    public Argument(String name, boolean allowsMultipleValues, boolean isMandatory, String authorizedValues, String defaultValues ) {
        this.name = name;
        this.allowsMultipleValues = allowsMultipleValues;
        this.isMandatory=isMandatory;
        addValidValues(authorizedValues);

        // Let's build the list of default values
        addDefaultValues(defaultValues);
        // Pre-fill the list of values with the default values
        addValues(defaultValues);
    }

    /**
     * Constructor
     * @param name : Nom de l'argument
     * @param allowsMultipleValues: true if argument accept multiple values
     * @param isMandatory : true if argument is mandatory, false if optional
     * @param authorizedValues : list of valid values as a comma separated string
     */
    public Argument(String name, boolean allowsMultipleValues, boolean isMandatory, String authorizedValues) {
        this(name,allowsMultipleValues,isMandatory, authorizedValues,"");
    }


    /**
     * Returns the argument's name
     * @return Argument's name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns true if argument is mandatory
     * @return true if argument is mandatory
     */
    public boolean isMandatory(){return isMandatory;}

    /**
     * Returns true if argument accepts multiple values
     * @return true if argument accepts multiple values
     */
    public boolean getAllowsMultipleValues(){return this.allowsMultipleValues;}

    /**
     * Returns true if argument is used on the command line
     * @return true if argument is used on the command line
     */
    public boolean getIsUsed(){return this.isUsed;}

    /**
     * Set if argument is used on the command line
     * @param value: true if argument is used on the command line
     */
    public void setIsUsed(boolean value){
        this.isUsed=value;
    }

    /**
     * Returns the list of default values
     * @return list of default values
     */
    public HashSet<String> getDefaultValues(){return defaultValues;}

    /**
     * Returns the list of values of the argument
     * @return list of values of the argument
     */
    public HashSet<String> getValues(){return values;}


    /**
     * Returns argument information (name and values) as a string
     * @return argument information (name and values) as a string
     */
    public String toString(){
        return name+": "+ getValuesString();
    }

    /**
     * Returns list of argument values, separated by a ","
     * @return list of argument values
     */
    public String getValuesString(){
        String result="";
        for (String value: values) {

            if (result.isEmpty()){result=value;}
            else{
                result+=","+value;
            }
        }

        return result;

    }


    /**
     * This method check if the values entered by the user are valid
     * @param theUserValues List of the values specified by the user
     * @return true if all specified values are valid
     * @throws InvalidValueException : one of the specified values is not in the list of authorized values
     * @throws TooManyValuesException : there is too many values for this argument
     */
    public boolean checkUserValues(ArrayList<String> theUserValues) throws InvalidValueException, TooManyValuesException {

        // If there is specified values, we put them in our final values (so it replace the default values)
        if ((theUserValues != null) && (theUserValues.size() > 0)) {
            // Clear the list of final values so we remove the default values
            this.values.clear();
            // And then we add the passed values (values specified by the user)
            this.values.addAll(theUserValues);
        }

        // Check if we can use many values (i.e. the argument allow multiple values)
        // If the argument do not accept many values and the final values list got a size higher than 1 -> TooManyValuesException
        if (!getAllowsMultipleValues() && values.size() > 1) {
            throw new TooManyValuesException(name);
        }

        boolean checkUserValues=false;
        // If a list of authorized values have been specified we have to check that each argument's values are in this list
        if (!authorizedValues.isEmpty()) {
            // Loop on each value of the argument
            for (String value : values) {
                checkUserValues=false;
                // If the value is in the authorized values list, everything is ok
                for (String validValue: authorizedValues) {
                    if (value.equalsIgnoreCase(validValue)) {
                        checkUserValues = true;
                        break;
                    }
                }
                // Else, we throw an exception
                if (!checkUserValues) {
                    throw new InvalidValueException(value);
                }
            }

        } else {
            // There is no authorized value list, so all values are acceptable
            checkUserValues = true;
        }

        return checkUserValues;
    }

    private void addValidValues(String values){
        // We convert the string in array (the delimiter is the character ",")
        String[] valuesTable=values.split(",");
        // Then we add each value of the new array in the list of valid values
        for(int i = 0 ; i < valuesTable.length ; i++){
            if (valuesTable[i].trim()!="") {
                authorizedValues.add(valuesTable[i]);};
        }
    }

    private void addDefaultValues(String values){
        // We convert the string in array (the delimiter is the character ",")
        String[] valuesTable=values.split(",");
        // Then we add each value of the new array in the list of valid values
        for(int i = 0 ; i < valuesTable.length ; i++){
            if (valuesTable[i].trim()!="") {defaultValues.add(valuesTable[i]);}
        }
    }

    private void addValues(String values) {
        // We convert the string in array (the delimiter is the character ",")
        String[] valuesTable = values.split(",");
        // Then we add each value of the new array in the list of final values
        for (int i = 0; i < valuesTable.length; i++) {
            if (valuesTable[i].trim() != "") {
                this.values.add(valuesTable[i]);
            }
        }
    }
}
