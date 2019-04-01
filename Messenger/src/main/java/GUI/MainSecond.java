package GUI;

import Core.Exception.CreateDirectoryFallException;
import Core.Exception.FileIsNotDirectoryException;
import Core.FileMessager;

import java.io.File;
import java.io.IOException;

public class MainSecond {
    public static void main(String[] args) throws CreateDirectoryFallException, FileIsNotDirectoryException, IOException {
        File outDir1=new File ("outDir1");
        File inDir1=new File ("inDir1");

        FileMessager fileMessager1= new FileMessager(inDir1,outDir1);
        MainFrame mainFrame=new MainFrame(fileMessager1);

    }
}
