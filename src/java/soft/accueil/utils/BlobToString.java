package soft.accueil.utils;

public class BlobToString {

    public String generate(byte[] blob) {
        String str = "";
        //read bytes from ByteArrayInputStream using read method
        for (byte b : blob) {
            str = str + (char) b;
        }
        return str ;
    }
}
