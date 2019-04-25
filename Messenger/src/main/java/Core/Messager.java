package Core;

import Core.Exception.CreateFileMessageFallException;

import java.util.List;

public interface Messager {

    public boolean sendMessage(Message message);

    /**
     * Блокирующий метод, возвращает List отсортированных по времени сообщений
     *
     * @return отсортированный по времени List<Message>
     */
    public List<Message> getMessages();
}
