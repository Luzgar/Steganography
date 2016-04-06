package message;

/**
 * Created by aelar on 02/12/15.
 */

import message.compressionStrat.Compression;
import message.compressionStrat.Huffman.Dico;
import message.compressionStrat.HuffmanCompression.Dictionary;

/**
 * \file          Message.java
 * \author    Benjamin PIAT
 * \version   1.0
 * \date       02 December 2015
 * \brief       Prepare the message to be hidden
 */
public class Message extends Binarable {
    private String fullMessage;
    private String binString;
    private String compressedString;
    private Dico dictionnary;
    private boolean compression = false;
    private long timeCompression;

    public Dico getDictionnary() {
        return dictionnary;
    }

    public String getCompressedString() {
        return compressedString;
    }

    public long getTimeCompression() {
        return timeCompression;
    }

    /**
     * \brief       Constructor of the message to make it ready to hide
     * \param    Message         The message to hide
     * \param    MagicWord         The used magic word
     * \param    bitPerPixel        Number of bit used per pixel
     */
    public Message(byte[] Message, String MagicWord, int bitPerPixel, Compression compression) {
        fullMessage = new String(Message) + MagicWord;



        if(compression != null){
            long startTimeForCompression = System.currentTimeMillis();
            dictionnary = compression.getDictionnary();

            compressedString = compression.getCompressed(Message);
            this.compression = true;
            for(int i = 0; i < MagicWord.length(); ++i) {
                compressedString += toBinary( (byte) ((byte) MagicWord.charAt(i) & 0xFF));

            }
            timeCompression = System.currentTimeMillis() - startTimeForCompression;
            dictionnary = compression.getDictionnary();
            //System.out.println(compressedString);
        }
        binString = "";
        String character;

        for (byte b : fullMessage.getBytes()) {
            character = Integer.toBinaryString(b);
            while (character.length() < 8) character = "0" + character;
            binString += character;

        }

        for (int i = 0; i < binString.length() % bitPerPixel; ++i) binString += "0";

        if(this.compression)checkSize();
    }



    private void checkSize(){
        if((binString.length() - compressedString.length()) < 0)
            System.out.println("La compression est négatives");
    }

    /**
     * \brief       Give back the message + the magic word
     * \return    A \e String representing the full message
     */
    public String getFullMessage() {
        return fullMessage;
    }

    /**
     * \brief       Give back the message + the magic word in binary
     * \return    A \e String representing the full binary message
     */
    public String getBinString() {
        return (compression) ? compressedString : binString;
    }

    public int getBinLength(){
        return binString.length();
    }


}
