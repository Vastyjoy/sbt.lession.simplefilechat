package GUI;

import Core.Exception.CreateDirectoryFallException;
import Core.Exception.FileIsNotDirectoryException;
import Core.FileMessager;

import java.io.File;
import java.io.IOException;

public class MainFirst {
    public static void main(String[] args) throws CreateDirectoryFallException, FileIsNotDirectoryException, IOException {
        File outDir1=new File ("C:\\Users\\Alex\\IdeaProjects\\SimpleChat\\outDir2");
        File inDir1=new File ("C:\\Users\\Alex\\IdeaProjects\\SimpleChat\\inDir2");

        FileMessager fileMessager1= new FileMessager(inDir1,outDir1);
        MainFrame mainFrame=new MainFrame(fileMessager1);

    }
}
