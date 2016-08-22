package cobspecapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public void overwriteFile(String path, byte[] newFileContents) {
        File myFile = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(myFile, false);
            fos.write(newFileContents);
            fos.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public String[] getFilenames(String directory) {
        File file = new File(directory);
        return file.list();
    }

    public boolean isDirectory(String filepath) {
        File file = new File(filepath);
        return file.isDirectory();
    }

}
