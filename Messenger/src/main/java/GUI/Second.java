package GUI;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Second {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/secondFrameContext.xml");
        MainFrame mainFrame = (MainFrame) context.getBean("MainFrame");
    }
}

