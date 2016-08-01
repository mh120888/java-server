package mocks;

import cobspecapp.FileIO;

/**
 * Created by matthewhiggins on 7/26/16.
 */
public class MockFileIO implements FileIO {
    byte[] fileContents;

    public MockFileIO(String contents) {
        fileContents = contents.getBytes();
    }

    public byte[] getAllBytesFromFile(String path) {
        return fileContents;
    }

    public void overwriteFile(String path, byte[] newFileContents) {
        fileContents = newFileContents;
    }
}
