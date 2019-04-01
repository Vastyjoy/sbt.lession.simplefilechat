import MessagingSystemException.ServiceStarted;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException, ServiceStarted, InterruptedException {
        MessagingSystem messagingSystem=new MessagingSystem();
        messagingSystem.addOutDirectory(Paths.get("inDir1"));
        messagingSystem.addOutDirectory(Paths.get("inDir2"));
        messagingSystem.addInDirectory(Paths.get("outDir1"));
        messagingSystem.addInDirectory(Paths.get("outDir2"));
        messagingSystem.startSystem();

    }
}
