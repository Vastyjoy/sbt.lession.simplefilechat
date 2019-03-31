package Core;

import Core.Exception.CreateFileMessageFallException;

import java.util.List;

public interface Messager {
    public boolean sendMessage(Message message) throws CreateFileMessageFallException;
    public List<Message> getMessages() throws InterruptedException;
}
