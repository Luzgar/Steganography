package message;

/**
 * Created by aelar on 20/01/16.
 */
public class Binarable {
    protected static String toBinary( byte bytes )
    {
        StringBuilder sb = new StringBuilder(Byte.SIZE);
        for( int i = 0; i < Byte.SIZE ; i++ )
            sb.append((bytes << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }



}
