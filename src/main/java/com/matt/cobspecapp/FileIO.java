package cobspecapp;

/**
 * Created by matthewhiggins on 7/26/16.
 */
public interface FileIO {
    byte[] getAllBytesFromFile(String path);
    void writeToFile(String path, byte[] newFileContents);
}
