package com.github.mh120888.mocks;

import com.github.mh120888.cobspecapp.FileIO;

public class MockFileIO implements FileIO {
    byte[] fileContents;
    boolean isDirectory = false;
    String[] fileNames = new String[0];

    public MockFileIO() {}

    public MockFileIO(String contents) {
        fileContents = contents.getBytes();
    }

    public byte[] getAllBytesFromFile(String path) {
        return fileContents;
    }

    public void overwriteFile(String path, byte[] newFileContents) {
        fileContents = newFileContents;
    }

    public String[] getFilenames(String directory) {
       return fileNames;
    }

    public void setFileNames(String[] fileNames) {
      this.fileNames = fileNames;
    }

    public boolean isDirectory(String directory) { return isDirectory; }

    public void respondToIsDirectoryWith(boolean value) {
        isDirectory = value;
    }
}
