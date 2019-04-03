package Core;


import Core.Exception.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

public class FileMessager implements Messager {

    private final File inputDirectory;
    private final Path inputPath;
    private final File outputDirectory;
    private final WatchService watchService = FileSystems.getDefault().newWatchService();



    public FileMessager(File inputDirectory, File outputDirectory) throws IOException, FileIsNotDirectoryException {

        if (!inputDirectory.exists()) inputDirectory.mkdir();
        if (!outputDirectory.exists()) outputDirectory.mkdir();

        if (inputDirectory.isFile() || outputDirectory.isFile())
            throw new FileIsNotDirectoryException(inputDirectory.toString()
                    + " or " + outputDirectory.toString() + " Isn't directory");
        this.inputDirectory = inputDirectory;
        this.outputDirectory = outputDirectory;
        inputPath = inputDirectory.toPath();
        inputPath.register(watchService, ENTRY_CREATE);

    }

    /**
     *
     * @param message
     * @return
     * @throws CreateFileMessageFallException
     */
    @Override
    public boolean sendMessage(Message message) throws CreateFileMessageFallException {
        String outFilePath = outputDirectory.toString() + '\\' + message.getSerName() + ".ser";

        try (FileOutputStream fos = new FileOutputStream(outFilePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException x) {
            throw new CreateFileMessageFallException("Failed to serialize to file", x);
        }
        return true;
    }

    private Message getMessage(File inFile) throws WrongClassReadFileException, ReadFileMessageFallException {
        Message message = null;

        try (FileInputStream fis = new FileInputStream(inFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object object = ois.readObject();
            if (!object.getClass().equals(Message.class))
                throw new WrongClassReadFileException("Wrong class in file:" + inFile);
            message = (Message) object;
        } catch (IOException x) {
            x.printStackTrace();
            throw new ReadFileMessageFallException("Failed to serialize out file:" + inFile.toString(), x);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;

    }

    /**
     * Блокирующий метод, возвращает List отсортированных по времени сообщений
     *
     * @return
     * @throws InterruptedException
     */

    @Override
    public List<Message> getMessages() throws InterruptedException {
        WatchKey key = watchService.take();
        List<Message> resultList = new ArrayList<>();
        for (WatchEvent event : key.pollEvents()) {
            if (event.kind() == ENTRY_CREATE) {
                Path eventPath = ((WatchEvent<Path>) event).context();
                Path realPath = inputPath.resolve(eventPath);
                System.out.println(realPath);

                try {
                    Thread.sleep(700);
                    Message message = getMessage(realPath.toFile());
                    if (message != null) resultList.add(message);
                } catch (WrongClassReadFileException | ReadFileMessageFallException x) {
                    System.err.println(x.getMessage() + "///" + x.toString());
                }

            }
        }
        key.reset();
        resultList.sort(Comparator.comparing(Message::getDate));
        return resultList;
    }



}
