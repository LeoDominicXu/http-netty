package leo.sunrise.netty;

import leo.sunrise.netty.service.HttpServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ApplicationContext ct=new ClassPathXmlApplicationContext("spring-main.xml");
    }
}
