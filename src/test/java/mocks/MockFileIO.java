package mocks;

import cobspecapp.FileIO;

public class MockFileIO implements FileIO {
    byte[] fileContents;
    boolean isDirectory = false;

    public MockFileIO(String contents) {
        fileContents = contents.getBytes();
    }

    public byte[] getAllBytesFromFile(String path) {
        return fileContents;
    }

    public void overwriteFile(String path, byte[] newFileContents) {
        fileContents = newFileContents;
    }

    public String[] getFilenames(String directory) { return null; }

    public boolean isDirectory(String directory) { return isDirectory; }

    public void responseToIsDirectoryWith(boolean value) {
        isDirectory = value;
    }
}
