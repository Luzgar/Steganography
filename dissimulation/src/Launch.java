
import exceptions.*;
import gestionOptions.*;
import imgAnalyse.ImageMatrix;
import message.compressionStrat.Compression;
import message.compressionStrat.EvenMoreHuffman.EvenMoreHuffman;
import message.compressionStrat.HuffmanCompression.Huffman;
import message.compressionStrat.MoreHuffman.MoreHuffman;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * \file      Launch.java
 * \author    Loïc Rose
 * \version   1.0
 * \date      13 November 2015
 * \brief     Main class of the program, used to launch our program
 */
public class Launch {


    public static void main(String[] cmdLineArguments) {
        ProgramOptions options;
        long startTime = System.currentTimeMillis();

        // Create the ArgumentManager
        ArgumentManager argManager = new ArgumentManager();
        // Add all options the program can use
        argManager.addArgument(new Argument("Fin", false, false, "jpeg,png,bmp,ppm,pgm"));
        argManager.addArgument(new Argument("Fout", false, false, "png,bmp,ppm,pgm", "png")); // Default extension is .png (1)
        argManager.addArgument(new Argument("in", false, true, ""));
        argManager.addArgument(new Argument("msg", false, true, ""));
        argManager.addArgument(new Argument("out", false, false, "", "result")); // Default file name is result (2)
        // (1) AND (2) give us the default output if we do not specify the option -out or -Fout. It gives : result.png
        argManager.addArgument(new Argument("b", false, false, "1,2,3,4,5,6,7,8", "1"));
        argManager.addArgument(new Argument("c", true, false, "", "Red,Green,Blue"));
        argManager.addArgument(new Argument("p", false, false, "direct,reverse,external_spiral,internal_spiral", "direct"));
        argManager.addArgument(new Argument("magic", false, false, "", "48 45 4C 50"));
        argManager.addArgument(new Argument("metrics", true, false, "histogram,template,time,impact,compression_saving,compression_time", ""));
        argManager.addArgument(new Argument("compress", false, false, ",more,even_more,recursive,bg"));
        argManager.addArgument(new Argument("show", false, false, ""));

        try {
            // Validate command line parameters
            argManager.Validate(cmdLineArguments);

            // Now, we have completed the validation of the command line arguments.
            // We can generate the Program options to be used by the rest of the application
            options = createProgramOptions(argManager);

            // Look for the picture
            String picturePath = options.getInputFile();
            ImageMatrix imageMatrix = new ImageMatrix(picturePath);
            byte[] msgString;

            File msg = new File(new String(options.getMessage()));
            byte[] b = null;
            if (msg.canRead()) {

                InputStream content = new FileInputStream(new String(options.getMessage()));


                msgString = IOUtils.toByteArray(content);

                if (new String(msgString).contains(options.getMagicNumber()))
                    throw new MessageContainsMagicNumberException();


            } else {
                msgString = options.getMessage().getBytes();
                if (new String(msgString).contains(options.getMagicNumber()))
                    throw new MessageContainsMagicNumberException();
            }

            Compression compression = null;
            if(options.getCompress() && options.getCompresses()[0].equals("more"))compression = new MoreHuffman();
            else if(options.getCompress() && options.getCompresses()[0].equals("even_more")) compression = new EvenMoreHuffman();
            else if (options.getCompress()) compression = new Huffman();

            imageMatrix.LSB(msgString, options.getMagicNumber(), Integer.parseInt(options.getNumberBits()),
                    options.getChannels(), options.getPattern(), compression);

            System.out.println("----------------------- DATA -----------------------");

            if (options.getShow()) {
                System.out.println("Dictionary used: \n\n" + compression.getDictionnary()+ "\n");
                System.out.println("\nCompressed binary message: \n\n" + compression.getCompressed(msgString).toString() +"\n");


            }


            imageMatrix.saveImage(options.getOutputFile(), options.getOutputFormat());
            System.out.println("Encodage effectué");

            // Compute Metrics
            for (int i = 0; i < options.getMetrics().length; i++) {
                if (options.getMetrics()[i].equals("time")) {
                    long endTime = System.currentTimeMillis();
                    System.out.println("Total elapsed time during the dissimulation process is : " + (endTime - startTime) + "ms.");
                } else if (options.getMetrics()[i].equals("impact")) {
                    System.out.println("Nb pixels impacted : " + imageMatrix.getNbPixelsImpacted());
                    for (int j = 0; j < options.getChannels().length; ++j) {
                        System.out.println("Nb " + options.getChannels()[j] + " channels impacted : " + imageMatrix.getNbChannelsImpacted().get(j));
                    }
                } else if (options.getMetrics()[i].equals("compression_saving") && options.getCompress()) {
                    double theCompressedSize = imageMatrix.getMessage().getCompressedString().length();
                    double theOriginalSize = imageMatrix.getMessage().getBinLength();
                    int numberAfterComma = 2;
                    double compressionSave = calculateCompressionSave(theCompressedSize, theOriginalSize);
                    double compressionSaveAfterRound = round(compressionSave, numberAfterComma);
                    System.out.println("The space saved is: " + compressionSaveAfterRound + "%.");
                } else if (options.getMetrics()[i].equals("compression_time") && options.getCompress()) {
                    // The compression time is the difference between the end (time) of the process AND the start (time of the process)
                    System.out.println("Total elapsed time during the compression is: " + (imageMatrix.getMessage().getTimeCompression()) + "ms.");
                } else {
                    System.out.println("The option -metrics hasn't be mentioned!");
                }
            }


        } catch (MandatoryArgumentNotDefinedException e) {
            // A mandatory argument is missing
            ExitWithException(1, e);

        } catch (ArgumentAlreadyUsedException e) {
            // An argument has been specified more than once
            ExitWithException(2, e);

        } catch (InvalidArgException e) {
            // An unknown argument has been specified
            ExitWithException(3, e);

        } catch (TooManyValuesException e) {
            // Several values have been found for an argument that accepts only one
            ExitWithException(4, e);

        } catch (InvalidValueException e) {
            // An invalid value has been found
            ExitWithException(5, e);

        } catch (Exception e) {
            // Other exceptions
            ExitWithException(99, e);
        }


    }


    /**
     * End program with a specific error code. The corresponding exception message is displayed
     *
     * @param exitCode
     * @param e:       exception to display
     */
    private static void ExitWithException(int exitCode, Exception e) {
        System.out.println("ERREUR: " );
        e.printStackTrace();
        System.exit(exitCode);
    }


    /**
     * Returns a ProgramOption object that contains all options selected by the user
     *
     * @param argManager The ArgumentManager containing all command line values
     * @return ProgramOption object that contains all options selected by the user
     */
    private static ProgramOptions createProgramOptions(ArgumentManager argManager) throws InvalidArgException, ArgumentMissingException {
        ProgramOptions options = new ProgramOptions();
        // Update all options to be used by our program
        options.setInputFormat(argManager.getArgument("Fin").getValuesString());
        options.setOutputFormat(argManager.getArgument("Fout").getValuesString());
        options.setInputFile(argManager.getArgument("in").getValuesString());
        options.setOutputFile(argManager.getArgument("out").getValuesString());
        options.setMessage(argManager.getArgument("msg").getValuesString());
        options.setNumberBits(argManager.getArgument("b").getValuesString());
        options.setChannels(argManager.getArgument("c").getValuesString());
        options.setPattern(argManager.getArgument("p").getValuesString());
        options.setMagicNumber(argManager.getArgument("magic").getValuesString());
        options.setMetrics(argManager.getArgument("metrics").getValuesString());
        options.setCompress(argManager.getArgument("compress").getIsUsed());
        options.setCompresses(argManager.getArgument("compress").getValuesString());
        options.setShow(argManager.getArgument("show").getIsUsed());


        // Apply additional logic (like not allowing -show if -compress is not specified)
        options.applyAdditionalLogic();

        System.out.println(options.toString());

        System.out.println("----------------------------------------");

        // Returns the options
        return options;
    }


    /**
     * Compute the compression saving percentage
     *
     * @param compressedSize
     * @param originalSize
     * @return compression saving percentage
     * @throws IOException
     */
    private static double calculateCompressionSave(double compressedSize, double originalSize)  {
        double compressionSave = 0;
        compressionSave = (1 - (compressedSize / originalSize)) * 100;
        return compressionSave;
    }

    /**
     * Round the given number
     *
     * @param theNumber
     * @param n
     * @return
     */
    private static double round(double theNumber, int n) {
        return (double) ((int) (theNumber * Math.pow(10, n) + .5)) / Math.pow(10, n);
    }

}
