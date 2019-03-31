import MessagingSystemException.ServiceStarted;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException, ServiceStarted, InterruptedException {
        MessagingSystem messagingSystem=new MessagingSystem();
        messagingSystem.addOutDirectory(Paths.get("C:\\Users\\Alex\\IdeaProjects\\SimpleChat\\inDir1"));
        messagingSystem.addOutDirectory(Paths.get("C:\\Users\\Alex\\IdeaProjects\\SimpleChat\\inDir2"));
        messagingSystem.addInDirectory(Paths.get("C:\\Users\\Alex\\IdeaProjects\\SimpleChat\\outDir1"));
        messagingSystem.addInDirectory(Paths.get("C:\\Users\\Alex\\IdeaProjects\\SimpleChat\\outDir2"));
        messagingSystem.startSystem();

    }
}
