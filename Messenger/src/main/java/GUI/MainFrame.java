package GUI;

import Core.Exception.CreateFileMessageFallException;
import Core.Message;
import Core.Messager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFrame extends JFrame {

    Messager messager;
    private JTextField jtfMessage;
    private JTextField jtfName;
    private JTextArea jtaTextAreaMessage;
    // имя клиента
    private String clientName = "";

    // получаем имя клиента
    public String getClientName() {
        return this.clientName;
    }


    // конструктор
    public MainFrame(Messager messager) {

        this.clientName = clientName;
        this.messager = messager;

        // Задаём настройки элементов на форме
        setBounds(600, 300, 600, 500);
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jtaTextAreaMessage = new JTextArea();
        jtaTextAreaMessage.setEditable(false);
        jtaTextAreaMessage.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(jtaTextAreaMessage);
        add(jsp, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        JButton jbSendMessage = new JButton("Отправить");
        bottomPanel.add(jbSendMessage, BorderLayout.EAST);
        jtfMessage = new JTextField("Введите ваше сообщение: ");
        bottomPanel.add(jtfMessage, BorderLayout.CENTER);
        jtfName = new JTextField("Введите ваше имя: ");
        bottomPanel.add(jtfName, BorderLayout.WEST);

        // обработчик события нажатия кнопки отправки сообщения
        jbSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // если имя клиента, и сообщение непустые, то отправляем сообщение
                if (!jtfMessage.getText().trim().isEmpty() && !jtfName.getText().trim().isEmpty()) {
                    clientName = jtfName.getText();
                    try {
                        sendMsg();
                    } catch (CreateFileMessageFallException e1) {
                        e1.printStackTrace();
                    }
                    // фокус на текстовое поле с сообщением
                    jtfMessage.grabFocus();
                }
            }
        });

        // при фокусе поле сообщения очищается
        jtfMessage.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfMessage.setText("");
            }
        });

        // при фокусе поле имя очищается
        jtfName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfName.setText("");
            }
        });

        // в отдельном потоке начинаем работу с сервером
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // бесконечный цикл
                    while (!Thread.interrupted()) {
                        // если есть входящее сообщение
                        System.err.println("Пытаемся получить сообщения");
                        List<Message> list = messager.getMessages();
                        System.err.println("получили сообщения:"+list.size());

                        for (Message msg : list) {
                            jtaTextAreaMessage.append(msg.toString());
                        }
                        Thread.sleep(500);
                    }
                } catch (Exception e) {
                }
            }
        });

        // добавляем обработчик события закрытия окна клиентского приложения
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thread.interrupt();
                super.windowClosing(e);

            }
        });
        // отображаем форму
        thread.setDaemon(true);
        setVisible(true);
        thread.start();
    }

    // отправка сообщения
    public void sendMsg() throws CreateFileMessageFallException {
        // формируем сообщение для отправки на сервер
        Message message = new Message(clientName, jtfMessage.getText());
        // отправляем сообщение
        messager.sendMessage(message);
        jtfMessage.setText("");
    }
}
