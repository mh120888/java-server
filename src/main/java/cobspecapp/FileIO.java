package cobspecapp;

public interface FileIO {
    byte[] getAllBytesFromFile(String path);
    void overwriteFile(String path, byte[] newFileContents);
    String[] getFilenames(String directory);
    boolean isDirectory(String directory);
}
