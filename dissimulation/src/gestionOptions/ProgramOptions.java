package gestionOptions;

import action.Ways;
import exceptions.ArgumentMissingException;
import exceptions.InvalidArgException;
import imgAnalyse.Channels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Loic Rose on 11/17/2015.
 */
public class ProgramOptions {

    private String inputFormat; // The input format (i.e: png, jpeg...)
    private String outputFormat; // The output format (i.e: png, ppm, bmp...)
    private String inputFile; // The inputFile name (i.e: small)
    private String outputFile; // The outputFile name (i.e: result)
    private String message; // The message we are going to dissimulate (+compress)
    private String numberBits; // The number of bits we use for the dissimulation
    private String channelsString; // Represent the chanel where we dissimulate
    private Channels[] channels; // An array of Channels that will be used for the dissimulation
    private Ways pattern; // The pattern we are going to use; can be direct, reverse or internal/external spiral
    private String magicNumber; // The string representing the magic number
    private String metricsString; // Represent all the metrics we want to use
    private String[] metrics; // Array of string representing all metrics specified by the user
    private String[] compresses; // Array of string representing all the compress options specified by the user
    private boolean compress; // If the option compress is used ?
    private boolean show; // If the option show is used ?

    /**
     * \brief       Returns the input file format
     * \return      Input file format
     */
    public String getInputFormat() {
        return inputFormat.toLowerCase();
    }

    /**
     * \brief   Set the input file format
     * \param   value Input file format
     */
    public void setInputFormat(String value) {
        inputFormat = value.toLowerCase();
    }

    /**
     * \brief       Returns the output file format.
     * \return      Output file format.
     */
    public String getOutputFormat() {
        return outputFormat.toLowerCase();
    }

    /**
     * \brief   Set the output file format
     * \param   value Output file format
     */
    public void setOutputFormat(String value) {
        outputFormat = value.toLowerCase();
    }

    /**
     * \brief       Get the path/name of the input file
     * \return      the path/name of the input file
     */
    public String getInputFile() {
        return inputFile;
    }

    /**
     * \brief       Set the path/name of the input file
     * \param   value      Path/name of the input file
     */
    public void setInputFile(String value) {
        inputFile = value.toLowerCase();
    }

    // Output File

    /**
     * \brief       Get the path/name of the output file
     * \return      A \e String representing the path/name of the output file
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * \brief       Set the path/name of the output file
     * \param   value      Represent the path/name of the output file
     */
    public void setOutputFile(String value) {
        outputFile = value.toLowerCase();
    }

    // message.Message

    /**
     * \brief       Get the message to hide
     * \return      A \e String representing the message to hide
     */
    public String getMessage() {
        return message;
    }

    /**
     * \brief       Set the message to hide
     * \param   value      Represent the message to hide
     */
    public void setMessage(String value) {
        message = value;
    }

    /**
     * \brief       Get the channel(s) used to hide the message
     * \return      A \e Channels[] representing the channel(s) used to hide the message
     */
    public Channels[] getChannels() {
        return channels;
    }

    /**
     * \brief       Set the channel(s) used to hide the message
     * \param   value      Represent the channel(s) used to hide the message
     */
    public void setChannels(String value) throws InvalidArgException {
        channelsString=value;
        String delims = ",";
        String[] tokens = value.split(delims);
        channels = new Channels[tokens.length];

        for (int i = 0; i < tokens.length; ++i) {
            switch (tokens[i]) {
                case "Red":
                    channels[i] = Channels.RED;
                    break;
                case "Green":
                    channels[i] = Channels.GREEN;
                    break;
                case "Blue":
                    channels[i] = Channels.BLUE;
                    break;
                case "Grey":
                    channels[i] = Channels.GREY;
                    break;
                case "Alpha":
                    channels[i] = Channels.ALPHA;
                    break;
                default:
                    throw new InvalidArgException("Invalid channel");

            }
        }
    }

    /**
     * \brief       Get the pattern used to go through the image matrix
     * \return      A \e Ways the pattern used to go through the image matrix
     */
    public Ways getPattern() {
        return pattern;
    }

    /**
     * \brief       Set the pattern used to go through the image matrix
     * \param   value      represent the pattern used to go through the image matrix
     */
    public void setPattern(String value) throws InvalidArgException {
        switch (value) {
            case "direct":
                pattern = Ways.DIRECT;
                break;
            case "reverse":
                pattern = Ways.REVERSE;
                break;
            case "external_spiral":
                pattern = Ways.EXTSPI;
                break;
            case "internal_spiral":
                pattern = Ways.INTSPI;
                break;
            default:
                throw new InvalidArgException("Invalid pattern");
        }
    }


    /**
     * \brief       Get the number of bits used to hide the message in each pixel for each channel
     * \return      A \e String representing the number of bits used to hide the message in each pixel for each channel
     */
    public String getNumberBits() {
        return numberBits;
    }

    /**
     * \brief       Set the number of bits used to hide the message in each pixel for each channel
     * \param   value      Represent the number of bits used to hide the message in each pixel for each channel
     */
    public void setNumberBits(String value) {
        numberBits = value;
    }


    /**
     * \brief       Get the magic number
     * \return      A \e String representing the magic number
     */
    public String getMagicNumber() {
        return magicNumber;
    }

    /**
     * \brief       Set the magic number
     * \param   value      represent the magic number
     */
    public void setMagicNumber(String value) {
        magicNumber = convertHexToString(value);
    }

    /**
     * \brief       Get the metrics
     * \return      A \e String representing the metrics
     */
    public String[] getMetrics() {
        return metrics;
    }

    /**
     * \brief       Set the metrics
     * \param   value      Represent the metrics
     */
    public void setMetrics(String value) {
        metricsString=value;
        metrics = value.split(",");
    }

    /**
     * This method return all the parameters specified by the user for the option -compress
     * @return an Array of String
     */
    public String[] getCompresses(){
        return compresses;
    }

    /**
     * This method will add the values of the string in the array of the parameters for the option -compress
     * @param value the compress's parameters separated by
     */
    public void setCompresses(String value){
        compresses = value.split(",");
    }

    /**
     * This method return the boolean compress (i.e: if the user mentioned the option compress it will be true, and if not, it will be false)
     * @return the boolean compress
     */
    public boolean getCompress() {
        return compress;
    }

    /**
     * If the user typed -compress in the command line, we change the value of compress to 'true' and if not, it's just 'false'
     * @param compress a boolean
     */
    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    /**
     * * This method return the boolean show (i.e: if the user mentioned the option show it will be true, and if not, it will be false)
     * @return the boolean show
     */
    public boolean getShow() {
        return show;
    }

    /**
     * If the user typed -show in the command line, we change the value of compress to 'true' and if not, it's just 'false'
     * @param show a boolean
     */
    public void setShow(boolean show) {
        this.show = show;
    }


    /**
     * Apply additional rules to define options values
     */
    public void applyAdditionalLogic() throws ArgumentMissingException {

        //OutputFile
        if (outputFormat.isEmpty()) {
            outputFormat = getFileExtension(outputFile);
            // If output file has no extension then...
            if (outputFormat.isEmpty()) {
                // if the input format is JPEG/JPG we can not have a output format like JPEG/JPG -> the output format is, by default, PNG
                if (inputFormat.equals("jpg") || inputFormat.equals("jpeg")) {
                    outputFormat = "png";
                } else {
                    // The output format is the same as the input format
                    outputFormat = inputFormat;
                }
            }
        }

        // InputFile
        // If there is no input format
        if (inputFormat.isEmpty()) {
            // we take the file extension of the input file as input format
            inputFormat = getFileExtension(inputFile);
        }

        // Message
        // If the user typed 'stdin'...
        if (message.equals("stdin")) {
            System.out.print("Text to encrypt: ");
            // We are going to read the input stream (i.e: the user will have to type his message)
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                // Read the line entered by the user
                message = br.readLine();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }

        // If option -show is used but not option -compress ...
        if (show == true && compress == false) {
            // ... then we raise an exception !
            throw new ArgumentMissingException();
        }

    }

    /**
     * \brief Return the program options and their values as a \e String
     */
    public String toString() {
        /*StringBuilder sb = new StringBuilder("----------------------- SELECTED OPTIONS -----------------------\n");
        sb.append("Input format: " + inputFormat + "\n");
        sb.append("Output format: " + outputFormat + "\n");
        sb.append("Input file: " + inputFile + "\n");
        sb.append("Output file: " + outputFile + "\n");
        sb.append("Message to dissimulate: " + message + "\n");
        sb.append("Number of bits: " + numberBits + "\n");
        sb.append("Channel(s) used: " + channelsString + "\n");
        sb.append("Pattern used: " + pattern + "\n");
        sb.append("Magic number: " + magicNumber + "\n");
        if (!metricsString.isEmpty()) sb.append("Metric(s) used: " + metricsString + "\n");
        if (compress) sb.append("Compress option used! " + compresses[0] + "\n");
        if (show) sb.append("Show option used! ");
        return sb.toString();*/
        return "";
    }


    // MagicNumber
    private String convertHexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 3) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }


        return sb.toString();
    }

    /**
     * Return the extension of a filename
     *
     * @param filename
     * @return extension of the file name
     */
    private String getFileExtension(String filename) {
        try {
            return filename.substring(filename.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

}