import MessagingSystemException.ServiceStarted;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

public class MessagingSystem {

    private final List<Path> inDirectory = new ArrayList<>();
    private final List<Path> outDirectory = new ArrayList<>();
    private final WatchService watchService = FileSystems.getDefault().newWatchService();
    private boolean isStart = false;

    public MessagingSystem() throws IOException {
    }

    /**
     * Необходимо обратить внимание на решение проблемы коллизии имен.
     * Файлы должны содержать уникальный идентификатор
     */

    /**
     * Добавление новых входящих путей в отслеживаемую систему
     *
     * @param path
     */
    public void addInDirectory(Path path) throws ServiceStarted {
        if (isStart)
            throw new ServiceStarted("Система уже запускалась, регистрация директорий должна происходить до запуска программы");
        inDirectory.add(path);
    }

    /**
     * Добавлене новых исходящих путей в отслеживаемую систему
     *
     * @param path
     */
    public void addOutDirectory(Path path) throws ServiceStarted {
        if (isStart)
            throw new ServiceStarted("Система уже запускалась, регистрация директорий должна происходить до запуска программы");
        outDirectory.add(path);
    }

    /**
     * Регистрация листенера во все входящие пути
     *
     * @throws IOException
     */
    protected void registrInDirectory() throws IOException {

        for (Path path : inDirectory) {
            System.out.println(path.toAbsolutePath());
            path.toAbsolutePath().register(watchService, ENTRY_CREATE);
        }
    }

    /**
     * Блокирующий метод, получение созданных файлов.
     *
     * @return
     * @throws InterruptedException
     */
    protected List<File> getCreatedFile() throws InterruptedException {
        WatchKey key = watchService.take();
        List<File> files = new ArrayList<>();
        for (WatchEvent event : key.pollEvents()) {
            if (event.kind() == ENTRY_CREATE) {
                Path eventPath = ((WatchEvent<Path>) event).context();
                for (Path inPath : inDirectory) {
                    Path child = inPath.resolve(eventPath);
                    File file = new File(child.toAbsolutePath().toString());
                    if (file.exists())
                        files.add(file);
                }
            }
        }
        key.reset();

        return files;
    }

    protected void copyFile() {

    }

    /**
     * Блокирующий метод. Запускает систему копирования из директорий in в директорию out;
     *
     * @throws ServiceStarted
     * @throws IOException
     * @throws InterruptedException
     */
    public void startSystem() throws ServiceStarted, IOException, InterruptedException {
        if (isStart) throw new ServiceStarted("Система уже запущена.");
        registrInDirectory();
        for (; ; ) {
            List<File> files = getCreatedFile();
            for (File inFile : files) {
                for (Path outPath : outDirectory) {
                    File outFile = Paths.get(outPath.toAbsolutePath() + "\\" + inFile.getName()).toFile();
                    Files.copy(inFile.toPath(), outFile.toPath());
                }
            }
        }
    }

}
