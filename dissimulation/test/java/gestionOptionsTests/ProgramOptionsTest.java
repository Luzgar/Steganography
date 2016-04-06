package gestionOptionsTests;

import action.Ways;
import exceptions.ArgumentMissingException;
import exceptions.InvalidArgException;
import gestionOptions.ProgramOptions;
import imgAnalyse.Channels;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Loïc
 * @date 20/01/2016
 */
public class ProgramOptionsTest {

    ProgramOptions theProgramOptions;

    @Before
    public void context() throws InvalidArgException{
        theProgramOptions = new ProgramOptions();
        theProgramOptions.setInputFormat("PPM");
        theProgramOptions.setOutputFormat("PNG");
        theProgramOptions.setInputFile("the_inputFile");
        theProgramOptions.setOutputFile("the_outputFile");
        theProgramOptions.setMessage("Here is a message!");
        theProgramOptions.setNumberBits("4");
        theProgramOptions.setChannels("Blue");
        theProgramOptions.setPattern("reverse");
        theProgramOptions.setMagicNumber("48 45 4C 50");
        theProgramOptions.setMetrics("compression_saving,compression_time,time");
        theProgramOptions.setCompress(true);
        theProgramOptions.setShow(false);
        theProgramOptions.setCompresses("bg");
    }

    @Test
    public void testGetInputFormat(){
        assertEquals("ppm", theProgramOptions.getInputFormat());
        assertNotEquals("PPM", theProgramOptions.getInputFormat());
    }

    @Test
    public void testGetOutputFormat(){
        assertEquals("png", theProgramOptions.getOutputFormat());
        assertNotEquals("PNG", theProgramOptions.getInputFormat());
    }

    @Test
    public void testGetInputFile(){
        assertEquals("the_inputfile", theProgramOptions.getInputFile());
    }

    @Test
    public void testGetOutputFile(){
        assertEquals("the_outputfile", theProgramOptions.getOutputFile());
    }

    @Test
    public void testGetMessage(){
        assertEquals("Here is a message!", theProgramOptions.getMessage());
    }

    @Test
    public void testGetChannel(){
        Channels[] theGoodChannels = {Channels.BLUE};
        assertArrayEquals(theGoodChannels, theProgramOptions.getChannels());
        Channels[] theWrongChannels = {Channels.ALPHA, Channels.GREEN, Channels.BLUE};
        assertNotSame(theWrongChannels, theProgramOptions.getChannels());
    }

    @Test
    public void testGetPatterns(){
        Ways theGoodWay = Ways.REVERSE;
        assertEquals(theGoodWay, theProgramOptions.getPattern());
        Ways theWrongWay = Ways.DIRECT;
        assertNotEquals(theWrongWay, theProgramOptions.getPattern());
    }

    @Test
    public void testIfExceptionThrowForGetPatterns(){
        try {
            theProgramOptions.setPattern("A wrong pattern");
            fail("Should throw an exception when the pattern is not correct");
        } catch (InvalidArgException iae){}
    }

    @Test
    public void testGetNumberBits(){
        assertEquals("4", theProgramOptions.getNumberBits());
        assertNotEquals("100", theProgramOptions.getNumberBits());
    }

    @Test
    public void testMagicNumber(){
        assertNotEquals("AIDE", theProgramOptions.getMagicNumber());
        assertEquals("HELP", theProgramOptions.getMagicNumber());
    }

    @Test
    public void testGetMetrics(){
        String[] theWrongArray = {"compression_saving","compression_time"};
        assertNotSame(theWrongArray, theProgramOptions.getMetrics());
        String[] theGoodArray = {"compression_saving","compression_time","time"};
        assertArrayEquals(theGoodArray, theProgramOptions.getMetrics());
    }

    @Test
    public void testGetCompressParameters(){
        String[] theWrongArray = {"more"};
        assertNotSame(theWrongArray, theProgramOptions.getCompresses());
        String[] theGoodArray = {"bg"};
        assertArrayEquals(theGoodArray, theProgramOptions.getCompresses());
    }

    @Test
    public void testGetCompress(){
        assertNotEquals(false, theProgramOptions.getCompress());
        assertEquals(true, theProgramOptions.getCompress());
    }

    @Test
    public void testGetShow(){
        assertEquals(false, theProgramOptions.getShow());
        assertNotEquals(true, theProgramOptions.getShow());
    }

    @Test
    public void testApplyAdditionalLogic(){

        // first case : we have the output file but not the outputFormat
        theProgramOptions.setOutputFormat("");
        theProgramOptions.setOutputFile("output.ppm");
        try {
            theProgramOptions.applyAdditionalLogic();
        } catch (ArgumentMissingException ame){
            ame.getMessage();
        }
        assertEquals("ppm", theProgramOptions.getOutputFormat());

        // second case : we do not have any information about the output but we have the inputFormat (a JPEG)
        theProgramOptions.setOutputFormat("");
        theProgramOptions.setOutputFile("");
        theProgramOptions.setInputFormat("jpeg");
        try {
            theProgramOptions.applyAdditionalLogic();
        } catch (ArgumentMissingException ame){
            ame.getMessage();
        }
        assertEquals("png", theProgramOptions.getOutputFormat());

        // third case : we do not have any information about the output but we have the inputFormat (anything but JPEG/JPG)
        theProgramOptions.setOutputFormat("");
        theProgramOptions.setOutputFile("");
        theProgramOptions.setInputFormat("bmp");
        try {
            theProgramOptions.applyAdditionalLogic();
        } catch (ArgumentMissingException ame){
            ame.getMessage();
        }
        assertEquals("bmp", theProgramOptions.getOutputFormat());


        // fourth case about the option -show and -compress (compress must be true, so we can use the option show)
        theProgramOptions.setCompress(false);
        theProgramOptions.setShow(true);
        try {
            theProgramOptions.applyAdditionalLogic();
            fail("The option -compress is not mentioned, so we can not use the option -show!");
        } catch (ArgumentMissingException e){}

    }



}
