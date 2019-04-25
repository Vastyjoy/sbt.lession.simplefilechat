package Core;


import Core.Exception.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

/**
 * Мессенджер. Вид передачи сообщений запись/чтение сериализованных объектов класса Message
 * Чтение новых поступаюхших сообщений происходит из директории inputDirectory
 * Запись новых отправляемых сообщений происходит в директорию outputDirectory
 */
public class FileMessager implements Messager {

    private final Path inputPath;
    private final File outputDirectory;
    private final WatchService watchService = FileSystems.getDefault().newWatchService();


    public FileMessager(File inputDirectory, File outputDirectory) throws IOException, FileIsNotDirectoryException {

        if (!inputDirectory.exists()) inputDirectory.mkdir();
        if (!outputDirectory.exists()) outputDirectory.mkdir();

        if (inputDirectory.isFile() || outputDirectory.isFile())
            throw new FileIsNotDirectoryException(inputDirectory.toString()
                    + " or " + outputDirectory.toString() + " Isn't directory");
        File inputDirectory1 = inputDirectory;
        this.outputDirectory = outputDirectory;
        inputPath = inputDirectory.toPath();
        inputPath.register(watchService, ENTRY_CREATE);

    }

    /**
     * Сериализует в файл Message в указанную директорию outputDirectory
     *
     * @param message
     * @return true если сериализация прошла успешно, иначе false;
     */
    @Override
    public boolean sendMessage(Message message) {
        String outFilePath = outputDirectory.toString() + '\\' + message.getSerName() + ".ser";

        try (FileOutputStream fos = new FileOutputStream(outFilePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException x) {
            System.err.println("Failed to serialize out file:" + outFilePath);
            ;
        }
        return true;
    }

    /**
     * Метод дессериализует Message из файла по пути inFile
     *
     * @param inFile путь к файлу сериализованного Message
     * @return Message дессериализованный из inFile
     * @throws WrongClassReadFileException
     * @throws ReadFileMessageFallException
     */
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
            throw new ReadFileMessageFallException("Failed to deserialize out file:" + inFile.toString(), x);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;

    }

    /**
     * Блокирующий метод, возвращает List отсортированных по времени сообщений
     * Из директории inputDirectory
     *
     * @return отсортированный по времени List<Message>
     * @throws InterruptedException
     */

    @Override
    public List<Message> getMessages() {
        WatchKey key = null;
        try {
            key = watchService.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                    System.err.println(x.getMessage() + "---" + x.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        key.reset();
        resultList.sort(Comparator.comparing(Message::getDate));
        return resultList;
    }


}
