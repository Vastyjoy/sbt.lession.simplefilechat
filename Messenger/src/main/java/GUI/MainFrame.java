package GUI;

import Core.Exception.CreateFileMessageFallException;
import Core.Message;
import Core.Messager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFrame extends JFrame {

    private String userName;
    Messager messager;
    private JTextField jtfMessage;
    private JLabel jtfName;
    private JTextArea jtaTextAreaMessage;


    // получаем имя клиента
    public String getClientName() {
        return this.userName;
    }


    // конструктор
    public MainFrame(String userName, Messager messager) {
        this.userName = userName;
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
        jtfName = new JLabel("Ваш никнейм:" + userName);
        add(jtfName, BorderLayout.NORTH);

        // обработчик события нажатия кнопки отправки сообщения
        jbSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // если имя клиента, и сообщение непустые, то отправляем сообщение
                if (!jtfMessage.getText().trim().isEmpty()) {
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


        // в отдельном потоке начинаем работу с сервером
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // бесконечный цикл
                    while (!Thread.interrupted()) {
                        // если есть входящее сообщение
                        System.err.println("Пытаемся получить сообщения");
                        List<Message> list = messager.getMessages();
                        System.err.println("получили сообщения:" + list.size());

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

    private void sendMsg() throws CreateFileMessageFallException {
        Message message = new Message(userName, jtfMessage.getText());
        messager.sendMessage(message);
        jtfMessage.setText("");
    }
}
