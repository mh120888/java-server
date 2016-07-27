package cobspecapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by matthewhiggins on 7/26/16.
 */
public class RealFileIO implements FileIO {

    public RealFileIO() {}

    public byte[] getAllBytesFromFile(String path) {
        byte[] result = new byte[0];
        try {
            result = Files.readAllBytes(Paths.get(path));;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public void writeToFile(String path, byte[] newFileContents) {
        File myFile = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(myFile, false);
            byte[] myBytes = newFileContents;
            fos.write(myBytes);
            fos.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
