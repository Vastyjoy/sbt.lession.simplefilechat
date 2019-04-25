package GUI;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

public class First {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/firstFrameContext.xml");
        MainFrame mainFrame = (MainFrame) context.getBean("MainFrame");
    }
}
